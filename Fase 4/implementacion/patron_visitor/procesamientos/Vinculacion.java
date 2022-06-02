package procesamientos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import asint.ProcesamientoPorDefecto;
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
import asint.TinyASint.Declaracion;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Delete;
import asint.TinyASint.Distinto;
import asint.TinyASint.Div;
import asint.TinyASint.Exp_muchas;
import asint.TinyASint.Exp_una;
import asint.TinyASint.False;
import asint.TinyASint.Flecha;
import asint.TinyASint.Genero;
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
import asint.TinyASint.Programa;
import asint.TinyASint.Punto;
import asint.TinyASint.Read;
import asint.TinyASint.Real;
import asint.TinyASint.Record;
import asint.TinyASint.Resta;
import asint.TinyASint.Star;
import asint.TinyASint.StringLocalizado;
import asint.TinyASint.String_cons;
import asint.TinyASint.Suma;
import asint.TinyASint.Tipo;
import asint.TinyASint.Tipo_Id;
import asint.TinyASint.True;
import asint.TinyASint.While_inst;
import asint.TinyASint.Write;

public class Vinculacion extends ProcesamientoPorDefecto{
	private Stack<Map<String, Nodo>> ts;
	
	public Vinculacion() {
		ts = new Stack<Map<String, Nodo>>();
		ts.push(new HashMap<String, Nodo>());
	}
	

	
	public Declaracion getDec(StringLocalizado id) {
		for (Map<String, Nodo> t: ts) {
			if (t.containsKey(id.toString())) return t.get(id.toString()).d;
		}
		
		return null;
	}
	
	public boolean idDuplicadoTodos(StringLocalizado id) {
		for (Map<String, Nodo> t: ts) {
			if (t.containsKey(id.toString())) return true;
		}
		
		return false;
	}
	
	public boolean idDuplicadoAct(StringLocalizado id) {
		if (ts.peek().containsKey(id.toString())) return true;
		return false;
	}
	
	public void aniade(StringLocalizado id, Declaracion d) {
		ts.peek().put(id.toString(), new Nodo(d, id));
	}

	
	public void recolectaTodos(StringLocalizado id, Declaracion d) {
		if (idDuplicadoTodos(id)) {
			errorDec(id);
		} else {
			aniade(id, d);
		}
	}
	
	public void recolectaAct(StringLocalizado id, Declaracion d) {
		if (idDuplicadoAct(id)) {
			errorDec(id);
		} else {
			aniade(id, d);
		}
	}
	
	public void anida() {
		ts.push(new HashMap<String,Nodo>());
	}
	
	private void desanida() {
		ts.pop();
	}
	
	private static class Nodo {
		public int fila;
		public int col;
		public Declaracion d;
		
		public Nodo(Declaracion d, StringLocalizado s) {
			this.d = d;
			this.fila = s.fila();
			this.col = s.col();
		}

		public String toString() {
			return Integer.toString(fila) + "-" + Integer.toString(col);
		}
	}
	
	private class VinculacionPointer  extends ProcesamientoPorDefecto{
		public void procesa(Decs_muchas decs_muchas) {
			decs_muchas.declaraciones().procesa(this);
			decs_muchas.declaracion().procesa(this);
		}
		
		public void procesa(Decs_una decs_una) {
			decs_una.declaracion().procesa(this);
		}
		
		public void procesa(DecVar var) {
			var.val().procesa(this);
		}
		
		public void procesa(DecTipo type) {
			type.val().procesa(this);
		}
		
		public void procesa(Int t) {}
		public void procesa(Real t) {}
		public void procesa(Bool t) {}
		public void procesa(String t) {}
		
		
		public void procesa(Pointer t) {
			t.tipo().procesa(this);
		}
		
		public void procesa(Tipo_Id id) {
			if (!idDuplicadoTodos(id.tipo())) {
				errorNoDec(id.tipo());
			} else {
				id.setVinculo((DecTipo) getDec(id.tipo()));
			}
		}
		
		public void procesa(Array t) {
			t.tipo_array().procesa(this);
		}
		
		public void procesa(Record t) {
			t.campos().procesa(this);
		}
		
		public void procesa(Campo_uno c) {
			c.campo().procesa(this);
		}
		
		public void procesa(Campos_muchos c) {
			c.campos().procesa(this);
			c.campo().procesa(this);
		}
		
		public void procesa(Camp c) {
			c.tipo().procesa(this);
		}
	}
	
	private void errorCommon(StringLocalizado id) {
		System.out.println("Error de vinculaci�n en " + Integer.toString(id.fila()) + ":" + Integer.toString(id.col()));
	}
	
	private void errorDec(StringLocalizado id) {
		errorCommon(id);
		System.out.println("  El identificador " + id + " ya ha sido declarado previamente en:" + ts.peek().get(id.toString()));
	}
	
	private void errorNoDec(StringLocalizado id) {
		errorCommon(id);
		System.out.println("  El identificador " + id + " no ha sido declarado anteriormente");
	}
	
	// Programa

