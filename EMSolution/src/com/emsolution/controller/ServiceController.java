package com.emsolution.controller;

import java.util.Collection;
import java.util.List;

import com.emsolution.bean.ArqSmuBean;
import com.emsolution.bean.ArqSosBean;
import com.emsolution.bean.CadastrarFmiBean;
import com.emsolution.bean.CodigoClienteBean;
import com.emsolution.bean.ComponenteCodeBean;
import com.emsolution.bean.ConfigAlarmeBean;
import com.emsolution.bean.ConsultorBean;
import com.emsolution.bean.ConsumoCombustivelBean;
import com.emsolution.bean.DetalhesPropostaBean;
import com.emsolution.bean.DiagnosticoBean;
import com.emsolution.bean.DocumentoPecasBean;
import com.emsolution.bean.FamiliaBean;
import com.emsolution.bean.FilialBean;
import com.emsolution.bean.FornecedorServicoTerceirosBean;
import com.emsolution.bean.HistoricoOSBean;
import com.emsolution.bean.InspecaoPmpBean;
import com.emsolution.bean.InspecaoPmpTreeBean;
import com.emsolution.bean.JobCodeBean;
import com.emsolution.bean.JobControlBean;
import com.emsolution.bean.LoginClienteBean;
import com.emsolution.bean.OportunidadePmpBean;
import com.emsolution.bean.OrigemNegocioBean;
import com.emsolution.bean.PecasBean;
import com.emsolution.bean.PropostaBean;
import com.emsolution.bean.SegmentoBean;
import com.emsolution.bean.ServicoTerceirosBean;
import com.emsolution.bean.SmuBean;
import com.emsolution.bean.SosBean;
import com.emsolution.bean.StatusOportunidadeBean;
import com.emsolution.bean.TipoAlarmeBean;
import com.emsolution.bean.TipoOportunidadeBean;
import com.emsolution.bean.TipoServicoBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.business.CadastrarFmiBusiness;
import com.emsolution.business.CodigoBusiness;
import com.emsolution.business.ConsultorBusiness;
import com.emsolution.business.ConsumoCombustivelBusiness;
import com.emsolution.business.DiagnosticoBusiness;
import com.emsolution.business.ImportXmlBusiness;
import com.emsolution.business.InvokePmpBusiness;
import com.emsolution.business.LoginClienteBusiness;
import com.emsolution.business.PmpBusiness;
import com.emsolution.business.PropostaBusiness;
import com.emsolution.business.SegmentoBusiness;
import com.emsolution.business.SmuBusiness;
import com.emsolution.business.SosBusiness;
import com.emsolution.business.TipoServicoBusiness;
import com.emsolution.business.UsuarioBusiness;
import com.emsolution.util.ExceptionLogin;

import flex.messaging.FlexContext;

public class ServiceController {
	private UsuarioBean usuarioBean;

