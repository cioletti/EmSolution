/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_CONSUMO_COMBUSTIVEL")


public class EmsConsumoCombustivel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "MIN_PORCETAGEM_BAIXA")
    private Long minPorcetagemBaixa;
    @Column(name = "MAX_PORCETAGEM_BAIXA")
    private Long maxPorcetagemBaixa;
    @Column(name = "MIN_PORCETAGEM_MEDIA")
    private Long minPorcetagemMedia;
    @Column(name = "MAX_PORCETAGEM_MEDIA")
    private Long maxPorcetagemMedia;
    @Column(name = "MIN_PORCETAGEM_ALTA")
    private Long minPorcetagemAlta;
    @Column(name = "MAX_PORCETAGEM_ALTA")
    private Long maxPorcetagemAlta;
    @OneToMany(mappedBy = "idConsumoCombustivel")
    private Collection<ScFamilia> scFamiliaCollection;
    @JoinColumn(name = "ID_FAMILIA", referencedColumnName = "ID")
    @ManyToOne
    private ScFamilia idFamilia;

    public EmsConsumoCombustivel() {
    }

    public EmsConsumoCombustivel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMinPorcetagemBaixa() {
        return minPorcetagemBaixa;
    }

    public void setMinPorcetagemBaixa(Long minPorcetagemBaixa) {
        this.minPorcetagemBaixa = minPorcetagemBaixa;
    }

    public Long getMaxPorcetagemBaixa() {
        return maxPorcetagemBaixa;
    }

    public void setMaxPorcetagemBaixa(Long maxPorcetagemBaixa) {
        this.maxPorcetagemBaixa = maxPorcetagemBaixa;
    }

    public Long getMinPorcetagemMedia() {
        return minPorcetagemMedia;
    }

    public void setMinPorcetagemMedia(Long minPorcetagemMedia) {
        this.minPorcetagemMedia = minPorcetagemMedia;
    }

    public Long getMaxPorcetagemMedia() {
        return maxPorcetagemMedia;
    }

    public void setMaxPorcetagemMedia(Long maxPorcetagemMedia) {
        this.maxPorcetagemMedia = maxPorcetagemMedia;
    }

    public Long getMinPorcetagemAlta() {
        return minPorcetagemAlta;
    }

    public void setMinPorcetagemAlta(Long minPorcetagemAlta) {
        this.minPorcetagemAlta = minPorcetagemAlta;
    }

    public Long getMaxPorcetagemAlta() {
        return maxPorcetagemAlta;
    }

    public void setMaxPorcetagemAlta(Long maxPorcetagemAlta) {
        this.maxPorcetagemAlta = maxPorcetagemAlta;
    }

    @XmlTransient
    public Collection<ScFamilia> getScFamiliaCollection() {
        return scFamiliaCollection;
    }

    public void setScFamiliaCollection(Collection<ScFamilia> scFamiliaCollection) {
        this.scFamiliaCollection = scFamiliaCollection;
    }

    public ScFamilia getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(ScFamilia idFamilia) {
        this.idFamilia = idFamilia;
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
        if (!(object instanceof EmsConsumoCombustivel)) {
            return false;
        }
        EmsConsumoCombustivel other = (EmsConsumoCombustivel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsConsumoCombustivel[ id=" + id + " ]";
    }
    
}
