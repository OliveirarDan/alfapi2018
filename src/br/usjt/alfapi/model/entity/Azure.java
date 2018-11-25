package br.usjt.alfapi.model.entity;

public class Azure
{
	// Atributos
	private String codAzure;

	// Construtores
	public Azure()
	{

	}

	public Azure(String codAzure)
	{
		this.codAzure = codAzure;
	}

	// Métodos Get e Set
	public String getCodAzure()
	{
		return codAzure;
	}

	public void setCodAzure(String codAzure)
	{
		this.codAzure = codAzure;
	}
}
