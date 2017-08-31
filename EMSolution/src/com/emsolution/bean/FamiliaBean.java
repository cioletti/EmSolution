package com.emsolution.bean;

public class FamiliaBean {
	private Long id;
	private String descricao;
	private Long idConsumoCombustivel;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdConsumoCombustivel() {
		return idConsumoCombustivel;
	}

	public void setIdConsumoCombustivel(Long idConsumoCombustivel) {
		this.idConsumoCombustivel = idConsumoCombustivel;
	}
}
