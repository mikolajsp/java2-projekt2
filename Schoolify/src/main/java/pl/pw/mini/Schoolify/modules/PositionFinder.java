package pl.pw.mini.Schoolify.modules;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.*;

public class PositionFinder {
	private static final String url = "https://api.geoapify.com/v1/geocode/search";

	public static Double[] findLonLat(String request) {
		HttpClient client = HttpClient.newHttpClient();
		StringBuilder sb = new StringBuilder(url);
		try {
			sb.append('?');
			sb.append(URLEncoder.encode("text", "UTF-8"));
			sb.append('=');
			sb.append(URLEncoder.encode(request, "UTF-8"));
			sb.append('&');
			sb.append(URLEncoder.encode("apiKey", "UTF-8"));
			sb.append('=');
			sb.append(URLEncoder.encode(Secret.apiKey, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Shit happens.");
		}
		String uri = sb.toString();
		HttpRequest req = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(URI.create(uri))
				.build();

		HttpResponse<String> response = null;
		try {
			response = client.send(req, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String rep = response.body();
		JSONObject jo;
		jo = new JSONObject(rep);
		JSONArray array = jo.getJSONArray("features");
		String coor = array.getJSONObject(0).getJSONObject("geometry").get("coordinates").toString();
		String coor2 = (String) coor.subSequence(1, coor.length()-2);
		String[] arr = coor2.split(",");
		Double[] res = new Double[2];
		res[0] = Double.parseDouble(arr[0]);
		res[1] = Double.parseDouble(arr[1]);
		return res;
      }
             
	}
