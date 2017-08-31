package com.emsolution.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.emsolution.entity.EmsConsultor;


public class JobAtualizarSerieMaquina implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		ResultSet rs = null;
		try {
			
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			//verifica se o DBS está ativo
			con = ConectionDbs.getConnecton();
			prstmt = con.createStatement();

			String SQL = "delete from EMS_CONSULTOR";
			Query query = manager.createNativeQuery(SQL);
			query.executeUpdate();
			manager.getTransaction().commit();



			SQL="select trim(s.cuno), trim(s.SLMN02), trim(f.SLMNM) from LIBU17.SCPDIVF0 s, LIBU17.SCPSMFM0 f"+ 
					" where s.divi = 'G' "+ 
					" and s.SLMT02 = '2' "+ 
					" and f.DIVI = 'G'"+ 
					" and f.SLMT = '2'  "+ 
					" and f.SLMN = s.SLMN02";

			rs = prstmt.executeQuery(SQL);
			while(rs.next()){
				try {
					manager.getTransaction().begin();
					EmsConsultor consultor = new EmsConsultor();
					consultor.setCodCliente(rs.getString(1));
					consultor.setCodConsultorDbs(rs.getString(2));
					consultor.setNomeConsultor(rs.getString(3));	
					manager.persist(consultor);
					manager.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

	} catch (Exception e) {
		if(manager != null && manager.getTransaction().isActive()){
			manager.getTransaction().rollback();
		}
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

}
