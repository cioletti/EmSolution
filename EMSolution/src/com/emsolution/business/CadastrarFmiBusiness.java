package com.emsolution.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.CadastrarFmiBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsFmi;
import com.emsolution.util.JpaUtil;

public class CadastrarFmiBusiness {
	
	private UsuarioBean usuariobean;

	public CadastrarFmiBusiness(UsuarioBean bean) {
		this.usuariobean = bean;
	}
	/**
	 * 
	 * @return
	 */
	public Boolean removerCadastrarFmi(CadastrarFmiBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			manager.remove(manager.find(EmsFmi.class, bean.getCodigo()));
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
	public CadastrarFmiBean saveOrUpdateCadastrarFmi (CadastrarFmiBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsFmi fmi = new EmsFmi();
			manager.getTransaction();
			fmi.setCodigo(bean.getCodigo());
			fmi.setDescricao(bean.getDescricao());
			manager.merge(fmi);
			manager.getTransaction().commit();
			bean.setCodigo(fmi.getCodigo());
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

	public List<CadastrarFmiBean> findAllCadastrarFmi(){
		List<CadastrarFmiBean> listForm = new ArrayList<CadastrarFmiBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsFmi" );
			List<EmsFmi> list = (List<EmsFmi>) query.getResultList();
			for (EmsFmi pair : list) {
				CadastrarFmiBean CadastrarFmiBean = new CadastrarFmiBean();
				CadastrarFmiBean.setCodigo(pair.getCodigo());
				CadastrarFmiBean.setDescricao(pair.getDescricao());
				listForm.add(CadastrarFmiBean);
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


