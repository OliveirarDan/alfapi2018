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

	@RequestMapping("/")
	public String iniciar()
	{
		return "index";
	}

	@RequestMapping("/index")
	public String iniciarA()
	{
		return "index";
	}

	@RequestMapping("/novaPessoa")
	public String novaPessoa()
	{
		return "NovaPessoa";
	}
	
	/*
	 * Método inserirPessoa que deve receber um Json com o objeto pessoa para cadastro
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

	//NÃO ALTERAR
	@Transactional
	@RequestMapping("/inserirPessoa")
	public String criarFilme(@Valid Pessoa pessoa, BindingResult erros, Model model) {
		try {
			if (!erros.hasErrors()) {
				Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
				endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
				pessoa.setEndereco(endereco);
				

				pessoa = pessoaService.inserirPessoa(pessoa);

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
