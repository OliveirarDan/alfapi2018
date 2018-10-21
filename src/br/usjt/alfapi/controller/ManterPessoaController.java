package br.usjt.alfapi.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import br.usjt.alfapi.model.dao.AzureDAO;
import br.usjt.alfapi.model.entity.Endereco;
import br.usjt.alfapi.model.entity.Pessoa;
import br.usjt.alfapi.model.service.EnderecoService;
import br.usjt.alfapi.model.service.PessoaService;

//@RestController
@Controller
public class ManterPessoaController
{
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private EnderecoService enderecoService;

<<<<<<< HEAD
	// N�O ALTERAR
=======
	//N�O ALTERAR
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	@RequestMapping("/")
	public String iniciar()
	{
		return "index";
	}
<<<<<<< HEAD

	// N�O ALTERAR
=======
	//N�O ALTERAR
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	@RequestMapping("/index")
	public String iniciarA()
	{
		return "index";
	}
<<<<<<< HEAD

	// N�O ALTERAR
=======
	//N�O ALTERAR
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	@RequestMapping("/novaPessoa")
	public String novaPessoa()
	{
		return "NovaPessoa";
	}
<<<<<<< HEAD

	// N�O ALTERAR
	@Transactional
	@RequestMapping("/inserirPessoa")
	public String inserirPessoa(@Valid Pessoa pessoa, BindingResult erros, Model model)
	{
		try
		{
			if (!erros.hasErrors())
			{
				// insere e pega o endere�o cadastrado (ID_endereco � necess�rio)
				Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
				endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
				// Atualiza endere�o de pessoa
				pessoa.setEndereco(endereco);
				// Insere pessoa no banco
				pessoa = pessoaService.inserirPessoa(pessoa);
				// Inserindo imagens da pessoa na API
				pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho1.jpg");
				pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho2.jpg");
				pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho3.jpg");
				// Treinando API ap�s inser��o de imagens
				pessoaService.treinarApi();
				// Identifica pessoa a partir de uma imagem
				pessoaService.identificarPessoa("C://Pessoas/Ronaldinho/ronaldinho4.jpg");
				// Manda o objeto pessoa atualizado para o model
				model.addAttribute("pessoa", pessoa);
				return "Resultado";
			} else
			{
				return "NovaPessoa";
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			model.addAttribute("erro", e);
			return "Erro";
		}
	}

	/**
	 * InserirPessoa - Insere uma pessoa no banco de dados e tamb�m na Azure.
	 * Sequencialmente, acontece: 
	 * 	1 - � inserido o endere�o da pessoa, retornando o ID 
	 * 	2 - Inserida uma pessoa no BD e em seguida Azure, dentro do grupo de pessoas "grupopi" 
	 * 
	 * @param Pessoa, Endere�o
	 * @return Objeto de pessoa em JSON
	 * @throws IOException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa", headers = "Accept=application/json")
	public @ResponseBody Pessoa inserirPessoa(@RequestBody Pessoa pessoa, Model model) throws IOException
=======
	
	//N�O ALTERAR
		@Transactional
		@RequestMapping("/inserirPessoa")
		public String criarFilme(@Valid Pessoa pessoa, BindingResult erros, Model model) {
			try {
				if (!erros.hasErrors()) {
					//insere e pega o endere�o cadastrado (ID_endereco � necess�rio)
					Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
					endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
					//Atualiza endere�o de pessoa
					pessoa.setEndereco(endereco);
					//Insere pessoa no banco
					pessoa = pessoaService.inserirPessoa(pessoa);
					//Inserindo imagens da pessoa na API
					pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho1.jpg");
					pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho2.jpg");
					pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho3.jpg");
					//Treinando API ap�s inser��o de imagens
					pessoaService.treinarApi();
					//Identifica pessoa a partir de uma imagem
					pessoaService.identificarPessoa("C://Pessoas/Ronaldinho/ronaldinho4.jpg");
					//Manda o objeto pessoa atualizado para o model
					model.addAttribute("pessoa", pessoa);
					return "Resultado";
				} else {
					return "NovaPessoa";
				}
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("erro", e);
				return "Erro";
			}
		}
	
	
		
		
	/*
	 * M�todo inserirPessoa2 que deve receber um Json com o objeto pessoa para cadastro
	 * Headers: utilizado para que o m�todo possa reconhecer que ir� receber um Json. 
	 * Observe que no GET n�o foi necess�rio (talvez em outros casos seja.).
	 * ResponseBody: qual ser� o retorno do m�todo.
	 * RequestBody: o que p m�todo ir� receber no corpo do Json. 
	 * **Observe que precisa ser definido o que ir� no corpo do response e do request.
	 * 
	 * PROBLEMA: est� informando um erro do tipo Hibernate no entity Endere�o.
	 * Acho que � algo sobre o relacionamento entre Endereco e Pessoa. 
	 * Precisa validar se a classe entity.Endereco e Pessoa est�o anotadas corretamente.
	*/
	
	@Transactional
	//@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa", headers = "Accept=application/json")
	//public @ResponseBody Pessoa inserirPessoa(@RequestBody Pessoa pessoa, Model model) throws IOException
	public Pessoa inserirPessoa2(Pessoa pessoa) throws IOException
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	{
		// insere e pega o endere�o cadastrado (ID_endereco � necess�rio)
		Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
		endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
		// Atualiza endere�o de pessoa
		pessoa.setEndereco(endereco);
		// Insere a pessoa no BD e na Azure
		pessoa = pessoaService.inserirPessoa(pessoa);

<<<<<<< HEAD
		// Adiciona Pessoa como um atributo do model
		model.addAttribute("pessoa", pessoa);

		return pessoa;
	}

	/**
	 * BuscarPessoaPorId - Busca uma pessoa no banco pelo ID
	 * 
	 * @param ID de pessoa
	 * @return Objeto de pessoa em JSON
	 * @throws IOException
	 */
=======
		
		//model.addAttribute("pessoa", pessoa);

		return pessoa;
	}
	
	
	/*
	 * M�todo para buscar pessoa por Id.
	 * RequestMapping � necess�rio para mapear o tipo de requisicao
	 * ResponseBody: determina qual ser� o corpo da resposta, ou seja, o que ir�
	 * no retorno do m�todo como Json
	 * PathVariable: usado para identificar o id como atributo e n�o um recurso do m�todo	 * 
	 * Return: um Json com o objeto pessoa que foi identificado pelo id.
	*/
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	@RequestMapping(method = RequestMethod.GET, value = "rest/pessoa/{id}")
	public @ResponseBody Pessoa buscaPessoaPorId(@PathVariable("id") int id, Model model) throws IOException
	{
		try
		{
			Pessoa pessoa = pessoaService.buscarPessoa(id);
			model.addAttribute("pessoa", pessoa);
			return pessoa;

		} catch (IOException e)
		{
			throw e;
		}
	}
	
