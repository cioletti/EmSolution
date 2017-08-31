package com.emsolution.bean;

import java.io.Serializable;

public class CrmPropostaBean implements Serializable {

	private static final long serialVersionUID = 2155358431269549665L;
	private Long id;
	private Long idEmsProposta;
	private String numSerie;
	private Long idStatusOpt;
	private Long tipoOpt;
	private String dataOpt;
	private String matriculaFuncionario;
	private String jobControl;
	private String tipoCliente;
	private String codClienteExt;
	private String codClienteInter;
	private String codClienteGarantia;
	private String idEquipamento;
	private String dataEnvio;
	private String dataAceite;
	private String dataFinalizacao;
	private String dataRejeicao;
	private Long fatorCliente;
	private String modelo;
	private Long idFamiliaCampo;
	private String prefixo;
	private Long idFamiliaOficina;
	private String possuiSubTributaria;
	private String isFindSubTributaria;
	private Long filial;
	private String telefone;
	private String obs;
	private String fatorUrgencia;
	private Long horimetro;
	private String isLock;
	private String idFuncionarioLock;
	private String estimateByFuncionarioLock;
	private Long idClassificacao;
	private Long idFaseNegociacao;
	private Long idMotivoPerda;
	private Long valorMaoDeObra;
	private Long valorPecas;
	private Long totalOrcamento;
	private Long totalDeslocamento;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdEmsProposta() {
		return idEmsProposta;
	}
	public void setIdEmsProposta(Long idEmsProposta) {
		this.idEmsProposta = idEmsProposta;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public Long getIdStatusOpt() {
		return idStatusOpt;
	}
	public void setIdStatusOpt(Long idStatusOpt) {
		this.idStatusOpt = idStatusOpt;
	}
	public Long getTipoOpt() {
		return tipoOpt;
	}
	public void setTipoOpt(Long tipoOpt) {
		this.tipoOpt = tipoOpt;
	}
	public String getDataOpt() {
		return dataOpt;
	}
	public void setDataOpt(String dataOpt) {
		this.dataOpt = dataOpt;
	}
	public String getMatriculaFuncionario() {
		return matriculaFuncionario;
	}
	public void setMatriculaFuncionario(String matriculaFuncionario) {
		this.matriculaFuncionario = matriculaFuncionario;
	}
	public String getJobContrl() {
		return jobControl;
	}
	public void setJobContrl(String jobControl) {
		this.jobControl = jobControl;
	}
	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public String getCodClienteExt() {
		return codClienteExt;
	}
	public void setCodClienteExt(String codClienteExt) {
		this.codClienteExt = codClienteExt;
	}
	public String getCodClienteInter() {
		return codClienteInter;
	}
	public void setCodClienteInter(String codClienteInter) {
		this.codClienteInter = codClienteInter;
	}
	public String getCodClienteGarantia() {
		return codClienteGarantia;
	}
	public void setCodClienteGarantia(String codClienteGarantia) {
		this.codClienteGarantia = codClienteGarantia;
	}
	public String getIdEquipamento() {
		return idEquipamento;
	}
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}
	public String getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public String getDataAceite() {
		return dataAceite;
	}
	public void setDataAceite(String dataAceite) {
		this.dataAceite = dataAceite;
	}
	public String getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(String dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	public String getDataRejeicao() {
		return dataRejeicao;
	}
	public void setDataRejeicao(String dataRejeicao) {
		this.dataRejeicao = dataRejeicao;
	}
	public Long getFatorCliente() {
		return fatorCliente;
	}
	public void setFatorCliente(Long fatorCliente) {
		this.fatorCliente = fatorCliente;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Long getIdFamiliaCampo() {
		return idFamiliaCampo;
	}
	public void setIdFamiliaCampo(Long idFamiliaCampo) {
		this.idFamiliaCampo = idFamiliaCampo;
	}
	public String getPrefixo() {
		return prefixo;
	}
	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}
	public Long getIdFamiliaOficina() {
		return idFamiliaOficina;
	}
	public void setIdFamiliaOficina(Long idFamiliaOficina) {
		this.idFamiliaOficina = idFamiliaOficina;
	}
	public String getPossuiSubTributaria() {
		return possuiSubTributaria;
	}
	public void setPossuiSubTributaria(String possuiSubTributaria) {
		this.possuiSubTributaria = possuiSubTributaria;
	}
	public String getIsFindSubTributaria() {
		return isFindSubTributaria;
	}
	public void setIsFindSubTributaria(String isFindSubTributaria) {
		this.isFindSubTributaria = isFindSubTributaria;
	}
	public Long getFilial() {
		return filial;
	}
	public void setFilial(Long filial) {
		this.filial = filial;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getFatorUrgencia() {
		return fatorUrgencia;
	}
	public void setFatorUrgencia(String fatorUrgencia) {
		this.fatorUrgencia = fatorUrgencia;
	}
	public Long getHorimetro() {
		return horimetro;
	}
	public void setHorimetro(Long horimetro) {
		this.horimetro = horimetro;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getIdFuncionarioLock() {
		return idFuncionarioLock;
	}
	public void setIdFuncionarioLock(String idFuncionarioLock) {
		this.idFuncionarioLock = idFuncionarioLock;
	}
	public String getEstimateByFuncionarioLock() {
		return estimateByFuncionarioLock;
	}
	public void setEstimateByFuncionarioLock(String estimateByFuncionarioLock) {
		this.estimateByFuncionarioLock = estimateByFuncionarioLock;
	}
	public Long getIdClassificacao() {
		return idClassificacao;
	}
	public void setIdClassificacao(Long idClassificacao) {
		this.idClassificacao = idClassificacao;
	}
	public Long getIdFaseNegociacao() {
		return idFaseNegociacao;
	}
	public void setIdFaseNegociacao(Long idFaseNegociacao) {
		this.idFaseNegociacao = idFaseNegociacao;
	}
	public Long getIdMotivoPerda() {
		return idMotivoPerda;
	}
	public void setIdMotivoPerda(Long idMotivoPerda) {
		this.idMotivoPerda = idMotivoPerda;
	}
	public Long getValorMaoDeObra() {
		return valorMaoDeObra;
	}
	public void setValorMaoDeObra(Long valorMaoDeObra) {
		this.valorMaoDeObra = valorMaoDeObra;
	}
	public Long getValorPecas() {
		return valorPecas;
	}
	public void setValorPecas(Long valorPecas) {
		this.valorPecas = valorPecas;
	}
	public Long getTotalOrcamento() {
		return totalOrcamento;
	}
	public void setTotalOrcamento(Long totalOrcamento) {
		this.totalOrcamento = totalOrcamento;
	}
	public Long getTotalDeslocamento() {
		return totalDeslocamento;
	}
	public void setTotalDeslocamento(Long totalDeslocamento) {
		this.totalDeslocamento = totalDeslocamento;
	}
	
	

	
	
	
	
	
	
	
	
	
	

}
