package br.usjt.alfapi.model.entity;

import java.util.ArrayList;

public class Azure {

	// Atributos
	private int idAzure;
	private String codAzure;
	private ArrayList<String> fotos;

	// Construtores
	public Azure() {
		// TODO Auto-generated constructor stub
	}
	
	public Azure(String codAzure) {
		this.codAzure = codAzure;
	}

	// Métodos Get e Set
	public int getIdAzure() {
		return idAzure;
	}

	public void setIdAzure(int idAzure) {
		this.idAzure = idAzure;
	}

	public String getCodAzure() {
		return codAzure;
	}

	public void setCodAzure(String codAzure) {
		this.codAzure = codAzure;
	}

	public ArrayList<String> getFotos() {
		return fotos;
	}

	public void setFotos(ArrayList<String> fotos) {
		this.fotos = fotos;
	}

	// Método ToString
	@Override
	public String toString() {
		return "Azure [idAzure=" + idAzure + ", codAzure=" + codAzure + ", fotos=" + fotos + "]";
	}
	
}
