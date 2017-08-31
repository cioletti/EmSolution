package com.emsolution.bean;

import java.text.SimpleDateFormat;

import com.emsolution.entity.EmsProposta;

public class PropostaBean {
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private Long id;
	private String nomeFuncionario;
	private String epdino;
	private String idEquipamento;
	private String numSerie;
	private Long fatorCliente;
	private String tipoCiente;
	private String codigoCiente;
	private Long idTipoOportunidade;
	private String tipoOportunidade;
	private String statusOportunidade;
	private Long idStatusOportunidade;
	private String modelo;
	private Long idFamilia;
	private String prefixo;
	private String siglaStatusOprtunidade;
	private String siglaTipoOprtunidade;
	private String dataOportunidade;
	private Long filial;
	private String obs;
	private String isAdm;
	private String idFuncionarioLock;
	private String observacao;
	private String filialStr;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public Long getIdTipoOportunidade() {
		return idTipoOportunidade;
	}
	public void setIdTipoOportunidade(Long idTipoOportunidade) {
		this.idTipoOportunidade = idTipoOportunidade;
	}
	public String getEpdino() {
		return epdino;
	}
	public void setEpdino(String epdino) {
		this.epdino = epdino;
	}
	public String getIdEquipamento() {
		return idEquipamento;
	}
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public Long getFatorCliente() {
		return fatorCliente;
	}
	public void setFatorCliente(Long fatorCliente) {
		this.fatorCliente = fatorCliente;
	}
	public String getTipoCiente() {
		return tipoCiente;
	}
	public void setTipoCiente(String tipoCiente) {
		this.tipoCiente = tipoCiente;
	}
	public String getCodigoCiente() {
		return codigoCiente;
	}
	public void setCodigoCiente(String codigoCiente) {
		this.codigoCiente = codigoCiente;
	}
	public Long getIdStatusOportunidade() {
		return idStatusOportunidade;
	}
	public void setIdStatusOportunidade(Long idStatusOportunidade) {
		this.idStatusOportunidade = idStatusOportunidade;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Long getIdFamilia() {
		return idFamilia;
	}
	public void setIdFamilia(Long idFamilia) {
		this.idFamilia = idFamilia;
	}
	public String getPrefixo() {
		return prefixo;
	}
	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}
	public String getSiglaStatusOprtunidade() {
		return siglaStatusOprtunidade;
	}
	public void setSiglaStatusOprtunidade(String siglaStatusOprtunidade) {
		this.siglaStatusOprtunidade = siglaStatusOprtunidade;
	}
	public String getSiglaTipoOprtunidade() {
		return siglaTipoOprtunidade;
	}
	public void setSiglaTipoOprtunidade(String siglaTipoOprtunidade) {
		this.siglaTipoOprtunidade = siglaTipoOprtunidade;
	}
	
	public String getDataOportunidade() {
		return dataOportunidade;
	}
	public void setDataOportunidade(String dataOportunidade) {
		this.dataOportunidade = dataOportunidade;
	}
	public Long getFilial() {
		return filial;
	}
	public void setFilial(Long filial) {
		this.filial = filial;
	}
	public String getTipoOportunidade() {
		return tipoOportunidade;
	}
	public void setTipoOportunidade(String tipoOportunidade) {
		this.tipoOportunidade = tipoOportunidade;
	}
	public String getStatusOportunidade() {
		return statusOportunidade;
	}
	public void setStatusOportunidade(String statusOportunidade) {
		this.statusOportunidade = statusOportunidade;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getIsAdm() {
		return isAdm;
	}
	public void setIsAdm(String isAdm) {
		this.isAdm = isAdm;
	}
	public String getIdFuncionarioLock() {
		return idFuncionarioLock;
	}
	public void setIdFuncionarioLock(String idFuncionarioLock) {
		this.idFuncionarioLock = idFuncionarioLock;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getFilialStr() {
		return filialStr;
	}
	public void setFilialStr(String filialStr) {
		this.filialStr = filialStr;
	}
	public void fromBean(EmsProposta emsProposta){
		setId(emsProposta.getId());
		setEpdino(emsProposta.getIdFuncionario());
		setIdStatusOportunidade(emsProposta.getIdStatusOpt().getId());
		setSiglaStatusOprtunidade(emsProposta.getIdStatusOpt().getSigla());
		setIdTipoOportunidade(emsProposta.getIdTipoOpt().getId());
		setSiglaTipoOprtunidade(emsProposta.getIdTipoOpt().getSigla());
		setNumSerie(emsProposta.getNumSerie());
		setTipoCiente(emsProposta.getTipoCliente());
		setPrefixo(emsProposta.getPrefixo());
		setModelo(emsProposta.getModelo());
		setIdFamilia(emsProposta.getIdFamiliaCampo().longValue());
		setDataOportunidade(df.format(emsProposta.getDataOpt()));
		setFilial(emsProposta.getFilial());
		setTipoOportunidade(emsProposta.getIdTipoOpt().getDescricao());
		setStatusOportunidade(emsProposta.getIdStatusOpt().getDescricao());
		setObs(emsProposta.getObs());
		setIdFuncionarioLock(emsProposta.getEstimateByFuncionarioLock());
		setCodigoCiente(emsProposta.getCodClienteExt());
		setObservacao(emsProposta.getMotivoRejeicao());
	}
}
