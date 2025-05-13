package com.aluracursos.conversor.principal;

import com.aluracursos.conversor.modelos.Conversion;
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
//			Origen
			String menuOrigen = """
					****************************
					¡Sea bienvenida/o al Conversor de Monedas!
					
					Elija el número correspondiente a la moneda de origen (o 0 para salir):
					
					1) Dólar (USD)
					2) Peso argentino (ARS)
					3) Peso colombiano (COP)
					4) Real brasileño (BRL)
					****************************
					""";
			System.out.println(menuOrigen);
			int opcionOrigen = sc.nextInt();
			if (opcionOrigen == 0) {
				break;
			}
			Monedas origen = Monedas.values()[opcionOrigen - 1];

//			Destino
			String menuDestino = """
					****************************
					Elija el número correspondiente a la moneda de destino (o 0 para salir):
					
					1) Dólar (USD)
					2) Peso argentino (ARS)
					3) Peso colombiano (COP)
					4) Real brasileño (BRL)
					****************************
					""";
			System.out.println(menuDestino);
			int opcionDestino = sc.nextInt();
			if (opcionDestino == 0) {
				break;
			}
			Monedas destino = Monedas.values()[opcionDestino - 1];

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
