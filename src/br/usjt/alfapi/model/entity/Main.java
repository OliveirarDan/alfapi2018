package br.usjt.alfapi.model.entity;


public class Main {
	
		public static void main(String[] args)
	    {
			
			Azure azure = new Azure();
			
			//Testando DETECT
	        /** 
	        String linkFoto = "https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg";
	        azure.detectaPessoaUrl(linkFoto);
	        */
			
			
			
			
			//Testando inserePessoa
			/**
			String nome = "DanDan";
			String dadosUsuario = "Brasileiro, legal e ciclista!";
			azure.inserePessoa(nome, dadosUsuario);
			*/
			
			//Testando insereFotoPessoaUrl - Insere foto na pessoa através de uma url
			
			String dadosUsuario = "Brasileiro, legal e ciclista! teste com foto";
			String idPessoa = "92cdabd5-9401-4f97-a834-c6d9f5ca92f9";
			String urlFoto = "https://media.licdn.com/dms/image/C5603AQGIZjaaEEZR4Q/profile-displayphoto-shrink_200_200/0?e=1543449600&v=beta&t=0KcGqHaAeBqmmfMBLVMB9XauA3W7tRaOQ5wlKj01kKI";
			azure.insereFotoPessoaUrl(idPessoa, urlFoto, dadosUsuario);
	    
			
	    }
	
	
	

}
