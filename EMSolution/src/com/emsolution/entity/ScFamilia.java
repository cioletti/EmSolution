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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Elizangela
 */
@Entity
@Table(name = "SC_FAMILIA")
public class ScFamilia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "CONSUMO")
    private String consumo;
    @JoinColumn(name = "ID_CONSUMO_COMBUSTIVEL", referencedColumnName = "ID")
    @ManyToOne
    private EmsConsumoCombustivel idConsumoCombustivel;
    @OneToMany(mappedBy = "idFamilia")
    private Collection<EmsConsumoCombustivel> emsConsumoCombustivelCollection;

    public ScFamilia() {
    }

    public ScFamilia(Long id) {
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

    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public EmsConsumoCombustivel getIdConsumoCombustivel() {
        return idConsumoCombustivel;
    }

    public void setIdConsumoCombustivel(EmsConsumoCombustivel idConsumoCombustivel) {
        this.idConsumoCombustivel = idConsumoCombustivel;
    }

    @XmlTransient
    public Collection<EmsConsumoCombustivel> getEmsConsumoCombustivelCollection() {
        return emsConsumoCombustivelCollection;
    }

    public void setEmsConsumoCombustivelCollection(Collection<EmsConsumoCombustivel> emsConsumoCombustivelCollection) {
        this.emsConsumoCombustivelCollection = emsConsumoCombustivelCollection;
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
        if (!(object instanceof ScFamilia)) {
            return false;
        }
        ScFamilia other = (ScFamilia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.ScFamilia[ id=" + id + " ]";
    }
    
}