	public ServiceController() throws Exception {
		usuarioBean = (UsuarioBean) FlexContext.getFlexSession().getAttribute("usuario");
	}
	
	
	public String getUrlLogintServer() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
				.concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
				":").concat(
				String.valueOf(FlexContext.getHttpRequest().getServerPort()))
				.concat("/ControlPanelPesa");
		 return url;
	}
	public String getUrlReport() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
   				.concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
   				":").concat(
   				String.valueOf(FlexContext.getHttpRequest().getServerPort()))
   				.concat("/EMSolution");
                                           		
		return url;
	}
	public String getUrlReportPMPInspecao() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
				.concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
						":").concat(
								String.valueOf(FlexContext.getHttpRequest().getServerPort()))
								.concat("/Pmp");
		
		return url;
	}
	public String getUrlReportCampoInspecao() throws Exception {
		String url = FlexContext.getHttpRequest().getProtocol().split("/")[0]
		                                                                   .concat("://").concat(FlexContext.getHttpRequest().getServerName()).concat(
		                                                                   ":").concat(
		                                                                		   String.valueOf(FlexContext.getHttpRequest().getServerPort()))
		                                                                		   .concat("/CampoPesa");
		// url = "http://192.168.1.21:8080/CampoPesa";
		
		return url;
	}

	public  UsuarioBean verificarLogin() throws Exception{
		validarUsuario();
		return usuarioBean;
	}
	
	private void validarUsuario() throws Exception {
		try {
			if (usuarioBean == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			ExceptionLogin exception = new ExceptionLogin("false");
			throw exception;
		}
	}
	public List<TipoAlarmeBean> findAllTipoAlarme()throws Exception{
		validarUsuario();
		CodigoBusiness business = new CodigoBusiness(usuarioBean);
		return business.findAllTipoAlarme();
	}
	public Boolean removerConfigAlarme(ConfigAlarmeBean bean)throws Exception{
		validarUsuario();
		CodigoBusiness business = new CodigoBusiness(this.usuarioBean);
		return business.removerCodigoAlarme(bean);
	}						
	public ConfigAlarmeBean saveOrUpdateCodigoAlarme(ConfigAlarmeBean bean)throws Exception{
		validarUsuario();
		CodigoBusiness business = new CodigoBusiness(this.usuarioBean);
		return business.saveOrUpdateConfigAlarmeBean(bean);	
	}
	public List<ConfigAlarmeBean> findAllConfigAlarme(Long idTipoAlarme)throws Exception{
		validarUsuario();
		CodigoBusiness business = new CodigoBusiness(this.usuarioBean);
		return business.findAllConfigAlarme(idTipoAlarme);	
	}
	 public Boolean fazerUploadSosTxt(byte[] bytes, String tipoAnalise, String nomeArquivo) throws Exception {
		 validarUsuario();
		 SosBusiness business = new SosBusiness(usuarioBean);
		 return business.fazerUploadSosTxt(bytes, tipoAnalise, nomeArquivo);
	 }
	 public List<DiagnosticoBean> findDiagnostic(Long filial, String campo, Long inicial, Long numRegistros) throws Exception {
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 //return business.findDiagnostic(filial, campo, inicial, numRegistros);
		 return business.findDiagnosticFisico(filial, campo, inicial, numRegistros);
	 }
	 public Collection<FilialBean> findAllFiliais() throws Exception {
			validarUsuario();
			UsuarioBusiness business = new UsuarioBusiness();
			return business.findAllFilial();
	 }
	 public List<ConfigAlarmeBean> findConfigAlarme(Long nivel, String serie) throws Exception{
		 validarUsuario();
		 CodigoBusiness business = new CodigoBusiness(this.usuarioBean);
		 return business.findConfigAlarme(nivel, serie);
	 }
	 
	 public InspecaoPmpBean findInspecaoPmp(Long idOsPalm) throws Exception{
		 validarUsuario();
		 InvokePmpBusiness bussines = new InvokePmpBusiness();
		 return bussines.findInspecaoPmp(idOsPalm);
	 }
	 public InspecaoPmpBean findInspecaoCampo(Long idOsPalm) throws Exception{
		 validarUsuario();
		 InvokePmpBusiness bussines = new InvokePmpBusiness();
		 return bussines.findInspecaoCampo(idOsPalm);
	 }
	 public List<InspecaoPmpTreeBean> findAllInspencaoPmpTree(Long idOsPalm) throws Exception{
		 validarUsuario();
		 InvokePmpBusiness bussines = new InvokePmpBusiness();
		 return bussines.findAllInspencaoPmpTree(idOsPalm);
	 }
	 public List<InspecaoPmpTreeBean> findAllInspencaoCampoTree(Long idOsPalm) throws Exception{
		 validarUsuario();
		 InvokePmpBusiness bussines = new InvokePmpBusiness();
		 return bussines.findAllInspencaoCampoTree(idOsPalm);
	 }
	 public List<Integer> findAllFotos(Integer idOsPalmDt) throws Exception{
		 validarUsuario();
		 InvokePmpBusiness business = new InvokePmpBusiness();
		 return business.findAllFotos(idOsPalmDt);
	 }
	 public List<SosBean> findAllSos(String serie, String nivel) throws Exception{
		 validarUsuario();
		 SosBusiness business = new SosBusiness(usuarioBean);
		 return business.findAllSos(serie , nivel);
	 }
	 public List<LoginClienteBean> findAllLoginCliente() throws Exception{
		 validarUsuario();
		 LoginClienteBusiness business = new LoginClienteBusiness(usuarioBean);
		 return business.findAllLoginCliente();
	 }
	 public LoginClienteBean saveOrUpdateLoginCliente(LoginClienteBean bean)throws Exception{
		 validarUsuario();
		 LoginClienteBusiness business = new LoginClienteBusiness(this.usuarioBean);
		 return business.saveOrUpdateLoginCliente(bean);		 
	 } 

	 public DiagnosticoBean findallPipPspGarantia(String serie) throws Exception{
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findallPipPspGarantia(serie);
	 } 
	 public Boolean salvarCodigoCliente(CodigoClienteBean bean) throws Exception{
		 validarUsuario();
		 CodigoBusiness business = new CodigoBusiness(usuarioBean);
		 return business.salvarCodigoCliente(bean);
	 } 
	 public List<CodigoClienteBean> findAllCodigoCliente(LoginClienteBean bean) throws Exception{
		 validarUsuario();
		 CodigoBusiness business = new CodigoBusiness(usuarioBean);
		 return business.findAllCodigoCliente(bean);
	 } 
	 public Boolean removerCodigoCliente(CodigoClienteBean bean) throws Exception{
		 validarUsuario();
		 CodigoBusiness business = new CodigoBusiness(usuarioBean);
		 return business.removerCodigoCliente(bean);
	 } 
	 public List<TipoOportunidadeBean> findAllTipoOportunidade() throws Exception{
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findAllTipoOportunidade();
	 } 
	 public List<StatusOportunidadeBean> findAllStatusOportunidade() throws Exception{
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findAllStatusOportunidade();
	 } 
	 public Collection<JobControlBean> findAllJobControl() throws Exception {
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findAllJobControl();
	 }
	 public Collection<ComponenteCodeBean> findAllCompCode(String caracter) throws Exception {
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findAllCompCode(caracter);
	 }
	 public Collection<JobCodeBean> findAllJobCode() throws Exception {
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findAllJobCode();
	 }
	 public PropostaBean salvarProposta (Long idTipoOportunidade, String numSerie, String modelo, String filial) throws Exception{
		 validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(usuarioBean);
		 return business.salvarProposta(idTipoOportunidade, numSerie, modelo, filial);
	 }
	 
	 public List<SegmentoBean> findAllSegmento(Long idProposta) throws Exception{
		 validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.findAllSegmento(idProposta);
	 }

	 public String findHoras (String prefixo, String jobCode, String compCode, String modelo, Long idFamilia) throws Exception{
		 validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.findHorasCampo(prefixo, jobCode, compCode, modelo, idFamilia);
	 }
	 public Collection<JobCodeBean> findAllJobCode(String caracter) throws Exception {
		 validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(usuarioBean);
		 return business.findAllJobCode(caracter);
	 }
	 public List<OportunidadePmpBean> findAllOportunidadePmp(Long idOsPalmDt) throws Exception {
		 validarUsuario();
		 PmpBusiness business = new PmpBusiness();
		 return business.findAllOportunidadePmp(idOsPalmDt);
	 }
	 public List<OportunidadePmpBean> findAllOportunidadeCampo(Long idOsPalmDt) throws Exception {
		 validarUsuario();
		 PmpBusiness business = new PmpBusiness();
		 return business.findAllOportunidadeCampo(idOsPalmDt);
	 }
	 public Boolean removerCadastrarFmi(CadastrarFmiBean bean)throws Exception{
		 validarUsuario();
		 CadastrarFmiBusiness business = new CadastrarFmiBusiness(this.usuarioBean);
		 return business.removerCadastrarFmi(bean);
	 }

	 public List<CadastrarFmiBean> findAllCadastrarFmi() throws Exception{
		 validarUsuario();
		 CadastrarFmiBusiness business = new CadastrarFmiBusiness(usuarioBean);
		 return business.findAllCadastrarFmi();
	 }
	 public Boolean removerLoginCliente(LoginClienteBean bean)throws Exception{
		 validarUsuario();
		 LoginClienteBusiness business = new LoginClienteBusiness(this.usuarioBean);
		 return business.removerLoginCliente(bean);
	 }
	 public SegmentoBean saveOrUpdateSegmento (SegmentoBean bean, String numSerie, Long idProposta, Long horimetro)throws Exception{
		 validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.saveOrUpdateSegmento(bean, numSerie, idProposta, horimetro);
	 }
	 public Boolean removerSegmento(Long idSegmento) throws Exception{
		 validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.removerSegmento(idSegmento);
	 }
	 public List<SosBean> findAllSosAssociado(Long idSegmento) throws Exception{
		 validarUsuario();
		 SosBusiness business = new SosBusiness(this.usuarioBean);
		 return business.findAllSosAssociado(idSegmento);
	 }
	 
	 public List<ConfigAlarmeBean> findAllPlAssociado(Long idSegmento) throws Exception{
		 validarUsuario();
		 CodigoBusiness business = new CodigoBusiness(this.usuarioBean);
		 return business.findAllPlAssociado(idSegmento);
	 }
	 public List<OportunidadePmpBean> findAllPmpAssociado(Long idSegmento) throws Exception{
		 validarUsuario();
		 PmpBusiness business = new PmpBusiness();
		 return business.findAllPmpAssociado(idSegmento);
	 }
	 public List<OportunidadePmpBean> findAllCampoAssociado(Long idSegmento) throws Exception{
		 validarUsuario();
		 PmpBusiness business = new PmpBusiness();
		 return business.findAllCampoAssociado(idSegmento);
	 }
	 public String savePecasAvulso (PecasBean bean, Long idSegmento) throws Exception{
		 validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness();
		 return business.savePecasAvulso(bean, idSegmento);
	 }
	 public Long fazerUpload(byte[] bytesArquivo, Long idSegmento) throws Exception {
		 this.validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness(this.usuarioBean);
		 synchronized (business) {
			 return business.salvarXml(bytesArquivo, idSegmento);
		 }
	 }
	 public String saveOrcamento(Long idProposta) throws Exception{
		 this.validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness(usuarioBean);
		 return business.saveOrcamento(idProposta);
	 }
	 public String saveOrcamentoSegmento(Long idSegmento) throws Exception{
		 this.validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness(usuarioBean);
		 return business.saveOrcamentoSegmento(idSegmento);
	 }
	 public List<PecasBean> findAllPecas(Long idSegmento) throws Exception{
		 this.validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness(usuarioBean);
		 return business.findAllPecas(idSegmento);
	 }
	 public Boolean removerPecas(Long idSegmento) throws Exception{
		 this.validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness(usuarioBean);
		 return business.removerPecas(idSegmento);
	 }
	 public String removerPeca(PecasBean bean) throws Exception{
		 this.validarUsuario();
		 ImportXmlBusiness business = new ImportXmlBusiness(usuarioBean);
		 return business.removerPeca(bean);
	 }
	 public List<PropostaBean> findAllProposta(String numSerie) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(usuarioBean);
		 return business.findAllProposta(numSerie);
	 }
	 public List<PropostaBean> findAllPropostaOrcamento(String numSerie) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(usuarioBean);
		 return business.findAllPropostaOrcamento(numSerie);
	 }
	 
	 public List<DocumentoPecasBean> verificaPecas (Long idProposta)throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.verificaPecas(idProposta);
	 }
	 public SegmentoBean findSegmentoBy(Long idSegmento) throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.findSegmentoBy(idSegmento);
	 }
	 public DetalhesPropostaBean findDetalhesProposta(PropostaBean propostaBean) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.findDetalhesProposta(propostaBean);
	 }
	 public DetalhesPropostaBean saveOrUpdateDetalhesProposta(Long idProposta, DetalhesPropostaBean detalhesPropostaBean) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.saveOrUpdateDetalhesProposta(idProposta, detalhesPropostaBean);
	 }
	 public Boolean verificaPendeciasPecas (Long idProposta) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.verificaPendeciasPecas(idProposta);
	 }

	 public List<FornecedorServicoTerceirosBean> findAllFornecedoresAssociados(Long idTipoServTerceiros) throws Exception{
		 validarUsuario();
		 TipoServicoBusiness business = new TipoServicoBusiness();
		 return business.findAllFornecedoresAssociados(idTipoServTerceiros);
	 }
	 public List<ServicoTerceirosBean> saveServicoTerceiros(ServicoTerceirosBean bean, Long idProposta, String observacao) throws Exception{
		 validarUsuario();
		 TipoServicoBusiness business = new TipoServicoBusiness();
		 return business.saveServicoTerceiros(bean, idProposta, observacao);
	 } 
	 public List<TipoServicoBean> findAllServicoTerceiros() throws Exception{
		 validarUsuario();
		 TipoServicoBusiness business = new TipoServicoBusiness();
		 return business.findAllServicoTerceiros();
	 }
	 public List<ServicoTerceirosBean> findAllServicoTerceirosAssociado(Long idSegmento) throws Exception{
		 validarUsuario();
		 TipoServicoBusiness business = new TipoServicoBusiness();
		 return business.findAllServicoTerceirosAssociado(idSegmento);
	 }
	 public Boolean remover(ServicoTerceirosBean bean) throws Exception{
		 validarUsuario();
		 TipoServicoBusiness business = new TipoServicoBusiness();
		 return business.remover(bean);
	 }
	 public List<FamiliaBean> findAllFamilia() throws Exception{
		 validarUsuario();
		 ConsumoCombustivelBusiness business = new ConsumoCombustivelBusiness();
		 return business.findAllFamilia();
	 }
	 public ConsumoCombustivelBean saveOrUpdateConsumo(ConsumoCombustivelBean bean) throws Exception{
		 this.validarUsuario();
		 ConsumoCombustivelBusiness business = new ConsumoCombustivelBusiness();
		 return business.saveOrUpdateConsumo(bean);
	 }
	 public Boolean removerConsumo(ConsumoCombustivelBean bean) throws Exception{
		 validarUsuario();
		 ConsumoCombustivelBusiness business = new ConsumoCombustivelBusiness();
		 return business.removerConsumo(bean);
     }
	 public List<ConsumoCombustivelBean> findAllConsumo() throws Exception{
		 validarUsuario();
		 ConsumoCombustivelBusiness business = new ConsumoCombustivelBusiness();
		 return business.findAllConsumo();
	 }
	 public List<HistoricoOSBean> findAllHistoricoOS(String serie)throws Exception{
		 validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.findAllHistoricoOS(serie);
	 }
	 public Boolean liberarEquipamento(String numSerie)throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.liberarEquipamento(numSerie);
	 }
	 public Boolean alocarEquipamento(Long idProposta, String numSerie)throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.alocarEquipamento(idProposta, numSerie);
	 }
	 public List<ConsultorBean> findAllConsultor(String codCliente) throws Exception{
		 this.validarUsuario();
		 ConsultorBusiness business = new ConsultorBusiness(this.usuarioBean);
		 return business.findAllConsultor(codCliente);
	 }
	 public String enviarProposta(String obs, List<ConsultorBean> bean, Long idTipo, Long idProposta, String origemNegocio) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.enviarProposta(obs, bean, idTipo, idProposta, origemNegocio);
	 }
	 
	 public void changeUser(Long idFilial){
		 usuarioBean = (UsuarioBean) FlexContext.getFlexSession().getAttribute("usuario");
		 usuarioBean.setFilial(idFilial.toString());
		 FlexContext.getFlexSession().setAttribute("usuario", usuarioBean);
	 }
	 public DiagnosticoBean findHorimetroDataAtualizacao(DiagnosticoBean bean) throws Exception{
		 this.validarUsuario();
		 DiagnosticoBusiness business = new DiagnosticoBusiness(this.usuarioBean);
		 return business.findHorimetroDataAtualizacao(bean);
	 }
	 public Boolean removerProposta(PropostaBean bean) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.removerProposta(bean);
	 }
	 public Boolean saveRejeicaoSegmento(Long idOsPalmDt, String obs, String numeroSerie) throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.saveRejeicaoSegmento(idOsPalmDt,obs, numeroSerie);
	 }
	 public Boolean saveRejeicaoCampoSegmento(Long idOsPalmDt, String obs, String numeroSerie) throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.saveRejeicaoCampoSegmento(idOsPalmDt,obs, numeroSerie);
	 }
	 public boolean rejeitarAlarmePl(ConfigAlarmeBean alarmeBean, String numSerie, String obs) throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.rejeitarAlarmePl(alarmeBean, numSerie, obs);
	 }
	 public boolean rejeitarOportunidadeSos(String textId, String obs) throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.rejeitarOportunidadeSos(textId, obs);
	 }
	 public List<ArqSosBean> findAllArquivoSos() throws Exception{
		 this.validarUsuario();
		 SosBusiness business = new SosBusiness(this.usuarioBean);
		 return business.findAllArquivoSos();
	 }
	 public List<ArqSmuBean> findAllArquivoSmu () throws Exception{
		 this.validarUsuario();
		 SmuBusiness business = new SmuBusiness(this.usuarioBean);
		 return business.findAllArquivoSmu();
	 }
	 public Boolean fazerUploadSmuXls(byte[] bytes, String nomeArquivo) throws Exception{
		 this.validarUsuario();
		 SmuBusiness business = new SmuBusiness(this.usuarioBean);
		 return business.fazerUploadSmuXls(bytes, nomeArquivo);
	 }
	 public List<SmuBean> findAllSmu(String serie)  throws Exception{
		 this.validarUsuario();
		 SmuBusiness business = new SmuBusiness(this.usuarioBean);
		 return business.findAllSmu(serie);
	 }
	 public Boolean rejeitarOportunidadeSmu(Long id, String obs)  throws Exception{
		 this.validarUsuario();
		 SegmentoBusiness business = new SegmentoBusiness(this.usuarioBean);
		 return business.rejeitarOportunidadeSmu(id, obs);
	 }
	 public List<SmuBean> findAllSmuAssociado(Long idSegmento) throws Exception{
		 this.validarUsuario();
		 SmuBusiness business = new SmuBusiness(this.usuarioBean);
		 return business.findAllSmuAssociado(idSegmento);
	 }
	 public String verificarSerieClonada(String serie, Long idProposta) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.verificarSerieClonada(serie, idProposta);
	 }
	 public String validarSerieMotorTransmissao(String serie, Long idProposta) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.validarSerieMotorTransmissao(serie, idProposta);
	 }
	 public String clonarPropostaSerieMotorTransmissao(List<String> series, Long idProposta, Long idTipoOportunidade) throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.clonarPropostaSerieMotorTransmissao(series, idProposta, idTipoOportunidade);
	 }
	 public List<OrigemNegocioBean> findAllOrigemNegocio() throws Exception{
		 this.validarUsuario();
		 PropostaBusiness business = new PropostaBusiness(this.usuarioBean);
		 return business.findAllOrigemNegocio();
	 }

}
