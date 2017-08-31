package com.emsolution.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.entity.EmsSegmento;

public class JobRemoverCotacao {
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement prstmt = null;
		Statement prstmtSubTributaria = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			con = ConectionDbs.getConnecton(); 
			prstmtSubTributaria = con.createStatement();
			con.setAutoCommit(false);
			prstmt = con.prepareStatement("select trim(d.DOCDBS) DOCDBS, trim(d.CODERR) CODERR, trim(d.DESCERR) DESCERR from "+IConstantAccess.AMBIENTE_DBS+".USPDDSM0 d where d.coderr is not null and d.coderr <> ''");
			rs = prstmt.executeQuery();
			Statement prstmtDelete = con.createStatement();
			while(rs.next()){
				String cotacao = rs.getString("DOCDBS");
				Query query = manager.createQuery("from EmsSegmento where numDoc =:numDoc");
				query.setParameter("numDoc", cotacao);
				EmsSegmento segmento = null;
				if(query.getResultList().size() > 0){
					segmento =(EmsSegmento)query.getSingleResult();
				}
				if(segmento == null){														
					prstmtDelete.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPDDSM0 where trim(DOCDBS) = '"+cotacao+"'");
					//prstmtDelete.executeUpdate("delete from LIBU15FTP.USPIFSM0 where wonosm = '"+rs.getString("wonosm")+"' and wosgno = '"+geSegmento.getNumeroSegmento()+"'");
					continue;
				}
				if(rs.getString("CODERR") != null && rs.getString("CODERR").trim().equals("99")){
					segmento.setMsgDocDbs(rs.getString("DESCERR").trim());
					segmento.setCodErroDocDbs("99");
				}else if(("").equals(rs.getString("CODERR"))){
					segmento.setCodErroDbs("99");
					segmento.setMsgDocDbs("Não foi possível remover cotação tente novamente!");
				}else{
					prstmtDelete.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPDDSM0 where trim(DOCDBS) = '"+cotacao+"'");
				}
			}
			manager.getTransaction().commit();
			con.commit();
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			if(con != null){
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if(con != null){
					prstmtSubTributaria.close();
					prstmt.close();
					rs.close();
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("WO/Seg:P003360-05 Nao Existe no DBS.".contains("Nao Existe no DBS"));
	}
}
