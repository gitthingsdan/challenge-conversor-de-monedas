package com.aluracursos.conversor.principal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class Principal {
	public static void main(String[] args) {
		URI direccion = URI.create("https://v6.exchangerate-api.com/v6/281a2b70643c0c8bae500d19/pair/EUR/GBP/7");

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(direccion)
				.build();
	}
}
