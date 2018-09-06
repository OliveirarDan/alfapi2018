package br.usjt.alfapi.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.usjt.alfapi.model.entity.Pessoa;

public class PessoaDAO {

	// Método inserir e retorna id do usuario inserido
	public int inserir(Pessoa pessoa) throws SQLException {
		// Especificando o comando sql de inserção
		String sqlInsert = "INSERT INTO pessoa (nome, cpf, rg, email, telCelular, codAzure) "
				+ "values (?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			// compila o comando sql
			stm.setString(1, pessoa.getNome());
			stm.setInt(2, pessoa.getCpf());
			stm.setString(3, pessoa.getRg());
			stm.setString(4, pessoa.getEmail());
			stm.setString(5, pessoa.getTelResidencial());
			stm.setString(6, pessoa.getCodAzure());

			// executa o comando sql
			stm.execute();

			// Recuperando id da pessoa cadastrada
			String sqlPegaId = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlPegaId); ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					// devolver o id gerado
					pessoa.setIdPessoa(rs.getInt(1));
				}

			}

		}
		return pessoa.getIdPessoa();
	}

	// Método atualizar
	public void atualizar(Pessoa pessoa) throws SQLException {
		String sqlUpdate = "UPDATE pessoa SET nome=?, cpf=?, rg=?, email=?, telCelular=?, codAzure=? WHERE idPessoa=?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			// compila o comando sql
			stm.setString(1, pessoa.getNome());
			stm.setInt(2, pessoa.getCpf());
			stm.setString(3, pessoa.getRg());
			stm.setString(4, pessoa.getEmail());
			stm.setString(5, pessoa.getTelResidencial());
			stm.setString(6, pessoa.getCodAzure());
			// stm.setInt(7, pessoa.getIdPessoa());

			// executa o comando sql
			stm.execute();

		}
	}

	// Método deletar
	public void deletar(Pessoa pessoa) throws SQLException {
		String sqlDelete = "DELETE FROM pessoa WHERE idPessoa=?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			// compila o comando sql
			stm.setInt(1, pessoa.getIdPessoa());
			// executa o comando sql
			stm.execute();

		}
	}

}
