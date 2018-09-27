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
		String stringConexao = "jdbc:mysql://localhost/alfapidb?";
		String usuario = "root";
		String senha = "alunos";
		
		
		
		return DriverManager.getConnection(stringConexao, usuario, senha);
		
	}

}