	/**
	 * ListarPessoas - Lista todas as pessoas cadastradas
	 * 
	 * @param none
	 * @return Lista de Pessoas
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "rest/pessoa")
	public @ResponseBody List<Pessoa> listarPessoas(Model model) throws IOException
	{
		try
		{
			List<Pessoa> pessoa = pessoaService.listarPessoas();
			model.addAttribute("pessoa", pessoa);
			return pessoa;

		} catch (IOException e)
		{
			throw e;
		}
	}
	
	/**
	 * AtualizarPessoa - Altera os dados de uma pessoa
	 * 
	 * @param Objeto Pessoa
	 * @return Objeto de pessoa em JSON
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "rest/pessoa", headers = "Accept=application/json")
	public @ResponseBody Pessoa atualizarPessoa(@RequestBody Pessoa pessoa, Model model) throws IOException
	{
		try
		{
			Pessoa pessoa1 = pessoaService.atualizarPessoa(pessoa);
			model.addAttribute("pessoa", pessoa1);
			return pessoa1;

		} catch (IOException e)
		{
			throw e;
		}
	}
	
	/**
	 * DeletarPessoa - Altera os dados de uma pessoa
	 * 
	 * @param Id
	 * @return Mensagem de sucesso
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "rest/pessoa/{id}")
	public @ResponseBody String removerPessoa(@PathVariable("id") int id, Model model) throws IOException
	{
		try
		{
			pessoaService.excluirPessoa(id);

		} catch (IOException e)
		{
			throw e;
		}
		return "Pessoa removida com sucesso";
	}
	
	

	/**
	 * IdentificaPessoa - Identifica uma pessoa na Azure por uma foto (URL)
	 * Detalhe: sequencialmente, acontece:
	 * 	1 - O grupo de pessoas "grupopi" � treinado.
	 * 	2 - � acionado o m�todo detectaPessoaURL passando a foto como par�metro e retornando o FaceID
	 * 	3 - � acionado o m�todo identificaPessoa que retorna as pessoas prov�veis que tenham este rosto
	 * 
	 * @param Foto
	 * @return Lista de pessoas prov�veis que tenham este rosto
	 * @throws IOException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa/identifica", headers = "Accept=application/json")
	public @ResponseBody String identificaPessoa(@RequestBody String foto, Model model) throws IOException
	{
		try
		{
			// Instancia o AzureDAO e aciona m�todo que treina o grupo
			AzureDAO azureDao = new AzureDAO();
			azureDao.treinar();

			// Aqui � acionado o IdentificaPessoa que precisa de um FaceID, obtido atrav�s do DetectaPessoa
			// IMPORTANTE: ajustar para receber a foto ou array de fotos, o que for necessario
			String testeFoto = foto;
			String response = azureDao.identificaPessoa(azureDao.detectaPessoaUrl(testeFoto));

			return response;

		} catch (Exception e)
		{
			throw e;
		}

	}

}
