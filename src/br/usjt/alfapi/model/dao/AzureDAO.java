package br.usjt.alfapi.model.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.usjt.alfapi.model.entity.Pessoa;
import br.usjt.alfapi.model.service.PessoaService;

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
	 * identificaPessoa - Esse método identifica uma pessoa no grupo a partir do id
	 * recebido do método detectaPessoa
	 * 
	 * @param idFoto
	 *            - id da foto gerado no detectaPessoa.
	 */
	public String identificaPessoa(String foto)
	{
		String retorno = null;
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/identify";
		String personid = null;
		double confidence;
		
		Pessoa pessoa = new Pessoa();
		ArrayList<Pessoa> pessoas = new ArrayList<>();

		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{\n" 
					+ "    \"personGroupId\": \"grupopii\",\n"
					+ "    \"faceIds\": [\n" 
					+ "        \"" 
					+ foto + "\"\n" 
					+ "    ],\n"
					+ "    \"maxNumOfCandidatesReturned\": 3,\n" 
					+ "    \"confidenceThreshold\": 0.5\n" + "}");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			retorno = json;
			System.out.println("IdentidicaPessoa, pessoas com este rosto:" + "\n" + json);
			
			
			// AQUI É CRIADO UM ARRAY JSON COM AS INFORMAÇÕES DOS CANDIDADOS
			JSONArray jsonArray = new JSONArray(json);
			JSONArray candidates = jsonArray.getJSONObject(0).getJSONArray("candidates");
			System.out.println("Candidatos:" + candidates.toString());
			System.out.println("Qtd de candidatos" + candidates.length());
			
			
			//ITERAÇÃO PARA PERCORRER O VETOR, SALVAR O CONFIDENCE (PROBABILIDADE DE SER A PESSOA) E CAPTAR O PERSON ID PARA USAR NO MÉTODO buscarPessoaPeloPersonId
	        for(int i = 0; i<candidates.length(); i++) {
	        	
	        	//aqui é capturado o valor de person id
	        	personid = candidates.getJSONObject(i).getString("personId");
	        	System.out.println("Personid " + i + ": "+ personid);
	        	
	        	//aqui é capturado o valor de confidence e gravado no objeto pessoa (veja a bean)
	        	confidence = candidates.getJSONObject(i).getDouble("confidence");
	            pessoa.setConfidence(confidence);
	            System.out.println("Confidence " + i + ": "+ pessoa.getConfidence());
	            
	            //aqui ocorre o problema, o pessoaService fica null, segundo o Bossini é um problema naa injeção de dependencia
	            pessoa = pessoaDAO.buscarPessoaPeloPersonId(personid);
	            System.out.println("Pessoa: " + pessoa);
	            
	            //AQUI A PESSOA RETORNADA DEVE SER INSERIDA EM UM ARRAY DE PESSOAS PARA RETORNAR NO ENDPOINT
	            pessoas.add(pessoa);            
	        }
	        
	        

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return retorno;
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
			// builder.setParameter("userData", "{" + dadosUsuario + "}");
			builder.setParameter("targetFace", "");
			// Request body

			// FileEntity reqEntity = new FileEntity(file,
			// ContentType.APPLICATION_OCTET_STREAM);
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

	/*
	 * =============================================================================
	 * ==============
	 * =============================================================================
	 * ============= =================== =========================
	 * =================== MÉTODOS TEMPORARIOS, NAO CONSIDERAR
	 * ========================= =================== =========================
	 * =============================================================================
	 * =============
	 * =============================================================================
	 * =============
	 */

	public void insereFotoPessoaLocal(String codAzure, String dadosUsuario, String fotourl)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopii/persons/"
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
				System.out.println("Foto :" + fotourl + " de Pesssoa Inserida no Azure, com sucesso!");
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	public void insereFotoPessoaFile(String codAzure, String dadosUsuario, File file)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopii/persons/"
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

			FileEntity reqEntity = new FileEntity(file, ContentType.APPLICATION_OCTET_STREAM);
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if (entity != null)
			{
				System.out.println(EntityUtils.toString(entity));

				System.out.println("Foto :" + file.getName() + " de Pesssoa Inserida no Azure, com sucesso!");
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public String detectaPessoaFile(File file)
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
				System.out.println(entity.toString());
			}
		} catch (Exception e)
		{
			// Display error message.
			System.out.println(e.getMessage());
		}
		return idFoto;
	}

	public String detectaPessoaUrl(String linkFoto)
	{
		String retorno = null;
		String endPointDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		String imageWithFaces = linkFoto;
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPointDetect);
			// Request parameters. All of them are optional.
			builder.setParameter("returnFaceId", "true");
			builder.setParameter("returnGender", "false");
			builder.setParameter("returnFaceLandmarks", "false");

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

			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return retorno;
	}

	
	
	
	
	
	//--------------------------- testando identifica pessoa -------------------------
	

	/**
	 * identificaPessoa - Esse método identifica uma pessoa no grupo a partir do id
	 * recebido do método detectaPessoa
	 * 
	 * @param idFoto
	 *            - id da foto gerado no detectaPessoa.
	 */
	public String identificaPessoaTeste(String foto)
	{
	
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/identify";
		String respotaTratada = null;
		HttpClient httpclient = new DefaultHttpClient();
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
			// Request body
			StringEntity reqEntity = new StringEntity("{\n" 
					+ "    \"personGroupId\": \"grupopii\",\n"
					+ "    \"faceIds\": [\n" 
					+ "        \"" 
					+ foto + "\"\n" 
					+ "    ],\n"
					+ "    \"maxNumOfCandidatesReturned\": 1,\n" 
					+ "    \"confidenceThreshold\": 0.5\n" + "}");
			request.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(response.getEntity());
			
		
			
			if (response != null) {
				String jsonString = json;
				JSONArray jsonArray = new JSONArray(jsonString);
				JSONArray candidates = jsonArray.getJSONObject(0).getJSONArray("candidates");
				for (int i = 0; i < candidates.length(); i++) {
					respotaTratada = candidates.getJSONObject(i).getString("personId");
					
				}
				
			}
			return respotaTratada;

		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return respotaTratada;
	}
	
}