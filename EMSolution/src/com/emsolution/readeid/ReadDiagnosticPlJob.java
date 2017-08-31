package com.emsolution.readeid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.entity.EmsDiagnosticBuffer;
import com.emsolution.entity.EmsEidBuffer;
import com.emsolution.util.JpaUtil;

public class ReadDiagnosticPlJob implements Job {

	private static String PASSWORD = "Par_U170";
	private static String APPLICATION_ID = "API_U170";
	private static String NEXT_BUFFER = "<nextBuffer>";
	private static String NEXT_BUFFER_END = "</nextBuffer>";
	private static String URL = "<url>";
	private static String URL_END = "</url>";
	private static String MORE_DATA = "<moreData>";
	private static String MORE_DATA_END = "</moreData>";
	String bookMark = "https://www.myvisionlink.com/APIService/CATDataTopics/v4/feed/ParanaEquip/Event/";
	private static long bufferPosition;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FeedAuthenticator reader = new FeedAuthenticator(APPLICATION_ID, PASSWORD);
		EntityManager manager = null;
		//boolean isConnection = true;
		//while(isConnection){
			try {
				manager = JpaUtil.getInstance();

				Query query = manager.createQuery("From EmsEidBuffer");
				EmsEidBuffer eidBuffer = new EmsEidBuffer();
				eidBuffer.setBuffer(0L);
				if(query.getResultList().size() > 0){
					eidBuffer = (EmsEidBuffer)query.getResultList().get(0);
				}
				bookMark += eidBuffer.getBuffer();
				bufferPosition = eidBuffer.getBuffer();
				//manager.close();
				String response = null;
				BufferedReader breader = reader.getFeedReaderLinux(bookMark);
				//BufferedReader breader = reader.getFeedReader(bookMark);
				NextBuffer buffer = new NextBuffer();
				buffer.setMoreData(true);
				Object waitObj = new Object();

				while (breader != null && buffer.isMoreData()) {	
					response = readResponse(breader);
					//System.out.println(response);
					buffer = findNextBuffer(response);
					ReadXml.readXmlPL(response);
					//System.out.println(bufferPosition);
					bookMark = buffer.getUrl();
					//System.out.println(bookMark.substring(77));
					if(bookMark.equals("")){
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Não foi recuperado nenhuma máquina do Product Link PESA", "WARNING PL", "rodrigo@rdrsistemas.com.br");
						break;
					}
					bufferPosition = Long.valueOf(bookMark.substring(80));
					
					//System.out.println(bufferPosition);
					//System.out.println(bookMark);
					saveBookMark(buffer.getUrl());
					if (buffer.isMoreData()) {
						/*
						 * Give the feed a break, it is your friend ;)
						 */
						synchronized (waitObj) {
							try {
								waitObj.wait(800);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						breader = reader.getFeedReaderLinux(bookMark);
						//breader = reader.getFeedReader(bookMark);
					}
				}

				//isConnection = false;
			} catch (Exception e1) {
				e1.printStackTrace(); //TODO: Retirar linha
				EmailHelper emailHelper = new EmailHelper();
				emailHelper.sendSimpleMail("Erro ao executar serviço de importação de alarmes do Product Link PESA EID : "+e1.getMessage(), "ERRO PL EID : "+e1.getMessage(), "rodrigo@rdrsistemas.com.br");
			}
			try {
				manager = JpaUtil.getInstance();
				manager.getTransaction().begin();
				Query query = manager.createQuery("From EmsEidBuffer");
				EmsEidBuffer eidBuffer = new EmsEidBuffer();
				if(query.getResultList().size() > 0){
					eidBuffer = (EmsEidBuffer)query.getResultList().get(0);
				}
				eidBuffer.setBuffer(bufferPosition);
				manager.merge(eidBuffer);
//				query = manager.createNativeQuery("delete from PMP_MAQUINA_PL where DATEDIFF( DD, DATA_ATUALIZACAO,GETDATE()) > 20");
//				query.executeUpdate();
				manager.getTransaction().commit();
				manager.getTransaction().begin();
				String SQL = "select NIVEL, MID, EID, seg.ID as ID_SEGMENTO from EMS_TYPE_DIAGNOSTIC d, EMS_PROPOSTA p, EMS_SEGMENTO seg"+
				"	where d.ID_EMS_SEGMENTO is not null"+
				"	and seg.ID = d.ID_EMS_SEGMENTO"+
				"	and seg.ID_PROPOSTA = p.ID"+
				"	and d.EID is not null"+
				"	and p.ID_STATUS_OPT not in (select ID from EMS_STATUS_OPORTUNIDADE where SIGLA in ('FIN', 'REJ'))";
				query = manager.createNativeQuery(SQL);
				List<Object[]> result = (List<Object[]>)query.getResultList();
				for (Object[] pair : result) {
					query = manager.createNativeQuery("update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO =:idSegmento where MID =:MID and EID =:EID and NIVEL =:NIVEL");
					query.setParameter("MID", (BigDecimal)pair[1]);
					query.setParameter("EID", (BigDecimal)pair[2]);
					query.setParameter("NIVEL", (BigDecimal)pair[0]);
					query.setParameter("idSegmento", (BigDecimal)pair[3]);
					query.executeUpdate();
				}
				manager.getTransaction().commit();
				
				//manager.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
			}
		//}

	}
	
	public static void main(String[] args) {
		String a = "https://www.myvisionlink.com/APIService/CATDataTopics/v2/feed/ParanaEquip/SMULoc/697844932";
		
		System.out.println(a.substring(77));
	}
	
	 /**
	 * @param breader
	 * @return
	 */
	private static String readResponse(BufferedReader breader) {
		try {
			StringWriter swriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(swriter);
			String line = breader.readLine();
			while (line != null) {
				if (line != null) {
					writer.write(line);
					writer.newLine();
				}
				line = breader.readLine();
			}
			writer.close();
			breader.close();
			swriter.close();
			return swriter.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param response
	 * @return
	 */
	private static NextBuffer findNextBuffer(String response) {
		NextBuffer buffer = new NextBuffer();
		String nextBufferTag = extractTagValue(response,NEXT_BUFFER,NEXT_BUFFER_END);
		buffer.setUrl(extractTagValue(nextBufferTag,URL,URL_END));
		String boolString = extractTagValue(nextBufferTag,MORE_DATA,MORE_DATA_END);
		buffer.setMoreData(Boolean.valueOf(boolString).booleanValue());	
		return buffer;
	}
	
	/**
	 * @param response
	 * @return
	 */
	private static String extractTagValue(String tag, String tagBegin, String tagEnd) {

		int start = tag.indexOf(tagBegin);
		int end = tag.indexOf(tagEnd);
		if (start > -1 && end > -1) {
			return tag.substring(start + tagBegin.length(), end);
		} else {
			throw new RuntimeException("No tag value found.");
		}
	}
	
	/**
	 * @param bookMark
	 */
	private static void saveBookMark(String bookMark) {
		/*
		 * Each feed response contains a nextBuffer attribute that represents
		 * the url to be invoked to retrieve the next buffer of messages
		 * immediately following the messages represented in the response. The
		 * last parameter of the bookmark is a number that represents the unique
		 * message identifier of the last message in the retrieved result. This
		 * unique id is also contained within each message body as <messageId>.
		 * 
		 * By saving this book mark the app developer can insure that...
		 * 
		 * A) If a client side application crash or restart occurs the next
		 * invocation of the feed will produce new data (i.e. data not yet
		 * received by the client.
		 * 
		 * B) The response is received as quickly as possible given that only
		 * new data will be queried.
		 * 
		 * C) If all clients track their book mark and retrieve the minimum
		 * number of records required, the stability and responsiveness of the
		 * service for all clients will be insured.
		 * 
		 * It is very important that the app developer make an attempt at
		 * managing their client side state/bookmark.
		 */

	}

}
