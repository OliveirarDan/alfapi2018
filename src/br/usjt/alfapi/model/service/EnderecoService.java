package br.usjt.alfapi.model.service;

import java.sql.SQLException;

import br.usjt.alfapi.model.dao.EnderecoDAO;
import br.usjt.alfapi.model.entity.Endereco;

public class EnderecoService {
	
	EnderecoDAO dao = new EnderecoDAO();
	
	public void inserir (Endereco endereco) throws SQLException {
		dao.inserir(endereco);
	}

	public void atualizar (Endereco endereco) throws SQLException {
		dao.atualizar(endereco);
	}
	
	public void deletar (Endereco endereco) throws SQLException {
		dao.deletar(endereco);
	}

}
