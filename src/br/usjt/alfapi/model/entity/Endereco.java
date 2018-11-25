package br.usjt.alfapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco
{

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEndereco;

	private String cep;
	private String tipoDeLogradouro;
	private String endereco;
	private int numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String pais;

	// Construtores
	public Endereco()
	{
		// TODO Auto-generated constructor stub
	}

	public Endereco(int idEndereco, String cep, String tipoDeLogradouro, String endereco, int numero, String bairro,
			String cidade, String estado, String pais)
	{
		super();
		this.idEndereco = idEndereco;
		this.cep = cep;
		this.tipoDeLogradouro = tipoDeLogradouro;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
	}

	// Métodos Getters e Setters
	public int getIdEndereco()
	{
		return idEndereco;
	}

	public void setIdEndereco(int idEndereco)
	{
		this.idEndereco = idEndereco;
	}

	public String getCep()
	{
		return cep;
	}

	public void setCep(String cep)
	{
		this.cep = cep;
	}

	public String getTipoDeLogradouro()
	{
		return tipoDeLogradouro;
	}

	public void setTipoDeLogradouro(String tipoDeLogradouro)
	{
		this.tipoDeLogradouro = tipoDeLogradouro;
	}

	public String getEndereco()
	{
		return endereco;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}

	public int getNumero()
	{
		return numero;
	}

	public void setNumero(int numero)
	{
		this.numero = numero;
	}

	public String getBairro()
	{
		return bairro;
	}

	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}

	public String getCidade()
	{
		return cidade;
	}

	public void setCidade(String cidade)
	{
		this.cidade = cidade;
	}

	public String getEstado()
	{
		return estado;
	}

	public void setEstado(String estado)
	{
		this.estado = estado;
	}

	public String getPais()
	{
		return pais;
	}

	public void setPais(String pais)
	{
		this.pais = pais;
	}

	// Método toString
	@Override
	public String toString()
	{
		return "Endereco [idEndereco=" + idEndereco + ", cep=" + cep + ", tipoDeLogradouro=" + tipoDeLogradouro
				+ ", endereco=" + endereco + ", numero=" + numero + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", estado=" + estado + ", pais=" + pais + "]";
	}

}
