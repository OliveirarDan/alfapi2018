package br.usjt.alfapi.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

public class Teste
{
	@Path("/teste") // DEFINICAO DO METODO POST PARA INSERT E DA URL
																	// COMO REST/PESSOA

	@GET
	@Produces("text/plain")
	public String getClichedMessage()
	{
		return "Hello World";
	}

}