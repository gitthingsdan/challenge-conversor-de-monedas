package com.aluracursos.conversor.modelos;

import java.util.Scanner;

public class Menu {
	private Scanner sc;

	public Menu(Scanner sc) {
		this.sc = sc;
	}

	public int obtenerOpcionMonedaOSalir(String origenODestino) {
		String mensaje = String.format("""
				Elija el número correspondiente a la moneda de %s (o 0 para salir):
				
				1) Dólar (USD)
				2) Peso argentino (ARS)
				3) Peso colombiano (COP)
				4) Real brasileño (BRL)
				""", origenODestino);
		System.out.println(mensaje);
		int opcion = sc.nextInt();
		if (opcion == 0) {
			System.out.println("Ha salido del sistema...");
			System.exit(0);
		}
		return opcion;
	}
}
