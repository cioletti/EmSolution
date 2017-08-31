package com.emsolution.bean;


public class CodigoClienteBean {
	private Long id;
	private Long idLoginCliente;
	private String codigo;
	private String loginCliente;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public Long getIdLoginCliente() {
		return idLoginCliente;
	}
	public void setIdLoginCliente(Long idLoginCliente) {
		this.idLoginCliente = idLoginCliente;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getLoginCliente() {
		return loginCliente;
	}
	public void setLoginCliente(String loginCliente) {
		this.loginCliente = loginCliente;
	}


}
