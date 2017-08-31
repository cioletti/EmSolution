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
public class EmsFuelPK implements Serializable {

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "MESSAGE_ID")
    private long messageId;
    @Basic(optional = false)
    @Column(name = "RECEIVED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedTime;

    public EmsFuelPK() {
    }

    public EmsFuelPK(long messageId, Date receivedTime) {
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
        if (!(object instanceof EmsFuelPK)) {
            return false;
        }
        EmsFuelPK other = (EmsFuelPK) object;
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
        return "com.bmax.entuty.EmsFuelPK[ messageId=" + messageId + ", receivedTime=" + receivedTime + " ]";
    }
    
}
