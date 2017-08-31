/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_TYPE_DIAGNOSTIC")
public class EmsTypeDiagnostic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NIVEL")
    private Long nivel;
    @Column(name = "MID")
    private Long mid;
    @Column(name = "CID")
    private Long cid;
    @Column(name = "FMI")
    private Long fmi;
    @Column(name = "EID")
    private Long eid;
    @Column(name = "OCURRANCES")
    private Long ocurrances;
    @Column(name = "RECEIVED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recievedTime;
    @JoinColumn(name = "ID_EMS_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private EmsSegmento idEmsSegmento;    
    @JoinColumns({
        @JoinColumn(name = "ID_MESSAGE_ID", referencedColumnName = "MESSAGE_ID"),
        @JoinColumn(name = "ID_RECEIVE_TIME", referencedColumnName = "RECEIVED_TIME")})
    @ManyToOne
    private EmsDiagnostic emsDiagnostic;

    public EmsTypeDiagnostic() {
    }

    public EmsTypeDiagnostic(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getFmi() {
        return fmi;
    }

    public void setFmi(Long fmi) {
        this.fmi = fmi;
    }

    public Long getOcurrances() {
        return ocurrances;
    }

    public void setOcurrances(Long ocurrances) {
        this.ocurrances = ocurrances;
    }

    public Date getRecievedTime() {
        return recievedTime;
    }

    public void setRecievedTime(Date recievedTime) {
        this.recievedTime = recievedTime;
    }

    public EmsDiagnostic getEmsDiagnostic() {
        return emsDiagnostic;
    }

    public void setEmsDiagnostic(EmsDiagnostic emsDiagnostic) {
        this.emsDiagnostic = emsDiagnostic;
    }

    public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	public EmsSegmento getIdEmsSegmento() {
		return idEmsSegmento;
	}

	public void setIdEmsSegmento(EmsSegmento idEmsSegmento) {
		this.idEmsSegmento = idEmsSegmento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsTypeDiagnostic)) {
            return false;
        }
        EmsTypeDiagnostic other = (EmsTypeDiagnostic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsTypeDiagnostic[ id=" + id + " ]";
    }
    
}
