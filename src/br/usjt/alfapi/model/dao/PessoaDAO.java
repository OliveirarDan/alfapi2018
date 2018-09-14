package br.usjt.alfapi.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.usjt.alfapi.model.entity.Pessoa;

public class PessoaDAO {

	// M�todo inserir e retorna id do usuario inserido
	public int inserir(Pessoa pessoa) throws SQLException {
		// Especificando o comando sql de inser��o
		String sqlInsert = "INSERT INTO pessoa (nome, sobrenome, cpf, registrosec, email, dataNascimento, genero, telResidencial, telSecundario, codAzure)"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			// compila o comando sql
			stm.setString(1, pessoa.getNome());
			stm.setString(2, pessoa.getSobrenome());
			stm.setString(3, pessoa.getCpf());
			stm.setString(4, pessoa.getRegistrosec());
			stm.setString(5, pessoa.getEmail());
			stm.setString(6, pessoa.getDataNascimento());
			stm.setString(7, pessoa.getGenero());
			stm.setString(8, pessoa.getTelResidencial());
			stm.setString(9, pessoa.getTelSecundario());
			stm.setString(10, pessoa.getCodAzure());

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

	// M�todo atualizar
	public void atualizar(Pessoa pessoa) throws SQLException {
		String sqlUpdate = "UPDATE pessoa SET nome=?, sobrenome=?, cpf=?, registrosec=?, email=?, dataNascimento=?, genero=?, telResidencial=?, "
				+ "telSecundario=?, codAzure=? WHERE idPessoa=?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			// compila o comando sql
			stm.setString(1, pessoa.getNome());
			stm.setString(2, pessoa.getSobrenome());
			stm.setString(3, pessoa.getCpf());
			stm.setString(4, pessoa.getRegistrosec());
			stm.setString(5, pessoa.getEmail());
			stm.setString(6, pessoa.getDataNascimento());
			stm.setString(7, pessoa.getGenero());
			stm.setString(8, pessoa.getTelResidencial());
			stm.setString(9, pessoa.getTelSecundario());
			stm.setString(10, pessoa.getCodAzure());
			// stm.setInt(7, pessoa.getIdPessoa());

			// executa o comando sql
			stm.execute();

		}
	}

	// M�todo deletar
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
