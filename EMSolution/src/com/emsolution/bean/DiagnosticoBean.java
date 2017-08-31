package com.emsolution.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DiagnosticoBean implements Comparable<DiagnosticoBean>{
	SimpleDateFormat dateFormatConverter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private Long messageId;
	private String receivedTime;
	private String filial;
	private String modelo;
	private String numeroSerie;
	private String nomeCliente;
	private Long numerDiagnosticos;
	private Long csa;
	private DetalhesDiagnosticoBean detalhesDiagnosticoBean;
	private String cor;
	private String backlog;
	private String backlogCampo;
	private Integer totalSos;
	private String corSos;
	private Integer pip;
	private Integer psp;
	private String inicioGarantia;
	private String fimGarantia;
	private Boolean isOportunidade;
	private String isLock;
	private String isLockMy;
	private String estimateByFuncionarioLock;
	private Long numLinhas;
	private String dataBacklogPmp;
	private String dataBacklogCampo;
	private String idFilial;
	private String dataColeta;
	private String dataImportacaoSos;
	private String corSmu;
	private Long totalSmu;
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
	public String getFilial() {
		return filial;
	}
	public void setFilial(String filial) {
		this.filial = filial;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public Long getNumerDiagnosticos() {
		return numerDiagnosticos;
	}
	public void setNumerDiagnosticos(Long numerDiagnosticos) {
		this.numerDiagnosticos = numerDiagnosticos;
	}
	public Long getCsa() {
		return csa;
	}
	public void setCsa(Long csa) {
		this.csa = csa;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public DetalhesDiagnosticoBean getDetalhesDiagnosticoBean() {
		return detalhesDiagnosticoBean;
	}
	public void setDetalhesDiagnosticoBean(
			DetalhesDiagnosticoBean detalhesDiagnosticoBean) {
		this.detalhesDiagnosticoBean = detalhesDiagnosticoBean;
	}
	public String getBacklog() {
		return backlog;
	}
	public void setBacklog(String backlog) {
		this.backlog = backlog;
	}
	public Integer getTotalSos() {
		return totalSos;
	}
	public void setTotalSos(Integer totalSos) {
		this.totalSos = totalSos;
	}
	public String getCorSos() {
		return corSos;
	}
	public void setCorSos(String corSos) {
		this.corSos = corSos;
	}
	public Integer getPip() {
		return pip;
	}
	public void setPip(Integer pip) {
		this.pip = pip;
	}
	public Integer getPsp() {
		return psp;
	}
	public void setPsp(Integer psp) {
		this.psp = psp;
	}
	public String getInicioGarantia() {
		return inicioGarantia;
	}
	public void setInicioGarantia(String inicioGarantia) {
		this.inicioGarantia = inicioGarantia;
	}
	public String getFimGarantia() {
		return fimGarantia;
	}
	public void setFimGarantia(String fimGarantia) {
		this.fimGarantia = fimGarantia;
	}
	public Boolean getIsOportunidade() {
		return isOportunidade;
	}
	public void setIsOportunidade(Boolean isOportunidade) {
		this.isOportunidade = isOportunidade;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getIsLockMy() {
		return isLockMy;
	}
	public void setIsLockMy(String isLockMy) {
		this.isLockMy = isLockMy;
	}
	public String getEstimateByFuncionarioLock() {
		return estimateByFuncionarioLock;
	}
	public void setEstimateByFuncionarioLock(String estimateByFuncionarioLock) {
		this.estimateByFuncionarioLock = estimateByFuncionarioLock;
	}
	public String getBacklogCampo() {
		return backlogCampo;
	}
	public void setBacklogCampo(String backlogCampo) {
		this.backlogCampo = backlogCampo;
	}
	public Long getNumLinhas() {
		return numLinhas;
	}
	public void setNumLinhas(Long numLinhas) {
		this.numLinhas = numLinhas;
	}

	public String getDataBacklogPmp() {
		return dataBacklogPmp;
	}
	public void setDataBacklogPmp(String dataBacklogPmp) {
		this.dataBacklogPmp = dataBacklogPmp;
	}
	public String getIdFilial() {
		return idFilial;
	}
	public void setIdFilial(String idFilial) {
		this.idFilial = idFilial;
	}
	public String getDataColeta() {
		return dataColeta;
	}
	public void setDataColeta(String dataColeta) {
		this.dataColeta = dataColeta;
	}
	public String getDataBacklogCampo() {
		return dataBacklogCampo;
	}
	public void setDataBacklogCampo(String dataBacklogCampo) {
		this.dataBacklogCampo = dataBacklogCampo;
	}
	public String getDataImportacaoSos() {
		return dataImportacaoSos;
	}
	public void setDataImportacaoSos(String dataImportacaoSos) {
		this.dataImportacaoSos = dataImportacaoSos;
	}
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
	@Override
	public int compareTo(DiagnosticoBean diagnosticoBean) {
		if(this.detalhesDiagnosticoBean.getDataInspecao() != null && diagnosticoBean.getDetalhesDiagnosticoBean().getDataInspecao() != null &&
				!this.detalhesDiagnosticoBean.getDataInspecao().equals("N/A") && !diagnosticoBean.getDetalhesDiagnosticoBean().getDataInspecao().equals("N/A")){
			try {
				return dateFormatConverter.parse(this.detalhesDiagnosticoBean.getDataInspecao()).compareTo(dateFormatConverter.parse(diagnosticoBean.getDetalhesDiagnosticoBean().getDataInspecao()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
}
