/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Embeddable
public class EmsDiagnosticPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "MESSAGE_ID")
    private long messageId;
    @Basic(optional = false)
    @Column(name = "RECEIVED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedTime;

    public EmsDiagnosticPK() {
    }

    public EmsDiagnosticPK(long messageId, Date receivedTime) {
        this.messageId = messageId;
        this.receivedTime = receivedTime;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) messageId;
        hash += (receivedTime != null ? receivedTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsDiagnosticPK)) {
            return false;
        }
        EmsDiagnosticPK other = (EmsDiagnosticPK) object;
        if (this.messageId != other.messageId) {
            return false;
        }
        if ((this.receivedTime == null && other.receivedTime != null) || (this.receivedTime != null && !this.receivedTime.equals(other.receivedTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.emsolution.entity.EmsDiagnosticPK[ messageId=" + messageId + ", receivedTime=" + receivedTime + " ]";
    }
    
}
