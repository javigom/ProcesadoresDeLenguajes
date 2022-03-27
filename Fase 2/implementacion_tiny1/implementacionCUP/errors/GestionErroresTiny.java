package errors;

import alex.UnidadLexica;

public class GestionErroresTiny {
   public void errorLexico(int fila, String lexema) {
     System.out.println("ERROR fila "+fila+": Caracter inexperado: "+lexema); 
     System.exit(1);
   }  
   public void errorSintactico(UnidadLexica unidadLexica) {
     System.out.println("ERROR fila "+unidadLexica.fila()+", columna "+unidadLexica.columna()+" : Elemento inexperado "+unidadLexica.value);
     System.exit(1);
   }
}
