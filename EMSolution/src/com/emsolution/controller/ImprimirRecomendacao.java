package com.emsolution.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import com.emsolution.bean.ClienteBean;
import com.emsolution.bean.DiagnosticoBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.business.ClienteBusiness;
import com.emsolution.business.DiagnosticoBusiness;
import com.emsolution.business.ImportXmlBusiness;
import com.emsolution.entity.EmsConsumoCombustivel;
import com.emsolution.entity.EmsDetalhesProposta;
import com.emsolution.entity.EmsSegmento;
import com.emsolution.entity.ScValorKm;
import com.emsolution.entity.TwFilial;
import com.emsolution.util.Connection;
import com.emsolution.util.JpaUtil;

/**
 * Servlet implementation class ImprimirRecomendacao
 */
public class ImprimirRecomendacao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImprimirRecomendacao() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idProposta  = request.getParameter("idProposta");
		String imprimirComPecas  = request.getParameter("imprimirComPecas");
		String orcamentista = request.getParameter("orcamentista");
		UsuarioBean usuarioBean = (UsuarioBean) request.getSession().getAttribute("usuario");
		
		
		//JasperReport jasperReport = null; 
		JasperReport jasperReport = null; 
		//byte[] pdfInspecao = null;
		

		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();  
		String caminhoJasper = servletContext.getRealPath("WEB-INF/recomendacao/proposta.jasper"); 
		String pathSubreport = servletContext.getRealPath("WEB-INF/recomendacao/")+"/"; 

		//Carrega o arquivo com o jasperReport  
		EntityManager manager = null;
		java.sql.Connection con = null;
		java.sql.Connection conDbs = null;

		try{
			manager = JpaUtil.getInstance();
			//EmsProposta proposta = manager.find(EmsProposta.class, Long.valueOf(idProposta));
			Query query = manager.createQuery("from EmsDetalhesProposta where idProposta.id =:idProposta");
			query.setParameter("idProposta", Long.valueOf(idProposta));
			EmsDetalhesProposta detalhesProposta = (EmsDetalhesProposta)query.getSingleResult();
//			query = manager.createQuery("from ScValorKm");
//			ScValorKm valorKm = (ScValorKm)query.getSingleResult();
			ClienteBusiness business = new ClienteBusiness();
			ClienteBean bean = business.findDataCliente(detalhesProposta.getIdProposta().getCodClienteExt());
			
			DiagnosticoBusiness diagnosticoBusiness = new DiagnosticoBusiness(usuarioBean);
			DiagnosticoBean diagnosticoBean = diagnosticoBusiness.findallPipPspGarantia(detalhesProposta.getIdProposta().getNumSerie());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date fimGarantia = dateFormat.parse(diagnosticoBean.getFimGarantia());
			try {  
				jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}
			
			 
			
			//recupera o último número do documento para recuperar as peças com os preços
			query = manager.createQuery("from EmsSegmento seg where seg.idProposta.id =:idProposta");
			query.setParameter("idProposta", Long.valueOf(idProposta));
			List<EmsSegmento> pecasList = query.getResultList();
			for (EmsSegmento emsSegmento : pecasList) {
				new ImportXmlBusiness(usuarioBean).sendContratoDbs(emsSegmento,	emsSegmento.getIdProposta().getPossuiSubTributaria(), emsSegmento.getNumDoc());
			}			
			Map parametros = new HashMap();

			query = manager.createNativeQuery("select PERC_LEVEL_UTILIZATION from EMS_FUEL where RECEIVED_TIME = ("+
					"	select MAX(RECEIVED_TIME) from EMS_FUEL where SERIAL_NUMBER = '"+detalhesProposta.getIdProposta().getNumSerie()+"')"+
					"	and SERIAL_NUMBER = '"+detalhesProposta.getIdProposta().getNumSerie()+"'");
			BigDecimal PERC_LEVEL_UTILIZATION = BigDecimal.ZERO;
			if(query.getResultList().size() > 0){
				PERC_LEVEL_UTILIZATION = (BigDecimal)query.getResultList().get(0);
			}
			query = manager.createQuery("from EmsConsumoCombustivel where idFamilia.id =:idFamilia");
			query.setParameter("idFamilia", detalhesProposta.getIdProposta().getIdFamiliaCampo().longValue());
			if(query.getResultList().size() > 0 && PERC_LEVEL_UTILIZATION.longValue() > 0){
				EmsConsumoCombustivel combustivel = (EmsConsumoCombustivel)query.getSingleResult();
				if(PERC_LEVEL_UTILIZATION.longValue() < combustivel.getMaxPorcetagemBaixa()){
					parametros.put("SVERIDADE_APLICACAO", "BAIXA");
				}else if(PERC_LEVEL_UTILIZATION.longValue() < combustivel.getMaxPorcetagemMedia()){
					parametros.put("SVERIDADE_APLICACAO", "MÉDIA");
				} else if(PERC_LEVEL_UTILIZATION.longValue() >= combustivel.getMinPorcetagemAlta()){
					parametros.put("SVERIDADE_APLICACAO", "ALTA");
				}
			}

			parametros.put("PERIODO_GARANTIA", (fimGarantia.after(new Date()))?"SIM":"NÃO");
			parametros.put("VALOR_REPARO_APOS_FALHA", detalhesProposta.getValorReparoAposFalha());
			if(detalhesProposta.getIdProposta().getPossuiSubTributaria() == null || detalhesProposta.getIdProposta().getPossuiSubTributaria().equals("N")){
				query = manager.createNativeQuery("select sum(CASE WHEN p.valor_total is null then 0 else p.valor_total end) from ems_pecas p, ems_segmento seg"+
						" where seg.id_proposta ="+Long.valueOf(idProposta)
						+" and seg.id = p.id_ems_segmento");
				parametros.put("IS_SUB_TRIBUTARIA", "N");
			}else if(detalhesProposta.getIdProposta().getPossuiSubTributaria().equals("S")){
				query = manager.createNativeQuery("select sum(CASE WHEN p.TOTALTRIBUTOS is null then 0 else p.TOTALTRIBUTOS end) from ems_pecas p, ems_segmento seg"+
						" where seg.id_proposta ="+Long.valueOf(idProposta)
						+" and seg.id = p.id_ems_segmento");
				parametros.put("IS_SUB_TRIBUTARIA", "S");
			}
			
			TwFilial filial = manager.find(TwFilial.class, Long.valueOf(usuarioBean.getFilial())); // Busca a aprovação da proposta do usuário logado.	
			
			parametros.put("APROVACAO_PROPOSTA_SERVICO", filial.getAprovacaoPropostaServico());
			parametros.put("SUBREPORT_DIR", pathSubreport);
			parametros.put("OBS", detalhesProposta.getObservacao());
			BigDecimal totalPecas = BigDecimal.ZERO;
			if(query.getResultList().size() > 0){
				totalPecas = (BigDecimal)query.getSingleResult();
			}
			parametros.put("ID_CHECHIN", BigDecimal.valueOf(Double.valueOf(idProposta))); 
			parametros.put("TOTAL_PECAS", totalPecas); 
			parametros.put("ENDERECO", bean.getEND());
			if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 0){
				parametros.put("ENDERECO_MARCOSA", "PESA PARANÁ EQUIPAMENTOS - BR 116, N.° 11.807, KM 100 - HAUER - PARANÁ.<br>"
						+"CEP: 81690-200. Fone: 55 (41) 2103-2211");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 10){
				parametros.put("ENDERECO_MARCOSA","AVENIDA TIRADENTES, Nº 2900 - JARDIM JOCKEY CLUB - PARANÁ.<br>"
						+"CEP: 86072-360. Fone: 55 (43) 2101-6000");	
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 11){
				parametros.put("ENDERECO_MARCOSA","RODOVIA PR 317, 7073, LOTE 01/02 - PQ. INDUSTRIAL MARIO BULHÕES - PARANÁ.<br>"
						+"CEP: 87065-005. Fone: 55 (44) 3366-3000");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 15){
				parametros.put("ENDERECO_MARCOSA","BR 277 KM 590 S/N - PARANÁ.<br>"
						+"CEP: 85818-560. Fone: 55 (45) 2101-2500");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 18){
				parametros.put("ENDERECO_MARCOSA","HELENA GRODZKI, 340 - UMBARA - PARANÁ.<br>"
						+"CEP: 81930-085. Fone: 55 (41) 3535-6300");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 21){
				parametros.put("ENDERECO_MARCOSA","BR 101 KM 33 - DISTRITO INDUSTRIAL - SANTA CATARINA.<br>"
						+"CEP: 89219-505. Fone: 55 (47) 2101-0777");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 22){
				parametros.put("ENDERECO_MARCOSA","R. PLINIO ARLINDO DE NEVES, 2133 D - ACESSO BR 282 - BELVEDERE - SANTA CATARINA.<br>"
						+"CEP: 89810-300. Fone: 55 (49) 3313-1400");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 25){
				parametros.put("ENDERECO_MARCOSA","PAULINO PEDRO HERMES, 2909, MARGINAL BR 101 - NSA SRA DO ROSÁRIO - SANTA CATARINA.<br>"
						+"CEP: 88110-693. Fone: 55 (48) 2107-8755");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 30){
				parametros.put("ENDERECO_MARCOSA","AV. INDUSTRIAS, 325 - BAIRRO ANCHIETA - RIO GRANDE DO SUL.<br>"
						+"CEP: 90200-290. Fone: 55 (51) 2125-5355");
			}else if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 32){
				parametros.put("ENDERECO_MARCOSA","RSC 453, NR 16685 - DESVIO RIZZO - RIO GRANDE DO SUL.<br>"
						+"CEP: 95112-360. Fone: 55 (54) 3535-5500");
			}if(Integer.valueOf(detalhesProposta.getIdProposta().getFilial().intValue()) == 33){
				parametros.put("ENDERECO_MARCOSA","ROD RS 153 KM 01 Nº 965 - JERÔNIMO COELHO - RIO GRANDE DO SUL.<br>"
						+"CEP: 99034-600. Fone: 55 (54) 3327-4611");
			}
			
