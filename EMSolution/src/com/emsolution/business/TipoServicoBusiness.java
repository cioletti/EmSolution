package com.emsolution.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.FornecedorServicoTerceirosBean;
import com.emsolution.bean.ServicoTerceirosBean;
import com.emsolution.bean.TipoServicoBean;
import com.emsolution.entity.EmsSegmentoServTerceiros;
import com.emsolution.entity.EmsSegmentoServTerceirosPK;
import com.emsolution.entity.GeCheckIn;
import com.emsolution.entity.GeFornTipoServTerceiros;
import com.emsolution.entity.GeFornecedorServTerceiros;
import com.emsolution.entity.GeSegmentoServTerceiros;
import com.emsolution.entity.GeSegmentoServTerceirosPK;
import com.emsolution.entity.GeStatusServTerceiros;
import com.emsolution.entity.GeTipoServicoTerceiros;
import com.emsolution.entity.ScTipoServicoTerceiros;
import com.emsolution.util.JpaUtil;
import com.emsolution.util.ValorMonetarioHelper;

public class TipoServicoBusiness {
	
	public TipoServicoBean saveOrUpdate(TipoServicoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeTipoServicoTerceiros terceiros = new GeTipoServicoTerceiros();
			if(bean.getId() > 0){
				terceiros = manager.find(GeTipoServicoTerceiros.class, bean.getId());
				terceiros.setDescricao(bean.getDescricao().toUpperCase());
				manager.merge(terceiros);
			}else{
				terceiros.setDescricao(bean.getDescricao().toUpperCase());
				manager.persist(terceiros);
			}
			ScTipoServicoTerceiros scTipoServicoTerceiros = new ScTipoServicoTerceiros();
			scTipoServicoTerceiros.setId(terceiros.getId());
			scTipoServicoTerceiros.setDescricao(terceiros.getDescricao());
			manager.merge(scTipoServicoTerceiros);
			manager.getTransaction().commit();
			bean.setId(terceiros.getId());
			return bean;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
	public Boolean remover(Long idTipoServico){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeTipoServicoTerceiros terceiros = manager.find(GeTipoServicoTerceiros.class, idTipoServico);
			manager.remove(terceiros);
			manager.getTransaction().commit();
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public List<TipoServicoBean> findAllServicoTerceiros(){
		EntityManager manager = null;
		List<TipoServicoBean> result = new ArrayList<TipoServicoBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GeTipoServicoTerceiros order by descricao");
			List<GeTipoServicoTerceiros> terceirosList = query.getResultList();
			for (GeTipoServicoTerceiros geTipoServicoTerceiros : terceirosList) {
				TipoServicoBean bean = new TipoServicoBean();
				bean.setId(geTipoServicoTerceiros.getId());
				bean.setDescricao(geTipoServicoTerceiros.getDescricao());
				result.add(bean);
			}
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public List<ServicoTerceirosBean> saveServicoTerceiros(ServicoTerceirosBean bean, Long idProposta, String observacao){
		//Boolean proposta = this.saveDataProposta( bean.getQtdDiasProposta(), idCheckin, observacao);
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			EmsSegmentoServTerceirosPK pk = new EmsSegmentoServTerceirosPK(bean.getIdSegmento(), bean.getIdTipoServicoTerceiros());
			EmsSegmentoServTerceiros servTerceiros = new EmsSegmentoServTerceiros(pk);
			servTerceiros.setData(new Date());
			servTerceiros.setValor(BigDecimal.valueOf( Double.valueOf(bean.getValor().replace(".", "").replace(",", "."))));
			servTerceiros.setQtd(bean.getQtdServTerceiros());
			servTerceiros.setObs(bean.getObs());
			servTerceiros.setLocalServico(bean.getLocalServico());
			servTerceiros.setIdFornServTerceiros(manager.find(GeFornecedorServTerceiros.class, bean.getIdFornecedor()));
			manager.merge(servTerceiros);
			manager.getTransaction().commit();
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return this.findAllServicoTerceirosAssociado(bean.getIdSegmento());
	}
	public Boolean saveDataProposta(String qtdDiasProposta, Long idCheckin, String observacao){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeCheckIn where id =:idCheckin");
			query.setParameter("idCheckin", idCheckin);
			//GeTipoFrete frete = manager.find(GeTipoFrete.class, idFrete);
			GeCheckIn checkIn = (GeCheckIn)query.getSingleResult();
			//checkIn.setValidadeProposta(Long.valueOf((qtdDiasProposta)));
			//checkIn.setIdTipoFrete(frete);
			checkIn.setObservacao(observacao);
			manager.merge(checkIn);
			manager.getTransaction().commit();			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;
	}
	
	public List<ServicoTerceirosBean> findAllServicoTerceirosAssociado(Long idSegmento){
		EntityManager manager = null;
		List<ServicoTerceirosBean> result = new ArrayList<ServicoTerceirosBean>();
		try {
			String SQL = "";
			if(idSegmento != null && idSegmento > 0){
				SQL = "From EmsSegmentoServTerceiros where emsSegmentoServTerceirosPK.idEmsSegmento = "+idSegmento;
			}else{
				SQL = "From EmsSegmentoServTerceiros";
			}
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery(SQL);
			List<EmsSegmentoServTerceiros> terceirosList = query.getResultList();
			//SituacaoServicoTerceirosBusiness situacaoServTerc = new SituacaoServicoTerceirosBusiness(null); 
			for (EmsSegmentoServTerceiros geTipoServicoTerceiros : terceirosList) {
				ServicoTerceirosBean bean = new ServicoTerceirosBean();
				//bean.setArquivoDetalhe(situacaoServTerc.recuperarNomeArquivoDetalhes(geTipoServicoTerceiros.getGeSegmentoServTerceirosPK().getIdSegmento()+"_"+ geTipoServicoTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros())); 
				bean.setIdSegmento(geTipoServicoTerceiros.getEmsSegmentoServTerceirosPK().getIdEmsSegmento());
				bean.setIdTipoServicoTerceiros(geTipoServicoTerceiros.getEmsSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
				GeTipoServicoTerceiros tipoServicoTerceiros = manager.find(GeTipoServicoTerceiros.class, geTipoServicoTerceiros.getEmsSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
				bean.setDescricao(tipoServicoTerceiros.getDescricao());
				bean.setValor(String.valueOf(ValorMonetarioHelper.formata("###,###,##0.00", geTipoServicoTerceiros.getValor().doubleValue())));
				bean.setQtdServTerceiros(geTipoServicoTerceiros.getQtd().longValue());
				bean.setObs(geTipoServicoTerceiros.getObs());
				if(geTipoServicoTerceiros.getIdStatusServTerceiros() != null){
					bean.setStatusServTerceiros(geTipoServicoTerceiros.getIdStatusServTerceiros().getDescricao());
					bean.setSiglaStatusServTerceiros(geTipoServicoTerceiros.getIdStatusServTerceiros().getSigla());
				}
				if(geTipoServicoTerceiros.getIdFornServTerceiros() != null){
					bean.setDescricaoFornecedor(geTipoServicoTerceiros.getIdFornServTerceiros().getDescricao());
					bean.setIdFornecedor(geTipoServicoTerceiros.getIdFornServTerceiros().getId());
				}
				bean.setFornecedorList(this.findAllFornecedoresAssociados(bean.getIdTipoServicoTerceiros()));
				
				if(geTipoServicoTerceiros.getLocalServico()!=null){
					bean.setLocalServico(geTipoServicoTerceiros.getLocalServico());

					if(geTipoServicoTerceiros.getLocalServico().equals("I")){
						bean.setDescricaoLocalServico("Interno");
					}else{
						bean.setDescricaoLocalServico("Externo");
					}
				}
				result.add(bean);
			}
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public Boolean remover(ServicoTerceirosBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from EMS_SEGMENTO_SERV_TERCEIROS where ID_EMS_SEGMENTO =:ID_SEGMENTO and ID_TIPO_SERVICO_TERCEIROS =:ID_TIPO_SERVICO_TERCEIROS");
			query.setParameter("ID_SEGMENTO", bean.getIdSegmento());
			query.setParameter("ID_TIPO_SERVICO_TERCEIROS", bean.getIdTipoServicoTerceiros());
			query.executeUpdate();
			manager.getTransaction().commit();
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public List<FornecedorServicoTerceirosBean> findAllFornecedorServTerceiro(){
		List<FornecedorServicoTerceirosBean> fornecedores = new ArrayList<FornecedorServicoTerceirosBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance(); 

			Query query = manager.createQuery("From GeFornecedorServTerceiros order by descricao");
			List<GeFornecedorServTerceiros> result = query.getResultList();
			for (GeFornecedorServTerceiros fornecedorObj : result) { 
				FornecedorServicoTerceirosBean bean = new FornecedorServicoTerceirosBean();
				bean.fromBean(fornecedorObj);
				fornecedores.add(bean);
			}
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return fornecedores;
	}
	public FornecedorServicoTerceirosBean saveOrUpdateFornecedorServTerceiros(FornecedorServicoTerceirosBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFornecedorServTerceiros forn = new GeFornecedorServTerceiros();
			
			if(bean.getId() > 0){
				forn = manager.find(GeFornecedorServTerceiros.class, bean.getId());			
				
				forn.setDescricao(bean.getDescricao().toUpperCase());
				forn.setEndereco(bean.getEndereco().toUpperCase());
				forn.setTelefone(bean.getTelefone());
				forn.setEmail(bean.getEmail());
				forn.setCnpj(bean.getCnpj());
				forn.setCodigo(bean.getCodigo());
				manager.merge(forn);
			}else{
				forn.setDescricao(bean.getDescricao().toUpperCase());
				forn.setEndereco(bean.getEndereco().toUpperCase());
				forn.setTelefone(bean.getTelefone());
				forn.setEmail(bean.getEmail());
				forn.setCnpj(bean.getCnpj());
				forn.setCodigo(bean.getCodigo());
				manager.persist(forn);
				
			}
			bean.setId(forn.getId());
			manager.getTransaction().commit();
			return bean;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;

	}
	
	public Boolean removerFornecedorServTerceiro(FornecedorServicoTerceirosBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFornecedorServTerceiros forn = manager.find(GeFornecedorServTerceiros.class, bean.getId());
			manager.remove(forn);
			manager.getTransaction().commit();

			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public List<TipoServicoBean> findAllServTerceirosAssociados(Long idFornecedor) {
		List<TipoServicoBean> servAssociados = new ArrayList<TipoServicoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeTipoServicoTerceiros t where t.id in(select idTipoServTerceiros.id from GeFornTipoServTerceiros where idFornServTerceiros.id =:idFornecedor) order by t.descricao");
			query.setParameter("idFornecedor", idFornecedor);
			List<GeTipoServicoTerceiros> geTipoServicoTerceiros = query.getResultList();
			for (GeTipoServicoTerceiros terceiros : geTipoServicoTerceiros) {
				TipoServicoBean tipoServ = new TipoServicoBean();
				tipoServ.setDescricao(terceiros.getDescricao());
				tipoServ.setId(terceiros.getId());
				servAssociados.add(tipoServ);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return servAssociados;
	}
	public List<TipoServicoBean> findAllServTerceirosNaoAssociados(Long idFornecedor) {
		List<TipoServicoBean> servAssociados = new ArrayList<TipoServicoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeTipoServicoTerceiros t where t.id not in(select idTipoServTerceiros.id from GeFornTipoServTerceiros where idFornServTerceiros.id =:idFornecedor) order by t.descricao");
			query.setParameter("idFornecedor",idFornecedor);
			List<GeTipoServicoTerceiros> geTipoServicoTerceiros = query.getResultList();
			for (GeTipoServicoTerceiros terceiros : geTipoServicoTerceiros) {
				TipoServicoBean tipoServ = new TipoServicoBean();
				tipoServ.setDescricao(terceiros.getDescricao());
				tipoServ.setId(terceiros.getId());
				servAssociados.add(tipoServ);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return servAssociados;
	}
	public Long associarServicoTerceiro(Long idFornecedor, Long idTipoServico){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFornecedorServTerceiros fornecedor = manager.find(GeFornecedorServTerceiros.class, idFornecedor);
			GeTipoServicoTerceiros tipoServicoTerceiro = manager.find(GeTipoServicoTerceiros.class, idTipoServico);
			GeFornTipoServTerceiros forn = new GeFornTipoServTerceiros();
			
			forn.setIdFornServTerceiros(fornecedor);
			forn.setIdTipoServTerceiros(tipoServicoTerceiro);
			manager.persist(forn);
			manager.getTransaction().commit();

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return idFornecedor;
		
	}
	
	public Boolean desassociarServicoTerceiro (TipoServicoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from GE_FORN_TIPO_SERV_TERCEIROS where ID_TIPO_SERV_TERCEIROS = "+bean.getId());
			query.executeUpdate();
			manager.getTransaction().commit();				

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;		
	}
	
	/**
	 * Recupera todos os fornecedores associados a um tipo de serviço de terceiros
	 * @param idTipoServTerceiros
	 * @return
	 */
	public List<FornecedorServicoTerceirosBean> findAllFornecedoresAssociados(Long idTipoServTerceiros) {
		List<FornecedorServicoTerceirosBean> fornecedores = new ArrayList<FornecedorServicoTerceirosBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeFornTipoServTerceiros where idTipoServTerceiros.id =:idTipoServTerceiros");
			query.setParameter("idTipoServTerceiros",idTipoServTerceiros);
			List<GeFornTipoServTerceiros> geTipoServicoTerceiros = query.getResultList();
			for (GeFornTipoServTerceiros terceiros : geTipoServicoTerceiros) {
				FornecedorServicoTerceirosBean bean = new FornecedorServicoTerceirosBean();
				bean.setId(terceiros.getIdFornServTerceiros().getId());
				bean.setDescricao(terceiros.getIdFornServTerceiros().getDescricao());
				fornecedores.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return fornecedores;
	}
}
