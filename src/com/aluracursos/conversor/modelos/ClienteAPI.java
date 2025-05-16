package com.aluracursos.conversor.modelos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteAPI {
	public ClienteAPI() {
	}

	public HttpResponse<String> obtenerRespuesta(URI direccion) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
		try (client) {
			HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
			return respuesta;
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
