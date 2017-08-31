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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_FUEL")
public class EmsFuel implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmsFuelPK emsFuelPK;
    @Column(name = "MASTER_MSG_ID")
    private Long masterMsgId;
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
    @Column(name = "CONSUMO_TOTAL")
    private Long consumoTotal;
    @Column(name = "PERC_LEVEL_UTILIZATION")
    private Long percLevelUtilization;

    public EmsFuel() {
    }

    public EmsFuel(EmsFuelPK emsFuelPK) {
        this.emsFuelPK = emsFuelPK;
    }

    public EmsFuel(long messageId, Date receivedTime) {
        this.emsFuelPK = new EmsFuelPK(messageId, receivedTime);
    }

    public EmsFuelPK getEmsFuelPK() {
        return emsFuelPK;
    }

    public void setEmsFuelPK(EmsFuelPK emsFuelPK) {
        this.emsFuelPK = emsFuelPK;
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

    public Long getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(Long consumoTotal) {
        this.consumoTotal = consumoTotal;
    }

    public Long getPercLevelUtilization() {
        return percLevelUtilization;
    }

    public void setPercLevelUtilization(Long percLevelUtilization) {
        this.percLevelUtilization = percLevelUtilization;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emsFuelPK != null ? emsFuelPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsFuel)) {
            return false;
        }
        EmsFuel other = (EmsFuel) object;
        if ((this.emsFuelPK == null && other.emsFuelPK != null) || (this.emsFuelPK != null && !this.emsFuelPK.equals(other.emsFuelPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bmax.entuty.EmsFuel[ emsFuelPK=" + emsFuelPK + " ]";
    }
    
}
