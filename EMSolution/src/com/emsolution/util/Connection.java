package com.emsolution.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class Connection {

	private static java.sql.Connection con = null;
	//private final static String user = "pesa";
	//private final static String password = "pesa";

	public static java.sql.Connection getConnection() {
		try {
			 Context ctx = new InitialContext();
		        //Recupera o DataSource
		        DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/pesa");
		 
		        //Cria a conexão através do DataSource
		         con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro em Connection.getConnection(): " + e.getMessage());
		}

		return con;
	}
	public static void main(String[] args) {
		Statement smtloop = null;
		ResultSet rsloop = null;
		//ResultSet rs = null;
		Statement smt = null;
		
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			java.sql.Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.2.247:1433/DSEControl",	"control_dse", "w8o#ay3*");
			
			smtloop = con.createStatement();
			smt = con.createStatement();
			rsloop = smtloop.executeQuery("select pl.FILIAL,  c.NUM_SERIE from crm_PROPOSTA c, PMP_CLIENTE_PL pl where NUM_SERIE = pl.SERIE and pl.FILIAL <> c.FILIAL");
			while(rsloop.next()){
				smt.executeUpdate("update crm_PROPOSTA set FILIAL = "+rsloop.getInt("FILIAL")+" where NUM_SERIE = '"+rsloop.getString("NUM_SERIE")+"'");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
