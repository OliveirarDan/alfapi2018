package br.usjt.alfapi.model.entity;

import br.usjt.alfapi.model.dao.AzureDAO;
import br.usjt.alfapi.model.dao.PessoaDAO;
import br.usjt.alfapi.model.service.PessoaService;

public class Testes {

	static AzureDAO azure = new AzureDAO();	
	static PessoaDAO pessoaDAO = new PessoaDAO();
<<<<<<< HEAD
	static PessoaService pService = new PessoaService(pessoaDAO);
=======
	static PessoaService pservice = new PessoaService(pessoaDAO);
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	
	
	public static void main(String[] args) {
		//chamaDetect();
<<<<<<< HEAD
		//pService.identificarPessoa("C://Pessoas/Neymar/Neymar4.jpg");
		//azureDao.identificaPessoa(azureDao.detectaPessoa(urlFoto));
		//azure.identificaPessoa("dfe81c09-9638-48dd-8e30-d4295f52807e");
		//chamaTreinar();
		
		chamaInserePessoa();
		chamaInsereFoto();
		chamaTreinar();
		chamaIdentificar();
=======
		pservice.identificarPessoa("C://Pessoas/Neymar/Neymar4.jpg");
		//azureDao.identificaPessoa(azureDao.detectaPessoa(urlFoto));
		//azure.identificaPessoa("dfe81c09-9638-48dd-8e30-d4295f52807e");
		//chamaTreinar();
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	}

	
		
	public static String chamaDetect() {
<<<<<<< HEAD
		return azure.detectaPessoa("C:/Users/alves/eclipse-workspace/alfapi2018/WebContent/Pessoas/Cristiano/cristiano3.jpg");
=======
		return azure.detectaPessoa("C://Pessoas/Neymar/neymar1.jpg");
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
		// "faceId": "d57759c8-b615-40d8-b904-d9231b409550" messi
	}
	
	
	public static void chamaInserePessoa() {
		azure.inserePessoa("Cristiano", "Português");
		
<<<<<<< HEAD
		
=======
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	//	Pessoas Inseridas
	//	Messi, argentino{id: "d30e75a2-fbd0-4b99-8e32-503b07c5a7e8"}
		//Fotos Messi 
		//HTTP/1.1 200 OK {"persistedFaceId":"2e1d770d-214f-4d09-9ed9-1bd001d7abbb"}
		//HTTP/1.1 200 OK {"persistedFaceId":"1f0fa418-ee64-4ff5-900e-19496841ea23"}

		
	//	Cristiano, Português{id: "006f5ce1-2dd4-4d9d-aa96-60fe317eca8e"}
		//Fotos Cristiano
		//HTTP/1.1 200 OK {"persistedFaceId":"92a36655-1258-48a3-9c4c-0fc9886e8724"}
		//HTTP/1.1 200 OK {"persistedFaceId":"eff65196-ace2-4645-a176-27d9bacde0a0"}
		//HTTP/1.1 200 OK {"persistedFaceId":"d2c124ac-ab87-4d9c-a26e-a370e9cadcc6"}
		//id: "0c2ea7ee-0266-459a-86d9-fefdfcc0d047"

		
	}
	public static void chamaInsereFoto() {
<<<<<<< HEAD
		azure.insereFotoPessoaLocal("78a10703-c1b5-488e-a5d3-6264bbbda90b", "Cristiano, Portugues", "C:/Users/alves/eclipse-workspace/alfapi2018/WebContent/Pessoas/Cristiano/cristiano3.jpg");
=======
		azure.insereFotoPessoaLocal("006f5ce1-2dd4-4d9d-aa96-60fe317eca8e", "Cristiano, Portugues", "C://Pessoas/Cristiano/cristiano3.jpg");
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	}
	
	public static void chamaTreinar() {
		azure.treinar();
	}
	public static void chamaIdentificar() {
		azure.identificaPessoa(chamaDetect());
	}
}
