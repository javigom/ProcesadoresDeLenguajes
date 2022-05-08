package c_ast_ascendente;

public class UnidadLexica extends java_cup.runtime.Symbol{
	private int fila;
	private int columna;
	
	public UnidadLexica(int fila, int columna, int clase, String lexema) {
		super(clase,lexema);
		this.fila = fila;
		this.columna = columna;
	}
	
	public int clase() {
		return sym;
	}
	
	public String lexema() {
		return (String)value;
	}
	
	public int fila() {
		return fila;
	}
	
	public int columna() {
		return columna;
	}
}
