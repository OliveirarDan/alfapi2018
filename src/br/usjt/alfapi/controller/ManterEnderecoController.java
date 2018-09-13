package br.usjt.alfapi.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.alfapi.model.entity.Endereco;
import br.usjt.alfapi.model.service.EnderecoService;

@Controller
public class ManterEnderecoController {
	
	private EnderecoService enderecoService;
	
	public ManterEnderecoController() {
		enderecoService = new EnderecoService();
	}
	
	
	@RequestMapping("index")
	public String inicio() {
		return "index";
	}
	
	@RequestMapping("/novoEndereco")
	public String novoEndereco() {
		return "NovoEndereco";
	}
	
	@RequestMapping("/inserirEndereco")
	public String inserirEndereco(Endereco endereco, Model model) throws SQLException 
	{
		enderecoService.inserir(endereco);
		model.addAttribute("endereco", endereco);
		return "Resultado";
		
	}

}
