package com.emsolution.bean;

import java.io.Serializable;

import com.emsolution.entity.TwFilial;

public class CrmDetalhesPropostaBean implements Serializable {

	private static final long serialVersionUID = 2155358431269549665L;
	private Long id;
	private String objetoProposta;
	private String maquina;
	private String condicoesPagamento;
	private String validadeProposta;
	private String email;
	private String formaEntregaProposta;
	private String observacao;
	private String dataCriacao;
	private Long idFuncionario;
	private Long prazoEntrega;
	private Long idProposta;
	private String contato;
	private Long valorReparoAposFalha;
	private Long distancia;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObjetoProposta() {
		return objetoProposta;
	}
	public void setObjetoProposta(String objetoProposta) {
		this.objetoProposta = objetoProposta;
	}
	public String getMaquina() {
		return maquina;
	}
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}
	public String getCondicoesPagamento() {
		return condicoesPagamento;
	}
	public void setCondicoesPagamento(String condicoesPagamento) {
		this.condicoesPagamento = condicoesPagamento;
	}
	public String getValidadeProposta() {
		return validadeProposta;
	}
	public void setValidadeProposta(String validadeProposta) {
		this.validadeProposta = validadeProposta;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFormaEntregaProposta() {
		return formaEntregaProposta;
	}
	public void setFormaEntregaProposta(String formaEntregaProposta) {
		this.formaEntregaProposta = formaEntregaProposta;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Long getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public Long getPrazoEntrega() {
		return prazoEntrega;
	}
	public void setPrazoEntrega(Long prazoEntrega) {
		this.prazoEntrega = prazoEntrega;
	}
	public Long getIdProposta() {
		return idProposta;
	}
	public void setIdProposta(Long idProposta) {
		this.idProposta = idProposta;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public Long getValorReparoAposFalha() {
		return valorReparoAposFalha;
	}
	public void setValorReparoAposFalha(Long valorReparoAposFalha) {
		this.valorReparoAposFalha = valorReparoAposFalha;
	}
	public Long getDistancia() {
		return distancia;
	}
	public void setDistancia(Long distancia) {
		this.distancia = distancia;
	}
	

	
	
	
	
	


}