//			if(checkIn.getIdTipoFrete() != null){
//				parametros.put("PORC_FRETE", checkIn.getIdTipoFrete().getTaxa());
//				parametros.put("FRETE_MIN", checkIn.getIdTipoFrete().getFreteMinimo());
//			}else{
//				parametros.put("PORC_FRETE", BigDecimal.ZERO);
//				parametros.put("FRETE_MIN", BigDecimal.ZERO);
//				
//			}
//			if(checkIn.getIdTipoFrete() != null){
//				query = manager.createNativeQuery("select sum(((p.qtd_nao_atendido * p.valor) * "+ checkIn.getIdTipoFrete().getTaxa()+") / 100) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
//						" where seg.id_checkin ="+Long.valueOf(idChechIn)+
//						" and seg.id = oper.id_segmento"+
//						" group by oper.num_doc)  aux"+
//						" where p.id_doc_seg_oper = aux.id_oper"); 
//
//
//				BigDecimal totalFrete = (BigDecimal)query.getSingleResult();
//				if(totalFrete != null && checkIn.getIdTipoFrete() != null && totalFrete.longValue() < checkIn.getIdTipoFrete().getFreteMinimo().longValue() && totalFrete.longValue() > 0){
//					parametros.put("TOTAL_FRETE", checkIn.getIdTipoFrete().getFreteMinimo());
//				}else{
//					parametros.put("TOTAL_FRETE", (totalFrete == null || totalFrete.longValue() == 0)?BigDecimal.ZERO:totalFrete);
//				}
//			}else{
//				parametros.put("TOTAL_FRETE", BigDecimal.ZERO);
//			}
			
			//TwFuncionario funcionario = manager.find(TwFuncionario.class, checkIn.getIdFuncionarioDataOrcamento());
		
			parametros.put("IMPRIMIR_PECAS", imprimirComPecas); 
			parametros.put("NOME_CLIENTE", bean.getRAZSOC()); 
			parametros.put("ID_EQUIPAMENTO", detalhesProposta.getIdProposta().getIdEquipamento()); 
			parametros.put("USUARIO", orcamentista); 
			if(detalhesProposta.getIdProposta().getNumSerie().length() >= 4){
				parametros.put("PREFIXO", detalhesProposta.getIdProposta().getNumSerie().subSequence(0, 4));
			}else{
				parametros.put("PREFIXO", detalhesProposta.getIdProposta().getNumSerie().subSequence(0, 4));
			}
			parametros.put("MODELO", detalhesProposta.getIdProposta().getModelo()); 
			
