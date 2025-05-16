package com.aluracursos.conversor.principal;

import com.aluracursos.conversor.modelos.Conversion;
import com.aluracursos.conversor.modelos.Menu;
import com.aluracursos.conversor.modelos.Monedas;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) throws IOException, InterruptedException {
		Dotenv dotenv = Dotenv.load();
		String apiKey = dotenv.get("API_KEY");

		Scanner sc = new Scanner(System.in);

		while (true) {
			String bienvenida = """
					******************************************
					¡Sea bienvenida/o al Conversor de Monedas!
					******************************************
					""";
			System.out.println(bienvenida);
			Menu menu = new Menu(sc);

//			Origen
			int opcionOrigen = menu.obtenerOpcionMoneda("origen");
			Monedas origen = Monedas.values()[opcionOrigen - 1];

//			Destino
			int opcionDestino = menu.obtenerOpcionMoneda("destino");
			Monedas destino = Monedas.values()[opcionDestino - 1];

//			Cantidad
			System.out.println("Ingrese la cantidad a convertir (formato: 999,99...): ");
			double cantidad = sc.nextDouble();

			URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + origen + "/" + destino + "/" + cantidad);

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			client.close();

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.enable(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION);
			Conversion conversion = mapper.readValue(response.body(), Conversion.class);

			System.out.println("El valor " + cantidad + " [" + origen + "] corresponde al valor final de => " + conversion.conversion_result() + " [" + destino + "]");
		}
	}
}
