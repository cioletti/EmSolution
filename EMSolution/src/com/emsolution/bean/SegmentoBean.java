package com.emsolution.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.emsolution.entity.EmsSegmento;
import com.emsolution.entity.GeSegmento;
import com.emsolution.entity.TwFuncionario;
import com.emsolution.bean.OperacaoBean;

public class SegmentoBean {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private String numeroSegmento;
	private String descricao;
	private String cscc;
	private String jbcd;
	private String cptcd;
	private String cptcdStr;
	private String shopField;
	private Long id;
	private String jbcdStr;
	private String jbctStr;
	private Long idProposta;
	private List<PecasBean> pecasList;
	private List<OperacaoBean> operacaoList;
	private String horas;
	private String horasSubst;
	private String hasSendPecasDbsSegmento;
	private Integer qtdComp;
	private Integer qtdCompSubst;
	private String msgDbs;
	private String msgDocDbs;
	private String codErroDocDbs;
	private String observacao;
	private String idFuncionarioCriador;
	private String codErroDbs;
	private String havePecas;
	private String idFuncionarioPecas;
	private String nomeFuncionarioPecas;
	private String dataLiberacaoPecas;
	private String dataTerminoServico;
	private String numeroDoc;
	private String pedido;
	private List<SosBean> listSosAssociado;
	private List<ConfigAlarmeBean> listPlAssociado;
	private List<OportunidadePmpBean> listPmpAssociado;
	private List<OportunidadePmpBean> listCampoAssociado;
	private List<SmuBean> listSmuAssociado;

	
	public String getNumeroSegmento() {
		return numeroSegmento;
	}
	public void setNumeroSegmento(String numeroSegmento) {
		this.numeroSegmento = numeroSegmento;
	}
	public String getCscc() {
		return cscc;
	}
	public void setCscc(String cscc) {
		this.cscc = cscc;
	}
	public String getShopField() {
		return shopField;
	}
	public void setShopField(String shopField) {
		this.shopField = shopField;
	}
	public List<PecasBean> getPecasList() {
		return pecasList;
	}
	public void setPecasList(List<PecasBean> pecasList) {
		this.pecasList = pecasList;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getJbcdStr() {
		return jbcdStr;
	}
	public void setJbcdStr(String jbcdStr) {
		this.jbcdStr = jbcdStr;
	}
	public String getJbcd() {
		return jbcd;
	}
	public void setJbcd(String jbcd) {
		this.jbcd = jbcd;
	}
	public String getCptcd() {
		return cptcd;
	}
	public void setCptcd(String cptcd) {
		this.cptcd = cptcd;
	}
	public String getJbctStr() {
		return jbctStr;
	}
	public void setJbctStr(String jbctStr) {
		this.jbctStr = jbctStr;
	}
	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}
	public String getCptcdStr() {
		return cptcdStr;
	}
	public void setCptcdStr(String cptcdStr) {
		this.cptcdStr = cptcdStr;
	}

