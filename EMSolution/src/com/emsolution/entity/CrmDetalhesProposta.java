/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "CRM_DETALHES_PROPOSTA")
@NamedQueries({
    @NamedQuery(name = "CrmDetalhesProposta.findAll", query = "SELECT c FROM CrmDetalhesProposta c")})
public class CrmDetalhesProposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "OBJETO_PROPOSTA")
    private String objetoProposta;
    @Column(name = "MAQUINA")
    private String maquina;
    @Lob
    @Column(name = "CONDICOES_PAGAMENTO")
    private String condicoesPagamento;
    @Column(name = "VALIDADE_PROPOSTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validadeProposta;
    @Column(name = "AOS_CUIDADOS")
    private String aosCuidados;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FORMA_ENTREGA_PROPOSTA")
    private String formaEntregaProposta;
    @Lob
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Column(name = "DATA_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "PRAZO_ENTREGA")
    private Long prazoEntrega;
    @Basic(optional = false)
    @Column(name = "ID_PROPOSTA")
    private long idProposta;
    @Column(name = "CONTATO")
    private String contato;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_REPARO_APOS_FALHA")
    private BigDecimal valorReparoAposFalha;
    @Column(name = "DISTANCIA")
    private Long distancia;
    @Column(name = "PRINT_PECAS")
    private String printPecas;
    @Column(name = "CONSULTOR")
    private String consultor;
    @Column(name = "EMAIL_CONSULTOR")
    private String emailConsultor;

    public CrmDetalhesProposta() {
    }

    public CrmDetalhesProposta(Long id) {
        this.id = id;
    }

    public CrmDetalhesProposta(Long id, long idProposta) {
        this.id = id;
        this.idProposta = idProposta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjetoProposta() {
        return objetoProposta;
    }

    public void setObjetoProposta(String objetoProposta) {
        this.objetoProposta = objetoProposta;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getCondicoesPagamento() {
        return condicoesPagamento;
    }

    public void setCondicoesPagamento(String condicoesPagamento) {
        this.condicoesPagamento = condicoesPagamento;
    }

    public Date getValidadeProposta() {
        return validadeProposta;
    }

    public void setValidadeProposta(Date validadeProposta) {
        this.validadeProposta = validadeProposta;
    }

    public String getAosCuidados() {
        return aosCuidados;
    }

    public void setAosCuidados(String aosCuidados) {
        this.aosCuidados = aosCuidados;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormaEntregaProposta() {
        return formaEntregaProposta;
    }

    public void setFormaEntregaProposta(String formaEntregaProposta) {
        this.formaEntregaProposta = formaEntregaProposta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Long getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Long prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(long idProposta) {
        this.idProposta = idProposta;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public BigDecimal getValorReparoAposFalha() {
        return valorReparoAposFalha;
    }

    public void setValorReparoAposFalha(BigDecimal valorReparoAposFalha) {
        this.valorReparoAposFalha = valorReparoAposFalha;
    }

    public Long getDistancia() {
        return distancia;
    }

    public void setDistancia(Long distancia) {
        this.distancia = distancia;
    }

    public String getPrintPecas() {
		return printPecas;
	}

	public void setPrintPecas(String printPecas) {
		this.printPecas = printPecas;
	}

	public String getConsultor() {
		return consultor;
	}

	public void setConsultor(String consultor) {
		this.consultor = consultor;
	}

	public String getEmailConsultor() {
		return emailConsultor;
	}

	public void setEmailConsultor(String emailConsultor) {
		this.emailConsultor = emailConsultor;
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
        if (!(object instanceof CrmDetalhesProposta)) {
            return false;
        }
        CrmDetalhesProposta other = (CrmDetalhesProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.CrmDetalhesProposta[ id=" + id + " ]";
    }
    
}
