package procesamientos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Delete;
import asint.TinyASint.Distinto;
import asint.TinyASint.Div;
import asint.TinyASint.Exp;
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
import asint.TinyASint.String_cons;
import asint.TinyASint.Suma;
import asint.TinyASint.Tipo_Id;
import asint.TinyASint.True;
import asint.TinyASint.While_inst;
import asint.TinyASint.Write;

public class ComprobacionTipos extends ProcesamientoPorDefecto{
	
	private boolean error = false;
	
	public enum Tipo_Nodo{
		BOOL, OK, LIT_ENT, LIT_REAL, STRING, ERROR, ARRAY, RECORD, REF, NULL;
	}
	
	public boolean isCorrect() {
		return !error;
	}
	
	public boolean isDirty() {
		return error;
	}
	
	private void error(Genero g) {
		System.out.println("Error de tipos en " + g);
		error = true;
		g.setTipo(Tipo.ERROR);
	}

	private Tipo compatibleNumero(Tipo t0, Tipo t1) {
		if (t0.getTipo() == Tipo.LIT_ENT && t1.getTipo() == Tipo.LIT_ENT) {
			return Tipo.LIT_ENT;
		} else if (t0.getTipo() == Tipo.LIT_REAL && t1.getTipo() == Tipo.LIT_REAL) {
			return Tipo.LIT_REAL;
		}
		
		return Tipo.ERROR;
	}
	
	private boolean compatiblePointer(Tipo t0, Tipo t1) {
		if (!(t0.getTipo() == Tipo.REF)) return false;
		
		return t1.isNull() 
			|| (t1.isPointer() && compatible(((TTipo_Pointer)t0).of, ((TTipo_Pointer)t1).of));
	}
	
	private boolean compatibleArray(TTipo t0, TTipo t1) {
		return t0.isArray() && t1.isArray()
			&& compatible(((TTipo_Array)t0).of, ((TTipo_Array)t1).of);
	}
	
	private boolean compatibleRecord(TTipo t0, TTipo t1) {
		if (t0.isRecord() && t1.isRecord()) {
			TTipo_Record tr0 = (TTipo_Record)t0;
			TTipo_Record tr1 = (TTipo_Record)t1;
			
			if (tr0.campos.size() != tr1.campos.size()) return false;
			
			Iterator<Camp> it0 = tr0.campos.values().iterator();
			Iterator<Camp> it1 = tr1.campos.values().iterator();
			
			while (it0.hasNext() && it1.hasNext()) {
				if (!compatible(it0.next().getTipo(),it1.next().getTipo())) {
					return false;
				}
			}
			
			// Everything compatible
			return true;
		}
		
		return false;
	}
	
	private boolean compatibleCmp(TTipo t0, TTipo t1) {
		return (t0.isBool() && t1.isBool())
			|| (t0.isString() && t1.isString())
			|| (t0.isNum() && t1.isNum());
	}
	
	private boolean compatibleMismoBasico(TTipo t0, TTipo t1)  {
		return ((t0.isBool() && t1.isBool())
			|| (t0.isString() && t1.isString()));
	}
	
	private boolean compatible(TTipo t0, TTipo t1) {
		return (compatibleMismoBasico(t0,t1))
				|| compatibleNumero(t0,t1).isNum()
				|| compatiblePointer(t0,t1)
				|| compatibleArray(t0,t1)
				|| compatibleRecord(t0,t1);
	}
	
	// Programa

