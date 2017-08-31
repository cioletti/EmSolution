package com.emsolution.bean;

import java.io.Serializable;

import com.emsolution.entity.TwFilial;

public class CrmSegmentoServicoTerceiroBean implements Serializable {

	private static final long serialVersionUID = 2155358431269549665L;
	private Long idCrmSegmento;
	private Long idEmsSegmento;
	private Long idTipoServicoTerceiro;
	private String data;
	private Double valor;
	private Long qtd;
	private Long idOper;
	private String obs;
	private Long idFornServTerceiro;
	private Long idStatusServTerceiro;
	private String localServico;
	
	public Long getIdCrmSegmento() {
		return idCrmSegmento;
	}
	public void setIdCrmSegmento(Long idCrmSegmento) {
		this.idCrmSegmento = idCrmSegmento;
	}
	public Long getIdEmsSegmento() {
		return idEmsSegmento;
	}
	public void setIdEmsSegmento(Long idEmsSegmento) {
		this.idEmsSegmento = idEmsSegmento;
	}
	public Long getIdTipoServicoTerceiro() {
		return idTipoServicoTerceiro;
	}
	public void setIdTipoServicoTerceiro(Long idTipoServicoTerceiro) {
		this.idTipoServicoTerceiro = idTipoServicoTerceiro;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Long getQtd() {
		return qtd;
	}
	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}
	public Long getIdOper() {
		return idOper;
	}
	public void setIdOper(Long idOper) {
		this.idOper = idOper;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Long getIdFornServTerceiro() {
		return idFornServTerceiro;
	}
	public void setIdFornServTerceiro(Long idFornServTerceiro) {
		this.idFornServTerceiro = idFornServTerceiro;
	}
	public Long getIdStatusServTerceiro() {
		return idStatusServTerceiro;
	}
	public void setIdStatusServTerceiro(Long idStatusServTerceiro) {
		this.idStatusServTerceiro = idStatusServTerceiro;
	}
	public String getLocalServico() {
		return localServico;
	}
	public void setLocalServico(String localServico) {
		this.localServico = localServico;
	}
	
}
