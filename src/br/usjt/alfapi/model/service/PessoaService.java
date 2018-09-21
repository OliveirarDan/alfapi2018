package br.usjt.alfapi.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.alfapi.model.dao.PessoaDAO;
import br.usjt.alfapi.model.entity.Pessoa;


@Service
public class PessoaService {
	PessoaDAO dao = new PessoaDAO();
	
	
	@Autowired
	public PessoaService(PessoaDAO pdao) {
		dao = pdao;
	}
	
	@Transactional
	public Pessoa inserirPessoa (Pessoa pessoa) throws IOException {
		int id = dao.inserirPessoa(pessoa);
		pessoa.setIdPessoa(id);
		return pessoa;
	}
	
	@Transactional
	public void atualizarPessoa (Pessoa pessoa) throws IOException {
		dao.atualizaPessoa(pessoa);
	}
	
	@Transactional
	public void excluirPessoa(Pessoa pessoa) throws IOException {
		dao.removerPessoa(pessoa);
	}
	
	public Pessoa buscarPessoa(int id) throws IOException{
		return dao.buscaPessoas(id);
	}
	
	public List<Pessoa> listarPessoas (String chave) throws IOException{
		return dao.listarPessoas(chave);
	}
	
	public List<Pessoa> listarFilmes() throws IOException{
		return dao.listarPessoas();
	}


	
	
	
}
