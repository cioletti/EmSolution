package com.emsolution.business;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.ConfigAlarmeBean;
import com.emsolution.bean.DocumentoPecasBean;
import com.emsolution.bean.OperacaoBean;
import com.emsolution.bean.OportunidadePmpBean;
import com.emsolution.bean.SegmentoBean;
import com.emsolution.bean.SmuBean;
import com.emsolution.bean.SosBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsProposta;
import com.emsolution.entity.EmsSegmento;
import com.emsolution.entity.EmsSmu;
import com.emsolution.entity.EmsSos;
import com.emsolution.entity.GeCheckIn;
import com.emsolution.entity.GeOperacao;
import com.emsolution.entity.GeSegmento;
import com.emsolution.entity.OsPalmDt;
import com.emsolution.entity.TwFuncionario;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.IConstantAccess;
import com.emsolution.util.JpaUtil;

public class SegmentoBusiness {
	
	private UsuarioBean usuarioBean;
	public SegmentoBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public Boolean removerCotacaoDBS(Long idSegmento, String numCotacao){
		Connection con = null;
		//ResultSet rs = null;
		Statement prstmtDelete = null;
		EntityManager manager = null;
		try {
			con = ConectionDbs.getConnecton(); 
			prstmtDelete = con.createStatement();
			//con.setAutoCommit(false);
			prstmtDelete.executeUpdate("insert into "+IConstantAccess.AMBIENTE_DBS+".USPDDSM0 (DOCDBS) values('"+numCotacao+"')");
			con.commit();
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsSegmento seg = manager.find(EmsSegmento.class, idSegmento);			
			seg.setCodErroDocDbs("100");
			seg.setMsgDocDbs("Aguarde resposta do DBS!");
			
			manager.merge(seg);
			manager.getTransaction().commit();
			
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if(con != null){
					if(prstmtDelete != null){
						prstmtDelete.close();
					}
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public List<SegmentoBean> findAllSegmento(Long idProposta){
		List<SegmentoBean> segmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From EmsSegmento where idProposta.id = :idProposta order by numeroSegmento");
			query.setParameter("idProposta", idProposta);
			List<EmsSegmento> result = query.getResultList();
			for(EmsSegmento segmentoObj : result){
				SegmentoBean bean = new SegmentoBean();
				bean.fromBean(segmentoObj);	
				segmento.add(bean);				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return segmento;
	}
	
	public String findHorasOficina (String prefixo, String jobCode, String compCode, String modelo, Long idFamilia){
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select pre.qtd_horas from ge_prefixo p, ge_arv_inspecao m, ge_preco pre " +
					" where p.id_modelo = m.id_arv " +
					" and p.descricao = '"+prefixo.toUpperCase()+"'" +
					"  and m.DESCRICAO = '"+modelo+"' " +
					" and pre.id_modelo = m.id_arv " +
					" and p.id = pre.id_prefixo" +
					" and pre.job_code = '"+jobCode+"'" +
					" and m.id_familia = '"+idFamilia+"'"+
					" and pre.comp_code = '"+compCode+"'" +
					" order by  pre.id desc");
			List<String> result = query.getResultList();
			for (String horas : result){
				return horas;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;

	}
	
	public String findHorasCampo (String prefixo, String jobCode, String compCode, String modelo, Long idFamilia){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select pre.qtd_horas from sc_prefixo p, sc_preco pre " +
					" where p.id_modelo_familia = (select id from SC_MODELO_FAMILIA where ID_FAMILIA  = "+idFamilia+" and MODELO = '"+modelo+"')"+
					" and p.descricao = '"+prefixo.toUpperCase()+"'" +
					" and p.id = pre.id_prefixo" +
					" and pre.job_code = '"+jobCode+"'" +
					" and pre.comp_code = '"+compCode+"'" +
					" order by  pre.id desc");
			List<String> result = query.getResultList();
			for (String horas : result){
				return horas;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null){
				manager.close();
			}	
		}
		return null;

	}
	public SegmentoBean saveOrUpdateSegmento (SegmentoBean bean, String numSerie, Long idProposta, Long horimetro){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsSegmento emsSegmento= null;
			EmsProposta  proposta = manager.find(EmsProposta.class, idProposta);
			proposta.setHorimetro(BigDecimal.valueOf(horimetro));
			if(bean.getId() == null || bean.getId() == 0){
				emsSegmento = new EmsSegmento();
				bean.toBean(emsSegmento);
				emsSegmento.setIdProposta(proposta);
				manager.persist(emsSegmento);
			}else{
				Query query = manager.createNativeQuery("update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
				query .setParameter("ID_EMS_SEGMENTO", bean.getId());
				query.executeUpdate();
				
				query = manager.createNativeQuery("update EMS_SOS set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
				query .setParameter("ID_EMS_SEGMENTO", bean.getId());
				query.executeUpdate();

				query = manager.createNativeQuery("update OS_PALM_DT set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
				query .setParameter("ID_EMS_SEGMENTO", bean.getId());
				query.executeUpdate();

				query = manager.createNativeQuery("update SC_OS_PALM_DT set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
				query .setParameter("ID_EMS_SEGMENTO", bean.getId());
				query.executeUpdate();
				
				query = manager.createNativeQuery("update EMS_SMU set ID_SEGMENTO = null where ID_SEGMENTO =:ID_SEGMENTO");
				query .setParameter("ID_SEGMENTO", bean.getId());
				query.executeUpdate();
				
				emsSegmento = manager.find(EmsSegmento.class, bean.getId());
				bean.toBean(emsSegmento);
				emsSegmento.setIdProposta(proposta);
				manager.merge(emsSegmento);
			}
			if(bean.getListPlAssociado() != null && bean.getListPlAssociado().size() > 0){
				String SQL = "";
				for (ConfigAlarmeBean alarmeBean : bean.getListPlAssociado()) {
					if(alarmeBean.getDescTipoAlarme().equals("MID - CID - FMI")){
						SQL = "select distinct td.ID_MESSAGE_ID, td.ID_RECEIVE_TIME from EMS_TYPE_DIAGNOSTIC td, EMS_DIAGNOSTIC d, EMS_PROPOSTA p"+
							  "	where d.MESSAGE_ID = td.ID_MESSAGE_ID"+
							  "	and d.RECEIVED_TIME = td.ID_RECEIVE_TIME"+
							  "	and td.MID = "+alarmeBean.getCodigo()+
							  "	and td.CID = "+alarmeBean.getCodigoCid()+
							  "	and td.FMI = "+alarmeBean.getCodigoFmi()+
							  "	and NIVEL = "+alarmeBean.getNivel()+
							  "	and d.SERIAL_NUMBER = '"+numSerie+"'"+
							  "	and p.NUM_SERIE = d.SERIAL_NUMBER"+
							  "	and p.ID_STATUS_OPT in (select so.ID from EMS_STATUS_OPORTUNIDADE so where so.sigla not in('REJ', 'FIN'))";
						Query query = manager.createNativeQuery(SQL);
						List<Object[]> result = (List<Object[]>)query.getResultList();
						for (Object[] pair : result) {
							SQL = "update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO where ID_MESSAGE_ID =:ID_MESSAGE_ID and ID_RECEIVE_TIME =:ID_RECEIVE_TIME and NIVEL =:NIVEL and MID =:MID and CID =:CID and FMI =:FMI ";
//							query = manager.createNativeQuery(SQL);
//							query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
//							query.setParameter("ID_MESSAGE_ID", (BigDecimal)pair[0]);
//							query.setParameter("ID_RECEIVE_TIME", (String)pair[1]);
//							query.executeUpdate();


//							SQL = "update EMS_TYPE_DIAGNOSTIC set IS_REJEITADO_EMS =:IS_REJEITADO_EMS, OBS_EMS_SEGMENTO =:OBS_EMS_SEGMENTO " +
//							" where ID_MESSAGE_ID =:ID_MESSAGE_ID and ID_RECEIVE_TIME =:ID_RECEIVE_TIME and NIVEL =:NIVEL and MID =:MID and CID =:CID and FMI =:FMI";
							query = manager.createNativeQuery(SQL);
							query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
							query.setParameter("ID_MESSAGE_ID", (BigDecimal)pair[0]);
							query.setParameter("ID_RECEIVE_TIME", (String)pair[1]);
							query.setParameter("NIVEL", alarmeBean.getNivel());
							query.setParameter("MID", alarmeBean.getCodigo());
							query.setParameter("CID", alarmeBean.getCodigoCid());
							query.setParameter("FMI",alarmeBean.getCodigoFmi());
							query.executeUpdate();
						}
					}else if(alarmeBean.getDescTipoAlarme().equals("MID - EID")){
						SQL = "select distinct td.ID_MESSAGE_ID, td.ID_RECEIVE_TIME from EMS_TYPE_DIAGNOSTIC td, EMS_DIAGNOSTIC d, EMS_PROPOSTA p"+
						"	where d.MESSAGE_ID = td.ID_MESSAGE_ID"+
						"	and d.RECEIVED_TIME = td.ID_RECEIVE_TIME"+
						"	and td.MID = "+alarmeBean.getCodigo()+
						"	and td.EID = "+alarmeBean.getCodigoEid()+
						"	and NIVEL = "+alarmeBean.getNivel()+
						"	and d.SERIAL_NUMBER = '"+numSerie+"'"+
						"	and p.NUM_SERIE = d.SERIAL_NUMBER"+
						"	and p.ID_STATUS_OPT in (select so.ID from EMS_STATUS_OPORTUNIDADE so where so.sigla not in('REJ', 'FIN'))";
						Query query = manager.createNativeQuery(SQL);
						List<Object[]> result = (List<Object[]>)query.getResultList();
						for (Object[] pair : result) {
							SQL = "update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO " +
									" where ID_MESSAGE_ID =:ID_MESSAGE_ID and ID_RECEIVE_TIME =:ID_RECEIVE_TIME and NIVEL =:NIVEL and MID =:MID and EID =:EID ";
//							query = manager.createNativeQuery(SQL);
//							query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
//							query.setParameter("ID_MESSAGE_ID", (BigDecimal)pair[0]);
//							query.setParameter("ID_RECEIVE_TIME", (String)pair[1]);
//							query.executeUpdate();

//							SQL = "update EMS_TYPE_DIAGNOSTIC set IS_REJEITADO_EMS =:IS_REJEITADO_EMS, OBS_EMS_SEGMENTO =:OBS_EMS_SEGMENTO " +
//							" where ID_MESSAGE_ID =:ID_MESSAGE_ID and ID_RECEIVE_TIME =:ID_RECEIVE_TIME and NIVEL =:NIVEL and MID =:MID and EID =:EID";
							query = manager.createNativeQuery(SQL);
							query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
							query.setParameter("ID_MESSAGE_ID", (BigDecimal)pair[0]);
							query.setParameter("ID_RECEIVE_TIME", (String)pair[1]);
							query.setParameter("NIVEL", alarmeBean.getNivel());
							query.setParameter("MID", alarmeBean.getCodigo());
							query.setParameter("EID", alarmeBean.getCodigoEid());
							query.executeUpdate();


						}
					}
				}
			}
			if(bean.getListSosAssociado() != null && bean.getListSosAssociado().size() > 0){
				for (SosBean sosBean : bean.getListSosAssociado()) {
					String SQL = "UPDATE EMS_SOS set ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO where TEXT_ID =:TEXT_ID";
					Query query = manager.createNativeQuery(SQL);
					query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
					query.setParameter("TEXT_ID", sosBean.getTextId());
					query.executeUpdate();
				}
			}
			if(bean.getListPmpAssociado() != null && bean.getListPmpAssociado().size() > 0){
				for (OportunidadePmpBean oportunidadePmpBean : bean.getListPmpAssociado()) {
					String SQL = "update OS_PALM_DT set ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO where IDOS_PALM_DT =:IDOS_PALM_DT";
					Query query = manager.createNativeQuery(SQL);
					query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
					query.setParameter("IDOS_PALM_DT", oportunidadePmpBean.getIdOsPalmDt());
					query.executeUpdate();
				}
			}
			if(bean.getListCampoAssociado() != null && bean.getListCampoAssociado().size() > 0){
				for (OportunidadePmpBean oportunidadePmpBean : bean.getListCampoAssociado()) {
					String SQL = "update SC_OS_PALM_DT set ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO where IDOS_PALM_DT =:IDOS_PALM_DT";
					Query query = manager.createNativeQuery(SQL);
					query.setParameter("ID_EMS_SEGMENTO", emsSegmento.getId());
					query.setParameter("IDOS_PALM_DT", oportunidadePmpBean.getIdOsPalmDt());
					query.executeUpdate();
				}
			}
			if(bean.getListSmuAssociado() != null && bean.getListSmuAssociado().size() > 0){
				for (SmuBean smuBean : bean.getListSmuAssociado()) {
					String SQL = "update EMS_SMU set ID_SEGMENTO =:ID_SEGMENTO where ID =:ID";
					Query query = manager.createNativeQuery(SQL);
					query.setParameter("ID_SEGMENTO", emsSegmento.getId());
					query.setParameter("ID", smuBean.getId());
					query.executeUpdate();
				}
			}
			
			manager.getTransaction().commit();
			
			DiagnosticoBusiness business = new DiagnosticoBusiness(this.usuarioBean);
			business.saveDiagnostic(-1L, emsSegmento.getIdProposta().getNumSerie());
			
			bean.fromBean(emsSegmento);
			return bean;

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
		return null;
	}
	public Boolean removerSegmento(Long idSegmento){
		EntityManager manager = null;
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsSegmento emsSegmento = manager.find(EmsSegmento.class, idSegmento);			
			Query query = manager.createNativeQuery("update EMS_TYPE_DIAGNOSTIC set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
			query .setParameter("ID_EMS_SEGMENTO", idSegmento);
			query.executeUpdate();
			
			query = manager.createNativeQuery("update EMS_SOS set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
			query .setParameter("ID_EMS_SEGMENTO", idSegmento);
			query.executeUpdate();

			query = manager.createNativeQuery("update OS_PALM_DT set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
			query .setParameter("ID_EMS_SEGMENTO", idSegmento);
			query.executeUpdate();

			query = manager.createNativeQuery("update SC_OS_PALM_DT set ID_EMS_SEGMENTO = null where ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO");
			query .setParameter("ID_EMS_SEGMENTO", idSegmento);
			query.executeUpdate();

			query = manager.createNativeQuery("update EMS_SMU set ID_SEGMENTO = null where ID_SEGMENTO =:ID_SEGMENTO");
			query .setParameter("ID_SEGMENTO", idSegmento);
			query.executeUpdate();
			
			manager.remove(emsSegmento);
			manager.getTransaction().commit();
			DiagnosticoBusiness business = new DiagnosticoBusiness(this.usuarioBean);
			business.saveDiagnostic(-1L, emsSegmento.getIdProposta().getNumSerie());
			
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public List<DocumentoPecasBean> verificaPecas (Long idProposta){
		List <DocumentoPecasBean> listDocSeg = new ArrayList<DocumentoPecasBean>();
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsSegmento where idProposta.id =:idProposta and codErroDocDbs is not null");
			query.setParameter("idProposta", idProposta);
			List<EmsSegmento> segmeList = query.getResultList();
				for(EmsSegmento emsSegmento : segmeList){    //((String)obj[2] == null)?"":(String)obj[2];  == null ?"" : oper.getIdOperacao().getId())
					DocumentoPecasBean bean = new DocumentoPecasBean();
					bean.setId(emsSegmento.getId());					
					bean.setDescSegmento(emsSegmento.getNumeroSegmento() + " - " + emsSegmento.getDescricaoJobCode()+"/"+ emsSegmento.getDescricaoCompCode());
					bean.setIdSegmento(emsSegmento.getId());
					bean.setMsgDbs(emsSegmento.getMsgDocDbs());
					bean.setNumDoc(emsSegmento.getNumDoc());
					bean.setCodErroDbs(emsSegmento.getCodErroDbs());
					if (bean.getMsgDbs() == null && bean.getNumDoc() != null){
						bean.setStatus("ok");
						bean.setStatusUrlImage("img/CE.png");						
					}else{
						bean.setStatus("pendente");
						bean.setStatusUrlImage("img/CN.png");
					}
					bean.setDataCriacao(String.valueOf(emsSegmento.getDataCriacao()));
					listDocSeg.add(bean);
			}		

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listDocSeg;
	}
	public SegmentoBean findSegmentoBy(Long idSegmento) {
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();
			EmsSegmento segmento = manager.find(EmsSegmento.class, idSegmento);

			SegmentoBean bean = new SegmentoBean();
			bean.fromBean(segmento);

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
	public Boolean inserirSegmento(List<SegmentoBean> segmentoList, Long idChechIn){
		//CHAMAR O REMOVER PEÇAS
		this.removerSegmento(idChechIn);
		EntityManager manager = null;
		//abrir transação
		try {

			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, idChechIn);
			for (SegmentoBean segmentoBean : segmentoList) {
				GeSegmento geSegmento = new GeSegmento();
				geSegmento.setIdCheckin(checkIn);
				segmentoBean.toBean(geSegmento, manager);
				
				manager.persist(geSegmento);
				List<OperacaoBean> operacaoList = segmentoBean.getOperacaoList();
				if(operacaoList != null){
					for (int i = 0; i < operacaoList.size(); i++){
						OperacaoBean operacaoBean = operacaoList.get(i);
						GeOperacao geOperacao = new GeOperacao();
						String[] cptcd = operacaoBean.getCptcd().split(" - ");
						geOperacao.setCptcd(cptcd[0]);
						geOperacao.setNumOperacao(operacaoBean.getNumero());
						geOperacao.setDescricaoCompCode(cptcd[1]);
						//geOperacao.setCptcd(operacaoBean.getCptcd());
						geOperacao.setJbcd(operacaoBean.getJbcd());
						geOperacao.setDescricaoJbcd(operacaoBean.getDesricao());
						geOperacao.setIdSegmento(geSegmento);
						geOperacao.setIsCreateOperacao("N");
						geOperacao.setCodErroDbs("100");
						geOperacao.setMsgDbs("Operação enviada, aguarde o retorno do DBS!");
						TwFuncionario funcionario = manager.find(TwFuncionario.class, operacaoBean.getIdFuncionarioCriador());
						geOperacao.setIdFuncionarioCriador(funcionario);
						manager.persist(geOperacao);
					}
				}
			}
			manager.getTransaction().commit();			
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean saveRejeicaoSegmento(Long idOsPalmDt, String obs, String numeroSerie){
		EntityManager manager = null;
		//abrir transação
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("update Os_palm_dt set OBS_EMS_SEGMENTO = '"+obs+"', IS_REJEITADO_EMS = 'S' where IDOS_PALM_DT = "+idOsPalmDt);
			query.executeUpdate();
			manager.getTransaction().commit();	
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, numeroSerie);
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean saveRejeicaoCampoSegmento(Long idOsPalmDt, String obs, String numeroSerie){
		EntityManager manager = null;
		//abrir transação
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("update sc_Os_palm_dt set OBS_EMS_SEGMENTO = '"+obs+"', IS_REJEITADO_EMS = 'S' where IDOS_PALM_DT = "+idOsPalmDt);
			query.executeUpdate();
			manager.getTransaction().commit();	
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, numeroSerie);
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public boolean rejeitarAlarmePl(ConfigAlarmeBean alarmeBean, String numSerie, String obs){
		
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			String SQL = null;
			if(alarmeBean.getDescTipoAlarme().equals("MID - CID - FMI")){
				SQL = "select distinct td.ID_MESSAGE_ID, td.ID_RECEIVE_TIME from EMS_TYPE_DIAGNOSTIC td, EMS_DIAGNOSTIC d, EMS_PROPOSTA p"+
				"	where d.MESSAGE_ID = td.ID_MESSAGE_ID"+
				"	and d.RECEIVED_TIME = td.ID_RECEIVE_TIME"+
				"	and td.MID = "+alarmeBean.getCodigo()+
				"	and td.CID = "+alarmeBean.getCodigoCid()+
				"	and td.FMI = "+alarmeBean.getCodigoFmi()+
				"	and NIVEL = "+alarmeBean.getNivel()+
				"	and d.SERIAL_NUMBER = '"+numSerie+"'"+
				"	and p.NUM_SERIE = d.SERIAL_NUMBER"+
				"	and p.ID_STATUS_OPT in (select so.ID from EMS_STATUS_OPORTUNIDADE so where so.sigla not in('REJ', 'FIN'))";
				Query query = manager.createNativeQuery(SQL);
				List<Object[]> result = (List<Object[]>)query.getResultList();
				for (Object[] pair : result) {
					SQL = "update EMS_TYPE_DIAGNOSTIC set IS_REJEITADO_EMS =:IS_REJEITADO_EMS, OBS_EMS_SEGMENTO =:OBS_EMS_SEGMENTO " +
							" where ID_MESSAGE_ID =:ID_MESSAGE_ID and ID_RECEIVE_TIME =:ID_RECEIVE_TIME and NIVEL =:NIVEL and MID =:MID and CID =:CID and FMI =:FMI";
					query = manager.createNativeQuery(SQL);
					query.setParameter("IS_REJEITADO_EMS", "S");
					query.setParameter("OBS_EMS_SEGMENTO", obs);
					query.setParameter("ID_MESSAGE_ID", (BigDecimal)pair[0]);
					query.setParameter("ID_RECEIVE_TIME", (String)pair[1]);
					query.setParameter("NIVEL", alarmeBean.getNivel());
					query.setParameter("MID", alarmeBean.getCodigo());
					query.setParameter("CID", alarmeBean.getCodigoCid());
					query.setParameter("FMI",alarmeBean.getCodigoFmi());
					query.executeUpdate();

				}
			}else if(alarmeBean.getDescTipoAlarme().equals("MID - EID")){
				SQL = "select distinct td.ID_MESSAGE_ID, td.ID_RECEIVE_TIME from EMS_TYPE_DIAGNOSTIC td, EMS_DIAGNOSTIC d, EMS_PROPOSTA p"+
				"	where d.MESSAGE_ID = td.ID_MESSAGE_ID"+
				"	and d.RECEIVED_TIME = td.ID_RECEIVE_TIME"+
				"	and td.MID = "+alarmeBean.getCodigo()+
				"	and td.EID = "+alarmeBean.getCodigoEid()+
				"	and NIVEL = "+alarmeBean.getNivel()+
				"	and d.SERIAL_NUMBER = '"+numSerie+"'"+
				"	and p.NUM_SERIE = d.SERIAL_NUMBER"+
				"	and p.ID_STATUS_OPT in (select so.ID from EMS_STATUS_OPORTUNIDADE so where so.sigla not in('REJ', 'FIN'))";
				Query query = manager.createNativeQuery(SQL);
				List<Object[]> result = (List<Object[]>)query.getResultList();
				for (Object[] pair : result) {
					SQL = "update EMS_TYPE_DIAGNOSTIC set IS_REJEITADO_EMS =:IS_REJEITADO_EMS, OBS_EMS_SEGMENTO =:OBS_EMS_SEGMENTO " +
							" where ID_MESSAGE_ID =:ID_MESSAGE_ID and ID_RECEIVE_TIME =:ID_RECEIVE_TIME and NIVEL =:NIVEL and MID =:MID and EID =:EID";
					query = manager.createNativeQuery(SQL);
					query.setParameter("IS_REJEITADO_EMS", "S");
					query.setParameter("OBS_EMS_SEGMENTO", obs);
					query.setParameter("ID_MESSAGE_ID", (BigDecimal)pair[0]);
					query.setParameter("ID_RECEIVE_TIME", (String)pair[1]);
					query.setParameter("NIVEL", alarmeBean.getNivel());
					query.setParameter("MID", alarmeBean.getCodigo());
					query.setParameter("EID", alarmeBean.getCodigoEid());
					query.executeUpdate();

				}
			}
			manager.getTransaction().commit();
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, numSerie);
			return true;
		}
		catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public Boolean rejeitarOportunidadeSos(String textId, String obs){
		EntityManager manager = null;
		//abrir transação
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("update EMS_SOS set OBS_REJEICAO =:ObsRejeicao, IS_REJEITADO = 'S' where TEXT_ID =:textId");
			query.setParameter("ObsRejeicao", obs);
			query.setParameter("textId", textId);
			query.executeUpdate();
			manager.getTransaction().commit();	
			EmsSos emsSos = manager.find(EmsSos.class, textId);
			
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, emsSos.getSerialNumber());
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public Boolean rejeitarOportunidadeSmu(Long id, String obs){
		EntityManager manager = null;
		//abrir transação
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("update EMS_SMU set OBSERVACAO =:ObsRejeicao, IS_REJEITADO = 'S' where ID =:ID");
			query.setParameter("ObsRejeicao", obs);
			query.setParameter("ID", id);
			query.executeUpdate();
			manager.getTransaction().commit();	
			EmsSmu emSmu = manager.find(EmsSmu.class, id);
			
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, emSmu.getNumSerie());
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null & manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
}
