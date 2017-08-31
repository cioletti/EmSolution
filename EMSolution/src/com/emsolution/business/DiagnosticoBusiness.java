package com.emsolution.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.ComponenteCodeBean;
import com.emsolution.bean.DetalhesDiagnosticoBean;
import com.emsolution.bean.DiagnosticoBean;
import com.emsolution.bean.JobCodeBean;
import com.emsolution.bean.JobControlBean;
import com.emsolution.bean.StatusOportunidadeBean;
import com.emsolution.bean.TipoOportunidadeBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsDiagnosticoView;
import com.emsolution.entity.EmsStatusOportunidade;
import com.emsolution.entity.EmsTipoOportunidade;
import com.emsolution.util.ConectionDbs;
import com.emsolution.util.EmailHelper;
import com.emsolution.util.IConstantAccess;
import com.emsolution.util.JpaUtil;

public class DiagnosticoBusiness {
	private UsuarioBean usuarioBean;
	public DiagnosticoBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	SimpleDateFormat dateFormatConverter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat dateFormatDefault = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<DiagnosticoBean> findDiagnostic(Long filial, String campo, Long inicial, Long numRegistros){
		EntityManager manager = null;
		List<DiagnosticoBean> result = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> vermelho = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> amarelo = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> verde = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> paginacao = new ArrayList<DiagnosticoBean>();
		try {

			manager = JpaUtil.getInstance();
			
			String SQL = "select distinct d.STNM, " +//0
				 " d.MODELO, " +//1
				 " d.NUMERO_SERIE, " +//2
				 " d.NOME_CLIENTE,"+//3
				 " d.pl numeroDiagnosticos, " +//4
				 " '' csa, " +//5
				 " d.dataInspecaoPmp," +//6
				 " d.idosPalmPmp,"+//7
				 " d.nivel1,"+//8
				 " d.nivel2,"+//9
				 " d.nivel3,"+//10
				 " d.horimetro, " +//11
				 " d.DATA_ATUALIZACAO_HORIMETRO , " +//12
				 " d.gap,"+//13
				 "		d.normal,"+//14
				 "		d.monitorar,"+//15
				 "		d.critico,"+//16
				 " (select COUNT(*) from EMS_PROPOSTA where NUM_SERIE = d.NUMERO_SERIE and IS_LOCK = 'S' and ID_FUNCIONARIO = '"+this.usuarioBean.getMatricula()+"') isLockMy,"+//17
				 "      d.isLock,"+//18
				 "      d.estimateBy,"+//19
				// "      d.backlogPmp,"+//20
				// "		d.backlogCampo,"+//21
				 "      d.idosPalmCampo,"+//22 20
				 "      d.dataInspecaoCampo," +//23 21
				 "  d.STNO,"+ //23 22
				 " (select distinct CONVERT(varchar(10), MAX(sampled_date), 103) from EMS_SOS where SERIAL_NUMBER = d.NUMERO_SERIE) data_sos, "+//23
				 " d.DATA_INICIO_GARANTIA,"+//24
				 " d.DATA_FIM_GARANTIA"+//25
			 " from VIEW_EMS_DIAGNOSTICO d "+
			" where (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')";
			if(filial != -1){
				SQL += " and  d.STNO = "+filial;
			}
			SQL += " order by d.dataInspecaoPmp";
			Query query = manager.createNativeQuery(SQL);
			
			
			//query.setFirstResult(inicial.intValue());
			//query.setMaxResults(numRegistros.intValue());
			List<Object[]> diagnosticoList = (List<Object[]>)query.getResultList();
//			query = manager.createNativeQuery("select count(*) from VIEW_EMS_DIAGNOSTICO d "+
//					" where  d.STNO =:filial "+
//					" and (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')");
//			query.setParameter("filial", filial);
			//Remove registro repetido
			for (int i = 0; i < diagnosticoList.size(); i++) {
				Object[] diagnosticoBean = diagnosticoList.get(i);
				for (int j = i+1; j < diagnosticoList.size(); j++) {
					Object[] dBean = diagnosticoList.get(j);
					if(((String)dBean[2]).equals((String)diagnosticoBean[2])){
						diagnosticoList.remove(j);
						break;
					}
				}
				
			}
			
			Integer tamanhoTotalLista = 0;
			if(filial != -1){
				tamanhoTotalLista = diagnosticoList.size();
			}
			for(int i = 0; i < diagnosticoList.size(); i++){
				Object[] pair = diagnosticoList.get(i);	
			//for (Object[] pair : diagnosticoList) {
				String cor = "VERDE";
				DiagnosticoBean bean = new DiagnosticoBean();
				bean.setNumLinhas(tamanhoTotalLista.longValue());
				bean.setFilial((String)pair[0]);
				bean.setModelo((String)pair[1]);
				bean.setNumeroSerie((String)pair[2]);
				bean.setNomeCliente((String)pair[3]);
				bean.setIdFilial(((BigDecimal)pair[22]).toString());
				bean.setInicioGarantia((String)pair[24]);
				bean.setFimGarantia((String)pair[25]);
				//Integer backlog = (Integer)pair[20];
				bean.setBacklog("Não");
				
				if(pair[7] != null){
					SQL = "select count(*) from os_palm_dt dt where dt.OS_PALM_IDOS_PALM = "+(BigDecimal)pair[7]+" and dt.STATUS = 'NC' and dt.ID_EMS_SEGMENTO IS NULL and dt.IS_REJEITADO_EMS is null";
					query = manager.createNativeQuery(SQL);
					Integer backlog = (Integer)query.getSingleResult();
					if(backlog > 0){
						cor = "VERMELHO";
						bean.setBacklog("Sim");
					}
				}
				bean.setBacklogCampo("Não");
				if(pair[20] != null){
					SQL = "select count(*) from sc_os_palm_dt dt where dt.OS_PALM_IDOS_PALM = "+(BigDecimal)pair[20]+" and dt.STATUS = 'NC' and dt.ID_EMS_SEGMENTO IS NULL and dt.IS_REJEITADO_EMS is null";
					query = manager.createNativeQuery(SQL);
					Integer backlog = (Integer)query.getSingleResult();
					if(backlog > 0){
						cor = "VERMELHO";
						bean.setBacklogCampo("Sim");
					}
				}
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklog("Sim");
//				}
				//backlog = (Integer)pair[21];
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklogCampo("Sim");
//				}
				//Integer csa = (Integer)pair[5];
				DetalhesDiagnosticoBean detalhesBean = new DetalhesDiagnosticoBean();
				detalhesBean.setDataAceite("N/A");
				detalhesBean.setTipoContrato("N/A");
				bean.setDataBacklogPmp("N/A");
				if(bean.getBacklog().equals("Sim")){
					SQL = " select c.ID, c.NUMERO_SERIE, c.DATA_ACEITE , (select tc.DESCRICAO from PMP_TIPO_CONTRATO tc where tc.ID = c.ID_TIPO_CONTRATO) tipoContrato from PMP_CONTRATO c"+ 
						  "	where c.ID = "+
						  "	(select max(cc.id) from PMP_CONTRATO cc"+
						  "	where cc.NUMERO_SERIE =:numeroSerie "+
						  "	and cc.ID_STATUS_CONTRATO in (select ID from PMP_STATUS_CONTRATO where SIGLA not in ('CEN', 'CNA')))";
					query = manager.createNativeQuery(SQL);
					query.setParameter("numeroSerie", bean.getNumeroSerie());
					List<Object[]> csaList = (List<Object[]>)query.getResultList();
					String tipoContrato = "";
					String dataAceite = "";
					for (Object[] objects : csaList) {
						tipoContrato += (String)objects[3]+" ";
						dataAceite += dateFormatConverter.format(dateFormatDefault.parse((String)objects[2]))+" ";
					}
					detalhesBean.setDataAceite(dataAceite);
					detalhesBean.setTipoContrato(tipoContrato);
					bean.setDataBacklogPmp(dateFormatConverter.format(dateFormatDefault.parse((String)pair[6])));
				}

				if(pair[6] == null){
					detalhesBean.setDataInspecao("N/A");
				}else{
					detalhesBean.setDataInspecao(dateFormatConverter.format(dateFormatDefault.parse((String)pair[6])));
				}
				
				if(pair[21] == null){
					detalhesBean.setDataInspecaoCampo("N/A");
				} else {
					detalhesBean.setDataInspecaoCampo(dateFormatConverter.format(dateFormatDefault.parse((String)pair[21])));
				}
				if(pair[7] != null){
					detalhesBean.setIdOsPalm(((BigDecimal)pair[7]).longValue());
				}
				if(pair[20] != null){
					detalhesBean.setIdOsPalmCampo(((BigDecimal)pair[20]).longValue());
				}
				if(pair[11] != null){
					detalhesBean.setHorimetro(((BigDecimal)pair[11]).longValue());
					detalhesBean.setDataAtualizacaoHorimetro(dateFormatConverter.format(dateFormatDefault.parse((String)pair[12])));
				}
				detalhesBean.setLevel1(0L);
				detalhesBean.setLevel2(0L);
				detalhesBean.setLevel3(0L);
				if(pair[8] != null){
					detalhesBean.setLevel1(((BigDecimal)pair[8]).longValue());
				}
				bean.setCor("green");
				if(pair[9] != null){
					detalhesBean.setLevel2(((BigDecimal)pair[9]).longValue());
				}
				if(detalhesBean.getLevel2() > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCor("yellow");
				}
				if(pair[10] != null){
					detalhesBean.setLevel3(((BigDecimal)pair[10]).longValue());
				}
				if(detalhesBean.getLevel3() > 0){
					cor = "VERMELHO";
					bean.setCor("red");
				}
				bean.setNumerDiagnosticos(detalhesBean.getLevel1()+detalhesBean.getLevel2()+detalhesBean.getLevel3());
				bean.setDetalhesDiagnosticoBean(detalhesBean);
				bean.setCorSos("green");
				bean.setTotalSos((Integer)pair[14]+(Integer)pair[15]+(Integer)pair[16]);
				bean.setDataColeta("N/A");
				if(pair[23] != null){
					bean.setDataColeta((String)pair[23]);
				}
				
				detalhesBean.setNormalSos((Integer)pair[14]);
				detalhesBean.setMonitorarSos((Integer)pair[15]);
				detalhesBean.setCriticoSos((Integer)pair[16]);
				
				if(((Integer)pair[15]) > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCorSos("yellow");
				}
				if(((Integer)pair[16]) > 0){
					cor = "VERMELHO";
					bean.setCorSos("red");
				}
				if(((Integer)pair[17]) > 0){
					bean.setIsLockMy("S");
				} else {
					bean.setIsLockMy("N");
				}
				if(((Integer)pair[18]) > 0){
					bean.setIsLock("S");
					if(this.usuarioBean.getIsAdm() == true){
						bean.setIsLockMy("S");
					}
				} else {
					bean.setIsLock("N");
				}
				bean.setEstimateByFuncionarioLock((String)pair[19]);
				if(cor.equals("VERDE")){
						verde.add(bean);
						//result.add(bean);
				}else if(cor.equals("AMARELO")){
					amarelo.add(bean);
					//result.add(bean);
				}else if(cor.equals("VERMELHO")){
					vermelho.add(bean);
					//result.add(bean);
				}
			}
			
			//Collections.sort(result);
			result.addAll(vermelho);
			result.addAll(amarelo);
			result.addAll(verde);

			List<DiagnosticoBean> list = new ArrayList<DiagnosticoBean>();
			for (int i = 0; i < result.size(); i++) {
				DiagnosticoBean diagnosticoBean = result.get(i);
				if(diagnosticoBean.getDataBacklogPmp().equals("N/A")){
					list.add(diagnosticoBean);
					result.remove(i);
					i--;
				}
			}
			result.addAll(list);
			if(diagnosticoList.size() < numRegistros && filial != -1){
				return result;
			}
			if(diagnosticoList.size() > numRegistros){
				for(int i = inicial.intValue(); i < result.size(); i++){
					paginacao.add(result.get(i));
					if(paginacao.size() == numRegistros && filial != -1){
						return paginacao;
					}
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return paginacao;
	}
	public List<DiagnosticoBean> findDiagnosticFisico(Long filial, String campo, Long inicial, Long numRegistros){
		EntityManager manager = null;
		List<DiagnosticoBean> result = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> backlogCampo = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> vermelho = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> amarelo = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> verde = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> paginacao = new ArrayList<DiagnosticoBean>();
		try {
			
			manager = JpaUtil.getInstance();
			
			String SQL ="SELECT " +
						  " NOME_FILIAL "+//0	
						  ",MODELO "+//1
						  ",NUMERO_SERIE "+//2
						  ",NOME_CLIENTE "+//3
						  ",NUMERO_DIAGNOSTICOS "+//4
						  ", '' csa " +//5
						  ",DATA_INSPECAO_PMP "+//6
						  ",ID_OS_PALM_PMP "+//7
						  ",LEVEL1 "+//8
					      ",LEVEL2 "+//9
					      ",LEVEL3 "+//10
					      ",HORIMETRO "+//11
					      ",DATA_ATUALIZACAO_HORIMETRO "+//12
					      ", 'false' gap"+//13
					      ",NORMAL_SOS "+//14
					      ",MONITORAR_SOS "+//15
					      ",CRITICO_SOS "+//16
					      ", (select COUNT(*) from EMS_PROPOSTA where NUM_SERIE = NUMERO_SERIE and IS_LOCK = 'S' and ID_FUNCIONARIO = '"+this.usuarioBean.getMatricula()+"') isLockMy"+//17
					      ", IS_LOCK_MY"+//18
					      ", ESTIMATE_BY_FUNCIONARIO_LOCK"+//19
					      ",ID_OS_PALM_CAMPO "+//20
					      ",DATA_INSPECAO_CAMPO "+//21
					      ",FILIAL "+//22
					      ",DATA_COLETA SOS"+//23
					    
					      ",BACKLOG_PMP "+//24
					      ",BACKLOG_CAMPO "+//25
					      ",DATA_BACKLOG_PMP "+//26
					      ",COR "+//27
					      ",NUMERO_DIAGNOSTICOS "+//28
					      ",COR_SOS "+//29
					      ",TOTAL_SOS "+//30
					      ",DATA_ACEITE_PMP "+//31
					      ",TIPO_CONTRATO_PMP "+//32
					      ",DATA_INICIO_GARANTIA "+//33
					      ",DATA_FIM_GARANTIA "+//34
					      ",DATA_IMPORTACAO_SOS IMPORTACAO_SOS"+//35
					      ",COR_SMU"+//36
					      ",TOTAL_SMU"+//37
					     
					  " FROM EMS_DIAGNOSTICO_VIEW "+	
			
			
			" where (NUMERO_SERIE like '%"+campo+"%' or NOME_CLIENTE LIKE '%"+campo+"%' or MODELO LIKE '%"+campo+"%')";
			if(filial != -1){
				SQL += " and  FILIAL = "+filial;
			}
			SQL += " order by DATA_INSPECAO_PMP, DATA_INSPECAO_CAMPO";
			Query query = manager.createNativeQuery(SQL);
			
			
			//query.setFirstResult(inicial.intValue());
			//query.setMaxResults(numRegistros.intValue());
			List<Object[]> diagnosticoList = (List<Object[]>)query.getResultList();
//			query = manager.createNativeQuery("select count(*) from VIEW_EMS_DIAGNOSTICO d "+
//					" where  d.STNO =:filial "+
//					" and (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')");
//			query.setParameter("filial", filial);
			//Remove registro repetido
			for (int i = 0; i < diagnosticoList.size(); i++) {
				Object[] diagnosticoBean = diagnosticoList.get(i);
				for (int j = i+1; j < diagnosticoList.size(); j++) {
					Object[] dBean = diagnosticoList.get(j);
					if(((String)dBean[2]).equals((String)diagnosticoBean[2])){
						diagnosticoList.remove(j);
						break;
					}
				}
				
			}
			
			Integer tamanhoTotalLista = 0;
			if(filial != -1){
				tamanhoTotalLista = diagnosticoList.size();
			}
			for(int i = 0; i < diagnosticoList.size(); i++){
				Object[] pair = diagnosticoList.get(i);	
				//for (Object[] pair : diagnosticoList) {
				String cor = "VERDE";
				DiagnosticoBean bean = new DiagnosticoBean();
				bean.setNumLinhas(tamanhoTotalLista.longValue());
				bean.setFilial((String)pair[0]);
				bean.setModelo((String)pair[1]);
				bean.setNumeroSerie((String)pair[2]);
				bean.setNomeCliente((String)pair[3]);
				bean.setIdFilial(((BigDecimal)pair[22]).toString());
				bean.setInicioGarantia((String)pair[33]);
				bean.setFimGarantia((String)pair[34]);
				bean.setIdFilial(((BigDecimal)pair[22]).toString());
				
				bean.setCorSmu((String)pair[36]);
				bean.setTotalSmu(((BigDecimal)pair[37]).longValue());
				if(((String)pair[36]).equals("red")){
					cor = "VERMELHO";
				}				
				
				//Integer backlog = (Integer)pair[20];
				bean.setBacklog("Não");
				
				if(((String)pair[24]).equals("Sim")){
						cor = "VERMELHO";
						bean.setBacklog("Sim");
				}
				bean.setBacklogCampo("Não");
				if(((String)pair[25]).equals("Sim")){
						cor = "VERMELHO";
						bean.setBacklogCampo("Sim");
						bean.setDataBacklogCampo((String)pair[21]);
						backlogCampo.add(bean);
				}
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklog("Sim");
//				}
				//backlog = (Integer)pair[21];
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklogCampo("Sim");
//				}
				//Integer csa = (Integer)pair[5];
				DetalhesDiagnosticoBean detalhesBean = new DetalhesDiagnosticoBean();
				detalhesBean.setDataAceite((String)pair[31]);
				detalhesBean.setTipoContrato((String)pair[32]);
				bean.setDataBacklogPmp((String)pair[26]);
				detalhesBean.setDataInspecao((String)pair[6]);
				detalhesBean.setDataInspecaoCampo((String)pair[21]);
		
				if(pair[20] != null){
					detalhesBean.setIdOsPalmCampo(((BigDecimal)pair[20]).longValue());
				}
				if(pair[7] != null){
					detalhesBean.setIdOsPalm(((BigDecimal)pair[7]).longValue());
				}
				if(pair[11] != null){
					detalhesBean.setHorimetro(((BigDecimal)pair[11]).longValue());
					detalhesBean.setDataAtualizacaoHorimetro((String)pair[12]);
				}
				detalhesBean.setLevel1(0L);
				detalhesBean.setLevel2(0L);
				detalhesBean.setLevel3(0L);
				if(pair[8] != null){
					detalhesBean.setLevel1(((BigDecimal)pair[8]).longValue());
				}
				bean.setCor("green");
				if(pair[9] != null){
					detalhesBean.setLevel2(((BigDecimal)pair[9]).longValue());
				}
				if(detalhesBean.getLevel2() > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCor("yellow");
				}
				if(pair[10] != null){
					detalhesBean.setLevel3(((BigDecimal)pair[10]).longValue());
				}
				if(detalhesBean.getLevel3() > 0){
					cor = "VERMELHO";
					bean.setCor("red");
				}
				bean.setNumerDiagnosticos(detalhesBean.getLevel1()+detalhesBean.getLevel2()+detalhesBean.getLevel3());
				bean.setDetalhesDiagnosticoBean(detalhesBean);
				bean.setCorSos("green");
				//bean.setTotalSos(((BigDecimal)pair[14]).intValue()+((BigDecimal)pair[15]).intValue()+((BigDecimal)pair[16]).intValue());
				bean.setTotalSos(((BigDecimal)pair[16]).intValue());
				bean.setDataColeta("N/A");
				if(pair[23] != null){
					bean.setDataColeta((String)pair[23]);
				}
				
				//detalhesBean.setNormalSos(((BigDecimal)pair[14]).intValue());
				detalhesBean.setNormalSos(0);
				//detalhesBean.setMonitorarSos(((BigDecimal)pair[15]).intValue());
				detalhesBean.setMonitorarSos(0);
				detalhesBean.setCriticoSos(((BigDecimal)pair[16]).intValue());
				
//				if((((BigDecimal)pair[15])).intValue() > 0){
//					if(cor.equals("VERDE")){
//						cor = "AMARELO";
//					}
//					bean.setCorSos("yellow");
//				}
				if((((BigDecimal)pair[16])).intValue() > 0){
					cor = "VERMELHO";
					bean.setCorSos("red");
				}
				if((((Integer)pair[17])).intValue() > 0){
					bean.setIsLockMy("S");
				} else {
					bean.setIsLockMy("N");
				}
				if(pair[18] != null && ((String)pair[18]).equals("S")){
					bean.setIsLock("S");
					if(this.usuarioBean.getIsAdm() == true){
						bean.setIsLockMy("S");
					}
				} else {
					bean.setIsLock("N");
				}
				bean.setEstimateByFuncionarioLock((String)pair[19]);
				if(cor.equals("VERDE")){
					verde.add(bean);
					//result.add(bean);
				}else if(cor.equals("AMARELO")){
					amarelo.add(bean);
					//result.add(bean);
				}else if(cor.equals("VERMELHO")){
					//Se não é backlog de campo
					if(((String)pair[25]).equals("Não")){
						vermelho.add(bean);
					}
					//result.add(bean);
				}
				if(((String)pair[35]) != null){
					bean.setDataImportacaoSos((String)pair[35]);
				}
			}
			//Collections.sort(result);
			result.addAll(backlogCampo);
			result.addAll(vermelho);
			result.addAll(amarelo);
			result.addAll(verde);
			
			List<DiagnosticoBean> list = new ArrayList<DiagnosticoBean>();
			for (int i = 0; i < result.size(); i++) {
				DiagnosticoBean diagnosticoBean = result.get(i);
				if(diagnosticoBean.getDataBacklogPmp() == null || diagnosticoBean.getDataBacklogPmp().equals("N/A")){
					diagnosticoBean.setDataBacklogPmp("N/A");
					list.add(diagnosticoBean);
					result.remove(i);
					i--;
				}
			}
			result.addAll(list);
			if(diagnosticoList.size() < numRegistros && filial != -1){
				return result;
			}
			if(diagnosticoList.size() > numRegistros){
				for(int i = inicial.intValue(); i < result.size(); i++){
					paginacao.add(result.get(i));
					if(paginacao.size() == numRegistros && filial != -1){
						return paginacao;
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return paginacao;
	}
	public List<DiagnosticoBean> saveDiagnostic(Long filial, String campo){
		EntityManager manager = null;
		List<DiagnosticoBean> result = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> vermelho = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> amarelo = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> verde = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> paginacao = new ArrayList<DiagnosticoBean>();
		
		try {
			
			manager = JpaUtil.getInstance();
			
			String SQL = "select distinct d.STNM, " +//0
			" d.MODELO, " +//1
			" d.NUMERO_SERIE, " +//2
			" d.NOME_CLIENTE,"+//3
			" d.pl numeroDiagnosticos, " +//4
			" '' csa, " +//5
			" d.dataInspecaoPmp," +//6
			" d.idosPalmPmp,"+//7
			" d.nivel1,"+//8
			" d.nivel2,"+//9
			" d.nivel3,"+//10
			" d.horimetro, " +//11
			" d.DATA_ATUALIZACAO_HORIMETRO , " +//12
			" d.gap,"+//13
			"		d.normal,"+//14
			"		d.monitorar,"+//15
			"		d.critico,"+//16
			" 0 isLockMy,"+//17
			"      d.isLock,"+//18
			"      d.estimateBy,"+//19
			// "      d.backlogPmp,"+//20
			// "		d.backlogCampo,"+//21
			"      d.idosPalmCampo,"+//22 20
			"      d.dataInspecaoCampo," +//23 21
			"  d.STNO,"+ //23 22
			" (select distinct CONVERT(varchar(10), MAX(sampled_date), 103) from EMS_SOS where SERIAL_NUMBER = d.NUMERO_SERIE) data_sos, "+//23
			" (select COUNT(*) from EMS_SMU where IS_REJEITADO is null and ID_SEGMENTO is null and NUM_SERIE = d.NUMERO_SERIE) SMU"+//24
			" from VIEW_EMS_DIAGNOSTICO d ";
			if(campo != null && !"".equals(campo)){
				SQL += " where (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')";
			}
			//" and d.NUMERO_SERIE = '0JKM01172'";
			if(filial != -1){
				SQL += " and  d.STNO = "+filial;
			}
			SQL += " order by d.dataInspecaoPmp";
			Query query = manager.createNativeQuery(SQL);
			
			
			//query.setFirstResult(inicial.intValue());
			//query.setMaxResults(numRegistros.intValue());
			List<Object[]> diagnosticoList = (List<Object[]>)query.getResultList();
//			query = manager.createNativeQuery("select count(*) from VIEW_EMS_DIAGNOSTICO d "+
//					" where  d.STNO =:filial "+
//					" and (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')");
//			query.setParameter("filial", filial);
			//Remove registro repetido
			for (int i = 0; i < diagnosticoList.size(); i++) {
				Object[] diagnosticoBean = diagnosticoList.get(i);
				for (int j = i+1; j < diagnosticoList.size(); j++) {
					Object[] dBean = diagnosticoList.get(j);
					if(((String)dBean[2]).equals((String)diagnosticoBean[2])){
						diagnosticoList.remove(j);
						break;
					}
				}
				
			}
			
			Integer tamanhoTotalLista = 0;
			if(filial != -1){
				tamanhoTotalLista = diagnosticoList.size();
			}
			//new EmailHelper().sendSimpleMail("ROTINA DE DIAGNÓSTICO INÍCIO", "Comecei a rodar rotina de diagnóstico ", "cioletti.rodrigo@gmail.com");
			for(int i = 0; i < diagnosticoList.size(); i++){
				Object[] pair = diagnosticoList.get(i);	
				//for (Object[] pair : diagnosticoList) {
				String cor = "VERDE";
				DiagnosticoBean bean = new DiagnosticoBean();
				bean.setNumLinhas(tamanhoTotalLista.longValue());
				bean.setFilial((String)pair[0]);
				bean.setModelo((String)pair[1]);
				bean.setNumeroSerie((String)pair[2]);
				bean.setNomeCliente((String)pair[3]);
				bean.setIdFilial(((BigDecimal)pair[22]).toString());
				//Integer backlog = (Integer)pair[20];
				bean.setBacklog("Não");
				
				if(pair[7] != null){
					SQL = "select count(*) from os_palm_dt dt where dt.OS_PALM_IDOS_PALM = "+(BigDecimal)pair[7]+" and dt.STATUS = 'NC' and dt.ID_EMS_SEGMENTO IS NULL and dt.IS_REJEITADO_EMS is null";
					query = manager.createNativeQuery(SQL);
					Integer backlog = (Integer)query.getSingleResult();
					if(backlog > 0){
						cor = "VERMELHO";
						bean.setBacklog("Sim");
					}else{
						cor = "VERDE";
						bean.setBacklog("Não");
					}
				}
				bean.setBacklogCampo("Não");
				if(pair[20] != null){
					SQL = "select count(*) from sc_os_palm_dt dt where dt.OS_PALM_IDOS_PALM = "+(BigDecimal)pair[20]+" and dt.STATUS = 'NC' and dt.ID_EMS_SEGMENTO IS NULL and dt.IS_REJEITADO_EMS is null";
					query = manager.createNativeQuery(SQL);
					Integer backlog = (Integer)query.getSingleResult();
					if(backlog > 0){
						cor = "VERMELHO";
						bean.setBacklogCampo("Sim");
					}else{
						cor = "VERDE";
						bean.setBacklogCampo("Não");
					}
				}
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklog("Sim");
//				}
				//backlog = (Integer)pair[21];
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklogCampo("Sim");
//				}
				//Integer csa = (Integer)pair[5];
				DetalhesDiagnosticoBean detalhesBean = new DetalhesDiagnosticoBean();
				detalhesBean.setDataAceite("N/A");
				detalhesBean.setTipoContrato("N/A");
				bean.setDataBacklogPmp("N/A");
				if(bean.getBacklog().equals("Sim")){
					SQL = " select c.ID, c.NUMERO_SERIE, c.DATA_ACEITE , (select tc.DESCRICAO from PMP_TIPO_CONTRATO tc where tc.ID = c.ID_TIPO_CONTRATO) tipoContrato from PMP_CONTRATO c"+ 
					"	where c.ID = "+
					"	(select max(cc.id) from PMP_CONTRATO cc"+
					"	where cc.NUMERO_SERIE =:numeroSerie "+
					"	and cc.ID_STATUS_CONTRATO in (select ID from PMP_STATUS_CONTRATO where SIGLA not in ('CEN', 'CNA')))";
					query = manager.createNativeQuery(SQL);
					query.setParameter("numeroSerie", bean.getNumeroSerie());
					List<Object[]> csaList = (List<Object[]>)query.getResultList();
					String tipoContrato = "";
					String dataAceite = "";
					for (Object[] objects : csaList) {
						tipoContrato += (String)objects[3]+" ";
						dataAceite += dateFormatConverter.format(dateFormatDefault.parse((String)objects[2]))+" ";
					}
					detalhesBean.setDataAceite(dataAceite);
					detalhesBean.setTipoContrato(tipoContrato);
					bean.setDataBacklogPmp(dateFormatConverter.format(dateFormatDefault.parse((String)pair[6])));
				}
				
				if(pair[6] == null){
					detalhesBean.setDataInspecao("N/A");
				}else{
					detalhesBean.setDataInspecao(dateFormatConverter.format(dateFormatDefault.parse((String)pair[6])));
				}
				
				if(pair[21] == null){
					detalhesBean.setDataInspecaoCampo("N/A");
				} else {
					detalhesBean.setDataInspecaoCampo(dateFormatConverter.format(dateFormatDefault.parse((String)pair[21])));
				}
				if(pair[7] != null){
					detalhesBean.setIdOsPalm(((BigDecimal)pair[7]).longValue());
				}
				if(pair[20] != null){
					detalhesBean.setIdOsPalmCampo(((BigDecimal)pair[20]).longValue());
				}
				if(pair[11] != null){
					detalhesBean.setHorimetro(((BigDecimal)pair[11]).longValue());
					detalhesBean.setDataAtualizacaoHorimetro(dateFormatConverter.format(dateFormatDefault.parse((String)pair[12])));
				}
				detalhesBean.setLevel1(0L);
				detalhesBean.setLevel2(0L);
				detalhesBean.setLevel3(0L);
				if(pair[8] != null){
					detalhesBean.setLevel1(((BigDecimal)pair[8]).longValue());
				}
				bean.setCor("green");
				if(pair[9] != null){
					detalhesBean.setLevel2(((BigDecimal)pair[9]).longValue());
				}
				if(detalhesBean.getLevel2() > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCor("yellow");
				}
				if(pair[10] != null){
					detalhesBean.setLevel3(((BigDecimal)pair[10]).longValue());
				}
				if(detalhesBean.getLevel3() > 0){
					cor = "VERMELHO";
					bean.setCor("red");
				}
				bean.setNumerDiagnosticos(detalhesBean.getLevel1()+detalhesBean.getLevel2()+detalhesBean.getLevel3());
				bean.setDetalhesDiagnosticoBean(detalhesBean);
				bean.setCorSos("green");
				bean.setTotalSos((Integer)pair[14]+(Integer)pair[15]+(Integer)pair[16]);
				bean.setDataColeta("N/A");
				if(pair[23] != null){
					bean.setDataColeta((String)pair[23]);
				}
				
				bean.setCorSmu("green");
				bean.setTotalSmu(0L);
				if(pair[24] != null && Long.valueOf((Integer)pair[24]) > 0){
					bean.setCorSmu("red");
					bean.setTotalSmu(Long.valueOf((Integer)pair[24]));
				}
				
				detalhesBean.setNormalSos((Integer)pair[14]);
				detalhesBean.setMonitorarSos((Integer)pair[15]);
				detalhesBean.setCriticoSos((Integer)pair[16]);
				
				if(((Integer)pair[15]) > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCorSos("yellow");
				}
				if(((Integer)pair[16]) > 0){
					cor = "VERMELHO";
					bean.setCorSos("red");
				}
				if(((Integer)pair[17]) > 0){
					bean.setIsLockMy("S");
				} else {
					bean.setIsLockMy("N");
				}
				if(((Integer)pair[18]) > 0){
					bean.setIsLock("S");
					//if(this.usuarioBean.getIsAdm() == true){
						bean.setIsLockMy("S");
					//}
				} else {
					bean.setIsLock("N");
				}
				bean.setEstimateByFuncionarioLock((String)pair[19]);
				if(cor.equals("VERDE")){
					verde.add(bean);
					//result.add(bean);
				}else if(cor.equals("AMARELO")){
					amarelo.add(bean);
					//result.add(bean);
				}else if(cor.equals("VERMELHO")){
					vermelho.add(bean);
					//result.add(bean);
				}
			}
			
			//Collections.sort(result);
			result.addAll(vermelho);
			result.addAll(amarelo);
			result.addAll(verde);
			
			for (DiagnosticoBean bean : result) {
				EmsDiagnosticoView dView = new EmsDiagnosticoView();
				query = manager.createQuery("from EmsDiagnosticoView where numeroSerie =:numeroSerie");
				query.setParameter("numeroSerie", bean.getNumeroSerie());
				if(query.getResultList().size() > 0){
					dView = (EmsDiagnosticoView)query.getSingleResult();
				}
				dView.setNumeroSerie(bean.getNumeroSerie());
				dView.setFilial(Long.valueOf(bean.getIdFilial()));
				dView.setNomeCliente(bean.getNomeCliente());
				dView.setModelo(bean.getModelo());
				dView.setNomeFilial(bean.getFilial());
				dView.setBacklogPmp(bean.getBacklog());
				dView.setBacklogCampo(bean.getBacklogCampo());
				dView.setDataBacklogPmp(bean.getDataBacklogPmp());
				dView.setCor(bean.getCor());
				dView.setNumeroDiagnosticos(bean.getNumerDiagnosticos());
				dView.setCorSos(bean.getCorSos());
				dView.setDataColeta(bean.getDataColeta());
				dView.setTotalSos(bean.getTotalSos().longValue());
				dView.setNormalSos(bean.getDetalhesDiagnosticoBean().getNormalSos().longValue());
				dView.setMonitorarSos(bean.getDetalhesDiagnosticoBean().getMonitorarSos().longValue());
				dView.setCriticoSos(bean.getDetalhesDiagnosticoBean().getCriticoSos().longValue());
				dView.setDataAceitePmp(bean.getDetalhesDiagnosticoBean().getDataAceite());
				dView.setTipoContratoPmp(bean.getDetalhesDiagnosticoBean().getTipoContrato());
				dView.setDataInspecaoPmp(bean.getDetalhesDiagnosticoBean().getDataInspecao());
				dView.setDataInspecaoCampo(bean.getDetalhesDiagnosticoBean().getDataInspecaoCampo());
				dView.setIdOsPalmPmp(bean.getDetalhesDiagnosticoBean().getIdOsPalm());
				dView.setIdOsPalmCampo(bean.getDetalhesDiagnosticoBean().getIdOsPalmCampo());
				dView.setHorimetro(bean.getDetalhesDiagnosticoBean().getHorimetro());
				dView.setDataAtualizacaoHorimetro(bean.getDetalhesDiagnosticoBean().getDataAtualizacaoHorimetro());
				dView.setLevel1(bean.getDetalhesDiagnosticoBean().getLevel1());
				dView.setLevel2(bean.getDetalhesDiagnosticoBean().getLevel2());
				dView.setLevel3(bean.getDetalhesDiagnosticoBean().getLevel3());
				dView.setIsLockMy(bean.getIsLock());
				dView.setEstimateByFuncionarioLock(bean.getEstimateByFuncionarioLock());
				//this.findallPipPspGarantia(bean.getNumeroSerie(), bean);
				//dView.setDataInicioGarantia(bean.getInicioGarantia());
				//dView.setDataFimGarantia(bean.getFimGarantia());
				dView.setCorSmu(bean.getCorSmu());
				dView.setTotalSmu(bean.getTotalSmu());
				manager.getTransaction().begin();
				manager.merge(dView);
				manager.getTransaction().commit();
				
			}
			
			
//			List<DiagnosticoBean> list = new ArrayList<DiagnosticoBean>();
//			for (int i = 0; i < result.size(); i++) {
//				DiagnosticoBean diagnosticoBean = result.get(i);
//				if(diagnosticoBean.getDataBacklogPmp().equals("N/A")){
//					list.add(diagnosticoBean);
//					result.remove(i);
//					i--;
//				}
//			}
//			result.addAll(list);
//			if(diagnosticoList.size() < numRegistros && filial != -1){
//				return result;
//			}
//			if(diagnosticoList.size() > numRegistros){
//				for(int i = inicial.intValue(); i < result.size(); i++){
//					paginacao.add(result.get(i));
//					if(paginacao.size() == numRegistros && filial != -1){
//						return paginacao;
//					}
//				}
//			}
			
			//new EmailHelper().sendSimpleMail("ROTINA DE DIAGNÓSTICO FIM", "terminei de rodar rotina de diagnóstico ", "cioletti.rodrigo@gmail.com");
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			

			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			new EmailHelper().sendSimpleMail("ROTINA DE DIAGNÓSTICO", "Erro ao rodar rotina de diagnóstico "+st.toString(), "rodrigoo@rdrsistemas.com.br");
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return paginacao;
	}
	
	public List<DiagnosticoBean> saveDiagnosticJob(Long filial, String campo){
		EntityManager manager = null;
		List<DiagnosticoBean> result = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> vermelho = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> amarelo = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> verde = new ArrayList<DiagnosticoBean>();
		List<DiagnosticoBean> paginacao = new ArrayList<DiagnosticoBean>();
		
		try {
			
			manager = JpaUtil.getInstance();
			
			String SQL = "select distinct d.STNM, " +//0
			" d.MODELO, " +//1
			" d.NUMERO_SERIE, " +//2
			" d.NOME_CLIENTE,"+//3
			" d.pl numeroDiagnosticos, " +//4
			" '' csa, " +//5
			" d.dataInspecaoPmp," +//6
			" d.idosPalmPmp,"+//7
			" d.nivel1,"+//8
			" d.nivel2,"+//9
			" d.nivel3,"+//10
			" d.horimetro, " +//11
			" d.DATA_ATUALIZACAO_HORIMETRO , " +//12
			" d.gap,"+//13
			"		d.normal,"+//14
			"		d.monitorar,"+//15
			"		d.critico,"+//16
			" 0 isLockMy,"+//17
			"      d.isLock,"+//18
			"      d.estimateBy,"+//19
			// "      d.backlogPmp,"+//20
			// "		d.backlogCampo,"+//21
			"      d.idosPalmCampo,"+//22 20
			"      d.dataInspecaoCampo," +//23 21
			"  d.STNO,"+ //23 22
			" (select distinct CONVERT(varchar(10), MAX(sampled_date), 103) from EMS_SOS where SERIAL_NUMBER = d.NUMERO_SERIE) data_sos, "+//23
			" (select distinct CONVERT(varchar(10), MAX(DATA_IMPORTACAO), 103) from EMS_SOS where SERIAL_NUMBER = d.NUMERO_SERIE) DATA_IMPORTACAO, "+//24
			" (select COUNT(*) from EMS_SMU where IS_REJEITADO is null and ID_SEGMENTO is null and NUM_SERIE = d.NUMERO_SERIE) SMU"+//25
			" from VIEW_EMS_DIAGNOSTICO d ";
			if(campo != null && !"".equals(campo)){
				SQL += " where (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')";
			}
			//" and d.NUMERO_SERIE = '0JKM01172'";
			if(filial != -1){
				SQL += " and  d.STNO = "+filial;
			}
			SQL += " order by d.dataInspecaoPmp";
			Query query = manager.createNativeQuery(SQL);
			
			
			//query.setFirstResult(inicial.intValue());
			//query.setMaxResults(numRegistros.intValue());
			List<Object[]> diagnosticoList = (List<Object[]>)query.getResultList();
//			query = manager.createNativeQuery("select count(*) from VIEW_EMS_DIAGNOSTICO d "+
//					" where  d.STNO =:filial "+
//					" and (d.NUMERO_SERIE like '%"+campo+"%' or d.NOME_CLIENTE LIKE '%"+campo+"%' or d.MODELO LIKE '%"+campo+"%')");
//			query.setParameter("filial", filial);
			//Remove registro repetido
			for (int i = 0; i < diagnosticoList.size(); i++) {
				Object[] diagnosticoBean = diagnosticoList.get(i);
				for (int j = i+1; j < diagnosticoList.size(); j++) {
					Object[] dBean = diagnosticoList.get(j);
					if(((String)dBean[2]).equals((String)diagnosticoBean[2])){
						diagnosticoList.remove(j);
						break;
					}
				}
				
			}
			
			Integer tamanhoTotalLista = 0;
			if(filial != -1){
				tamanhoTotalLista = diagnosticoList.size();
			}
			//new EmailHelper().sendSimpleMail("ROTINA DE DIAGNÓSTICO INÍCIO", "Comecei a rodar rotina de diagnóstico ", "cioletti.rodrigo@gmail.com");
			for(int i = 0; i < diagnosticoList.size(); i++){
				Object[] pair = diagnosticoList.get(i);	
				//for (Object[] pair : diagnosticoList) {
				String cor = "VERDE";
				DiagnosticoBean bean = new DiagnosticoBean();
				bean.setNumLinhas(tamanhoTotalLista.longValue());
				bean.setFilial((String)pair[0]);
				bean.setModelo((String)pair[1]);
				bean.setNumeroSerie((String)pair[2]);
				bean.setNomeCliente((String)pair[3]);
				bean.setIdFilial(((BigDecimal)pair[22]).toString());
				//Integer backlog = (Integer)pair[20];
				bean.setBacklog("Não");
				
				if(pair[7] != null){
					SQL = "select count(*) from os_palm_dt dt where dt.OS_PALM_IDOS_PALM = "+(BigDecimal)pair[7]+" and dt.STATUS = 'NC' and dt.ID_EMS_SEGMENTO IS NULL and dt.IS_REJEITADO_EMS is null";
					query = manager.createNativeQuery(SQL);
					Integer backlog = (Integer)query.getSingleResult();
					if(backlog > 0){
						cor = "VERMELHO";
						bean.setBacklog("Sim");
					}else{
						cor = "VERDE";
						bean.setBacklog("Não");
					}
				}
				bean.setBacklogCampo("Não");
				if(pair[20] != null){
					SQL = "select count(*) from sc_os_palm_dt dt where dt.OS_PALM_IDOS_PALM = "+(BigDecimal)pair[20]+" and dt.STATUS = 'NC' and dt.ID_EMS_SEGMENTO IS NULL and dt.IS_REJEITADO_EMS is null";
					query = manager.createNativeQuery(SQL);
					Integer backlog = (Integer)query.getSingleResult();
					if(backlog > 0){
						cor = "VERMELHO";
						bean.setBacklogCampo("Sim");
					}else{
						cor = "VERDE";
						bean.setBacklogCampo("Não");
					}
				}
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklog("Sim");
//				}
				//backlog = (Integer)pair[21];
//				if(backlog > 0){
//					cor = "VERMELHO";
//					bean.setBacklogCampo("Sim");
//				}
				//Integer csa = (Integer)pair[5];
				DetalhesDiagnosticoBean detalhesBean = new DetalhesDiagnosticoBean();
				detalhesBean.setDataAceite("N/A");
				detalhesBean.setTipoContrato("N/A");
				bean.setDataBacklogPmp("N/A");
				if(bean.getBacklog().equals("Sim")){
					SQL = " select c.ID, c.NUMERO_SERIE, c.DATA_ACEITE , (select tc.DESCRICAO from PMP_TIPO_CONTRATO tc where tc.ID = c.ID_TIPO_CONTRATO) tipoContrato from PMP_CONTRATO c"+ 
					"	where c.ID = "+
					"	(select max(cc.id) from PMP_CONTRATO cc"+
					"	where cc.NUMERO_SERIE =:numeroSerie "+
					"	and cc.ID_STATUS_CONTRATO in (select ID from PMP_STATUS_CONTRATO where SIGLA not in ('CEN', 'CNA')))";
					query = manager.createNativeQuery(SQL);
					query.setParameter("numeroSerie", bean.getNumeroSerie());
					List<Object[]> csaList = (List<Object[]>)query.getResultList();
					String tipoContrato = "";
					String dataAceite = "";
					for (Object[] objects : csaList) {
						tipoContrato += (String)objects[3]+" ";
						dataAceite += dateFormatConverter.format(dateFormatDefault.parse((String)objects[2]))+" ";
					}
					detalhesBean.setDataAceite(dataAceite);
					detalhesBean.setTipoContrato(tipoContrato);
					bean.setDataBacklogPmp(dateFormatConverter.format(dateFormatDefault.parse((String)pair[6])));
				}
				
				if(pair[6] == null){
					detalhesBean.setDataInspecao("N/A");
				}else{
					detalhesBean.setDataInspecao(dateFormatConverter.format(dateFormatDefault.parse((String)pair[6])));
				}
				
				if(pair[21] == null){
					detalhesBean.setDataInspecaoCampo("N/A");
				} else {
					detalhesBean.setDataInspecaoCampo(dateFormatConverter.format(dateFormatDefault.parse((String)pair[21])));
				}
				if(pair[7] != null){
					detalhesBean.setIdOsPalm(((BigDecimal)pair[7]).longValue());
				}
				if(pair[20] != null){
					detalhesBean.setIdOsPalmCampo(((BigDecimal)pair[20]).longValue());
				}
				if(pair[11] != null){
					detalhesBean.setHorimetro(((BigDecimal)pair[11]).longValue());
					detalhesBean.setDataAtualizacaoHorimetro(dateFormatConverter.format(dateFormatDefault.parse((String)pair[12])));
				}
				detalhesBean.setLevel1(0L);
				detalhesBean.setLevel2(0L);
				detalhesBean.setLevel3(0L);
				if(pair[8] != null){
					detalhesBean.setLevel1(((BigDecimal)pair[8]).longValue());
				}
				bean.setCor("green");
				if(pair[9] != null){
					detalhesBean.setLevel2(((BigDecimal)pair[9]).longValue());
				}
				if(detalhesBean.getLevel2() > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCor("yellow");
				}
				if(pair[10] != null){
					detalhesBean.setLevel3(((BigDecimal)pair[10]).longValue());
				}
				if(detalhesBean.getLevel3() > 0){
					cor = "VERMELHO";
					bean.setCor("red");
				}
				bean.setNumerDiagnosticos(detalhesBean.getLevel1()+detalhesBean.getLevel2()+detalhesBean.getLevel3());
				bean.setDetalhesDiagnosticoBean(detalhesBean);
				bean.setCorSos("green");
				bean.setTotalSos((Integer)pair[14]+(Integer)pair[15]+(Integer)pair[16]);
				//bean.setTotalSos((Integer)pair[16]);
				bean.setDataColeta("N/A");
				if(pair[23] != null){
					bean.setDataColeta((String)pair[23]);
				}
				if(pair[24] != null){
					bean.setDataImportacaoSos((String)pair[24]);
				}
				bean.setCorSmu("green");
				bean.setTotalSmu(0L);
				if(pair[25] != null && Long.valueOf((Integer)pair[25]) > 0){
					bean.setCorSmu("red");
					bean.setTotalSmu(Long.valueOf((Integer)pair[25]));
				}
				
				detalhesBean.setNormalSos((Integer)pair[14]);
				detalhesBean.setMonitorarSos((Integer)pair[15]);
				detalhesBean.setCriticoSos((Integer)pair[16]);
				
				if(((Integer)pair[15]) > 0){
					if(cor.equals("VERDE")){
						cor = "AMARELO";
					}
					bean.setCorSos("yellow");
				}
				if(((Integer)pair[16]) > 0){
					cor = "VERMELHO";
					bean.setCorSos("red");
				}
				if(((Integer)pair[17]) > 0){
					bean.setIsLockMy("S");
				} else {
					bean.setIsLockMy("N");
				}
				if(((Integer)pair[18]) > 0){
					bean.setIsLock("S");
					//if(this.usuarioBean.getIsAdm() == true){
					bean.setIsLockMy("S");
					//}
				} else {
					bean.setIsLock("N");
				}
				bean.setEstimateByFuncionarioLock((String)pair[19]);
				if(cor.equals("VERDE")){
					verde.add(bean);
					//result.add(bean);
				}else if(cor.equals("AMARELO")){
					amarelo.add(bean);
					//result.add(bean);
				}else if(cor.equals("VERMELHO")){
					vermelho.add(bean);
					//result.add(bean);
				}
			}
			
			//Collections.sort(result);
			result.addAll(vermelho);
			result.addAll(amarelo);
			result.addAll(verde);
			
			for (DiagnosticoBean bean : result) {
				EmsDiagnosticoView dView = new EmsDiagnosticoView();
//				query = manager.createQuery("from EmsDiagnosticoView where numeroSerie =:numeroSerie");
//				query.setParameter("numeroSerie", bean.getNumeroSerie());
//				if(query.getResultList().size() > 0){
//					dView = (EmsDiagnosticoView)query.getSingleResult();
//				}
				dView.setNumeroSerie(bean.getNumeroSerie());
				dView.setFilial(Long.valueOf(bean.getIdFilial()));
				dView.setNomeCliente(bean.getNomeCliente());
				dView.setModelo(bean.getModelo());
				dView.setNomeFilial(bean.getFilial());
				dView.setBacklogPmp(bean.getBacklog());
				dView.setBacklogCampo(bean.getBacklogCampo());
				dView.setDataBacklogPmp(bean.getDataBacklogPmp());
				dView.setCor(bean.getCor());
				dView.setNumeroDiagnosticos(bean.getNumerDiagnosticos());
				dView.setCorSos(bean.getCorSos());
				dView.setDataColeta(bean.getDataColeta());
				dView.setTotalSos(bean.getTotalSos().longValue());
				dView.setNormalSos(bean.getDetalhesDiagnosticoBean().getNormalSos().longValue());
				dView.setMonitorarSos(bean.getDetalhesDiagnosticoBean().getMonitorarSos().longValue());
				dView.setCriticoSos(bean.getDetalhesDiagnosticoBean().getCriticoSos().longValue());
				dView.setDataAceitePmp(bean.getDetalhesDiagnosticoBean().getDataAceite());
				dView.setTipoContratoPmp(bean.getDetalhesDiagnosticoBean().getTipoContrato());
				dView.setDataInspecaoPmp(bean.getDetalhesDiagnosticoBean().getDataInspecao());
				dView.setDataInspecaoCampo(bean.getDetalhesDiagnosticoBean().getDataInspecaoCampo());
				dView.setIdOsPalmPmp(bean.getDetalhesDiagnosticoBean().getIdOsPalm());
				dView.setIdOsPalmCampo(bean.getDetalhesDiagnosticoBean().getIdOsPalmCampo());
				dView.setHorimetro(bean.getDetalhesDiagnosticoBean().getHorimetro());
				dView.setDataAtualizacaoHorimetro(bean.getDetalhesDiagnosticoBean().getDataAtualizacaoHorimetro());
				dView.setLevel1(bean.getDetalhesDiagnosticoBean().getLevel1());
				dView.setLevel2(bean.getDetalhesDiagnosticoBean().getLevel2());
				dView.setLevel3(bean.getDetalhesDiagnosticoBean().getLevel3());
				dView.setIsLockMy(bean.getIsLock());
				dView.setEstimateByFuncionarioLock(bean.getEstimateByFuncionarioLock());
				this.findallPipPspGarantia(bean.getNumeroSerie(), bean);
				dView.setDataInicioGarantia(bean.getInicioGarantia());
				dView.setDataFimGarantia(bean.getFimGarantia());
				dView.setDataImportacaoSos(bean.getDataImportacaoSos());
				dView.setCorSmu(bean.getCorSmu());
				dView.setTotalSmu(bean.getTotalSmu());
				manager.getTransaction().begin();
				manager.merge(dView);
				manager.getTransaction().commit();
				
			}
			
			
//			List<DiagnosticoBean> list = new ArrayList<DiagnosticoBean>();
//			for (int i = 0; i < result.size(); i++) {
//				DiagnosticoBean diagnosticoBean = result.get(i);
//				if(diagnosticoBean.getDataBacklogPmp().equals("N/A")){
//					list.add(diagnosticoBean);
//					result.remove(i);
//					i--;
//				}
//			}
//			result.addAll(list);
//			if(diagnosticoList.size() < numRegistros && filial != -1){
//				return result;
//			}
//			if(diagnosticoList.size() > numRegistros){
//				for(int i = inicial.intValue(); i < result.size(); i++){
//					paginacao.add(result.get(i));
//					if(paginacao.size() == numRegistros && filial != -1){
//						return paginacao;
//					}
//				}
//			}
			
			//new EmailHelper().sendSimpleMail("ROTINA DE DIAGNÓSTICO FIM", "terminei de rodar rotina de diagnóstico ", "cioletti.rodrigo@gmail.com");
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			
			
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			new EmailHelper().sendSimpleMail("ROTINA DE DIAGNÓSTICO", "Erro ao rodar rotina de diagnóstico "+st.toString(), "rodrigoo@rdrsistemas.com.br");
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return paginacao;
	}
	
	public void findallPipPspGarantia(String serie, DiagnosticoBean bean){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		Connection con = null;
		Integer numPip = 0;
		Integer numPsp = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");

		try {
			con = ConectionDbs.getConnecton();
			String SQL  = "SELECT WCLPIPS0.EQMFCD, WCLPIPS0.EQMFSN, WCLPIPS0.PIPNO, WCLPIPS0.OPNDT8"+
					" FROM "+IConstantAccess.LIB_DBS+".WCLPIPS0 WCLPIPS0"+
					" WHERE (WCLPIPS0.OPNDT8=0) "+
					" AND WCLPIPS0.EQMFSN = '"+serie+"'";

			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			String aux;
			while(rs.next()){
				aux =  rs.getString(3).substring(0,2);
				if(aux.equals("PS")){
					numPsp++;
				}
				if (aux.equals("PI")){
					numPip++;
				}
			}
			bean.setPip(numPip);
			bean.setPsp(numPsp);
			
			SQL  = "SELECT b.WRSTD8 inicio, b.WAEXD8 fim FROM "+IConstantAccess.LIB_DBS+".EMPEQPD0 a, "+IConstantAccess.LIB_DBS+".WPPEWAR0 b"+
					" where a.EQMFCD = b.EQMFCD"+
					" and a.EQMFS2 = b.EQMFSN"+
					" and b.EQMFSN = '"+serie+"'" +
					" order by b.WAEXD8 desc";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			
			if(rs.next()){
				bean.setInicioGarantia(dateFormat.format((dateFormat2.parse(rs.getString(1)))));
				bean.setFimGarantia(dateFormat.format((dateFormat2.parse(rs.getString(2)))));
			}else{
				SQL  = "SELECT b.WRSTD8 inicio, b.WAEXD8 fim FROM "+IConstantAccess.LIB_DBS+".EMPEQPD0 a, "+IConstantAccess.LIB_DBS+".WPPEWAR0 b"+
				" where a.EQMFCD = b.EQMFCD"+
				" and a.EQMFS2 = b.EQMFSN"+
				" and b.EQMFSN = (select EQMFS2 FROM "+IConstantAccess.LIB_DBS+".EMPEQPD0 b where b.RDMSR1 = '"+serie+"')" +
				" order by b.WAEXD8 desc";
				prstmt_ = con.prepareStatement(SQL);
				rs = prstmt_.executeQuery();
				if(rs.next()){
					bean.setInicioGarantia(dateFormat.format((dateFormat2.parse(rs.getString(1)))));
					bean.setFimGarantia(dateFormat.format((dateFormat2.parse(rs.getString(2)))));
				}
			}
			

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null){
					con.close();
				}
				if(prstmt_ != null){
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public DiagnosticoBean findallPipPspGarantia(String serie){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		Connection con = null;
		DiagnosticoBean bean = new DiagnosticoBean();
		Integer numPip = 0;
		Integer numPsp = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");

		try {
			con = ConectionDbs.getConnecton();
			String SQL  = "SELECT WCLPIPS0.EQMFCD, WCLPIPS0.EQMFSN, WCLPIPS0.PIPNO, WCLPIPS0.OPNDT8"+
					" FROM "+IConstantAccess.LIB_DBS+".WCLPIPS0 WCLPIPS0"+
					" WHERE (WCLPIPS0.OPNDT8=0) "+
					" AND WCLPIPS0.EQMFSN = '"+serie+"'";

			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			String aux;
			while(rs.next()){
				aux =  rs.getString(3).substring(0,2);
				if(aux.equals("PS")){
					numPsp++;
				}
				if (aux.equals("PI")){
					numPip++;
				}
			}
			bean.setPip(numPip);
			bean.setPsp(numPsp);
			
			SQL  = "SELECT b.WRSTD8 inicio, b.WAEXD8 fim FROM "+IConstantAccess.LIB_DBS+".EMPEQPD0 a, "+IConstantAccess.LIB_DBS+".WPPEWAR0 b"+
					" where a.EQMFCD = b.EQMFCD"+
					" and a.EQMFS2 = b.EQMFSN"+
					" and b.EQMFSN = '"+serie+"'" +
					" order by b.WAEXD8 desc";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			
			if(rs.next()){
				bean.setInicioGarantia(dateFormat.format((dateFormat2.parse(rs.getString(1)))));
				bean.setFimGarantia(dateFormat.format((dateFormat2.parse(rs.getString(2)))));
			}else{
				SQL  = "SELECT b.WRSTD8 inicio, b.WAEXD8 fim FROM "+IConstantAccess.LIB_DBS+".EMPEQPD0 a, "+IConstantAccess.LIB_DBS+".WPPEWAR0 b"+
				" where a.EQMFCD = b.EQMFCD"+
				" and a.EQMFS2 = b.EQMFSN"+
				" and b.EQMFSN = (select EQMFS2 FROM "+IConstantAccess.LIB_DBS+".EMPEQPD0 b where b.RDMSR1 = '"+serie+"')" +
				" order by b.WAEXD8 desc";
				prstmt_ = con.prepareStatement(SQL);
				rs = prstmt_.executeQuery();
				if(rs.next()){
					bean.setInicioGarantia(dateFormat.format((dateFormat2.parse(rs.getString(1)))));
					bean.setFimGarantia(dateFormat.format((dateFormat2.parse(rs.getString(2)))));
				}
			}
			

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null){
					con.close();
				}
				if(prstmt_ != null){
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bean;
	}

//	public static void main(String[] args) {
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
//		try {
//			System.out.println(dateFormat.format((dateFormat2.parse("20130119"))));
//
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
	
	public List<TipoOportunidadeBean> findAllTipoOportunidade(){
		List<TipoOportunidadeBean> listForm = new ArrayList<TipoOportunidadeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsTipoOportunidade");
			List<EmsTipoOportunidade> tipoOptList = (List<EmsTipoOportunidade>) query.getResultList();
			for (EmsTipoOportunidade emsTipoOportunidade : tipoOptList) {
				TipoOportunidadeBean tpOptBean = new TipoOportunidadeBean();
				tpOptBean.setId(emsTipoOportunidade.getId());
				tpOptBean.setDescricao(emsTipoOportunidade.getDescricao());
				tpOptBean.setSigla(emsTipoOportunidade.getSigla());
				listForm.add(tpOptBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	public List<StatusOportunidadeBean> findAllStatusOportunidade(){
		List<StatusOportunidadeBean> listForm = new ArrayList<StatusOportunidadeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from EmsStatusOportunidade order by descricao");
			List<EmsStatusOportunidade> statusOptList = (List<EmsStatusOportunidade>) query.getResultList();
			for (EmsStatusOportunidade emsStatusOportunidade : statusOptList) {
				StatusOportunidadeBean tpOptBean = new StatusOportunidadeBean();
				tpOptBean.setId(emsStatusOportunidade.getId());
				tpOptBean.setDescricao(emsStatusOportunidade.getDescricao());
				tpOptBean.setSigla(emsStatusOportunidade.getSigla());
				listForm.add(tpOptBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	
	public Collection<JobControlBean> findAllJobControl() {
		Collection<JobControlBean> listForm = new ArrayList<JobControlBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select respar from jobcontrol order by respar");
			List<String> list = (List<String>) query.getResultList();
			for (String jbctr : list) {
				JobControlBean bean = new JobControlBean();
				bean.setDescricao(jbctr);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return listForm;
	}
	
	public Collection<ComponenteCodeBean> findAllCompCode(String caracter) {
		Collection<ComponenteCodeBean> listForm = new ArrayList<ComponenteCodeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select cptcd, cptcdd from cptcd where cptcdd like '"+caracter.toUpperCase()+"%' or cptcd = '"+caracter+"'");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] jbcd : list) {
				ComponenteCodeBean bean = new ComponenteCodeBean();
				bean.setId((String)jbcd[0]);
				bean.setDescricao((String)jbcd[1]);
				bean.setLabel((String)jbcd[0]+" - "+(String)jbcd[1]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	
	/**
	 * Recupera um job code específico
	 * @return
	 */
	public Collection<JobCodeBean> findAllJobCode() {
		Collection<JobCodeBean> listForm = new ArrayList<JobCodeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select distinct jbcd, jbcdds from jbcd order by jbcd");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] jbcd : list) {
				JobCodeBean bean = new JobCodeBean();
				bean.setId((String)jbcd[0]);
				bean.setDescricao((String)jbcd[1]);
				bean.setLabel((String)jbcd[0]+" - "+(String)jbcd[1]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	
	public Collection<JobCodeBean> findAllJobCode(String caracter) {
		Collection<JobCodeBean> listForm = new ArrayList<JobCodeBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select distinct jbcd, jbcdds from jbcd where jbcdds like '"+caracter.toUpperCase()+"%' or jbcd = '"+caracter+"'" +
					" order by jbcd");
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] jbcd : list) {
				JobCodeBean bean = new JobCodeBean();
				bean.setId((String)jbcd[0]);
				bean.setDescricao((String)jbcd[1]);
				bean.setLabel((String)jbcd[0]+" - "+(String)jbcd[1]);
				listForm.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return listForm;
	}
	public DiagnosticoBean findHorimetroDataAtualizacao(DiagnosticoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select HORIMETRO, convert(varchar(10),DATA_ATUALIZACAO, 103) from PMP_MAQUINA_PL where id ="+
											  "	(select MAX(id) from PMP_MAQUINA_PL where NUMERO_SERIE =:NUMERO_SERIE" +
											  "  and HORIMETRO is not null)");
			query.setParameter("NUMERO_SERIE", bean.getNumeroSerie());
			if(query.getResultList().size() > 0){
				Object[] pair = (Object[])query.getSingleResult();
				bean.getDetalhesDiagnosticoBean().setHorimetro(((BigDecimal)pair[0]).longValue());
				bean.getDetalhesDiagnosticoBean().setDataAtualizacaoHorimetro((String)pair[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bean;
	}
}
