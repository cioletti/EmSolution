/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emsolution.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rodrigo
 */
@Entity
@Table(name = "EMS_DIAGNOSTICO_VIEW")
public class EmsDiagnosticoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NUMERO_SERIE")
    private String numeroSerie;
    @Column(name = "FILIAL")
    private Long filial;
    @Column(name = "MODELO")
    private String modelo;
    @Column(name = "NOME_CLIENTE")
    private String nomeCliente;
    @Column(name = "NOME_FILIAL")
    private String nomeFilial;
    @Column(name = "BACKLOG_PMP")
    private String backlogPmp;
    @Column(name = "BACKLOG_CAMPO")
    private String backlogCampo;
    @Column(name = "DATA_BACKLOG_PMP")
    private String dataBacklogPmp;
    @Column(name = "COR")
    private String cor;
    @Column(name = "NUMERO_DIAGNOSTICOS")
    private Long numeroDiagnosticos;	
    @Column(name = "COR_SOS")
    private String corSos;
    @Column(name = "DATA_COLETA")
    private String dataColeta;
    @Column(name = "TOTAL_SOS")
    private Long totalSos;
    @Column(name = "NORMAL_SOS")
    private Long normalSos;
    @Column(name = "MONITORAR_SOS")
    private Long monitorarSos;
    @Column(name = "CRITICO_SOS")
    private Long criticoSos;
    @Column(name = "IS_LOCK_MY")
    private String isLockMy;
    @Column(name = "DATA_ACEITE_PMP")
    private String dataAceitePmp;
    @Column(name = "TIPO_CONTRATO_PMP")
    private String tipoContratoPmp;
    @Column(name = "DATA_INSPECAO_PMP")
    private String dataInspecaoPmp;
    @Column(name = "DATA_INSPECAO_CAMPO")
    private String dataInspecaoCampo;
    @Column(name = "ID_OS_PALM_PMP")
    private Long idOsPalmPmp;
    @Column(name = "ID_OS_PALM_CAMPO")
    private Long idOsPalmCampo;
    @Column(name = "HORIMETRO")
    private Long horimetro;
    @Column(name = "DATA_ATUALIZACAO_HORIMETRO")
    private String dataAtualizacaoHorimetro;
    @Column(name = "LEVEL1")
    private Long level1;
    @Column(name = "LEVEL2")
    private Long level2;
    @Column(name = "LEVEL3")
    private Long level3;
    @Column(name = "ESTIMATE_BY_FUNCIONARIO_LOCK")
    private String estimateByFuncionarioLock;
    @Column(name = "DATA_INICIO_GARANTIA")
    private String dataInicioGarantia;
    @Column(name = "DATA_FIM_GARANTIA")
    private String dataFimGarantia;
    @Column(name = "DATA_IMPORTACAO_SOS")
    private String dataImportacaoSos;
    @Column(name = "DATA_IMPORTACAO_SMU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataImportacaoSmu;
    @Column(name = "COR_SMU")
    private String corSmu;
    @Column(name = "TOTAL_SMU")
    private Long totalSmu;

    public String getCorSmu() {
		return corSmu;
	}

	public void setCorSmu(String corSmu) {
		this.corSmu = corSmu;
	}

	public Long getTotalSmu() {
		return totalSmu;
	}

	public void setTotalSmu(Long totalSmu) {
		this.totalSmu = totalSmu;
	}

	public EmsDiagnosticoView() {
    }

    public EmsDiagnosticoView(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Long getFilial() {
        return filial;
    }

    public void setFilial(Long filial) {
        this.filial = filial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        this.nomeFilial = nomeFilial;
    }

    public String getBacklogPmp() {
        return backlogPmp;
    }

    public void setBacklogPmp(String backlogPmp) {
        this.backlogPmp = backlogPmp;
    }

    public String getBacklogCampo() {
        return backlogCampo;
    }

    public void setBacklogCampo(String backlogCampo) {
        this.backlogCampo = backlogCampo;
    }

    public String getDataBacklogPmp() {
        return dataBacklogPmp;
    }

    public void setDataBacklogPmp(String dataBacklogPmp) {
        this.dataBacklogPmp = dataBacklogPmp;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getNumeroDiagnosticos() {
        return numeroDiagnosticos;
    }

    public void setNumeroDiagnosticos(Long numeroDiagnosticos) {
        this.numeroDiagnosticos = numeroDiagnosticos;
    }

    public String getCorSos() {
        return corSos;
    }

    public void setCorSos(String corSos) {
        this.corSos = corSos;
    }

    public String getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(String dataColeta) {
        this.dataColeta = dataColeta;
    }

    public Long getTotalSos() {
        return totalSos;
    }

    public void setTotalSos(Long totalSos) {
        this.totalSos = totalSos;
    }

    public Long getNormalSos() {
        return normalSos;
    }

    public void setNormalSos(Long normalSos) {
        this.normalSos = normalSos;
    }

    public Long getMonitorarSos() {
        return monitorarSos;
    }

    public void setMonitorarSos(Long monitorarSos) {
        this.monitorarSos = monitorarSos;
    }

    public Long getCriticoSos() {
        return criticoSos;
    }

    public void setCriticoSos(Long criticoSos) {
        this.criticoSos = criticoSos;
    }

    public String getIsLockMy() {
        return isLockMy;
    }

    public void setIsLockMy(String isLockMy) {
        this.isLockMy = isLockMy;
    }

    public String getDataAceitePmp() {
        return dataAceitePmp;
    }

    public void setDataAceitePmp(String dataAceitePmp) {
        this.dataAceitePmp = dataAceitePmp;
    }

    public String getTipoContratoPmp() {
        return tipoContratoPmp;
    }

    public void setTipoContratoPmp(String tipoContratoPmp) {
        this.tipoContratoPmp = tipoContratoPmp;
    }

    public String getDataInspecaoPmp() {
        return dataInspecaoPmp;
    }

    public void setDataInspecaoPmp(String dataInspecaoPmp) {
        this.dataInspecaoPmp = dataInspecaoPmp;
    }

    public String getDataInspecaoCampo() {
        return dataInspecaoCampo;
    }

    public void setDataInspecaoCampo(String dataInspecaoCampo) {
        this.dataInspecaoCampo = dataInspecaoCampo;
    }

    public Long getIdOsPalmPmp() {
        return idOsPalmPmp;
    }

    public void setIdOsPalmPmp(Long idOsPalmPmp) {
        this.idOsPalmPmp = idOsPalmPmp;
    }

    public Long getIdOsPalmCampo() {
        return idOsPalmCampo;
    }

    public void setIdOsPalmCampo(Long idOsPalmCampo) {
        this.idOsPalmCampo = idOsPalmCampo;
    }

    public Long getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(Long horimetro) {
        this.horimetro = horimetro;
    }

    public String getDataAtualizacaoHorimetro() {
        return dataAtualizacaoHorimetro;
    }

    public void setDataAtualizacaoHorimetro(String dataAtualizacaoHorimetro) {
        this.dataAtualizacaoHorimetro = dataAtualizacaoHorimetro;
    }

    public Long getLevel1() {
        return level1;
    }

    public void setLevel1(Long level1) {
        this.level1 = level1;
    }

    public Long getLevel2() {
        return level2;
    }

    public void setLevel2(Long level2) {
        this.level2 = level2;
    }

    public Long getLevel3() {
        return level3;
    }

    public void setLevel3(Long level3) {
        this.level3 = level3;
    }

    public String getEstimateByFuncionarioLock() {
		return estimateByFuncionarioLock;
	}

	public void setEstimateByFuncionarioLock(String estimateByFuncionarioLock) {
		this.estimateByFuncionarioLock = estimateByFuncionarioLock;
	}

	public String getDataInicioGarantia() {
		return dataInicioGarantia;
	}

	public void setDataInicioGarantia(String dataInicioGarantia) {
		this.dataInicioGarantia = dataInicioGarantia;
	}

	public String getDataFimGarantia() {
		return dataFimGarantia;
	}

	public void setDataFimGarantia(String dataFimGarantia) {
		this.dataFimGarantia = dataFimGarantia;
	}

	public String getDataImportacaoSos() {
		return dataImportacaoSos;
	}

	public void setDataImportacaoSos(String dataImportacaoSos) {
		this.dataImportacaoSos = dataImportacaoSos;
	}

	public Date getDataImportacaoSmu() {
		return dataImportacaoSmu;
	}

	public void setDataImportacaoSmu(Date dataImportacaoSmu) {
		this.dataImportacaoSmu = dataImportacaoSmu;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSerie != null ? numeroSerie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmsDiagnosticoView)) {
            return false;
        }
        EmsDiagnosticoView other = (EmsDiagnosticoView) object;
        if ((this.numeroSerie == null && other.numeroSerie != null) || (this.numeroSerie != null && !this.numeroSerie.equals(other.numeroSerie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EmsDiagnosticoView[ numeroSerie=" + numeroSerie + " ]";
    }
    
}
