package implementacion_jflex;

public class ALexOperations {
	private AnalizadorLexicoTiny alex;

	public ALexOperations(AnalizadorLexicoTiny alex) {
		this.alex = alex;
	}

	public UnidadLexica unidadId() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ID, alex.lexema());
	}

	public UnidadLexica unidadLitCad() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LIT_CAD, alex.lexema());
	}

	public UnidadLexica unidadFalse() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FALSE);
	}

	public UnidadLexica unidadTrue() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TRUE);
	}

	public UnidadLexica unidadAnd() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AND);
	}

	public UnidadLexica unidadOr() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.OR);
	}

	public UnidadLexica unidadNot() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NOT);
	}

	public UnidadLexica unidadLitEnt() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LIT_ENT, alex.lexema());
	}

	public UnidadLexica unidadLitReal() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LIT_REAL, alex.lexema());
	}

	public UnidadLexica unidadSuma() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAS);
	}

	public UnidadLexica unidadResta() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOS);
	}

	public UnidadLexica unidadMul() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.POR);
	}

	public UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIV);
	}

	public UnidadLexica unidadPAp() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PAP);
	}

	public UnidadLexica unidadPCierre() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCIERRE);
	}

	public UnidadLexica unidadIgual() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIGUAL);
	}

	public UnidadLexica unidadAsig() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.IGUAL);
	}

	public UnidadLexica unidadPuntoComa() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCOMA);
	}

	public UnidadLexica unidadDAmp() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DAMP);
	}
	
	public UnidadLexica unidadDif() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIF);
	}
	
	public UnidadLexica unidadMenor() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOR);
	}

	public UnidadLexica unidadMayor() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAYOR);
	}

	public UnidadLexica unidadMayorIgual() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAYOR_IGUAL);
	}
	
	public UnidadLexica unidadMenorIgual() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOR_IGUAL);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public UnidadLexica unidadComa() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.COMA);
	}
	
	public UnidadLexica unidadFlecha() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FLECHA);
	}
	
	public UnidadLexica unidadPunto() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
	}
	
	public UnidadLexica unidadAmp() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AMP);
	}
	
	public UnidadLexica unidadCap() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CAP);
	}
	
	public UnidadLexica unidadCcierre() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CCIERRE);
	}
	
	public UnidadLexica unidadLlap() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LLAP);
	}
	
	public UnidadLexica unidadLlcierre() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LLCIERRE);
	}
	
	public UnidadLexica unidadPorcent() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PORCENT);
	}
	
	public UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EOF);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public UnidadLexica unidadInt() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.INT);
	}
	
	public UnidadLexica unidadReal() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.REAL);
	}
	
	public UnidadLexica unidadBool() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BOOL);
	}
	
	public UnidadLexica unidadString() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.STRING);
	}
	
	public UnidadLexica unidadNull() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NULL);
	}
	
	public UnidadLexica unidadProc() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PROC);
	}
	
	public UnidadLexica unidadIf() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.IF);
	}
	
	public UnidadLexica unidadThen() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.THEN);
	}
	
	public UnidadLexica unidadElse() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ELSE);
	}
	
	public UnidadLexica unidadEndif() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ENDIF);
	}
	
	public UnidadLexica unidadWhile() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.WHILE);
	}
	
	public UnidadLexica unidadDo() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DO);
	}
	
	public UnidadLexica unidadEndwhile() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ENDWHILE);
	}
	
	public UnidadLexica unidadCall() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CALL);
	}
	
	public UnidadLexica unidadRecord() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.RECORD);
	}
	
	public UnidadLexica unidadArray() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ARRAY);
	}
	
	public UnidadLexica unidadOf() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.OF);
	}
	
	public UnidadLexica unidadPointer() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.POINTER);
	}
	
	public UnidadLexica unidadNew() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NEW);
	}
	
	public UnidadLexica unidadDelete() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DELETE);
	}
	
	public UnidadLexica unidadRead() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.READ);
	}
	
	public UnidadLexica unidadWrite() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.WRITE);
	}
	
	public UnidadLexica unidadNl() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NL);
	}
	
	public UnidadLexica unidadVar() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.VAR);
	}
	
	public UnidadLexica unidadType() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TYPE);
	}
	

	public void error() {
		System.err.println("***" + alex.fila() + " Caracter inexperado: " + alex.lexema());
	}
}
