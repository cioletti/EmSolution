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
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_SITUACAO_PROPOSTA")

public class CrmSituacaoProposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DATA_ENVIO_CONSULTOR")
    private Date dataEnvioConsultor;
    @Column(name = "DATA_ABERTURA_CONSULTOR")
    private Date dataAberturaConsultor;
    @Column(name = "DATA_ENVIO_CLIENTE")
    private Date dataEnvioCliente;
    @Column(name = "DATA_NEGOCIACAO")
    private Date dataNegociacao;
    @Column(name = "DATA_VENDA_CONCLUIDA")
    private Date dataVendaConcluida;
    @Column(name = "DATA_REJEICAO_CLIENTE")
    private Date dataRejeicaoCliente;
    @Column(name = "DATA_PRORROGACAO_CONTATO_CLIENTE")
    private Date dataProrrogacaoContatoCliente;
    @Column(name = "DATA_PRORROGACAO_NEGOCIACAO")
    private Date dataProrrogacaoNegociacao;
    @Column(name = "ID_EMS_PROPOSTA")
    private Long idEmsProposta;
    @JoinColumn(name = "ID_PROPOSTA", referencedColumnName = "ID")
    @ManyToOne
    private CrmProposta idProposta;

    public CrmSituacaoProposta() {
    }

    public CrmSituacaoProposta(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataEnvioConsultor() {
        return dataEnvioConsultor;
    }

    public void setDataEnvioConsultor(Date dataEnvioConsultor) {
        this.dataEnvioConsultor = dataEnvioConsultor;
    }

    public Date getDataAberturaConsultor() {
        return dataAberturaConsultor;
    }

    public void setDataAberturaConsultor(Date dataAberturaConsultor) {
        this.dataAberturaConsultor = dataAberturaConsultor;
    }

    public Date getDataEnvioCliente() {
        return dataEnvioCliente;
    }

    public void setDataEnvioCliente(Date dataEnvioCliente) {
        this.dataEnvioCliente = dataEnvioCliente;
    }

    public Date getDataNegociacao() {
        return dataNegociacao;
    }

    public void setDataNegociacao(Date dataNegociacao) {
        this.dataNegociacao = dataNegociacao;
    }

    public Date getDataVendaConcluida() {
        return dataVendaConcluida;
    }

    public void setDataVendaConcluida(Date dataVendaConcluida) {
        this.dataVendaConcluida = dataVendaConcluida;
    }

    public Date getDataRejeicaoCliente() {
        return dataRejeicaoCliente;
    }

    public void setDataRejeicaoCliente(Date dataRejeicaoCliente) {
        this.dataRejeicaoCliente = dataRejeicaoCliente;
    }

    public Date getDataProrrogacaoContatoCliente() {
        return dataProrrogacaoContatoCliente;
    }

    public void setDataProrrogacaoContatoCliente(Date dataProrrogacaoContatoCliente) {
        this.dataProrrogacaoContatoCliente = dataProrrogacaoContatoCliente;
    }

    public Date getDataProrrogacaoNegociacao() {
        return dataProrrogacaoNegociacao;
    }

    public void setDataProrrogacaoNegociacao(Date dataProrrogacaoNegociacao) {
        this.dataProrrogacaoNegociacao = dataProrrogacaoNegociacao;
    }


    public Long getIdEmsProposta() {
		return idEmsProposta;
	}

	public void setIdEmsProposta(Long idEmsProposta) {
		this.idEmsProposta = idEmsProposta;
	}

	public CrmProposta getIdProposta() {
		return idProposta;
	}

	public void setIdProposta(CrmProposta idProposta) {
		this.idProposta = idProposta;
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
        if (!(object instanceof CrmSituacaoProposta)) {
            return false;
        }
        CrmSituacaoProposta other = (CrmSituacaoProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.CrmSituacaoProposta[ id=" + id + " ]";
    }
    
}
