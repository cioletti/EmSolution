package com.emsolution.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.entity.EmsProposta;

public class JobSubTributariaDbs implements Job {
	
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		
        Connection con = null;
        Statement prstmt = null;

        EntityManager manager = null;
        ResultSet rs = null;

        try {
        	manager = JpaUtil.getInstance();
        	con = ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	
        	String SQL = "select TRIM(WONO) WONO from "+IConstantAccess.PESARDRTRIBUTACAO+".RDRWONO";
        	rs = prstmt.executeQuery(SQL);
        	String pair = "";
        	while (rs.next()) {
        		pair += "'"+rs.getString("WONO")+"',";
        	}
        	pair = (pair.length() > 0)?pair.substring(0, pair.length()-1):"''";

        	manager.getTransaction().begin();
        	Query query = manager.createQuery("select distinct p from EmsProposta p, EmsSegmento seg where p.isFindSubTributaria = 'P' and p.id = seg.idProposta.id and seg.numDoc not in ("+pair+")");
        	List<EmsProposta> numDocList = (List<EmsProposta>)query.getResultList();

        	for (EmsProposta obj : numDocList) {
        		obj.setIsFindSubTributaria("S");
        	}
        	manager.getTransaction().commit();
        } catch (Exception e) {
        	if(manager != null && manager.getTransaction().isActive()){
        		manager.getTransaction().rollback();
        	}
        	e.printStackTrace();

        }finally{
        	try {
        		if(manager != null && manager.isOpen()){
        			manager.close();
        		}
				rs.close();
				prstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		
	}
//		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(null);
//		business.saveErro();
		
	}


