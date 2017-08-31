package com.emsolution.business;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.ClienteBean;
import com.emsolution.bean.ConsumoCombustivelBean;
import com.emsolution.bean.FamiliaBean;
import com.emsolution.bean.LoginClienteBean;
import com.emsolution.bean.TipoAlarmeBean;
import com.emsolution.entity.EmsConsumoCombustivel;
import com.emsolution.entity.EmsLoginCliente;
import com.emsolution.entity.EmsTipoAlarme;
import com.emsolution.entity.ScFamilia;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.IConstantAccess;
import com.emsolution.util.JpaUtil;

public class ConsumoCombustivelBusiness {

	public List<FamiliaBean> findAllFamilia(){
		List<FamiliaBean> listFamilia = new ArrayList<FamiliaBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from sc_familia" );
			List<Object[]> familia = query.getResultList();
			for (Object[] objects : familia) {
				FamiliaBean aux = new FamiliaBean();
				aux.setId(Long.valueOf(((BigDecimal)objects[0]).toString()));
				aux.setDescricao((String)objects[1]);
				aux.setIdConsumoCombustivel((Long)objects[2]);
				listFamilia.add(aux);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listFamilia;
	}
	public ConsumoCombustivelBean saveOrUpdateConsumo (ConsumoCombustivelBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
	
			EmsConsumoCombustivel ecc = null;

			if(bean.getId() == null || bean.getId() == 0){
				ecc = new EmsConsumoCombustivel();

				ecc.setIdFamilia(manager.find(ScFamilia.class, bean.getIdFamilia()));
				ecc.setMaxPorcetagemAlta(bean.getMaxPorcentagemAlta());
				ecc.setMaxPorcetagemBaixa(bean.getMaxPorcentagemBaixa());
				ecc.setMaxPorcetagemMedia(bean.getMaxPorcentagemMedia());
				ecc.setMinPorcetagemAlta(bean.getMinPorcentagemAlta());
				ecc.setMinPorcetagemBaixa(bean.getMinPorcentagemBaixa());
				ecc.setMinPorcetagemMedia(bean.getMinPorcentagemMedia());
				manager.persist(ecc);
			}else{
				ecc = manager.find(EmsConsumoCombustivel.class, bean.getId());
				//ecc.setId(bean.getId());
				ecc.setIdFamilia(manager.find(ScFamilia.class, bean.getIdFamilia()));
				ecc.setMaxPorcetagemAlta(bean.getMaxPorcentagemAlta());
				ecc.setMaxPorcetagemBaixa(bean.getMaxPorcentagemBaixa());
				ecc.setMaxPorcetagemMedia(bean.getMaxPorcentagemMedia());
				ecc.setMinPorcetagemAlta(bean.getMinPorcentagemAlta());
				ecc.setMinPorcetagemBaixa(bean.getMinPorcentagemBaixa());
				ecc.setMinPorcetagemMedia(bean.getMinPorcentagemMedia());
				manager.merge(ecc);
			}
			manager.getTransaction().commit();
			bean.setId(ecc.getId());
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
	public Boolean removerConsumo (ConsumoCombustivelBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			manager.remove(manager.find(EmsConsumoCombustivel.class, bean.getId()));
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
	public List<ConsumoCombustivelBean> findAllConsumo(){
		List<ConsumoCombustivelBean> listFamilia = new ArrayList<ConsumoCombustivelBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsConsumoCombustivel" );
			if(query.getResultList().size()>0){
				List<EmsConsumoCombustivel> combustivel = query.getResultList();

				for (EmsConsumoCombustivel objects : combustivel) {
					ConsumoCombustivelBean aux = new ConsumoCombustivelBean();
					aux.setId(objects.getId());
					aux.setIdFamilia(objects.getIdFamilia().getId());
					aux.setFamilia(objects.getIdFamilia().getDescricao());
					aux.setMinPorcentagemBaixa(objects.getMinPorcetagemBaixa());
					aux.setMaxPorcentagemBaixa(objects.getMaxPorcetagemBaixa());
					aux.setMinPorcentagemMedia(objects.getMinPorcetagemMedia());
					aux.setMaxPorcentagemMedia(objects.getMaxPorcetagemMedia());
					aux.setMinPorcentagemAlta(objects.getMinPorcetagemAlta());
					aux.setMaxPorcentagemAlta(objects.getMaxPorcetagemAlta());
					aux.setBaixo(objects.getMinPorcetagemBaixa()+" - "+objects.getMaxPorcetagemBaixa());
					aux.setMedio(objects.getMinPorcetagemMedia()+" - "+objects.getMaxPorcetagemMedia());
					aux.setAlto(objects.getMinPorcetagemAlta()+" - "+objects.getMaxPorcetagemAlta());
					listFamilia.add(aux);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listFamilia;
	}
}
