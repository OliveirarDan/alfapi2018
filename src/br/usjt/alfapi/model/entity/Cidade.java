package br.usjt.alfapi.model.entity;

public class Cidade {
	
	//atributos
	private int idCidade;
	private String nomeCidade;
	private int idEstado;
	
	
	//M�todos Getters e Setters
	public int getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	//M�todo toString
	@Override
	public String toString() {
		return "Cidade [idCidade=" + idCidade + ", nomeCidade=" + nomeCidade + ", idEstado=" + idEstado + "]";
	}
	
	
	
	
}
