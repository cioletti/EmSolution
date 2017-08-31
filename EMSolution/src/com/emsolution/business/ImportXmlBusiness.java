package com.emsolution.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.emsolution.bean.PecasBean;
import com.emsolution.bean.SegmentoBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsPecas;
import com.emsolution.entity.EmsSegmento;
import com.emsolution.entity.TwFuncionario;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.IConstantAccess;
import com.emsolution.util.JpaUtil;

public class ImportXmlBusiness {
	
	private UsuarioBean usuarioBean;

	public ImportXmlBusiness() {
		// TODO Auto-generated constructor stub
	}
	
	public ImportXmlBusiness(UsuarioBean bean) {
		this.usuarioBean = bean;
	}
	public Long salvarXml(byte[] bytesArquivo, Long idSegmento) {
		try {
			File xml = File.createTempFile(""+new Date().getTime(), ".xml", new File("."));
			FileOutputStream outPut = new FileOutputStream(xml);  
			outPut.write(bytesArquivo);  
			outPut.flush();
			outPut.close();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db;
			List<PecasBean> messageList = new ArrayList<PecasBean>();
			try{
				db = dbf.newDocumentBuilder();
				Document doc = db.parse( xml );  
				Element elem = doc.getDocumentElement();  
				// pega todos os elementos usuario do XML  

				NodeList nl = elem.getElementsByTagName( "HEADER" ); 
				Element tagMessage = (Element) nl.item( 0 ); 

				String USERID = getChildTagValue( tagMessage, "USERID" ); 
				//headerEl = (Element)messageChidrenList.item(0);
				String SERIALNO = getChildTagValue( tagMessage, "SERIALNO" ); 

				nl = elem.getElementsByTagName( "PARTS" ); 
				// percorre cada elemento usuario encontrado  
				//EntityManager manager = JpaUtil.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//manager.getTransaction().begin();
				tagMessage = (Element) nl.item( 0 );  
				NodeList messageChidrenList = tagMessage.getChildNodes();
				for( int i=0; i<messageChidrenList.getLength(); i++ ) {  

					PecasBean bean = new PecasBean();
					Element headerEl = (Element)messageChidrenList.item(i);
					bean.setPartNo(getChildTagValue( headerEl, "PARTNO" ));   
					bean.setPartName(getChildTagValue( headerEl, "PARTNAME" )); 
					bean.setQtd(getChildTagValue( headerEl, "QUANTITY" )); 
					bean.setGroupNumber(getChildTagValue( headerEl, "GROUPNO" )); 
					bean.setGroupName(getChildTagValue( headerEl, "GROUPNAME" )); 
					bean.setSmcsCode(getChildTagValue( headerEl, "SMCSCODE" )); 			        
					bean.setReferenceNo(getChildTagValue( headerEl, "REFERENCENO" )); 	
					bean.setUserId(USERID);
					bean.setSos("000");
					bean.setSerialNo(SERIALNO);
					messageList.add(bean);
				}  
				xml.delete();
				return this.savePecas(messageList, idSegmento);
				//this.savePecasLog(idDocSegOperPk);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//	public Long salvarCsv(byte[] bytesArquivo, Long idModelo, Long idPrefixo) {
//		EntityManager manager = null;
//		try {
//			manager = JpaUtil.getInstance();
//			File csv = File.createTempFile(""+new Date().getTime(), ".csv", new File("."));
//			FileOutputStream outPut = new FileOutputStream(csv);  
//			outPut.write(bytesArquivo);  
//			outPut.flush();
//			outPut.close();
//			PrecoBusiness business = new PrecoBusiness(null);
//			BufferedReader reader = new BufferedReader(new FileReader(csv));
//			String thisLine = "";
//			int count = 1; 
//
//			String horas = "";
//			while ((thisLine = reader.readLine()) != null) { // while loop begins here
//				String [] aux = thisLine.split("\",\"");
//				PrecoBean bean = new PrecoBean();
//				bean.setIdModelo(idModelo);
//				bean.setIdPrefixo(idPrefixo);
//				if(count == 2){
//					bean.setCompCode(aux[4].replace("\"", "")); 
//					Query query = manager.createNativeQuery("select cptcdd from cptcd where cptcd =:cptcd");
//					query.setParameter("cptcd", bean.getCompCode());
//					List<String> list = query.getResultList();
//					if(list.size() > 0){
//						bean.setDescricaoCompCode(list.get(0));
//					}					
//					bean.setJobCode(aux[5].replace("\"", ""));
//					query = manager.createNativeQuery("select jbcdds from jbcd where jbcd =:jbcd");
//					query.setParameter("jbcd", bean.getJobCode());
//					list = query.getResultList();
//					if(list.size() > 0){
//						bean.setDescricaoJobCode(list.get(0));
//					}
//					bean.setIdComplexidade(Long.valueOf(aux[7].replace("\"", "")));
//					horas = aux[12].replace("\"", "");
//					String [] horasAux = horas.split(",");
//					Integer minutos = Integer.valueOf(horasAux[1]);
//					horas = horasAux[0]+"."+((minutos < 10)?"0"+minutos:minutos);
//					bean.setQtdHoras(horas);
//					business.saveOrUpdate(bean);
//				}else if(count > 2){
//					bean.setCompCode(aux[27].replace("\"", "")); 
//					Query query = manager.createNativeQuery("select cptcdd from cptcd where cptcd =:cptcd");
//					query.setParameter("cptcd", bean.getCompCode());
//					List<String> list = query.getResultList();
//					if(list.size() > 0){
//						bean.setDescricaoCompCode(list.get(0));
//					}
//					bean.setJobCode(aux[28].replace("\"", ""));
//					query = manager.createNativeQuery("select jbcdds from jbcd where jbcd =:jbcd");
//					query.setParameter("jbcd", bean.getJobCode());
//					list = query.getResultList();
//					if(list.size() > 0){
//						bean.setDescricaoJobCode(list.get(0));
//					}
//					bean.setIdComplexidade(Long.valueOf(aux[30].replace("\"", "")));
//					horas = aux[35].replace("\"", "");
//					String [] horasAux = horas.split(",");
//					Integer minutos = Integer.valueOf(horasAux[1]);
//					bean.setQtdHoras(horasAux[0]+"."+((minutos < 10)?"0"+minutos:minutos));
//					business.saveOrUpdate(bean);
//				}
//				count++;
//			} // end
//			csv.delete();
//
//			return 1L;	
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		return null;
//	}
	public Boolean removerPecas(Long idSegmento){ 
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsSegmento emsSegmento = manager.find(EmsSegmento.class, idSegmento);
			emsSegmento.setCodErroDocDbs(null);
			emsSegmento.setMsgDocDbs(null);
			manager.merge(emsSegmento);
			Query query = manager.createNativeQuery("delete from ems_pecas where id_ems_segmento = "+idSegmento);
			query.executeUpdate();
			manager.getTransaction().commit();
			return true;
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
		return null;
	}

	public Long savePecas(List<PecasBean> list, Long idSegmento){//salvar peças
		//this.removerPecas(idDocSegOper);//deletar where idOper
		EntityManager manager = null;
		EmsSegmento emsSegmento = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			emsSegmento = manager.find(EmsSegmento.class, idSegmento);

			for (PecasBean pecasBean : list) {
				EmsPecas pecas = new EmsPecas();
				pecas.setIdEmsSegmento(emsSegmento);
				pecasBean.toBean(pecas);
				manager.persist(pecas);			
			}
			manager.getTransaction().commit();	
			return emsSegmento.getId();
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
		return null;
	}
	
	/**
	 * Salva o log das peças importadas para o sistema
	 * @param list
	 * @param idSegmento
	 * @param idOper
	 * @param idDocSegOper
	 */
//	private void savePecasLog(Long idDocSegOperPk){//salvar peças
//		//this.removerPecas(idDocSegOper);//deletar where idOper
//		EntityManager manager = null;
//		try {
//			manager = JpaUtil.getInstance();
//			manager.getTransaction().begin();
//			Query query = manager.createQuery("from GePecas where idDocSegOper.id =:idDocSegOper");
//			query.setParameter("idDocSegOper", idDocSegOperPk);
//			List<GePecas> list = query.getResultList();
//			
//			for (GePecas pecasBean : list) {
//				GePecasLog pecas = new GePecasLog();
//				
//				pecas.setPartNo(pecasBean.getPartNo());
//				pecas.setPartName(pecasBean.getPartName());
//				//pecas.setId(getId());
//				pecas.setQtd(Long.valueOf(pecasBean.getQtd()));
//				pecas.setGroupNumber(pecasBean.getGroupNumber());
//				pecas.setReferenceNo(pecasBean.getReferenceNo());
//				pecas.setSmcsCode(pecasBean.getSmcsCode());
//				pecas.setGroupName(pecasBean.getGroupName());
//				pecas.setUserId(pecasBean.getUserId());
//				pecas.setSos1(pecasBean.getSos1());
//				pecas.setIdDocSegOper(idDocSegOperPk);
//				pecas.setIdFuncionario(this.usuarioBean.getMatricula());
//				pecas.setDate(new Date());
//				pecas.setNumSegmento(pecasBean.getIdDocSegOper().getIdSegmento().getNumeroSegmento());
//				pecas.setNumeroOs(pecasBean.getIdDocSegOper().getIdSegmento().getIdCheckin().getNumeroOs());
//				pecas.setIdPecaOriginal(pecasBean.getId());
//				manager.persist(pecas);			
//			}
//			manager.getTransaction().commit();	
//			
//		} catch (Exception e) {
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.getTransaction().rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//	}
//	public Boolean savePecasAvulsoDocSeg (PecasBean bean, Long idSegmento, Long idOper){
//		GeDocSegOper geDocSegOper = new GeDocSegOper();
//		EntityManager manager = null;
//		try {
//			manager = JpaUtil.getInstance();
//			manager.getTransaction().begin();
//			GeSegmento segmento = manager.find(GeSegmento.class, idSegmento);
//			GeOperacao operacao = null;
//			if(idOper != null){
//				operacao = manager.find(GeOperacao.class, idOper);
//			}
//			geDocSegOper.setDataCriacao(new Date());
//			geDocSegOper.setIdOperacao(operacao);
//			geDocSegOper.setIdSegmento(segmento);
//			manager.persist(geDocSegOper);	
//			GePecas pecas = new GePecas();
//			pecas.setIdDocSegOper(geDocSegOper);
//			bean.toBean(pecas);
//			manager.persist(pecas);
//			//log do sistema para saber se as que foi o usuário que imporou as pecas
//			GePecasLog pecasLog = new GePecasLog();
//			pecasLog.setIdDocSegOper(geDocSegOper.getId());
//			bean.toBean(pecasLog);
//			pecasLog.setIdFuncionario(this.usuarioBean.getMatricula());
//			pecasLog.setDate(new Date());
//			pecasLog.setNumeroOs(segmento.getIdCheckin().getNumeroOs());
//			pecasLog.setNumSegmento(segmento.getNumeroSegmento());
//			pecasLog.setIdPecaOriginal(pecas.getId());
//			manager.merge(pecasLog);
//			manager.getTransaction().commit();
//			return true;
//		} catch (Exception e) {
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.getTransaction().rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		return false;
//	}
	public String savePecasAvulso (PecasBean bean, Long idSegmento)throws Exception {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsSegmento emsSegmento = manager.find(EmsSegmento.class, idSegmento);
			if(emsSegmento.getNumDoc() != null){
				return "Não é possivel salvar essa peça pois existe número de documento-false";
			}
			EmsPecas pecas = new EmsPecas();			
			//pecas.setIdDocSegOper(idDocSegOper);
			pecas.setPartNo(bean.getPartNo());
			pecas.setPartName(bean.getPartName());
			//pecas.setId(Long.valueOf(bean.getId()));
			pecas.setQtd(Long.valueOf(bean.getQtd()));
			pecas.setGroupNumber(bean.getGroupNumber());
			pecas.setReferenceNo(bean.getReferenceNo());
			pecas.setSmcsCode(bean.getSmcsCode());
			pecas.setGroupName(bean.getGroupName());
			pecas.setUserId(bean.getUserId());
			pecas.setSos1(bean.getSos());
			pecas.setIdEmsSegmento(emsSegmento);
			manager.persist(pecas);
			
			//log do sistema para saber se as que foi o usuário que imporou as pecas
//			GePecasLog pecasLog = new GePecasLog();
//			pecasLog.setIdDocSegOper(pecas.getIdDocSegOper().getId());
//			bean.toBean(pecasLog);
//			pecasLog.setIdFuncionario(this.usuarioBean.getMatricula());
//			pecasLog.setDate(new Date());
//			pecasLog.setNumeroOs(pecas.getIdDocSegOper().getIdSegmento().getIdCheckin().getNumeroOs());
//			pecasLog.setNumSegmento(pecas.getIdDocSegOper().getIdSegmento().getNumeroSegmento());
//			pecasLog.setIdPecaOriginal(pecas.getId());
//			manager.merge(pecasLog);
			
			
			manager.getTransaction().commit();
			return "Peça salva com sucesso";
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
		return "Ocorreu um erro ao salvar a peça-false";
	}
	public List<PecasBean> findAllPecas(Long idSegmento){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From EmsPecas where idEmsSegmento.id = "+idSegmento);
			//query.setParameter("idSegmento", idSegmento);
			List<EmsPecas> result = query.getResultList();
			for(EmsPecas pecasObj : result){
				PecasBean bean = new PecasBean();
				bean.fromBean(pecasObj);
				pecas.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	
//	public List<PecasBean> findAllPecas(Long idSegmento){
//		List<PecasBean> pecas = new ArrayList<PecasBean>();
//		EntityManager manager = null;
//		try {
//			manager = JpaUtil.getInstance();
//			String complemento = "";
//			String SQL = "from GePecas p where p.idDocSegOper.id =(#)";
//		
//			complemento += "	select max(dso.id) from GeDocSegOper dso where dso.idSegmento.id ="+idSegmento;
//			
//			Query query = manager.createQuery(SQL.replace("#", complemento));
//			//query.setParameter("idSegmento", idSegmento);
//			List<EmsPecas> result = query.getResultList();
//			for(EmsPecas pecasObj : result){
//				PecasBean bean = new PecasBean();
//				bean.fromBean(pecasObj);
//				pecas.add(bean);
//			}
//
//		} catch (Exception e) {
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.getTransaction().rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		return pecas;
//	}	
	// este método lê e retorna o conteúdo (texto) de uma tag (elemento)  
	// filho da tag informada como parâmetro. A tag filho a ser pesquisada  
	// é a tag informada pelo nome (string)  
	private static String getChildTagValue( Element elem, String tagName ) throws Exception {  
		NodeList children = elem.getElementsByTagName( tagName );  
		if( children == null ) return null;  
		Element child = (Element) children.item(0);  
		if( child == null || child.getFirstChild() == null) return null;  
		return child.getFirstChild().getNodeValue();  
	}
	//Este método remove uma única peça do Grid de Peças 
	public String removerPeca(PecasBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			EmsSegmento segmento = manager.find(EmsSegmento.class, bean.getIdEmsSegmento());
			segmento.setCodErroDocDbs(null);
			segmento.setNumDoc(null);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			EmsPecas pecas = manager.find(EmsPecas.class, Long.valueOf(bean.getId()));
			Query query = manager.createNativeQuery("select * from ems_pecas where id_ems_segmento =:id_ems_segmento");
			query.setParameter("id_ems_segmento", pecas.getIdEmsSegmento());
			if(query.getResultList().size() == 1){
				pecas.getIdEmsSegmento().setCodErroDocDbs(null);
				pecas.getIdEmsSegmento().setMsgDocDbs(null);
				manager.merge(pecas);
			}
			manager.remove(pecas);
			
			manager.getTransaction().commit();

			return "Peça excluída com sucesso!";
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return "Ocorreu um erro ao exluir a peça-false";
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
//	public Boolean saveResponsavelPecas(SegmentoBean segmentoBean) {		
//		EntityManager manager = null;
//		
//		try {
//			manager = JpaUtil.getInstance();
//			manager.getTransaction().begin();
//			
//			GeSegmento segmento = manager.find(GeSegmento.class, segmentoBean.getId());
//			
//			if(segmento != null){
//				segmento.setIdFuncionarioPecas(segmentoBean.getIdFuncionarioPecas());
//				manager.merge(segmento);				
//				manager.getTransaction().commit();
//				return true;
//			}
//			
//		} catch (Exception e) {
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.getTransaction().rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		
//		return false;
//	}
//	public Boolean removerFuncionarioPecas(SegmentoBean segmentoBean) {
//		EntityManager manager = null;
//		
//		try {
//			manager = JpaUtil.getInstance();
//			manager.getTransaction().begin();
//			
//			Query query = manager.createNativeQuery("UPDATE GE_SEGMENTO SET ID_FUNCIONARIO_PECAS = null"+ 
//						" WHERE ID = "+segmentoBean.getId());
//
//			query.executeUpdate();
//			
//			manager.getTransaction().commit();
//			return true;
//
//		} catch (Exception e) {
//			if(manager != null && manager.getTransaction().isActive()){
//				manager.getTransaction().rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}
//		}
//		
//		return false;
//	}
	public SegmentoBean findSegmentoBy(Long idSegmento) {
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();

			EmsSegmento segmento = manager.find(EmsSegmento.class, idSegmento);

			SegmentoBean bean = new SegmentoBean();
			bean.fromBean(segmento);
			
			if(bean.getIdFuncionarioPecas() != null){
				Query query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+bean.getIdFuncionarioPecas()+"'");
				
				String nomeFuncionarioPecas = (String) query.getSingleResult();
				bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
			}			

			return bean;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return null;
	}
	public List<UsuarioBean> findAllFuncionariosByCampoPesquisa(String campoPesquisa) {
		EntityManager manager = null;
		List<UsuarioBean> beans = new ArrayList<UsuarioBean>();

		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createQuery("FROM TwFuncionario WHERE (EPIDNO LIKE '%"+campoPesquisa+"%' or EPLSNM LIKE '%"+campoPesquisa+"%')" +
					" AND STN1 = "+ Long.valueOf(usuarioBean.getFilial()));
			
			List<TwFuncionario> list = (List<TwFuncionario>)query.getResultList();
			
			for(TwFuncionario bean : list){
				UsuarioBean objUsuario = new UsuarioBean();				
				objUsuario.setNome(bean.getEplsnm());
				objUsuario.setMatricula(bean.getEpidno());
				objUsuario.setFilial(bean.getStn1());
								
				beans.add(objUsuario);				
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return beans;

	}
	public String saveOrcamento(Long idProposta){
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		try {
			manager = JpaUtil.getInstance();
			con = ConectionDbs.getConnecton(); 
			try {
				prstmt = con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Query query = manager.createQuery ("from EmsSegmento where idProposta.id =:idProposta and (codErroDocDbs is null or codErroDocDbs = '99')");
			query.setParameter("idProposta", idProposta);

			List<EmsSegmento> result = query.getResultList();
			if(result.isEmpty()){
				return "Não existem peças sem cotação para serem enviadas para o DBS";
			}
			for (EmsSegmento emsSegmento : result){
				
					
					query = manager.createQuery("from EmsPecas where idEmsSegmento.id =:id");
					query.setParameter("id", Long.valueOf(emsSegmento.getId()));
					List<EmsPecas> list = query.getResultList();
					if(list.size() == 0){
						continue;
					}
					prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'M-"+emsSegmento.getId()+"'");
					prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPHSM0 where PEDSM = 'M-"+emsSegmento.getId()+"'");
					for (EmsPecas gePecas : list) {
						String numPeca = gePecas.getPartNo();
						if(gePecas.getSos1().equals("000") || gePecas.getSos1().equals("995")){
							numPeca = gePecas.getPartNo().replace("-", "");
						}
						String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 (PEDSM, SOS, PANO20, QTDE) values('M-"+emsSegmento.getId()+"', '"+((gePecas.getSos1() == null)?"000":gePecas.getSos1())+"', '"+numPeca+"', '"+gePecas.getQtd()+"')";
						prstmt.executeUpdate(SQL);
					}
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPHSM0 (PEDSM, STNSM, CUNOSM) values('M-"+emsSegmento.getId()+"', '"+((Integer.valueOf(this.usuarioBean.getFilial())< 10)?"0"+Integer.valueOf(this.usuarioBean.getFilial()):Integer.valueOf(this.usuarioBean.getFilial()))+"', '"+emsSegmento.getIdProposta().getCodClienteExt()+"')";
					prstmt.executeUpdate(SQL);
					manager.getTransaction().begin();
					emsSegmento.setMsgDocDbs("Peças enviadas para o DBS, aguarde 2 minutos para o retorno do documento!");
					emsSegmento.setCodErroDocDbs("100");
					manager.getTransaction().commit();					
				
			}	
			return "Peças enviadas para o DBS com sucesso!";
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}

			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					prstmt.close();
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			return "";
	}
	
	public String saveOrcamentoSegmento(Long idSegmento){
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		try {
			manager = JpaUtil.getInstance();
			con = ConectionDbs.getConnecton(); 
			try {
				prstmt = con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Query query = manager.createQuery ("from EmsSegmento where id =:id and (codErroDocDbs is null or codErroDocDbs = '99')");
			query.setParameter("id", idSegmento);
			
			List<EmsSegmento> result = query.getResultList();
			if(result.isEmpty()){
				return "Não existem peças sem cotação para serem enviadas para o DBS";
			}
			for (EmsSegmento emsSegmento : result){
				
				
				query = manager.createQuery("from EmsPecas where idEmsSegmento.id =:id");
				query.setParameter("id", Long.valueOf(emsSegmento.getId()));
				List<EmsPecas> list = query.getResultList();
				prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'M-"+emsSegmento.getId()+"'");
				prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPHSM0 where PEDSM = 'M-"+emsSegmento.getId()+"'");
				for (EmsPecas gePecas : list) {
					String numPeca = gePecas.getPartNo();
					if(gePecas.getSos1().equals("000") || gePecas.getSos1().equals("995")){
						numPeca = gePecas.getPartNo().replace("-", "");
					}
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 (PEDSM, SOS, PANO20, QTDE) values('M-"+emsSegmento.getId()+"', '"+((gePecas.getSos1() == null)?"000":gePecas.getSos1())+"', '"+numPeca+"', '"+gePecas.getQtd()+"')";
					prstmt.executeUpdate(SQL);
				}
				String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPHSM0 (PEDSM, STNSM, CUNOSM) values('M-"+emsSegmento.getId()+"', '"+((Integer.valueOf(this.usuarioBean.getFilial())< 10)?"0"+Integer.valueOf(this.usuarioBean.getFilial()):Integer.valueOf(this.usuarioBean.getFilial()))+"', '"+emsSegmento.getIdProposta().getCodClienteExt()+"')";
				prstmt.executeUpdate(SQL);
				manager.getTransaction().begin();
				emsSegmento.setMsgDocDbs("Peças enviadas para o DBS, aguarde 2 minutos para o retorno do documento!");
				emsSegmento.setCodErroDocDbs("100");
				manager.getTransaction().commit();					
				
			}	
			return "Peças enviadas para o DBS com sucesso!";
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					prstmt.close();
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public Boolean sendContratoDbs(EmsSegmento emsSegmento, String possuiSubTributaria, String numDoc) throws Exception{
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		EntityManager manager = null;
		Connection con = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();

				con = ConectionDbs.getConnecton();

				String SQL = " SELECT PCPCOPD0.ORQY, PCPCOPD0.BOIMCT, PCPCOPD0.SOS1, PCPCOPD0.DSFCCD, PCPCOPD0.PANO20, PCPCOPD0.DS18, PCPCOPD0.UNSEL, unsel*orqy UNSEL_ORQY, PCPCOPD0.SKNSKI, PCPCOPD0.BECTYC"+
				" FROM "+IConstantAccess.LIB_DBS+".PCPCOPD0 PCPCOPD0"+
				" WHERE PCPCOPD0.RFDCNO='"+emsSegmento.getNumDoc()+"'";
				prstmt_ = con.prepareStatement(SQL);
				rs = prstmt_.executeQuery();
				int count = 0;
				while(rs.next()){
					if(count == 0){
						Query query = manager.createNativeQuery("delete from EMS_PECAS where id_ems_segmento =:id_ems_segmento");
						query.setParameter("id_ems_segmento", emsSegmento.getId());
						query.executeUpdate();
					}
					count++;
					EmsPecas pecas = new EmsPecas();
					pecas.setQtd(Long.valueOf(rs.getString("ORQY").trim()));
					pecas.setSos1(rs.getString("SOS1").trim());
					pecas.setPartNo(rs.getString("PANO20").trim());
					pecas.setPartName(rs.getString("DS18").trim().replaceAll("'", ""));
					pecas.setUnsel(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
					if("EXT".equals(emsSegmento.getIdProposta().getTipoCliente())){
						pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNSEL")));
						pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
					}else if("INT".equals(emsSegmento.getIdProposta().getTipoCliente())){
						pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNCS")));
						pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNCS_ORQY")));
					}
					pecas.setQtdNaoAtendido(Long.valueOf(rs.getString("BOIMCT").trim()));
					pecas.setIdEmsSegmento(emsSegmento);
					
					pecas.setIpi(BigDecimal.ZERO);
					pecas.setIcmsub(BigDecimal.ZERO);
					pecas.setTotaltributos(BigDecimal.ZERO);
					manager.persist(pecas);
				}
				manager.getTransaction().commit();
				if("S".equals(possuiSubTributaria) && numDoc != null){
					SQL = "select parts.TOTSEL PUNI, parts.ORQY QTD, parts.IPI IPI, parts.ICMSUB ICMSUB, parts.VLRTOT TOTALTRIBUTOS," +
					" trim(parts.PANO20) PANO20, trim(parts.SOS1) SOS1, trim(parts.DESCRICAO) DESCRICAO from "+IConstantAccess.PESARDRTRIBUTACAO+".RDRPARTS parts where parts.RFDCNO = '"+numDoc+"'";
					prstmt_ = con.prepareStatement(SQL);
					rs = prstmt_.executeQuery();
					while(rs.next()){
						manager.getTransaction().begin();
						Query query = manager.createNativeQuery("update ems_Pecas  set ipi ="+BigDecimal.valueOf(rs.getDouble("IPI"))+", icmsub ="+BigDecimal.valueOf(rs.getDouble("ICMSUB"))+", totaltributos ="+BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS"))+
								" where PART_NO = '"+rs.getString("PANO20")+"' and PART_NAME = '"+rs.getString("DESCRICAO").replace("'", "")+"'" +
										" and qtd = "+Long.valueOf(rs.getString("QTD").trim())+" " +
												" and sos1 ='"+rs.getString("SOS1")+"'"+
												" and ID_EMS_SEGMENTO ="+emsSegmento.getId()+
												" and UNSEL ="+BigDecimal.valueOf(rs.getDouble("PUNI")));
//						query.setParameter("ipi", BigDecimal.valueOf(rs.getDouble("IPI")));
//						query.setParameter("icmsub", BigDecimal.valueOf(rs.getDouble("ICMSUB")));
//						query.setParameter("totalTributos", BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS")));
						//query.setParameter("partNo", rs.getString("PANO20"));
						//query.setParameter("sos1", rs.getString("SOS1"));
						//query.setParameter("partName", rs.getString("DESCRICAO"));
						//query.setParameter("qtd", Long.valueOf(rs.getString("QTD").trim()));
						query.executeUpdate();
						manager.getTransaction().commit();
					}
				}
			return true;
		
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				prstmt_.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new Exception();
		
	}

}
