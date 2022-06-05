package procesamientos;

import java.util.Iterator;

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
import asint.TinyASint.Camps;
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
import asint.TinyASint.Nodo;
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
import asint.TinyASint.Tipo;
import asint.TinyASint.Tipo_Id;
import asint.TinyASint.True;
import asint.TinyASint.While_inst;
import asint.TinyASint.Write;

public class ComprobacionTipos extends ProcesamientoPorDefecto{
	
	private boolean error = false;
	
	public enum tNodo{
		BOOL, OK, LIT_ENT, LIT_REAL, STRING, ERROR, ARRAY, RECORD, NULL, POINTER;
	}
	
	public void printError(Nodo g) {
		System.out.println("Error de tipos en " + g);
		error = true;
		g.setTipo(tNodo.ERROR);
	}
	
	public boolean errorComprobacionTipos() {
		return error;
	}
	
	private boolean compatibleNumero(tNodo t0, tNodo t1) {
		if (t0 == tNodo.LIT_ENT && t1 == tNodo.LIT_ENT) {
			return true;
		} else if (t0 == tNodo.LIT_REAL && t1 == tNodo.LIT_REAL) {
			return true;
		}
		
		return false;
	}
	
	private boolean compatiblePointer(Nodo t0, Nodo t1) {
		if (!(t0.getTipo() == tNodo.POINTER)) return false;
		if (t1.getTipo() == tNodo.NULL) return true;
		
		if(t0 instanceof Flecha) {
			t0 = ((Flecha) t0).exp();
		}
		if(t1 instanceof Flecha) {
			t1 = ((Flecha) t1).exp();
		}
		
		if(t0.getVinculo() != null && t1.getVinculo() != null) {
			return ((Tipo_Id) t0.getVinculo().val()).tipoString().toString().equals(((Tipo_Id) t0.getVinculo().val()).tipoString().toString()) || compatible(t0.getVinculo().val(), t1.getVinculo().val());
		} else if (t0.tipo() != null && t1.tipo() != null) {
			return compatible(t0.tipo().getVinculo().val(), t1.tipo().getVinculo().val());
		} else if (t1.getTipo() == tNodo.POINTER) {
			return compatible(t0.getTipoNodo(), t1.getTipoNodo());
		}
		
		return false;
	}
	
	private boolean compatibleArray(Nodo t0, Nodo t1) {
		return t0.getTipo() == tNodo.ARRAY && t1.getTipo() == tNodo.ARRAY 
			&& compatible(t0.getTipoNodoArray(), t1.getTipoNodoArray());
	}
	
	private boolean compatibleRecord(Nodo t0, Nodo t1) {
		if (t0.getTipo() ==  tNodo.RECORD && t1.getTipo() ==  tNodo.RECORD) {
			
			if (((Record) t0).campos().getCampos().size() != ((Record) t1).campos().getCampos().size()) return false;
			
			Iterator<Camp> it0 = ((Record) t0).campos().getCampos().values().iterator();
			Iterator<Camp> it1 = ((Record) t1).campos().getCampos().values().iterator();
			
			while (it0.hasNext() && it1.hasNext()) {
				if (!compatible(it0.next(),it1.next())) {
					return false;
				}
			}
			
			// Everything compatible
			return true;
		}
		
		return false;
	}
	
	private boolean compatibleCmp(tNodo t0, tNodo t1) {
		return (t0 == tNodo.BOOL && t1 == tNodo.BOOL)
			|| (t0 == tNodo.STRING && t1 == tNodo.STRING)
			|| (t0 == tNodo.LIT_ENT && t1 == tNodo.LIT_ENT)
			|| (t0 == tNodo.LIT_REAL && t1 == tNodo.LIT_REAL);
	}
	
	private boolean compatibleMismoBasico(tNodo t0, tNodo t1)  {
		return ((t0 == tNodo.BOOL && t1 == tNodo.BOOL)
			|| (t0 == tNodo.STRING && t1 == tNodo.STRING));
	}
	
