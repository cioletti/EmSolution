package com.emsolution.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.OportunidadePmpBean;
import com.emsolution.util.JpaUtil;

public class PmpBusiness {
	public List<OportunidadePmpBean> findAllOportunidadePmp(Long idOsPalmDt) {
		List<OportunidadePmpBean> listForm = new ArrayList<OportunidadePmpBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select dt.IDOS_PALM_DT, arv.DESCRICAO, convert(varchar(4000),dt.OBS) obsFolha, dt.TIPO_MANUTENCAO, CONVERT(varchar(4000), palm.OBS) obsCabecalho from OS_PALM_DT dt, ARV_INSPECAO arv, OS_PALM palm"+
					" where dt.STATUS = 'NC' "+
					" and dt.OS_PALM_IDOS_PALM =:idOsPalmDt"+
			" and dt.ID_IDARV = arv.ID_ARV" +
			" and dt.ID_EMS_SEGMENTO IS NULL " +
			" and dt.IS_REJEITADO_EMS is null " +
			" and palm.IDOS_PALM = dt.OS_PALM_IDOS_PALM ");
			query.setParameter("idOsPalmDt", idOsPalmDt);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				OportunidadePmpBean bean = new OportunidadePmpBean();
				bean.setIdOsPalmDt(((BigDecimal)pair[0]).longValue());
				bean.setDescricao((String)pair[1]);
				bean.setObs((String)pair[2]);
				bean.setTipoManutencao((String)pair[3]);
				bean.setObsCabecalho((String)pair[4]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}
	public List<OportunidadePmpBean> findAllOportunidadeCampo(Long idOsPalmDt) {
		List<OportunidadePmpBean> listForm = new ArrayList<OportunidadePmpBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select dt.IDOS_PALM_DT, arv.DESCRICAO, convert(varchar(4000),dt.OBS) obsFolha, dt.TIPO_MANUTENCAO, CONVERT(varchar(4000), palm.OBS) obsCabecalho from SC_OS_PALM_DT dt, SC_ARV_INSPECAO arv, SC_OS_PALM palm"+
					" where dt.STATUS = 'NC' "+
					" and dt.OS_PALM_IDOS_PALM =:idOsPalmDt"+
					" and dt.ID_IDARV = arv.ID_ARV" +
					" and dt.ID_EMS_SEGMENTO IS NULL " +
					" and dt.IS_REJEITADO_EMS is null " +
			" and palm.IDOS_PALM = dt.OS_PALM_IDOS_PALM ");
			query.setParameter("idOsPalmDt", idOsPalmDt);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				OportunidadePmpBean bean = new OportunidadePmpBean();
				bean.setIdOsPalmDt(((BigDecimal)pair[0]).longValue());
				bean.setDescricao((String)pair[1]);
				bean.setObs((String)pair[2]);
				bean.setTipoManutencao((String)pair[3]);
				bean.setObsCabecalho((String)pair[4]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}
	public List<OportunidadePmpBean> findAllPmpAssociado(Long idSegmento) {
		List<OportunidadePmpBean> listForm = new ArrayList<OportunidadePmpBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select dt.IDOS_PALM_DT, arv.DESCRICAO, convert(varchar(4000),dt.OBS), dt.TIPO_MANUTENCAO from OS_PALM_DT dt, ARV_INSPECAO arv"+
					" where dt.ID_EMS_SEGMENTO =:idSegmento"+
			" and dt.ID_IDARV = arv.ID_ARV");
			query.setParameter("idSegmento", idSegmento);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				OportunidadePmpBean bean = new OportunidadePmpBean();
				bean.setIdOsPalmDt(((BigDecimal)pair[0]).longValue());
				bean.setDescricao((String)pair[1]);
				bean.setObs((String)pair[2]);
				bean.setTipoManutencao((String)pair[3]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}
	public List<OportunidadePmpBean> findAllCampoAssociado(Long idSegmento) {
		List<OportunidadePmpBean> listForm = new ArrayList<OportunidadePmpBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select dt.IDOS_PALM_DT, arv.DESCRICAO, convert(varchar(4000),dt.OBS), dt.TIPO_MANUTENCAO from SC_OS_PALM_DT dt, SC_ARV_INSPECAO arv"+
					" where dt.ID_EMS_SEGMENTO =:idSegmento"+
			" and dt.ID_IDARV = arv.ID_ARV");
			query.setParameter("idSegmento", idSegmento);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				OportunidadePmpBean bean = new OportunidadePmpBean();
				bean.setIdOsPalmDt(((BigDecimal)pair[0]).longValue());
				bean.setDescricao((String)pair[1]);
				bean.setObs((String)pair[2]);
				bean.setTipoManutencao((String)pair[3]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}
}
