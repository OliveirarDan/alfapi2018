package br.usjt.alfapi.model.entity;

import java.util.ArrayList;

public class Azure {

	// Atributos
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
		return "Azure [codAzure=" + codAzure + ", fotos=" + fotos + "]";
	}
	
}