	private boolean compatible(tNodo t0, tNodo t1) {
		return (compatibleMismoBasico(t0,t1))
				|| compatibleNumero(t0,t1);
	}
	
	private boolean compatible(Nodo t0, Nodo t1) {
		return (compatibleMismoBasico(t0.getTipo(),t1.getTipo()))
				|| compatibleNumero(t0.getTipo(),t1.getTipo())
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
		
		if (call.getVinculo().getTipo() == tNodo.OK) {
			call.setTipo(tNodo.OK);
		} else {
			printError(call);
		}
	}

	@Override
	public void procesa(Delete delete) {
		delete.exp().procesa(this);
		
		if (delete.exp().getTipo() == tNodo.POINTER) {
			delete.setTipo(tNodo.OK);
		} else {
			printError(delete);
		}
	}

	@Override
	public void procesa(New_cons new_cons) {
		new_cons.exp().procesa(this);
		
		if (new_cons.exp().getTipo() == tNodo.POINTER) {
			new_cons.setTipo(tNodo.OK);
		} else {
			printError(new_cons);
		}
	}

	@Override
	public void procesa(Nl nl) {
		nl.setTipo(tNodo.OK);
	}

	@Override
	public void procesa(Write write) {
		write.exp().procesa(this);
		Exp e = write.exp();
		
		if (e.getTipo() == tNodo.LIT_ENT || e.getTipo() == tNodo.LIT_REAL || e.getTipo() == tNodo.STRING || e.getTipo() == tNodo.BOOL) {
			write.setTipo(tNodo.OK);
		} else {
			printError(write);
		}
	}

	@Override
	public void procesa(Read read) {
		read.exp().procesa(this);
		Exp e = read.exp();
		
		if (e.esDesignador() && (e.getTipo() == tNodo.LIT_ENT || e.getTipo() == tNodo.LIT_REAL || e.getTipo() == tNodo.STRING)) {
			read.setTipo(tNodo.OK);
		} else {
			printError(read);
		}
	}

	@Override
	public void procesa(While_inst while_inst) {
		while_inst.exp().procesa(this);
		while_inst.instrucciones().procesa(this);
		
		if (while_inst.exp().getTipo() == tNodo.BOOL && while_inst.instrucciones().getTipo() == tNodo.OK) {
			while_inst.setTipo(tNodo.OK);
		} else {
			printError(while_inst);
		}
	}

	@Override
	public void procesa(If_else if_else) {
		if_else.exp().procesa(this);
		if_else.instrucciones().procesa(this);
		if_else.instrucciones_else().procesa(this);
		
		if (if_else.exp().getTipo() == tNodo.BOOL && if_else.instrucciones().getTipo() == tNodo.OK && if_else.instrucciones_else().getTipo() == tNodo.OK) {
			if_else.setTipo(tNodo.OK);
		} else {
			printError(if_else);
		}
	}

	@Override
	public void procesa(If_inst if_inst) {
		if_inst.exp().procesa(this);
		if_inst.instrucciones().procesa(this);
		
		if (if_inst.exp().getTipo() == tNodo.BOOL && if_inst.instrucciones().getTipo() == tNodo.OK) {
			if_inst.setTipo(tNodo.OK);
		} else {
			printError(if_inst);
		}
	}

