/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny;
import alex.ClaseLexica;
import errors.GestionErroresTiny;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoTiny {
	private UnidadLexica anticipo;
	private AnalizadorLexicoTiny alex;
	private GestionErroresTiny errores;

	public AnalizadorSintacticoTiny(Reader input) throws IOException {
		errores = new GestionErroresTiny();
		alex = new AnalizadorLexicoTiny(input);
		alex.fijaGestionErrores(errores);
		sigToken();
	}

	public void PROGRAMAp() {
		PROGRAMA();
		empareja(ClaseLexica.EOF);
	}

	private void PROGRAMA() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			DECLARACIONES();
			empareja(ClaseLexica.DAMP);
			INSTRUCCIONES();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
		}
	}

	private void DECLARACIONES() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			DECLARACION();
			RDEC();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
		}
	}
	
	private void RDEC() {
		switch (anticipo.clase()) {
		case DAMP:
			break;
		case BOOL:
		case INT:
		case REAL:
			empareja(ClaseLexica.PCOMA);
			DECLARACION();
			RDEC();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DAMP,
					ClaseLexica.BOOL, ClaseLexica.INT, ClaseLexica.REAL);
		}
	}
	
	private void DECLARACION() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			TIPO();
			empareja(ClaseLexica.ID);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL, 
					ClaseLexica.INT, ClaseLexica.REAL, ClaseLexica.EOF);
		}
	}
	
	private void TIPO() {
		switch (anticipo.clase()) {
		case BOOL:
			empareja(ClaseLexica.BOOL);
			break;
		case INT:
			empareja(ClaseLexica.INT);
			break;
		case REAL:
			empareja(ClaseLexica.REAL);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
		}
	}

	private void INSTRUCCIONES() {
		switch (anticipo.clase()) {
		case ID:
			INSTRUCCION();
			RINS();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID);
		}
	}
	
	private void RINS() {
		switch (anticipo.clase()) {
		case ID:
			empareja(ClaseLexica.PCOMA);
			INSTRUCCION();
			RINS();
			break;
		case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID,
					ClaseLexica.EOF);
		}
	}

	private void INSTRUCCION() {
		switch (anticipo.clase()) {
		case ID:
			empareja(ClaseLexica.ID);
			empareja(ClaseLexica.IGUAL);
			E0();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID);
		}
	}

	private void E0() {
		switch (anticipo.clase()) {
		case FALSE:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			E1();
			RE0();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT, ClaseLexica.PAP,
					ClaseLexica.TRUE);
		}
	}
	
	private void RE0() {
		switch (anticipo.clase()) {
		case MAS:
			empareja(ClaseLexica.MAS);
			E1();
			RE0();
			break;
		case MENOS:
			empareja(ClaseLexica.MENOS);
			E1();
			break;
		case ID:
		case PCIERRE:
		case EOF:
			empareja(ClaseLexica.EOF);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MAS,
					ClaseLexica.MENOS, ClaseLexica.ID, ClaseLexica.PCIERRE, ClaseLexica.EOF);
		}
	}

	private void E1() {
		switch (anticipo.clase()) {
		case FALSE:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			E2();
			RE1();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT, ClaseLexica.PAP,
					ClaseLexica.TRUE);
		}
	}
	
	private void RE1() {
		switch (anticipo.clase()) {
		case AND:
		case OR:
			OPBN1();
			E2();
			RE1();
			break;
		case MAS:
		case MENOS:
		case PCIERRE:
		case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.AND,
					ClaseLexica.OR, ClaseLexica.MAS, ClaseLexica.MENOS, ClaseLexica.PCIERRE, ClaseLexica.EOF);
		}
	}
	
	private void OPBN1() {
		switch (anticipo.clase()) {
		case AND:
			empareja(ClaseLexica.AND);
			break;
		case OR:
			empareja(ClaseLexica.OR);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.AND,
					ClaseLexica.OR);
		}
	}

	private void E2() {
		switch (anticipo.clase()) {
		case FALSE:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			E3();
			RE2();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT, ClaseLexica.PAP,
					ClaseLexica.TRUE);
		}
	}
	
	private void RE2() {
		switch (anticipo.clase()) {
		case DIF:
		case DIGUAL:
		case MAYOR_IGUAL:
		case MAYOR:
		case MENOR_IGUAL:
		case MENOR:
			OPBN2();
			E3();
			RE2();
			break;
		case AND:
		case ID:
		case MAS:
		case MENOS:
		case OR:
		case PCIERRE:
		case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DIF,
					ClaseLexica.DIGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL, ClaseLexica.MENOR,
					ClaseLexica.AND, ClaseLexica.ID, ClaseLexica.MAS, ClaseLexica.MENOS, ClaseLexica.OR, ClaseLexica.PCIERRE,
					ClaseLexica.EOF);
		}
	}
	
	private void OPBN2() {
		switch (anticipo.clase()) {
		case MENOR:
			empareja(ClaseLexica.MENOR);
			break;
		case MAYOR:
			empareja(ClaseLexica.MAYOR);
			break;
		case MENOR_IGUAL:
			empareja(ClaseLexica.MENOR_IGUAL);
			break;
		case MAYOR_IGUAL:
			empareja(ClaseLexica.MAYOR_IGUAL);
			break;
		case DIF:
			empareja(ClaseLexica.DIF);
			break;
		case DIGUAL:
			empareja(ClaseLexica.DIGUAL);
			break;	
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MENOR,
					ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.DIF,
					ClaseLexica.DIGUAL);
		}
	}

	private void E3() {
		switch (anticipo.clase()) {
		case FALSE:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			E4();
			RE3();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT, ClaseLexica.PAP,
					ClaseLexica.TRUE);
		}
	}
	
	private void RE3() {
		switch (anticipo.clase()) {
		case DIV:
		case POR:
			OPBN3();
			E4();
			break;
		case AND:
		case DIF:
		case DIGUAL:
		case ID:
		case MAS:
		case MENOS:
		case MAYOR_IGUAL:
		case MENOR_IGUAL:
		case OR:
		case PCIERRE:
		case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DIV,
					ClaseLexica.POR, ClaseLexica.AND, ClaseLexica.DIF, ClaseLexica.DIGUAL, ClaseLexica.ID,
					ClaseLexica.MAS, ClaseLexica.MENOS, ClaseLexica.MAYOR_IGUAL, ClaseLexica.MENOR_IGUAL, ClaseLexica.OR, 
					ClaseLexica.PCIERRE, ClaseLexica.EOF);
		}
	}
	
	private void OPBN3() {
		switch (anticipo.clase()) {
		case POR:
			empareja(ClaseLexica.POR);
			break;
		case DIV:
			empareja(ClaseLexica.DIV);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.POR,
					ClaseLexica.DIV);
		}
	}

	private void E4() {
		switch (anticipo.clase()) {
		case NOT:
			empareja(ClaseLexica.NOT);
			E4();
			break;
		case MENOS:
			empareja(ClaseLexica.MENOS);
			E5();
			break;
		case FALSE:
		case LIT_ENT:
		case LIT_REAL:
		case PAP:
		case TRUE:
			E5();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.NOT,
					ClaseLexica.MENOS, ClaseLexica.FALSE, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.PAP,
					ClaseLexica.TRUE);
		}
	}

	private void E5() {
		switch (anticipo.clase()) {
		case FALSE:
		case LIT_ENT:
		case LIT_REAL:
		case TRUE:
			EXPRESION();
			break;
		case PAP:
			empareja(ClaseLexica.PAP);
			E0();
			empareja(ClaseLexica.PCIERRE);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.LIT_ENT,
					ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.PAP);
		}
	}
	
	private void EXPRESION() {
		switch (anticipo.clase()) {
		case TRUE:
			empareja(ClaseLexica.TRUE);
		case FALSE:
			empareja(ClaseLexica.FALSE);
		case LIT_REAL:
			empareja(ClaseLexica.LIT_REAL);
		case LIT_ENT:
			empareja(ClaseLexica.LIT_ENT);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.LIT_ENT,
					ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE);
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
