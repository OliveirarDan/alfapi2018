package br.usjt.alfapi.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	// NÃO ALTERAR
	@RequestMapping("/")
	public String iniciar()
	{
		return "index";
	}

	// NÃO ALTERAR
	@RequestMapping("/index")
	public String iniciarA()
	{
		return "index";
	}

	// NÃO ALTERAR
	@RequestMapping("/novaPessoa")
	public String novaPessoa()
	{
		return "NovaPessoa";
	}

	// NÃO ALTERAR
	@Transactional
	@RequestMapping("/inserirPessoa")
	public String inserirPessoa(@Valid Pessoa pessoa, BindingResult erros, Model model)
	{
		try
		{
			if (!erros.hasErrors())
			{
				// insere e pega o endereço cadastrado (ID_endereco é necessário)
				Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
				endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
				// Atualiza endereço de pessoa
				pessoa.setEndereco(endereco);
				// Insere pessoa no banco
				pessoa = pessoaService.inserirPessoa(pessoa);
				// Inserindo imagens da pessoa na API
				pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho1.jpg");
				pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho2.jpg");
				pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho3.jpg");
				// Treinando API após inserção de imagens
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
	
	

	// NÃO ALTERAR
	@Transactional
	@RequestMapping("/inserirPessoaFoto64")
	public String criarPessoaFoto64(@Valid Pessoa pessoa, @RequestParam(required=false,name="file") String string64, BindingResult erros,
			Model model) {
		try {
			if (!erros.hasErrors()) {
				// insere e pega o endereço cadastrado (ID_endereco é necessário)
				Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
				endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
				// Atualiza endereço de pessoa
				pessoa.setEndereco(endereco);
				// Insere pessoa no banco
				pessoa = pessoaService.inserirPessoa(pessoa);
				
				// Prepara a string do base64
				String base64Image = string64.split(",")[1];
				// This will decode the String which is encoded by using Base64 class
				byte[] imageByte = Base64.decodeBase64(base64Image);

				// salva imagem no servidor
				String directory = "C:/teste/" + pessoa.getNome() + ".png";

				// Convertendo Tipo de file
				File foto = new File(directory);
				foto.createNewFile();
				FileOutputStream fos = new FileOutputStream(foto);
				fos.write(imageByte);
				fos.close();
				
				
				// Inserindo imagens da pessoa na API
				pessoaService.inserirFotoPessoaFile(pessoa, foto);
				// Treinando API após inserção de imagens
				pessoaService.treinarApi();
				// Manda o objeto pessoa atualizado para o model
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

	// TESTE
	@RequestMapping("/pegaFoto64")
	public String pegaFoto64( Pessoa pessoa, @RequestParam(required=false,name="file") String string64, BindingResult erros, Model model) {
		try {
			if (!erros.hasErrors()) {
				// Prepara a string do base64
				String base64Image = string64.split(",")[1];
				// This will decode the String which is encoded by using Base64 class
				byte[] imageByte = Base64.decodeBase64(base64Image);
				// salva imagem no servidor
				String directory = "C:/teste/" + pessoa.getNome() + ".png";
				// Convertendo Tipo de file
				File foto = new File(directory);
				foto.createNewFile();
				FileOutputStream fos = new FileOutputStream(foto);
				fos.write(imageByte);
				fos.close();
				pessoaService.identificarPessoa(foto);
				System.out.println(pessoa.getNome());
				return "Resultado";
			} else {
				System.out.println(pessoa.getNome());
				
				return "ruim";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("erro", e);
			return "Erro";
		}
	}

	
	@RequestMapping("/identifica")
	public String identifica() {
		return "PegaFotos";
	}

	
	
	
	
	
	

	/**
	 * InserirPessoa - Insere uma pessoa no banco de dados e também na Azure.
	 * Sequencialmente, acontece: 
	 * 	1 - É inserido o endereço da pessoa, retornando o ID 
	 * 	2 - Inserida uma pessoa no BD e em seguida Azure, dentro do grupo de pessoas "grupopi" 
	 * 
	 * @param Pessoa, Endereço
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
	 * 	1 - O grupo de pessoas "grupopi" é treinado.
	 * 	2 - É acionado o método detectaPessoaURL passando a foto como parâmetro e retornando o FaceID
	 * 	3 - É acionado o método identificaPessoa que retorna as pessoas prováveis que tenham este rosto
	 * 
	 * @param Foto
	 * @return Lista de pessoas prováveis que tenham este rosto
	 * @throws IOException
	 */

	@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa/identifica", headers = "Accept=application/json")
	public @ResponseBody String identificaPessoa(@RequestBody String foto, Model model) throws IOException
	{
		try
		{
			// Instancia o AzureDAO e aciona método que treina o grupo
			AzureDAO azureDao = new AzureDAO();
			azureDao.treinar();

			// Aqui é acionado o IdentificaPessoa que precisa de um FaceID, obtido através do DetectaPessoa
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
