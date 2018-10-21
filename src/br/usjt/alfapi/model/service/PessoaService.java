package br.usjt.alfapi.model.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.alfapi.model.dao.AzureDAO;
import br.usjt.alfapi.model.dao.PessoaDAO;
import br.usjt.alfapi.model.entity.Pessoa;

@Service
public class PessoaService
{
	PessoaDAO pessoaDao = new PessoaDAO();
	AzureDAO azureDao = new AzureDAO();

	@Autowired
	public PessoaService(PessoaDAO pdao)
	{
		pessoaDao = pdao;
	}

	/**
	 * InserirPessoa - Esse m�todo insere a pessoa no azure, recebe o codAzure e
	 * insere os dados da pessoa no banco.
	 * 
	 * @param pessoa
	 *            - recebe um objeto pessoa da view.
	 * @return pessoa - retorna um objeto pessoa atualizado com o cod azure
	 * @throws IOException
	 */
	@Transactional
	public Pessoa inserirPessoa(Pessoa pessoa) throws IOException
	{
		pessoa.setCodAzure(azureDao.inserePessoa(pessoa.getNome(), pessoa.getSobrenome()));
		System.out.println("C�digo de pessoa no Azure: " + pessoa.getCodAzure());
		int id = pessoaDao.inserirPessoa(pessoa);
		pessoa.setIdPessoa(id);
		return pessoa;
	}

	/**
	 * inserirFotoPessoa - Esse m�todo recebe 1 foto (endere�o em string) e envia
	 * para o azure
	 * 
	 * @param pessoa
	 * @param foto
	 *            - Endere�o da foto
	 * @throws IOException
	 */
	public void inserirFotoPessoa(Pessoa pessoa, String foto) throws IOException
	{
		azureDao.insereFotoPessoaLocal(pessoa.getCodAzure(), (pessoa.getNome() + " " + pessoa.getSobrenome()), foto);
	}

	/**
	 * treinarAPI - Esse m�todo faz a API da azure treinar. Reconhece as fotos j�
	 * carregadas e aumenta a precis�o da identifica��o.
	 */
	public void treinarApi()
	{
		azureDao.treinar();
	}

	/**
	 * identificarPessoa - Esse m�todo recebe o endere�o de uma foto. Chama o
	 * detecta pessoa que realiza a detec��o da foto e retorna um ID. O ID �
	 * utilizado no indentify para reconhecer as pessoas parecidas em um grupo.
	 * 
	 * @param urlFoto
	 *            - Foto selecionada para fazer a identifica��o.
	 */
	public void identificarPessoa(String urlFoto)
	{
		azureDao.identificaPessoa(azureDao.detectaPessoa(urlFoto));

	}

	@Transactional
	public Pessoa atualizarPessoa(Pessoa pessoa) throws IOException
	{
		pessoaDao.atualizaPessoa(pessoa);
		return pessoa;
	}

	@Transactional
	public void excluirPessoa(int id) throws IOException
	{
		pessoaDao.removerPessoa(id);
	}

	public Pessoa buscarPessoa(int id) throws IOException
	{
		return pessoaDao.buscaPessoas(id);
	}

	public List<Pessoa> listarPessoas(String chave) throws IOException
	{
		return pessoaDao.listarPessoas(chave);
	}

	public List<Pessoa> listarPessoas() throws IOException
	{
		return pessoaDao.listarPessoas();
	}

}
