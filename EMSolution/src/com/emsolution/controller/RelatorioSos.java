package com.emsolution.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.emsolution.bean.SosBean;

/**
 * Servlet implementation class RelatorioSos
 */
public class RelatorioSos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat dateFormatPar = new SimpleDateFormat("dd/MM/yyyy");
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RelatorioSos() {
		super();
		// TODO Auto-generated constructor stub
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
		String serialNumber = request.getParameter("serialNumber");
		String processDate = request.getParameter("processDate");
		String compCode = request.getParameter("compCode");
		String manufacturer = request.getParameter("manufacturer");
		String model = request.getParameter("model");
		String compDesc = request.getParameter("compDesc");
		String customer = request.getParameter("customer");
		String idProposta  = request.getParameter("idProposta");
		String equipNumber = request.getParameter("equipNumber");
		String jobsite = request.getParameter("jobsite");
		
		
		//JasperReport jasperReport = null; 
		JasperReport jasperReport = null; 
		//byte[] pdfInspecao = null;
		

		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();  
		String caminhoJasper = servletContext.getRealPath("WEB-INF/relatorio_sos/relatorio_sos.jasper"); 
		String pathSubreport = servletContext.getRealPath("WEB-INF/relatorio_sos/")+"/"; 

		//Carrega o arquivo com o jasperReport  
		EntityManager manager = null;
		java.sql.Connection con = null;

		try{
			manager = com.emsolution.util.JpaUtil.getInstance();
			//EmsProposta proposta = manager.find(EmsProposta.class, Long.valueOf(idProposta));
			try {  
				jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}
			
			String SQL = "select top 4 s.OVERALL_INTERP,s.TEXT_ID, CONVERT(varchar(10),s.SAMPLED_DATE, 103) dataInterpretacao, "+
					" CONVERT(varchar(10),s.PROCESS_DATE, 103) dataProcesso, s.meter +'-'+s.METER_UNITS horimetro, s.METER_ON_FLUID +'-'+s.METER_UNITS horimetroOleo,s.FLUID_CHANGED," +
					" s.FILTER_CHANGED, FLUID_ADD,s.FLUID_BRAND+''+s.FLUID_WEIGHT fabricante, s.COMP_DESC, s.COMP_CODE , convert(varchar(4000),s.INTERPRETATION_TEXT) INTERPRETATION_TEXT,"+
					" CONVERT(varchar(10),s.PROCESS_DATE,112) PROCESS_DATE   from EMS_SOS s"+
					" where SERIAL_NUMBER = '"+serialNumber+"'"+
					" and COMP_CODE = '"+compCode+"'"+
					" and CONVERT(varchar(10),PROCESS_DATE ,112) <= '"+processDate+"'"+
					" order by PROCESS_DATE desc";
			 
			
			

			Query query = manager.createNativeQuery(SQL);
			
			List<SosBean> listaFinal = new ArrayList<SosBean>();
			
			
			if( query.getResultList().size() > 0){
				List<Object[]> listaBean = query.getResultList();
				for (int i = 0; i<4;i++) {
					if( i < listaBean.size() ){
						SosBean bean = new SosBean();
						Object[] object = listaBean.get(i);
						bean.setOverallInterp((String)object[0]);
						bean.setTextId((String)object[1]);
						bean.setInterpDateTime((String)object[2]);
						bean.setDataProcesso((String)object[3]);
						bean.setHorimetro((String)object[4]);
						bean.setHorimetroOleo((String)object[5]);
						bean.setFluidChanged((String)object[6]);
						bean.setFilterChanged((String)object[7]);
						bean.setFluidAdd((String)object[8]);
						bean.setFabricante((String)object[9]);
						bean.setCompDesc((String)object[10]);
						bean.setCompCode((String)object[11]);
						bean.setInterpretationText((String)object[12]);
						bean.setProcessDate((String)object[13]);
						
						listaFinal.add(bean);
					}else {
						SosBean beanVazio = new SosBean();
						listaFinal.add(beanVazio);
					}
				}
			}
			
			JRBeanCollectionDataSource result =  new JRBeanCollectionDataSource(listaFinal);
			
			
			Map parametros = new HashMap();
		
			parametros.put("SERIAL_NUMBER", serialNumber); 
			parametros.put("PROCESS_DATE", processDate); 
			parametros.put("COMP_CODE", compCode); 
			parametros.put("MANUFACTURER", manufacturer); 
			parametros.put("MODEL", model); 
			parametros.put("COMP_DESC", compDesc); 
			parametros.put("CUSTOMER", customer); 
			parametros.put("EQUIP_NUMBER", equipNumber); 
			parametros.put("JOBSITE", jobsite); 
			parametros.put("SUBREPORT_DIR", pathSubreport);
			parametros.put("LIST_SOURCE", result);
			
			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("logo.jpg");

			File img=File.createTempFile("img", "gif",new File("."));

			OutputStream out=new FileOutputStream(img);
			byte buf[]=new byte[1024];
			int len;
			while((len=inputStream.read(buf))>0)
				out.write(buf,0,len);
			out.close();
			inputStream.close();	
			parametros.put("logo", img);

			
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
				con =com.emsolution.util.Connection.getConnection();
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
			"attachment; filename=relatorioSos.pdf" ); 

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