	public void procesa(Programa prog) {
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
			prog.declaraciones().procesa(new VinculacionPointer());
		}
		prog.instrucciones().procesa(this);
	}

	// Declaraciones

	public void procesa(Decs_muchas decs) {
		decs.declaraciones().procesa(this);
		decs.declaracion().procesa(this);
	}

	public void procesa(Decs_una decs) {
		decs.declaracion().procesa(this);
	}
	
	@Override
	public void procesa(DecProc dec) {
		StringLocalizado id = dec.id();
		
		recolectaAct(id, dec);
		anida();
		dec.pforms().procesa(this);    
		dec.bloque().procesa(this);
		desanida();
	}

	@Override
	public void procesa(DecTipo dec) {
		StringLocalizado id = dec.id();
		dec.val().procesa(this);
		
		recolectaAct(id, dec);
		dec.val().procesa(this);
		dec.size = dec.val().size;
	}

	@Override
	public void procesa(DecVar dec) {
		dec.val().procesa(this);
		
		StringLocalizado id = dec.id();
		recolectaAct(id, dec);
	}

	// Instrucciones
	

	@Override
	public void procesa(Bloque_inst bloque_inst) {
		anida();
		bloque_inst.bloque().procesa(this);
		desanida();
	}
	
	@Override
	public void procesa(Call call) {
		StringLocalizado id = call.string();
		
		if (!idDuplicadoTodos(id)) {
			errorNoDec(id);
		} else {
			call.setVinculo((DecProc) getDec(id));
			call.exps().procesa(this);
		}
	}

	@Override
	public void procesa(Delete delete) {
		delete.exp().procesa(this);
	}

	@Override
	public void procesa(New_cons new_cons) {
		new_cons.exp().procesa(this);
	}

	@Override
	public void procesa(Nl nl) {
	}

	@Override
	public void procesa(Write write) {
		write.exp().procesa(this);
	}

	@Override
	public void procesa(Read read) {
		read.exp().procesa(this);
	}

	@Override
	public void procesa(While_inst while_inst) {
		while_inst.exp().procesa(this);
		while_inst.instrucciones().procesa(this);
	}

	@Override
	public void procesa(If_else if_else) {
		if_else.exp().procesa(this);
		if_else.instrucciones().procesa(this);
		if_else.instrucciones_else().procesa(this);
	}

	@Override
	public void procesa(If_inst if_inst) {
		if_inst.exp().procesa(this);
		if_inst.instrucciones().procesa(this);
	}

	@Override
	public void procesa(Asig asig) {
		asig.exp0().procesa(this);
		asig.exp1().procesa(this);
	}

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);;
		insts.instruccion().procesa(this);
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
	}
	
	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
	}

	@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		lista_inst_una.instruccion().procesa(this);
	}

	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		lista_inst_muchas.instrucciones().procesa(this);
		lista_inst_muchas.instruccion().procesa(this);
	}
	
	
	// Param Formales


	@Override
	public void procesa(ParamForm paramForm) {
		StringLocalizado id = paramForm.id();
		recolectaAct(id, paramForm);
	}
	

	@Override
	public void procesa(Pformal_ref paramForm) {
		StringLocalizado id = paramForm.id();
		recolectaAct(id, paramForm);
	}

	@Override
	public void procesa(ParamForms_muchos paramForms_muchos) {
		paramForms_muchos.params().procesa(this);
		paramForms_muchos.param().procesa(this);
	}

	@Override
	public void procesa(ParamForms_uno paramForms_uno) {
		paramForms_uno.param().procesa(this);
	}

	@Override
	public void procesa(ParamForms_empty paramForms_empty) {
	}
	
	// Campos
	
	@Override
	public void procesa(Campos_muchos campos_muchos) {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
	}

	@Override
	public void procesa(Campo_uno campo_uno) {
		campo_uno.campo().procesa(this);
	}

	@Override
	public void procesa(Camp camp) {
		camp.tipo().procesa(this);
	}
	
	
	// Bloque 
	
	@Override
	public void procesa(Bloque_prog bloque_prog) {
		//anida();
		bloque_prog.programa().procesa(this);
		//desanida();
	}

	@Override
	public void procesa(No_bloque no_bloque) {
	}

	
	// Expresiones
	
	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		System.out.println();
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
		System.out.print(",");
		exp_muchas.expresion().procesa(this);
	}

	@Override
	public void procesa(Exp_una exp_una) {
		exp_una.expresion().procesa(this);
	}

	// Operadores

	// Nivel 0
	public void procesa(Suma exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(Resta exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	// Nivel 1
	public void procesa(And exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(Or exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	// Nivel 2
	public void procesa(Menor exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(Mayor exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(MenorIgual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(MayorIgual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(Igual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(Distinto exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	// Nivel 3
	public void procesa(Mul exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	public void procesa(Div exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Percent exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		exp.arg0().procesa(this);
	}

	public void procesa(Not exp) {
		exp.arg0().procesa(this);
	}
	
	//Nivel 5
	
	@Override
	public void procesa(Corchete corchete) {
		corchete.arg0().procesa(this);
		corchete.arg1().procesa(this);
	}

	@Override
	public void procesa(Punto punto) {
		punto.exp().procesa(this);
	}

	@Override
	public void procesa(Flecha flecha) {
		flecha.exp().procesa(this);
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		star.arg0().procesa(this);
	}

	// Nivel 7

	public void procesa(Id exp) {
		StringLocalizado id = exp.id();
		if (!idDuplicadoTodos(id)) {
			errorNoDec(id);
		} else {
			exp.setVinculo((DecVar) getDec(id));
		}
	}

	// Tipo

	@Override
	public void procesa(Tipo_Id tipo_Id) {
		StringLocalizado id = tipo_Id.tipo();
		if (!idDuplicadoTodos(id)) {
			errorNoDec(id);
		} else {
			tipo_Id.setVinculo((DecTipo) getDec(id));
		}
	}

	@Override
	public void procesa(Array array) {
		array.tipo_array().procesa(this);
	}

	@Override
	public void procesa(Record record) {
		record.campos().procesa(this);
		record.setTipo(record.campos().getTipo());
	}

	@Override
	public void procesa(Pointer pointer) {
		//Segunda pasada
	}
	
}
