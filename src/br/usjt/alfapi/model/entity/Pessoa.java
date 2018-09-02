package br.usjt.alfapi.model.entity;

public class Pessoa {

	
	//atributos
	private int idPessoa;
	private String nome;
	private int cpf;
	private String rg;
	private String email;
	private String telResidencial;
	private int codAzure;
	
	
	
	
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
	public int getCodAzure() {
		return codAzure;
	}
	public void setCodAzure(int codAzure) {
		this.codAzure = codAzure;
	}
	
	//Método toString
	@Override
	public String toString() {
		return "Pessoa [idPessoa=" + idPessoa + ", nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + ", email=" + email
				+ ", telResidencial=" + telResidencial + ", codAzure=" + codAzure + "]";
	}
	
	
	
}
