package ch.lohmes.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

public class APICaller {
	
	private HttpURLConnection con;
	
	public APICaller() {
		
	}
	
	public List<String> getConnections(String from, String to) {

		var trains = new LinkedList<String>();
		
		if(from ==  null || to == null) {
			System.out.println("No input");
			return null;
		}
		
		try {
			URL url = new URL("http://transport.opendata.ch/v1/connections?from="+from+"&to="+to+"&limit=16");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			System.out.println(con.getURL());
			
			String result = performApiCall();
			
			if(result == null) {
				System.out.println("No response from the server");
				return null;
			}
			
			JSONObject json = new JSONObject(result);
			// Get all connections from json
			JSONArray connections = json.getJSONArray("connections");
			
			for(int i = 0; i < connections.length(); i++) {
				
				// Get one object off the json array
				JSONObject indexJson = connections.getJSONObject(i);
				
				// Get departure location
				JSONObject fromJson = indexJson.getJSONObject("from");
				System.out.println(fromJson);
				
				// Get Platform number
				String platform = fromJson.getString("platform");
				
				// Get departure time
				String timeStamp = fromJson.getString("departure");
				
				SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		        parser.setTimeZone(TimeZone.getTimeZone("UTC"));
		        Date parsed = parser.parse(timeStamp);
		        
		        String time = new SimpleDateFormat("H:mm").format(parsed);
				
				// Inside the products array there are all the trains for the connection
				JSONArray products = indexJson.getJSONArray("products");
				// We only require the first in the array (the train leaving from our location)
				String zugName = products.getString(0);
				
				trains.add("Zug: " + zugName + "\t\tAbfahrt: " + time + "\t\tGleis: " + platform);
			}

			con.disconnect();
			
			return trains;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String performApiCall() throws IOException {
		int status = con.getResponseCode();
		System.out.println("Status Code: " + status);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		
		in.close();
		
		String response = content.toString();
		
		System.out.println(response);
		return response;
	}
	
}
