package procesamientos;

import asint.TinyASint.Suma;
import asint.TinyASint.Tipo_Id;
import asint.TinyASint.True;
import asint.TinyASint.While_inst;
import asint.TinyASint.Write;
import asint.TinyASint.Resta;
import asint.TinyASint.Star;
import asint.TinyASint.String_cons;
import asint.TinyASint.Mul;
import asint.TinyASint.New_cons;
import asint.TinyASint.Nl;
import asint.TinyASint.No_bloque;
import asint.TinyASint.Not;
import asint.TinyASint.Or;
import asint.TinyASint.ParamForm;
import asint.TinyASint.ParamForms_empty;
import asint.TinyASint.ParamForms_muchos;
import asint.TinyASint.ParamForms_uno;
import asint.TinyASint.Percent;
import asint.TinyASint.Pformal_ref;
import asint.TinyASint.Pointer;
import asint.TinyASint.Div;
import asint.TinyASint.Id;
import asint.TinyASint.If_else;
import asint.TinyASint.If_inst;
import asint.TinyASint.Igual;
import asint.TinyASint.Insts_muchas;
import asint.TinyASint.Insts_una;
import asint.TinyASint.Int;
import asint.TinyASint.Lista_exp_empty;
import asint.TinyASint.Lista_inst_empty;
import asint.TinyASint.Lista_inst_muchas;
import asint.TinyASint.Lista_inst_una;
import asint.TinyASint.LitCad;
import asint.TinyASint.LitEnt;
import asint.TinyASint.LitNull;
import asint.TinyASint.LitReal;
import asint.TinyASint.Mayor;
import asint.TinyASint.MayorIgual;
import asint.TinyASint.Menor;
import asint.TinyASint.MenorIgual;
import asint.TinyASint.MenosUnario;
import asint.TinyASint.And;
import asint.TinyASint.Array;
import asint.TinyASint.Asig;
import asint.TinyASint.Bloque_inst;
import asint.TinyASint.Bloque_prog;
import asint.TinyASint.Bool;
import asint.TinyASint.Call;
import asint.TinyASint.Camp;
import asint.TinyASint.Campo_uno;
import asint.TinyASint.Campos_muchos;
import asint.TinyASint.Corchete;
import asint.TinyASint.DecProc;
import asint.TinyASint.DecTipo;
import asint.TinyASint.DecVar;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Delete;
import asint.TinyASint.Distinto;
import asint.TinyASint.Programa;
import asint.TinyASint.Punto;
import asint.TinyASint.Read;
import asint.TinyASint.Real;
import asint.TinyASint.Record;
import asint.ProcesamientoPorDefecto;
import asint.TinyASint.Exp;
import asint.TinyASint.ExpN5;
import asint.TinyASint.Exp_muchas;
import asint.TinyASint.Exp_una;
import asint.TinyASint.False;
import asint.TinyASint.Flecha;

public class Impresion extends ProcesamientoPorDefecto {
	public Impresion() {
	}

	// Programa

	public void procesa(Programa prog) {
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
			System.out.println();
		}
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
	
	@Override
	public void procesa(DecProc dec) {
		System.out.print("proc " + dec.id());
		dec.pforms().procesa(this);
		dec.bloque().procesa(this);
		
	}

	@Override
	public void procesa(DecTipo dec) {
		System.out.print("type ");
		dec.val().procesa(this);
		System.out.print(" " + dec.id());
	}

	@Override
	public void procesa(DecVar dec) {
		System.out.print("var ");
		dec.val().procesa(this);
		System.out.print(" " + dec.id());
	}

	// Instrucciones
	

	@Override
	public void procesa(Bloque_inst bloque_inst) {
		bloque_inst.bloque().procesa(this);
	}
	
	@Override
	public void procesa(Call call) {
		System.out.println("call " + call.string() + " (");
		call.exps().procesa(this);
		System.out.println(")");
	}

	@Override
	public void procesa(Delete delete) {
		System.out.print("delete ");
		delete.exp().procesa(this);
	}

