package br.usjt.alfapi.model.entity;

import br.usjt.alfapi.model.dao.AzureDAO;
import br.usjt.alfapi.model.dao.PessoaDAO;
import br.usjt.alfapi.model.service.PessoaService;

public class Testes {

	static AzureDAO azure = new AzureDAO();	
	static PessoaDAO pessoaDAO = new PessoaDAO();
	static PessoaService pservice = new PessoaService(pessoaDAO);
	
	
	public static void main(String[] args) {
		//chamaDetect();
		pservice.identificarPessoa("C://Pessoas/Neymar/Neymar4.jpg");
		//azureDao.identificaPessoa(azureDao.detectaPessoa(urlFoto));
		//azure.identificaPessoa("dfe81c09-9638-48dd-8e30-d4295f52807e");
		//chamaTreinar();
	}

	
		
	public static String chamaDetect() {
		return azure.detectaPessoa("C://Pessoas/Neymar/neymar1.jpg");
	}
	
	
	public static void chamaInserePessoa() {
		azure.inserePessoa("Cristiano", "Português");		
	}
	
	public static void chamaInsereFoto() {
		azure.insereFotoPessoaLocal("006f5ce1-2dd4-4d9d-aa96-60fe317eca8e", "Cristiano, Portugues", "C://Pessoas/Cristiano/cristiano3.jpg");
	}
	
	public static void chamaTreinar() {
		azure.treinar();
	}
	public static void chamaIdentificar() {
		azure.identificaPessoa(chamaDetect());
	}
}
