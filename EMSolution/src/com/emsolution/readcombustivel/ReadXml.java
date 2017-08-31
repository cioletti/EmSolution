package com.emsolution.readcombustivel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.emsolution.entity.EmsFuel;
import com.emsolution.entity.EmsFuelPK;
import com.emsolution.util.JpaUtil;

public class ReadXml {

	/**
	 * @param args
	 */
	public static void readXmlPL(String response) {
		//String xml = "C:\\Documents and Settings\\RDR\\Desktop\\xml.xml";
		response = "<messages>"+ response.substring(response.indexOf("</nextBuffer>")+13, response.indexOf("</fuel:topic>"))+"</messages>";
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
				EmsFuel emsFuel = new EmsFuel();
				
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
				header.setOwner(getChildTagValue( headerEl, "owner" )); 			        

				equipament equipament = new equipament();
				Element equipamentEl = (Element)headerEl.getChildNodes().item(2);
				equipament.setSerialNumber("0"+getChildTagValue( equipamentEl, "serialNumber" )); 
				equipament.setMake(getChildTagValue( equipamentEl, "make" )); 
				equipament.setModel(getChildTagValue( equipamentEl, "model" )); 
				header.setEquipament(equipament);
				message.setHeader(header);
				EmsFuelPK emsFuelPK = new EmsFuelPK(Long.valueOf(message.getHeader().getMessageId()), df.parse(message.getHeader().getReceivedTime()));
				emsFuel.setSerialNumber(message.getHeader().getEquipament().getSerialNumber());
				emsFuel.setModel(message.getHeader().getEquipament().getModel());
				emsFuel.setMake(message.getHeader().getEquipament().getMake());
				emsFuel.setModuleCode(message.getHeader().getModuleCode());
				emsFuel.setModuleTime(df.parse(message.getHeader().getModuleTime()));
				emsFuel.setMasterMsgId(Long.valueOf(message.getHeader().getMasterMsgId()));
				
				Element consumptionEl = (Element)messageChidrenList.item(1);
				emsFuel.setConsumoTotal(Long.valueOf(getChildTagValue(consumptionEl,"value")));
				
				Element levelEl = (Element)messageChidrenList.item(2);
				
				if(levelEl != null && getChildTagValue(levelEl,"value") != null){
					emsFuel.setPercLevelUtilization(Long.valueOf(getChildTagValue(levelEl,"value")));
				}
				
				emsFuel.setEmsFuelPK(emsFuelPK);
				manager.merge(emsFuel);
				manager.getTransaction().commit();
			}  
			xml.delete();
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
