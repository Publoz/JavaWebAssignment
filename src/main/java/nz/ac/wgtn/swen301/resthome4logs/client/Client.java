package nz.ac.wgtn.swen301.resthome4logs.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class Client {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.exit(1);
		} else if(!( args[0].equals("excel") || args[0].equals("csv"))) {
			System.exit(1);
		}


		URI uri = null;
		try {
			String path;

			if(args[0].equals("csv")) {
				path = "/resthome4logs/statscsv";
			} else {
				path = "/resthome4logs/statsxls";
			}


			uri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost:8080")
					.setPath(path)
					.build();
		} catch (URISyntaxException e) {
			System.exit(1);
		}

		// create and execute the request
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(uri);
		HttpResponse response = null;
		
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e1) {
			System.out.println("Server unavaliable");
			System.exit(1);
		} catch (IOException e1) {
			System.out.println("Server unavaliable");
			System.exit(1);
		}
		
		
		
		if(response == null || response.getStatusLine().getStatusCode() != 200) {
			System.out.println("Server unavaliable");
			System.exit(1);
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(args[1]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getEntity().writeTo(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
