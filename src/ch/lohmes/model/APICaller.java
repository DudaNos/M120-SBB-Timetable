package ch.lohmes.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICaller {

	private HttpURLConnection con;
	
	public APICaller() {
		
	}
	
	public void getConnections(String from, String to) {

		try {
			URL url = new URL("http://transport.opendata.ch/v1/connections?from="+from+"&to="+to);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			System.out.println(con.getURL());
			
			performApiCall();
		
			con.disconnect();
			
		} catch (IOException e) {
			e.printStackTrace();
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
		
		System.out.println(content.toString());
		
		return content.toString();
	}
	
}
