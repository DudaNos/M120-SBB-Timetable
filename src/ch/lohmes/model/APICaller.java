package ch.lohmes.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class APICaller {

	private HttpURLConnection con;
	
	public APICaller() {
		
	}
	
	public String getConnections(String from, String to) {

		try {
			URL url = new URL("http://transport.opendata.ch/v1/connections?from="+from+"&to="+to);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			System.out.println(con.getURL());
			
			JSONObject json = new JSONObject(performApiCall());
			JSONArray connections = json.getJSONArray("connections");
			System.out.println(connections.toString());
			JSONObject indexJson = connections.getJSONObject(0);
			System.out.println(indexJson.toString());
			JSONObject fromJson = indexJson.getJSONObject("from");
			System.out.println(fromJson.toString());
			String time = fromJson.getString("departure");
			
			JSONArray products = indexJson.getJSONArray("products");
			
			String zugName = products.getString(0);

			con.disconnect();

			return zugName + " " + time;
			
		} catch (IOException e) {
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
		
		return response;
	}
	
}
