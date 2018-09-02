package br.usjt.alfapi.model.entity;

public class Estado {

	//Atributos
	private int idEstado;
	private String nomeEstado;
	private int idPais;
	
	//Métodos Getters e Setters
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public String getNomeEstado() {
		return nomeEstado;
	}
	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	public int getIdPais() {
		return idPais;
	}
	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}
	
	//Método toString
	@Override
	public String toString() {
		return "Estado [idEstado=" + idEstado + ", nomeEstado=" + nomeEstado + ", idPais=" + idPais + "]";
	}
	
	
	
	
}
