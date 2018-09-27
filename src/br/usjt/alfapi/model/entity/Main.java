package br.usjt.alfapi.model.entity;




import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
public class Main {

	//chave da azure
	private static final String subscriptionKey = "8fe8eda9d154459f91b30bc8165e4f17";
	
	private static final String uriBase =
			"https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";

	    private static final String imageWithFaces =
	        "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";

	    private static final String faceAttributes =
	        "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";

	
	
		public static void main(String[] args)
	    {
	        HttpClient httpclient = new DefaultHttpClient();

	        try
	        {
	        	/*
	            URIBuilder builder = new URIBuilder(uriBase);

	            // Request parameters. All of them are optional.
	            builder.setParameter("returnFaceId", "true");
	            builder.setParameter("returnFaceLandmarks", "false");
	            builder.setParameter("returnFaceAttributes", faceAttributes);

	            // Prepare the URI for the REST API call.
	            URI uri = builder.build();*/
	        	URI uri = new URI(uriBase+"?returnFaceId=true&returnFaceLandmarks=false");
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
	
	
	

}