package implementacion_manual;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class AnalizadorLexicoTiny {
	private Reader input;
	private StringBuffer lex;
	private int sigCar;
	private int filaInicio;
	private int columnaInicio;
	private int filaActual;
	private int columnaActual;
	private static String NL = System.getProperty("line.separator");

	private static enum Estado {
		INICIO, SEP, SEP_PC, ASIG, MAS, MENOS, POR, DIV, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, IGUAL, PDEC_0, PEXP_0,
		DIF, PAP, PCIERRE, PUNTO, VAR, NUM_ENT, NUM_ENT_0, NUM_REAL, PDEC, PEXP, PEXP_E, SUB_SEP, NUM_PUNTO, PEXP_ADD, DIF_0,PEXP_00
	}

	private Estado estado;

	public AnalizadorLexicoTiny(Reader input) throws IOException {
		this.input = input;
		lex = new StringBuffer();
		sigCar = input.read();
		filaActual = 1;
		columnaActual = 1;
	}

	public UnidadLexica sigToken() throws IOException {
		estado = Estado.INICIO;
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		lex.delete(0, lex.length());
		while (true) {
			switch (estado) {
			case INICIO:
				if (hayLetra())
					transita(Estado.VAR);
				else if (hayDigitoPos())
					transita(Estado.NUM_ENT);
				else if (hayCero())
					transita(Estado.NUM_ENT_0);
				else if (haySuma())
					transita(Estado.MAS);
				else if (hayResta())
					transita(Estado.MENOS);
				else if (hayMul())
					transita(Estado.POR);
				else if (hayDiv())
					transita(Estado.DIV);
				else if (hayPAp())
					transita(Estado.PAP);
				else if (hayPCierre())
					transita(Estado.PCIERRE);
				else if (hayIgual())
					transita(Estado.ASIG);
				else if (hayExclamacion())
					transita(Estado.DIF_0);
				else if (hayMayor())
					transita(Estado.MAYOR);
				else if (hayMenor())
					transita(Estado.MENOR);
				else if (hayPunto())
					transita(Estado.PUNTO);
				else if (hayPuntoComa())
					transita(Estado.SEP_PC);
				else if (hayAmpersand())
					transita(Estado.SUB_SEP);
				else if (haySep())
					transitaIgnorando(Estado.INICIO);
				else if (hayEOF())
					transita(Estado.REC_EOF);
				else
					error();
				break;
			case VAR:
				if (hayLetra() || hayDigito())
					transita(Estado.VAR);
				else
					return unidadId();
				break;
			case DIF_0:
				if (hayIgual())
					transita(Estado.DIF);
				else
					return unidadId();
				break;
			case ASIG:
				if (hayIgual())
					transita(Estado.IGUAL);
				else
					return unidadId();
				break;
			case MENOR:
				if (hayIgual())
					transita(Estado.MENOR_IGUAL);
				else
					return unidadId();
				break;
			case MAYOR:
				if (hayIgual())
					transita(Estado.MAYOR_IGUAL);
				else
					return unidadId();
				break;
			case SUB_SEP:
				if (hayAmpersand())
					transita(Estado.SEP);
				else
					return unidadId();
				break;
			case NUM_ENT:
				if (hayDigito())
					transita(Estado.NUM_ENT);
				else if (hayPunto())
					transita(Estado.NUM_PUNTO);
				else if (hayE())
					transita(Estado.PEXP_E);
				else
					return unidadEnt();
				break;
			case NUM_ENT_0:
				if (hayPunto())
					transita(Estado.NUM_PUNTO);
				else
					return unidadEnt();
				break;
			case MAS:
				if (hayDigitoPos())
					transita(Estado.NUM_ENT);
				else if (hayCero())
					transita(Estado.NUM_ENT_0);
				else
					return unidadMas();
				break;
			case MENOS:
				if (hayDigitoPos())
					transita(Estado.NUM_ENT);
				else if (hayCero())
					transita(Estado.NUM_ENT_0);
				else
					return unidadMenos();
				break;
			case POR:
				return unidadPor();
			case DIV:
				return unidadDiv();
			case PAP:
				return unidadPAp();
			case PCIERRE:
				return unidadPCierre();
			case IGUAL:
				return unidadIgual();
			case SEP_PC:
				return unidadPuntoComa();
			case SEP:
				return unidadSeparador();
			case REC_EOF:
				return unidadEof();
			case NUM_PUNTO:
				if (hayDigito())
					transita(Estado.PDEC);
				else
					error();
				break;
			case PDEC:
				if (hayDigitoPos())
					transita(Estado.PDEC);
				else if (hayCero())
					transita(Estado.PDEC_0);
				else if (hayE())
					transita(Estado.PEXP_E);
				else
					return unidadReal();
				break;
			case PDEC_0:
				if (hayDigitoPos())
					transita(Estado.PDEC);
				else
					error();
				break;
			case PEXP_E:
				if (hayDigitoPos())
					transita(Estado.PEXP);
				else if (hayCero())
					transita(Estado.PEXP_0);
				else if (haySuma() || hayResta())
					transita(Estado.PEXP_ADD);
				else
					error();
				break;
			case PEXP_ADD:
				if (hayDigitoPos())
					transita(Estado.PEXP);
				else if (hayCero())
					transita(Estado.PEXP_0);
				else
					error();
				break;
			case PEXP_0:
				return unidadReal();
				break;
			case PEXP:
				if (hayDigitoPos())
					transita(Estado.PEXP);
				else if (hayCero())
					transita(Estado.PEXP_00);
				else
					return unidadReal();
				break;
			case PEXP_00:
				if (hayDigitoPos())
					transita(Estado.PEXP);
				else
					error();
				break;
			}
		}
	}

	private void transita(Estado sig) throws IOException {
		lex.append((char) sigCar);
		sigCar();
		estado = sig;
	}

	private void transitaIgnorando(Estado sig) throws IOException {
		sigCar();
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		estado = sig;
	}

	private void sigCar() throws IOException {
		sigCar = input.read();
		if (sigCar == NL.charAt(0))
			saltaFinDeLinea();
		if (sigCar == '\n') {
			filaActual++;
			columnaActual = 0;
		} else {
			columnaActual++;
		}
	}

	private void saltaFinDeLinea() throws IOException {
		for (int i = 1; i < NL.length(); i++) {
			sigCar = input.read();
			if (sigCar != NL.charAt(i))
				error();
		}
		sigCar = '\n';
	}

	private boolean hayLetra() {
		return sigCar >= 'a' && sigCar <= 'z' || sigCar >= 'A' && sigCar <= 'Z';
	}

	private boolean hayDigitoPos() {
		return sigCar >= '1' && sigCar <= '9';
	}

	private boolean hayCero() {
		return sigCar == '0';
	}

	private boolean hayDigito() {
		return hayDigitoPos() || hayCero();
	}

	private boolean haySuma() {
		return sigCar == '+';
	}

	private boolean hayResta() {
		return sigCar == '-';
	}

	private boolean hayMul() {
		return sigCar == '*';
	}

	private boolean hayDiv() {
		return sigCar == '/';
	}

	private boolean hayPAp() {
		return sigCar == '(';
	}

	private boolean hayPCierre() {
		return sigCar == ')';
	}

	private boolean hayIgual() {
		return sigCar == '=';
	}

	private boolean hayComa() {
		return sigCar == ',';
	}

	private boolean hayPunto() {
		return sigCar == '.';
	}

	private boolean haySep() {
		return sigCar == ' ' || sigCar == '\t' || sigCar == '\n';
	}

	private boolean hayNL() {
		return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';
	}

	private boolean hayEOF() {
		return sigCar == -1;
	}

	private UnidadLexica unidadId() {
		switch (lex.toString()) {
		case "false":
			return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.FALSE);
		case "true":
			return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.TRUE);
		case "and":
			return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.AND);
		case "or":
			return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.OR);
		case "not":
			return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.NOT);
		default:
			return new UnidadLexicaMultivaluada(filaInicio, columnaInicio, ClaseLexica.VAR, lex.toString());
		}
	}

	private UnidadLexica unidadEnt() {
		return new UnidadLexicaMultivaluada(filaInicio, columnaInicio, ClaseLexica.NUM_ENT, lex.toString());
	}

	private UnidadLexica unidadReal() {
		return new UnidadLexicaMultivaluada(filaInicio, columnaInicio, ClaseLexica.NUM_REAL, lex.toString());
	}

	private UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.MAS);
	}

	private UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.MENOS);
	}

	private UnidadLexica unidadPor() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.POR);
	}

	private UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.DIV);
	}

	private UnidadLexica unidadPAp() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.PAP);
	}

	private UnidadLexica unidadPCierre() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.PCIERRE);
	}

	private UnidadLexica unidadIgual() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.IGUAL);
	}

	private UnidadLexica unidadComa() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.COMA);
	}

	private UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.EOF);
	}

	private void error() {
		System.err.println("(" + filaActual + ',' + columnaActual + "):Caracter inexperado");
		System.exit(1);
	}

	public static void main(String arg[]) throws IOException {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
		UnidadLexica unidad;
		do {
			unidad = al.sigToken();
			System.out.println(unidad);
		} while (unidad.clase() != ClaseLexica.EOF);
	}
}
