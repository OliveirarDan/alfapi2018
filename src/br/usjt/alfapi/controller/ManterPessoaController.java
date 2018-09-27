package br.usjt.alfapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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

/**
 * @author Estev�o Lucena
 *
 */


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
	 * M�todo inserirPessoa que deve receber um Json com o objeto pessoa para cadastro
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
	@RequestMapping(method = RequestMethod.POST, value = "rest/pessoa", headers = "Accept=application/json")
	public @ResponseBody Pessoa inserirPessoa(@RequestBody Pessoa pessoa, Model model) throws IOException
	{

		/*
		 * Endereco endereco = enderecoService.inserirEndereco(pessoa.getEndereco());
		 * endereco.setIdEndereco(pessoa.getEndereco().getIdEndereco());
		 * pessoa.setEndereco(endereco);
		 */
		Endereco enderecoNovo = new Endereco(99, "02991120", "Av", "Rua teste", 244, "Jaragua", "SP", "SP", "Europa");
		pessoa.setEndereco(enderecoNovo);

		pessoa = pessoaService.inserirPessoa(pessoa);

		model.addAttribute("pessoa", pessoa);

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
