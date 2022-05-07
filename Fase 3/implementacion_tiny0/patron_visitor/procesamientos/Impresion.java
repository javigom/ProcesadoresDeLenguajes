package procesamientos;

import asint.TinyASint.Suma;
import asint.TinyASint.Tipo;
import asint.TinyASint.Resta;
import asint.TinyASint.Mul;
import asint.TinyASint.Neg;
import asint.TinyASint.Not;
import asint.TinyASint.Or;
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
import asint.TinyASint.And;
import asint.TinyASint.Declaracion;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Distinto;
import asint.TinyASint.Programa;
import asint.ProcesamientoPorDefecto;
import asint.TinyASint.Exp;

public class Impresion extends ProcesamientoPorDefecto {
	public Impresion() {
	}

	// Programa

	public void procesa(Programa prog) {
		prog.declaraciones().procesa(this);
		System.out.println("\n&&");
		prog.instrucciones().procesa(this);
		System.out.println();
	}

	// Declaraciones

	public void procesa(Decs_muchas decs) {
		decs.declaraciones().procesa(this);
		System.out.println(";");
		decs.declaracion().procesa(this);
	}

	public void procesa(Decs_una decs) {
		decs.declaracion().procesa(this);
	}

	public void procesa(Declaracion dec) {
		dec.val().procesa(this);
		System.out.print(" " + dec.id());
	}

	// Instrucciones

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		System.out.println(";");
		insts.instruccion().procesa(this);
	}

	public void procesa(Insts_una inst) {
		inst.instruccion().procesa(this);
	}

	public void procesa(Instruccion instruccion) {
		System.out.print(instruccion.id() + " = ");
		instruccion.exp().procesa(this);
	}

	// Operadores

	// Nivel 0
	public void procesa(Suma exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print(" + ");
		imprime_arg(exp.arg1(), 0);
	}

	public void procesa(Resta exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print(" - ");
		imprime_arg(exp.arg1(), 1);
	}

	// Nivel 1
	public void procesa(And exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print(" and ");
		imprime_arg(exp.arg1(), 2);
	}

	public void procesa(Or exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print(" or ");
		imprime_arg(exp.arg1(), 2);
	}

	// Nivel 2
	public void procesa(Menor exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" < ");
		imprime_arg(exp.arg1(), 3);
	}

	public void procesa(Mayor exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" > ");
		imprime_arg(exp.arg1(), 3);
	}

	public void procesa(MenorIgual exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" <= ");
		imprime_arg(exp.arg1(), 3);
	}

	public void procesa(MayorIgual exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" >= ");
		imprime_arg(exp.arg1(), 3);
	}

	public void procesa(Igual exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" == ");
		imprime_arg(exp.arg1(), 3);
	}

	public void procesa(Distinto exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" != ");
		imprime_arg(exp.arg1(), 3);
	}

	// Nivel 3
	public void procesa(Mul exp) {
		imprime_arg(exp.arg0(), 4);
		System.out.print(" * ");
		imprime_arg(exp.arg1(), 4);
	}

	public void procesa(Div exp) {
		imprime_arg(exp.arg0(), 4);
		System.out.print(" / ");
		imprime_arg(exp.arg1(), 4);
	}

	// Nivel 4
	public void procesa(Neg exp) {
		System.out.print(" - ");
		imprime_arg(exp.arg0(), 5);
	}

	public void procesa(Not exp) {
		System.out.print(" not ");
		imprime_arg(exp.arg0(), 4);
	}

	private void imprime_arg(Exp arg, int p) {
		
		if (arg.prioridad() < p) {
			System.out.print("(");
			arg.procesa(this);
			System.out.print(")");
		} else {
			arg.procesa(this);
		}
	}

	// Nivel 5
	public void procesa(LitTrue exp) {
		System.out.print("true");
	}

	public void procesa(LitFalse exp) {
		System.out.print("false");
	}

	public void procesa(LitReal exp) {
		System.out.print(exp.num());
	}

	public void procesa(Id exp) {
		System.out.print(exp.id());
	}

	public void procesa(LitEnt exp) {
		System.out.print(exp.num());
	}

	// Tipo
	public void procesa(Tipo tipo) {
		System.out.print(tipo.tipo());
	}
	
}
