package com.emsolution.bean;

import javax.persistence.Column;

import com.emsolution.entity.EmsPecas;
import com.emsolution.entity.EmsPecasLog;


public class PecasBean {
	
	private String partNo;
	private String partName;
	private Long id;
	private String qtd;
	private String groupNumber;
	private String referenceNo;
	private String smcsCode;
	private String groupName;
	private String userId;
	private String serialNo;
	private String sos;
	private Long idEmsSegmento;
    private String coderr;
    private String descerr;
	
	public String getCoderr() {
		return coderr;
	}
	public void setCoderr(String coderr) {
		this.coderr = coderr;
	}
	public String getDescerr() {
		return descerr;
	}
	public void setDescerr(String descerr) {
		this.descerr = descerr;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQtd() {
		return qtd;
	}
	public void setQtd(String qtd) {
		this.qtd = qtd;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getSmcsCode() {
		return smcsCode;
	}
	public void setSmcsCode(String smcsCode) {
		this.smcsCode = smcsCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getSos() {
		return sos;
	}
	public void setSos(String sos) {
		this.sos = sos;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public Long getIdEmsSegmento() {
		return idEmsSegmento;
	}
	public void setIdEmsSegmento(Long idEmsSegmento) {
		this.idEmsSegmento = idEmsSegmento;
	}
	public void toBean(EmsPecas entity){
		entity.setPartNo(getPartNo());
		entity.setPartName(getPartName());
		entity.setId(entity.getId());
		entity.setQtd(Long.valueOf(getQtd()));
		entity.setGroupNumber(getGroupNumber());
		entity.setReferenceNo(getReferenceNo());
		entity.setSmcsCode(getSmcsCode());
		entity.setGroupName(getGroupName());
		entity.setUserId(getUserId());
		entity.setSos1(getSos());
		//entity.setIdEmsSegmento(getIdEmsSegmento());
	}
	public void toBean(EmsPecasLog entity){
		entity.setPartNo(getPartNo());
		entity.setPartName(getPartName());
		//entity.setId(getId());
		entity.setQtd(Long.valueOf(getQtd()));
		entity.setGroupNumber(getGroupNumber());
		entity.setReferenceNo(getReferenceNo());
		entity.setSmcsCode(getSmcsCode());
		entity.setGroupName(getGroupName());
		entity.setUserId(getUserId());
		entity.setSos1(getSos());
		entity.setIdEmsSegmento(entity.getIdEmsSegmento());
	}
	public void fromBean (EmsPecas entity){
		setPartNo(entity.getPartNo());
		setPartName(entity.getPartName());
		setId(entity.getId());
		setQtd(entity.getQtd().toString());
		setGroupNumber(entity.getGroupNumber());
		setReferenceNo(entity.getReferenceNo());
		setSmcsCode(entity.getSmcsCode());
		setGroupName(entity.getGroupName());
		setReferenceNo(entity.getReferenceNo());
		setUserId(entity.getUserId());
		setSos(entity.getSos1());
		setCoderr(entity.getCoderr());
		setDescerr(entity.getDescerr());
		
	}

}
