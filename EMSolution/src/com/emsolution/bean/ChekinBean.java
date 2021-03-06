package com.emsolution.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.emsolution.entity.GeCheckIn;
import com.emsolution.entity.GeOsPalm;
import com.emsolution.entity.GeSituacaoOs;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.DateHelper;
import com.emsolution.util.IConstantAccess;

public class ChekinBean {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Long id;
	private String data;
	private String modelo;
	private Long idModelo;
	private Long idPrefixo;
	private String numeroOs;
	private String cliente;
	private String contato;
	private String telefone;
	private String tecnico;
	private String horimetro;
	private String familia;
	private Long idFamilia;
	private String maquina;
	private String serie;
	private String equipamento;
	private String codCliente;
	private String filial;
	private String hasSendDbs;
	private String hasSendDataOrcamento;
	private String hasConclusaoServico;
	private Long idOsPalm;
	private String hasSendDataAprovacao;
	private String dataPrevisaoEntrega;
	private String hasSendDataPrevisao;
	private String hasSendPecasDbs;
	private String tipoCliente;
	private String siglaIndicadorGarantia;
	private String tipoOperacao;
	private String tipoRejeicao;
	private String obs;
	private String isRemoveSegmento;
	private String nomeImagemStatus;
	private String obsCrm;
	private String statusCrm;
	private String obsOs;
	private Long validadeProposta;
	private Long idTipoFrete;
	private String descricaoTipoFrete;
	private List<ServicoTerceirosBean> servicoTerceirosList;
	private String printPdf;
	private String idEquipamento;
	private String isLiberadoPDigitador;
	private String isLiberadoPOrcamentista;
	private String isLiberadoPConsultor;
	private String statusNegociacaoConsultor;
	private String obsNegociacaoConsultor;
	private String idFuncionarioConsultor;
	private String codErroDbs;
	private String dataOrcamento;
	private String descricaoNegociacaoConsultor;
	private String dataAprovacao;
	private String dataTerminoServico;
	private String dataFaturamento;
	private String dataLiberacaoPecas;
	private String obsProposta;
	private String backgroundColor;
	private String hasDataEntregaPedidos;
	private String isOpenOs;
	private String isMoreThirtyDay;
	
	private String jobControl;

