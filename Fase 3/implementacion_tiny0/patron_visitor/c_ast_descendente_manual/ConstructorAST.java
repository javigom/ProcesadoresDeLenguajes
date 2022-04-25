package c_ast_descendente_manual;

import java.io.IOException;
import java.io.Reader;
import asint.TinyASint.Programa;
import asint.TinyASint.Tipo;
import errors.GestionErroresTiny;
import asint.TinyASint.Declaracion;
import asint.TinyASint.Declaraciones;
import asint.TinyASint.Exp;
import asint.TinyASint.Instruccion;
import asint.TinyASint.Instrucciones;
import semops.SemOps;

public class ConstructorAST {
	private UnidadLexica anticipo;
	private AnalizadorLexicoTiny alex;
	private GestionErroresTiny errores;
	private SemOps sem;

	public ConstructorAST(Reader input) throws IOException {
		errores = new GestionErroresTiny();
		alex = new AnalizadorLexicoTiny(input);
		alex.fijaGestionErrores(errores);
		sigToken();
		sem = new SemOps();
	}

	public Programa PROGRAMAp() {
		Programa prog = PROGRAMA();
		empareja(ClaseLexica.EOF);
		return prog;
	}

	private Programa PROGRAMA() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			Declaraciones decs = DECLARACIONES();
			empareja(ClaseLexica.DAMP);
			Instrucciones ins = INSTRUCCIONES();
			return sem.Programa(decs, ins);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}

	private Declaraciones DECLARACIONES() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			Declaracion dec = DECLARACION();
			return RDEC(sem.decs_una(dec));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}

	private Declaraciones RDEC(Declaraciones decsh) {
		switch (anticipo.clase()) {
		case DAMP:
			return decsh;
		case PCOMA:
			empareja(ClaseLexica.PCOMA);
			Declaracion dec = DECLARACION();
			return RDEC(sem.decs_muchas(decsh, dec));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DAMP,
					ClaseLexica.PCOMA);
			return null;
		}
	}

	private Declaracion DECLARACION() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			Tipo tipo = TIPO();
			UnidadLexica tkid = anticipo;
			empareja(ClaseLexica.ID);
			return sem.declaracion(tipo, sem.str(tkid.lexema(), tkid.fila(), tkid.columna()));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}

	private Tipo TIPO() {
		switch (anticipo.clase()) {
		case BOOL:
			UnidadLexica tkbool = anticipo;
			empareja(ClaseLexica.BOOL);
			return sem.bool_cons(sem.str("bool", tkbool.fila(), tkbool.columna()));
		case INT:
			UnidadLexica tkint = anticipo;
			empareja(ClaseLexica.INT);
			return sem.int_cons(sem.str("int", tkint.fila(), tkint.columna()));
		case REAL:
			UnidadLexica tkreal = anticipo;
			empareja(ClaseLexica.REAL);
			return sem.real_cons(sem.str("real", tkreal.fila(), tkreal.columna()));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}

	private Instrucciones INSTRUCCIONES() {
		switch (anticipo.clase()) {
		case ID:
			Instruccion in = INSTRUCCION();
			return RINS(sem.insts_una(in));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID);
			return null;
		}
	}

	private Instrucciones RINS(Instrucciones insh) {
		switch (anticipo.clase()) {
		case PCOMA:
			empareja(ClaseLexica.PCOMA);
			Instruccion in = INSTRUCCION();
			return RINS(sem.insts_muchas(insh, in));
		case EOF:
			return insh;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PCOMA,
					ClaseLexica.EOF);
			return null;
		}
	}

	private Instruccion INSTRUCCION() {
		switch (anticipo.clase()) {
		case ID:
			UnidadLexica tkid = anticipo;
			empareja(ClaseLexica.ID);
			empareja(ClaseLexica.IGUAL);
			Exp exp0 = E0();

			return sem.instruccion(sem.str(tkid.lexema(), tkid.fila(), tkid.columna()), exp0);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID);
			return null;
		}
	}

	private Exp E0() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp1 = E1();
			return RE0(exp1);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}

	private Exp RE0(Exp exph) {
		switch (anticipo.clase()) {
		case MAS:
		case MENOS:
			char op = Op0();
			Exp exp1 = E1();
			return RE0(sem.exp(op, exph, exp1));
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MAS,
					ClaseLexica.MENOS, ClaseLexica.ID);
			return null;
		}
	}

	private Exp E1() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp2 = E2();
			return RE1(exp2);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}

	private Exp RE1(Exp exph) {
		switch (anticipo.clase()) {
		case AND:
			Exp exp2 = E2();
			empareja(ClaseLexica.AND);
			return RE1(sem.and_cons(exph, exp2));
		case OR:
			Exp exp21 = E2();
			empareja(ClaseLexica.OR);
			return RE1(sem.or_cons(exph, exp21));
		case MAS:
		case MENOS:
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.AND,
					ClaseLexica.OR, ClaseLexica.MAS, ClaseLexica.MENOS);
			return null;
		}
	}

	private Exp E2() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp3 = E3();
			return RE2(exp3);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}

	private Exp RE2(Exp exph) {
		switch (anticipo.clase()) {
		case MENOR:
			Exp exp3 = E3();
			empareja(ClaseLexica.MENOR);
			return RE2(sem.menor(exph, exp3));
		case MAYOR:
			Exp exp31 = E3();
			empareja(ClaseLexica.MAYOR);
			return RE2(sem.mayor(exph, exp31));
		case MENOR_IGUAL:
			Exp exp32 = E3();
			empareja(ClaseLexica.MENOR_IGUAL);
			return RE2(sem.menorIgual(exph, exp32));
		case MAYOR_IGUAL:
			Exp exp33 = E3();
			empareja(ClaseLexica.MENOR_IGUAL);
			return RE2(sem.mayorIgual(exph, exp33));
		case DIF:
			Exp exp34 = E3();
			empareja(ClaseLexica.DIF);
			return RE2(sem.distinto(exph, exp34));
		case DIGUAL:
			Exp exp35 = E3();
			empareja(ClaseLexica.DIGUAL);
			return RE2(sem.igual(exph, exp35));
		case AND:
		case MAS:
		case MENOS:
		case OR:
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DIF,
					ClaseLexica.DIGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL,
					ClaseLexica.MENOR, ClaseLexica.AND, ClaseLexica.MAS, ClaseLexica.MENOS, ClaseLexica.OR);
			return null;
		}
	}

	private Exp E3() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp4 = E4();
			return RE3(exp4);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}

	private Exp RE3(Exp exph) {
		switch (anticipo.clase()) {
		case DIV:
		case POR:
			char op = OPBN3();
			Exp exp4 = E4();
			return sem.exp(op, exph, exp4);
		case AND:
		case DIGUAL:
		case DIF:
		case MAS:
		case MENOS:
		case MAYOR_IGUAL:
		case MENOR_IGUAL:
		case MAYOR:
		case MENOR:
		case OR:
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DIV,
					ClaseLexica.POR, ClaseLexica.AND, ClaseLexica.DIF, ClaseLexica.DIGUAL, ClaseLexica.MAS,
					ClaseLexica.MENOS, ClaseLexica.MAYOR_IGUAL, ClaseLexica.MENOR_IGUAL, ClaseLexica.OR);
			return null;
		}
	}

	private Exp E4() {
		switch (anticipo.clase()) {
		case NOT:
			empareja(ClaseLexica.NOT);
			Exp exp4 = E4();
			return sem.not(exp4);
		case MENOS:
			empareja(ClaseLexica.MENOS);
			Exp exp5 = E5();
			return sem.neg(exp5);
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case PAP:
		case TRUE:
			return E5();
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}

	private Exp E5() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case TRUE:
			return EXPRESION();
		case PAP:
			empareja(ClaseLexica.PAP);
			Exp exp0 = E0();
			empareja(ClaseLexica.PCIERRE);
			return exp0;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.LIT_ENT,
					ClaseLexica.ID, ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.PAP);
			return null;
		}
	}

	private Exp EXPRESION() {
		switch (anticipo.clase()) {
		case TRUE:
			empareja(ClaseLexica.TRUE);
			return sem.true_cons();
		case FALSE:
			empareja(ClaseLexica.FALSE);
			return sem.false_cons();
		case LIT_REAL:
			UnidadLexica tkreal = anticipo;
			empareja(ClaseLexica.LIT_REAL);
			return sem.litReal(sem.str(tkreal.lexema(), tkreal.fila(), tkreal.columna()));
		case LIT_ENT:
			UnidadLexica tkNum = anticipo;
			empareja(ClaseLexica.LIT_ENT);
			return sem.litEnt(sem.str(tkNum.lexema(), tkNum.fila(), tkNum.columna()));
		case ID:
			UnidadLexica tkid = anticipo;
			empareja(ClaseLexica.ID);
			return sem.id(sem.str(tkid.lexema(), tkid.fila(), tkid.columna()));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.LIT_ENT,
					ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE);
			return null;
		}
	}

	private char Op0() {
		switch (anticipo.clase()) {
		case MAS:
			empareja(ClaseLexica.MAS);
			return '+';
		case MENOS:
			empareja(ClaseLexica.MENOS);
			return '-';
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MAS,
					ClaseLexica.MENOS);
			return '?';
		}
	}

	private char OPBN3() {
		switch (anticipo.clase()) {
		case POR:
			empareja(ClaseLexica.POR);
			return '*';
		case DIV:
			empareja(ClaseLexica.DIV);
			return '/';
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.POR,
					ClaseLexica.DIV);
			return '?';
		}
	}

	private void empareja(ClaseLexica claseEsperada) {
		if (anticipo.clase() == claseEsperada)
			sigToken();
		else
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), claseEsperada);
	}

	private void sigToken() {
		try {
			anticipo = alex.sigToken();
		} catch (IOException e) {
			errores.errorFatal(e);
		}
	}

}
