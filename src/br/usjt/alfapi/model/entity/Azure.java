package br.usjt.alfapi.model.entity;

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

public class Azure {

	// Atributos
	private String codAzure;
	private ArrayList<String> fotos;
	

	// Atributos AZURE
	//chave da azure
	private static final String subscriptionKey = "8fe8eda9d154459f91b30bc8165e4f17";
	
	//uriBaseDetect
	private static final String uriBaseDetect = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
		
	//faceAttributesDetect
	private static final String faceAttributesDetect =
	        "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";

	
	
	// Construtores
	public Azure() {
		// TODO Auto-generated constructor stub
	}
	
	public Azure(String codAzure) {
		this.codAzure = codAzure;
	}

	// Métodos Get e Set
	public String getCodAzure() {
		return codAzure;
	}

	public void setCodAzure(String codAzure) {
		this.codAzure = codAzure;
	}

	public ArrayList<String> getFotos() {
		return fotos;
	}

	public void setFotos(ArrayList<String> fotos) {
		this.fotos = fotos;
	}

	// Método ToString
	@Override
	public String toString() {
		return "Azure [codAzure=" + codAzure + ", fotos=" + fotos + "]";
	}

	
	//Método detectaUrl - Detecta foto através de url
	public void detectaPessoaUrl(String linkFoto) {
		String imageWithFaces ="{\"url\":\""+ linkFoto +"\"}";
		
		HttpClient httpclient = new DefaultHttpClient();

        try
        {
        	
            URIBuilder builder = new URIBuilder(uriBaseDetect);

            // Request parameters. All of them are optional.
            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnGender", "true");
            builder.setParameter("returnFaceLandmarks", "false");
            builder.setParameter("returnFaceAttributes", faceAttributesDetect);

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
        	//URI uri = new URI(uriBase+"?returnFaceId=true&returnFaceLandmarks=false&returnGender=true");
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

            if (entity != null)
            {
                // Format and display the JSON response.
                System.out.println("REST Response:\n");

                String jsonString = EntityUtils.toString(entity).trim();
                if (jsonString.charAt(0) == '[') {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    System.out.println(jsonArray.toString(2));
                }
                else if (jsonString.charAt(0) == '{') {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    System.out.println(jsonObject.toString(2));
                } else {
                    System.out.println(jsonString);
                }
            }
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
    
	}
	
	
	
	//Método InserePessoa - Insere pessoa no grupopi
	public void inserePessoa (String nome, String dadosUsuario) {
		HttpClient httpclient = new DefaultHttpClient();
		
        try
        {
            URIBuilder builder = new URIBuilder("https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons");


            URI uri = builder.build();
            
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);


            // Request body
            StringEntity reqEntity = new StringEntity("{\r\n" + 
            		"    \"name\": \"" + nome +"\",\r\n" + 
            		"    \"userData\": \"" + dadosUsuario +"\"\r\n" + 
            		"}\r\n" + 
            		"");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(response.getEntity());
            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
		
	
	//Método InsereFotoPessoaUrl - Insere foto em pessoa através do URL
	
	public void insereFotoPessoaUrl (String idPessoa, String urlFoto, String dadosUsuario ) {
		HttpClient httpclient = new DefaultHttpClient();

        try
        {
            URIBuilder builder = new URIBuilder("https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/persongroups/grupopi/persons/92cdabd5-9401-4f97-a834-c6d9f5ca92f9/persistedFaces");

            builder.setParameter("userData", "{"+ dadosUsuario +"}");
            builder.setParameter("targetFace", "");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);


            // Request body
            StringEntity reqEntity = new StringEntity("{\r\n" + 
            		"    \"url\": \"https://media.licdn.com/dms/image/C5603AQGIZjaaEEZR4Q/profile-displayphoto-shrink_200_200/0?e=1543449600&v=beta&t=0KcGqHaAeBqmmfMBLVMB9XauA3W7tRaOQ5wlKj01kKI\"\r\n" + 
            		"}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            
            
            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
		
	}
		
		
	
}
