package com.emsolution.read;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.entity.EmsDiagnosticBuffer;
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
	String bookMark = "https://www.myvisionlink.com/APIService/CATDataTopics/v2/feed/ParanaEquip/Diagnostic/";
	private static long bufferPosition;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FeedAuthenticator reader = new FeedAuthenticator(APPLICATION_ID, PASSWORD);
		EntityManager manager = null;
			try {
				manager = JpaUtil.getInstance();

				Query query = manager.createQuery("From EmsDiagnosticBuffer");
				EmsDiagnosticBuffer diagnosticBuffer = new EmsDiagnosticBuffer();
				diagnosticBuffer.setBuffer(0L);
				if(query.getResultList().size() > 0){
					diagnosticBuffer = (EmsDiagnosticBuffer)query.getResultList().get(0);
				}
				bookMark += diagnosticBuffer.getBuffer();
				bufferPosition = diagnosticBuffer.getBuffer();
				//manager.close();
				String response = null;
				//BufferedReader breader = reader.getFeedReader(bookMark);
				BufferedReader breader = reader.getFeedReaderLinux(bookMark);
				NextBuffer buffer = new NextBuffer();
				buffer.setMoreData(true);
				Object waitObj = new Object();

				while (breader != null && buffer.isMoreData()) {	
					response = readResponse(breader);
					buffer = findNextBuffer(response);
					ReadXml.readXmlPL(response);
					bookMark = buffer.getUrl();
					if(bookMark.equals("")){
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Não foi recuperado nenhuma máquina do Product Link PESA", "WARNING PL", "rodrigo@rdrsistemas.com.br");
						break;
					}
					bufferPosition = Long.valueOf(bookMark.substring(85));
					
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

			} catch (Exception e1) {
				e1.printStackTrace(); //TODO: Retirar linha
				EmailHelper emailHelper = new EmailHelper();
				emailHelper.sendSimpleMail("Erro ao executar serviço de importação de alarmes do Product Link PESA ALARME : "+e1.getMessage(), "ERRO ALARMES PL", "rodrigo@rdrsistemas.com.br");
			}
			try {
				//manager = JpaUtil.getInstance();
				manager.getTransaction().begin();
				Query query = manager.createQuery("From EmsDiagnosticBuffer");
				EmsDiagnosticBuffer diagnosticBuffer = new EmsDiagnosticBuffer();
				if(query.getResultList().size() > 0){
					diagnosticBuffer = (EmsDiagnosticBuffer)query.getResultList().get(0);
				}
				diagnosticBuffer.setBuffer(bufferPosition);
				manager.merge(diagnosticBuffer);
				manager.getTransaction().commit();
				
				manager.getTransaction().begin();
				String SQL = "select NIVEL, MID, CID, FMI, seg.ID as ID_SEGMENTO, d.IS_REJEITADO_EMS from EMS_TYPE_DIAGNOSTIC d, EMS_PROPOSTA p, EMS_SEGMENTO seg"+
							 "	where (d.ID_EMS_SEGMENTO is not null or d.IS_REJEITADO_EMS is not null)"+
							 "	and seg.ID = d.ID_EMS_SEGMENTO"+
							 "	and seg.ID_PROPOSTA = p.ID"+
							 "	and d.CID is not null"+
							 "	and p.ID_STATUS_OPT not in (select ID from EMS_STATUS_OPORTUNIDADE where SIGLA in ('FIN', 'REJ'))";
				query = manager.createNativeQuery(SQL);
				List<Object[]> result = (List<Object[]>)query.getResultList();
				for (Object[] pair : result) {
					query = manager.createNativeQuery("update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO =:idSegmento, IS_REJEITADO_EMS =:IS_REJEITADO_EMS where MID =:MID and CID =:CID and FMI =:FMI and NIVEL =:NIVEL");
					query.setParameter("MID", (BigDecimal)pair[1]);
					query.setParameter("CID", (BigDecimal)pair[2]);
					query.setParameter("FMI", (BigDecimal)pair[3]);
					query.setParameter("NIVEL", (BigDecimal)pair[0]);
					query.setParameter("idSegmento", (BigDecimal)pair[4]);
					query.setParameter("IS_REJEITADO_EMS", (BigDecimal)pair[5]);
					query.executeUpdate();
				}
				
				SQL = "select NIVEL, MID, CID, FMI, seg.ID as ID_SEGMENTO, d.IS_REJEITADO_EMS from EMS_TYPE_DIAGNOSTIC d, EMS_PROPOSTA p, EMS_SEGMENTO seg"+
				"	where (d.ID_EMS_SEGMENTO is not null or d.IS_REJEITADO_EMS is not null)"+
				"	and seg.ID = d.ID_EMS_SEGMENTO"+
				"	and seg.ID_PROPOSTA = p.ID"+
				"	and d.CID is null"+
				"	and p.ID_STATUS_OPT not in (select ID from EMS_STATUS_OPORTUNIDADE where SIGLA in ('FIN', 'REJ'))";
				query = manager.createNativeQuery(SQL);
				result = (List<Object[]>)query.getResultList();
				for (Object[] pair : result) {
					query = manager.createNativeQuery("update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO =:idSegmento, IS_REJEITADO_EMS =:IS_REJEITADO_EMS where MID =:MID and CID =:CID and FMI =:FMI and NIVEL =:NIVEL");
					query.setParameter("MID", (BigDecimal)pair[1]);
					query.setParameter("CID", (BigDecimal)pair[2]);
					query.setParameter("FMI", (BigDecimal)pair[3]);
					query.setParameter("NIVEL", (BigDecimal)pair[0]);
					query.setParameter("idSegmento", (BigDecimal)pair[4]);
					query.setParameter("IS_REJEITADO_EMS", (BigDecimal)pair[5]);
					query.executeUpdate();
				}
				
				manager.getTransaction().commit();
				SQL = "select t.ID_MESSAGE_ID, convert(varchar(10), t.ID_RECEIVE_TIME, 103) ID_RECEIVE_TIME from EMS_TYPE_DIAGNOSTIC t, EMS_DIAGNOSTIC d"+ 
						"	where DATEDIFF (DAY, t.ID_RECEIVE_TIME, getdate()) > 15 "+
						"	and t.ID_EMS_SEGMENTO is null"+
						"	and d.RECEIVED_TIME = t.ID_RECEIVE_TIME"+
						"	and d.MESSAGE_ID = t.ID_MESSAGE_ID	";
				query = manager.createNativeQuery(SQL);
				result = (List<Object[]>)query.getResultList();
				for (Object[] pair : result) {
					manager.getTransaction().begin();
//					SQL = "delete from EMS_TYPE_DIAGNOSTIC where convert(varchar(10),ID_RECEIVE_TIME,103) =:RECEIVED_TIME and ID_MESSAGE_ID =:MESSAGE_ID";
//					query = manager.createNativeQuery(SQL);
//					query.setParameter("MESSAGE_ID", (BigDecimal)pair[0]);
//					query.setParameter("RECEIVED_TIME", (String)pair[1]);
//					query.executeUpdate();
					SQL = "delete from EMS_DIAGNOSTIC where convert(varchar(10),RECEIVED_TIME,103) =:RECEIVED_TIME and MESSAGE_ID =:MESSAGE_ID";
					query = manager.createNativeQuery(SQL);
					query.setParameter("RECEIVED_TIME", (String)pair[1]);
					query.setParameter("MESSAGE_ID", (BigDecimal)pair[0]);
					query.executeUpdate();
					manager.getTransaction().commit();
				}
			} catch (Exception e) {
				if(manager != null && manager.getTransaction().isActive()){
					manager.getTransaction().rollback();
				}
				e.printStackTrace();
			} finally {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
			}

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
