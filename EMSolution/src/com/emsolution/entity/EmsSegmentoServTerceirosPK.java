/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Rodrigo
 */
@Embeddable
public class EmsSegmentoServTerceirosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_EMS_SEGMENTO")
    private long idEmsSegmento;
    @Basic(optional = false)
    @Column(name = "ID_TIPO_SERVICO_TERCEIROS")
    private long idTipoServicoTerceiros;

    public EmsSegmentoServTerceirosPK() {
    }

    public EmsSegmentoServTerceirosPK(long idEmsSegmento, long idTipoServicoTerceiros) {
        this.idEmsSegmento = idEmsSegmento;
        this.idTipoServicoTerceiros = idTipoServicoTerceiros;
    }

    public long getIdEmsSegmento() {
        return idEmsSegmento;
    }

    public void setIdEmsSegmento(long idEmsSegmento) {
        this.idEmsSegmento = idEmsSegmento;
    }

    public long getIdTipoServicoTerceiros() {
        return idTipoServicoTerceiros;
    }

    public void setIdTipoServicoTerceiros(long idTipoServicoTerceiros) {
        this.idTipoServicoTerceiros = idTipoServicoTerceiros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEmsSegmento;
        hash += (int) idTipoServicoTerceiros;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsSegmentoServTerceirosPK)) {
            return false;
        }
        EmsSegmentoServTerceirosPK other = (EmsSegmentoServTerceirosPK) object;
        if (this.idEmsSegmento != other.idEmsSegmento) {
            return false;
        }
        if (this.idTipoServicoTerceiros != other.idTipoServicoTerceiros) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsSegmentoServTerceirosPK[ idEmsSegmento=" + idEmsSegmento + ", idTipoServicoTerceiros=" + idTipoServicoTerceiros + " ]";
    }
    
}
