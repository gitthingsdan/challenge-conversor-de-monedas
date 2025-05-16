package com.aluracursos.conversor.principal;

import com.aluracursos.conversor.modelos.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
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
			int opcionOrigen = menu.obtenerOpcionMonedaOSalir("origen");
			Monedas origen = Monedas.values()[opcionOrigen - 1];

//			Destino
			int opcionDestino = menu.obtenerOpcionMonedaOSalir("destino");
			Monedas destino = Monedas.values()[opcionDestino - 1];

//			Cantidad
			System.out.println("Ingrese la cantidad a convertir (formato: 999,99...): ");
			double cantidad = sc.nextDouble();

			URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + origen + "/" + destino + "/" + cantidad);

			ClienteAPI cliente = new ClienteAPI();
			HttpResponse<String> respuesta = cliente.obtenerRespuesta(direccion);

			ObjectMapper mapper = new CustomObjectMapper();
			Conversion conversion = mapper.readValue(respuesta.body(), Conversion.class);

			System.out.println("El valor " + cantidad + " [" + origen + "] corresponde al valor final de => " + conversion.conversion_result() + " [" + destino + "]");
		}
	}
}