	public ChekinBean() {
		servicoTerceirosList = new java.util.ArrayList<ServicoTerceirosBean>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumeroOs() {
		return numeroOs;
	}

	public void setNumeroOs(String numeroOs) {
		this.numeroOs = numeroOs;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getHorimetro() {
		return horimetro;
	}

	public void setHorimetro(String horimetro) {
		this.horimetro = horimetro;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}

	public Long getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(Long idFamilia) {
		this.idFamilia = idFamilia;
	}

	public String getIdEquipamento() {
		return idEquipamento;
	}

	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public String getHasSendDbs() {
		return hasSendDbs;
	}

	public void setHasSendDbs(String hasSendDbs) {
		this.hasSendDbs = hasSendDbs;
	}

	public String getHasSendDataOrcamento() {
		return hasSendDataOrcamento;
	}

	public void setHasSendDataOrcamento(String hasSendDataOrcamento) {
		this.hasSendDataOrcamento = hasSendDataOrcamento;
	}

	public Long getIdOsPalm() {
		return idOsPalm;
	}

	public void setIdOsPalm(Long idOsPalm) {
		this.idOsPalm = idOsPalm;
	}

	public Long getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}

	public Long getIdPrefixo() {
		return idPrefixo;
	}

	public void setIdPrefixo(Long idPrefixo) {
		this.idPrefixo = idPrefixo;
	}

	public String getHasSendDataAprovacao() {
		return hasSendDataAprovacao;
	}

	public void setHasSendDataAprovacao(String hasSendDataAprovacao) {
		this.hasSendDataAprovacao = hasSendDataAprovacao;
	}

	public String getDataPrevisaoEntrega() {
		return dataPrevisaoEntrega;
	}

	public void setDataPrevisaoEntrega(String dataPrevisaoEntrega) {
		this.dataPrevisaoEntrega = dataPrevisaoEntrega;
	}

	public String getHasSendDataPrevisao() {
		return hasSendDataPrevisao;
	}

	public void setHasSendDataPrevisao(String hasSendDataPrevisao) {
		this.hasSendDataPrevisao = hasSendDataPrevisao;
	}

	public String getHasConclusaoServico() {
		return hasConclusaoServico;
	}

	public void setHasConclusaoServico(String hasConclusaoServico) {
		this.hasConclusaoServico = hasConclusaoServico;
	}

	public String getHasSendPecasDbs() {
		return hasSendPecasDbs;
	}

	public void setHasSendPecasDbs(String hasSendPecasDbs) {
		this.hasSendPecasDbs = hasSendPecasDbs;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String clienteInter) {
		this.tipoCliente = clienteInter;
	}

	public String getDescricaoTipoFrete() {
		return descricaoTipoFrete;
	}

	public void setDescricaoTipoFrete(String descricaoTipoFrete) {
		this.descricaoTipoFrete = descricaoTipoFrete;
	}

	public String getSiglaIndicadorGarantia() {
		return siglaIndicadorGarantia;
	}

	public void setSiglaIndicadorGarantia(String siglaIndicadorGarantia) {
		this.siglaIndicadorGarantia = siglaIndicadorGarantia;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOpercacao) {
		this.tipoOperacao = tipoOpercacao;
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

	public String getIsRemoveSegmento() {
		return isRemoveSegmento;
	}

	public void setIsRemoveSegmento(String isRemoveSegmento) {
		this.isRemoveSegmento = isRemoveSegmento;
	}

	public String getNomeImagemStatus() {
		return nomeImagemStatus;
	}

	public void setNomeImagemStatus(String nomeImagemStatus) {
		this.nomeImagemStatus = nomeImagemStatus;
	}

	public String getStatusCrm() {
		return statusCrm;
	}

	public void setStatusCrm(String statusCrm) {
		this.statusCrm = statusCrm;
	}

	public String getObsCrm() {
		return obsCrm;
	}

	public void setObsCrm(String obsCrm) {
		this.obsCrm = obsCrm;
	}

	public String getObsOs() {
		return obsOs;
	}

	public void setObsOs(String obsOs) {
		this.obsOs = obsOs;
	}

	

	public Long getValidadeProposta() {
		return validadeProposta;
	}

	public void setValidadeProposta(Long validadeProposta) {
		this.validadeProposta = validadeProposta;
	}

	public Long getIdTipoFrete() {
		return idTipoFrete;
	}

	public void setIdTipoFrete(Long idTipoFrete) {
		this.idTipoFrete = idTipoFrete;
	}

	public List<ServicoTerceirosBean> getServicoTerceirosList() {
		return servicoTerceirosList;
	}

	public void setServicoTerceirosList(
			List<ServicoTerceirosBean> servicoTerceirosList) {
		this.servicoTerceirosList = servicoTerceirosList;
	}

	public String getIsLiberadoPDigitador() {
		return isLiberadoPDigitador;
	}

	public void setIsLiberadoPDigitador(String isLiberadoPDigitador) {
		this.isLiberadoPDigitador = isLiberadoPDigitador;
	}

	public String getIsLiberadoPOrcamentista() {
		return isLiberadoPOrcamentista;
	}

	public void setIsLiberadoPOrcamentista(String isLiberadoPOrcamentista) {
		this.isLiberadoPOrcamentista = isLiberadoPOrcamentista;
	}

	public String getIsLiberadoPConsultor() {
		return isLiberadoPConsultor;
	}

	public void setIsLiberadoPConsultor(String isLiberadoPConsultor) {
		this.isLiberadoPConsultor = isLiberadoPConsultor;
	}

	public String getStatusNegociacaoConsultor() {
		return statusNegociacaoConsultor;
	}

	public void setStatusNegociacaoConsultor(String statusNegociacaoConsultor) {
		this.statusNegociacaoConsultor = statusNegociacaoConsultor;
	}

	public String getObsNegociacaoConsultor() {
		return obsNegociacaoConsultor;
	}

	public void setObsNegociacaoConsultor(String obsNegociacaoConsultor) {
		this.obsNegociacaoConsultor = obsNegociacaoConsultor;
	}

	public String getIdFuncionarioConsultor() {
		return idFuncionarioConsultor;
	}

	public void setIdFuncionarioConsultor(String idFuncionarioConsultor) {
		this.idFuncionarioConsultor = idFuncionarioConsultor;
	}

	public String getPrintPdf() {
		return printPdf;
	}

	public void setPrintPdf(String printPdf) {
		this.printPdf = printPdf;
	}

	public String getCodErroDbs() {
		return codErroDbs;
	}

	public void setCodErroDbs(String codErroDbs) {
		this.codErroDbs = codErroDbs;
	}

	public String getDataOrcamento() {
		return dataOrcamento;
	}

	public void setDataOrcamento(String dataOrcamento) {
		this.dataOrcamento = dataOrcamento;
	}

	public String getDescricaoNegociacaoConsultor() {
		return descricaoNegociacaoConsultor;
	}

	public void setDescricaoNegociacaoConsultor(String descricaoNegociacaoConsultor) {
		this.descricaoNegociacaoConsultor = descricaoNegociacaoConsultor;
	}

	public String getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getDataTerminoServico() {
		return dataTerminoServico;
	}

	public void setDataTerminoServico(String dataTerminoServico) {
		this.dataTerminoServico = dataTerminoServico;
	}

	public String getDataFaturamento() {
		return dataFaturamento;
	}

	public void setDataFaturamento(String dataFaturamento) {
		this.dataFaturamento = dataFaturamento;
	}

	public String getJobControl() {
		return jobControl;
	}

	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}

	public String getObsProposta() {
		return obsProposta;
	}

	public void setObsProposta(String obsProposta) {
		this.obsProposta = obsProposta;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getDataLiberacaoPecas() {
		return dataLiberacaoPecas;
	}

	public void setDataLiberacaoPecas(String dataLiberacaoPecas) {
		this.dataLiberacaoPecas = dataLiberacaoPecas;
	}

	public String getHasDataEntregaPedidos() {
		return hasDataEntregaPedidos;
	}

	public void setHasDataEntregaPedidos(String hasDataEntregaPedidos) {
		this.hasDataEntregaPedidos = hasDataEntregaPedidos;
	}

	public String getIsOpenOs() {
		return isOpenOs;
	}

	public void setIsOpenOs(String isOpenOs) {
		this.isOpenOs = isOpenOs;
	}

	public String getIsMoreThirtyDay() {
		return isMoreThirtyDay;
	}

	public void setIsMoreThirtyDay(String isMoreThirtyDay) {
		this.isMoreThirtyDay = isMoreThirtyDay;
	}

	public void fromBean(GeCheckIn bean, GeSituacaoOs os, GeOsPalm palm, String dataLiberacaoPecas, String dataTerminoServico, UsuarioBean usuarioBean) {
		setIsOpenOs(bean.getIsOpenOs());
		setId(bean.getId());
		setTipoOperacao(palm.getTipoOperacao());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		setData(format.format(bean.getDataEmissao()));
		setModelo(bean.getIdOsPalm().getModelo());
		setNumeroOs(bean.getNumeroOs());
		setCliente(bean.getIdOsPalm().getCliente());
		setContato(bean.getIdOsPalm().getContato());
		setTelefone(bean.getIdOsPalm().getTelefone());
		setTecnico(bean.getIdOsPalm().getTecnico());
		if (bean.getIdOsPalm().getHorimetro() != null) {
			setHorimetro(String.valueOf(bean.getIdOsPalm().getHorimetro()));
		} else {
			setHorimetro("");
		}
		setIdEquipamento(bean.getIdEquipamento());
		setFamilia(bean.getIdOsPalm().getIdFamilia().getDescricao());
		setIdFamilia(bean.getIdOsPalm().getIdFamilia().getId());
		setMaquina(bean.getIdOsPalm().getEquipamento());
		setSerie(bean.getIdOsPalm().getSerie());
		setEquipamento(bean.getIdOsPalm().getEquipamento());
		setCodCliente(bean.getCodCliente());
		setFilial(bean.getIdOsPalm().getFilial());
		setHasSendDbs((os.getDataEntregaPedidos() != null) ? "N" : "S");
		setHasDataEntregaPedidos((os.getDataEntregaPedidos() != null) ? "S" : "N");
		setHasSendDataOrcamento(bean.getHasSendDataOrcamento());
		setIdOsPalm(bean.getIdOsPalm().getIdosPalm());
		setHasSendDataAprovacao(bean.getHasSendDataAprovacao());
		setHasSendDataPrevisao(bean.getHasSendDataPrevisao());
		setHasConclusaoServico(((os.getDataTerminoServico() != null) ? "S" : "N"));
		setHasSendPecasDbs((bean.getHasSendPecasDbs() != null) ? bean.getHasSendPecasDbs() : "N");
		setTipoCliente(bean.getTipoCliente());
		setSiglaIndicadorGarantia(bean.getSiglaIndicadorGarantia());
		setTipoRejeicao(os.getTipoRejeicao());
		setObs(os.getObs());
		setObsCrm(bean.getObservacao());
		setCodErroDbs(bean.getCodErroDbs());
		//setObsCrm(bean.getObservacao());
		setValidadeProposta(bean.getValidadeProposta());
		if(bean.getIdTipoFrete() != null){
			setIdTipoFrete(bean.getIdTipoFrete().getId());
			setDescricaoTipoFrete(bean.getIdTipoFrete().getTipoFrete());
		}
			
		setIsLiberadoPOrcamentista(bean.getIsLiberadoPOrcamentista());
		if(bean.getStatusNegociacaoConsultor() != null && bean.getStatusNegociacaoConsultor().equals("A")){
			setIsLiberadoPOrcamentista("S");
		}
		
		setIsLiberadoPConsultor(bean.getIsLiberadoPConsultor());
		setStatusNegociacaoConsultor(bean.getStatusNegociacaoConsultor());
		setObsNegociacaoConsultor(bean.getObsNegociacaoConsultor());
		setDescricaoNegociacaoConsultor(getStatusNegociacaoConsultorStr(bean.getStatusNegociacaoConsultor(), dataLiberacaoPecas, bean.getIsLiberadoPConsultor(), bean.getIsLiberadoPOrcamentista(), bean.getNumeroOs(), os, usuarioBean));
		setJobControl(bean.getJobControl());
		setObsProposta(bean.getObsProposta());
		if(os.getDataOrcamento() != null){
			setDataOrcamento(dateFormat.format(os.getDataOrcamento()));
		}
		if(os.getDataAprovacao() != null){
			setDataAprovacao(dateFormat.format(os.getDataAprovacao()));
		}
		if (os.getDataPrevisaoEntrega() != null) {
			setDataPrevisaoEntrega(dateFormat.format(os.getDataPrevisaoEntrega()));
			double diasPrevisaoEntrega = DateHelper.diferencaEmDia(new Date(), os.getDataPrevisaoEntrega());
			if(diasPrevisaoEntrega <= 2 && diasPrevisaoEntrega >= 0){
				setBackgroundColor("0xFF9900");
			}else{
				setBackgroundColor("0xFFFFFF");
			}
		}else{
			setBackgroundColor("0xFFFFFF");
		}
		if (os.getDataTerminoServico() != null) {
			setDataTerminoServico(dateFormat.format(os.getDataTerminoServico()));
		}
		setIsLiberadoPDigitador(bean.getIsLiberadoPDigitador());
		if(dataLiberacaoPecas != null && dataLiberacaoPecas.length() > 2){//dataLiberacaoPecas > 2 significa que é uma data
			setDataLiberacaoPecas(dataLiberacaoPecas);
		} else if(bean.getStatusNegociacaoConsultor() != null && bean.getStatusNegociacaoConsultor().equals("A")){
			if(getDataLiberacaoPecas() == null){
				setDataLiberacaoPecas("APROVADO");//Significa que a OS foi aprovada e não pode mais ser liberada pelo encarregado
			}
		}
		setDataTerminoServico(dataTerminoServico);			
		
		if (os.getDataFaturamento() != null) {
			setDataFaturamento(dateFormat.format(os.getDataFaturamento()));
		}
		
		setNomeImagemStatus(getNomeImagemStatus(os.getDataOrcamento()));
		setIsMoreThirtyDay("N");
		if(os.getDataAprovacao() == null && os.getDataOrcamento() != null){
			Date diaAtual = new Date();
			double dias = DateHelper.diferencaEmDia(os.getDataOrcamento(), diaAtual);
			if(dias > 30){
				setIsMoreThirtyDay("S");
			}
		}
		
	}
		
		private String getNomeImagemStatus(Date dataOrcamento){
			Date dataAtual = new Date();
			String imagemSatus = null;
			if(dataOrcamento != null){
				double diasDecorridos = DateHelper.diferencaEmDia(dataOrcamento, dataAtual);
				if(diasDecorridos >= 30d && diasDecorridos < 60d) {
					imagemSatus = "img/LA.png";
				}else if(diasDecorridos >= 60d){
					imagemSatus = "img/CN.png";
				}else{
					imagemSatus = "img/CE.png";
				}
			}else {
				imagemSatus = "img/CE.png";
			}		
			
		return imagemSatus;
		}
	
	private String getStatusNegociacaoConsultorStr(String statusNegociacaoConsultor, String isLiberadoPDigitador, String isLiberadoPConsultor, String isLiberadoPOrcamentista, String numeroOs, GeSituacaoOs situacao, UsuarioBean usuarioBean){
		if(statusNegociacaoConsultor != null && statusNegociacaoConsultor.equals("A") && situacao.getDataFaturamento() == null){
			
			if(usuarioBean.getJobControl() != null && (usuarioBean.getJobControl().equals("TR") || usuarioBean.getJobControl().equals("MT"))){
				Connection con = null;

				try {
					con =ConectionDbs.getConnecton();	
					Statement statement = con.createStatement();
					String SQL = "select distinct CSCC from "+IConstantAccess.LIB_DBS+".PCPCOHD0 p, "+IConstantAccess.LIB_DBS+".WOPHDRS0 w"+
					" WHERE p.WONO = w.WONO"+
					" AND trim(p.WONO) = '"+numeroOs+"'"+
					" AND p.DOCSU = 'B'";
					ResultSet rs = statement.executeQuery(SQL);
					String segmentos = "";
					while(rs.next()){
						segmentos += rs.getString("CSCC").trim()+"-";
					}
					if(segmentos.length() > 0){
						segmentos = segmentos.substring(0, segmentos.length() - 1);
						return "APROVADA BO "+segmentos;	
					}
					//				if(rs.next()){
					//					return "APROVADA BO";	
					//				}

				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(con != null){
							con.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
			}
			return "APROVADA";
		} else if(situacao.getDataFaturamento() != null && !"R".equals(statusNegociacaoConsultor)){ 
			return "FATURADA";	
		} else if(statusNegociacaoConsultor != null && statusNegociacaoConsultor.equals("R")){
			return "REJEITADA";
		} else if(statusNegociacaoConsultor != null && statusNegociacaoConsultor.equals("P")){
			return "REJ. PARCIALMENTE";
		} else if(isLiberadoPDigitador != null && ("N".equals(isLiberadoPOrcamentista) || isLiberadoPOrcamentista == null)){
			return "DIGITADOR";
		} else if("S".equals(isLiberadoPConsultor)){
			return "CONSULTOR";
		} else if("S".equals(isLiberadoPOrcamentista) && ("N".equals(isLiberadoPConsultor) || isLiberadoPConsultor == null)){
			return "ORÇAMENTISTA";
		}if(numeroOs != null && numeroOs.length() > 0){
			return "ENCARREGADO";
		} else if(situacao.getDataFaturamento() != null){ 
			return "FATURADA";
		}else{
			return "CRIAR OS";
		}
	}

}
