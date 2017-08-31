package com.emsolution.business;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javassist.compiler.ast.Stmnt;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.ClienteBean;
import com.emsolution.bean.ConsultorBean;
import com.emsolution.bean.DetalhesPropostaBean;
import com.emsolution.bean.DiagnosticoBean;
import com.emsolution.bean.HistoricoOSBean;
import com.emsolution.bean.OrigemNegocioBean;
import com.emsolution.bean.PecasBean;
import com.emsolution.bean.PropostaBean;
import com.emsolution.bean.SegmentoBean;
import com.emsolution.bean.SmuBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.CrmClassificacao;
import com.emsolution.entity.CrmDetalhesProposta;
import com.emsolution.entity.CrmFaseNegociacao;
import com.emsolution.entity.CrmPecas;
import com.emsolution.entity.CrmProposta;
import com.emsolution.entity.CrmSegmento;
import com.emsolution.entity.CrmSegmentoServTerceiros;
import com.emsolution.entity.CrmSituacaoProposta;
import com.emsolution.entity.CrmTipoProposta;
import com.emsolution.entity.EmsDetalhesProposta;
import com.emsolution.entity.EmsOrigemNegocio;
import com.emsolution.entity.EmsPecas;
import com.emsolution.entity.EmsProposta;
import com.emsolution.entity.EmsSegmento;
import com.emsolution.entity.EmsSegmentoServTerceiros;
import com.emsolution.entity.EmsStatusOportunidade;
import com.emsolution.entity.EmsTipoOportunidade;
import com.emsolution.entity.TwFilial;
import com.emsolution.entity.TwFuncionario;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.ConnectionPostgre;
import com.emsolution.util.EmailHelper;
import com.emsolution.util.IConstantAccess;
import com.emsolution.util.JpaUtil;
import com.emsolution.util.ValorMonetarioHelper;
import com.emsolution.entity.CrmUsuarioProposta;

