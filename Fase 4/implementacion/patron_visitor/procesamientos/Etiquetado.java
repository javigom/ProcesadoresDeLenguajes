package procesamientos;

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
import asint.TinyASint.Exp_muchas;
import asint.TinyASint.Exp_una;
import asint.TinyASint.Exps;
import asint.TinyASint.False;
import asint.TinyASint.Flecha;
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
import procesamientos.ComprobacionTipos.tNodo;

public class Etiquetado extends ProcesamientoPorDefecto{
	private int etq = 0;
	
	
	// Programa
	
	public void procesa(Programa prog) {
		prog.etqi = etq;
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
		}
		prog.instrucciones().procesa(this);
		prog.etqs = etq;
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
		dec.etqi = etq;
		dec.bloque().procesa(this);
		dec.etqs = etq;
	}
	
	@Override
	public void procesa(DecTipo dec) {
	}
	
	@Override
	public void procesa(DecVar dec) {
	}
	
	// Instrucciones
	
	
	@Override
	public void procesa(Bloque_inst bloque_inst) {
		bloque_inst.bloque().procesa(this);
	}
	
	@Override
	public void procesa(Call call) {	
		call.etqi = etq;
		etq ++;
		//call.exps();
		int i = 0;
		Exps exps = call.exps();
		if(exps instanceof Exp_una){
			etq += 3;
			((Exp_una) exps).expresion().procesa(this);
			etq ++;
			i++;
		}
		etq += 2; 
		call.etqs = etq;
	}
	
	@Override
	public void procesa(Delete delete) {
		delete.etqi = etq;
		delete.exp().procesa(this);
		etq++;
		delete.etqs = etq;
	}
	
	@Override
	public void procesa(New_cons new_cons) {
		new_cons.etqi = etq;
		new_cons.exp().procesa(this);
		etq += 2;
		new_cons.etqs = etq;
	}
	
	@Override
	public void procesa(Nl nl) {
		nl.etqi = etq;
		etq += 2;
		nl.etqs = etq;
	}
	
	@Override
	public void procesa(Write write) {
		write.etqi = etq;
		write.exp().procesa(this);
		if (write.exp().esDesignador()) {
			etq++;
		}
		etq++;
		write.etqs = etq;
	}
	
	@Override
	public void procesa(Read read) {
		read.etqi = etq;
		read.exp().procesa(this);
		etq += 2;
		read.etqs = etq;
	}
	
	@Override
	public void procesa(While_inst while_inst) {
		while_inst.etqi = etq;
		while_inst.exp().procesa(this);
		etq++;
		while_inst.instrucciones().procesa(this);
		etq++;
		while_inst.etqs = etq;
	}
	
	@Override
	public void procesa(If_else if_else) {
		if_else.etqi = etq;
		if_else.exp().procesa(this);
		etq++;
		if_else.instrucciones().procesa(this);
		etq++;
		if_else.instrucciones_else().procesa(this);
		if_else.etqs = etq;
	}
	
	@Override
	public void procesa(If_inst if_inst) {
		if_inst.etqi = etq;
		if_inst.exp().procesa(this);
		etq++;
		if_inst.instrucciones().procesa(this);
		if_inst.etqs = etq;
	}
	
	@Override
	public void procesa(Asig asig) {
		asig.etqi = etq;
		asig.exp0().procesa(this);
		asig.exp1().procesa(this);
		etq++;
		asig.etqs = etq;
	}
	
	public void procesa(Insts_muchas insts) {
		insts.etqi = etq;
		insts.instrucciones().procesa(this);
		insts.instruccion().procesa(this);
		insts.etqs = etq;
	}
	
	public void procesa(Insts_una insts) {
		insts.etqi = etq;
		insts.instruccion().procesa(this);
		insts.etqs = etq;
	}
	
	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
		lista_inst_empty.etqi = etq;
		lista_inst_empty.etqs = etq;
	}
	
	@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		lista_inst_una.etqi = etq;
		lista_inst_una.instruccion().procesa(this);
		lista_inst_una.etqs = etq;
	}
	
	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		lista_inst_muchas.etqi = etq;
		lista_inst_muchas.instrucciones().procesa(this);
		lista_inst_muchas.instruccion().procesa(this);
		lista_inst_muchas.etqs = etq;
	}
	
	
	// Param Formales
	
	
	@Override
	public void procesa(ParamForm paramForm) {
	}
	
	
	@Override
	public void procesa(Pformal_ref paramForm) {
	}
	
	@Override
	public void procesa(ParamForms_muchos paramForms_muchos) {
	}
	
	@Override
	public void procesa(ParamForms_uno paramForms_uno) {
	}
	
	@Override
	public void procesa(ParamForms_empty paramForms_empty) {
	}
	
	// Campos
	
	@Override
	public void procesa(Campos_muchos campos_muchos) {
	}
	
	@Override
	public void procesa(Campo_uno campo_uno) {
	}
	
	@Override
	public void procesa(Camp camp) {
	}
	
	
	// Bloque 
	
	@Override
	public void procesa(Bloque_prog bloque_prog) {
		bloque_prog.programa().procesa(this);
	}
	
	@Override
	public void procesa(No_bloque no_bloque) {
	}
	
	
	// Expresiones
	
	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
	}
	
	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.etqi = etq;
		exp_muchas.expresiones().procesa(this);
		exp_muchas.expresion().procesa(this);
	}
	
	@Override
	public void procesa(Exp_una exp_una) {
		exp_una.etqi = etq;
		exp_una.expresion().procesa(this);
	}
	
	// Operadores
	
	// Nivel 0
	public void procesa(Suma exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(Resta exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	// Nivel 1
	public void procesa(And exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(Or exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	// Nivel 2
	public void procesa(Menor exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		if (exp.arg0().getTipo() == tNodo.BOOL) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq += 2;
		exp.etqs = etq;
	}
	
	public void procesa(Mayor exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq += 2;
		exp.etqs = etq;
	}
	
	public void procesa(MenorIgual exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq += 2;
		exp.etqs = etq;
	}
	
	public void procesa(MayorIgual exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq += 2;
		exp.etqs = etq;
	}
	
	public void procesa(Igual exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq += 2;
		exp.etqs = etq;
	}
	
	public void procesa(Distinto exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq += 2;
		exp.etqs = etq;
	}
	
	// Nivel 3
	public void procesa(Mul exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(Div exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	@Override
	public void procesa(Percent exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	// Nivel 4
	public void procesa(MenosUnario exp) {
		exp.etqi = etq;
		etq++;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(Not exp) {
		exp.etqi = etq;
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) etq++;
		etq++;
		exp.etqs = etq;
	}
	
	//Nivel 5
	
	@Override
	public void procesa(Corchete corchete) {
		corchete.etqi = etq;
		corchete.arg0().procesa(this);
		corchete.arg1().procesa(this);
		if (corchete.arg1().esDesignador()) etq++;
		etq += 3;
		corchete.etqs = etq;
	}
	
	@Override
	public void procesa(Punto punto) {
		punto.etqi = etq;
		punto.exp().procesa(this);
		etq += 2;
		punto.etqs = etq;
	}
	
	@Override
	public void procesa(Flecha flecha) {
		flecha.etqi = etq;
		flecha.exp().procesa(this);
		etq += 3;
		flecha.etqs = etq;
	}
	
	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		star.etqi = etq;
		star.arg0().procesa(this);
		etq++;
		star.etqs = etq;
	}
	
	// Nivel 7
	public void procesa(True exp) {
		exp.etqi = etq;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(False exp) {
		exp.etqi = etq;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(LitReal exp) {
		exp.etqi = etq;
		etq++;
		exp.etqs = etq;
	}
	
	public void procesa(Id exp) {
		exp.etqi = etq;
		if (exp.nivel == 0) {
			etq++;
		} else {
			etq += 3;
		}
		exp.etqs = etq;
	}
	
	public void procesa(LitEnt exp) {
		exp.etqi = etq;
		etq++;
		exp.etqs = etq;
	}
	
	@Override
	public void procesa(LitNull exp) {
		exp.etqi = etq;
		etq++;
		exp.etqs = etq;
	}
	
	@Override
	public void procesa(LitCad exp) {
		exp.etqi = etq;
		etq++;
		exp.etqs = etq;
	}
	
	// Tipo
	
	@Override
	public void procesa(Bool bool){
	}
	
	@Override
	public void procesa(Int int1) {
	}
	
	@Override
	public void procesa(Real real) {
	}
	
	@Override
	public void procesa(String_cons string_cons) {
	}
	
	@Override
	public void procesa(Tipo_Id tipo_Id) {
	}
	
	@Override
	public void procesa(Array array) {
		
	}
	
	@Override
	public void procesa(Record record) {
	}
	
	@Override
	public void procesa(Pointer pointer) {
	}

	
}

