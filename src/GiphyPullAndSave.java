	import java.io.BufferedReader;
	import java.io.FileOutputStream;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.util.LinkedList;
	import java.util.Queue;
	import java.util.Scanner;
	import java.util.StringTokenizer;

	import org.json.*; 

public class GiphyPullAndSave {
	





		private static final String GET = "GET";
		private final static String API_KEY = "&api_key=dc6zaTOxFJmzC";   
		private final static String PREFIX = "http://api.giphy.com/v1/gifs/search?q="; 
		static HttpURLConnection connection = null; 
		private final static String PHOTO_NAME =  "id" ;
		private final static String PHOTO_URL = "embed_url"; 
		private final static String EXTENSION = ".gif"; 
		
		public static String buildURL(Queue<String> searchItems){
			StringBuilder toRet = new StringBuilder(); 
			toRet.append(PREFIX);  
			
			while(!searchItems.isEmpty() && searchItems.size()!= 1 ){
				toRet.append(searchItems.poll()); 
				toRet.append("+"); 
			}
			
			toRet.append(searchItems.poll());  
			toRet.append(API_KEY); 
			return toRet.toString(); 
		}
		
		public static Queue<String> tokenize( String toToken){
			Queue<String> toRet = new LinkedList<String>();   
			StringTokenizer st = new StringTokenizer(toToken);
			while(st.hasMoreTokens()){
				toRet.add(st.nextToken()); 
			}
			return toRet; 
		}
		
		
		public static String getJsonResponse( String url) throws Exception{
			URL myUrl = new URL(url);
			connection = (HttpURLConnection)myUrl.openConnection();
			connection.setRequestMethod(GET);
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			System.out.println(response.toString());
			return response.toString(); 
		}
		
		
		
		
		
		public static void saveAllImagesToFile( String json ) throws Exception{
			
			JSONObject obj = new JSONObject(json); 
			JSONArray data = obj.getJSONArray("data"); 
			
			for(int looper = 0 ; looper < data.length() ; looper ++ ){
				JSONObject temp = data.getJSONObject(looper); 
				String imageURL = temp.getString(PHOTO_URL); 
				String destinationFile = temp.getString(PHOTO_NAME)+ EXTENSION; 
				saveImage(imageURL, destinationFile);
			}
			
		}
		
		
		public static void saveImage(String imageUrl, String destinationFile) throws Exception {
			
			
		}
		
		public static void main (String[] args){
			Scanner ob=new Scanner(System.in);
			
			System.out.println("enter your search query"); 
			String search = ob.nextLine();
			while(true){
				String searchUrl = buildURL(tokenize(search)); 
				System.out.println(searchUrl); 
				try {
					getJsonResponse(searchUrl);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				System.out.println("enter your search query"); 
				
				search = ob.nextLine();
				
				if (search == "0") break; 
				
			}
			
			ob.close();
		}
		
		
		
	}


