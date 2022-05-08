package errors;

import c_ast_ascendente.UnidadLexica;

public class GestionErroresTiny {
	public void errorLexico(int fila, int col, String lexema) {
		System.out.println("ERROR fila " + fila + "," + col + ": Caracter inexperado: " + lexema);
		System.exit(1);
	}

   public void errorSintactico(UnidadLexica unidadLexica) {
     System.out.print("ERROR fila "+ unidadLexica.fila()+","+unidadLexica.columna()+
                              ": Elemento inexperado "+unidadLexica.value);
     System.exit(1);
   }

	public void errorFatal(Exception e) {
		System.out.println(e);
		e.printStackTrace();
		System.exit(1);
	}
}