//			if(detalhesProposta.getIdProposta().getTipoCliente() != null && detalhesProposta.getIdProposta().getTipoCliente().equals("INT")){
//				parametros.put("PORC_SERV_TERC", "1.00"); 
//			}else{
//				parametros.put("PORC_SERV_TERC", "1.50"); 
//			}
//			if(detalhesProposta.getValidadeProposta() != null){
//				parametros.put("EXEC_SERVICO", String.valueOf(detalhesProposta.getValidadeProposta())+" dia(s).");				
//			}
//			String SQL = "select id from SC_MODELO_FAMILIA where ID_FAMILIA = "+detalhesProposta.getIdProposta().getIdFamiliaCampo()+" and modelo = '"+detalhesProposta.getIdProposta().getModelo()+"'";
//			
//			query = manager.createNativeQuery(SQL);
//			BigDecimal idFamiliaModelo = (BigDecimal)query.getSingleResult();
			
			
//			if(detalhesProposta.getIdProposta().getTipoCliente() != null && !detalhesProposta.getIdProposta().getTipoCliente().equals("INT")){
//				SQL = "select distinct ((pre.valor_de_venda * ch.fator_cliente)* "+
//						" (select c.fator from sc_complexidade c"+
//						"	where c.id =  "+
//						"	(select p.id_complexidade from sc_preco p where p.id_modelo_familia = "+idFamiliaModelo+ 
//						"	 and p.id_prefixo = (select pre.id from sc_prefixo pre where pre.descricao = ch.PREFIXO and pre.id_modelo_familia = "+idFamiliaModelo+" )"+
//						"	 and p.comp_code = seg.com_code"+
//						"	 and p.job_code = seg.job_code )"+
//						"	) * CASE WHEN ch.fator_urgencia = 'S'"+
//						" THEN (select fator_urgencia from ge_fator) ELSE 1 END) * seg.qtd_comp valor_total_hora, seg.horas_prevista,"+
//
//						"	(select c.fator from ge_complexidade c"+ 
//						"	where c.id =  "+
//						"	(select p.id_complexidade from sc_preco p where p.id_modelo_familia = "+idFamiliaModelo+ 
//						"	 and p.id_prefixo = (select pre.id from sc_prefixo pre where pre.descricao = ch.PREFIXO and pre.id_modelo_familia = "+idFamiliaModelo+" )"+
//						"	 and p.comp_code = seg.com_code"+
//						"	 and p.job_code = seg.job_code )"+
//						"	) complexidade,ch.fator_urgencia , ch.fator_cliente, pre.valor_de_venda, seg.numero_segmento, seg.JOB_CODE, seg.COM_CODE"+
//
//						"	from ems_proposta ch, ems_segmento seg, sc_prefixo pre"+ 
//						"	where ch.ID = "+idProposta+
//						"	and ch.ID = seg.ID_PROPOSTA"+
//						"	and pre.descricao = ch.PREFIXO"+
//						"	and pre.id_modelo_familia ="+idFamiliaModelo;
//			}else{
//				SQL = " select ((select f.valor_inter from sc_fator f) * seg.qtd_comp), seg.horas_prevista"+
//						"	from EMS_PROPOSTA ch, ems_segmento seg, ge_prefixo pre"+ 
//						"	where ch.ID = "+idProposta+
//						"	and ch.ID = seg.ID_PROPOSTA"+
//						"	and pre.descricao = ch.PREFIXO"+
//						"	and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = ch.modelo and a.id_familia = ch.id_familia_campo)";
//			}
//			query = manager.createNativeQuery(SQL);
//			List<Object[]> list = query.getResultList();
//			BigDecimal valorMaoDeObra = BigDecimal.ZERO;
//			BigDecimal total = BigDecimal.ZERO;
//			for (Object[] objects : list) {
//				valorMaoDeObra = (BigDecimal)objects[0];
//				if(valorMaoDeObra == null){
//					valorMaoDeObra = BigDecimal.ZERO;
//				}
//				String horas = (String)objects[1];
//				//horas = "5.00"; //<<<<<<<<<<<<<<<<<<<<<<<<<<<<
////				String [] aux = horas.split(":");
////				Double h = Double.valueOf(aux[0]);
////				Double m = Double.valueOf(aux[1]) / 60;
////				h = h+m;
//				total = total.add(valorMaoDeObra.multiply(BigDecimal.valueOf(Double.valueOf(horas))));
//				//servico_terceiros = (BigDecimal)objects[6];
//			}
//			BigDecimal servico_terceiros = BigDecimal.ZERO;
//			SQL = "select sum(CASE WHEN st.valor is null then 0 else st.valor end)  from ems_segmento seg, ems_segmento_serv_terceiros st"+
//				  "	where seg.id = st.id_ems_segmento"+
//				  "	and seg.id_proposta = "+Long.valueOf(idProposta); 
//			query = manager.createNativeQuery(SQL);
//			BigDecimal st = (BigDecimal)query.getSingleResult();
//			if(st != null  && st.longValue() > 0){
//				servico_terceiros = st;
//			}else{
//				servico_terceiros = BigDecimal.ZERO;
//			}
//			
//			parametros.put("TOTAL_MAO_DE_OBRA", total);
//			manager.getTransaction().begin();
//			detalhesProposta.getIdProposta().setValorMaoDeObra(total);
//			detalhesProposta.getIdProposta().setValorPecas(totalPecas);
//			
//			if(totalPecas != null && servico_terceiros != null){
//				if(detalhesProposta.getIdProposta().getTipoCliente() != null && detalhesProposta.getIdProposta().getTipoCliente().equals("INT")){
//					parametros.put("TOTAL_ORCAMENTO", totalPecas.add(total).add(servico_terceiros));
//				}else{
//					parametros.put("TOTAL_ORCAMENTO", totalPecas.add(total).add(servico_terceiros.multiply(BigDecimal.valueOf(1.50))));
//				}
//			}else if(servico_terceiros != null){
//				if(detalhesProposta.getIdProposta().getTipoCliente() != null && detalhesProposta.getIdProposta().getTipoCliente().equals("INT")){
//					parametros.put("TOTAL_ORCAMENTO", total.add(servico_terceiros));
//				}else{
//					parametros.put("TOTAL_ORCAMENTO", total.add(servico_terceiros.multiply(BigDecimal.valueOf(1.50))));
//				}
//
//			}else{
//				if(detalhesProposta.getIdProposta().getTipoCliente() != null && detalhesProposta.getIdProposta().getTipoCliente().equals("INT")){
//					parametros.put("TOTAL_ORCAMENTO", total);
//				}else{
//					parametros.put("TOTAL_ORCAMENTO", total);
//				}
//				
//			}
//			detalhesProposta.getIdProposta().setValorDeslocamento(valorKm.getKmVenda().multiply(BigDecimal.valueOf(detalhesProposta.getDistancia())));
//			parametros.put("TOTAL_DESLOCAMENTO", detalhesProposta.getIdProposta().getValorDeslocamento());
////			if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
////				parametros.put("TOTAL_MAT_USU_TEC", BigDecimal.ZERO);
////			}else{
////				parametros.put("TOTAL_MAT_USU_TEC", total.multiply(BigDecimal.valueOf(0.05)));
////			}
//			if(detalhesProposta.getIdProposta().getTipoCliente() != null && detalhesProposta.getIdProposta().getTipoCliente().equals("INT")){
//				parametros.put("TOTAL_SERVICO_TERCEIROS", servico_terceiros);
//				detalhesProposta.getIdProposta().setValorMaoDeObra(detalhesProposta.getIdProposta().getValorMaoDeObra().add((BigDecimal)parametros.get("TOTAL_SERVICO_TERCEIROS")));
//			}else{
//				parametros.put("TOTAL_SERVICO_TERCEIROS", servico_terceiros.multiply(BigDecimal.valueOf(1.50)));
//				detalhesProposta.getIdProposta().setValorMaoDeObra(detalhesProposta.getIdProposta().getValorMaoDeObra().add((BigDecimal)parametros.get("TOTAL_SERVICO_TERCEIROS")));
//			}
//			if(totalPecas != null){
//				detalhesProposta.getIdProposta().setTotalOrcamento(detalhesProposta.getIdProposta().getValorMaoDeObra().add(detalhesProposta.getIdProposta().getValorPecas()));
//			}
			manager.merge(detalhesProposta);
//			manager.getTransaction().commit();
//			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("marcosaLogo.gif");
//			File img = File.createTempFile("img", "gif",new File("."));
//			OutputStream out=new FileOutputStream(img);
//			byte buf[]=new byte[1024];
//			int len;
//			while((len=inputStream.read(buf))>0)
//				out.write(buf,0,len);
//			out.close();
//			inputStream.close();
			
			//parametros.put("logo", img);			
			byte[] pdfInspecao = null;  
			//Gera o pdf para exibicao..  
			try { 
				con =Connection.getConnection();
				//while (con == null ){
					//con = Connection.getConnection();
				//}
				pdfInspecao = JasperRunManager.runReportToPdf(jasperReport, parametros, con);  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}  

			//Parametros para nao fazer cache e o que será exibido..  
			response.setContentType( "application/pdf" );  
			response.setHeader("Content-disposition",
			"attachment; filename=proposta.pdf" ); 

			//Envia para o navegador o pdf..  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			servletOutputStream.write( pdfInspecao );  
			servletOutputStream.flush();  
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null){
				manager.close();
			}
			if(conDbs != null){
				try {
					conDbs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
