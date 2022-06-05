package tiny;

import asint.TinyASint.Programa;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_ascendente.ClaseLexica;
import c_ast_ascendente.UnidadLexica;
import errors.GestionErroresTiny;
import maquinaP.MaquinaP;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import procesamientos.AsignacionEspacio;
import procesamientos.ComprobacionTipos;
import procesamientos.Etiquetado;
import procesamientos.GeneracionCodigo;
import procesamientos.Impresion;
import procesamientos.Vinculacion;

public class Main {
	public static void main(String[] args) throws Exception {

		Programa prog = null;
		if (args.length > 1 && args[1].equals("asc")) {
			prog = ejecuta_ascendente(args[0]);
		} else if (args.length > 1 && args[1].equals("desc")) {
			prog = ejecuta_descendente(args[0]);
		} else {
			System.err.println("Error en los argumentos");
			System.exit(1);
		}

		ejecuta_impresion(prog);
		ejecuta_vinculacion(prog);
		ejecuta_comprobacion_tipos(prog);
		ejecuta_asignacion_espacio(prog);
		ejecuta_etiquetado(prog);
		MaquinaP maquina = new MaquinaP(prog.size, 30, 20, 5);
		prog.procesa(new GeneracionCodigo(maquina));

	}
	
	private static void ejecuta_lexico(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		GestionErroresTiny errores = new GestionErroresTiny();
		UnidadLexica t = (UnidadLexica) alex.next_token();
		while (t.clase() != ClaseLexica.EOF) {
			System.out.println(t);
			t = (UnidadLexica) alex.next_token();
		}
	}
	
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

	private static void ejecuta_impresion(Programa prog) {
		System.out.println();
		System.out.println("|================================|");
		System.out.println("| Iniciando proceso de impresión |");
		System.out.println("|================================|");
		System.out.println();
 		prog.procesa(new Impresion());
		System.out.println();
		System.out.println("|==========================|");
		System.out.println("| Fin proceso de impresión |");
		System.out.println("|==========================|");
		System.out.println();
	}

	private static void ejecuta_vinculacion(Programa prog) {
		System.out.println();
		System.out.println("|==================================|");
		System.out.println("| Iniciando proceso de vinculación |");
		System.out.println("|==================================|");
		System.out.println();
		Vinculacion vinculacion = new Vinculacion();
		prog.procesa(vinculacion);
		if (vinculacion.errorVinculacion()) {
			System.exit(1);
		}
		System.out.println();
		System.out.println("|============================|");
		System.out.println("| Fin proceso de vinculación |");
		System.out.println("|============================|");
		System.out.println();
	}

	private static void ejecuta_comprobacion_tipos(Programa prog) {
		System.out.println();
		System.out.println("|============================================|");
		System.out.println("| Iniciando proceso de comprobación de tipos |");
		System.out.println("|============================================|");
		System.out.println();
		ComprobacionTipos comprobacionTipos = new ComprobacionTipos();
		prog.procesa(comprobacionTipos);
		if (comprobacionTipos.errorComprobacionTipos()) {
			System.exit(1);
		}
		System.out.println();
		System.out.println("|======================================|");
		System.out.println("| Fin proceso de comprobación de tipos |");
		System.out.println("|======================================|");
		System.out.println();
	}

	private static void ejecuta_asignacion_espacio(Programa prog) {
		System.out.println();
		System.out.println("|============================================|");
		System.out.println("| Iniciando proceso de asignación de espacio |");
		System.out.println("|============================================|");
		System.out.println();
		prog.procesa(new AsignacionEspacio());
		System.out.println();
		System.out.println("|======================================|");
		System.out.println("| Fin proceso de asignación de espacio |");
		System.out.println("|======================================|");
		System.out.println();
	}

	private static void ejecuta_etiquetado(Programa prog) {
		System.out.println();
		System.out.println("|=================================|");
		System.out.println("| Iniciando proceso de etiquetado |");
		System.out.println("|=================================|");
		System.out.println();
		Etiquetado etiquetado = new Etiquetado();
		prog.procesa(etiquetado);
		System.out.println();
		System.out.println("|===========================|");
		System.out.println("| Fin proceso de etiquetado |");
		System.out.println("|===========================|");
		System.out.println();
	}
}
