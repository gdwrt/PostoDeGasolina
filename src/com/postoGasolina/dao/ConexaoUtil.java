package com.postoGasolina.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoUtil {
	private static ConexaoUtil conexaoUtil;
	
	public static ConexaoUtil getInstance(){
		if(conexaoUtil == null){
			conexaoUtil = new ConexaoUtil();
		}
		return conexaoUtil;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost/db_posto_gasolina";
        String usuario = "root";
        String senha = "";
        Connection con = DriverManager.getConnection(url, usuario, senha);
		
		
		return con;
	}
	/*
	public static void main(String[] args){
	try {
			System.out.println(getInstance().getConnection());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	*/


}