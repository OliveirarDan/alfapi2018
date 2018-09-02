package br.usjt.alfapi.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	static
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String stringConexao = "jdbc:mysql://localhost:3306/alfapidb";
		String usuario = "alunos";
		String senha = "alunos";
		
		
		
		return DriverManager.getConnection(stringConexao, usuario, senha);
		
	}

}
