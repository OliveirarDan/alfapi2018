package br.usjt.alfapi.model.dao;

import java.io.File;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

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
	// ChaveAzure
	private static final String subscriptionKey = "8fe8eda9d154459f91b30bc8165e4f17";

	/**
	 * detectaPessoa - Esse método detecta pessoa carregando um arquivo (foto)
	 * 
	 * @param urlFoto
	 *            - Endereço da foto no sistema
	 * @return - Id da foto (usado para o identificarPessoa)
	 */
	public String detectaPessoa(String urlFoto)
	{
		String endPointDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		String idFoto = "";
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
			File file = new File(urlFoto);
			FileEntity reqEntity = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);
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
				System.out.println("Rodando detectPessoa() \nId da foto: " + idFoto
						+ "\n---------------------------------------------------\n");
			}
		} catch (Exception e)
		{
			// Display error message.
			System.out.println(e.getMessage());
		}
		return idFoto;
	}

	/**
	 * detectaPessoaUrl - Esse método detecta pessoa(s) em uma foto por meio de um
	 * URL.
	 * 
	 * @param linkFoto
	 */
	public String detectaPessoaUrl(String linkFoto)
	{
		String retorno = null;
		String endPointDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		//String faceAttributesDetect = "age,gender,headPose,smile,facialHair,glasses,emotion,hair";
		String imageWithFaces = linkFoto;
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPointDetect);
			// Request parameters. All of them are optional.
			builder.setParameter("returnFaceId", "true");
			builder.setParameter("returnGender", "false");
			builder.setParameter("returnFaceLandmarks", "false");
			//builder.setParameter("returnFaceAttributes", faceAttributesDetect);
			// Prepare the URI for the REST API call.
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			// Request headers.
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body.
			StringEntity reqEntity = new StringEntity(imageWithFaces);
			request.setEntity(reqEntity);
			// Execute the REST API call and get the response entity.
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			json = json.replace("[", "");
			if (entity != null)
			{
				// Verifica o response e captura o faceID
				JSONObject teste = new JSONObject(json);
				retorno = teste.getString("faceId");
				System.out.println("FaceID da pessoa: " + retorno);
				/*// Format and display the JSON response.
				System.out.println("REST Response:\n");
				String jsonString = EntityUtils.toString(entity).trim();
				if (jsonString.charAt(0) == '[')
				{
					JSONArray jsonArray = new JSONArray(jsonString);
					System.out.println(jsonArray.toString(2));
				} else if (jsonString.charAt(0) == '{')
				{
					JSONObject jsonObject = new JSONObject(jsonString);
					System.out.println(jsonObject.toString(2));
				} else
				{
					System.out.println(jsonString);
				}*/
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return retorno;
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
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons";
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
	 * InsereFotoPessoaLocal - Esse método insere a foto da pessoa através de uma
	 * foto carregada do sistema.
	 * 
	 * @param codAzure
	 *            CodAzure utilizado no cadastro da Pessoa.
	 * @param dadosUsuario
	 *            Descrição ou informações da Pessoa.
	 * @param fotourl
	 *            URL da foto no sistema.
	 */
	public void insereFotoPessoaLocal(String codAzure, String dadosUsuario, String fotourl)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons/"
				+ codAzure + "/persistedFaces";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/octet-stream");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			builder.setParameter("userData", "{" + dadosUsuario + "}");
			builder.setParameter("targetFace", "");
			// Request body
			File file = new File(fotourl);
			FileEntity reqEntity = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if (entity != null)
			{
				System.out.println(EntityUtils.toString(entity));

				System.out.println("Foto :" + fotourl + " de Pesssoa Inserida no Azure, com sucesso!");
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	/**
	 * treinar - Esse método faz a API treinar, deve ser utilizado depois de inserir
	 * as fotos de uma pessoa.
	 */
	public void treinar()
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/train";
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
				System.out.println(EntityUtils.toString(entity));
				System.out.println("Treinando API");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * identificaPessoa - Esse método identifica uma pessoa no grupo a partir do id
	 * recebido do método detectaPessoa
	 * 
	 * @param idFoto
	 *            - id da foto gerado no detectaPessoa.
	 */
	public String identificaPessoa(String idFoto)
	{
		String retorno = null;
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/identify";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{\n" + "    \"personGroupId\": \"grupopi\",\n"
					+ "    \"faceIds\": [\n" + "        \"" + idFoto + "\"\n" + "    ],\n"
					+ "    \"maxNumOfCandidatesReturned\": 5,\n" + "    \"confidenceThreshold\": 0.5\n" + "}");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			retorno = json;
			System.out.println("IdentidicaPessoa, pessoas com este rosto:" + "\n" + json);


		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return retorno;
	}

}