	@Override
	public void procesa(Asig asig) {
		asig.exp0().procesa(this);
		asig.exp1().procesa(this);
		
		if (asig.exp0().esDesignador()) {
			if (compatible(asig.exp0(), asig.exp1())) {
				asig.setTipo(tNodo.OK);
			} else {
				printError(asig);
			}	
		} else {
			printError(asig);
			System.out.println("La parte izquierda no es un designador");
		}
	}

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		insts.instruccion().procesa(this);
		if (insts.instrucciones().getTipo() == tNodo.OK
			&& insts.instruccion().getTipo() == tNodo.OK) {
			insts.setTipo(tNodo.OK);
		} else {
			printError(insts);
		}
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
		insts.setTipo(insts.instruccion().getTipo());
	}
	
	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
		lista_inst_empty.setTipo(tNodo.OK);
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
		if (lista_inst_muchas.instrucciones().getTipo() == tNodo.OK
			&& lista_inst_muchas.instruccion().getTipo() == tNodo.OK) {
			lista_inst_muchas.setTipo(tNodo.OK);
			
		} else {
			printError(lista_inst_muchas);
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
		if (paramForms_muchos.params().getTipo() == tNodo.OK) {
			paramForms_muchos.setTipo(paramForms_muchos.param().getTipo());
		} else {
			printError(paramForms_muchos);
		}
	}

	@Override
	public void procesa(ParamForms_uno paramForms_uno) {
		paramForms_uno.param().procesa(this);
		paramForms_uno.setTipo(paramForms_uno.param().getTipo());
	}

	@Override
	public void procesa(ParamForms_empty paramForms_empty) {
		paramForms_empty.setTipo(tNodo.OK);
	}
	
	// Campos
	
	@Override
	public void procesa(Campos_muchos campos_muchos) {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
		campos_muchos.setRecord(campos_muchos.campos(), campos_muchos.campo());
	}

	@Override
	public void procesa(Campo_uno campo_uno) {
		campo_uno.campo().procesa(this);
		campo_uno.setRecord(campo_uno.campo());
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
		no_bloque.setTipo(tNodo.OK);
	}

	
	// Expresiones
	
	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		lista_exp_empty.setTipo(tNodo.OK);
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
		exp_muchas.expresion().procesa(this);
		if (exp_muchas.expresiones().getTipo() == tNodo.OK) {
			exp_muchas.setTipo(exp_muchas.expresion().getTipo());
		} else {
			printError(exp_muchas);
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
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT && exp.arg1().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL && exp.arg1().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Resta exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT && exp.arg1().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL && exp.arg1().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}

	// Nivel 1
	public void procesa(And exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.BOOL && exp.arg1().getTipo() == tNodo.BOOL) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Or exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.BOOL && exp.arg1().getTipo() == tNodo.BOOL) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	// Nivel 2
	public void procesa(Menor exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0(), exp.arg1())) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Mayor exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0(), exp.arg1())) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	public void procesa(MenorIgual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0(), exp.arg1())) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	public void procesa(MayorIgual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0(), exp.arg1())) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Igual exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0(), exp.arg1())) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Distinto exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (compatible(exp.arg0(), exp.arg1())) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}

	// Nivel 3
	public void procesa(Mul exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT && exp.arg1().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL && exp.arg1().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Div exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT && exp.arg1().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL && exp.arg1().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}

	@Override
	public void procesa(Percent exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT && exp.arg1().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL && exp.arg1().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		exp.arg0().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			exp.setTipo(tNodo.LIT_ENT);
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			exp.setTipo(tNodo.LIT_REAL);
		} else {
			printError(exp);
		}
	}

	public void procesa(Not exp) {
		exp.arg0().procesa(this);
		
		if (exp.arg0().getTipo() == tNodo.BOOL) {
			exp.setTipo(tNodo.BOOL);
		} else {
			printError(exp);
		}
	}
	
	//Nivel 5
	
	@Override
	public void procesa(Corchete corchete) {
		corchete.arg0().procesa(this);
		corchete.arg1().procesa(this);
		
		if (corchete.arg1().getTipo() == tNodo.LIT_ENT && corchete.arg0().getTipo() == tNodo.ARRAY) {
			DecVar var = (DecVar) ((Id) ((Punto) corchete.arg0()).exp()).getVinculo();
			String nombre = ((Punto) corchete.arg0()).id().toString();
			Record r = (Record) var.val().getVinculo().val();
			corchete.setTipo(r.campos().getCampos().get(nombre).tipo().getTipoNodoArray());
		} else {
			printError(corchete);
		}
	}

	@Override
	public void procesa(Punto punto) {
		punto.exp().procesa(this);
		if (punto.exp().getTipo() == tNodo.RECORD) {
			Camps c = ((Record) ((Tipo_Id) ((Id) punto.exp()).getVinculo().val()).getVinculo().val()).campos();
			if (c.getCampos().containsKey(punto.id().toString())) {
				punto.setTipo(c.getCampos().get(punto.id().toString()).getTipo());
			} else {
				printError(punto);
				System.out.println("  > Nombre de campo "+punto.id()+" desconocido");
			}
		} else {
			printError(punto);
		}
	}

	@Override
	public void procesa(Flecha flecha) {
		flecha.exp().procesa(this);
		if (flecha.exp().getTipo() == tNodo.POINTER) {
			Exp t = flecha.exp();
			while (!(t instanceof Id)) {
				t = ((Flecha) t).exp();
			}
			Tipo subtipo = ((Pointer) ((Id) t).getVinculo().val().getVinculo().val()).tipo().getVinculo().val(); ////////////////////////////////////////////////////////////////////////////////////////////////////
			if (subtipo.getTipo() == tNodo.RECORD) {
				Camps c = ((Record) subtipo).campos();
				if (c.getCampos().containsKey(flecha.id().toString())) {
					flecha.setTipo(c.getCampos().get(flecha.id().toString()).getTipo());
				} else {
					printError(flecha);
					System.out.println("  > Nombre de campo "+flecha.id()+" desconocido");
				}
			} else {
				printError(flecha);
			}
		} else {
			printError(flecha);
		}
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		star.arg0().procesa(this);
		
		if (star.arg0().getTipo() == tNodo.POINTER) {
			star.setTipo(star.arg0().getTipo());
		} else {
			printError(star);
		}
	}

	// Nivel 7
	public void procesa(True exp) {
		exp.setTipo(tNodo.BOOL);
	}

	public void procesa(False exp) {
		exp.setTipo(tNodo.BOOL);
	}

	public void procesa(LitReal exp) {
		exp.setTipo(tNodo.LIT_REAL);
	}

	public void procesa(Id exp) {
		Tipo expresion = exp.getVinculo().val();
		if (expresion.getTipo() == null) {
			Tipo expresion2 = (Tipo) expresion.getVinculo().val();
			exp.setTipo(expresion2.getTipo());
		}
		else {
			exp.setTipo(expresion.getTipo());
		}
	}

	public void procesa(LitEnt exp) {
		exp.setTipo(tNodo.LIT_ENT);
	}

	@Override
	public void procesa(LitNull exp) {
		exp.setTipo(tNodo.NULL);
	}

	@Override
	public void procesa(LitCad exp) {
		exp.setTipo(tNodo.STRING);
	}

	// Tipo

	@Override
	public void procesa(Bool bool) {
		bool.setTipo(tNodo.BOOL);
	}

	@Override
	public void procesa(Int int1) {
		int1.setTipo(tNodo.LIT_ENT);
	}

	@Override
	public void procesa(Real real) {
		real.setTipo(tNodo.LIT_REAL);
	}

	@Override
	public void procesa(String_cons string_cons) {
		string_cons.setTipo(tNodo.STRING);
	}

	@Override
	public void procesa(Tipo_Id tipo_Id) {
		tipo_Id.setTipo(tipo_Id.getVinculo().val().getTipo());
	}

	@Override
	public void procesa(Array array) {
		array.tipo_array().procesa(this);
		array.setArray(array.tipo_array().getTipo());
	}

	@Override
	public void procesa(Record record) {
		record.campos().procesa(this);
		record.setTipo(record.campos().getTipo());
	}

	@Override
	public void procesa(Pointer pointer) {
		pointer.tipo().procesa(this);
		pointer.setPointer(pointer.tipo().getTipo());
	}
	
}

