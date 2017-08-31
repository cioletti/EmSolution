package com.emsolution.bean;

import java.math.BigDecimal;

import com.emsolution.entity.EmsCodigoAlarme;

public class ConfigAlarmeBean {
	private Long id;
	private Long idTipoAlarme;
	private String codigo;
	private String eventoDiagnostico;
	private String compartimento;
	private String possiveisCausas;
	private String indicadoresSos;
	private String pmTa;
	private String acoesRecomendadas;
	private String descTipoAlarme;
	private BigDecimal codigoFmi;
	private BigDecimal codigoCid;
	private BigDecimal codigoEid;
	private String codigoFmiStr;
	private String codigoCidStr;
	private String codigoEidStr;
	private BigDecimal totalOcorrencias;
	private String DescFmi;
	private Long nivel;
	
		public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdTipoAlarme() {
		return idTipoAlarme;
	}
	public void setIdTipoAlarme(Long idTipoAlarme) {
		this.idTipoAlarme = idTipoAlarme;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getEventoDiagnostico() {
		return eventoDiagnostico;
	}
	public void setEventoDiagnostico(String eventoDiagnostico) {
		this.eventoDiagnostico = eventoDiagnostico;
	}
	public String getCompartimento() {
		return compartimento;
	}
	public void setCompartimento(String compartimento) {
		this.compartimento = compartimento;
	}
	public String getPossiveisCausas() {
		return possiveisCausas;
	}
	public void setPossiveisCausas(String possiveisCausas) {
		this.possiveisCausas = possiveisCausas;
	}
	public String getIndicadoresSos() {
		return indicadoresSos;
	}
	public void setIndicadoresSos(String indicadoresSos) {
		this.indicadoresSos = indicadoresSos;
	}
	public String getPmTa() {
		return pmTa;
	}
	public void setPmTa(String pmTa) {
		this.pmTa = pmTa;
	}
	public String getAcoesRecomendadas() {
		return acoesRecomendadas;
	}
	public void setAcoesRecomendadas(String acoesRecomendadas) {
		this.acoesRecomendadas = acoesRecomendadas;
	}
	public String getDescTipoAlarme() {
		return descTipoAlarme;
	}
	public void setDescTipoAlarme(String descTipoAlarme) {
		this.descTipoAlarme = descTipoAlarme;
	}
	public BigDecimal getCodigoFmi() {
		return codigoFmi;
	}
	public void setCodigoFmi(BigDecimal codigoFmi) {
		this.codigoFmi = codigoFmi;
	}
	public BigDecimal getCodigoCid() {
		return codigoCid;
	}
	public void setCodigoCid(BigDecimal codigoCid) {
		this.codigoCid = codigoCid;
	}
	public BigDecimal getCodigoEid() {
		return codigoEid;
	}
	public void setCodigoEid(BigDecimal codigoEid) {
		this.codigoEid = codigoEid;
	}
	public Long getNivel() {
		return nivel;
	}
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}
	public BigDecimal getTotalOcorrencias() {
		return totalOcorrencias;
	}
	public void setTotalOcorrencias(BigDecimal totalOcorrencias) {
		this.totalOcorrencias = totalOcorrencias;
	}
	public String getDescFmi() {
		return DescFmi;
	}
	public void setDescFmi(String descFmi) {
		DescFmi = descFmi;
	}
	public String getCodigoFmiStr() {
		return codigoFmiStr;
	}
	public void setCodigoFmiStr(String codigoFmiStr) {
		this.codigoFmiStr = codigoFmiStr;
	}
	public String getCodigoCidStr() {
		return codigoCidStr;
	}
	public void setCodigoCidStr(String codigoCidStr) {
		this.codigoCidStr = codigoCidStr;
	}
	public String getCodigoEidStr() {
		return codigoEidStr;
	}
	public void setCodigoEidStr(String codigoEidStr) {
		this.codigoEidStr = codigoEidStr;
	}
	public void toBean(EmsCodigoAlarme entity){
		entity.setId(getId());
		entity.setDescricao(getCodigo());
		entity.setEventoDiagnostico(getEventoDiagnostico());
		entity.setCompartimento(getCompartimento());
		entity.setPossiveisCausas(getPossiveisCausas());
		entity.setIndicadoresSos(getIndicadoresSos());
	    entity.setPmTa(getPmTa());
	    entity.setAcoesRecomendadas(getAcoesRecomendadas());
	    entity.setCodigoCid(getCodigoCid());
	    entity.setCodigoFmi(getCodigoFmi());
	    entity.setCodigoEid(getCodigoEid());
	}
	public void fromBean(EmsCodigoAlarme entity){
		setId(entity.getId());
		setCodigo(entity.getDescricao());
		setEventoDiagnostico(entity.getEventoDiagnostico());
		setCompartimento(entity.getCompartimento());
		setPossiveisCausas(entity.getPossiveisCausas());
		setIndicadoresSos(entity.getIndicadoresSos());
	    setPmTa(entity.getPmTa());
	    setAcoesRecomendadas(entity.getAcoesRecomendadas());
	    setIdTipoAlarme(entity.getIdTipoAlarme().getId());
	    setDescTipoAlarme(entity.getIdTipoAlarme().getDescricao());
	    setCodigoCid(entity.getCodigoCid());
	    setCodigoFmi(entity.getCodigoFmi());
	    setCodigoEid(entity.getCodigoEid());
	    if(entity.getIdTipoAlarme().getDescricao().equals("MID - EID")){
	    	setCodigoCidStr(null);
	    	setCodigoFmiStr(null);
	    	setCodigoEidStr(entity.getCodigoEid().toString());
	    }else{
	    	setCodigoCidStr(entity.getCodigoCid().toString());
	    	setCodigoFmiStr(entity.getCodigoFmi().toString());
	    	setCodigoEidStr(null);
	    	
	    }
	}
	
}
