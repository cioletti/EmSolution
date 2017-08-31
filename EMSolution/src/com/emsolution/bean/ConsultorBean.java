package com.emsolution.bean;

import java.io.Serializable;



public class ConsultorBean implements Serializable {
	private static final long serialVersionUID = 1684541854272705930L;
	private String matriculaDbs;
	private String nome;
	private String email;
	private Boolean isSelected;
	private String matricula;
	private Integer filial;
	
	public Integer getFilial() {
		return filial;
	}
	public void setFilial(Integer filial) {
		this.filial = filial;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMatriculaDbs() {
		return matriculaDbs;
	}
	public void setMatriculaDbs(String matriculaDbs) {
		this.matriculaDbs = matriculaDbs;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
