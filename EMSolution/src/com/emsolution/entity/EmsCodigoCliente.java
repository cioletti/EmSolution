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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Elizangela
 */
@Entity
@Table(name = "EMS_CODIGO_CLIENTE")
@XmlRootElement
public class EmsCodigoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ID_LOGIN_CLIENTE")
    private Long idLoginCliente;
    @Column(name = "CODIGO")
    private String codigo;

    public EmsCodigoCliente() {
    }

    public EmsCodigoCliente(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLoginCliente() {
        return idLoginCliente;
    }

    public void setIdLoginCliente(Long idLoginCliente) {
        this.idLoginCliente = idLoginCliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        if (!(object instanceof EmsCodigoCliente)) {
            return false;
        }
        EmsCodigoCliente other = (EmsCodigoCliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsCodigoCliente[ id=" + id + " ]";
    }
    
}
