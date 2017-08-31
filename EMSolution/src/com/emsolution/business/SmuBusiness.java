package com.emsolution.business;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.emsolution.bean.ArqSmuBean;
import com.emsolution.bean.SmuBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsArquivoSmu;
import com.emsolution.entity.EmsDiagnosticoView;
import com.emsolution.entity.EmsSmu;
import com.emsolution.entity.TwFilial;
import com.emsolution.entity.TwFuncionario;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.JpaUtil;

public class SmuBusiness {
	private UsuarioBean usuarioBean;
	
	public SmuBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public Boolean fazerUploadSmuXls(byte[] bytes, String nomeArquivo)  {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
		//SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aaa", Locale.ENGLISH);
		//int pulaLinha = 0;
		InputStream is = null;

		EntityManager manager = null;
		Connection conn = null;
		try {
			conn = ConectionDbs.getConnecton();
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Set<SmuBean> listaSerie = new HashSet(0);
			//incluir a rotina de salvar na tabela EMS_ARQUIVO_SOS
			EmsArquivoSmu emsSmu = new EmsArquivoSmu();
			DiagnosticoBusiness business = new DiagnosticoBusiness(this.usuarioBean);
			//setar objeto
			emsSmu.setNomeArquivo(nomeArquivo);
			emsSmu.setData(new Date());
			emsSmu.setIdFuncionario(this.usuarioBean.getMatricula());
			//emsSmu.setTipoAnalise(tipoAnalise);
			//inserir no banco
			manager.persist(emsSmu);
			manager.getTransaction().commit();
			
			is = new ByteArrayInputStream(bytes);
			HSSFWorkbook planilha = new HSSFWorkbook(is); 
			
			//BufferedReader bfReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			//Query query = null;
			//String linha = null;
			HSSFSheet sheet = planilha.getSheetAt(0);
			
			for(int i = 0; i <= sheet.getLastRowNum(); i++){
				if(i == 0){
					continue;
				}
				SmuBean smuBean = new SmuBean();
				EmsSmu smu = new EmsSmu();
				HSSFRow row = sheet.getRow(i);
				manager.getTransaction().begin();

				HSSFCell cell = row.getCell(1);
				String serie = "";
				if(cell.getCellType() != cell.CELL_TYPE_NUMERIC){
					serie = "0"+cell.getStringCellValue();
				}else{
					serie = new Double(cell.getNumericCellValue()).toString();
					
				}
                
                smuBean.setSerie(serie);
                
                cell = row.getCell(2);
                smuBean.setModelo(cell.getStringCellValue());
                
                smu.setNumSerie(serie);
               // System.out.println(serie);
                cell = row.getCell(3);
                Double ultimoHorimetro = cell.getNumericCellValue();
                smu.setHorimetro(ultimoHorimetro.longValue());

                cell = row.getCell(4);
                Double proximoHorimetro = 0D;
                if(cell.getCellType() != cell.CELL_TYPE_NUMERIC){
                	proximoHorimetro = Double.valueOf(cell.getStringCellValue());
				}else{
					proximoHorimetro = cell.getNumericCellValue();
					
				}
                smu.setHorimetroProx(proximoHorimetro.longValue());
                
                cell = row.getCell(5);
                String tipoServico = "";
                if(cell.getCellType() != cell.CELL_TYPE_NUMERIC){
                	tipoServico = cell.getStringCellValue();
                }else{
                	tipoServico = new Integer( new Double(cell.getNumericCellValue()).intValue()).toString();
                }
                smu.setTipoServico(tipoServico);
                
				smu.setDate(new Date());
				manager.merge(smu);
				manager.getTransaction().commit();
				listaSerie.add(smuBean);
//
                
			}
			ResultSet rs = null;
			
			for (SmuBean smuBean : listaSerie) {
				Query query = manager.createNativeQuery("select COUNT(*) from EMS_SMU where IS_REJEITADO is null and ID_SEGMENTO is null and NUM_SERIE = '"+smuBean.getSerie()+"'");
				Integer totalSmu = (Integer)query.getSingleResult();
				query = manager.createNativeQuery("update EMS_DIAGNOSTICO_VIEW set COR_SMU = 'red', TOTAL_SMU = "+totalSmu+" where NUMERO_SERIE = '"+smuBean.getSerie()+"'");
				manager.getTransaction().begin();
				int totalUpdate = query.executeUpdate();
				manager.getTransaction().commit();
				if(totalUpdate == 0){
					java.sql.PreparedStatement stmt = conn.prepareStatement("select cip.STN1, cip.CUNM from  libu17.cipname0 cip,libu17.empeqpd0 emp left join libu17.wppewar0 w on w.EQMFSN = emp.EQMFS2"+
										"	where cip.CUNO = emp.CUNO"+
										"	and emp.EQMFS2 = '"+smuBean.getSerie()+"'");
					rs = stmt.executeQuery();
					rs.next();
					Long filialLong = rs.getLong("STN1");
					
//					query = manager.createNativeQuery("select COD_CLIENTE, NOME_CLIENTE, FILIAL from PMP_CLIENTE_PL where SERIE = '"+smuBean.getSerie()+"'");
//					if(query.getResultList().size() == 0){
//						continue;
//					}
//					Object[] clientePl = (Object[])query.getSingleResult();
					//System.out.println(clientePl[2]);
					TwFilial filial = manager.find(TwFilial.class, filialLong);
					
					
					EmsDiagnosticoView dView = new EmsDiagnosticoView();
					dView.setNumeroSerie(smuBean.getSerie());
					dView.setFilial(filial.getStno());
					dView.setNomeCliente(rs.getString("CUNM"));
					dView.setModelo(smuBean.getModelo());
					dView.setNomeFilial(filial.getStnm());
					dView.setBacklogPmp("Não");
					dView.setBacklogCampo("Não");
					dView.setDataBacklogPmp("N/A");
					dView.setCor("green");
					dView.setNumeroDiagnosticos(0L);
					dView.setCorSos("green");
					dView.setDataColeta("N/A");
					dView.setTotalSos(0L);
					dView.setNormalSos(0L);
					dView.setMonitorarSos(0L);
					dView.setCriticoSos(0L);
					dView.setDataAceitePmp("N/A");
					dView.setTipoContratoPmp("N/A");
					dView.setDataInspecaoPmp("N/A");
					dView.setDataInspecaoCampo("N/A");
					dView.setIdOsPalmPmp(null);
					dView.setIdOsPalmCampo(null);
					dView.setHorimetro(null);
					dView.setDataAtualizacaoHorimetro(null);
					dView.setLevel1(0L);
					dView.setLevel2(0L);
					dView.setLevel3(0L);
					dView.setIsLockMy("N");
					dView.setEstimateByFuncionarioLock(null);
					//this.findallPipPspGarantia(bean.getNumeroSerie(), bean);
					//dView.setDataInicioGarantia(bean.getInicioGarantia());
					//dView.setDataFimGarantia(bean.getFimGarantia());
					dView.setCorSmu("red");
					dView.setTotalSmu(totalSmu.longValue());
					manager.getTransaction().begin();
					manager.merge(dView);
					manager.getTransaction().commit();
				}
				
				//business.saveDiagnosticJob(-1L, serie);
			}
			return true;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public List<SmuBean> findAllSmu(String serie)  {
		List<SmuBean> smuList = new ArrayList<SmuBean>();
		EntityManager manager = null;
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("from EmsSmu where idSegmento.id is null and isRejeitado is null and numSerie =:numSerie");
			query.setParameter("numSerie", serie);
			List<EmsSmu> result = (List<EmsSmu>)query.getResultList();
			for (EmsSmu smu : result) {
				SmuBean smuBean = new SmuBean();
				smuBean.setId(smu.getId());
				smuBean.setHorimetroAnterior(smu.getHorimetro());
				smuBean.setHorimetroProximo(smu.getHorimetroProx());
				smuBean.setTipoServico(smu.getTipoServico());
				smuBean.setData(fmt.format(smu.getDate()));
				smuList.add(smuBean);
				
			}

			return smuList;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public List<SmuBean> findAllSmuAssociado(Long idSegmento)  {
		List<SmuBean> sosList = new ArrayList<SmuBean>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("from EmsSmu where idSegmento.id = "+idSegmento);

			List<EmsSmu> result = (List<EmsSmu>)query.getResultList();
			for (EmsSmu smu : result) {
				SmuBean sos = new SmuBean();
				sos.setId(smu.getId());
				sos.setHorimetroAnterior(smu.getHorimetro());
				sos.setHorimetroProximo(smu.getHorimetroProx());
				sos.setTipoServico(smu.getTipoServico());
				sos.setObservacao(smu.getObservacao());
				sos.setData(dateFormat.format(smu.getDate()));
				sosList.add(sos);
			}

			return sosList;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	public List<ArqSmuBean> findAllArquivoSmu (){
		EntityManager manager = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<ArqSmuBean> sosList = new ArrayList<ArqSmuBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From EmsArquivoSmu sos, TwFuncionario f where f.epidno = sos.idFuncionario order by sos.data desc");
			List<Object[]> result = (List<Object[]>)query.getResultList();
			for (Object[] emsArquivoSos : result) {
				EmsArquivoSmu arquivoSmu = (EmsArquivoSmu)emsArquivoSos[0];
				TwFuncionario funcionario = (TwFuncionario)emsArquivoSos[1];
				//instanciar o bean do java  que você criou
				ArqSmuBean arqSmu = new ArqSmuBean();
				//arqSos.setData(dateFormat.format(arquivoSos.getData()));
				arqSmu.setData(dateFormat.format(arquivoSmu.getData()));
				//setar os dados no bean
				arqSmu.setIdFuncionario(funcionario.getEplsnm());
				arqSmu.setNomeArquivo(arquivoSmu.getNomeArquivo());
				
				
				//colocar o bean na coleção sosList
				sosList.add(arqSmu);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return sosList;

	}

}

