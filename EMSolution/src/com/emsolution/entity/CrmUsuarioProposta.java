/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emsolution.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RDR
 */
@Entity
@Table(name = "CRM_USUARIO_PROPOSTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CrmUsuarioProposta.findAll", query = "SELECT c FROM CrmUsuarioProposta c"),
    @NamedQuery(name = "CrmUsuarioProposta.findById", query = "SELECT c FROM CrmUsuarioProposta c WHERE c.id = :id"),
    @NamedQuery(name = "CrmUsuarioProposta.findByIdTwFuncionario", query = "SELECT c FROM CrmUsuarioProposta c WHERE c.idTwFuncionario = :idTwFuncionario"),
    @NamedQuery(name = "CrmUsuarioProposta.findByIdProposta", query = "SELECT c FROM CrmUsuarioProposta c WHERE c.idProposta = :idProposta")})
public class CrmUsuarioProposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ID_TW_FUNCIONARIO")
    private String idTwFuncionario;
    @Column(name = "ID_PROPOSTA")
    private Long idProposta;

    public CrmUsuarioProposta() {
    }

    public CrmUsuarioProposta(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTwFuncionario() {
        return idTwFuncionario;
    }

    public void setIdTwFuncionario(String idTwFuncionario) {
        this.idTwFuncionario = idTwFuncionario;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(Long idProposta) {
        this.idProposta = idProposta;
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
        if (!(object instanceof CrmUsuarioProposta)) {
            return false;
        }
        CrmUsuarioProposta other = (CrmUsuarioProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.CrmUsuarioProposta[ id=" + id + " ]";
    }
    
}