public class PropostaBusiness {
	private UsuarioBean usuarioBean;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private SimpleDateFormat fmtPtBr = new SimpleDateFormat("dd/MM/yyyy");
	public PropostaBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	public PropostaBean salvarProposta (Long idTipoOportunidade, String numSerie, String modelo, String filial){
		EntityManager manager = null;
		PropostaBean bean = new PropostaBean();
		Connection con = null;
		try {
			EmsProposta proposta = new EmsProposta();
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from EMS_PROPOSTA where IS_LOCK = 'S' and NUM_SERIE = '"+numSerie+"'  and ID_FUNCIONARIO_LOCK is not null");

			if(query.getResultList().size() > 0){
				query = manager.createNativeQuery("select * from EMS_PROPOSTA where IS_LOCK = 'S' and id_funcionario =:id_funcionario and NUM_SERIE = '"+numSerie+"'");
				query.setParameter("id_funcionario", this.usuarioBean.getMatricula());
				if(query.getResultList().size() == 0){
					return null;
				}
			}
//			con = ConectionDbs.getConnecton();
//			String SQL = "select e.CUNO, e.IDNO1 from "+IConstantAccess.LIB_DBS+".empeqpd0 e where (trim(e.EQMFS2) = '"+numSerie+"' or trim(e.RDMSR1) = '"+numSerie+"')";
//			Statement statement = con.createStatement();
//			ResultSet rs = statement.executeQuery(SQL);
//			rs.next();
			
			String SQL = "select COD_CLIENTE CUNO, id_equipamento IDNO1 from PMP_CLIENTE_PL where SERIE = '"+numSerie+"'";
			
			query = manager.createNativeQuery(SQL);
			Object[] pair = (Object[])query.getSingleResult();
			
			String codCliente = (String)pair[0];
			String idEquipamento = (String)pair[1];
			proposta.setCodClienteExt(codCliente);
			proposta.setIdEquipamento(idEquipamento);
			SQL = "select ic.IDCD01 from "+IConstantAccess.LIB_DBS+".SCPDIVF0 ic	where ic.CUNO = '"+codCliente+"' and ic.IDCD01 is not null and trim(ic.IDCD01) <> ''";
			con = ConectionDbs.getConnecton();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(SQL);

			if(rs.next()){
				String codigoSegmento = rs.getString("IDCD01");
				query = manager.createNativeQuery("select cli.fator  from sc_segmento_cliente cli where cli.codigo =:codigo order by cli.id desc");
				query.setParameter("codigo", codigoSegmento.trim());
				if(query.getResultList().size() > 0){
					proposta.setFatorCliente((BigDecimal)query.getResultList().get(0));
				}			  

			}


			query = manager.createQuery("from EmsStatusOportunidade where sigla = 'EA'");
			EmsStatusOportunidade stOp = (EmsStatusOportunidade)query.getSingleResult();

			query = manager.createNativeQuery("select id_familia from SC_MODELO_FAMILIA where MODELO =:modelo");
			query.setParameter("modelo", modelo);
			BigDecimal idFamiliaCampo = (BigDecimal)query.getSingleResult();

			query = manager.createNativeQuery("select ID_FAMILIA from GE_ARV_INSPECAO where DESCRICAO =:modelo");
			query.setParameter("modelo", modelo);
			BigDecimal idFamiliaOficina = (BigDecimal)query.getSingleResult();

			manager.getTransaction().begin();
			query = manager.createNativeQuery("update EMS_PROPOSTA set ESTIMATE_BY_FUNCIONARIO_LOCK = '"+this.usuarioBean.getEstimateBy()+"' where NUM_SERIE = '"+numSerie+"'");
			query.executeUpdate();
			
			proposta.setDataOpt(new Date());
			proposta.setIdFuncionario(this.usuarioBean.getMatricula());
			proposta.setIdStatusOpt(stOp);
			proposta.setIdTipoOpt(manager.find(EmsTipoOportunidade.class, idTipoOportunidade));
			proposta.setNumSerie(numSerie);
			proposta.setTipoCliente("EXT");
			proposta.setPrefixo(numSerie.substring(0,4));
			proposta.setModelo(modelo);
			proposta.setIdFamiliaCampo(idFamiliaCampo);
			proposta.setIdFamiliaOficina(idFamiliaOficina);
			proposta.setFilial(Long.valueOf(filial));
			proposta.setIsLock("S");
			proposta.setEstimateByFuncionarioLock(this.usuarioBean.getEstimateBy());
			manager.persist(proposta);
			manager.getTransaction().commit();
			bean.setId(proposta.getId());
			bean.setIdTipoOportunidade(proposta.getIdTipoOpt().getId());
			bean.setNomeFuncionario(this.usuarioBean.getNome());
			bean.setCodigoCiente(codCliente);
			bean.setEpdino(this.usuarioBean.getMatricula());
			bean.setIdStatusOportunidade(proposta.getIdStatusOpt().getId());
			bean.setIdFamilia(idFamiliaCampo.longValue());
			bean.setModelo(modelo);
			bean.setPrefixo(proposta.getPrefixo());
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, numSerie);
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
			try {
				if(con != null){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<PropostaBean> findAllProposta(String numSerie){
		EntityManager manager = null;
		List<PropostaBean> result = new ArrayList<PropostaBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsProposta where numSerie =:numSerie order by dataOpt");
			query.setParameter("numSerie", numSerie);
			List<EmsProposta> serieList = query.getResultList();
			for (EmsProposta emsProposta : serieList) {
				PropostaBean proposta = new PropostaBean();
				proposta.fromBean(emsProposta);
				TwFuncionario funcionario = manager.find(TwFuncionario.class, emsProposta.getIdFuncionario());
				proposta.setNomeFuncionario(funcionario.getEplsnm());
				if(usuarioBean.getIsAdm()){
					proposta.setIsAdm("S");
				}
				result.add(proposta);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public List<PropostaBean> findAllPropostaOrcamento(String numSerie){
		EntityManager manager = null;
		List<PropostaBean> result = new ArrayList<PropostaBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsProposta where numSerie like '"+numSerie+"%' and idStatusOpt.id = (select id from EmsStatusOportunidade where sigla = 'EA') order by dataOpt");
			//query.setParameter("numSerie", numSerie);
			List<EmsProposta> serieList = query.getResultList();
			for (EmsProposta emsProposta : serieList) {
				PropostaBean proposta = new PropostaBean();
				proposta.fromBean(emsProposta);
				TwFuncionario funcionario = manager.find(TwFuncionario.class, emsProposta.getIdFuncionario());
				proposta.setNomeFuncionario(funcionario.getEplsnm());
				if(usuarioBean.getIsAdm()){
					proposta.setIsAdm("S");
				}
				TwFilial filial = manager.find(TwFilial.class, emsProposta.getFilial());
				if(filial != null){
					proposta.setFilialStr(filial.getStnm());
				}
				result.add(proposta);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	/**
	 * Recupera todos os detalhes da proposta para imprimir
	 */
	public DetalhesPropostaBean findDetalhesProposta(PropostaBean propostaBean){
		EntityManager manager = null;
		DetalhesPropostaBean detalhesProppostaBean = new DetalhesPropostaBean();
		try {
			manager = JpaUtil.getInstance();
			EmsProposta proposta = manager.find(EmsProposta.class, propostaBean.getId());
			Query query = manager.createQuery("From EmsSegmento where idProposta.id =:idProposta");
			query.setParameter("idProposta", proposta.getId());
			List<EmsSegmento> segmentoList = query.getResultList();

			for (EmsSegmento seg : segmentoList) {
				SegmentoBean segmentoBean = new SegmentoBean();
				segmentoBean.setId(seg.getId());
				segmentoBean.setObservacao(seg.getObservacao());
				segmentoBean.setDescricao(seg.getNumeroSegmento()+" - "+seg.getDescricaoJobCode()+" - "+seg.getDescricaoCompCode());
				detalhesProppostaBean.getSegmentoList().add(segmentoBean);
			}
			query = manager.createQuery("From EmsDetalhesProposta where idProposta.id =:idProposta");
			query.setParameter("idProposta", proposta.getId());
			EmsDetalhesProposta detalhesProposta = new EmsDetalhesProposta();
			List<Object[]> objects;
			if(query.getResultList().size() > 0){
				detalhesProposta = (EmsDetalhesProposta)query.getSingleResult();
				detalhesProppostaBean.setAosCuidados(detalhesProposta.getAosCuidados());
				detalhesProppostaBean.setContato(detalhesProposta.getContato());
				detalhesProppostaBean.setEmail(detalhesProposta.getEmail());
				detalhesProppostaBean.setTelefone(proposta.getTelefone());
			}else{
				query = manager.createNativeQuery("select RAZAO_SOCIAL, CONTATO_SERVICOS, EMAIL_CONTATO_SERVICOS, TELEFONE_SERVICOS from PMP_CONTRATO"+ 
						" where ID = (select MAX(id) from PMP_CONTRATO where NUMERO_SERIE = '"+propostaBean.getNumSerie()+"')");
				if(query.getResultList().size() >0){
					objects = query.getResultList();
					for (Object[] contrato : objects) {
						detalhesProppostaBean.setAosCuidados((String)contrato[0]);
						detalhesProppostaBean.setContato((String)contrato[1]);
						detalhesProppostaBean.setEmail((String)contrato[2]);
						detalhesProppostaBean.setTelefone((String)contrato[3]);
					}
				}			
				query = manager.createNativeQuery("select RAZAO_SOCIAL, CONTATO, EMAIL, TELEFONE from SC_CHAMADO"+ 
						"  where ID = (select MAX(id) from SC_CHAMADO where SERIE = '"+propostaBean.getNumSerie()+"')");
				if(query.getResultList().size() >0){
					objects = query.getResultList();
					for (Object[] contrato : objects) {
						detalhesProppostaBean.setAosCuidados((String)contrato[0]);
						detalhesProppostaBean.setContato((String)contrato[1]);
						detalhesProppostaBean.setEmail((String)contrato[2]);
						detalhesProppostaBean.setTelefone((String)contrato[3]);
					}
				}			

			}

			detalhesProppostaBean.setFormaEntregaProposta(detalhesProposta.getFormaEntregaProposta());
			detalhesProppostaBean.setObjetoProposta(detalhesProposta.getObjetoProposta());
			detalhesProppostaBean.setMaquina("CATERPILLAR");
			if(detalhesProposta.getMaquina() != null){
				detalhesProppostaBean.setMaquina(detalhesProposta.getMaquina());
			}
			detalhesProppostaBean.setModelo(proposta.getModelo());
			detalhesProppostaBean.setSerie(proposta.getNumSerie());
			detalhesProppostaBean.setObservacao(detalhesProposta.getObservacao());
			detalhesProppostaBean.setCondicaoPagamento(detalhesProposta.getCondicoesPagamento());
			detalhesProppostaBean.setPrazoEntrega(detalhesProposta.getPrazoEntrega());
			if(detalhesProposta.getValidadeProposta() != null){
				detalhesProppostaBean.setValidadeProposta(fmtPtBr.format(detalhesProposta.getValidadeProposta()));
			}
			TwFilial filial = manager.find(TwFilial.class, propostaBean.getFilial());

			detalhesProppostaBean.setAprovacaoPropostaServico(filial.getAprovacaoPropostaServico());
			if(detalhesProposta.getValorReparoAposFalha()!= null){
				detalhesProppostaBean.setValorReparoAposFalha((detalhesProposta.getValorReparoAposFalha()).toString());
				Double doubleValor = new Double(detalhesProposta.getValorReparoAposFalha().toString());
				String valor = ValorMonetarioHelper.formata("###,###.00",doubleValor);
				detalhesProppostaBean.setValorReparoAposFalha(valor);
			}
			if(proposta.getIdFuncionario() != null){
				TwFuncionario funcionario = manager.find(TwFuncionario.class, proposta.getIdFuncionario());
				detalhesProppostaBean.setOrcamentista(funcionario.getEplsnm());
			}
			//detalhesProppostaBean.setFatorUrgencia(proposta.getFatorUrgencia());
			if(detalhesProposta.getIdProposta() != null){
				detalhesProppostaBean.setImprimirSubTributaria(detalhesProposta.getIdProposta().getPossuiSubTributaria());
				detalhesProppostaBean.setIsFindSubTributaria(detalhesProposta.getIdProposta().getIsFindSubTributaria());
			}
			detalhesProppostaBean.setDistancia(detalhesProposta.getDistancia());
			return detalhesProppostaBean;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	/**
	 * Salva as informações dos detalhes da proposta e envia a os para aplicar os valores de substituição tributária se for o caso
	 * @param idCheckIn
	 * @param detalhesPropostaBean
	 * @return
	 */
	public DetalhesPropostaBean saveOrUpdateDetalhesProposta(Long idProposta, DetalhesPropostaBean detalhesPropostaBean){
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("from EmsDetalhesProposta where idProposta.id =:idProposta");
			query.setParameter("idProposta", idProposta);			
			EmsDetalhesProposta emsDetalhesProposta = new EmsDetalhesProposta();
			if(query.getResultList().size() > 0){
				emsDetalhesProposta = (EmsDetalhesProposta)query.getSingleResult();
			}

			EmsProposta emsProposta = manager.find(EmsProposta.class, idProposta);
			if(detalhesPropostaBean.getId() != null && detalhesPropostaBean.getId() > 0){
				emsDetalhesProposta = manager.find(EmsDetalhesProposta.class, detalhesPropostaBean.getId());
				setDetalhesProposta(detalhesPropostaBean, manager,
						emsDetalhesProposta, emsProposta);
				manager.merge(emsDetalhesProposta);
			}else{
				setDetalhesProposta(detalhesPropostaBean, manager,
						emsDetalhesProposta, emsProposta);
				manager.persist(emsDetalhesProposta);
				detalhesPropostaBean.setId(emsDetalhesProposta.getId());
			}

			for (SegmentoBean segmentoBean : detalhesPropostaBean.getSegmentoList()) {
				EmsSegmento emsSegmento = manager.find(EmsSegmento.class, segmentoBean.getId());
				emsSegmento.setObservacao(segmentoBean.getObservacao());
			}
			emsDetalhesProposta.getIdProposta().setPossuiSubTributaria(detalhesPropostaBean.getImprimirSubTributaria());
			emsDetalhesProposta.getIdProposta().setFatorUrgencia(detalhesPropostaBean.getFatorUrgencia());
			if(detalhesPropostaBean.getImprimirSubTributaria().equals("N")){
				emsProposta.setIsFindSubTributaria("N");
				detalhesPropostaBean.setIsFindSubTributaria("N");
			} else if(detalhesPropostaBean.getImprimirSubTributaria().equals("S") && !"P".equals(emsProposta.getIsFindSubTributaria()) && !"S".equals(emsProposta.getIsFindSubTributaria())){
				emsProposta.setIsFindSubTributaria("P");
				detalhesPropostaBean.setIsFindSubTributaria("P");
				con = ConectionDbs.getConnecton(); 
				prstmt = con.createStatement();
				for (SegmentoBean segmentoBean : detalhesPropostaBean.getSegmentoList()) {
					EmsSegmento emsSegmento = manager.find(EmsSegmento.class, segmentoBean.getId());
					if(emsSegmento.getNumDoc() != null){
						//Inclui a OS na tabela do DBS 				
						String SQL = "insert into "+IConstantAccess.PESARDRTRIBUTACAO+".RDRWONO (WONO) values('"+emsSegmento.getNumDoc()+"')";
						prstmt.executeUpdate(SQL);
					}
				}
			}
			//emsDetalhesProposta.getIdProposta().setFatorUrgencia(detalhesPropostaBean.getFatorUrgencia());
			manager.getTransaction().commit();	
			return detalhesPropostaBean;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(con != null){
				try {
					prstmt.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void setDetalhesProposta(DetalhesPropostaBean detalhesPropostaBean,
			EntityManager manager, EmsDetalhesProposta emsDetalhesProposta,
			EmsProposta emsProposta) throws ParseException {
		emsDetalhesProposta.setFormaEntregaProposta(detalhesPropostaBean.getFormaEntregaProposta());
		emsDetalhesProposta.setAosCuidados(detalhesPropostaBean.getAosCuidados());
		emsProposta.setTelefone(detalhesPropostaBean.getTelefone());
		//emsProposta.setModelo(detalhesPropostaBean.getModelo());
		//emsProposta.setSerie(detalhesPropostaBean.getSerie());
		emsDetalhesProposta.setEmail(detalhesPropostaBean.getEmail());
		emsDetalhesProposta.setObjetoProposta(detalhesPropostaBean.getObjetoProposta());
		emsDetalhesProposta.setMaquina(detalhesPropostaBean.getMaquina());
		emsDetalhesProposta.setCondicoesPagamento(detalhesPropostaBean.getCondicaoPagamento());
		emsDetalhesProposta.setPrazoEntrega(detalhesPropostaBean.getPrazoEntrega());
		if(detalhesPropostaBean.getValidadeProposta() != null){
			emsDetalhesProposta.setValidadeProposta(fmtPtBr.parse(detalhesPropostaBean.getValidadeProposta()));
		}
		TwFilial filial = manager.find(TwFilial.class, Long.valueOf(this.usuarioBean.getFilial()));
		filial.setAprovacaoPropostaServico(detalhesPropostaBean.getAprovacaoPropostaServico());
		emsDetalhesProposta.setIdFuncionario(this.usuarioBean.getMatricula());
		emsDetalhesProposta.setIdProposta(emsProposta);
		emsDetalhesProposta.setObservacao(detalhesPropostaBean.getObservacao());
		emsDetalhesProposta.setDataCriacao(new Date());
		emsDetalhesProposta.setContato(detalhesPropostaBean.getContato());
		if(detalhesPropostaBean.getValorReparoAposFalha() != null){
			emsDetalhesProposta.setValorReparoAposFalha((BigDecimal.valueOf(Double.valueOf(detalhesPropostaBean.getValorReparoAposFalha().replace(".", "").replace(",", ".").replace("R$", "")))));
		}
		emsDetalhesProposta.setDistancia(detalhesPropostaBean.getDistancia());
	}

	public Boolean verificaPendeciasPecas (Long idProposta){
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsSegmento where idProposta.id =:idProposta");
			query.setParameter("idProposta", idProposta);
			List<EmsSegmento> result = (List<EmsSegmento>) query.getResultList();
			//			int i = 0;
			for (EmsSegmento segmento : result){

				if (segmento.getCodErroDocDbs() != null && (segmento.getCodErroDocDbs().equals("100") || segmento.getCodErroDocDbs().equals("99"))){
					return false;						
				}
				if(segmento.getCodErroDocDbs() == null){
					query = manager.createNativeQuery("select * from ems_pecas where id_ems_segmento =:id_ems_segmento");
					query.setParameter("id_ems_segmento", segmento.getId());
					if(query.getResultList().size() > 0){
						return false;
					}
				}
			}		

		}catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;
	}

	public List<HistoricoOSBean> findAllHistoricoOS(String serie){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatDbs = new SimpleDateFormat("yyyyMMdd");

		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		Connection con = null;

		List<HistoricoOSBean> result = new ArrayList<HistoricoOSBean>();

		try {
			con = ConectionDbs.getConnecton();
			String SQL  = "select s.wono, s.cunm, s.svmthr, seg.jbcd, seg.jbcdds, seg.cptcd,"+
					"	seg.cptcdd, s.stno, s.ivdt8, s.opndt8 from LIBU17.WOPHDRS0 s, LIBU17.WOPSEGS0 seg"+
					"	where s.wono = seg.wono"+
					"	and seg.cscc <> 'SV'"+
					"	and s.eqmfsn = '"+serie+"'" +
					" order by s.ivdt8 desc";

			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();

			while(rs.next()){
				HistoricoOSBean bean = new HistoricoOSBean();
				bean.setNumOs(rs.getString("wono"));
				bean.setCliente(rs.getString("cunm"));
				bean.setHorimetro(rs.getString("svmthr"));
				bean.setJbcd(rs.getString("jbcd"));
				bean.setDescJbcd(rs.getString("jbcdds"));
				bean.setCptcd(rs.getString("cptcd"));
				bean.setDescCptcd(rs.getString("cptcdd"));
				bean.setFilial(rs.getString("stno"));
				if(rs.getString("opndt8") != null && ! rs.getString("opndt8").equals("00000000")){
					bean.setDtAbertura(dateFormat.format(dateFormatDbs.parse(rs.getString("opndt8"))));
				}
				if(rs.getString("ivdt8") != null && ! rs.getString("ivdt8").equals("00000000")){
					bean.setDtFinalizacao(dateFormat.format(dateFormatDbs.parse(rs.getString("ivdt8"))));
				}

				result.add(bean);
			}


		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null){
					con.close();
				}
				if(prstmt_ != null){
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Boolean liberarEquipamento(String numSerie){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("update ems_proposta set is_lock = 'N', ESTIMATE_BY_FUNCIONARIO_LOCK = null where num_serie =:num_serie ");
			query.setParameter("num_serie", numSerie);
			query.executeUpdate();
			manager.getTransaction().commit();
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, numSerie);

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
	public Boolean alocarEquipamento(Long idProposta, String numSerie){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from EMS_PROPOSTA where IS_LOCK = 'S' and NUM_SERIE = '"+numSerie+"'");

			if(query.getResultList().size() > 0){
				query = manager.createNativeQuery("select * from EMS_PROPOSTA where IS_LOCK = 'S' and estimate_by_funcionario_lock =:id_funcionario and NUM_SERIE = '"+numSerie+"'");
				query.setParameter("id_funcionario", this.usuarioBean.getEstimateBy());
				if(query.getResultList().size() == 0){
					return null;
				}
			}

			manager.getTransaction().begin();
			query = manager.createNativeQuery("update ems_proposta set is_lock = 'S', ESTIMATE_BY_FUNCIONARIO_LOCK = '"+this.usuarioBean.getEstimateBy()+"' where id =:id ");
			query.setParameter("id", idProposta);
			query.executeUpdate();
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
	public String enviarProposta(String obs, List<ConsultorBean> bean, Long idTipo, Long idPropostaEms, String origemNegocio){
		EntityManager manager = null;
		Connection con = null;
        PreparedStatement prstmt_ = null;
        String SQL = "";
		try {
			manager = JpaUtil.getInstance();
			
			EmsDetalhesProposta detalhesProposta;
			Query query = manager.createNativeQuery("select * from Ems_Segmento where id_Proposta =:idProposta and (COD_ERRO_DOC_DBS = '100' or COD_ERRO_DOC_DBS = '99')");
			query.setParameter("idProposta", idPropostaEms);
			if(query.getResultList().size() > 0){
				return "Não é possível enviar proposta com pendências de retorno de peças!";
			}
			query = manager.createQuery("From EmsDetalhesProposta where idProposta.id =:idProposta");
			query.setParameter("idProposta", idPropostaEms);
			if(query.getResultList().size() > 0){
				detalhesProposta = (EmsDetalhesProposta)query.getSingleResult();
			}else{
				return "Não é permitido enviar proposta sem os detalhes";
			}
			
			
			
				query = manager.createQuery("from EmsStatusOportunidade where sigla = 'ENV'");
			EmsStatusOportunidade emsStatusOportunidade = (EmsStatusOportunidade)query.getSingleResult();

			query = manager.createQuery("from CrmClassificacao where sigla = 'P'");
			CrmClassificacao crmClassificacao = (CrmClassificacao)query.getSingleResult();

			query = manager.createQuery("from CrmFaseNegociacao where sigla = 'EPCON'");
			CrmFaseNegociacao crmFaseNegociacao = (CrmFaseNegociacao)query.getSingleResult();

			query = manager.createQuery("from CrmTipoProposta where sigla = 'EMSC'");
			CrmTipoProposta crmTipoProposta = (CrmTipoProposta)query.getSingleResult();

			EmsTipoOportunidade emsTipoOportunidade = manager.find(EmsTipoOportunidade.class,idTipo);
			if(emsTipoOportunidade.getSigla().equals("CRM") && (detalhesProposta.getIdProposta().getValorMaoDeObra() == null
					|| detalhesProposta.getIdProposta().getTotalOrcamento() == null || detalhesProposta.getIdProposta().getValorDeslocamento() == null)){
				return "Não é permitido enviar proposta sem o valor de mão de obra ou deslocamento!";
			}
			
			ClienteBusiness business = new ClienteBusiness();
			ClienteBean clienteBean = business.findDataCliente(detalhesProposta.getIdProposta().getCodClienteExt());
			for(ConsultorBean consultor: bean){
				if(consultor.getIsSelected()){
					if(consultor.getMatricula() == null){
						return "Não é possivel enviar proposta pois um dos consultores selcionados não está cadastrado no CRM!";
					}
				}
				
			}
			
			query = manager.createQuery("from EmsSegmento where idProposta.id =:idProposta");
			query.setParameter("idProposta", idPropostaEms);
			List<EmsSegmento> emsSegmentos = query.getResultList();
			
			for (EmsSegmento emsSegmento : emsSegmentos) {
				if(emsSegmento.getJobControl().equals("CA")){
					SQL = "select id from SC_MODELO_FAMILIA where ID_FAMILIA = " + detalhesProposta.getIdProposta().getIdFamiliaCampo() + " and modelo = '" + detalhesProposta.getIdProposta().getModelo() + "'";
					query = manager.createNativeQuery(SQL);
					if(query.getResultList().size() == 0){
						return "Não existe configuração de preço no campo cadastrado para a máquina, favor conferir!";
					}
					BigDecimal idFamiliaModelo = (BigDecimal) query.getSingleResult();
					SQL = "select ID from SC_PREFIXO where ID_MODELO_FAMILIA = "+idFamiliaModelo+" and DESCRICAO = '"+detalhesProposta.getIdProposta().getPrefixo()+"'";
					query = manager.createNativeQuery(SQL);
					BigDecimal idPrefixo = (BigDecimal) query.getSingleResult();
					SQL = "select id from SC_PRECO where ID_MODELO_FAMILIA = " + idFamiliaModelo + " and id_prefixo = " + idPrefixo ;
					query = manager.createNativeQuery(SQL);
					if(query.getResultList().size() == 0){
						return "Não existe configuração de preço no campo cadastrado para a máquina, favor conferir!";
					}else{
						SQL = "select id from SC_PRECO where ID_PREFIXO = "+idPrefixo+" and ID_MODELO_FAMILIA = "+idFamiliaModelo+
						" and JOB_CODE = '"+emsSegmento.getJobCode()+"' and COMP_CODE = '"+emsSegmento.getComCode()+"'";
						if(query.getResultList().size() == 0){
							return "Não existe configuração de preço no campo cadastrado para a máquina, favor conferir!";
						}
					}
				}else {
					SQL = "select ID_ARV from GE_ARV_INSPECAO where DESCRICAO =  '" + detalhesProposta.getIdProposta().getModelo() + "'";
					query = manager.createNativeQuery(SQL);
					if(query.getResultList().size() == 0){
						return "Não existe árvore de inspeção na oficina cadastrada para a máquina, favor conferir!";
					}
					BigDecimal idModelo = (BigDecimal) query.getSingleResult();
					SQL = "select id from GE_PREFIXO where ID_MODELO = " + idModelo + " and descricao = '" + detalhesProposta.getIdProposta().getPrefixo() + "'";
					query = manager.createNativeQuery(SQL);
					if(query.getResultList().size() == 0){
						return "Não existe configuração de preço na oficina cadastrado para a máquina, favor conferir!";
					}else{
						SQL = "select id from GE_PRECO where ID_PREFIXO = "+(BigDecimal)query.getSingleResult()+" and ID_MODELO = "+idModelo+
						" and JOB_CODE = '"+emsSegmento.getJobCode()+"' and COMP_CODE = '"+emsSegmento.getComCode()+"'";
						if(query.getResultList().size() == 0){
							return "Não existe configuração de preço na oficina cadastrado para a máquina, favor conferir!";
						}
					}
				}
			}
			
			String statusOpt = detalhesProposta.getIdProposta().getIdStatusOpt().getSigla();
			manager.getTransaction().begin();
			detalhesProposta.getIdProposta().setIdStatusOpt(emsStatusOportunidade);
			detalhesProposta.getIdProposta().setDataEnvio(new Date());
			detalhesProposta.getIdProposta().setObs(obs);
			detalhesProposta.getIdProposta().setOrigemNegocio(origemNegocio);
			manager.getTransaction().commit();
			
			DiagnosticoBean diagnosticoBean = new DiagnosticoBusiness(null).findallPipPspGarantia(detalhesProposta.getIdProposta().getNumSerie());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String garantia = "";
			if(diagnosticoBean.getFimGarantia() != null){
				Date fimGarantia = dateFormat.parse(diagnosticoBean.getFimGarantia());
				garantia = (fimGarantia.after(new Date())) ? "SIM" : "NÃO";
			}
			
			if(statusOpt.equals("REJ")){
				query = manager.createQuery("from CrmProposta where idEmsProposta =:idEmsProposta");
				query.setParameter("idEmsProposta", detalhesProposta.getIdProposta().getId());
				CrmProposta crmProposta = (CrmProposta)query.getSingleResult();
				
				
				
				String consultorStr = "";
				String emailConsultor = "";
				String epidinoConsultor = "";
				String filialConsultor = "";
				for(ConsultorBean consultor: bean){
					if(consultor.getIsSelected()){
						if("".equals(epidinoConsultor)){
							epidinoConsultor = consultor.getMatricula();
						}
					}

				}
				if(!"".equals(epidinoConsultor)){
					TwFuncionario funcionario = manager.find(TwFuncionario.class, epidinoConsultor);
					consultorStr = funcionario.getEplsnm().toUpperCase();
					emailConsultor = funcionario.getEmail().toUpperCase();
					TwFilial filial = manager.find(TwFilial.class, Long.valueOf(funcionario.getStn1()));
					filialConsultor = filial.getStnm();
//					if(crmProposta.getValorMaoDeObra().doubleValue() == 0  && crmProposta.getValorPecas().doubleValue() <= 3000){
//						consultorStr = "RAFAELA CRISTINA MORAIS DE LIMA";
//						emailConsultor = "lima_rafaela@pesa.com.br".toUpperCase();
//						filialConsultor = "CURITIBA"; 	
//					}
				}

//				for(ConsultorBean consultor: bean){
//					if(consultor.getIsSelected()){
//						new EmailHelper().sendSimpleMail(consultor.getNome()+" gostaríamos de informar que a oportunidade do cliente "+clienteBean.getRAZSOC()+", contato "+detalhesProposta.getContato()+", telefone "+crmProposta.getTelefone()+" foi enviada para você.\n"+ obs, "Envio de Oportunidade", consultor.getEmail());
//					}
//
//				}
				
				
				//envia a proposta para o ZOHO
				con = ConnectionPostgre.getConnecton();
				TwFuncionario analista = manager.find(TwFuncionario.class, detalhesProposta.getIdFuncionario());
				//TwFilial filialConsultor = manager.find(TwFilial.class, Long.valueOf(funcionarioConsultor.getStn1()));
				SQL = "update BELETI_NEGOCIO  set NOME_CLIENTE = '" +detalhesProposta.getAosCuidados().replace("'", "")+"'"+
				"      ,LOCAL = '" +clienteBean.getEND().replace("'", "")+"'"+
				"      ,AOS_CUIDADOS = '" +detalhesProposta.getAosCuidados().replace("'", "")+"'"+
				"      ,TELEFONE = '" +detalhesProposta.getIdProposta().getTelefone()+"'"+
				"      ,EMAIL = '" +detalhesProposta.getEmail()+"'"+
				"      ,OBJETO_PROPOSTA = '" +detalhesProposta.getObjetoProposta()+"'"+
				"      ,MODELO = '" +detalhesProposta.getIdProposta().getModelo()+"'"+
				"      ,SERIE = '" +detalhesProposta.getIdProposta().getNumSerie()+"'"+
				"      ,HORIMETRO = '" +detalhesProposta.getIdProposta().getHorimetro()+"'"+
				"      ,ID_EQUIPAMENTO = '" +detalhesProposta.getIdProposta().getIdEquipamento()+"'"+
				"      ,EQUIP_PERIODO_GARANTIA = '" +garantia+"'"+
				"      ,VALOR_REPARO_APOS_FALHA_INICIAL = " +detalhesProposta.getValorReparoAposFalha()+
				"      ,TOTAL_SERVICOS_INICIAL = " +detalhesProposta.getIdProposta().getValorMaoDeObra()+
				"      ,TOTAL_PECAS_INICIAL = " +detalhesProposta.getIdProposta().getValorPecas()+
				"      ,TOTAL_GERAL_INICIAL = " +detalhesProposta.getIdProposta().getTotalOrcamento().add(detalhesProposta.getIdProposta().getValorDeslocamento())+
				"      ,STATUS = 12" +
				"      ,CONSULTOR = '"+consultorStr+"'"+
				"      ,FILIAL = '"+filialConsultor+"'"+
				"      ,EMAIL_CONSULTOR = '"+emailConsultor+"'"+
				"      ,CPF_CNPJ = '"+clienteBean.getCNPJ()+"'"+
				"      ,data_update_beleti = now()"+
				"      ,analista = '"+analista.getEplsnm()+"'"+
				"      ,origem_negocio = '"+origemNegocio+"'"+
				"      ,email_analista = '"+analista.getEmail()+"'"+
				"  where DOCUMENTO_NUMERO = "+crmProposta.getId();
				//+crmProposta.getId()+")";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.executeUpdate();
				
				return "Proposta enviada para o CRM com sucesso!";
			}
			

			//Setar dados da proposta
			CrmProposta crmProposta = new CrmProposta(idPropostaEms);
			crmProposta.setIdStatusOpt(emsStatusOportunidade.getId());
			crmProposta.setIdClassificacao(crmClassificacao);
			crmProposta.setIdFaseNegociacao(crmFaseNegociacao);
			crmProposta.setIdTipoProposta(crmTipoProposta);

			crmProposta.setNumSerie(detalhesProposta.getIdProposta().getNumSerie());
			//crmProposta.setIdFuncionarioCrm(bean.getMatricula());
			crmProposta.setIdTipoOpt(emsTipoOportunidade);
			//crmProposta.setMatriculaFuncionarioDbs((bean.getMatriculaDbs()));
			crmProposta.setDataOpt(detalhesProposta.getIdProposta().getDataOpt());
			crmProposta.setJobControl(detalhesProposta.getIdProposta().getJobControl());
			crmProposta.setTipoCliente(detalhesProposta.getIdProposta().getTipoCliente());
			crmProposta.setCodClienteExt(detalhesProposta.getIdProposta().getCodClienteExt());
			crmProposta.setCodClienteInter(detalhesProposta.getIdProposta().getCodClienteInter());
			crmProposta.setCodClienteGarantia(detalhesProposta.getIdProposta().getCodClienteGarantia());
			crmProposta.setDataEnvio(detalhesProposta.getIdProposta().getDataEnvio());
			crmProposta.setDataAceite(detalhesProposta.getIdProposta().getDataAceite());
			crmProposta.setDataFinalizacao(detalhesProposta.getIdProposta().getDataFinalizacao());
			crmProposta.setDataRejeicao(detalhesProposta.getIdProposta().getDataRejeicao());
			crmProposta.setFatorCliente(detalhesProposta.getIdProposta().getFatorCliente().longValue());
			crmProposta.setModelo(detalhesProposta.getIdProposta().getModelo());
			crmProposta.setIdFamiliaCampo(detalhesProposta.getIdProposta().getIdFamiliaCampo());
			crmProposta.setPrefixo(detalhesProposta.getIdProposta().getPrefixo());
			crmProposta.setIdFamiliaOficina(detalhesProposta.getIdProposta().getIdFamiliaOficina());
			crmProposta.setPossuiSubTributaria(detalhesProposta.getIdProposta().getPossuiSubTributaria());
			crmProposta.setIsFindSubTributaria(detalhesProposta.getIdProposta().getIsFindSubTributaria());
			crmProposta.setFilial(detalhesProposta.getIdProposta().getFilial());
			crmProposta.setTelefone(detalhesProposta.getIdProposta().getTelefone());
			crmProposta.setObs(obs);
			crmProposta.setFatorUrgencia(detalhesProposta.getIdProposta().getFatorUrgencia());
			crmProposta.setHorimetro(detalhesProposta.getIdProposta().getHorimetro());
			crmProposta.setIsLock(detalhesProposta.getIdProposta().getIsLock());
			crmProposta.setValorMaoDeObra(detalhesProposta.getIdProposta().getValorMaoDeObra());
			crmProposta.setValorPecas(detalhesProposta.getIdProposta().getValorPecas());
			crmProposta.setTotalOrcamento(detalhesProposta.getIdProposta().getTotalOrcamento());
			crmProposta.setValorDeslocamento(detalhesProposta.getIdProposta().getValorDeslocamento());
			manager.getTransaction().begin();
			manager.persist(crmProposta);
			manager.getTransaction().commit();

			

			//Setar dados do detalhe da proposta
			CrmDetalhesProposta crmDetalhesProposta = new CrmDetalhesProposta();
			crmDetalhesProposta.setObjetoProposta(detalhesProposta.getObjetoProposta());
			crmDetalhesProposta.setMaquina(detalhesProposta.getMaquina());
			crmDetalhesProposta.setCondicoesPagamento(detalhesProposta.getCondicoesPagamento());
			crmDetalhesProposta.setValidadeProposta(detalhesProposta.getValidadeProposta());
			crmDetalhesProposta.setAosCuidados(detalhesProposta.getAosCuidados());
			crmDetalhesProposta.setEmail(detalhesProposta.getEmail());
			crmDetalhesProposta.setFormaEntregaProposta(detalhesProposta.getFormaEntregaProposta());
			crmDetalhesProposta.setObservacao(detalhesProposta.getObservacao());
			crmDetalhesProposta.setDataCriacao(detalhesProposta.getDataCriacao());
			crmDetalhesProposta.setIdFuncionario(detalhesProposta.getIdFuncionario());
			crmDetalhesProposta.setPrazoEntrega(detalhesProposta.getPrazoEntrega());
			crmDetalhesProposta.setIdProposta(crmProposta.getId());
			crmDetalhesProposta.setContato(detalhesProposta.getContato());
			crmDetalhesProposta.setValorReparoAposFalha(detalhesProposta.getValorReparoAposFalha());
			crmDetalhesProposta.setDistancia(detalhesProposta.getDistancia());
			crmDetalhesProposta.setPrintPecas("S");
			manager.getTransaction().begin();
			manager.persist(crmDetalhesProposta);
			manager.getTransaction().commit();
			
			for (EmsSegmento emsSegmento : emsSegmentos) {
				
				CrmSegmento crmSegmento = new CrmSegmento();
				crmSegmento.setIdEmsSegmento(emsSegmento.getId());
				crmSegmento.setIdProposta(idPropostaEms);
				crmSegmento.setNumeroSegmento(emsSegmento.getNumeroSegmento());
				crmSegmento.setJobCode(emsSegmento.getJobCode());
				crmSegmento.setJobControl(emsSegmento.getJobControl());
				crmSegmento.setDescricaoJobCode(emsSegmento.getDescricaoJobCode());
				crmSegmento.setComCode(emsSegmento.getComCode());
				crmSegmento.setDescricaoCompCode(emsSegmento.getDescricaoCompCode());
				crmSegmento.setHorasPrevista(emsSegmento.getHorasPrevista());
				crmSegmento.setNumDoc(emsSegmento.getNumDoc());
				crmSegmento.setMsgDocDbs(emsSegmento.getMsgDocDbs());
				crmSegmento.setQtdComp(emsSegmento.getQtdComp());
				crmSegmento.setIdFuncionarioCriador(emsSegmento.getIdFuncionarioCriador());
				crmSegmento.setObservacao(emsSegmento.getObservacao());
				crmSegmento.setIsCreateSegmento(emsSegmento.getIsCreateSegmento());
				crmSegmento.setDataCriacao(emsSegmento.getDataCriacao());
				crmSegmento.setIdProposta(crmProposta.getId());
				crmSegmento.setValorPecas(emsSegmento.getValorPecas());
				manager.getTransaction().begin();
				manager.persist(crmSegmento);
				manager.getTransaction().commit();
				query = manager.createQuery("from EmsSegmentoServTerceiros where emsSegmentoServTerceirosPK.idEmsSegmento =:idEmsSegmento");
				query.setParameter("idEmsSegmento", emsSegmento.getId());
				List<EmsSegmentoServTerceiros> emsSegmentoServTerceiros = query.getResultList();
				for (EmsSegmentoServTerceiros segmentoServTerceiros : emsSegmentoServTerceiros) {
					
					CrmSegmentoServTerceiros terceiros = new CrmSegmentoServTerceiros(crmSegmento.getId(), segmentoServTerceiros.getEmsSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
					terceiros.setIdEmsSegmento(emsSegmento.getId());
					terceiros.setData(segmentoServTerceiros.getData());
					terceiros.setValor(segmentoServTerceiros.getValor());
					terceiros.setQtd(segmentoServTerceiros.getQtd());
					terceiros.setIdFornServTerceiros(segmentoServTerceiros.getIdFornServTerceiros());
					terceiros.setLocalServico(segmentoServTerceiros.getLocalServico());
					terceiros.setObs(segmentoServTerceiros.getObs());
					manager.getTransaction().begin();
					manager.persist(terceiros);
					manager.getTransaction().commit();
				}

				query = manager.createQuery("from EmsPecas where idEmsSegmento.id =:idSegmento");
				query.setParameter("idSegmento", emsSegmento.getId());
				List<EmsPecas> emsPecasList = query.getResultList();
				manager.getTransaction().begin();
				for (EmsPecas emsPecas : emsPecasList) {
					CrmPecas pecas = new CrmPecas();
					pecas.setCrmSegmento(crmSegmento);
					pecas.setPartNo(emsPecas.getPartNo());
					pecas.setPartName(emsPecas.getPartName());
					pecas.setQtd(emsPecas.getQtd());
					pecas.setGroupNumber(emsPecas.getGroupNumber());
					pecas.setGroupName(emsPecas.getGroupName());
					pecas.setReferenceNo(emsPecas.getReferenceNo());
					pecas.setSmcsCode(emsPecas.getSmcsCode());
					pecas.setUserId(emsPecas.getUserId());
					pecas.setValor(emsPecas.getValor());
					pecas.setValorTotal(emsPecas.getValorTotal());
					pecas.setSos1(emsPecas.getSos1());
					pecas.setQtdNaoAtendido(emsPecas.getQtdNaoAtendido());
					pecas.setIpi(emsPecas.getIpi());
					pecas.setIcmsub(emsPecas.getIcmsub());
					pecas.setTotaltributos(emsPecas.getTotaltributos());
					pecas.setUnsel(emsPecas.getUnsel());
					manager.persist(pecas);					
				}
				manager.getTransaction().commit();	
			}
			CrmSituacaoProposta situacaoProposta = new CrmSituacaoProposta();
			situacaoProposta.setDataEnvioConsultor(new Date());
			situacaoProposta.setIdEmsProposta(detalhesProposta.getIdProposta().getId());
			situacaoProposta.setIdProposta(crmProposta);
			manager.getTransaction().begin();
			//detalhesProposta.getIdProposta().setIsLock(null);
			query = manager.createNativeQuery("update EMS_PROPOSTA	set IS_LOCK = null	where NUM_SERIE = '"+detalhesProposta.getIdProposta().getNumSerie()+"'");
			query.executeUpdate();
			manager.merge(detalhesProposta);
			manager.persist(situacaoProposta);
			manager.getTransaction().commit();
			String consultorStr = "";
			String emailConsultor = "";
			String epidinoConsultor = "";
			String filialConsultor = "";
			for(ConsultorBean consultor: bean){
				if(consultor.getIsSelected()){
					CrmUsuarioProposta usuarioProposta = new CrmUsuarioProposta();
					usuarioProposta.setIdProposta(crmProposta.getId());
					usuarioProposta.setIdTwFuncionario(consultor.getMatricula());
					if("".equals(epidinoConsultor)){
						epidinoConsultor = consultor.getMatricula();
					}
					//new EmailHelper().sendSimpleMail(consultor.getNome()+" gostaríamos de informar que a oportunidade do cliente "+clienteBean.getRAZSOC()+", contato "+detalhesProposta.getContato()+", telefone "+crmProposta.getTelefone()+" foi enviada para você.\n"+ obs, "Envio de Oportunidade", consultor.getEmail());
					manager.getTransaction().begin();
					manager.persist(usuarioProposta);
					manager.getTransaction().commit();
				}

			}
			if(!"".equals(epidinoConsultor)){
				TwFuncionario funcionario = manager.find(TwFuncionario.class, epidinoConsultor);
				consultorStr = funcionario.getEplsnm().toUpperCase();
				emailConsultor = funcionario.getEmail().toUpperCase();
				TwFilial filial = manager.find(TwFilial.class, Long.valueOf(funcionario.getStn1()));
				filialConsultor = filial.getStnm();
//				if(crmProposta.getValorMaoDeObra().doubleValue() == 0  && crmProposta.getValorPecas().doubleValue() <= 3000){
//					consultorStr = "RAFAELA CRISTINA MORAIS DE LIMA";
//					emailConsultor = "lima_rafaela@pesa.com.br".toUpperCase();
//					filialConsultor = "CURITIBA"; 	
//				}
				manager.getTransaction().begin();
				crmDetalhesProposta.setConsultor(consultorStr);
				crmDetalhesProposta.setEmailConsultor(emailConsultor);
				manager.merge(crmDetalhesProposta);
				manager.getTransaction().commit();
			}

//			for(ConsultorBean consultor: bean){
//				if(consultor.getIsSelected()){
//					new EmailHelper().sendSimpleMail(consultor.getNome()+" gostaríamos de informar que a oportunidade do cliente "+clienteBean.getRAZSOC()+", contato "+detalhesProposta.getContato()+", telefone "+crmProposta.getTelefone()+" foi enviada para você.\n"+ obs, "Envio de Oportunidade", consultor.getEmail());
//				}
//
//			}
			
			//montagem da URL
			String URL = "";
			 if(detalhesProposta.getIdProposta().getIdTipoOpt().getSigla().equals("CRM")){
		            if(crmProposta.getIdTipoProposta().getSigla().equals("EMSC")){
		            	URL = "http://oficina.pesa.com.br:8080/crmservicos/PropostaCampo?idProposta="+crmProposta.getId();
		            } else  if(crmProposta.getIdTipoProposta().getSigla().equals("C")){
		                //return this.imprimirRelatorioCampo();
		            	URL = "http://oficina.pesa.com.br:8080/crmservicos/PropostaCampo?idProposta="+crmProposta.getId();
		            } else  if(crmProposta.getIdTipoProposta().getSigla().equals("EMSO")){
		            	URL = "http://oficina.pesa.com.br:8080/crmservicos/PropostaOficina?idProposta="+crmProposta.getId();
		            } else  if(crmProposta.getIdTipoProposta().getSigla().equals("O")){
		                 //return this.imprimirRelatorioOficina();
		            	URL = "http://oficina.pesa.com.br:8080/crmservicos/PropostaOficina?idProposta="+crmProposta.getId();
		            }
		        } else if(detalhesProposta.getIdProposta().getIdTipoOpt().getSigla().equals("RECCON")){
		        	URL = "http://oficina.pesa.com.br:8080/crmservicos/PropostaRecomendacao?idProposta="+crmProposta.getId();
		        }
			//envia a proposta para o ZOHO
			con = ConnectionPostgre.getConnecton();
			TwFuncionario analista = manager.find(TwFuncionario.class, detalhesProposta.getIdFuncionario());
			//TwFilial filialConsultor = manager.find(TwFilial.class, Long.valueOf(funcionarioConsultor.getStn1()));
			SQL = "insert into BELETI_NEGOCIO (DOCUMENTO_NUMERO\n" +
			"      ,NOME_CLIENTE\n" +
			"      ,LOCAL\n" +
			"      ,AOS_CUIDADOS\n" +
			"      ,TELEFONE\n" +
			"      ,EMAIL\n" +
			"      ,OBJETO_PROPOSTA\n" +
			"      ,MODELO\n" +
			"      ,SERIE\n" +
			"      ,HORIMETRO\n" +
			"      ,ID_EQUIPAMENTO\n" +
			"      ,EQUIP_PERIODO_GARANTIA\n" +
			"      ,VALOR_REPARO_APOS_FALHA_INICIAL\n" +
			"      ,TOTAL_SERVICOS_INICIAL\n" +
			"      ,TOTAL_PECAS_INICIAL\n" +
			"      ,TOTAL_GERAL_INICIAL\n" +
			//"      ,VALOR_REPARO_APOS_FALHA\n" +
			//"      ,TOTAL_SERVICOS\n" +
			//"      ,TOTAL_PECAS\n" +
			//"      ,TOTAL_GERAL\n" +
			"      ,STATUS\n" +
			"      ,CONSULTOR\n"+
			"      ,FILIAL\n"+
			"      ,EMAIL_CONSULTOR\n"+
			//"      ,MOTIVO_VENDA_PERDIDA\n"+
			"      ,CPF_CNPJ\n"+
			"      ,data_update_beleti\n"+
			"      ,url_proposta\n"+
			"      ,analista\n"+
			"      ,origem_negocio\n"+
			"      ,email_analista)\n"+
			//"	   ,id_negocio_crm)\n" +
			" values ("+crmProposta.getId()+",'"
			+crmDetalhesProposta.getAosCuidados().replace("'", "") +"','"
			+ clienteBean.getEND()+"','"
			+ crmDetalhesProposta.getAosCuidados().replace("'", "")+"','"
			+ crmProposta.getTelefone()+"','"
			+ crmDetalhesProposta.getEmail()+"','"
			+ crmDetalhesProposta.getObjetoProposta()+"','"
			+ crmProposta.getModelo()+"','"
			+ crmProposta.getNumSerie()+"',"
			+ crmProposta.getHorimetro()+",'"
			+ crmProposta.getIdEquipamento()+"','"
			+garantia+"',"
			//+ crmDetalhesProposta.getValorReparoAposFalha()+","
			//+ emsProposta.getValorMaoDeObra()+","
			//+ emsProposta.getValorPecas()+","
			//+ emsProposta.getTotalOrcamento().add(emsProposta.getValorDeslocamento())+","
			+ crmDetalhesProposta.getValorReparoAposFalha()+","
			+ crmProposta.getValorMaoDeObra()+","
			+ crmProposta.getValorPecas()+","
			+ crmProposta.getTotalOrcamento().add(crmProposta.getValorDeslocamento())+","
			+ 3+",'"//significa que a proposta foi enviada para o consultor
			+ consultorStr+"','"
			+ filialConsultor+"','"
			+ emailConsultor+"','"
			//+ motivoPerda+"','"
			+ clienteBean.getCNPJ()+"',"
			+ "now(),"
			+ "'"+URL+"'," +
			" '"+analista.getEplsnm()+"'," +
			" '"+origemNegocio+"'," +
			"'"+analista.getEmail()+"')";
			//+crmProposta.getId()+")";
			prstmt_ = con.prepareStatement(SQL);
			prstmt_.executeUpdate();

			return "Proposta enviada para o CRM com sucesso!";
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			new EmailHelper().sendSimpleMail(SQL, "Erro ao salvar na tabela do CRM", "rodrigo@rdrsistemas.com.br");
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(con != null){
				try {
					con.close();
					//prstmt_.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "Erro ao tentar enviar a proposta para o CRM!";
	}

	public Boolean removerProposta(PropostaBean bean){
		EntityManager manager = null;
		try {

			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("select * From EMS_SEGMENTO where id_proposta ="+bean.getId()+"");
			if(query.getResultList().size() > 0){
				List<Object[]> emsSegmento = query.getResultList();
				for (Object[] objects : emsSegmento) {
					SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
					business.removerSegmento(((BigDecimal)objects[0]).longValue()); 
				}
			}

			EmsProposta proposta = manager.find(EmsProposta.class, bean.getId());
			manager.remove(proposta);
			query = manager.createNativeQuery("update EMS_PROPOSTA	set IS_LOCK = null	where NUM_SERIE = '"+proposta.getNumSerie()+"'");
			query.executeUpdate();
			manager.getTransaction().commit();
			
			DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
			business.saveDiagnostic(-1L, proposta.getNumSerie());
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
	public String verificarSerieClonada(String serie, Long idProposta){
		EntityManager manager = null;
		try {

			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select convert(varchar(4000), smu.TIPO_SERVICO) from EMS_SEGMENTO seg, EMS_SMU smu, EMS_PROPOSTA p"+
						"	where  smu.ID_SEGMENTO = seg.ID"+
						"	and (smu.TIPO_SERVICO = 'Motor e Transmissão' or smu.TIPO_SERVICO = 'Motor' or smu.TIPO_SERVICO = 'Material Rodante' or smu.TIPO_SERVICO = 'Kit Tombamento')"+
						"	and p.ID = seg.ID_PROPOSTA"+
						"	and p.ID = "+idProposta);
			List<String> tipoServico = (List<String>)query.getResultList();
			String tipoServicoSmu = "";
			for (String tps : tipoServico) {
				query = manager.createNativeQuery("select * from EMS_SMU where NUM_SERIE = '"+serie+"' and ID_SEGMENTO is null and TIPO_SERVICO = '"+tps+"'");
				if(query.getResultList().size() == 0){
					tipoServicoSmu += tps+",";
				}
			}
			if(tipoServicoSmu.length() > 0){
				tipoServicoSmu = tipoServicoSmu.substring(0, tipoServicoSmu.length() -1);
				return "A série não possui a oportunidade disponível "+tipoServicoSmu;
			}
			return "";
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
		return "Erro ao tentar validar a série do equipamento";
	}
	public String validarSerieMotorTransmissao(String serie, Long idProposta){
		EntityManager manager = null;
		try {
			
			manager = JpaUtil.getInstance();
			//Query query = manager.createNativeQuery("select * from EMS_SMU where NUM_SERIE = '"+serie+"' and ID_SEGMENTO is null and (TIPO_SERVICO = 'Motor e Transmissão' or TIPO_SERVICO = 'Motor')");
			
			Query query = manager.createNativeQuery("select smu.TIPO_SERVICO from EMS_SEGMENTO seg, EMS_SMU smu, EMS_PROPOSTA p"+
					"	where  smu.ID_SEGMENTO = seg.ID"+
					"	and (smu.TIPO_SERVICO = 'Motor e Transmissão' or smu.TIPO_SERVICO = 'Motor' or  smu.TIPO_SERVICO = 'Material Rodante' or  smu.TIPO_SERVICO = 'Kit Tombamento')"+
					"	and p.ID = seg.ID_PROPOSTA" +
					"   and smu.NUM_SERIE = '"+serie+"'"+
					"	and p.ID = "+idProposta);
			
			if(query.getResultList().size() == 0){
				return "A série não possui o tipo de serviço Motor e Transmissão ou Motor.";
			}
			return "";
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
		return "Erro ao tentar validar a série do equipamento";
	}
	
	public String clonarPropostaSerieMotorTransmissao(List<String> series, Long idProposta, Long idTipoOportunidade){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "";
			SegmentoBusiness segBusiness = new SegmentoBusiness(usuarioBean);
			ImportXmlBusiness importXmlBusiness = new ImportXmlBusiness(usuarioBean);
			for (String numSerie : series) {
				SQL = "select distinct MODELO, filial, HORIMETRO from EMS_DIAGNOSTICO_VIEW where NUMERO_SERIE = '"+numSerie+"'";
				Query query = manager.createNativeQuery(SQL);
				Object [] pair = (Object [])query.getSingleResult();
				PropostaBean propostaBean = this.salvarProposta(idTipoOportunidade, numSerie, (String)pair[0], ((BigDecimal)pair[1]).toString());
				this.liberarEquipamento(numSerie);
				//Aos cuidados, contato, telefone, e-mail.
				
				String razaoSocial = null;
				String contato = null;
				String telefone = null;
				String email = null;
				
				SQL = "select RAZAO_SOCIAL, CONTATO_COMERCIAL, TELEFONE_COMERCIAL, EMAIL_CONTATO_COMERCIAL  from PMP_CONTRATO where NUMERO_SERIE = '"+numSerie+"' order by id desc";
				query = manager.createNativeQuery(SQL);
				if(query.getResultList().size() > 0){
					Object [] parametro = (Object[])query.getResultList().get(0);
					razaoSocial = (String)parametro[0];
					contato = (String)parametro[1];
					telefone = (String)parametro[2];
					email = (String)parametro[3];
				}else {
					SQL = "select RAZAO_SOCIAL, CONTATO, TELEFONE, EMAIL  from SC_CHAMADO where SERIE = '"+numSerie+"' order by id desc";
					query = manager.createNativeQuery(SQL);
					if(query.getResultList().size() > 0){
						Object [] parametro = (Object[])query.getResultList().get(0);
						razaoSocial = (String)parametro[0];
						contato = (String)parametro[1];
						telefone = (String)parametro[2];
						email = (String)parametro[3];
					}
				}
				
				
				if(propostaBean == null){
					return "Erro ao tentar salvar a proposta para a máquina de série "+numSerie+" e modelo "+(String)pair[0];
				}
				
				SQL = "insert into EMS_DETALHES_PROPOSTA (OBJETO_PROPOSTA"+
				" ,MAQUINA"+
				"  ,CONDICOES_PAGAMENTO"+
				"  ,VALIDADE_PROPOSTA"+
				"  ,AOS_CUIDADOS"+
				"  ,EMAIL"+
				"  ,FORMA_ENTREGA_PROPOSTA"+
				"  ,OBSERVACAO"+
				"  ,DATA_CRIACAO"+
				"  ,ID_FUNCIONARIO"+
				"  ,PRAZO_ENTREGA"+
				"  ,ID_PROPOSTA"+
				"  ,CONTATO"+
				"  ,VALOR_REPARO_APOS_FALHA"+
				"  ,DISTANCIA)"+
				" SELECT "+
				"  OBJETO_PROPOSTA"+
				"  ,MAQUINA"+
				"  ,CONDICOES_PAGAMENTO"+
				"  ,VALIDADE_PROPOSTA"+
				"  ,AOS_CUIDADOS"+
				"  ,EMAIL"+
				"  ,FORMA_ENTREGA_PROPOSTA"+
				"  ,OBSERVACAO"+
				"  ,DATA_CRIACAO"+
				"  ,ID_FUNCIONARIO"+
				"  ,PRAZO_ENTREGA"+
				"  ,"+propostaBean.getId()+
				"  ,CONTATO"+
				"  ,VALOR_REPARO_APOS_FALHA"+
				"  ,DISTANCIA"+
				" FROM EMS_DETALHES_PROPOSTA"+
				" where ID_PROPOSTA = "+idProposta;
				manager.getTransaction().begin();
				query = manager.createNativeQuery(SQL);
				query.executeUpdate();
				manager.getTransaction().commit();
				
				manager.getTransaction().begin();
				query = manager.createQuery("from EmsDetalhesProposta where idProposta.id =:idProposta");
				query.setParameter("idProposta", propostaBean.getId());
				EmsDetalhesProposta emsDetalhesProposta = (EmsDetalhesProposta)query.getSingleResult();
				
				emsDetalhesProposta.setAosCuidados(razaoSocial);
				emsDetalhesProposta.setContato(contato);
				emsDetalhesProposta.setEmail(email);
				emsDetalhesProposta.getIdProposta().setTelefone(telefone);
				manager.merge(emsDetalhesProposta);
				manager.getTransaction().commit();
				
				List<SegmentoBean> segmentos = segBusiness.findAllSegmento(idProposta);
				for (SegmentoBean segmentoBean : segmentos) {
					segmentoBean.setListSmuAssociado(new ArrayList<SmuBean>());

					//Buscar os smus
					query = manager.createNativeQuery("select convert(varchar(4000), smu.TIPO_SERVICO) from EMS_SEGMENTO seg, EMS_SMU smu, EMS_PROPOSTA p"+
							"	where  smu.ID_SEGMENTO = seg.ID"+
							"	and (smu.TIPO_SERVICO = 'Motor e Transmissão' or smu.TIPO_SERVICO = 'Motor' or  smu.TIPO_SERVICO = 'Material Rodante' or smu.TIPO_SERVICO = 'Kit Tombamento')"+
							"	and p.ID = seg.ID_PROPOSTA"+
							"	and seg.ID = "+segmentoBean.getId()+
							"	and p.ID = "+idProposta);
					List<String> tipoServico = (List<String>)query.getResultList();
					//List<SmuBean> smuList = new ArrayList<SmuBean>(); 
					for (String tps : tipoServico) {
						query = manager.createNativeQuery("select id from EMS_SMU where NUM_SERIE = '"+numSerie+"' and ID_SEGMENTO is null and TIPO_SERVICO = '"+tps+"'");
						if(query.getResultList().size() > 0){
							SmuBean smuBean = new SmuBean();
							smuBean.setId(((BigDecimal)query.getSingleResult()).longValue());
							segmentoBean.getListSmuAssociado().add(smuBean);
						}
					}
					Long idSegmento = segmentoBean.getId();
					segmentoBean.setId(null);
					SegmentoBean segNovo =  segBusiness.saveOrUpdateSegmento(segmentoBean, numSerie, propostaBean.getId(), ((pair[2] != null)?((BigDecimal)pair[2]).longValue():0));
					List<PecasBean> listPecas = importXmlBusiness.findAllPecas(idSegmento);//peças do segmento a ser clonado
					importXmlBusiness.savePecas(listPecas, segNovo.getId());
					importXmlBusiness.saveOrcamento(propostaBean.getId());
					
				}
//				DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
//				business.saveDiagnostic(-1L, numSerie);
				SQL = "select distinct d.NUMERO_SERIE, (select COUNT(*) from EMS_SMU where IS_REJEITADO is null and ID_SEGMENTO is null and NUM_SERIE = d.NUMERO_SERIE) SMU from EMS_DIAGNOSTICO_VIEW d  where d.NUMERO_SERIE = '"+numSerie+"'";
				query = manager.createNativeQuery(SQL);
				if(query.getResultList().size() > 0){
					Object [] p = (Object [])query.getSingleResult();
					if(Long.valueOf((Integer)p[1]) > 0){
						SQL = "update EMS_DIAGNOSTICO_VIEW set TOTAL_SMU = "+(Integer)p[1]+", COR_SMU = 'red' where NUMERO_SERIE = '"+numSerie+"'";
						manager.getTransaction().begin();
						query = manager.createNativeQuery(SQL);
						query.executeUpdate();
						manager.getTransaction().commit();
					}else{
						SQL = "update EMS_DIAGNOSTICO_VIEW set TOTAL_SMU = 0, COR_SMU = 'green' where NUMERO_SERIE = '"+numSerie+"'";
						manager.getTransaction().begin();
						query = manager.createNativeQuery(SQL);
						query.executeUpdate();
						manager.getTransaction().commit();
					}
				}
			}
			
			
			return "";
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
		return "Erro ao tentar validar a série do equipamento";
	}
	
	public List<OrigemNegocioBean> findAllOrigemNegocio(){
		EntityManager manager = null;
		List<OrigemNegocioBean>  og = new ArrayList<OrigemNegocioBean>();
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsOrigemNegocio");
			List<EmsOrigemNegocio> negocioList = (List<EmsOrigemNegocio>)query.getResultList();
			for (EmsOrigemNegocio emsOrigemNegocio : negocioList) {
				OrigemNegocioBean bean = new OrigemNegocioBean();
				bean.setId(emsOrigemNegocio.getId());
				bean.setDescricao(emsOrigemNegocio.getDescricao());
				bean.setSigla(emsOrigemNegocio.getSigla());
				og.add(bean);
			}
		}catch (Exception e) {
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
		return og;
	}

}
