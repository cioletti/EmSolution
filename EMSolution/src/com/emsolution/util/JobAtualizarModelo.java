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


public class JobAtualizarModelo implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		ResultSet rs = null;
		try {
			
			manager = JpaUtil.getInstance();
			con = ConectionDbs.getConnecton();
			prstmt = con.createStatement();

			String SQL = "select distinct SERIAL_NUMBER from EMS_DIAGNOSTIC";
			Query query = manager.createNativeQuery(SQL);
			List<String> list = query.getResultList();
            for (String numSerie : list) {
            	SQL="select trim(f.DSPMDL) DSPMDL from "+IConstantAccess.LIB_DBS+".empeqpd0 f where trim(f.EQMFS2) = '"+numSerie+"'";
            	rs = prstmt.executeQuery(SQL);
				if(rs.next()){
					manager.getTransaction().begin();
					query = manager.createNativeQuery("update EMS_DIAGNOSTIC set MODEL = '"+rs.getString("DSPMDL")+"' where SERIAL_NUMBER = '"+numSerie+"'");
					query.executeUpdate();
					query = manager.createNativeQuery("update EMS_FUEL set MODEL = '"+rs.getString("DSPMDL")+"' where SERIAL_NUMBER = '"+numSerie+"'");
					query.executeUpdate();
					query = manager.createNativeQuery("update EMS_ALERT_MAQUINA set MODEL = '"+rs.getString("DSPMDL")+"' where SERIAL_NUMBER = '"+numSerie+"'");
					query.executeUpdate();
					manager.getTransaction().commit();
				}
			}
            
            query = manager.createNativeQuery("select modelo_diagnostico, modelo_sistemas from ems_transformar_modelo");
            List<Object[]> modelostransformarList = query.getResultList();
            for (Object[] objects : modelostransformarList) {
            	manager.getTransaction().begin();
            	query = manager.createNativeQuery("update EMS_DIAGNOSTIC set model = '"+(String)objects[1]+"' where MODEL = '"+(String)objects[0]+"'");
            	query.executeUpdate();
            	query = manager.createNativeQuery("update EMS_FUEL set model = '"+(String)objects[1]+"' where MODEL = '"+(String)objects[0]+"'");
            	query.executeUpdate();
            	query = manager.createNativeQuery("update EMS_ALERT_MAQUINA set model = '"+(String)objects[1]+"' where MODEL = '"+(String)objects[0]+"'");
            	query.executeUpdate();
            	manager.getTransaction().commit();
			}
            query = manager.createNativeQuery("select prefixo, modelo_sistemas from EMS_TRANSFORMAR_MODELO_prefixo");
          modelostransformarList = query.getResultList();
          for (Object[] objects : modelostransformarList) {
            	manager.getTransaction().begin();
            	query = manager.createNativeQuery("update EMS_DIAGNOSTIC set model = '"+(String)objects[1]+"' where substring(SERIAL_NUMBER,0,5) = '"+(String)objects[0]+"'");
            	query.executeUpdate();
            	query = manager.createNativeQuery("update EMS_FUEL set model = '"+(String)objects[1]+"' where substring(SERIAL_NUMBER,0,5) = '"+(String)objects[0]+"'");
            	query.executeUpdate();
            	query = manager.createNativeQuery("update EMS_ALERT_MAQUINA set model = '"+(String)objects[1]+"' where substring(SERIAL_NUMBER,0,5) = '"+(String)objects[0]+"'");
            	query.executeUpdate();
            	manager.getTransaction().commit();
            }


	} catch (Exception e) {
		if(manager != null && manager.getTransaction().isActive()){
			manager.getTransaction().rollback();
		}
		new EmailHelper().sendSimpleMail("Erro rotina normalizar modelo", "Erro rotina normalizar modelo", "rodrigo@rdrsistemas.com.br");
		e.printStackTrace();

	}finally{
		try {
			con.close();
			prstmt.close();
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
public static void main(String[] args) {
	System.out.println("0CJN02888".substring(0,4));
}
}
