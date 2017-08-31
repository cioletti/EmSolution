/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "SC_VALOR_KM")
public class ScValorKm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "KM_VENDA")
    private BigDecimal kmVenda;
    @Column(name = "KM_GARANTIA")
    private BigDecimal kmGarantia;
    @Column(name = "KM_INTER")
    private BigDecimal kmInter;

    public ScValorKm() {
    }

    public ScValorKm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getKmVenda() {
        return kmVenda;
    }

    public void setKmVenda(BigDecimal kmVenda) {
        this.kmVenda = kmVenda;
    }

    public BigDecimal getKmGarantia() {
        return kmGarantia;
    }

    public void setKmGarantia(BigDecimal kmGarantia) {
        this.kmGarantia = kmGarantia;
    }

    public BigDecimal getKmInter() {
        return kmInter;
    }

    public void setKmInter(BigDecimal kmInter) {
        this.kmInter = kmInter;
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
        if (!(object instanceof ScValorKm)) {
            return false;
        }
        ScValorKm other = (ScValorKm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.campo.entity.ScValorKm[ id=" + id + " ]";
    }
    
}
