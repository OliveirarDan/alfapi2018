package br.usjt.alfapi.model.entity;

public class Endereco {

	//Atributos
	private int idEndereco;
	private String cep;
	private String tipoDeLogradouro;
	private String endereco;
	private String numero;
	private String bairro;
	private int idCidade;
	private int idPessoa;
	
	
	//Métodos Getters e Setters
	public int getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getTipoDeLogradouro() {
		return tipoDeLogradouro;
	}
	public void setTipoDeLogradouro(String tipoDeLogradouro) {
		this.tipoDeLogradouro = tipoDeLogradouro;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public int getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}
	public int getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	
	//Método toString
	@Override
	public String toString() {
		return "Endereco [idEndereco=" + idEndereco + ", cep=" + cep + ", tipoDeLogradouro=" + tipoDeLogradouro
				+ ", endereco=" + endereco + ", numero=" + numero + ", bairro=" + bairro + ", idCidade=" + idCidade
				+ ", idPessoa=" + idPessoa + "]";
	}
	
}
