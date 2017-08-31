/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emsolution.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_PROPOSTA")
public class EmsProposta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NUM_SERIE")
    private String numSerie;
    @Column(name = "DATA_OPT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOpt;
    @Column(name = "ID_FUNCIONARIO")
    private String idFuncionario;
    @Column(name = "JOB_CONTROL")
    private String jobControl;
    @Column(name = "TIPO_CLIENTE")
    private String tipoCliente;
    @Column(name = "COD_CLIENTE_EXT")
    private String codClienteExt;
    @Column(name = "COD_CLIENTE_INTER")
    private String codClienteInter;
    @Column(name = "COD_CLIENTE_GARANTIA")
    private String codClienteGarantia;
    @Column(name = "ID_EQUIPAMENTO")
    private String idEquipamento;
    @Column(name = "DATA_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;
    @Column(name = "DATA_ACEITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAceite;
    @Column(name = "DATA_FINALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFinalizacao;
    @Column(name = "DATA_REJEICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRejeicao;
    @Column(name = "FATOR_CLIENTE")
    private BigDecimal fatorCliente;
    @Column(name = "TELEFONE")
    private String telefone;
    @OneToMany(mappedBy = "idProposta")
    private Collection<EmsSegmento> emsSegmentoCollection;
    @JoinColumn(name = "ID_TIPO_OPT", referencedColumnName = "ID")
    @ManyToOne
    private EmsTipoOportunidade idTipoOpt;
    @JoinColumn(name = "ID_STATUS_OPT", referencedColumnName = "ID")
    @ManyToOne
    private EmsStatusOportunidade idStatusOpt;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "ID_FAMILIA_CAMPO")
    private BigDecimal idFamiliaCampo;
    @Column(name = "ID_FAMILIA_OFICINA")
    private BigDecimal idFamiliaOficina;
    @Column(name = "PREFIXO")
    private String prefixo;
    @Column(name = "POSSUI_SUB_TRIBUTARIA")
    private String possuiSubTributaria;
    @Column(name = "IS_FIND_SUB_TRIBUTARIA")
    private String isFindSubTributaria;
    @Column(name = "OBS")
    private String obs;
    @Column(name = "FILIAL")
    private Long filial;
    @Column(name = "FATOR_URGENCIA")
    private String fatorUrgencia;
    @Column(name = "HORIMETRO")
    private BigDecimal horimetro;
    @Column(name = "IS_LOCK")
    private String isLock;
    @Column(name = "ID_FUNCIONARIO_LOCK")
    private String idFuncionarioLock;
    @Column(name = "ESTIMATE_BY_FUNCIONARIO_LOCK")
    private String estimateByFuncionarioLock;
    @Column(name = "VALOR_MAO_DE_OBRA")
    private BigDecimal valorMaoDeObra;
    @Column(name = "VALOR_PECAS")
    private BigDecimal valorPecas;
    @Column(name = "TOTAL_ORCAMENTO")
    private BigDecimal totalOrcamento;
    @Column(name = "VALOR_DESLOCAMENTO")
    private BigDecimal valorDeslocamento;
    @Column(name = "MOTIVO_REJEICAO")
    private String motivoRejeicao;
    @Column(name = "ORIGEM_NEGOCIO")
    private String origemNegocio;
    

	public EmsProposta() {
    }

    public EmsProposta(Long id) {
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

    public Date getDataOpt() {
        return dataOpt;
    }

    public void setDataOpt(Date dataOpt) {
        this.dataOpt = dataOpt;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getJobControl() {
        return jobControl;
    }

    public void setJobControl(String jobControl) {
        this.jobControl = jobControl;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCodClienteExt() {
        return codClienteExt;
    }

    public void setCodClienteExt(String codClienteExt) {
        this.codClienteExt = codClienteExt;
    }

    public String getCodClienteInter() {
        return codClienteInter;
    }

    public void setCodClienteInter(String codClienteInter) {
        this.codClienteInter = codClienteInter;
    }

    public String getCodClienteGarantia() {
        return codClienteGarantia;
    }

    public void setCodClienteGarantia(String codClienteGarantia) {
        this.codClienteGarantia = codClienteGarantia;
    }

    public String getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(String idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getDataAceite() {
        return dataAceite;
    }

    public void setDataAceite(Date dataAceite) {
        this.dataAceite = dataAceite;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public Date getDataRejeicao() {
        return dataRejeicao;
    }

    public void setDataRejeicao(Date dataRejeicao) {
        this.dataRejeicao = dataRejeicao;
    }

    public Collection<EmsSegmento> getEmsSegmentoCollection() {
        return emsSegmentoCollection;
    }

    public void setEmsSegmentoCollection(Collection<EmsSegmento> emsSegmentoCollection) {
        this.emsSegmentoCollection = emsSegmentoCollection;
    }

    public EmsTipoOportunidade getIdTipoOpt() {
        return idTipoOpt;
    }

    public void setIdTipoOpt(EmsTipoOportunidade idTipoOpt) {
        this.idTipoOpt = idTipoOpt;
    }

    public EmsStatusOportunidade getIdStatusOpt() {
        return idStatusOpt;
    }

    public void setIdStatusOpt(EmsStatusOportunidade idStatusOpt) {
        this.idStatusOpt = idStatusOpt;
    }

    public BigDecimal getFatorCliente() {
		return fatorCliente;
	}

	public void setFatorCliente(BigDecimal fatorCliente) {
		this.fatorCliente = fatorCliente;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getIdFamiliaCampo() {
		return idFamiliaCampo;
	}

	public void setIdFamiliaCampo(BigDecimal idFamiliaCampo) {
		this.idFamiliaCampo = idFamiliaCampo;
	}

	public BigDecimal getIdFamiliaOficina() {
		return idFamiliaOficina;
	}

	public void setIdFamiliaOficina(BigDecimal idFamiliaOficina) {
		this.idFamiliaOficina = idFamiliaOficina;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public String getPossuiSubTributaria() {
		return possuiSubTributaria;
	}

	public void setPossuiSubTributaria(String possuiSubTributaria) {
		this.possuiSubTributaria = possuiSubTributaria;
	}

	public String getIsFindSubTributaria() {
		return isFindSubTributaria;
	}

	public void setIsFindSubTributaria(String isFindSubTributaria) {
		this.isFindSubTributaria = isFindSubTributaria;
	}

	public Long getFilial() {
		return filial;
	}

	public void setFilial(Long filial) {
		this.filial = filial;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getFatorUrgencia() {
		return fatorUrgencia;
	}

	public void setFatorUrgencia(String fatorUrgencia) {
		this.fatorUrgencia = fatorUrgencia;
	}

	public BigDecimal getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(BigDecimal horimetro) {
		this.horimetro = horimetro;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getIdFuncionarioLock() {
		return idFuncionarioLock;
	}

	public void setIdFuncionarioLock(String idFuncionarioLock) {
		this.idFuncionarioLock = idFuncionarioLock;
	}

	public String getEstimateByFuncionarioLock() {
		return estimateByFuncionarioLock;
	}

	public void setEstimateByFuncionarioLock(String estimateByFuncionarioLock) {
		this.estimateByFuncionarioLock = estimateByFuncionarioLock;
	}

	public BigDecimal getValorMaoDeObra() {
		return valorMaoDeObra;
	}

	public void setValorMaoDeObra(BigDecimal valorMaoDeObra) {
		this.valorMaoDeObra = valorMaoDeObra;
	}

	public BigDecimal getValorPecas() {
		return valorPecas;
	}

	public void setValorPecas(BigDecimal valorPecas) {
		this.valorPecas = valorPecas;
	}

	public BigDecimal getTotalOrcamento() {
		return totalOrcamento;
	}

	public void setTotalOrcamento(BigDecimal totalOrcamento) {
		this.totalOrcamento = totalOrcamento;
	}

	public BigDecimal getValorDeslocamento() {
		return valorDeslocamento;
	}

	public void setValorDeslocamento(BigDecimal valorDeslocamento) {
		this.valorDeslocamento = valorDeslocamento;
	}

	public String getMotivoRejeicao() {
		return motivoRejeicao;
	}

	public void setMotivoRejeicao(String motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}

	public String getOrigemNegocio() {
		return origemNegocio;
	}

	public void setOrigemNegocio(String origemNegocio) {
		this.origemNegocio = origemNegocio;
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
        if (!(object instanceof EmsProposta)) {
            return false;
        }
        EmsProposta other = (EmsProposta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.emsolution.entity.EmsProposta[ id=" + id + " ]";
    }
    
}
