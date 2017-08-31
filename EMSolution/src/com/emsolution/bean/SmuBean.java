package com.emsolution.bean;

import java.io.Serializable;

public class SmuBean implements Serializable  {
	
    private Long id;
    private Long horimetroAnterior;
    private Long horimetroProximo;
    private String tipoServico;
    private String data;
    private String observacao;
    private String modelo;
    private String serie;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHorimetroAnterior() {
		return horimetroAnterior;
	}
	public void setHorimetroAnterior(Long horimetroAnterior) {
		this.horimetroAnterior = horimetroAnterior;
	}
	public Long getHorimetroProximo() {
		return horimetroProximo;
	}
	public void setHorimetroProximo(Long horimetroProximo) {
		this.horimetroProximo = horimetroProximo;
	}
	public String getTipoServico() {
		return tipoServico;
	}
	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
   
}
