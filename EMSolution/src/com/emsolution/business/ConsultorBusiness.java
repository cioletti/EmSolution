package com.emsolution.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.ConsultorBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.util.JpaUtil;

public class ConsultorBusiness {
	private UsuarioBean usuarioBean;
	public ConsultorBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	public List<ConsultorBean> findAllConsultor(String codCliente){
		List<ConsultorBean> listForm = new ArrayList<ConsultorBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select distinct  f.LOGIN,  c.NOME_CONSULTOR, f.EMAIL, f.EPIDNO, f.STN1 from TW_FUNCIONARIO f right join EMS_CONSULTOR c on f.LOGIN = c.COD_CONSULTOR_DBS left join ADM_PERFIL_SISTEMA_USUARIO apsu"+
					"	on f.EPIDNO = apsu.ID_TW_USUARIO"+
					"	and apsu.ID_PERFIL = (select ID from ADM_PERFIL where TIPO_SISTEMA = 'CRM' and SIGLA in ('CON', 'SUPER'))" +
					"  union " +
					"	select distinct  f.LOGIN,  f.EPLSNM +' SUPERVISOR', f.EMAIL, f.EPIDNO, f.STN1 from TW_FUNCIONARIO f, ADM_PERFIL_SISTEMA_USUARIO apsu"+
					"							where f.EPIDNO = apsu.ID_TW_USUARIO"+
					"							and apsu.ID_PERFIL = (select ID from ADM_PERFIL where TIPO_SISTEMA = 'CRM' and SIGLA in ('SUPER'))" +
					"   order by 2");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ConsultorBean bean = new ConsultorBean();
				bean.setMatriculaDbs((String)pair[0]);
				bean.setNome((String)pair[1]);
				bean.setEmail((String)pair[2]);
				bean.setMatricula((String)pair[3]);
				if(pair[4] != null){
					bean.setFilial(Integer.valueOf((String)pair[4]));
				}
				if((String)pair[0] == null){
					bean.setNome(((String)pair[1]).trim()+" - NÃO CADASTRADO NO CRM");
				}
				listForm.add(bean);
			}

			query = manager.createNativeQuery("select COD_CONSULTOR_DBS  from EMS_CONSULTOR where COD_CLIENTE =:COD_CLIENTE" );
			query.setParameter("COD_CLIENTE", codCliente);
			String codConsultor = "";
			if(query.getResultList().size() > 0){
				codConsultor = (String)query.getSingleResult();
			}
			boolean possuiConsultor = false;
			for (ConsultorBean consultorBean : listForm) {
				if(consultorBean.getMatriculaDbs() != null && consultorBean.getMatriculaDbs().equals(codConsultor)){
					consultorBean.setIsSelected(true);
					possuiConsultor = true;
				}
			}
			if(possuiConsultor == false){
				for (ConsultorBean consultorBean : listForm) {
					if(consultorBean.getFilial() != null && Integer.valueOf(this.usuarioBean.getFilial()) == consultorBean.getFilial() && consultorBean.getNome().contains("SUPERVISOR")){
						consultorBean.setIsSelected(true);
					}
					if(this.usuarioBean.getFilial().equals(14) || this.usuarioBean.getFilial().equals(13)){
						if(consultorBean.getMatricula().equals("104942") || consultorBean.getMatricula().equals("266975A")){
							consultorBean.setIsSelected(true);
						}
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
}
