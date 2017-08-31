/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "EMS_SMU")

public class EmsSmu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NUM_SERIE")
    private String numSerie;
    @Column(name = "IS_REJEITADO")
    private String isRejeitado;
    @Column(name = "HORIMETRO")
    private Long horimetro;
    @Column(name = "HORIMETRO_PROX")
    private Long horimetroProx;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Lob
    @Column(name = "TIPO_SERVICO")
    private String tipoServico;
    @JoinColumn(name = "ID_SEGMENTO", referencedColumnName = "ID")
    @ManyToOne
    private EmsSegmento idSegmento;
    @Column(name = "OBSERVACAO")
    private String observacao;

    public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public EmsSmu() {
    }

    public EmsSmu(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public Long getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(Long horimetro) {
        this.horimetro = horimetro;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getIsRejeitado() {
		return isRejeitado;
	}

	public void setIsRejeitado(String isRejeitado) {
		this.isRejeitado = isRejeitado;
	}

	public Long getHorimetroProx() {
		return horimetroProx;
	}

	public void setHorimetroProx(Long horimetroProx) {
		this.horimetroProx = horimetroProx;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public EmsSegmento getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(EmsSegmento idSegmento) {
		this.idSegmento = idSegmento;
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
        if (!(object instanceof EmsSmu)) {
            return false;
        }
        EmsSmu other = (EmsSmu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsSmu[ id=" + id + " ]";
    }
    
}
