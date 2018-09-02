package br.usjt.alfapi.model.entity;

public class Pais {

	//Atributos
	private int idPais;
	private String nomePais;
	
	//Métodos Getters e Setters
	public int getIdPais() {
		return idPais;
	}
	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}
	public String getNomePais() {
		return nomePais;
	}
	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}
	
	//Método toString
	@Override
	public String toString() {
		return "Pais [idPais=" + idPais + ", nomePais=" + nomePais + "]";
	}
	
	
	
}
