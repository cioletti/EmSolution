/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_SEGMENTO_SERV_TERCEIROS")
public class EmsSegmentoServTerceiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmsSegmentoServTerceirosPK emsSegmentoServTerceirosPK;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "QTD")
    private Long qtd;
    @Column(name = "ID_OPER")
    private Long idOper;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Column(name = "LOCAL_SERVICO")
    private String localServico;
    @JoinColumn(name = "ID_STATUS_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeStatusServTerceiros idStatusServTerceiros;
    @JoinColumn(name = "ID_FORN_SERV_TERCEIROS", referencedColumnName = "ID")
    @ManyToOne
    private GeFornecedorServTerceiros idFornServTerceiros;

    public EmsSegmentoServTerceiros() {
    }

    public EmsSegmentoServTerceiros(EmsSegmentoServTerceirosPK emsSegmentoServTerceirosPK) {
        this.emsSegmentoServTerceirosPK = emsSegmentoServTerceirosPK;
    }

    public EmsSegmentoServTerceiros(long idEmsSegmento, long idTipoServicoTerceiros) {
        this.emsSegmentoServTerceirosPK = new EmsSegmentoServTerceirosPK(idEmsSegmento, idTipoServicoTerceiros);
    }

    public EmsSegmentoServTerceirosPK getEmsSegmentoServTerceirosPK() {
        return emsSegmentoServTerceirosPK;
    }

    public void setEmsSegmentoServTerceirosPK(EmsSegmentoServTerceirosPK emsSegmentoServTerceirosPK) {
        this.emsSegmentoServTerceirosPK = emsSegmentoServTerceirosPK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getQtd() {
        return qtd;
    }

    public void setQtd(Long qtd) {
        this.qtd = qtd;
    }

    public Long getIdOper() {
        return idOper;
    }

    public void setIdOper(Long idOper) {
        this.idOper = idOper;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getLocalServico() {
        return localServico;
    }

    public void setLocalServico(String localServico) {
        this.localServico = localServico;
    }

    public GeStatusServTerceiros getIdStatusServTerceiros() {
        return idStatusServTerceiros;
    }

    public void setIdStatusServTerceiros(GeStatusServTerceiros idStatusServTerceiros) {
        this.idStatusServTerceiros = idStatusServTerceiros;
    }

    public GeFornecedorServTerceiros getIdFornServTerceiros() {
        return idFornServTerceiros;
    }

    public void setIdFornServTerceiros(GeFornecedorServTerceiros idFornServTerceiros) {
        this.idFornServTerceiros = idFornServTerceiros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emsSegmentoServTerceirosPK != null ? emsSegmentoServTerceirosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsSegmentoServTerceiros)) {
            return false;
        }
        EmsSegmentoServTerceiros other = (EmsSegmentoServTerceiros) object;
        if ((this.emsSegmentoServTerceirosPK == null && other.emsSegmentoServTerceirosPK != null) || (this.emsSegmentoServTerceirosPK != null && !this.emsSegmentoServTerceirosPK.equals(other.emsSegmentoServTerceirosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsSegmentoServTerceiros[ emsSegmentoServTerceirosPK=" + emsSegmentoServTerceirosPK + " ]";
    }
    
}
