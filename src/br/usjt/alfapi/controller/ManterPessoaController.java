package br.usjt.alfapi.controller;

import java.io.IOException;
import java.util.List;

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

	// N�O ALTERAR
	@RequestMapping("/")
	public String iniciar()
	{
		return "index";
	}

	// N�O ALTERAR
	@RequestMapping("/index")
	public String iniciarA()
	{
		return "index";
	}

	// N�O ALTERAR
	@RequestMapping("/novaPessoa")
	public String novaPessoa()
	{
		return "NovaPessoa";
	}

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
	{
		// insere e pega o endere�o cadastrado (ID_endereco � necess�rio)
		Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
		endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
		// Atualiza endere�o de pessoa
		pessoa.setEndereco(endereco);
		// Insere a pessoa no BD e na Azure
		pessoa = pessoaService.inserirPessoa(pessoa);

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
