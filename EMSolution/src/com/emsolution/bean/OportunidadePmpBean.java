package com.emsolution.bean;

public class OportunidadePmpBean {
	private Long idOsPalmDt;
	private String descricao;
	private String obs;
	private String obsCabecalho;
	private String tipoManutencao;
	public Long getIdOsPalmDt() {
		return idOsPalmDt;
	}
	public void setIdOsPalmDt(Long idOsPalmDt) {
		this.idOsPalmDt = idOsPalmDt;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getTipoManutencao() {
		return tipoManutencao;
	}
	public void setTipoManutencao(String tipoManutencao) {
		this.tipoManutencao = tipoManutencao;
	}
	public String getObsCabecalho() {
		return obsCabecalho;
	}
	public void setObsCabecalho(String obsCabecalho) {
		this.obsCabecalho = obsCabecalho;
	}
}
