/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "OS_PALM_DT")
@NamedQueries({
    @NamedQuery(name = "OsPalmDt.findAll", query = "SELECT o FROM OsPalmDt o")})
public class OsPalmDt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDOS_PALM_DT")
    private Long idosPalmDt;
    @Column(name = "ID_IDARV")
    private Long idIdarv;
    @Column(name = "SIMNAO")
    private String simnao;
    @Column(name = "GRUPO")
    private String grupo;
    @Lob
    @Column(name = "OBS")
    private String obs;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "TIPO_MANUTENCAO")
    private String tipoManutencao;
    @Lob
    @Column(name = "OBS_EMS_SEGMENTO")
    private String obsEmsSegmento;
    @JoinColumn(name = "OS_PALM_IDOS_PALM", referencedColumnName = "IDOS_PALM")
    @ManyToOne(optional = false)
    private OsPalm osPalmIdosPalm;
    @JoinColumn(name = "ID_EMS_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private EmsSegmento idEmsSegmento;

    public OsPalmDt() {
    }

    public OsPalmDt(Long idosPalmDt) {
        this.idosPalmDt = idosPalmDt;
    }

    public Long getIdosPalmDt() {
        return idosPalmDt;
    }

    public void setIdosPalmDt(Long idosPalmDt) {
        this.idosPalmDt = idosPalmDt;
    }

    public Long getIdIdarv() {
        return idIdarv;
    }

    public void setIdIdarv(Long idIdarv) {
        this.idIdarv = idIdarv;
    }

    public String getSimnao() {
        return simnao;
    }

    public void setSimnao(String simnao) {
        this.simnao = simnao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public OsPalm getOsPalmIdosPalm() {
        return osPalmIdosPalm;
    }

    public void setOsPalmIdosPalm(OsPalm osPalmIdosPalm) {
        this.osPalmIdosPalm = osPalmIdosPalm;
    }

    public EmsSegmento getIdEmsSegmento() {
        return idEmsSegmento;
    }

    public void setIdEmsSegmento(EmsSegmento idEmsSegmento) {
        this.idEmsSegmento = idEmsSegmento;
    }

    public String getObsEmsSegmento() {
		return obsEmsSegmento;
	}

	public void setObsEmsSegmento(String obsEmsSegmento) {
		this.obsEmsSegmento = obsEmsSegmento;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idosPalmDt != null ? idosPalmDt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsPalmDt)) {
            return false;
        }
        OsPalmDt other = (OsPalmDt) object;
        if ((this.idosPalmDt == null && other.idosPalmDt != null) || (this.idosPalmDt != null && !this.idosPalmDt.equals(other.idosPalmDt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.OsPalmDt[ idosPalmDt=" + idosPalmDt + " ]";
    }
    
}
