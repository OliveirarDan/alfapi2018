package br.usjt.alfapi.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

	//NÃO ALTERAR
	@RequestMapping("/")
	public String iniciar()
	{
		return "index";
	}
	//NÃO ALTERAR
	@RequestMapping("/index")
	public String iniciarA()
	{
		return "index";
	}
	//NÃO ALTERAR
	@RequestMapping("/novaPessoa")
	public String novaPessoa()
	{
		return "NovaPessoa";
	}
	
	//NÃO ALTERAR
		@Transactional
		@RequestMapping("/inserirPessoa")
		public String criarFilme(@Valid Pessoa pessoa, BindingResult erros, Model model) {
			try {
				if (!erros.hasErrors()) {
					//insere e pega o endereço cadastrado (ID_endereco é necessário)
					Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
					endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
					//Atualiza endereço de pessoa
					pessoa.setEndereco(endereco);
					//Insere pessoa no banco
					pessoa = pessoaService.inserirPessoa(pessoa);
					//Inserindo imagens da pessoa na API
					pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho1.jpg");
					pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho2.jpg");
					pessoaService.inserirFotoPessoa(pessoa, "C://Pessoas/Ronaldinho/ronaldinho3.jpg");
					//Treinando API após inserção de imagens
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
	 * Método inserirPessoa2 que deve receber um Json com o objeto pessoa para cadastro
	 * Headers: utilizado para que o método possa reconhecer que irá receber um Json. 
	 * Observe que no GET não foi necessário (talvez em outros casos seja.).
	 * ResponseBody: qual será o retorno do método.
	 * RequestBody: o que p método irá receber no corpo do Json. 
	 * **Observe que precisa ser definido o que irá no corpo do response e do request.
	 * 
	 * PROBLEMA: está informando um erro do tipo Hibernate no entity Endereço.
	 * Acho que é algo sobre o relacionamento entre Endereco e Pessoa. 
	 * Precisa validar se a classe entity.Endereco e Pessoa estão anotadas corretamente.
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
	 * Método para buscar pessoa por Id.
	 * RequestMapping é necessário para mapear o tipo de requisicao
	 * ResponseBody: determina qual será o corpo da resposta, ou seja, o que irá
	 * no retorno do método como Json
	 * PathVariable: usado para identificar o id como atributo e não um recurso do método	 * 
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
