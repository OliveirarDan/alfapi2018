package br.usjt.alfapi.model.service;

import java.sql.SQLException;

import br.usjt.alfapi.model.dao.PessoaDAO;
import br.usjt.alfapi.model.entity.Pessoa;

public class PessoaService {

	PessoaDAO dao = new PessoaDAO();
	
	public void inserir (Pessoa pessoa) throws SQLException {
		dao.inserir(pessoa);
	}
	
	
	
	public void atualizar (Pessoa pessoa) throws SQLException {
		dao.atualizar(pessoa);
	}
	
	public void deletar (Pessoa pessoa) throws SQLException {
		dao.deletar(pessoa);
	}
	
	
}
