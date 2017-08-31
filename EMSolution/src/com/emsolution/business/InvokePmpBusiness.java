package com.emsolution.business;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.InspecaoPmpBean;
import com.emsolution.bean.InspecaoPmpTreeBean;
import com.emsolution.util.JpaUtil;

public class InvokePmpBusiness {
	public InspecaoPmpBean findInspecaoPmp(Long idOsPalm){

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			String sql = "select op.tipo_operacao, op.cliente, op.numero_os, op.modelo, op.serie, op.tecnico, op.filial, " +
					"op.tipo_manutencao, op.contato, op.telefone, op.emissao, op.smu, f.descricao, op.equipamento, op.idos_palm," +
					" CASE (select et.id_os_palm from pmp_file_et et where et.id_os_palm = op.idos_palm)WHEN null THEN 'N' ELSE 'S' END as fileEt," +
					" CASE (select count(distinct (dt.status)) from OS_PALM_DT dt where dt.OS_PALM_IDOS_PALM = op.idos_palm)WHEN 1 THEN 'Não' ELSE 'Sim' END as backlog  " +
					" from os_palm op " +
					"inner join pmp_familia f on f.id = op.id_familia where op.tipoinspecao like 'PM' " +
					" and op.idos_palm =:idOsPalm";
			Query query = manager.createNativeQuery(sql);
			query.setParameter("idOsPalm",idOsPalm);


			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");

			Object[] inspecao = (Object[])query.getSingleResult();

			InspecaoPmpBean insPmp = new InspecaoPmpBean();
			if(inspecao[0] != null){insPmp.setTipoOperacao(inspecao[0].toString());}
			insPmp.setCliente(inspecao[1].toString());
			insPmp.setNumeroOs(inspecao[2].toString());
			insPmp.setModelo(inspecao[3].toString());
			insPmp.setSerie(inspecao[4].toString());
			if(inspecao[5] != null){insPmp.setTecnico(inspecao[5].toString());}				
			insPmp.setFilial(inspecao[6].toString());
			if(inspecao[7] != null){insPmp.setTipoManutencao(inspecao[7].toString());}
			if(inspecao[8] != null){insPmp.setContato(inspecao[8].toString());}
			if(inspecao[9] != null){insPmp.setTelefone(inspecao[9].toString());}
			if(inspecao[10] != null){
				Date date = format.parse((String)inspecao[10]);
				insPmp.setEmissao((format2.format(date)).replaceAll("-", "/"));
			}
			if(inspecao[11] != null){insPmp.setHorimetro(inspecao[11].toString());}				
			if(inspecao[12] != null){insPmp.setFamilia(inspecao[12].toString());}
			if(inspecao[13] != null){insPmp.setEquipamento(inspecao[13].toString());}
			insPmp.setId(((BigDecimal)inspecao[14]).longValue());
			insPmp.setFileEt(((String)inspecao[15]));
			insPmp.setHaveBacklog(((String)inspecao[16]));
			return insPmp;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public InspecaoPmpBean findInspecaoCampo(Long idOsPalm){
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			String sql = "select op.tipo_operacao, op.cliente, op.numero_os, op.modelo, op.serie, op.tecnico, op.filial, " +
			"op.tipo_manutencao, op.contato, op.telefone, op.emissao, op.smu, f.descricao, op.equipamento, op.idos_palm," +
			" 'N' as fileEt," +
			" CASE (select count(distinct (dt.status)) from SC_OS_PALM_DT dt where dt.OS_PALM_IDOS_PALM = op.idos_palm)WHEN 1 THEN 'Não' ELSE 'Sim' END as backlog  " +
			" from sc_os_palm op " +
			"inner join sc_familia f on f.id = op.id_familia where op.tipoinspecao like 'SC' " +
			" and op.idos_palm =:idOsPalm";
			Query query = manager.createNativeQuery(sql);
			query.setParameter("idOsPalm",idOsPalm);
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
			
			Object[] inspecao = (Object[])query.getSingleResult();
			
			InspecaoPmpBean insPmp = new InspecaoPmpBean();
			if(inspecao[0] != null){insPmp.setTipoOperacao(inspecao[0].toString());}
			insPmp.setCliente(inspecao[1].toString());
			insPmp.setNumeroOs(inspecao[2].toString());
			insPmp.setModelo(inspecao[3].toString());
			insPmp.setSerie(inspecao[4].toString());
			if(inspecao[5] != null){insPmp.setTecnico(inspecao[5].toString());}				
			insPmp.setFilial(inspecao[6].toString());
			if(inspecao[7] != null){insPmp.setTipoManutencao(inspecao[7].toString());}
			if(inspecao[8] != null){insPmp.setContato(inspecao[8].toString());}
			if(inspecao[9] != null){insPmp.setTelefone(inspecao[9].toString());}
			if(inspecao[10] != null){
				Date date = format.parse((String)inspecao[10]);
				insPmp.setEmissao((format2.format(date)).replaceAll("-", "/"));
			}
			if(inspecao[11] != null){insPmp.setHorimetro(inspecao[11].toString());}				
			if(inspecao[12] != null){insPmp.setFamilia(inspecao[12].toString());}
			if(inspecao[13] != null){insPmp.setEquipamento(inspecao[13].toString());}
			insPmp.setId(((BigDecimal)inspecao[14]).longValue());
			insPmp.setFileEt(((String)inspecao[15]));
			insPmp.setHaveBacklog(((String)inspecao[16]));
			return insPmp;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public List<InspecaoPmpTreeBean> findAllInspencaoPmpTree(Long idOsPalm){

		EntityManager manager = null;
		List<InspecaoPmpTreeBean> inspecaoPmpTree = new ArrayList<InspecaoPmpTreeBean>();
		try {
			manager = JpaUtil.getInstance();

			Query query_pai = manager.createNativeQuery("select arv.id_arv, arv.descricao ,  arv.smcs, arv.id_pai " +
					"from arv_inspecao arv where arv.id_arv in(select distinct op.grupo " +
					"from os_palm_dt op where op.os_palm_idos_palm = :idOsPalm )order by arv.id_arv");

			query_pai.setParameter("idOsPalm", idOsPalm);

			List<Object[]> resultPai = query_pai.getResultList();

			for (Object [] inspecaoPai : resultPai) {
				InspecaoPmpTreeBean obj = new InspecaoPmpTreeBean();
				if(inspecaoPai[0] != null){obj.setIdIrdav(((BigDecimal)inspecaoPai[0]).longValue());}
				if(inspecaoPai[1] != null){obj.setDescricao(inspecaoPai[1].toString());}

				Query queryFilho = manager.createNativeQuery("select arv.id_arv,arv.descricao ,arv.smcs, dt.grupo, dt.status, dt.simnao, dt.tipo_manutencao, CONVERT (VARCHAR(1000), dt.obs), dt.idos_palm_dt " +
						"from arv_inspecao arv, os_palm_dt dt where dt.grupo = :grupo and dt.os_palm_idos_palm = :idOsPalm " +
						"and arv.id_arv = dt.id_idarv");

				queryFilho.setParameter("idOsPalm", idOsPalm);
				queryFilho.setParameter("grupo", obj.getIdIrdav());

				List<Object[]> resultFilho = queryFilho.getResultList();
				List<InspecaoPmpTreeBean> inspecaoPmpTreeFilhos = new ArrayList<InspecaoPmpTreeBean>();
				for (Object [] inspecaoFilho : resultFilho) {
					InspecaoPmpTreeBean objFilho = new InspecaoPmpTreeBean();
					if(inspecaoFilho[0] != null){objFilho.setIdIrdav(((BigDecimal)inspecaoFilho[0]).longValue());}
					if(inspecaoFilho[1] != null){objFilho.setDescricao(inspecaoFilho[1].toString());}
					if(inspecaoFilho[3] != null){objFilho.setGrupo(inspecaoFilho[3].toString());}

					if(inspecaoFilho[4] != null){
						if(inspecaoFilho[4].toString().equals("C")){
							objFilho.setStatus("Normal");	
							objFilho.setStatusUrlImage("img/CE.png");
						}else  if(inspecaoFilho[4].toString().equals("NC")){
							objFilho.setStatus("Não Conforme");	
							objFilho.setStatusUrlImage("img/CN.png");	
						}else  if(inspecaoFilho[4].toString().equals("NA")){
							objFilho.setStatus("Não Se Aplica");	
							objFilho.setStatusUrlImage("img/AM.png");	
						}		
					}

					if(inspecaoFilho[5] != null){objFilho.setSimNao(inspecaoFilho[5].toString());}	
					if(inspecaoFilho[2] != null){objFilho.setSmcs(inspecaoFilho[2].toString());}
					if(inspecaoFilho[6] != null){objFilho.setTipoManutencao(inspecaoFilho[6].toString());}
					if(inspecaoFilho[8] != null){objFilho.setIdOsPalmDt(((BigDecimal)inspecaoFilho[8]).longValue());}
					if(inspecaoFilho[7] != null){objFilho.setObs(inspecaoFilho[7].toString());}


					Query queryFotoInspecao = manager.createNativeQuery("select count(id_foto_inspecao) from foto_inspecao " +
							"where id_os_palm = :idOsPalm and id_os_palm_dt = :idOsPalmDt ");
					queryFotoInspecao .setParameter("idOsPalm", idOsPalm);
					queryFotoInspecao .setParameter("idOsPalmDt", objFilho.getIdOsPalmDt());
					Long quantidadeFotos = ((Integer)queryFotoInspecao.getResultList().get(0)).longValue();

					if(quantidadeFotos > 0){
						objFilho.setFotoUrlImage("img/camera.png");
					}else{
						objFilho.setFotoUrlImage("");						
					}					
					inspecaoPmpTreeFilhos.add(objFilho);
				}
				obj.setChildren(inspecaoPmpTreeFilhos);			
				inspecaoPmpTree.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return inspecaoPmpTree;
	}
	public List<InspecaoPmpTreeBean> findAllInspencaoCampoTree(Long idOsPalm){
		
		EntityManager manager = null;
		List<InspecaoPmpTreeBean> inspecaoPmpTree = new ArrayList<InspecaoPmpTreeBean>();
		try {
			manager = JpaUtil.getInstance();
			
			Query query_pai = manager.createNativeQuery("select arv.id_arv, arv.descricao ,  arv.smcs, arv.id_pai " +
					"from sc_arv_inspecao arv where arv.id_arv in(select distinct op.grupo " +
			"from sc_os_palm_dt op where op.os_palm_idos_palm = :idOsPalm )order by arv.id_arv");
			
			query_pai.setParameter("idOsPalm", idOsPalm);
			
			List<Object[]> resultPai = query_pai.getResultList();
			
			for (Object [] inspecaoPai : resultPai) {
				InspecaoPmpTreeBean obj = new InspecaoPmpTreeBean();
				if(inspecaoPai[0] != null){obj.setIdIrdav(((BigDecimal)inspecaoPai[0]).longValue());}
				if(inspecaoPai[1] != null){obj.setDescricao(inspecaoPai[1].toString());}
				
				Query queryFilho = manager.createNativeQuery("select arv.id_arv,arv.descricao ,arv.smcs, dt.grupo, dt.status, dt.simnao, dt.tipo_manutencao, CONVERT (VARCHAR(1000), dt.obs), dt.idos_palm_dt " +
						"from sc_arv_inspecao arv, sc_os_palm_dt dt where dt.grupo = :grupo and dt.os_palm_idos_palm = :idOsPalm " +
				"and arv.id_arv = dt.id_idarv");
				
				queryFilho.setParameter("idOsPalm", idOsPalm);
				queryFilho.setParameter("grupo", obj.getIdIrdav());
				
				List<Object[]> resultFilho = queryFilho.getResultList();
				List<InspecaoPmpTreeBean> inspecaoPmpTreeFilhos = new ArrayList<InspecaoPmpTreeBean>();
				for (Object [] inspecaoFilho : resultFilho) {
					InspecaoPmpTreeBean objFilho = new InspecaoPmpTreeBean();
					if(inspecaoFilho[0] != null){objFilho.setIdIrdav(((BigDecimal)inspecaoFilho[0]).longValue());}
					if(inspecaoFilho[1] != null){objFilho.setDescricao(inspecaoFilho[1].toString());}
					if(inspecaoFilho[3] != null){objFilho.setGrupo(inspecaoFilho[3].toString());}
					
					if(inspecaoFilho[4] != null){
						if(inspecaoFilho[4].toString().equals("C")){
							objFilho.setStatus("Normal");	
							objFilho.setStatusUrlImage("img/CE.png");
						}else  if(inspecaoFilho[4].toString().equals("NC")){
							objFilho.setStatus("Não Conforme");	
							objFilho.setStatusUrlImage("img/CN.png");	
						}else  if(inspecaoFilho[4].toString().equals("NA")){
							objFilho.setStatus("Não Se Aplica");	
							objFilho.setStatusUrlImage("img/AM.png");	
						}		
					}
					
					if(inspecaoFilho[5] != null){objFilho.setSimNao(inspecaoFilho[5].toString());}	
					if(inspecaoFilho[2] != null){objFilho.setSmcs(inspecaoFilho[2].toString());}
					if(inspecaoFilho[6] != null){objFilho.setTipoManutencao(inspecaoFilho[6].toString());}
					if(inspecaoFilho[8] != null){objFilho.setIdOsPalmDt(((BigDecimal)inspecaoFilho[8]).longValue());}
					if(inspecaoFilho[7] != null){objFilho.setObs(inspecaoFilho[7].toString());}
					
					
					Query queryFotoInspecao = manager.createNativeQuery("select count(id_foto_inspecao) from sc_foto_inspecao " +
					"where id_os_palm = :idOsPalm and id_os_palm_dt = :idOsPalmDt ");
					queryFotoInspecao .setParameter("idOsPalm", idOsPalm);
					queryFotoInspecao .setParameter("idOsPalmDt", objFilho.getIdOsPalmDt());
					Long quantidadeFotos = ((Integer)queryFotoInspecao.getResultList().get(0)).longValue();
					
					if(quantidadeFotos > 0){
						objFilho.setFotoUrlImage("img/camera.png");
					}else{
						objFilho.setFotoUrlImage("");						
					}					
					inspecaoPmpTreeFilhos.add(objFilho);
				}
				obj.setChildren(inspecaoPmpTreeFilhos);			
				inspecaoPmpTree.add(obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return inspecaoPmpTree;
	}
	
	public List<Integer> findAllFotos(Integer idOsPalmDt){
		EntityManager manager = null;
		List<Integer> result = new ArrayList<Integer>();
		
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select f.id_foto_inspecao from foto_inspecao f where f.id_os_palm_dt =:id");
			query.setParameter("id", idOsPalmDt);
			List<BigDecimal> list = query.getResultList();
			for (BigDecimal bd : list) {
				result.add(bd.intValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
}
