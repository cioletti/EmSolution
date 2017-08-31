/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_ALERT_MAQUINA")
public class EmsAlertMaquina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    @Column(name = "MESSAGE_ID")
    private Long messageId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECEIVED_TIME")
    private Date receivedTime;
    @Column(name = "MASTER_MSG_ID")
    private Long masterMsgId;
    @Column(name = "MAKE")
    private String make;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "MODULE_CODE")
    private String moduleCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODULE_TIME")
    private Date moduleTime;
    @Column(name = "time_Watch_Active")
    private String timeWatchActive;
    @Column(name = "exclusive_Watch_Active")
    private String exclusiveWatchActive;
    @Column(name = "inclusive_Watch_Active")
    private String inclusiveWatchActive;
    @Column(name = "time_Watch_Alarm")
    private String timeWatchAlarm;
    @Column(name = "exclusive_Watch_Alarm")
    private String exclusiveWatchAlarm;
    @Column(name = "inclusive_Watch_Alarm")
    private String inclusiveWatchAlarm;
    @Column(name = "satellite_Blockage")
    private String satelliteBlockage;
    @Column(name = "disconnect_Switch_Used")
    private String disconnectSwitchUsed;

    public EmsAlertMaquina() {
    }

    public EmsAlertMaquina(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Long getMasterMsgId() {
        return masterMsgId;
    }

    public void setMasterMsgId(Long masterMsgId) {
        this.masterMsgId = masterMsgId;
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

    public String getTimeWatchActive() {
        return timeWatchActive;
    }

    public void setTimeWatchActive(String timeWatchActive) {
        this.timeWatchActive = timeWatchActive;
    }

    public String getExclusiveWatchActive() {
        return exclusiveWatchActive;
    }

    public void setExclusiveWatchActive(String exclusiveWatchActive) {
        this.exclusiveWatchActive = exclusiveWatchActive;
    }

    public String getInclusiveWatchActive() {
        return inclusiveWatchActive;
    }

    public void setInclusiveWatchActive(String inclusiveWatchActive) {
        this.inclusiveWatchActive = inclusiveWatchActive;
    }

    public String getTimeWatchAlarm() {
        return timeWatchAlarm;
    }

    public void setTimeWatchAlarm(String timeWatchAlarm) {
        this.timeWatchAlarm = timeWatchAlarm;
    }

    public String getExclusiveWatchAlarm() {
        return exclusiveWatchAlarm;
    }

    public void setExclusiveWatchAlarm(String exclusiveWatchAlarm) {
        this.exclusiveWatchAlarm = exclusiveWatchAlarm;
    }

    public String getInclusiveWatchAlarm() {
        return inclusiveWatchAlarm;
    }

    public void setInclusiveWatchAlarm(String inclusiveWatchAlarm) {
        this.inclusiveWatchAlarm = inclusiveWatchAlarm;
    }

    public String getSatelliteBlockage() {
        return satelliteBlockage;
    }

    public void setSatelliteBlockage(String satelliteBlockage) {
        this.satelliteBlockage = satelliteBlockage;
    }

    public String getDisconnectSwitchUsed() {
        return disconnectSwitchUsed;
    }

    public void setDisconnectSwitchUsed(String disconnectSwitchUsed) {
        this.disconnectSwitchUsed = disconnectSwitchUsed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serialNumber != null ? serialNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsAlertMaquina)) {
            return false;
        }
        EmsAlertMaquina other = (EmsAlertMaquina) object;
        if ((this.serialNumber == null && other.serialNumber != null) || (this.serialNumber != null && !this.serialNumber.equals(other.serialNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pmp.EmsAlertMaquina[ serialNumber=" + serialNumber + " ]";
    }
    
}
