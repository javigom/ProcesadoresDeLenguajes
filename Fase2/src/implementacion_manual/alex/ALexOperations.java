package alex;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadId() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ID, alex.lexema());
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
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NUM_ENT, alex.lexema());
	}

	public UnidadLexica unidadLitReal() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NUM_REAL, alex.lexema());
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

	public UnidadLexica unidadDigual() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIGUAL);
	}

	public UnidadLexica unidadIgual() {
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
	
	public UnidadLexica unidadPunto() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
	}
	
	public UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EOF);
	}
}
