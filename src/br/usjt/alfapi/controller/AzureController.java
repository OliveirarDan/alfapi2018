package br.usjt.alfapi.controller;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.usjt.alfapi.model.entity.Pessoa;

@RestController
// @Controller
public class AzureController
{
	// chave da azure
	private static final String subscriptionKey = "8fe8eda9d154459f91b30bc8165e4f17";

	// url do servidor que está cadastrada a chave acima

	// imagem para teste
	private static final String imagem = "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";

	// @RequestMapping(method = RequestMethod.GET, value = "rest/pessoa/faces")
	public String detectaFace()
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";

		HttpClient httpclient = new DefaultHttpClient();
		String jsonString = null;
		try
		{
			URIBuilder builder = new URIBuilder(endPoint);

			builder.setParameter("returnFaceId", "true");
			builder.setParameter("returnFaceLandmarks", "false");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);

			// Request headers.
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

			// Request body.
			StringEntity reqEntity = new StringEntity(imagem);
			request.setEntity(reqEntity);

			// Execute the REST API call and get the response entity.
			HttpResponse response = httpclient.execute(request);
			org.apache.http.HttpEntity entity = response.getEntity();

			if (entity != null)
			{
				// Format and display the JSON response.
				/*
				 * System.out.println("REST Response:\n");
				 */
				jsonString = EntityUtils.toString(entity).trim();
				if (jsonString.charAt(0) == '[')
				{
					JSONArray jsonArray = new JSONArray(jsonString);

					/* System.out.println(jsonArray.toString(2)); */
				} else if (jsonString.charAt(0) == '{')
				{
					JSONObject jsonObject = new JSONObject(jsonString);
					/* System.out.println(jsonObject.toString(2)); */
				} else
				{
					/* System.out.println(jsonString); */
				}
			}
			return jsonString;

		} catch (Exception e)
		{
			// Display error message.
			System.out.println(e.getMessage());
		}
		return jsonString;
	}

	// Método InserePessoa - Insere pessoa no grupopi
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
					System.out.println(codAzure); //só para teste
				}
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return codAzure;
	}

	// Método InsereFotoPessoaUrl - Insere foto em pessoa através do URL

	public void insereFotoPessoaUrl(String idPessoa, String urlFoto, String dadosUsuario)
	{
		String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons/92cdabd5-9401-4f97-a834-c6d9f5ca92f9/persistedFaces";

		HttpClient httpclient = new DefaultHttpClient();

		try
		{
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

			builder.setParameter("userData", "{" + dadosUsuario + "}");
			builder.setParameter("targetFace", "");

			// Request body
			StringEntity reqEntity = new StringEntity("{\r\n"
					+ "    \"url\": \"https://media.licdn.com/dms/image/C5603AQGIZjaaEEZR4Q/profile-displayphoto-shrink_200_200/0?e=1543449600&v=beta&t=0KcGqHaAeBqmmfMBLVMB9XauA3W7tRaOQ5wlKj01kKI\"\r\n"
					+ "}");
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

	}
	
	//Método Treinar
	public void treinar(){
		String endPoint = "https://brazil.outh.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/train";

		HttpClient httpclient = new DefaultHttpClient();

		try {
			URIBuilder builder = new URIBuilder(endPoint);
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

			// Request body
			StringEntity reqEntity = new StringEntity("{\r\n"
					+ "    \"url\": \"https://media.licdn.com/dms/image/C5603AQGIZjaaEEZR4Q/profile-displayphoto-shrink_200_200/0?e=1543449600&v=beta&t=0KcGqHaAeBqmmfMBLVMB9XauA3W7tRaOQ5wlKj01kKI\"\r\n"
					+ "}");
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null){
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e){
			e.printStackTrace();
		}

	}
	
	//Método identificar
	
	public void identificar(){
		
		String endPoint = "https://brazil.outh.api.cognitive.microsoft.com/face/v1.0/identify";

		HttpClient httpclient = new DefaultHttpClient();

		try {
			URIBuilder builder = new URIBuilder(endPoint);
			
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

			// Request body
			StringEntity reqEntity = new StringEntity("{\r\n"
					+ "    \"url\": \"https://media.licdn.com/dms/image/C5603AQGIZjaaEEZR4Q/profile-displayphoto-shrink_200_200/0?e=1543449600&v=beta&t=0KcGqHaAeBqmmfMBLVMB9XauA3W7tRaOQ5wlKj01kKI\"\r\n"
					+ "}");
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null){
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

	}
	
	

}
