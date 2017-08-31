package com.emsolution.business;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.emsolution.bean.ArqSosBean;
import com.emsolution.bean.SosBean;
import com.emsolution.bean.UsuarioBean;
import com.emsolution.entity.EmsArquivoSos;
import com.emsolution.entity.EmsSegmento;
import com.emsolution.entity.EmsSos;
import com.emsolution.entity.TwFuncionario;
import com.emsolution.util.JpaUtil;

public class SosBusiness {
	private UsuarioBean usuarioBean;
	
	public SosBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public Boolean fazerUploadSosTxt(byte[] bytes, String tipoAnalise, String nomeArquivo)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aaa", Locale.ENGLISH);
		int pulaLinha = 0;
		InputStream is = null;

		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Set<String> listaSerie = new HashSet(0);
			//incluir a rotina de salvar na tabela EMS_ARQUIVO_SOS
			EmsArquivoSos emsSos = new EmsArquivoSos();
			DiagnosticoBusiness business = new DiagnosticoBusiness(this.usuarioBean);
			//setar objeto
			emsSos.setNomeArquivo(nomeArquivo);
			emsSos.setData(new Date());
			emsSos.setIdFuncionario(this.usuarioBean.getMatricula());
			emsSos.setTipoAnalise(tipoAnalise);
			//inserir no banco
			manager.persist(emsSos);
			manager.getTransaction().commit();			
			is = new ByteArrayInputStream(bytes);
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			Query query = null;
			String linha = null;
			if(tipoAnalise.equals("AO")){
				while((linha = bfReader.readLine()) != null ){
//				String Teste []= linha.split("[|]");  
//				if(pulaLinha == 132){
//					System.out.println(pulaLinha);
//				}
					if(pulaLinha > 2 ){
						manager.getTransaction().begin();
						EmsSos sos = new EmsSos();
						String[] linhaSplit = linha.split("[|]"); 
						query = manager.createNativeQuery("select ID_EMS_SEGMENTO from EMS_SOS where ID_EMS_SEGMENTO is not null	and TEXT_ID =:TEXT_ID");
						query.setParameter("TEXT_ID", linhaSplit[0]);
						if(query.getResultList().size() > 0){
							BigDecimal idEmsSegmento = (BigDecimal)query.getSingleResult();
							sos.setIdEmsSegmento(manager.find(EmsSegmento.class, idEmsSegmento.longValue()));
						}
						sos.setTextId(linhaSplit[0]);
						sos.setProcessDate(dateFormat.parse(linhaSplit[1]));
						sos.setDealerCode(linhaSplit[2]);
						sos.setCustomer(linhaSplit[3]);
						sos.setSerialNumber("0"+linhaSplit[4]);
						sos.setEquipNumber(linhaSplit[5]);
						sos.setCompCode(linhaSplit[6]);
						sos.setCompDesc(linhaSplit[7]);
						sos.setModel(linhaSplit[8]);
						sos.setManufacturer(linhaSplit[9]);
						sos.setJobsite(linhaSplit[10]);
						sos.setSampledDate(dateFormat.parse(linhaSplit[11]));
						sos.setShipTime(linhaSplit[12]);
						sos.setFluidBrand(linhaSplit[13]);
						sos.setFluidType(linhaSplit[14]);
						sos.setFluidWeight(linhaSplit[15]);
						sos.setFluidAdd(linhaSplit[16]);
						sos.setFluidAddUnits(linhaSplit[17]);
						sos.setMeter(linhaSplit[18]);
						sos.setMeterUnits(linhaSplit[19]);
						sos.setMeterOnFluid(linhaSplit[20]);
						sos.setFluidChanged(linhaSplit[21]);
						sos.setFilterChanged(linhaSplit[22]);
						sos.setKidneyLoop(linhaSplit[23]);
						sos.setLabelNo(linhaSplit[24]);
						sos.setShopJobNo(linhaSplit[25]);
						sos.setOverallInterp(linhaSplit[26]);
						sos.setDebris(linhaSplit[27]);
						sos.setW(linhaSplit[28]);
						sos.setH2o(linhaSplit[29]);
						sos.setA(linhaSplit[30]);
						sos.setF(linhaSplit[31]);
						sos.setSt(linhaSplit[32]);
						sos.setOxi(linhaSplit[33]);
						sos.setNit(linhaSplit[34]);
						sos.setSul(linhaSplit[35]);
						sos.setAl(linhaSplit[36]);
						sos.setBa(linhaSplit[37]);
						sos.setB(linhaSplit[38]);
						sos.setCd(linhaSplit[39]);
						sos.setCa(linhaSplit[40]);
						sos.setCr(linhaSplit[41]);
						sos.setCu(linhaSplit[42]);
						sos.setFe(linhaSplit[43]);
						sos.setPb(linhaSplit[44]);
						sos.setMg(linhaSplit[45]);
						sos.setMn(linhaSplit[46]);
						sos.setMo(linhaSplit[47]);
						sos.setNi(linhaSplit[48]);
						sos.setP(linhaSplit[49]);
						sos.setK(linhaSplit[50]);
						sos.setSi(linhaSplit[51]);
						sos.setAg(linhaSplit[52]);
						sos.setNa(linhaSplit[53]);
						sos.setS(linhaSplit[54]);
						sos.setSn(linhaSplit[55]);
						sos.setTi(linhaSplit[56]);
						sos.setV(linhaSplit[57]);
						sos.setZn(linhaSplit[58]);
						sos.setIso(linhaSplit[59]);
						sos.setU4(linhaSplit[60]);
						sos.setU6(linhaSplit[61]);
						sos.setU10(linhaSplit[62]);
						sos.setU14(linhaSplit[63]);
						sos.setU18(linhaSplit[64]);
						sos.setU21(linhaSplit[65]);
						sos.setU38(linhaSplit[66]);
						sos.setU50(linhaSplit[67]);
						sos.setPqi(linhaSplit[68]);
						sos.setNas(linhaSplit[69]);
						sos.setPcRating(linhaSplit[70]);
						sos.setPfc(linhaSplit[71]);
						sos.setPgc(linhaSplit[72]);
						sos.setTan(linhaSplit[73]);
						sos.setTbn(linhaSplit[74]);
						sos.setV100(linhaSplit[75]);
						sos.setV40(linhaSplit[76]);
						sos.setVi(linhaSplit[77]);
						sos.setInterpDateTime(dateFormat2.parse(linhaSplit[78]));
						sos.setInterpretationText(linhaSplit[79]);
						sos.setTipoAnalise("AO");
						sos.setDataImportacao(new Date());
						manager.merge(sos);
						manager.getTransaction().commit();
						listaSerie.add(sos.getSerialNumber());
						
					}

					pulaLinha++;
				}
			}else{
				while((linha = bfReader.readLine()) != null ){
					if(pulaLinha > 2 ){
						manager.getTransaction().begin();
						EmsSos sos = new EmsSos();
						String[] linhaSplit = linha.split("[|]"); 
						query = manager.createNativeQuery("select ID_EMS_SEGMENTO from EMS_SOS where ID_EMS_SEGMENTO is not null	and TEXT_ID =:TEXT_ID");
						query.setParameter("TEXT_ID", linhaSplit[0]);
						if(query.getResultList().size() > 0){
							BigDecimal idEmsSegmento = (BigDecimal)query.getSingleResult();
							sos.setIdEmsSegmento(manager.find(EmsSegmento.class, idEmsSegmento.longValue()));
						}
						sos.setTextId(linhaSplit[0]);
						sos.setProcessDate(dateFormat.parse(linhaSplit[1]));
						sos.setDealerCode(linhaSplit[2]);
						sos.setCustomer(linhaSplit[3]);
						sos.setSerialNumber("0"+linhaSplit[4]);
						sos.setEquipNumber(linhaSplit[5]);
						sos.setCompCode(linhaSplit[6]);
						sos.setCompDesc(linhaSplit[7]);
						sos.setModel(linhaSplit[8]);
						sos.setManufacturer(linhaSplit[9]);
						sos.setJobsite(linhaSplit[10]);
						sos.setSampledDate(dateFormat.parse(linhaSplit[11]));
						sos.setShipTime(linhaSplit[12]);
						sos.setFluidBrand(linhaSplit[13]);
						sos.setCoolType(linhaSplit[14]);
						sos.setFluidAdd(linhaSplit[15]);
						sos.setFluidAddUnits(linhaSplit[16]);
						sos.setMeter(linhaSplit[17]);
						sos.setMeterUnits(linhaSplit[18]);
						sos.setMeterOnFluid(linhaSplit[19]);
						sos.setFluidChanged(linhaSplit[20]);
						sos.setLabelNo(linhaSplit[21]);
						sos.setShopJobNo(linhaSplit[22]);
						sos.setOverallInterp(linhaSplit[23]);
						sos.setSac(linhaSplit[24]);
						sos.setTt(linhaSplit[25]);
						sos.setCl(linhaSplit[26]);
						sos.setSo4(linhaSplit[27]);
						sos.setNo2(linhaSplit[28]);
						sos.setNo3(linhaSplit[29]);
						sos.setGlo(linhaSplit[30]);
						sos.setCon(linhaSplit[31]);
						sos.setOdor(linhaSplit[32]);
						sos.setColor(linhaSplit[33]);
						sos.setAppearence(linhaSplit[34]);
						sos.setOilAppear(linhaSplit[35]);
						sos.setPptAmount(linhaSplit[36]);
						sos.setPptColor(linhaSplit[37]);
						sos.setPptAppear(linhaSplit[38]);
						sos.setPptProp(linhaSplit[39]);
						sos.setFoam(linhaSplit[40]);
						sos.setMolybdate(linhaSplit[41]);
						sos.setPh(linhaSplit[42]);
						sos.setAl(linhaSplit[43]);
						sos.setCu(linhaSplit[44]);
						sos.setFe(linhaSplit[45]);
						sos.setPb(linhaSplit[46]);
						sos.setMo(linhaSplit[47]);
						sos.setK(linhaSplit[48]);
						sos.setNa(linhaSplit[49]);
						sos.setSn(linhaSplit[50]);
						sos.setZn(linhaSplit[51]);
						sos.setTh(linhaSplit[52]);
						sos.setMoo4(linhaSplit[53]);
						sos.setSio3(linhaSplit[54]);
						sos.setBo3(linhaSplit[55]);
						sos.setPo4(linhaSplit[56]);
						sos.setGl(linhaSplit[57]);
						sos.setFp(linhaSplit[58]);
						sos.setBp(linhaSplit[59]);
						sos.setTurbidity(linhaSplit[60]);
						sos.setInterpDateTime(dateFormat2.parse(linhaSplit[61]));
						sos.setInterpretationText(linhaSplit[62]);

						sos.setTipoAnalise("LR");
						manager.merge(sos);
						manager.getTransaction().commit();
						listaSerie.add(sos.getSerialNumber());
						
					}

					pulaLinha++;
				}
				
			}

