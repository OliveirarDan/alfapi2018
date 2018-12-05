package br.usjt.alfapi.model.dao;

import java.io.IOException;
import java.net.URI;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * AzureDAO - Acesso a Face API
 * 
 * @author AlfaPIGroup Classe responsável pelos métodos de acesso a
 *         Azure_Face_API
 * 
 */
public class AzureDAO
{
	@Autowired
	private PessoaDAO pessoaDAO;

	// ChaveAzure
	private static final String subscriptionKey = "7fd3ca785d244cd397458187788f108c";

	/**
	 * detectaPessoa - Esse método detecta pessoa carregando um arquivo (foto)
	 * 
	 * @param urlFoto
	 *            - Endereço da foto no sistema
	 * @return - Id da foto (usado para o identificarPessoa)
	 * @throws IOException
	 */
	public String detectaPessoa(String foto) throws IOException
	{
		String endPointDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		String idFoto = "";

		// Prepara a string do base64
		String base64Image = foto.split(",")[1];
		// This will decode the String which is encoded by using Base64 class
		byte[] imageByte = Base64.decodeBase64(base64Image);

		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPointDetect);
			// Request parameters. All of them are optional.
			builder.setParameter("returnFaceId", "true");
			builder.setParameter("returnFaceLandmarks", "false");
			// Prepare the URI for the REST API call.
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			// Request headers.
			request.setHeader("Content-Type", "application/octet-stream");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body.
			ByteArrayEntity reqEntity = new ByteArrayEntity(imageByte);
			request.setEntity(reqEntity);
			// Execute the REST API call and get the response entity.
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			json = json.replace("[", "");

			// Tratamento do response
			if (entity != null)
			{
				JSONObject teste = new JSONObject(json);
				idFoto = teste.getString("faceId");
				System.out.println("Detecta Pessoa, faceId: \n " + idFoto);
			}
		} catch (Exception e)
		{
			// Display error message.
			System.out.println(e.getMessage());
		}
		return idFoto;
	}

	/**
	 * inserePessoa - Esse método insere pessoa em um grupo da azure
	 * 
	 * @param nome
	 * @param dadosUsuario
	 * @return codAzure
	 */
	public String inserePessoa(String nome, String dadosUsuario)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopii/persons";
		String codAzure = "";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			// Request header
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{\r\n" + "    \"name\": \"" + nome + "\",\r\n"
					+ "    \"userData\": \"" + dadosUsuario + "\"\r\n" + "}\r\n" + "");
			request.setEntity(reqEntity);
			// Response
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());

			// Tratamento do response
			if (entity != null)
			{
				final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
				if (node.has("personId"))
				{
					codAzure = node.get("personId").toString();
					codAzure = codAzure.replaceAll("\"", "");
				}
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return codAzure;
	}

	/**
	 * treinar - Esse método faz a API treinar, deve ser utilizado depois de inserir
	 * as fotos de uma pessoa.
	 */
	public void treinar()
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopii/train";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{ }");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				System.out.println("Treinando grupo de pessoas \n");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * InsereFotoPessoa - Esse método insere a foto da pessoa através de uma file
	 * 
	 * @param codAzure
	 *            CodAzure utilizado no cadastro da Pessoa.
	 * @param dadosUsuario
	 *            Descrição ou informações da Pessoa.
	 * @param file
	 *            file da foto.
	 * @throws IOException
	 */
	public String insereFotoPessoa(String codAzure, String foto) throws IOException
	{
		String idFoto = null;
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopii/persons/"
				+ codAzure + "/persistedFaces";

		// Prepara a string do base64
		String base64Image = foto.split(",")[1];
		// This will decode the String which is encoded by using Base64 class
		byte[] imageByte = Base64.decodeBase64(base64Image);

		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/octet-stream");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			builder.setParameter("targetFace", "");
			// Request body

			ByteArrayEntity reqEntity = new ByteArrayEntity(imageByte);
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity);

			final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
			if (node.has("persistedFaceId"))
			{
				idFoto = node.get("persistedFaceId").toString();
				idFoto = idFoto.replaceAll("\"", "");
			}

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return idFoto;
	}

	/**
	 * identificaPessoa - Esse método identifica uma pessoa no grupo a partir do id
	 * recebido do método detectaPessoa
	 * 
	 * @param idFoto
	 *            - id da foto gerado no detectaPessoa.
	 */
	public String identificaPessoa(String foto)
	{

		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/identify";
		String resposta = null;
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{\n" + "    \"personGroupId\": \"grupopii\",\n"
					+ "    \"faceIds\": [\n" + "        \"" + foto + "\"\n" + "    ],\n"
					+ "    \"maxNumOfCandidatesReturned\": 1,\n" + "    \"confidenceThreshold\": 0.5\n" + "}");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			resposta = EntityUtils.toString(response.getEntity());

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return resposta;
	}

}