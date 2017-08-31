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
 * @author Elizangela
 */
@Entity
@Table(name = "EMS_CONSULTOR")
public class EmsConsultor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "COD_CLIENTE")
    private String codCliente;
    @Column(name = "COD_CONSULTOR_DBS")
    private String codConsultorDbs;
    @Column(name = "NOME_CONSULTOR")
    private String nomeConsultor;

    public EmsConsultor() {
    }

    public EmsConsultor(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCodConsultorDbs() {
        return codConsultorDbs;
    }

    public void setCodConsultorDbs(String codConsultorDbs) {
        this.codConsultorDbs = codConsultorDbs;
    }

    public String getNomeConsultor() {
        return nomeConsultor;
    }

    public void setNomeConsultor(String nomeConsultor) {
        this.nomeConsultor = nomeConsultor;
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
        if (!(object instanceof EmsConsultor)) {
            return false;
        }
        EmsConsultor other = (EmsConsultor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsConsultor[ id=" + id + " ]";
    }
    
}
