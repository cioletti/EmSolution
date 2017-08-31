/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_ALERTA_BUFFER")
public class EmsAlertaBuffer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BUFFER")
    private Long buffer;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmsAlertaBuffer() {
    }

    public EmsAlertaBuffer(Long buffer) {
        this.buffer = buffer;
    }

    public Long getBuffer() {
        return buffer;
    }

    public void setBuffer(Long buffer) {
        this.buffer = buffer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buffer != null ? buffer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsAlertaBuffer)) {
            return false;
        }
        EmsAlertaBuffer other = (EmsAlertaBuffer) object;
        if ((this.buffer == null && other.buffer != null) || (this.buffer != null && !this.buffer.equals(other.buffer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.emsolution.entity.EmsDiagnosticBuffer[ buffer=" + buffer + " ]";
    }
    
}
