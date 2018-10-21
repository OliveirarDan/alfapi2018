package br.usjt.alfapi.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

	//N�O ALTERAR
	@RequestMapping("/")
	public String iniciar()
	{
		return "index";
	}
	//N�O ALTERAR
	@RequestMapping("/index")
	public String iniciarA()
	{
		return "index";
	}
	//N�O ALTERAR
	@RequestMapping("/novaPessoa")
	public String novaPessoa()
	{
		return "NovaPessoa";
	}
	
	//N�O ALTERAR
		@Transactional
		@RequestMapping("/inserirPessoa")
		public String criarPessoa(@Valid Pessoa pessoa, BindingResult erros, Model model) {
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
	
		
		
		//N�O ALTERAR
				@Transactional
				@RequestMapping("/inserirPessoaFoto")
				public String criarPessoaFoto(@Valid Pessoa pessoa, @RequestParam("file") MultipartFile file, BindingResult erros, Model model) {
					try {
						if (!erros.hasErrors()) {
							//insere e pega o endere�o cadastrado (ID_endereco � necess�rio)
							Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
							endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
							//Atualiza endere�o de pessoa
							pessoa.setEndereco(endereco);
							//Insere pessoa no banco
							pessoa = pessoaService.inserirPessoa(pessoa);
							
							//Convertendo Tipo de file
							File foto = new File(file.getOriginalFilename());
							foto.createNewFile();
							FileOutputStream fos = new FileOutputStream(foto); 
						    fos.write(file.getBytes());
						    fos.close();
						   
						    pessoa.setFoto(foto);
							
							//Inserindo imagens da pessoa na API
							pessoaService.inserirFotoPessoaFile(pessoa, foto);
						
							//Treinando API ap�s inser��o de imagens
							pessoaService.treinarApi();
							//Identifica pessoa a partir de uma imagem
							pessoaService.identificarPessoa(foto);
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
			
		
		//TESTE
		@RequestMapping("/pegaFoto")
		public String pegaFoto1( Pessoa pessoa,@RequestParam("file") MultipartFile file, BindingResult erros, Model model) {
			try {
				if (!erros.hasErrors()) {
					
					//Convertendo Tipo de file
					File foto = new File(file.getOriginalFilename());
					foto.createNewFile();
					FileOutputStream fos = new FileOutputStream(foto); 
				    fos.write(file.getBytes());
				    fos.close();
					
					System.out.println(foto.getName());
					
					pessoaService.identificarPessoa(foto);
					
					
					System.out.println(pessoa.getNome());
					System.out.println(file.getOriginalFilename());
					return "Resultado";
					
				} else {
					System.out.println(pessoa.getNome());
					System.out.println(pessoa.getFoto().toString());
					return "ruim";
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("erro", e);
				return "Erro";
			}
		}
		
		
		@RequestMapping("/identifica")
		public String identifica()
		{
			return "PegaFotos";
		}
		
		//TESTE
		@RequestMapping("/upload")
		
		public  String pegaFoto(@RequestParam("file") MultipartFile file, BindingResult erros, Model model) {
			try {
				if (!erros.hasErrors()) {
					
					//Convertendo Tipo de file
					File foto = new File(file.getOriginalFilename());
					foto.createNewFile();
					FileOutputStream fos = new FileOutputStream(foto); 
				    fos.write(file.getBytes());
				    fos.close();
					
					System.out.println(foto.getName());
					
					pessoaService.identificarPessoa(foto);
					
					return "Resultado";
					
				} else {
					
					System.out.println(file.getName());
					return "ruim";
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("erro", e);
				return "Erro";
			}
		}
		

		//TESTE
		@RequestMapping("/uploadMultiple")
		public String iniciar1()
		{
			return "uploadMultiple";
		}
		//TESTE
		/**
		 * Upload multiple file using Spring Controller
		 */
		@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
		public @ResponseBody
		String uploadMultipleFileHandler(@RequestParam("name") String[] names,
				@RequestParam("file") MultipartFile[] files) {

			if (files.length != names.length)
				return "Mandatory information missing";

			String message = "";
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String name = names[i];
				try {
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

				

					message = message + "You successfully uploaded file=" + name
							+ "<br />";
				} catch (Exception e) {
					return "You failed to upload " + name + " => " + e.getMessage();
				}
			}
			return message;
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
	{
		pessoa = pessoaService.inserirPessoa(pessoa);

		
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
}
