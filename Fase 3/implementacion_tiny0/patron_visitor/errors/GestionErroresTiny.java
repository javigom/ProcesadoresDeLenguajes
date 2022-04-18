package errors;

import alex.ClaseLexica;

public class GestionErroresTiny {
	public void errorLexico(int fila, int col, String lexema) {
		System.out.println("ERROR fila " + fila + "," + col + ": Caracter inexperado: " + lexema);
		System.exit(1);
	}

	public void errorSintactico(int fila, int col, ClaseLexica encontrada, ClaseLexica... esperadas) {
		System.out.print("ERROR fila " + fila + "," + col + ": Encontrado " + encontrada + " Se esperada: ");
		for (ClaseLexica esperada : esperadas)
			System.out.print(esperada + " ");
		System.out.println();
		System.exit(1);
	}

	public void errorFatal(Exception e) {
		System.out.println(e);
		e.printStackTrace();
		System.exit(1);
	}
}
