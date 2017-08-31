package com.emsolution.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.CodigoClienteBean;
import com.emsolution.bean.ConfigAlarmeBean;
import com.emsolution.bean.LoginClienteBean;
import com.emsolution.bean.TipoAlarmeBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsCodigoAlarme;
import com.emsolution.entity.EmsCodigoCliente;
import com.emsolution.entity.EmsLoginCliente;
import com.emsolution.entity.EmsTipoAlarme;
import com.emsolution.util.JpaUtil;

public class CodigoBusiness {
	
	private UsuarioBean usuariobean;

	public CodigoBusiness(UsuarioBean bean) {
		this.usuariobean = bean;
	}
	/**
	 * Retorna uma Coleção de tipo de Alarme
	 * @return
	 */
	public List<TipoAlarmeBean> findAllTipoAlarme(){
		List<TipoAlarmeBean> listForm = new ArrayList<TipoAlarmeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsTipoAlarme" );
			List<EmsTipoAlarme> tipoAlarmes = (List<EmsTipoAlarme>) query.getResultList();
			for (EmsTipoAlarme alarme : tipoAlarmes) {
				TipoAlarmeBean alarmeBean = new TipoAlarmeBean();
				alarmeBean.setDescricao(alarme.getDescricao());
				alarmeBean.setId(alarme.getId());
				listForm.add(alarmeBean);
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
	public Boolean removerCodigoAlarme(ConfigAlarmeBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			manager.remove(manager.find(EmsCodigoAlarme.class, bean.getId()));
			manager.getTransaction().commit();
			return true;
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
		return false;
	}
	public ConfigAlarmeBean saveOrUpdateConfigAlarmeBean (ConfigAlarmeBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsTipoAlarme tipoAlarme = manager.find(EmsTipoAlarme.class, bean.getIdTipoAlarme());
			EmsCodigoAlarme alarme= null;
			manager.getTransaction();
			if(bean.getId() == null || bean.getId() == 0){
				alarme = new EmsCodigoAlarme();
				alarme.setIdTipoAlarme(tipoAlarme);
				if(tipoAlarme.getDescricao().equals("MID - EID")){
					alarme.setCodigoCid(null);
					alarme.setCodigoFmi(null);
					alarme.setCodigoEid(bean.getCodigoEid());
			    }else{
			    	alarme.setCodigoCid(bean.getCodigoCid());
					alarme.setCodigoFmi(bean.getCodigoFmi());
					alarme.setCodigoEid(null);
			    }
				bean.toBean(alarme);
				manager.merge(alarme);
			}else{
				alarme = manager.find(EmsCodigoAlarme.class, bean.getId());
				bean.toBean(alarme);
				alarme.setIdTipoAlarme(tipoAlarme);
				if(tipoAlarme.getDescricao().equals("MID - EID")){
					alarme.setCodigoCid(null);
					alarme.setCodigoFmi(null);
					alarme.setCodigoEid(bean.getCodigoEid());
			    }else{
			    	alarme.setCodigoCid(bean.getCodigoCid());
					alarme.setCodigoFmi(bean.getCodigoFmi());
					alarme.setCodigoEid(null);
			    }
				manager.merge(alarme);
			}
			manager.getTransaction().commit();
			bean.setId(alarme.getId());
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
	
	public List<ConfigAlarmeBean> findAllConfigAlarme(Long idTipoAlarme){
		List<ConfigAlarmeBean> listForm = new ArrayList<ConfigAlarmeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsTipoAlarme tp, EmsCodigoAlarme ca where ca.idTipoAlarme.id = tp.id and tp.id =:idTipoAlarme order by convert(int,ca.descricao)" );
			query.setParameter("idTipoAlarme", idTipoAlarme);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				EmsCodigoAlarme codigoAlarme = (EmsCodigoAlarme)pair[1];
				ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
				alarmeBean.fromBean(codigoAlarme);
				if(codigoAlarme.getIdTipoAlarme().getDescricao().equals("MID - CID - FMI")){
					query = manager.createNativeQuery("select DESCRICAO from EMS_FMI where codigo="+alarmeBean.getCodigoFmi()+"");
					String descFmi = (String)query.getSingleResult();
					alarmeBean.setDescFmi(descFmi);
				}else{
					alarmeBean.setCodigoCidStr(null);
					if(alarmeBean.getCodigoEid() != null){
						alarmeBean.setCodigoEidStr(alarmeBean.getCodigoEid().toString());
					}
				}
				listForm.add(alarmeBean);
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
	public List<ConfigAlarmeBean> findConfigAlarme(Long nivel, String serie){
		List<ConfigAlarmeBean> listForm = new ArrayList<ConfigAlarmeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select distinct td.MID, td.CID, td.FMI, td.EID, sum(td.OCURRANCES) OCURRANCES from EMS_DIAGNOSTIC d left join EMS_TYPE_DIAGNOSTIC td"+
					"	on d.MESSAGE_ID = td.ID_MESSAGE_ID"+
					"	and d.RECEIVED_TIME = td.ID_RECEIVE_TIME"+
					"	and d.SERIAL_NUMBER =:SERIE"+
			"	and td.NIVEL =:NIVEL" +
			"   and td.ID_EMS_SEGMENTO IS NULL" +
			"   and td.IS_REJEITADO_EMS IS NULL" +
			"	group by td.MID, td.CID, td.FMI, td.EID" );
			query.setParameter("SERIE", serie);
			query.setParameter("NIVEL", nivel);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			String SQL = "";
			List<EmsCodigoAlarme> codigoAlarmes = null;
			for (Object[] pair : list) {
				if(pair[0] != null && pair[1] != null && pair[2] != null){
					SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
					" 	select id from EmsTipoAlarme where descricao = 'MID - CID - FMI')" +
					" and ca.codigoCid =:codigoCid"+
					" and ca.codigoFmi =:codigoFmi";
					query = manager.createQuery(SQL);
					query.setParameter("codigoAlarme", ((BigDecimal)pair[0]).toString());
					query.setParameter("codigoCid", (BigDecimal)pair[1]);
					query.setParameter("codigoFmi", (BigDecimal)pair[2]);
					codigoAlarmes = query.getResultList();
					for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
						ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
						alarmeBean.fromBean(emsCodigoAlarme);
						alarmeBean.setNivel(nivel);
						alarmeBean.setCodigoCidStr(alarmeBean.getCodigoCid().toString());
						alarmeBean.setCodigoFmiStr(alarmeBean.getCodigoFmi().toString());
						alarmeBean.setCodigoEidStr(null);
						alarmeBean.setTotalOcorrencias((BigDecimal)pair[4]);
						listForm.add(alarmeBean);

					}
//					if(codigoAlarmes.size() == 0){
//						ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
//						alarmeBean.setNivel(nivel);
//						alarmeBean.setCodigo(((BigDecimal)pair[0]).toString());
//						alarmeBean.setCodigoCid((BigDecimal)pair[1]);
//						alarmeBean.setCodigoFmi((BigDecimal)pair[2]);
//						alarmeBean.setCodigoCidStr(((BigDecimal)pair[1]).toString());
//						alarmeBean.setCodigoFmiStr(((BigDecimal)pair[2]).toString());
//						alarmeBean.setCodigoEidStr(null);
//						alarmeBean.setTotalOcorrencias((BigDecimal)pair[4]);
//						alarmeBean.setDescTipoAlarme("MID - CID - FMI");
//						listForm.add(alarmeBean);
//					}
				}
				//				if(pair[1] != null){
				//				SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
				//				" 	select id from EmsTipoAlarme where descricao = 'CID')";
				//				query = manager.createQuery(SQL);
				//				query.setParameter("codigoAlarme", ((BigDecimal)pair[1]).toString());
				//				codigoAlarmes = query.getResultList();
				//				for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
				//					ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
				//					alarmeBean.fromBean(emsCodigoAlarme);
				//					listForm.add(alarmeBean);
				//					
				//				}
				//				}
				//				if(pair[2] != null){
				//				SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
				//				" 	select id from EmsTipoAlarme where descricao = 'FMI')";
				//				query = manager.createQuery(SQL);
				//				query.setParameter("codigoAlarme", ((BigDecimal)pair[2]).toString());
				//				codigoAlarmes = query.getResultList();
				//				for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
				//					ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
				//					alarmeBean.fromBean(emsCodigoAlarme);
				//					listForm.add(alarmeBean);
				//					
				//				}
				//				}
				if(pair[0] != null && pair[3] != null){
					SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
					" 	select id from EmsTipoAlarme where descricao = 'MID - EID')"+
					" and ca.codigoEid =:codigoEid";
					query = manager.createQuery(SQL);
					query.setParameter("codigoAlarme", ((BigDecimal)pair[0]).toString());
					query.setParameter("codigoEid", (BigDecimal)pair[3]);
					codigoAlarmes = query.getResultList();
					for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
						ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
						alarmeBean.fromBean(emsCodigoAlarme);
						alarmeBean.setNivel(nivel);
						alarmeBean.setCodigoCidStr(null);
						alarmeBean.setCodigoFmiStr(null);
						alarmeBean.setCodigoEidStr(alarmeBean.getCodigoEid().toString());
						alarmeBean.setTotalOcorrencias((BigDecimal)pair[4]);
						listForm.add(alarmeBean);

					}
//					if(codigoAlarmes.size() == 0){
//						ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
//						alarmeBean.setNivel(nivel);
//						alarmeBean.setCodigo(((BigDecimal)pair[0]).toString());
//						alarmeBean.setCodigoCidStr(null);
//						alarmeBean.setCodigoFmiStr(null);
//						alarmeBean.setCodigoEid((BigDecimal)pair[3]);
//						alarmeBean.setCodigoEidStr(((BigDecimal)pair[3]).toString());
//						alarmeBean.setTotalOcorrencias((BigDecimal)pair[4]);
//						alarmeBean.setDescTipoAlarme("MID - EID");
//						listForm.add(alarmeBean);
//					}
				}

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
	public List<ConfigAlarmeBean> findAllPlAssociado(Long idSegmento){
		List<ConfigAlarmeBean> listForm = new ArrayList<ConfigAlarmeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select distinct td.MID, td.CID, td.FMI, td.EID, td.NIVEL from EMS_DIAGNOSTIC d, EMS_TYPE_DIAGNOSTIC td"+
					"	where d.MESSAGE_ID = td.ID_MESSAGE_ID"+
					"	and d.RECEIVED_TIME = td.ID_RECEIVE_TIME"+
					"	and td.ID_EMS_SEGMENTO =:ID_EMS_SEGMENTO" );
			query.setParameter("ID_EMS_SEGMENTO", idSegmento);
			List<Object[]> list = (List<Object[]>) query.getResultList();
			String SQL = "";
			List<EmsCodigoAlarme> codigoAlarmes = null;
			for (Object[] pair : list) {
				if(pair[0] != null && pair[1] != null && pair[2] != null){
					SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
					" 	select id from EmsTipoAlarme where descricao = 'MID - CID - FMI')" +
					" and ca.codigoCid =:codigoCid"+
					" and ca.codigoFmi =:codigoFmi";
					query = manager.createQuery(SQL);
					query.setParameter("codigoAlarme", ((BigDecimal)pair[0]).toString());
					query.setParameter("codigoCid", (BigDecimal)pair[1]);
					query.setParameter("codigoFmi", (BigDecimal)pair[2]);
					codigoAlarmes = query.getResultList();
					for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
						ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
						alarmeBean.fromBean(emsCodigoAlarme);
						alarmeBean.setNivel(((BigDecimal)pair[4]).longValue());
						listForm.add(alarmeBean);
						
					}
				}
				//				if(pair[1] != null){
				//				SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
				//				" 	select id from EmsTipoAlarme where descricao = 'CID')";
				//				query = manager.createQuery(SQL);
				//				query.setParameter("codigoAlarme", ((BigDecimal)pair[1]).toString());
				//				codigoAlarmes = query.getResultList();
				//				for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
				//					ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
				//					alarmeBean.fromBean(emsCodigoAlarme);
				//					listForm.add(alarmeBean);
				//					
				//				}
				//				}
				//				if(pair[2] != null){
				//				SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
				//				" 	select id from EmsTipoAlarme where descricao = 'FMI')";
				//				query = manager.createQuery(SQL);
				//				query.setParameter("codigoAlarme", ((BigDecimal)pair[2]).toString());
				//				codigoAlarmes = query.getResultList();
				//				for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
				//					ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
				//					alarmeBean.fromBean(emsCodigoAlarme);
				//					listForm.add(alarmeBean);
				//					
				//				}
				//				}
				if(pair[0] != null && pair[3] != null){
					SQL = "from EmsCodigoAlarme ca where ca.descricao =:codigoAlarme and ca.idTipoAlarme.id = ("+
					" 	select id from EmsTipoAlarme where descricao = 'MID - EID')"+
					" and ca.codigoEid =:codigoEid";
					query = manager.createQuery(SQL);
					query.setParameter("codigoAlarme", ((BigDecimal)pair[0]).toString());
					query.setParameter("codigoEid", (BigDecimal)pair[3]);
					codigoAlarmes = query.getResultList();
					for (EmsCodigoAlarme emsCodigoAlarme : codigoAlarmes) {
						ConfigAlarmeBean alarmeBean = new ConfigAlarmeBean();
						alarmeBean.fromBean(emsCodigoAlarme);
						alarmeBean.setNivel(((BigDecimal)pair[4]).longValue());
						listForm.add(alarmeBean);
						
					}
				}
				
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
	
	public Boolean salvarCodigoCliente (CodigoClienteBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsCodigoCliente where codigo=:codigo");
			query.setParameter("codigo",bean.getCodigo());
			if(query.getResultList().size()>0){
				EmsCodigoCliente codCliente = (EmsCodigoCliente)query.getSingleResult();
				if(bean.getIdLoginCliente().equals(codCliente.getIdLoginCliente())){
					if(bean.getId() == null || bean.getId() == 0){
						return false;
					}
				}else{
					return false;
				}
				
			}
			manager.getTransaction().begin();
			
			EmsCodigoCliente codigoCliente = new EmsCodigoCliente();
			
			if(bean.getId() == null || bean.getId() == 0){
				codigoCliente.setIdLoginCliente(bean.getIdLoginCliente());
				codigoCliente.setCodigo(bean.getCodigo());
				manager.merge(codigoCliente);
			}else{
				codigoCliente = manager.find(EmsCodigoCliente.class, bean.getId());
				codigoCliente.setIdLoginCliente(bean.getIdLoginCliente());
				codigoCliente.setCodigo(bean.getCodigo());
				manager.merge(codigoCliente);
			}
			manager.getTransaction().commit();

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
		return true;
	}
	
	public List<CodigoClienteBean> findAllCodigoCliente(LoginClienteBean bean){
		List<CodigoClienteBean> listForm = new ArrayList<CodigoClienteBean>();
		EntityManager manager = null;
		EmsLoginCliente loginAux = new EmsLoginCliente(); 
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsCodigoCliente where idLoginCliente=:login " );
			query.setParameter("login", bean.getId());
			List<EmsCodigoCliente> codigos = (List<EmsCodigoCliente>) query.getResultList();
			for (EmsCodigoCliente codigo : codigos) {
				CodigoClienteBean codigoCliente = new CodigoClienteBean();
				codigoCliente.setId(codigo.getId());
				codigoCliente.setCodigo(codigo.getCodigo());
				codigoCliente.setIdLoginCliente(codigo.getIdLoginCliente()); 
				loginAux = manager.find(EmsLoginCliente.class, codigo.getIdLoginCliente());
				codigoCliente.setLoginCliente(loginAux.getLoginCliente());
				listForm.add(codigoCliente);
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
	
	public Boolean removerCodigoCliente(CodigoClienteBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			manager.remove(manager.find(EmsCodigoCliente.class, bean.getId()));
			manager.getTransaction().commit();
			return true;
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
		return false;
	}
//	public static void main(String[] args) {
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
//		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa", Locale.ENGLISH);
//		try {
//			System.out.println(dateFormat2.parse("13-APR-13 01:03:55 PM"));
//			//System.out.println(dateFormat.parse("13/APR/2013"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
}


