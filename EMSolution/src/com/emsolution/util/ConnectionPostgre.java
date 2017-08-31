/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emsolution.util;

import java.sql.DriverManager;

/**
 *
 * @author Rodrigo
 */
public class ConnectionPostgre {
    public static java.sql.Connection getConnecton(){
		
		java.sql.Connection con = null;
		int i = 0;
		while(i < 5){
			try {
				String url = "jdbc:postgresql://amazonpesa.wikiconsultoria.com.br:10543/zohoPesa";
				String user = "belete";
				String password = "beleti@pesacrm";
				Class.forName("org.postgresql.Driver").newInstance();

				//			String url = "jdbc:as400://192.168.128.146";
				//			String user = "XUPU15PSS";
				//			String password = "marcosa";
				//			Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
				con = DriverManager.getConnection(url, user, password); 
				return con;
			}catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		return null;
	}
//	public static void main(String[] args) {
//		java.sql.Connection conn = new ConnectionPostgre().getConnecton();
//		          System.err.println(conn);
//                        
//	}
}