	public void procesa(Programa prog) {
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
		}
		prog.instrucciones().procesa(this);
		prog.setTipo(prog.instrucciones().getTipo());
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
		dec.pforms().procesa(this);
		dec.bloque().procesa(this);
		dec.setTipo(dec.bloque().getTipo());
	}

	@Override
	public void procesa(DecTipo dec) {
		dec.val().procesa(this);
	}

	@Override
	public void procesa(DecVar dec) {
		dec.val().procesa(this);
		dec.setTipo(dec.val().getTipo());
	}

	// Instrucciones
	

	@Override
	public void procesa(Bloque_inst bloque_inst) {
		bloque_inst.bloque().procesa(this);
		bloque_inst.setTipo(bloque_inst.bloque().getTipo());
	}
	
	@Override
	public void procesa(Call call) {
		call.exps().procesa(this);
		call.setTipo(call.exps().getTipo());
	}

	@Override
	public void procesa(Delete delete) {
		delete.exp().procesa(this);
		
		if (delete.exp().getTipo().isPointer()) {
			delete.setTipo(new TTipo_OK());
		} else {
			error(delete);
		}
	}

	@Override
	public void procesa(New_cons new_cons) {
		new_cons.exp().procesa(this);
		
		if (new_cons.exp().getTipo().isPointer()) {
			new_cons.setTipo(new TTipo_OK());
		} else {
			error(new_cons);
		}
	}

	@Override
	public void procesa(Nl nl) {
		nl.setTipo(new TTipo_OK());
	}

	@Override
	public void procesa(Write write) {
		write.exp().procesa(this);
		Exp e = write.exp();
		
		if (e.getTipo().isEntero() || e.getTipo().isReal() || e.getTipo().isString() || e.getTipo().isBool()) {
			write.setTipo(new TTipo_OK());
		} else {
			error(write);
		}
	}

	@Override
	public void procesa(Read read) {
		read.exp().procesa(this);
		Exp e = read.exp();
		
		if (e.esDesignador() && (e.getTipo().isEntero() || e.getTipo().isReal() || e.getTipo().isString())) {
			read.setTipo(new TTipo_OK());
		} else {
			error(read);
		}
	}

	@Override
	public void procesa(While_inst while_inst) {
		while_inst.exp().procesa(this);
		while_inst.instrucciones().procesa(this);
		
		if (while_inst.exp().getTipo().isBool() && while_inst.instrucciones().getTipo().isOK()) {
			while_inst.setTipo(new TTipo_OK());
		} else {
			error(while_inst);
		}
	}

	@Override
	public void procesa(If_else if_else) {
		if_else.exp().procesa(this);
		if_else.instrucciones().procesa(this);
		if_else.instrucciones_else().procesa(this);
		
		if (if_else.exp().getTipo().isBool() && if_else.instrucciones().getTipo().isOK() && if_else.instrucciones_else().getTipo().isOK()) {
			if_else.setTipo(new TTipo_OK());
		} else {
			error(if_else);
		}
	}

	@Override
	public void procesa(If_inst if_inst) {
		if_inst.exp().procesa(this);
		if_inst.instrucciones().procesa(this);
		
		if (if_inst.exp().getTipo().isBool() && if_inst.instrucciones().getTipo().isOK()) {
			if_inst.setTipo(new TTipo_OK());
		} else {
			error(if_inst);
		}
	}

	@Override
	public void procesa(Asig asig) {
		asig.exp0().procesa(this);
		asig.exp1().procesa(this);
		
		if (asig.exp0().esDesignador()) {
			if (compatible(asig.exp0().getTipo(), asig.exp1().getTipo())) {
				asig.setTipo(new TTipo_OK());
			} else {
				error(asig);
			}	
		} else {
			error(asig);
			System.out.println("La parte izquierda no es un designador");
		}
	}

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		insts.instruccion().procesa(this);
		if (insts.instrucciones().getTipo().isOK()
			&& insts.instruccion().getTipo().isOK()) {
			insts.setTipo(new TTipo_OK());
			
		} else {
			error(insts);
		}
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
		insts.setTipo(insts.instruccion().getTipo());
	}
	
	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
		lista_inst_empty.setTipo(new TTipo_OK());
	}

	@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		lista_inst_una.instruccion().procesa(this);
		lista_inst_una.setTipo(lista_inst_una.instruccion().getTipo());
	}

	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		lista_inst_muchas.instrucciones().procesa(this);
		lista_inst_muchas.instruccion().procesa(this);
		if (lista_inst_muchas.instrucciones().getTipo().isOK()
			&& lista_inst_muchas.instruccion().getTipo().isOK()) {
			lista_inst_muchas.setTipo(new TTipo_OK());
			
		} else {
			error(lista_inst_muchas);
		}
	}
	
	
	// Param Formales


	@Override
	public void procesa(ParamForm paramForm) {
		paramForm.setTipo(paramForm.tipo().getTipo());
	}
	

	@Override
	public void procesa(Pformal_ref paramForm) {
		paramForm.setTipo(paramForm.tipo().getTipo());
	}

	@Override
	public void procesa(ParamForms_muchos paramForms_muchos) {
		paramForms_muchos.params().procesa(this);
		paramForms_muchos.param().procesa(this);
		if (paramForms_muchos.params().getTipo().isOK()) {
			paramForms_muchos.setTipo(paramForms_muchos.param().getTipo());
		} else {
			error(paramForms_muchos);
		}
	}

	@Override
	public void procesa(ParamForms_uno paramForms_uno) {
		paramForms_uno.param().procesa(this);
		paramForms_uno.setTipo(paramForms_uno.param().getTipo());
	}

	@Override
	public void procesa(ParamForms_empty paramForms_empty) {
		paramForms_empty.setTipo(new TTipo_OK());
	}
	
	// Campos
	
	@Override
	public void procesa(Campos_muchos campos_muchos) {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
		campos_muchos.setRecord(new TTipo_Record(campos_muchos.campos().getRecord(), campos_muchos.campo()));
	}

	@Override
	public void procesa(Campo_uno campo_uno) {
		campo_uno.campo().procesa(this);
		campo_uno.setRecord(new TTipo_Record(campo_uno.campo()));
	}

	@Override
	public void procesa(Camp camp) {
		camp.tipo().procesa(this);
		camp.setTipo(camp.tipo().getTipo());
	}
	
	
	// Bloque 
	
	@Override
	public void procesa(Bloque_prog bloque_prog) {
		bloque_prog.programa().procesa(this);
		bloque_prog.setTipo(bloque_prog.programa().getTipo());
	}

	@Override
	public void procesa(No_bloque no_bloque) {
		no_bloque.setTipo(new TTipo_OK());
	}

	
	// Expresiones
	
	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		lista_exp_empty.setTipo(new TTipo_OK());
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
		exp_muchas.expresion().procesa(this);
		if (exp_muchas.expresiones().getTipo().isOK()) {
			exp_muchas.setTipo(exp_muchas.expresion().getTipo());
		} else {
			error(exp_muchas);
		}
	}

	@Override
	public void procesa(Exp_una exp_una) {
		exp_una.expresion().procesa(this);
		exp_una.setTipo(exp_una.expresion().getTipo());
	}

	// Operadores

	// Nivel 0
	public void procesa(Suma exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isEntero() && exp.arg1().getTipo().isEntero()) {
			exp.setTipo(new TTipo_Entero());
		} else if (exp.arg0().getTipo().isNum() && exp.arg1().getTipo().isNum()) {
			exp.setTipo(new TTipo_Real());
		} else {
			error(exp);
		}
	}

	public void procesa(Resta exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isEntero() && exp.arg1().getTipo().isEntero()) {
			exp.setTipo(new TTipo_Entero());
		} else if (exp.arg0().getTipo().isNum() && exp.arg1().getTipo().isNum()) {
			exp.setTipo(new TTipo_Real());
		} else {
			error(exp);
		}
	}

	// Nivel 1
	public void procesa(And exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isBool() && exp.arg1().getTipo().isBool()) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	public void procesa(Or exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isBool() && exp.arg1().getTipo().isBool()) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	// Nivel 2
	public void procesa(Menor exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0().getTipo(), exp.arg1().getTipo())) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	public void procesa(Mayor exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0().getTipo(), exp.arg1().getTipo())) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	public void procesa(MenorIgual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0().getTipo(), exp.arg1().getTipo())) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	public void procesa(MayorIgual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0().getTipo(), exp.arg1().getTipo())) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	public void procesa(Igual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0().getTipo(), exp.arg1().getTipo())) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	public void procesa(Distinto exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0().getTipo(), exp.arg1().getTipo())) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}

	// Nivel 3
	public void procesa(Mul exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isEntero() && exp.arg1().getTipo().isEntero()) {
			exp.setTipo(new TTipo_Entero());
		} else if (exp.arg0().getTipo().isNum() && exp.arg1().getTipo().isNum()) {
			exp.setTipo(new TTipo_Real());
		} else {
			error(exp);
		}
	}

	public void procesa(Div exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isEntero() && exp.arg1().getTipo().isEntero()) {
			exp.setTipo(new TTipo_Entero());
		} else if (exp.arg0().getTipo().isNum() && exp.arg1().getTipo().isNum()) {
			exp.setTipo(new TTipo_Real());
		} else {
			error(exp);
		}
	}

	@Override
	public void procesa(Percent exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo().isEntero() && exp.arg1().getTipo().isEntero()) {
			exp.setTipo(new TTipo_Entero());
		} else if (exp.arg0().getTipo().isNum() && exp.arg1().getTipo().isNum()) {
			exp.setTipo(new TTipo_Real());
		} else {
			error(exp);
		}
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		exp.arg0().procesa(this);
		
		if (exp.arg0().getTipo().isEntero()) {
			exp.setTipo(new TTipo_Entero());
		} else if (exp.arg0().getTipo().isNum()) {
			exp.setTipo(new TTipo_Real());
		} else {
			error(exp);
		}
	}

	public void procesa(Not exp) {
		exp.arg0().procesa(this);
		
		if (exp.arg0().getTipo().isBool()) {
			exp.setTipo(new TTipo_Bool());
		} else {
			error(exp);
		}
	}
	
	//Nivel 5
	
	@Override
	public void procesa(Corchete corchete) {
		corchete.arg0().procesa(this);
		corchete.arg1().procesa(this);
		
		if (corchete.arg1().getTipo().isEntero() && corchete.arg0().getTipo().isArray()) {
			corchete.setTipo(((TTipo_Ref) corchete.arg0().getTipo()).of);
		} else {
			error(corchete);
		}
	}

	@Override
	public void procesa(Punto punto) {
		punto.exp().procesa(this);
		if (punto.exp().getTipo().isRecord()) {
			TTipo_Record r = (TTipo_Record) punto.exp().getTipo();
			
			if (r.campos.containsKey(punto.id().toString())) {
				punto.setTipo(r.campos.get(punto.id().toString()).getTipo());
			} else {
				error(punto);
				System.out.println("  > Nombre de campo "+punto.id()+" desconocido");
			}
		} else {
			error(punto);
		}
	}

	@Override
	public void procesa(Flecha flecha) {
		flecha.exp().procesa(this);
		if (flecha.exp().getTipo().isPointer()) {
			TTipo_Ref p = (TTipo_Ref) flecha.exp().getTipo();
			
			if (p.of.isRecord()) {
				TTipo_Record r = (TTipo_Record) p.of;
				
				if (r.campos.containsKey(flecha.id().toString())) {
					flecha.setTipo(r.campos.get(flecha.id().toString()).getTipo());
				} else {
					error(flecha);
					System.out.println("  > Nombre de campo "+flecha.id()+" desconocido");
				}
			} else {
				error(flecha);
			}
		} else {
			error(flecha);
		}
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		star.arg0().procesa(this);
		
		if (star.arg0().getTipo().isPointer()) {
			star.setTipo(((TTipo_Pointer)star.arg0().getTipo()).of);
		} else {
			error(star);
		}
	}

	// Nivel 7
	public void procesa(True exp) {
		exp.setTipo(new TTipo_Bool());
	}

	public void procesa(False exp) {
		exp.setTipo(new TTipo_Bool());
	}

	public void procesa(LitReal exp) {
		exp.setTipo(new TTipo_Real());
	}

	public void procesa(Id exp) {
		exp.setTipo(exp.getVinculo().val().getTipo());
	}

	public void procesa(LitEnt exp) {
		exp.setTipo(new TTipo_Entero());
	}

	@Override
	public void procesa(LitNull exp) {
		exp.setTipo(new TTipo_Null());
	}

	@Override
	public void procesa(LitCad exp) {
		exp.setTipo(new TTipo_String());
	}

	// Tipo

	@Override
	public void procesa(Bool bool) {
		bool.setTipo(new TTipo_Bool());
	}

	@Override
	public void procesa(Int int1) {
		int1.setTipo(new TTipo_Entero());
	}

	@Override
	public void procesa(Real real) {
		real.setTipo(new TTipo_Real());
	}

	@Override
	public void procesa(String_cons string_cons) {
		string_cons.setTipo(new TTipo_String());
	}

	@Override
	public void procesa(Tipo_Id tipo_Id) {
		tipo_Id.setTipo(tipo_Id.getVinculo().getTipo());
	}

	@Override
	public void procesa(Array array) {
		array.tipo_array().procesa(this);
		array.setTipo(new TTipo_Array(array.size, array.tipo_array().getTipo()));
	}

	@Override
	public void procesa(Record record) {
		record.campos().procesa(this);
		record.setTipo(record.campos().getTipo());
	}

	@Override
	public void procesa(Pointer pointer) {
		pointer.tipo().procesa(this);
		pointer.setTipo(new TTipo_Pointer(pointer.tipo().getTipo()));
	}
	
}

