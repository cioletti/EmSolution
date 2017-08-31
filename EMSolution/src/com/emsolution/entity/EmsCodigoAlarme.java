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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "EMS_CODIGO_ALARME")
public class EmsCodigoAlarme implements Serializable {
	 private static final long serialVersionUID = 1L;
	    @Id 
	    @Column(name = "ID", nullable = false)
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	    @Column(name = "DESCRICAO")
	    private String descricao;
	    @Column(name = "EVENTO_DIAGNOSTICO")
	    private String eventoDiagnostico;
	    @Column(name = "COMPARTIMENTO")
	    private String compartimento;
	    @Column(name = "POSSIVEIS_CAUSAS")
	    private String possiveisCausas;
	    @Column(name = "INDICADORES_SOS")
	    private String indicadoresSos;
	    @Column(name = "PM_TA")
	    private String pmTa;
	    @Column(name = "CODIGO_FMI")
	    private BigDecimal codigoFmi;
	    @Column(name = "CODIGO_CID")
	    private BigDecimal codigoCid;
	    @Column(name = "CODIGO_EID")
	    private BigDecimal codigoEid;
	    @Column(name = "ACOES_RECOMENDADAS")
	    private String acoesRecomendadas;
	    @JoinColumn(name = "ID_TIPO_ALARME", referencedColumnName = "ID")
	    @ManyToOne
	    private EmsTipoAlarme idTipoAlarme;

	    public EmsCodigoAlarme() {
	    }

	    public EmsCodigoAlarme(Long id) {
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

	    public String getEventoDiagnostico() {
	        return eventoDiagnostico;
	    }

	    public void setEventoDiagnostico(String eventoDiagnostico) {
	        this.eventoDiagnostico = eventoDiagnostico;
	    }

	    public String getCompartimento() {
	        return compartimento;
	    }

	    public void setCompartimento(String compartimento) {
	        this.compartimento = compartimento;
	    }

	    public String getPossiveisCausas() {
	        return possiveisCausas;
	    }

	    public void setPossiveisCausas(String possiveisCausas) {
	        this.possiveisCausas = possiveisCausas;
	    }

	    public String getIndicadoresSos() {
	        return indicadoresSos;
	    }

	    public void setIndicadoresSos(String indicadoresSos) {
	        this.indicadoresSos = indicadoresSos;
	    }

	    public String getPmTa() {
	        return pmTa;
	    }

	    public void setPmTa(String pmTa) {
	        this.pmTa = pmTa;
	    }

	    public String getAcoesRecomendadas() {
	        return acoesRecomendadas;
	    }

	    public void setAcoesRecomendadas(String acoesRecomendadas) {
	        this.acoesRecomendadas = acoesRecomendadas;
	    }

	    public EmsTipoAlarme getIdTipoAlarme() {
	        return idTipoAlarme;
	    }

	    public void setIdTipoAlarme(EmsTipoAlarme idTipoAlarme) {
	        this.idTipoAlarme = idTipoAlarme;
	    }

	    public BigDecimal getCodigoFmi() {
			return codigoFmi;
		}

		public void setCodigoFmi(BigDecimal codigoFmi) {
			this.codigoFmi = codigoFmi;
		}

		public BigDecimal getCodigoCid() {
			return codigoCid;
		}

		public void setCodigoCid(BigDecimal codigoCid) {
			this.codigoCid = codigoCid;
		}

		public BigDecimal getCodigoEid() {
			return codigoEid;
		}

		public void setCodigoEid(BigDecimal codigoEid) {
			this.codigoEid = codigoEid;
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
	        if (!(object instanceof EmsCodigoAlarme)) {
	            return false;
	        }
	        EmsCodigoAlarme other = (EmsCodigoAlarme) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.emsolution.entity.EmsCodigoAlarme[ id=" + id + " ]";
	    }
    
}
