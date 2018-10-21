package br.usjt.alfapi.model.entity;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Azure
{

	// Atributos
	private String codAzure;
	private ArrayList<File> fotos;

	
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

	public ArrayList<File> getFotos()
	{
		return fotos;
	}

	public void setFotos(ArrayList<File> fotos)
	{
		this.fotos = fotos;
	}

	// Método ToString
	@Override
	public String toString()
	{
		return "Azure [codAzure=" + codAzure + ", fotos=" + fotos + "]";
	}

	

}