			bfReader.close();
			for (String serie : listaSerie) {
				business.saveDiagnosticJob(-1L, serie);
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
	public List<SosBean> findAllSos(String serie, String nivel)  {
		List<SosBean> sosList = new ArrayList<SosBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select COMP_DESC, JOBSITE, convert(varchar(10),SAMPLED_DATE,103) INTERP_DATE_TIME, convert(varchar(4000),INTERPRETATION_TEXT) INTERPRETATION_TEXT, TEXT_ID, convert(varchar(10),PROCESS_DATE,112) PROCESS_DATE, comp_code, manufacturer, customer, model, equip_number, serial_number, tipo_analise, TEXT_ID, OVERALL_INTERP, METER  from EMS_SOS where SERIAL_NUMBER = '"+serie+"' and OVERALL_INTERP = '"+nivel+"' and ID_EMS_SEGMENTO IS NULL AND IS_REJEITADO IS NULL");
			List<Object[]> result = (List<Object[]>)query.getResultList();
			for (Object[] linhaSplit : result) {
				SosBean sos = new SosBean();
				sos.setCompDesc((String)linhaSplit[0]);
				sos.setJobsite((String)linhaSplit[1]);
				sos.setInterpDateTime((String)linhaSplit[2]);
				sos.setInterpretationText((String)linhaSplit[3]);
				sos.setTextId((String)linhaSplit[4]);
				sos.setProcessDate((String)linhaSplit[5]);
				sos.setCompCode((String)linhaSplit[6]);
				sos.setManufacturer((String)linhaSplit[7]);
				sos.setCustomer((String)linhaSplit[8]);
				sos.setModel((String)linhaSplit[9]);
				sos.setEquipNumber((String)linhaSplit[10]);
				sos.setSerialNumber((String)linhaSplit[11]);
				sos.setTipoAnalise((String)linhaSplit[12]);
				sos.setTextId((String)linhaSplit[13]);
				sos.setOverallInterp((String)linhaSplit[14]);
				sos.setMeter((String)linhaSplit[15]);
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
	public List<SosBean> findAllSosAssociado(Long idSegmento)  {
		List<SosBean> sosList = new ArrayList<SosBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery("select " +
					" COMP_DESC, JOBSITE, " +
					" convert(varchar(10),SAMPLED_DATE,103) INTERP_DATE_TIME, " +
					" convert(varchar(4000),INTERPRETATION_TEXT) INTERPRETATION_TEXT, " +
					" TEXT_ID,TIPO_ANALISE," +
					" CONVERT(varchar(10),PROCESS_DATE, 112) dataProcesso, " +
					" SERIAL_NUMBER, " +
					" COMP_CODE, " +
					" MANUFACTURER, " +
					" MODEL, " +
					" CUSTOMER, " +
					" EQUIP_NUMBER, " +
					" tipo_analise   " +
					" from EMS_SOS where ID_EMS_SEGMENTO = "+idSegmento);

			List<Object[]> result = (List<Object[]>)query.getResultList();
			for (Object[] linhaSplit : result) {
				SosBean sos = new SosBean();
				sos.setCompDesc((String)linhaSplit[0]);
				sos.setJobsite((String)linhaSplit[1]);
				sos.setInterpDateTime((String)linhaSplit[2]);
				sos.setInterpretationText((String)linhaSplit[3]);
				sos.setTextId((String)linhaSplit[4]);
				sos.setTipoAnalise((String)linhaSplit[5]);
				sos.setProcessDate((String)linhaSplit[6]);
				sos.setSerialNumber((String)linhaSplit[7]);
				sos.setCompCode((String)linhaSplit[8]);
				sos.setManufacturer((String)linhaSplit[9]);
				sos.setModel((String)linhaSplit[10]);
				sos.setCustomer((String)linhaSplit[11]);
				sos.setEquipNumber((String)linhaSplit[12]);
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

	public List<ArqSosBean> findAllArquivoSos (){
		EntityManager manager = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<ArqSosBean> sosList = new ArrayList<ArqSosBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From EmsArquivoSos sos, TwFuncionario f where f.epidno = sos.idFuncionario order by sos.data desc");
			List<Object[]> result = (List<Object[]>)query.getResultList();
			for (Object[] emsArquivoSos : result) {
				EmsArquivoSos arquivoSos = (EmsArquivoSos)emsArquivoSos[0];
				TwFuncionario funcionario = (TwFuncionario)emsArquivoSos[1];
				//instanciar o bean do java  que você criou
				ArqSosBean arqSos = new ArqSosBean();
				//arqSos.setData(dateFormat.format(arquivoSos.getData()));
				arqSos.setData(dateFormat.format(arquivoSos.getData()));
				//setar os dados no bean
				arqSos.setIdFuncionario(funcionario.getEplsnm());
				arqSos.setNomeArquivo(arquivoSos.getNomeArquivo());
				if(arquivoSos.getTipoAnalise().equals("AO")){
					arqSos.setTipoAnalise("Análise de Óleo");
				}else{
					arqSos.setTipoAnalise("Líquido de Arrefecimento");
				}
				
				//colocar o bean na coleção sosList
				sosList.add(arqSos);
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

