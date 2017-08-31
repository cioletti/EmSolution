/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_FASE_NEGOCIACAO")
@NamedQueries({
    @NamedQuery(name = "CrmFaseNegociacao.findAll", query = "SELECT c FROM CrmFaseNegociacao c")})
public class CrmFaseNegociacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "SIGLA")
    private String sigla;
    @OneToMany(mappedBy = "idFaseNegociacao")
    private Collection<CrmProposta> crmPropostaCollection;

    public CrmFaseNegociacao() {
    }

    public CrmFaseNegociacao(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Collection<CrmProposta> getCrmPropostaCollection() {
        return crmPropostaCollection;
    }

    public void setCrmPropostaCollection(Collection<CrmProposta> crmPropostaCollection) {
        this.crmPropostaCollection = crmPropostaCollection;
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
        if (!(object instanceof CrmFaseNegociacao)) {
            return false;
        }
        CrmFaseNegociacao other = (CrmFaseNegociacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.CrmFaseNegociacao[ id=" + id + " ]";
    }
    
}
