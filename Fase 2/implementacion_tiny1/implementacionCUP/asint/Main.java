package implementacionCUP.asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import implementacionCUP.alex.AnalizadorLexicoTiny;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
	 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
	 AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);
	 //asint.setScanner(alex);
	 asint.debug_parse();
 }
}   
   
