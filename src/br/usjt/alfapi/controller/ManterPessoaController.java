package br.usjt.alfapi.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.alfapi.model.entity.Endereco;
import br.usjt.alfapi.model.entity.Pessoa;
import br.usjt.alfapi.model.service.EnderecoService;
import br.usjt.alfapi.model.service.PessoaService;



@Controller
public class ManterPessoaController {
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired 
	private EnderecoService enderecoService;
	
	@RequestMapping("/")
	public String iniciar() {
		return "index";
	}
	
	@RequestMapping("/index")
	public String iniciarA() {
		return "index";
	}
	
	@RequestMapping("/novaPessoa")
	public String novaPessoa() {
		return "NovaPessoa";
	}
	
	
	
	
	
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
	
	
	
	
	
}
