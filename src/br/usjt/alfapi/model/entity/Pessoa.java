package br.usjt.alfapi.model.entity;

public class Pessoa extends Azure{

	
	//atributos
	private int idPessoa;
	private String nome;
	private int cpf;
	private String rg;
	private String email;
	private String telResidencial;
	private Endereco endereco;	
		
	
/*	private String dataNasc;
	
	public String getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}
	
	Obs.: Fazer toString de data de nascimento, caso prof. aceite
	*/
	
	//Construtores
	public Pessoa() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public Pessoa(String codAzure, int idPessoa, String nome, int cpf, String rg, String email, String telResidencial, Endereco endereco) {
	super(codAzure);
	this.idPessoa = idPessoa;
	this.nome = nome;
	this.cpf = cpf;
	this.rg = rg;
	this.email = email;
	this.telResidencial = telResidencial;
	this.endereco = endereco;
}




	//Métodos Getters e Setters
	public int getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCpf() {
		return cpf;
	}
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelResidencial() {
		return telResidencial;
	}
	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}



	//Método toString
	@Override
	public String toString() {
		return "Pessoa [idPessoa=" + idPessoa + ", nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + ", email=" + email
				+ ", telResidencial=" + telResidencial + ", endereco=" + endereco + "]";
	}
	
	
	
}
