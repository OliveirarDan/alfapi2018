package br.usjt.alfapi.model.entity;

import java.io.IOException;

import br.usjt.alfapi.controller.AzureController;
import br.usjt.alfapi.controller.ManterPessoaController;

public class Main
{

	public static void main(String[] args) throws IOException
	{

		AzureController azure = new AzureController();
		ManterPessoaController pessoaController = new ManterPessoaController();


		// Testando DETECT
		
		  String linkFoto =
		  "https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg";
		  azure.detectaPessoaUrl(linkFoto);
		 

		// Testando inserePessoa via ManterPessoaController
		
		//Parâmetros de pessoa
		/*Endereco enderecoNovo = new Endereco(545, "02991120", "Av", "Rua teste", 244, "Jaragua", "SP", "SP", "Europa");			
		Pessoa pessoa = new Pessoa();
		
		String codAzure;
		//int idPessoa;
		String nome = "Estevao";
		String sobrenome = "Lucena";
		String cpf = "40156111990";
		String registrosec = "";
		String email = "alves_estevao@hotmail.com";
		String dataNascimento = "09/12/1994";
		String genero = "Masculino";
		String telResidencial = "11954884997";
		String telSecundario = "11954884997";
		Endereco endereco = enderecoNovo;
		
		codAzure = azure.inserePessoa(nome, sobrenome);
		
		pessoa.setCodAzure(codAzure);
		pessoa.setNome(nome);
		pessoa.setSobrenome(sobrenome);
		pessoa.setCpf(cpf);
		pessoa.setRegistrosec(registrosec);
		pessoa.setEmail(email);
		pessoa.setDataNascimento(dataNascimento);
		pessoa.setGenero(genero);
		pessoa.setTelResidencial(telResidencial);
		pessoa.setTelSecundario(telSecundario);
		pessoa.setEndereco(endereco);
		
		pessoaController.inserirPessoa(pessoa);
		

	 	Testando insereFotoPessoaUrl - Insere foto na pessoa através de uma url

		
		 String dadosUsuario = "Brasileiro, legal e ciclista! teste com foto"; String
		 idPessoa = "92cdabd5-9401-4f97-a834-c6d9f5ca92f9"; String urlFoto =
		 "https://media.licdn.com/dms/image/C5603AQGIZjaaEEZR4Q/profile-displayphoto-shrink_200_200/0?e=1543449600&v=beta&t=0KcGqHaAeBqmmfMBLVMB9XauA3W7tRaOQ5wlKj01kKI";
		 azure.insereFotoPessoaUrl(idPessoa, urlFoto, dadosUsuario);
		*/ 

		
		azure.insereFotoPessoaLocal("0d57c9bf-f97f-4af3-ac1c-d360444699c5", "dandan");
		System.out.println("-------------------------------------");
//		azure.detectaFace("0d57c9bf-f97f-4af3-ac1c-d360444699c5", "dandan");
		System.out.println("-------------------------------------");
//		azure.treinar();
		System.out.println("-------------------------------------");
		//azure.identificar();
	}

}
