package br.usjt.alfapi.controller;

import java.io.IOException;

import java.util.List;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	/**
	 * InserirPessoa - Insere uma pessoa no banco de dados e também na Azure.
	 * Sequencialmente, acontece: 1 - É inserido o endereço da pessoa, retornando o
	 * ID 2 - Inserida uma pessoa no BD e em seguida Azure, dentro do grupo de
	 * pessoas "grupopi"
	 * 
	 * @param Pessoa,
	 *            Endereço
	 * @return Objeto de pessoa em JSON
	 * @throws IOException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa", headers = "Accept=application/json")
	public @ResponseBody Pessoa inserirPessoa(@RequestBody Pessoa pessoa, Model model) throws IOException
	{
		// insere e pega o endereço cadastrado (ID_endereco é necessário)
		Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
		endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
		// Atualiza endereço de pessoa
		pessoa.setEndereco(endereco);
		// Insere a pessoa no BD e na Azure
		pessoa = pessoaService.inserirPessoa(pessoa);

		// treinaPessoa
		pessoaService.treinarApi();

		// Adiciona Pessoa como um atributo do model
		model.addAttribute("pessoa", pessoa);

		return pessoa;
	}

	/**
	 * BuscarPessoaPorId - Busca uma pessoa no banco pelo ID
	 * 
	 * @param ID
	 *            de pessoa
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
	 * @param Objeto
	 *            Pessoa
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
	 * IdentificaPessoa - Identifica uma pessoa na Azure por uma foto (URL) Detalhe:
	 * sequencialmente, acontece: 1 - O grupo de pessoas "grupopi" é treinado. 2 - É
	 * acionado o método detectaPessoaURL passando a foto como parâmetro e
	 * retornando o FaceID 3 - É acionado o método identificaPessoa que retorna as
	 * pessoas prováveis que tenham este rosto
	 * 
	 * @param Foto
	 * @return Lista de pessoas prováveis que tenham este rosto
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa/identifica", headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<Pessoa> identificaPessoa(@RequestBody String foto, Model model) throws IOException
	{
		try
		{
			// Instancia o AzureDAO e aciona método que treina o grupo
			AzureDAO azureDao = new AzureDAO();
			String personId = null;
			double confidence = 0;
			Pessoa pessoaIdentificada = new Pessoa();


			// Aqui é acionado o IdentificaPessoa que precisa de um FaceID, obtido através DetectaPessoa
			String resposta = azureDao.identificaPessoa(azureDao.detectaPessoa(foto));

			if (resposta != null)
			{
				String jsonString = resposta;
				JSONArray jsonArray = new JSONArray(jsonString);
				JSONArray candidates = jsonArray.getJSONObject(0).getJSONArray("candidates");
				for (int i = 0; i < candidates.length(); i++)
				{
					personId = candidates.getJSONObject(i).getString("personId");
					confidence = candidates.getJSONObject(i).getDouble("confidence");					
				}

			}
			System.out.println("Person id: " + personId);
			System.out.println("Confidence: " + confidence);

			pessoaIdentificada = pessoaService.buscarPessoaPeloPersonId(personId);
			pessoaIdentificada.setConfidence(confidence);
			System.out.println(pessoaIdentificada.toString());

			return new ResponseEntity<>(pessoaIdentificada,HttpStatus.OK);

		} catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}
}