	public String getHasSendPecasDbsSegmento() {
		return hasSendPecasDbsSegmento;
	}
	public void setHasSendPecasDbsSegmento(String hasSendPecasDbsSegmento) {
		this.hasSendPecasDbsSegmento = hasSendPecasDbsSegmento;
	}
	public Integer getQtdComp() {
		return qtdComp;
	}
	public void setQtdComp(Integer qtdComp) {
		this.qtdComp = qtdComp;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getMsgDbs() {
		return msgDbs;
	}
	public void setMsgDbs(String msgDbs) {
		this.msgDbs = msgDbs;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getIdFuncionarioCriador() {
		return idFuncionarioCriador;
	}
	public void setIdFuncionarioCriador(String idFuncionarioCriador) {
		this.idFuncionarioCriador = idFuncionarioCriador;
	}
	public String getCodErroDbs() {
		return codErroDbs;
	}
	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}
	public String getHavePecas() {
		return havePecas;
	}
	public void setHavePecas(String havePecas) {
		this.havePecas = havePecas;
	}
	public String getIdFuncionarioPecas() {
		return idFuncionarioPecas;
	}
	public void setIdFuncionarioPecas(String idFuncionarioPecas) {
		this.idFuncionarioPecas = idFuncionarioPecas;
	}
	public String getDataLiberacaoPecas() {
		return dataLiberacaoPecas;
	}
	public void setDataLiberacaoPecas(String dataLiberacaoPecas) {
		this.dataLiberacaoPecas = dataLiberacaoPecas;
	}
	public String getDataTerminoServico() {
		return dataTerminoServico;
	}
	public void setDataTerminoServico(String dataTerminoServico) {
		this.dataTerminoServico = dataTerminoServico;
	}
	public String getNomeFuncionarioPecas() {
		return nomeFuncionarioPecas;
	}
	public void setNomeFuncionarioPecas(String nomeFuncionarioPecas) {
		this.nomeFuncionarioPecas = nomeFuncionarioPecas;
	}
	public String getNumeroDoc() {
		return numeroDoc;
	}
	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	public String getPedido() {
		return pedido;
	}
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	public String getHorasSubst() {
		return horasSubst;
	}
	public void setHorasSubst(String horasSubst) {
		this.horasSubst = horasSubst;
	}
	public Integer getQtdCompSubst() {
		return qtdCompSubst;
	}
	public void setQtdCompSubst(Integer qtdCompSubst) {
		this.qtdCompSubst = qtdCompSubst;
	}
	public List<SosBean> getListSosAssociado() {
		return listSosAssociado;
	}
	public void setListSosAssociado(List<SosBean> listSosAssociado) {
		this.listSosAssociado = listSosAssociado;
	}
	public List<ConfigAlarmeBean> getListPlAssociado() {
		return listPlAssociado;
	}
	public void setListPlAssociado(List<ConfigAlarmeBean> listPlAssociado) {
		this.listPlAssociado = listPlAssociado;
	}
	public List<OportunidadePmpBean> getListPmpAssociado() {
		return listPmpAssociado;
	}
	public void setListPmpAssociado(List<OportunidadePmpBean> listPmpAssociado) {
		this.listPmpAssociado = listPmpAssociado;
	}
	public Long getIdProposta() {
		return idProposta;
	}
	public void setIdProposta(Long idProposta) {
		this.idProposta = idProposta;
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
	public List<OperacaoBean> getOperacaoList() {
		return operacaoList;
	}
	public void setOperacaoList(List<OperacaoBean> operacaoList) {
		this.operacaoList = operacaoList;
	}
	public List<OportunidadePmpBean> getListCampoAssociado() {
		return listCampoAssociado;
	}
	public void setListCampoAssociado(List<OportunidadePmpBean> listCampoAssociado) {
		this.listCampoAssociado = listCampoAssociado;
	}
	public List<SmuBean> getListSmuAssociado() {
		return listSmuAssociado;
	}
	public void setListSmuAssociado(List<SmuBean> listSmuAssociado) {
		this.listSmuAssociado = listSmuAssociado;
	}
	public void toBean(EmsSegmento entity){
		entity.setNumeroSegmento(getNumeroSegmento());
		entity.setComCode(getCptcd());
		entity.setJobCode(getJbcd());
		entity.setJobControl(getJbctStr());
		//entity.setIdCheckin(entity.getIdCheckin());
		//entity.setId(getId());
		entity.setDataCriacao(new Date());
		entity.setDescricaoJobCode(getJbcdStr());
		entity.setJobControl(getJbctStr());
		entity.setHorasPrevista(getHoras());
		entity.setDescricaoCompCode(getCptcdStr());
		entity.setQtdComp(getQtdComp().longValue());
		entity.setIsCreateSegmento("S");
		entity.setCodErroDbs("00");
		entity.setMsgDbs("Segmento processado com sucesso!");
		entity.setIdFuncionarioCriador(this.getIdFuncionarioCriador());
		entity.setObservacao(getObservacao());
	}
	
	public void toBean(GeSegmento entity, EntityManager manager){
		entity.setNumeroSegmento(getNumeroSegmento());
		entity.setComCode(getCptcd());
		entity.setJobCode(getJbcd());
		entity.setJobControl(getJbctStr());
		//entity.setIdCheckin(entity.getIdCheckin());
		//entity.setId(getId());
		entity.setDescricaoJobCode(getJbcdStr());
		entity.setJobControl(getJbctStr());
		entity.setHorasPrevista(getHoras());
		entity.setDescricaoCompCode(getCptcdStr());
		entity.setQtdComp(getQtdComp());
		entity.setIsCreateSegmento("N");
		entity.setCodErroDbs("100");
		entity.setMsgDbs("Processando segmento no DBS, aguarde o retorno!");
		TwFuncionario funcionario = manager.find(TwFuncionario.class, this.getIdFuncionarioCriador());
		entity.setIdFuncionarioCriador(funcionario);
	}

	
	public void fromBean (EmsSegmento bean){
		setNumeroSegmento(bean.getNumeroSegmento());
		setJbcd(bean.getJobCode());
		setJbcdStr(bean.getDescricaoJobCode());
		setCptcd(bean.getComCode());
		setId(bean.getId());
		setJbctStr(bean.getJobControl());
		setHoras(bean.getHorasPrevista());
		setCptcdStr(bean.getDescricaoCompCode());
		
		
		setQtdComp(bean.getQtdComp().intValue());
		setDescricao(bean.getNumeroSegmento() +" - "+bean.getDescricaoJobCode()+" - "+bean.getDescricaoCompCode());
		setMsgDbs(bean.getMsgDbs());
		setIdFuncionarioCriador((bean.getIdFuncionarioCriador() == null)? "" : bean.getIdFuncionarioCriador());
		setCodErroDbs(bean.getCodErroDbs());
		setIdFuncionarioPecas(bean.getIdFuncionarioPecas());
		setNumeroDoc(bean.getNumDoc());
		setCodErroDocDbs(bean.getCodErroDocDbs());
		setObservacao(bean.getObservacao());
	}
	
}
