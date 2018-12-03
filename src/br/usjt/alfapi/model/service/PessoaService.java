package br.usjt.alfapi.model.service;

import java.io.File;
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
	 * InserirPessoa - Esse método insere a pessoa no azure, recebe o codAzure e
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
		pessoa.setFoto(azureDao.insereFotoPessoa(pessoa.getCodAzure(), pessoa.getFoto()));
		System.out.println("Código de pessoa no Azure (personID): " + pessoa.getCodAzure());
		System.out.println("Id da foto na Azure: " + pessoa.getFoto());
		int id = pessoaDao.inserirPessoa(pessoa);
		pessoa.setIdPessoa(id);
		return pessoa;
	}

	/**
	 * inserirFotoPessoa - Esse método recebe 1 foto (endereço em string) e envia
	 * para o azure
	 * 
	 * @param pessoa
	 * @param foto
	 *            - Endereço da foto
	 * @throws IOException
	 */
	public void inserirFotoPessoa(Pessoa pessoa, String foto) throws IOException
	{
		azureDao.insereFotoPessoaLocal(pessoa.getCodAzure(), (pessoa.getNome() + " " + pessoa.getSobrenome()), foto);
	}

	
	/**
	 * treinarAPI - Esse método faz a API da azure treinar. Reconhece as fotos já
	 * carregadas e aumenta a precisão da identificação.
	 */
	public void treinarApi()
	{
		azureDao.treinar();
	}

	/**
	 * identificarPessoa - Esse método recebe o endereço de uma foto. Chama o
	 * detecta pessoa que realiza a detecção da foto e retorna um ID. O ID é
	 * utilizado no indentify para reconhecer as pessoas parecidas em um grupo.
	 * 
	 * @param urlFoto
	 *            - Foto selecionada para fazer a identificação.
	 * @throws IOException 
	 */
	public void identificarPessoa(String foto) throws IOException
	{
		azureDao.identificaPessoa(azureDao.detectaPessoa(foto));
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
	
	public Pessoa buscarPessoaPeloPersonId(String personId)
	{
		return pessoaDao.buscarPessoaPeloPersonId2(personId);
	}

	public List<Pessoa> listarPessoas(String chave) throws IOException
	{
		return pessoaDao.listarPessoas(chave);
	}

	public List<Pessoa> listarPessoas() throws IOException
	{
		return pessoaDao.listarPessoas();
	}
	
	
	/*===========================================================================================	 
	 * ==========================================================================================
	 * ===================												=========================
	 * ===================		MÉTODOS TEMPORARIOS, NAO CONSIDERAR 	=========================
	 * ===================												=========================
	 * ==========================================================================================
	 * ==========================================================================================*/
	
	
	
	/**
	 * identificarPessoaFile - Esse método recebe o endereço de uma foto.
	 * Chama o detecta pessoa que realiza a detecção da foto e retorna um ID.
	 * O ID é utilizado no indentify para reconhecer as pessoas parecidas em um grupo.
	 * @param file - Foto selecionada para fazer a identificação.
	 */
	public void identificarPessoa(File file) {
		System.out.println(azureDao.identificaPessoaTeste(azureDao.detectaPessoaFile(file)));
		

	}
	
	
	/**
	 * inserirFotoPessoaFile - Esse método recebe 1 foto (endereço em string) e envia para o azure
	 * @param pessoa 
	 * @param foto - Arquivo da foto
	 * @throws IOException
	 */
	public void inserirFotoPessoaFile (Pessoa pessoa, File foto) throws IOException{
		azureDao.insereFotoPessoaFile(pessoa.getCodAzure(), (pessoa.getNome()+ " " + pessoa.getSobrenome()), foto);
	}
	

}
