package br.usjt.alfapi.model.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.alfapi.model.dao.EnderecoDAO;
import br.usjt.alfapi.model.entity.Endereco;

@Service
public class EnderecoService
{
	private EnderecoDAO dao;

	@Autowired
	public EnderecoService(EnderecoDAO dao)
	{
		this.dao = dao;
	}

	@Transactional
	public Endereco inserirEndereco(Endereco endereco) throws IOException
	{
		int id = dao.inserirEndereco(endereco);
		endereco.setIdEndereco(id);
		return endereco;
	}

}
