package com.emsolution.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.LoginClienteBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsLoginCliente;
import com.emsolution.util.JpaUtil;

public class LoginClienteBusiness {
	
	private UsuarioBean usuariobean;

	public LoginClienteBusiness(UsuarioBean bean) {
		this.usuariobean = bean;
	}
	/**
	 * Retorna uma Coleção de tipo de Alarme
	 * @return
	 */
	public Boolean removerLoginCliente(LoginClienteBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			manager.remove(manager.find(EmsLoginCliente.class, bean.getId()));
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
	public LoginClienteBean saveOrUpdateLoginCliente (LoginClienteBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsLoginCliente loginCliente = manager.find(EmsLoginCliente.class, bean.getId());
			EmsLoginCliente login= null;
			manager.getTransaction();
			if(bean.getId() == null || bean.getId() == 0){
				login = new EmsLoginCliente();
				login.setLoginCliente(bean.getLoginCliente());
				manager.merge(login);
			}else{
				login = manager.find(EmsLoginCliente.class, bean.getId());
				login.setId(bean.getId());
				login.setLoginCliente(bean.getLoginCliente());
				manager.merge(login);
			}
			manager.getTransaction().commit();
			bean.setId(login.getId());
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

	public List<LoginClienteBean> findAllLoginCliente(){
		List<LoginClienteBean> listForm = new ArrayList<LoginClienteBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from Ems_Login_Cliente" );
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				LoginClienteBean loginClienteBean = new LoginClienteBean();
				loginClienteBean.setId(((BigDecimal)pair[0]).longValue());
				loginClienteBean.setLoginCliente((String)pair[1]);
				listForm.add(loginClienteBean);
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


