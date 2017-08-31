package com.emsolution.read;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.emsolution.entity.EmsDiagnostic;
import com.emsolution.entity.EmsDiagnosticPK;
import com.emsolution.entity.EmsTypeDiagnostic;
import com.emsolution.readeid.EmailHelper;
import com.emsolution.util.JpaUtil;

public class ReadXml {

	/**
	 * @param args
	 */
	public static void readXmlPL(String response) {
		//String xml = "C:\\Documents and Settings\\RDR\\Desktop\\xml.xml";
		response = "<messages>"+ response.substring(response.indexOf("</nextBuffer>")+13, response.indexOf("</diagnostic:topic>"))+"</messages>";
		//System.out.println(response);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		DocumentBuilder db;
		//List<message> messageList = new ArrayList<message>();
		EntityManager manager = null;
		try {
			File xml = File.createTempFile("xml", "xml", new File("."));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(xml));
			bufferedWriter.write(response);
			bufferedWriter.flush();
			bufferedWriter.close();
			db = dbf.newDocumentBuilder();
			Document doc = db.parse( xml );  
			Element elem = doc.getDocumentElement();  
			// pega todos os elementos usuario do XML  
			NodeList nl = elem.getElementsByTagName( "message" ); 
			// percorre cada elemento usuario encontrado  
			manager = JpaUtil.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for( int i=0; i<nl.getLength(); i++ ) {  
				manager.getTransaction().begin();
				EmsDiagnostic diagnosticPl = new EmsDiagnostic();
				
				message message = new message();
				Element tagMessage = (Element) nl.item( i );  
				NodeList messageChidrenList = tagMessage.getChildNodes();
				header header = new header();
				Element headerEl = (Element)messageChidrenList.item(0);
				header.setMessageId(getChildTagValue( headerEl, "messageId" ));   
				header.setMasterMsgId(getChildTagValue( headerEl, "masterMsgId" )); 
				header.setModuleCode(getChildTagValue( headerEl, "moduleCode" )); 
				header.setModuleTime(getChildTagValue( headerEl, "moduleTime" )); 
				header.setReceivedTime(getChildTagValue( headerEl, "receivedTime" )); 
				//header.setOwner(getChildTagValue( headerEl, "owner" )); 			        

				equipament equipament = new equipament();
				Element equipamentEl = (Element)headerEl.getChildNodes().item(2);
				equipament.setSerialNumber(getChildTagValue( equipamentEl, "serialNumber" )); 
				equipament.setMake(getChildTagValue( equipamentEl, "make" )); 
				equipament.setModel(getChildTagValue( equipamentEl, "model" )); 
				header.setEquipament(equipament);
				message.setHeader(header);

				Long numberOfDiagnostic = Long.valueOf(getChildTagValue(tagMessage, "numberOfDiagnostics"));

				EmsDiagnosticPK diagnosticPK = new EmsDiagnosticPK(Long.valueOf(message.getHeader().getMessageId()), df.parse(message.getHeader().getReceivedTime()));
				diagnosticPl.setSerialNumber("0"+message.getHeader().getEquipament().getSerialNumber());
				diagnosticPl.setModel(message.getHeader().getEquipament().getModel());
				diagnosticPl.setMake(message.getHeader().getEquipament().getMake());
				diagnosticPl.setModuleCode(message.getHeader().getModuleCode());
				diagnosticPl.setModuleTime(df.parse(message.getHeader().getModuleTime()));
				diagnosticPl.setMasterMsgId(Long.valueOf(message.getHeader().getMasterMsgId()));
				diagnosticPl.setNumberOfDiagnostic(numberOfDiagnostic);
				diagnosticPl.setEmsDiagnosticPK(diagnosticPK);
//				if("0NBT00739".equals(diagnosticPl.getSerialNumber())){
//					new EmailHelper().sendSimpleMail("modelo : "+diagnosticPl.getModel(), "URGENTE 0NBT00739", "rodrigo@rdrsistemas.com.br");
//				}
				manager.merge(diagnosticPl);
				Query query = manager.createNativeQuery("delete from EMS_TYPE_DIAGNOSTIC where id_message_id =:id_message_id and id_receive_time =:id_receive_time and id_ems_segmento is null");
				query.setParameter("id_message_id",diagnosticPl.getEmsDiagnosticPK().getMessageId());
				query.setParameter("id_receive_time",diagnosticPl.getEmsDiagnosticPK().getReceivedTime());
				query.executeUpdate();
				Element diagnosticBlockEl = (Element)messageChidrenList.item(2);
				for(int j = 0; j < numberOfDiagnostic; j++){
					Element diagnosticEl = (Element)diagnosticBlockEl.getChildNodes().item(j);
					EmsTypeDiagnostic  diagnostic = new EmsTypeDiagnostic();
					diagnostic.setCid(Long.valueOf(getChildTagValue(diagnosticEl, "cid")));
					diagnostic.setFmi(Long.valueOf(getChildTagValue(diagnosticEl, "fmi")));
					diagnostic.setMid(Long.valueOf(getChildTagValue(diagnosticEl, "mid")));
					diagnostic.setNivel(Long.valueOf(getChildTagValue(diagnosticEl, "level")));
					diagnostic.setOcurrances(Long.valueOf(getChildTagValue(diagnosticEl, "occurances")));
					diagnostic.setRecievedTime(df.parse(getChildTagValue(diagnosticEl, "timestamp")));
					diagnostic.setEmsDiagnostic(diagnosticPl);
					manager.merge(diagnostic);
				}
				manager.getTransaction().commit();
			}  
			xml.delete();
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		} 
	}
	 // este método lê e retorna o conteúdo (texto) de uma tag (elemento)  
    // filho da tag informada como parâmetro. A tag filho a ser pesquisada  
    // é a tag informada pelo nome (string)  
    private static String getChildTagValue( Element elem, String tagName ) throws Exception {  
    	if(tagName == null){
    		return "";
    	}
        NodeList children = elem.getElementsByTagName( tagName );  
        if( children == null ) return null;  
            Element child = (Element) children.item(0);  
        if( child == null ) return null;  
        return child.getFirstChild().getNodeValue();  
    } 

}
