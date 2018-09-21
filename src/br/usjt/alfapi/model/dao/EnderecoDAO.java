package br.usjt.alfapi.model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.usjt.alfapi.model.entity.Endereco;
import br.usjt.alfapi.model.entity.Pessoa;

@Repository
public class EnderecoDAO {
	@PersistenceContext
	EntityManager manager;
	
	
	public int inserirEndereco(Endereco endereco) throws IOException{
		manager.persist(endereco);
		return endereco.getIdEndereco();
	}

	public Endereco buscaEnderecos(int id) throws IOException{
		return manager.find(Endereco.class, id);
	}
	
	public void atualizaEndereco (Endereco endereco)throws IOException{
		System.out.println(endereco);
		manager.merge(endereco);
	}
	

	

}
