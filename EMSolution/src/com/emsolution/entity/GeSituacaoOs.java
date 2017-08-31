/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author RDR
 */
@Entity
@Table(name = "GE_SITUACAO_OS")

public class GeSituacaoOs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)  
    private Long id;
    @Column(name = "NUMERO_OS")
    private String numeroOs;
    @Column(name = "DATA_CHEGADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataChegada;
    @Column(name = "DATA_ORCAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOrcamento;
    @Column(name = "DATA_ENTREGA_PEDIDOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntregaPedidos;
    @Column(name = "DATA_APROVACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAprovacao;
    @Column(name = "DATA_PREVISAO_ENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrevisaoEntrega;
    @Column(name = "DATA_TERMINO_SERVICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTerminoServico;
    @Column(name = "DATA_FATURAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFaturamento;
    @Column(name = "FILIAL")
    private Long filial;
    @Column(name = "IS_CHECK_OUT")
    private String isCheckOut;
    @Column(name = "TIPO_REJEICAO")
    private String tipoRejeicao;
    @Column(name = "ID_FUNC_DATA_CHEGADA")
    private String idFuncDataChegada;
    @Column(name = "ID_FUNC_DATA_ENTREGA_PEDIDOS")
    private String idFuncDataEntregaPedidos;
    @Column(name = "ID_FUNC_DATA_ORCAMENTO")
    private String idFuncDataOrcamento;
    @Column(name = "ID_FUNC_DATA_APROVACAO")
    private String idFuncDataAprovacao;
    @Column(name = "ID_FUNC_DATA_PREVISAO_ENTREGA")
    private String idFuncDataPrevisaoEntrega;
    @Column(name = "ID_FUNC_DATA_TERMINO_SERVICO")
    private String idFuncDataTerminoServico;
    @Column(name = "ID_FUNC_DATA_FATURAMENTO")
    private String idFuncDataFaturamento;
    @Column(name = "OBS")
    private String obs;
    @JoinColumn(name = "ID_CHECKIN", referencedColumnName = "ID")
    @ManyToOne
    private GeCheckIn idCheckin;

    public GeSituacaoOs() {
    }

    public GeSituacaoOs(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Date getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(Date dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }

    public Date getDataEntregaPedidos() {
        return dataEntregaPedidos;
    }

    public void setDataEntregaPedidos(Date dataEntregaPedidos) {
        this.dataEntregaPedidos = dataEntregaPedidos;
    }

    public Date getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(Date dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Date getDataPrevisaoEntrega() {
        return dataPrevisaoEntrega;
    }

    public void setDataPrevisaoEntrega(Date dataPrevisaoEntrega) {
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }

    public Date getDataTerminoServico() {
        return dataTerminoServico;
    }

    public void setDataTerminoServico(Date dataTerminoServico) {
        this.dataTerminoServico = dataTerminoServico;
    }

    public Date getDataFaturamento() {
        return dataFaturamento;
    }

    public void setDataFaturamento(Date dataFaturamento) {
        this.dataFaturamento = dataFaturamento;
    }

    public Long getFilial() {
        return filial;
    }

    public void setFilial(Long filial) {
        this.filial = filial;
    }

    public String getIsCheckOut() {
        return isCheckOut;
    }

    public void setIsCheckOut(String isCheckOut) {
        this.isCheckOut = isCheckOut;
    }

    public String getTipoRejeicao() {
        return tipoRejeicao;
    }

    public void setTipoRejeicao(String tipoRejeicao) {
        this.tipoRejeicao = tipoRejeicao;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public GeCheckIn getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(GeCheckIn idCheckin) {
        this.idCheckin = idCheckin;
    }

    public String getIdFuncDataChegada() {
		return idFuncDataChegada;
	}

	public void setIdFuncDataChegada(String idFuncDataChegada) {
		this.idFuncDataChegada = idFuncDataChegada;
	}

	public String getIdFuncDataEntregaPedidos() {
		return idFuncDataEntregaPedidos;
	}

	public void setIdFuncDataEntregaPedidos(String idFuncDataEntregaPedidos) {
		this.idFuncDataEntregaPedidos = idFuncDataEntregaPedidos;
	}

	public String getIdFuncDataOrcamento() {
		return idFuncDataOrcamento;
	}

	public void setIdFuncDataOrcamento(String idFuncDataOrcamento) {
		this.idFuncDataOrcamento = idFuncDataOrcamento;
	}

	public String getIdFuncDataAprovacao() {
		return idFuncDataAprovacao;
	}

	public void setIdFuncDataAprovacao(String idFuncDataAprovacao) {
		this.idFuncDataAprovacao = idFuncDataAprovacao;
	}

	public String getIdFuncDataPrevisaoEntrega() {
		return idFuncDataPrevisaoEntrega;
	}

	public void setIdFuncDataPrevisaoEntrega(String idFuncDataPrevisaoEntrega) {
		this.idFuncDataPrevisaoEntrega = idFuncDataPrevisaoEntrega;
	}

	public String getIdFuncDataTerminoServico() {
		return idFuncDataTerminoServico;
	}

	public void setIdFuncDataTerminoServico(String idFuncDataTerminoServico) {
		this.idFuncDataTerminoServico = idFuncDataTerminoServico;
	}

	public String getIdFuncDataFaturamento() {
		return idFuncDataFaturamento;
	}

	public void setIdFuncDataFaturamento(String idFuncDataFaturamento) {
		this.idFuncDataFaturamento = idFuncDataFaturamento;
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
        if (!(object instanceof GeSituacaoOs)) {
            return false;
        }
        GeSituacaoOs other = (GeSituacaoOs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "geradorbean.GeSituacaoOs[ id=" + id + " ]";
    }
    
}
