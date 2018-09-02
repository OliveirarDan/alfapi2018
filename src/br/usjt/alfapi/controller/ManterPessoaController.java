package br.usjt.alfapi.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.alfapi.model.entity.Pessoa;
import br.usjt.alfapi.model.service.PessoaService;



@Controller
public class ManterPessoaController {

	private PessoaService pessoaService;
	
	public ManterPessoaController() {
		pessoaService = new PessoaService();
	}
	
	@RequestMapping("/novaPessoa")
	public String novaPessoa() {
		return "NovaPessoa";
	}
	
	@RequestMapping("/inserirPessoa")
	public String inserirPessoa(Pessoa pessoa, Model model) throws SQLException 
	{
		pessoaService.inserir(pessoa);
		model.addAttribute("pessoa", pessoa);
		return "Resultado";
		
	}
	
	
}
