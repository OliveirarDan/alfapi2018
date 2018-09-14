package br.usjt.alfapi.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.usjt.alfapi.model.entity.Endereco;

public class EnderecoDAO {
	
	public int inserir(Endereco endereco) throws SQLException {
		// Especificando o comando sql de inserção
		String sqlInsert = "INSERT INTO endereco (cep, tipoDeLogradouro, endereco, numero, bairro, estado, cidade, pais)"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			// compila o comando sql
			stm.setString(1, endereco.getCep());
			stm.setString(2, endereco.getTipoDeLogradouro());
			stm.setString(3, endereco.getEndereco());
			stm.setInt(4, endereco.getNumero());
			stm.setString(5, endereco.getBairro());
			stm.setString(6, endereco.getEstado());
			stm.setString(7, endereco.getCidade());
			stm.setString(8, endereco.getPais());

			// executa o comando sql
			stm.execute();

			// Recuperando id da pessoa cadastrada
			String sqlPegaId = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlPegaId); ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					// devolver o id gerado
					endereco.setIdEndereco(rs.getInt(1));
				}

			}

		}
		return endereco.getIdEndereco();
	}

	// Método atualizar
	public void atualizar(Endereco endereco) throws SQLException {
		String sqlUpdate = "UPDATE endereco SET cep=?, tipoDeLogradouro=?, endereco=?, numero=?, bairro=?, estado=?, cidade=?, pais=?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			// compila o comando sql
			stm.setString(1, endereco.getCep());
			stm.setString(2, endereco.getTipoDeLogradouro());
			stm.setString(3, endereco.getEndereco());
			stm.setInt(4, endereco.getNumero());
			stm.setString(5, endereco.getBairro());
			stm.setString(6, endereco.getEstado());
			stm.setString(7, endereco.getCidade());
			stm.setString(8, endereco.getPais());
			// stm.setInt(7, pessoa.getIdEndereco());

			// executa o comando sql
			stm.execute();

		}
	}

	// Método deletar
	public void deletar(Endereco endereco) throws SQLException {
		String sqlDelete = "DELETE FROM endereco WHERE idEndereco=?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			// compila o comando sql
			stm.setInt(1, endereco.getIdEndereco());
			// executa o comando sql
			stm.execute();

		}
	}


}
