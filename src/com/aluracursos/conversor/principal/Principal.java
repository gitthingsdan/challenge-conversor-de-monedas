package com.aluracursos.conversor.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Principal {
	public static void main(String[] args) throws IOException, InterruptedException {
		String apiKey = "281a2b70643c0c8bae500d19";
		URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/EUR/GBP/7");

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(direccion)
				.build();
		HttpResponse<String> response = client
				.send(request, HttpResponse.BodyHandlers.ofString());

		String json = response.body();
	}
}
