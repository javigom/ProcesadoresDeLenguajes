package tiny;

import asint.TinyASint.Programa;
import c_ast_ascendente.AnalizadorLexicoTiny;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import procesamientos.Impresion;

public class Main {
	public static void main(String[] args) throws Exception {

		Programa prog = null; 
		if (args[1].equals("asc")) {
			prog = ejecuta_ascendente(args[0]);
			prog.procesa(new Impresion());
		}

		else if (args[1].equals("desc")) {
			prog = ejecuta_descendente(args[0]);
			prog.procesa(new Impresion());
		}
		
		else 
			System.err.println("Args error");
		
	}

	/*
	 * private static void ejecuta_lexico(String in) throws Exception { Reader input
	 * = new InputStreamReader(new FileInputStream(in)); AnalizadorLexico alex = new
	 * AnalizadorLexico(input); GestionErrores errores = new GestionErrores();
	 * UnidadLexica t = (UnidadLexica) alex.next_token(); while (t.clase() !=
	 * ClaseLexica.EOF) { System.out.println(t); t = (UnidadLexica)
	 * alex.next_token(); } }
	 */

	private static Programa ejecuta_ascendente(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		c_ast_ascendente.ConstructorAST constructorast = new c_ast_ascendente.ConstructorAST(alex);
		return (Programa) constructorast.parse().value;
	}

	private static Programa ejecuta_descendente(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		c_ast_descendente.ConstructorAST constructorast = new c_ast_descendente.ConstructorAST(input);
		return constructorast.PROGRAMAp();
	}
}
