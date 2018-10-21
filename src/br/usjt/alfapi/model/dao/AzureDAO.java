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
<<<<<<< HEAD
 * 
 * @author AlfaPIGroup Classe responsável pelos métodos de acesso a
 *         Azure_Face_API
 * 
 */
public class AzureDAO
{
=======
 * @author Danilo - AlfaPIGroup
 * Classe responsável pelos métodos de acesso a Azure_Face_API
 * 
 */
public class AzureDAO {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	// ChaveAzure
	private static final String subscriptionKey = "8fe8eda9d154459f91b30bc8165e4f17";

	/**
	 * detectaPessoa - Esse método detecta pessoa carregando um arquivo (foto)
<<<<<<< HEAD
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
=======
	 * @param urlFoto - Endereço da foto no sistema
	 * @return - Id da foto (usado para o identificarPessoa
	 */
	public String detectaPessoa(String urlFoto) {
		String endPointDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		String idFoto = "";
		HttpClient httpclient = new DefaultHttpClient();
		try {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
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
<<<<<<< HEAD
			if (entity != null)
			{
=======
			if (entity != null) {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
				JSONObject teste = new JSONObject(json);
				idFoto = teste.getString("faceId");
				System.out.println("Rodando detectPessoa() \nId da foto: " + idFoto
						+ "\n---------------------------------------------------\n");
			}
<<<<<<< HEAD
		} catch (Exception e)
		{
=======
		} catch (Exception e) {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
			// Display error message.
			System.out.println(e.getMessage());
		}
		return idFoto;
	}
<<<<<<< HEAD

=======
	
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	/**
	 * detectaPessoaUrl - Esse método detecta pessoa(s) em uma foto por meio de um
	 * URL.
	 * 
	 * @param linkFoto
	 */
<<<<<<< HEAD
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
=======
	public void detectaPessoaUrl(String linkFoto) {
		String endPointDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		String faceAttributesDetect = "age,gender,headPose,smile,facialHair,glasses,emotion,hair";
		String imageWithFaces = "{\"url\":\"" + linkFoto + "\"}";
		HttpClient httpclient = new DefaultHttpClient();
		try {
			URIBuilder builder = new URIBuilder(endPointDetect);
			// Request parameters. All of them are optional.
			builder.setParameter("returnFaceId", "true");
			builder.setParameter("returnGender", "true");
			builder.setParameter("returnFaceLandmarks", "false");
			builder.setParameter("returnFaceAttributes", faceAttributesDetect);
			// Prepare the URI for the REST API call.
			URI uri = builder.build();
			// URI uri = new
			// URI(uriBase+"?returnFaceId=true&returnFaceLandmarks=false&returnGender=true");
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
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
<<<<<<< HEAD
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

=======
			if (entity != null) {
				// Format and display the JSON response.
				System.out.println("REST Response:\n");
				String jsonString = EntityUtils.toString(entity).trim();
				if (jsonString.charAt(0) == '[') {
					JSONArray jsonArray = new JSONArray(jsonString);
					System.out.println(jsonArray.toString(2));
				} else if (jsonString.charAt(0) == '{') {
					JSONObject jsonObject = new JSONObject(jsonString);
					System.out.println(jsonObject.toString(2));
				} else {
					System.out.println(jsonString);
				}
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
	}
		
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	/**
	 * inserePessoa - Esse método insere pessoa em um grupo da azure
	 * 
	 * @param nome
	 * @param dadosUsuario
	 * @return codAzure
	 */
<<<<<<< HEAD
	public String inserePessoa(String nome, String dadosUsuario)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons";
		String codAzure = "";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
=======
	public String inserePessoa(String nome, String dadosUsuario) {
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons";
		String codAzure = "";
		HttpClient httpclient = new DefaultHttpClient();
		try {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
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
<<<<<<< HEAD
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
=======
			if (entity != null) {
				final ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
				if (node.has("personId")) {
					codAzure = node.get("personId").toString();
					codAzure = codAzure.replaceAll("\"", "");
					System.out.println(codAzure); // só para teste
				}
			}
		} catch (Exception e) {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
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
<<<<<<< HEAD
	public void insereFotoPessoaLocal(String codAzure, String dadosUsuario, String fotourl)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons/"
				+ codAzure + "/persistedFaces";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
=======
	public void insereFotoPessoaLocal(String codAzure, String dadosUsuario, String fotourl) {
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons/"
				+ codAzure + "/persistedFaces";
		HttpClient httpclient = new DefaultHttpClient();
		try {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
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
<<<<<<< HEAD
			if (entity != null)
			{
				System.out.println(EntityUtils.toString(entity));

				System.out.println("Foto :" + fotourl + " de Pesssoa Inserida no Azure, com sucesso!");
			}
		} catch (Exception e)
		{
=======
			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
				
				System.out.println("Foto :" + fotourl + " de Pesssoa Inserida no Azure, com sucesso!");
			}
		} catch (Exception e) {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
			System.out.println(e.getMessage());
		}

	}

	/**
	 * treinar - Esse método faz a API treinar, deve ser utilizado depois de inserir
	 * as fotos de uma pessoa.
	 */
<<<<<<< HEAD
	public void treinar()
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/train";
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
=======
	public void treinar() {
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/train";
		HttpClient httpclient = new DefaultHttpClient();
		try {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{ }");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
<<<<<<< HEAD
			if (entity != null)
			{
				System.out.println(EntityUtils.toString(entity));
				System.out.println("Treinando API");
			}
		} catch (Exception e)
		{
=======
			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
				System.out.println("Treinando API");
			}
		} catch (Exception e) {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
			e.printStackTrace();
		}
	}

	/**
<<<<<<< HEAD
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
=======
	 * identificaPessoa - Esse método identifica uma pessoa no grupo a partir do id recebido do método detectaPessoa
	 * @param idFoto - id da foto gerado no detectaPessoa.
	 */
	public void identificaPessoa(String idFoto) {
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/identify";
		HttpClient httpclient = new DefaultHttpClient();
		try {
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{\n" + "    \"personGroupId\": \"grupopi\",\n"
<<<<<<< HEAD
					+ "    \"faceIds\": [\n" + "        \"" + idFoto + "\"\n" + "    ],\n"
=======
					+ "    \"faceIds\": [\n" + "        \""+ idFoto +"\"\n" + "    ],\n"
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
					+ "    \"maxNumOfCandidatesReturned\": 5,\n" + "    \"confidenceThreshold\": 0.5\n" + "}");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
<<<<<<< HEAD
			String json = EntityUtils.toString(response.getEntity());
			retorno = json;
			System.out.println("IdentidicaPessoa, pessoas com este rosto:" + "\n" + json);


		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return retorno;
=======
			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
>>>>>>> 876a43baf63f7de18a407159dadfd3fd0158e19e
	}

}
