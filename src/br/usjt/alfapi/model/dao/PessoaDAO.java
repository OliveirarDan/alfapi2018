package br.usjt.alfapi.model.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.alfapi.model.entity.Pessoa;

@Repository
public class PessoaDAO {
	@PersistenceContext
	EntityManager manager;
	
	public int inserirPessoa(Pessoa pessoa) throws IOException{
		manager.persist(pessoa);
		return pessoa.getIdPessoa();
	}
	
	public Pessoa buscaPessoas(int id) throws IOException{
		return manager.find(Pessoa.class, id);
	}
	
	public void atualizaPessoa (Pessoa pessoa)throws IOException{
		System.out.println(pessoa);
		manager.merge(pessoa);
	}
	
	public void removerPessoa(Pessoa pessoa)throws IOException{
		manager.remove(manager.find(Pessoa.class, pessoa.getIdPessoa()));
	}
	
	
	
	public List<Pessoa> listarPessoas (String chave) throws IOException{
		
		String jpql = "select f from Pessoa f where f.nome like :chave";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("chave", "%"+chave+"%");
		
		List<Pessoa> result = query.getResultList();
		
		return result;		
	}

	public List<Pessoa> listarPessoas() throws IOException {
		return manager.createQuery("select f from Pessoa f").getResultList();
	}
	
	
	
}
