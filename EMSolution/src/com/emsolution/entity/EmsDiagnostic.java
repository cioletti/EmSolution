/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_DIAGNOSTIC")
public class EmsDiagnostic implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmsDiagnosticPK emsDiagnosticPK;
    @Column(name = "MASTER_MSG_ID")
    private Long masterMsgId;
    @Lob
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    @Column(name = "MAKE")
    private String make;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "MODULE_CODE")
    private String moduleCode;
    @Column(name = "MODULE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date moduleTime;
    @Column(name = "NUMBER_OF_DIAGNOSTIC")
    private Long numberOfDiagnostic;
    @Column(name = "DATA_ATUALIZACAO_HORIMETRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacaoHorimetro;
    @Column(name = "HORIMETRO")
    private Long horimetro;
    
    
    public Date getDataAtualizacaoHorimetro() {
		return dataAtualizacaoHorimetro;
	}

	public void setDataAtualizacaoHorimetro(Date dataAtualizacaoHorimetro) {
		this.dataAtualizacaoHorimetro = dataAtualizacaoHorimetro;
	}

	public Long getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(Long horimetro) {
		this.horimetro = horimetro;
	}

    
    public EmsDiagnostic() {
    }

    public EmsDiagnostic(EmsDiagnosticPK emsDiagnosticPK) {
        this.emsDiagnosticPK = emsDiagnosticPK;
    }

    public EmsDiagnostic(long messageId, Date receivedTime) {
        this.emsDiagnosticPK = new EmsDiagnosticPK(messageId, receivedTime);
    }

    public EmsDiagnosticPK getEmsDiagnosticPK() {
        return emsDiagnosticPK;
    }

    public void setEmsDiagnosticPK(EmsDiagnosticPK emsDiagnosticPK) {
        this.emsDiagnosticPK = emsDiagnosticPK;
    }

    public Long getMasterMsgId() {
        return masterMsgId;
    }

    public void setMasterMsgId(Long masterMsgId) {
        this.masterMsgId = masterMsgId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Date getModuleTime() {
        return moduleTime;
    }

    public void setModuleTime(Date moduleTime) {
        this.moduleTime = moduleTime;
    }

    public Long getNumberOfDiagnostic() {
        return numberOfDiagnostic;
    }

    public void setNumberOfDiagnostic(Long numberOfDiagnostic) {
        this.numberOfDiagnostic = numberOfDiagnostic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emsDiagnosticPK != null ? emsDiagnosticPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsDiagnostic)) {
            return false;
        }
        EmsDiagnostic other = (EmsDiagnostic) object;
        if ((this.emsDiagnosticPK == null && other.emsDiagnosticPK != null) || (this.emsDiagnosticPK != null && !this.emsDiagnosticPK.equals(other.emsDiagnosticPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.emsolution.entity.EmsDiagnostic[ emsDiagnosticPK=" + emsDiagnosticPK + " ]";
    }
    
}
