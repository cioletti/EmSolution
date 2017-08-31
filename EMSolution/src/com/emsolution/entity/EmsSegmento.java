/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_SEGMENTO")
public class EmsSegmento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NUMERO_SEGMENTO")
    private String numeroSegmento;
    @Column(name = "JOB_CODE")
    private String jobCode;
    @Column(name = "DESCRICAO_JOB_CODE")
    private String descricaoJobCode;
    @Column(name = "COM_CODE")
    private String comCode;
    @Column(name = "HORAS_PREVISTA")
    private String horasPrevista;
    @Column(name = "MSG_DBS")
    private String msgDbs;
    @Column(name = "COD_ERRO_DBS")
    private String codErroDbs;
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Column(name = "MSG_DOC_DBS")
    private String msgDocDbs;
    @Column(name = "COD_ERRO_DOC_DBS")
    private String codErroDocDbs;
    @Column(name = "JOB_CONTROL")
    private String jobControl;
    @Column(name = "DESCRICAO_COMP_CODE")
    private String descricaoCompCode;
    @Column(name = "QTD_COMPONENTE")
    private Long qtdComponente;
    @Column(name = "HAS_SEND_DBS")
    private String hasSendDbs;
    @Column(name = "QTD_COMP")
    private Long qtdComp;
    @Column(name = "ID_FUNCIONARIO_CRIADOR")
    private String idFuncionarioCriador;
    @Lob
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Column(name = "IS_CREATE_SEGMENTO")
    private String isCreateSegmento;
    @Column(name = "ID_FUNCIONARIO_PECAS")
    private String idFuncionarioPecas;
    @Basic(optional = false)
    @Column(name = "DATA_CRIACAO")
    private Date dataCriacao;
    @Column(name = "DATA_IMPORTACAO_PECAS")
    private String dataImportacaoPecas;
    @Column(name = "QTD_COMP_SUBST")
    private Long qtdCompSubst;
    @Column(name = "HORAS_PREVISTA_SUBST")
    private String horasPrevistaSubst;
    @Column(name = "ID_TW_FUNCIONARIO_SUBST")
    private String idTwFuncionarioSubst;
    @Column(name = "VALOR_PECAS")
    private BigDecimal valorPecas;
    @Column(name = "DATA_SUBSTITUICAO")
    private String dataSubstituicao;
    @OneToMany(mappedBy = "idEmsSegmento")
    private Collection<EmsPecas> emsPecasCollection;
    @JoinColumn(name = "ID_PROPOSTA", referencedColumnName = "ID")
    @ManyToOne
    private EmsProposta idProposta;

    public EmsSegmento() {
    }

    public EmsSegmento(Long id) {
        this.id = id;
    }

    public EmsSegmento(Long id, Date dataCriacao) {
        this.id = id;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSegmento() {
        return numeroSegmento;
    }

    public void setNumeroSegmento(String numeroSegmento) {
        this.numeroSegmento = numeroSegmento;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getDescricaoJobCode() {
        return descricaoJobCode;
    }

    public void setDescricaoJobCode(String descricaoJobCode) {
        this.descricaoJobCode = descricaoJobCode;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getHorasPrevista() {
        return horasPrevista;
    }

    public void setHorasPrevista(String horasPrevista) {
        this.horasPrevista = horasPrevista;
    }

    public String getMsgDbs() {
        return msgDbs;
    }

    public void setMsgDbs(String msgDbs) {
        this.msgDbs = msgDbs;
    }

    public String getCodErroDbs() {
        return codErroDbs;
    }

    public void setCodErroDbs(String codErroDbs) {
        this.codErroDbs = codErroDbs;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getMsgDocDbs() {
        return msgDocDbs;
    }

    public void setMsgDocDbs(String msgDocDbs) {
        this.msgDocDbs = msgDocDbs;
    }

    public String getCodErroDocDbs() {
        return codErroDocDbs;
    }

    public void setCodErroDocDbs(String codErroDocDbs) {
        this.codErroDocDbs = codErroDocDbs;
    }

    public String getJobControl() {
        return jobControl;
    }

    public void setJobControl(String jobControl) {
        this.jobControl = jobControl;
    }

    public String getDescricaoCompCode() {
        return descricaoCompCode;
    }

    public void setDescricaoCompCode(String descricaoCompCode) {
        this.descricaoCompCode = descricaoCompCode;
    }

    public Long getQtdComponente() {
        return qtdComponente;
    }

    public void setQtdComponente(Long qtdComponente) {
        this.qtdComponente = qtdComponente;
    }

    public String getHasSendDbs() {
        return hasSendDbs;
    }

    public void setHasSendDbs(String hasSendDbs) {
        this.hasSendDbs = hasSendDbs;
    }

    public Long getQtdComp() {
        return qtdComp;
    }

    public void setQtdComp(Long qtdComp) {
        this.qtdComp = qtdComp;
    }

    public String getIdFuncionarioCriador() {
        return idFuncionarioCriador;
    }

    public void setIdFuncionarioCriador(String idFuncionarioCriador) {
        this.idFuncionarioCriador = idFuncionarioCriador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getIsCreateSegmento() {
        return isCreateSegmento;
    }

    public void setIsCreateSegmento(String isCreateSegmento) {
        this.isCreateSegmento = isCreateSegmento;
    }

    public String getIdFuncionarioPecas() {
        return idFuncionarioPecas;
    }

    public void setIdFuncionarioPecas(String idFuncionarioPecas) {
        this.idFuncionarioPecas = idFuncionarioPecas;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataImportacaoPecas() {
        return dataImportacaoPecas;
    }

    public void setDataImportacaoPecas(String dataImportacaoPecas) {
        this.dataImportacaoPecas = dataImportacaoPecas;
    }

    public Long getQtdCompSubst() {
        return qtdCompSubst;
    }

    public void setQtdCompSubst(Long qtdCompSubst) {
        this.qtdCompSubst = qtdCompSubst;
    }

    public String getHorasPrevistaSubst() {
        return horasPrevistaSubst;
    }

    public void setHorasPrevistaSubst(String horasPrevistaSubst) {
        this.horasPrevistaSubst = horasPrevistaSubst;
    }

    public String getIdTwFuncionarioSubst() {
        return idTwFuncionarioSubst;
    }

    public void setIdTwFuncionarioSubst(String idTwFuncionarioSubst) {
        this.idTwFuncionarioSubst = idTwFuncionarioSubst;
    }

    public String getDataSubstituicao() {
        return dataSubstituicao;
    }

    public void setDataSubstituicao(String dataSubstituicao) {
        this.dataSubstituicao = dataSubstituicao;
    }

    public Collection<EmsPecas> getEmsPecasCollection() {
        return emsPecasCollection;
    }

    public void setEmsPecasCollection(Collection<EmsPecas> emsPecasCollection) {
        this.emsPecasCollection = emsPecasCollection;
    }

    public EmsProposta getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(EmsProposta idProposta) {
        this.idProposta = idProposta;
    }

    public BigDecimal getValorPecas() {
		return valorPecas;
	}

	public void setValorPecas(BigDecimal valorPecas) {
		this.valorPecas = valorPecas;
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
        if (!(object instanceof EmsSegmento)) {
            return false;
        }
        EmsSegmento other = (EmsSegmento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsSegmento[ id=" + id + " ]";
    }
    
}
