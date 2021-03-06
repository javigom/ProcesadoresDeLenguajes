package asint;

import asint.TinyASint.Suma;
import asint.TinyASint.Tipo;
import asint.TinyASint.Resta;
import asint.TinyASint.Mul;
import asint.TinyASint.Neg;
import asint.TinyASint.Not;
import asint.TinyASint.Div;
import asint.TinyASint.Id;
import asint.TinyASint.Igual;
import asint.TinyASint.Instruccion;
import asint.TinyASint.Insts_muchas;
import asint.TinyASint.Insts_una;
import asint.TinyASint.LitEnt;
import asint.TinyASint.LitFalse;
import asint.TinyASint.LitReal;
import asint.TinyASint.LitTrue;
import asint.TinyASint.Mayor;
import asint.TinyASint.MayorIgual;
import asint.TinyASint.Menor;
import asint.TinyASint.MenorIgual;
import asint.TinyASint.Or;
import asint.TinyASint.And;
import asint.TinyASint.Declaracion;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Distinto;
import asint.TinyASint.Programa;

public interface Procesamiento {

	// Operadores

	// Nivel 0
	void procesa(Suma exp);

	void procesa(Resta exp);

	// Nivel 1
	void procesa(And exp);

	void procesa(Or exp);

	// Nivel 2
	void procesa(Menor exp);

	void procesa(Mayor exp);

	void procesa(MenorIgual exp);

	void procesa(MayorIgual exp);

	void procesa(Igual exp);

	void procesa(Distinto exp);

	// Nivel 3
	void procesa(Mul exp);

	void procesa(Div exp);

	// Nivel 4
	void procesa(Neg exp);

	void procesa(Not exp);

	// Nivel 5
	void procesa(LitTrue exp);

	void procesa(LitFalse exp);

	void procesa(LitEnt exp);

	void procesa(LitReal exp);

	void procesa(Id exp);

	// Tipo
    void procesa(Tipo tipo);

	// Declaraciones
	void procesa(Declaracion declaracion);

	void procesa(Decs_muchas decs);

	void procesa(Decs_una decs);

	// Instrucciones
	void procesa(Instruccion instruccion);

	void procesa(Insts_muchas insts);

	void procesa(Insts_una insts);

	// Programa
	void procesa(Programa programa);
}