package br.usjt.alfapi.controller;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.alfapi.model.entity.Endereco;
import br.usjt.alfapi.model.entity.Pessoa;
import br.usjt.alfapi.model.service.EnderecoService;
import br.usjt.alfapi.model.service.PessoaService;

@RestController
// @Controller
public class FacesController
{
	//chave da azure
	private static final String subscriptionKey = "8fe8eda9d154459f91b30bc8165e4f17";
	
	//url do servidor que está cadastrada a chave acima
	private static final String endPoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";
	
	// imagem para teste
	private static final String imagem = "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";
	//possiveis retornos da api DetectaFaces, mas nem precisamos usar.
	private static final String faceAttributes = "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";

	/*
	 * Este método quando chamado via Postman está funcionando, fiz somente para ir implementando as coisas aos poucos
	 * até entender melhor onde esta acontecendo o problema.
	*/	
	@RequestMapping(method = RequestMethod.GET, value = "rest/pessoa/teste")
	public @ResponseBody String teste()
	{
		return detectaFace();
	}
	
	/*
	 * Este método é só um teste igual ao que o professor fez, mas me baseei na documentacao do site da Azure.
	 * Não consegui fazer funcionar e não consegui debuggar a api, nesse caso fui criando o método acima pra ver se implementando 
	 * aos poucos encontrava o problema.
	*/
	//@RequestMapping(method = RequestMethod.GET, value = "rest/pessoa/faces")
	public String detectaFace()
	{
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
/*				System.out.println("REST Response:\n");
*/
				jsonString = EntityUtils.toString(entity).trim();
				if (jsonString.charAt(0) == '[')
				{
					JSONArray jsonArray = new JSONArray(jsonString);
					
					/*System.out.println(jsonArray.toString(2));*/
				} else if (jsonString.charAt(0) == '{')
				{
					JSONObject jsonObject = new JSONObject(jsonString);
		/*			System.out.println(jsonObject.toString(2));*/
				} else
				{
					/*System.out.println(jsonString);*/
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

}
