package com.emsolution.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.business.ImportXmlBusiness;
import com.emsolution.entity.EmsPecas;
import com.emsolution.entity.EmsSegmento;

public class JobFindOrcamento implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		Statement prstmt2 = null;
		ResultSet rs = null;
		ResultSet rsPecas = null;
		Statement prstmtPecas = null;
		try {
			manager = JpaUtil.getInstance();
			//verifica se o DBS está ativo
			con = ConectionDbs.getConnecton();
			prstmt = con.createStatement();
			prstmt2 = con.createStatement();
			prstmtPecas = con.createStatement();
			String SQL = "select PEDSM, CODERR, PLDBS, DESCERR from "+IConstantAccess.AMBIENTE_DBS+".USPPHSM0 where CODERR is not null and SUBSTRING (PEDSM ,0,2) = 'M'";
			rs = prstmt.executeQuery(SQL);
			ImportXmlBusiness business = new ImportXmlBusiness();
			while(rs.next()){
				manager.getTransaction().begin();
				try {
					String CODERR = rs.getString("CODERR");
					String PEDSM = rs.getString("PEDSM").split("-")[1];
					EmsSegmento segmento = manager.find(EmsSegmento.class, Long.parseLong(PEDSM.trim()));
					if(Integer.parseInt(CODERR.trim()) == 0 && segmento != null){
						segmento.setValorPecas(BigDecimal.valueOf(new Double(rs.getString("DESCERR").substring(31).trim())));
						segmento.setNumDoc(rs.getString("PLDBS").trim());
						segmento.setCodErroDocDbs(rs.getString("CODERR").trim());
						segmento.setMsgDocDbs(null);
						business.sendContratoDbs(segmento, "N", null);
					}else if(segmento != null){
						segmento.setMsgDocDbs(rs.getString("DESCERR").trim());
						segmento.setCodErroDocDbs(rs.getString("CODERR").trim());
						Query query = manager.createQuery("delete from EmsPecas where idEmsSegmento.id =:idEmsSegmento");
						query.setParameter("idEmsSegmento", segmento.getId());
						query.executeUpdate();
						SQL = "select sos, pano20, qtde, CODERR, DESCERR from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where pedsm = 'M-"+PEDSM+"'";
    					rsPecas = prstmtPecas.executeQuery(SQL);
    					while(rsPecas.next()){
    						String sos = rsPecas.getString("sos");
    						String pano20 = rsPecas.getString("pano20");
    						Integer qtde = rsPecas.getInt("qtde");
    						String CODERRPECAS = rsPecas.getString("CODERR");
    						String DESCERRPECAS = rsPecas.getString("DESCERR");
    						EmsPecas gePecas = new EmsPecas();
    						gePecas.setSos1(sos);
    						gePecas.setIdEmsSegmento(segmento);
    						gePecas.setQtd(qtde.longValue());
    						gePecas.setPartNo(pano20);
    						gePecas.setCoderr(CODERRPECAS);
    						gePecas.setDescerr(DESCERRPECAS);
    						manager.persist(gePecas);
    					}
					}
					if(segmento != null){
						manager.merge(segmento);
					}
					prstmt2.execute("delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'M-"+PEDSM+"'");
					prstmt2.execute("delete from "+IConstantAccess.AMBIENTE_DBS+".USPPHSM0 where PEDSM = 'M-"+PEDSM+"'");
				} catch (Exception e) {
					e.printStackTrace();
				}
				manager.getTransaction().commit();
				
			}
		
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				con.close();
				prstmt.close();
				prstmt2.close();
				if(prstmtPecas != null){
					prstmtPecas.close();
					rsPecas.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

}