	@Override
	public void procesa(New_cons new_cons) {
		System.out.print("new ");
		new_cons.exp().procesa(this);
	}

	@Override
	public void procesa(Nl nl) {
		System.out.print("nl ");
	}

	@Override
	public void procesa(Write write) {
		System.out.print("write ");
		write.exp().procesa(this);
	}

	@Override
	public void procesa(Read read) {
		System.out.print("read ");
		read.exp().procesa(this);
	}

	@Override
	public void procesa(While_inst while_inst) {
		System.out.print("while ");
		while_inst.exp().procesa(this);
		System.out.print(" do\n");
		while_inst.instrucciones().procesa(this);
	}

	@Override
	public void procesa(If_else if_else) {
		System.out.println("if");
		if_else.exp().procesa(this);
		System.out.println("\nthen");
		if_else.instrucciones().procesa(this);
		System.out.println("\nelse");
		if_else.instrucciones_else().procesa(this);
	}

	@Override
	public void procesa(If_inst if_inst) {
		System.out.println("if");
		if_inst.exp().procesa(this);
		System.out.println("\nthen");
		if_inst.instrucciones().procesa(this);
	}

	@Override
	public void procesa(Asig asig) {
		asig.exp0().procesa(this);
		System.out.println(" = ");
		asig.exp1().procesa(this);
	}

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		System.out.println(";");
		insts.instruccion().procesa(this);
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
	}
	
	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
		System.out.println();
	}

	@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		lista_inst_una.instruccion().procesa(this);
	}

	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		lista_inst_muchas.instrucciones().procesa(this);
		System.out.println(";");
		lista_inst_muchas.instruccion().procesa(this);
	}
	
	
	// Param Formales


	@Override
	public void procesa(ParamForm paramForm) {
		paramForm.tipo().procesa(this);
		System.out.print(paramForm.id());
	}
	

	@Override
	public void procesa(Pformal_ref paramForm) {
		paramForm.tipo().procesa(this);
		System.out.print("& "+ paramForm.id());
	}

	@Override
	public void procesa(ParamForms_muchos paramForms_muchos) {
		paramForms_muchos.params().procesa(this);
		System.out.println(",");
		paramForms_muchos.param().procesa(this);
	}

	@Override
	public void procesa(ParamForms_uno paramForms_uno) {
		paramForms_uno.param().procesa(this);
	}

	@Override
	public void procesa(ParamForms_empty paramForms_empty) {
		System.out.println();
	}
	
	// Campos
	
	@Override
	public void procesa(Campos_muchos campos_muchos) {
		campos_muchos.campos().procesa(this);
		System.out.println(";");
		campos_muchos.campo().procesa(this);
	}

	@Override
	public void procesa(Campo_uno campo_uno) {
		campo_uno.campo().procesa(this);
	}

	@Override
	public void procesa(Camp camp) {
		camp.tipo().procesa(this);
		System.out.print(camp.id());
	}
	
	
	// Bloque 
	
	@Override
	public void procesa(Bloque_prog bloque_prog) {
		System.out.println("{");
		bloque_prog.programa().procesa(this);
		System.out.print("}");
	}

	@Override
	public void procesa(No_bloque no_bloque) {
		System.out.println("{}");
	}

	
	// Expresiones
	
	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		System.out.println();
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
		System.out.println(",");
		exp_muchas.expresion().procesa(this);
	}

	@Override
	public void procesa(Exp_una exp_una) {
		exp_una.expresion().procesa(this);
	}

	// Operadores

	// Nivel 0
	public void procesa(Suma exp) {
		imprime_arg(exp.arg0(), 0);
		System.out.print("+");
		imprime_arg(exp.arg1(), 1);
	}

	public void procesa(Resta exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print("-");
		imprime_arg(exp.arg1(), 1);
	}

	// Nivel 1
	public void procesa(And exp) {
		imprime_arg(exp.arg1(), 1);
		System.out.print(" and ");
		imprime_arg(exp.arg0(), 2);
	}

	public void procesa(Or exp) {
		imprime_arg(exp.arg1(), 1);
		System.out.print(" or ");
		imprime_arg(exp.arg0(), 2);
	}

	// Nivel 2
	public void procesa(Menor exp) {
		imprime_arg(exp.arg0(), 2);
		System.out.print(" < ");
		imprime_arg(exp.arg1(), 3);
	}

	public void procesa(Mayor exp) {
		imprime_arg(exp.arg1(), 2);
		System.out.print(" > ");
		imprime_arg(exp.arg0(), 3);
	}

	public void procesa(MenorIgual exp) {
		imprime_arg(exp.arg1(), 2);
		System.out.print(" <= ");
		imprime_arg(exp.arg0(), 3);
	}

	public void procesa(MayorIgual exp) {
		imprime_arg(exp.arg1(), 2);
		System.out.print(" >= ");
		imprime_arg(exp.arg0(), 3);
	}

	public void procesa(Igual exp) {
		imprime_arg(exp.arg1(), 2);
		System.out.print(" == ");
		imprime_arg(exp.arg0(), 3);
	}

	public void procesa(Distinto exp) {
		imprime_arg(exp.arg1(), 2);
		System.out.print(" != ");
		imprime_arg(exp.arg0(), 3);
	}

	// Nivel 3
	public void procesa(Mul exp) {
		imprime_arg(exp.arg0(), 4);
		System.out.print("*");
		imprime_arg(exp.arg1(), 4);
	}

	public void procesa(Div exp) {
		imprime_arg(exp.arg0(), 4);
		System.out.print("/");
		imprime_arg(exp.arg1(), 4);
	}

	@Override
	public void procesa(Percent exp) {
		imprime_arg(exp.arg0(), 4);
		System.out.print("%");
		imprime_arg(exp.arg1(), 4);
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		System.out.print("-");
		imprime_arg(exp.arg0(), 5);
	}

	public void procesa(Not exp) {
		System.out.print(" not ");
		imprime_arg(exp.arg0(), 4);
	}
	
	//Nivel 5
	
	@Override
	public void procesa(ExpN5 expN5) {
		// TODO Auto-generated method stub
		imprime_arg(expN5.arg1(), 5);
		imprime_arg(expN5.arg0(), 5);
	}
	
	@Override
	public void procesa(Corchete corchete) {
		System.out.println("[" + corchete.exp() + "]");
	}

	@Override
	public void procesa(Punto punto) {
		System.out.println("." + punto.id());
	}

	@Override
	public void procesa(Flecha flecha) {
		System.out.println("->" + flecha.id());
		
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		System.out.print(" not ");
		imprime_arg(star.arg0(), 6);
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

	// Nivel 7
	public void procesa(True exp) {
		System.out.print("true");
	}

	public void procesa(False exp) {
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

	@Override
	public void procesa(LitNull exp) {
		System.out.print("null");
	}

	@Override
	public void procesa(LitCad exp) {
		System.out.print(exp.cad());
	}

	// Tipo

	@Override
	public void procesa(Bool bool) {
		System.out.print("bool ");
	}

	@Override
	public void procesa(Int int1) {
		System.out.print("int ");
	}

	@Override
	public void procesa(Real real) {
		System.out.print("real ");
	}

	@Override
	public void procesa(String_cons string_cons) {
		System.out.print("string ");
	}

	@Override
	public void procesa(Tipo_Id tipo_Id) {
		System.out.print(tipo_Id.tipo() + " ");
	}

	@Override
	public void procesa(Array array) {
		System.out.print("array [" + array.tam() + "] of ");
		array.tipo_array().procesa(this);
	}

	@Override
	public void procesa(Record record) {
		System.out.println("record {");
		record.campos().procesa(this);
		System.out.println("} ");
	}

	@Override
	public void procesa(Pointer pointer) {
		System.out.print("pointer ");
		pointer.tipo().procesa(this);
	}
	
	
	
}
