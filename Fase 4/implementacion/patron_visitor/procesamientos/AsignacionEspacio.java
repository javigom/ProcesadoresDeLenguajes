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
import asint.TinyASint.Exp;
import asint.TinyASint.Exp_muchas;
import asint.TinyASint.Exp_una;
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

public class AsignacionEspacio extends ProcesamientoPorDefecto{
	private int dir = 0;
	private int nivel = 0;
	private int nivelMaximo = 0;
	
	public int getMaxNivel() {
		return nivelMaximo;
	}
	
	// Programa

	public void procesa(Programa prog) {
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
		}
		prog.instrucciones().procesa(this);
		prog.size = dir;
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
		int dir_ant = dir;
		nivel++;
		nivelMaximo = Math.max(nivel, nivelMaximo);
		dir = 0;
		
		dec.pforms().procesa(this);
		dec.bloque().procesa(this);
		
		dec.nivel = nivel;
		dec.size = dir;
	
		nivel--;
		dir = dir_ant;
	}

	@Override
	public void procesa(DecTipo dec) {
		dec.val().procesa(this);
		dec.size = dec.val().size;
	}

	@Override
	public void procesa(DecVar dec) {
		dec.dir = dir;
		dec.nivel = nivel;
		
		dec.val().procesa(this);
		
		dec.size = dec.val().size;
		dec.basesize = dec.val().basesize;
		dir += dec.size;
	}

	// Instrucciones
	

	@Override
	public void procesa(Bloque_inst bloque_inst) {
		bloque_inst.bloque().procesa(this);
	}
	
	@Override
	public void procesa(Call call) {
		call.exps().procesa(this);
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
		paramForm.tipo().procesa(this);
		paramForm.dir = dir;
		paramForm.nivel = nivel;
		paramForm.size = paramForm.tipo().size;
		dir += paramForm.size;
	}
	

	@Override
	public void procesa(Pformal_ref paramForm) {
		paramForm.tipo().procesa(this);
		paramForm.dir = dir;
		paramForm.nivel = nivel;
		paramForm.size = 1;
		dir++;
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
		campos_muchos.campo().despl = campos_muchos.campos().size; 
		campos_muchos.size = campos_muchos.campos().size + campos_muchos.campo().size;
	}

	@Override
	public void procesa(Campo_uno campo_uno) {
		campo_uno.campo().procesa(this);
		campo_uno.size = campo_uno.campo().size;
		campo_uno.campo().despl = 0;
	}

	@Override
	public void procesa(Camp camp) {
		camp.tipo().procesa(this);
		camp.size = camp.tipo().size;
		camp.basesize = camp.tipo().basesize;
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
		System.out.println();
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		exp_muchas.expresiones().procesa(this);
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
		corchete.size = corchete.arg0().basesize;
	}

	@Override
	public void procesa(Punto punto) {
		punto.exp().procesa(this);
		Camp c = ((Record) punto.exp().getVinculo().val().getVinculo().val()).campos().getCampos().get(punto.id().toString());
		punto.size = c.size;
		punto.basesize = c.basesize;
	}

	@Override
	public void procesa(Flecha flecha) {
		flecha.exp().procesa(this);
		Exp t = flecha.exp();
		while (!(t instanceof Id)) {
			t = ((Flecha) t).exp();
		}
		Record r = (Record) t.getVinculo().val().getVinculo().val().tipo().getVinculo().val();
		Camp c = r.campos().getCampos().get(flecha.id().toString());
		flecha.size = c.size;
		flecha.basesize = c.basesize;
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		star.arg0().procesa(this);
		star.size = 1;
		star.basesize = star.arg0().size;
	}

	// Nivel 7
	public void procesa(True exp) {
	}

	public void procesa(False exp) {
	}

	public void procesa(LitReal exp) {
	}

	public void procesa(Id exp) {
		exp.dir = exp.getVinculo().dir;
		exp.nivel = exp.getVinculo().nivel;
	}

	public void procesa(LitEnt exp) {
	}

	@Override
	public void procesa(LitNull exp) {
	}

	@Override
	public void procesa(LitCad exp) {
	}

	// Tipo

	@Override
	public void procesa(Bool bool) {
		bool.size = 1;
	}

	@Override
	public void procesa(Int int1) {
		int1.size = 1;
	}

	@Override
	public void procesa(Real real) {
		real.size = 1;
	}

	@Override
	public void procesa(String_cons string_cons) {
		string_cons.size = 1;
	}

	@Override
	public void procesa(Tipo_Id tipo_Id) {
		tipo_Id.dir = tipo_Id.getVinculo().dir;
		tipo_Id.size = tipo_Id.getVinculo().size;
		tipo_Id.nivel = tipo_Id.getVinculo().nivel;
		tipo_Id.basesize = tipo_Id.getVinculo().basesize;
	}

	@Override
	public void procesa(Array array) {
		array.tipo_array().procesa(this);
		array.basesize = array.tipo_array().size;
		array.size = array.basesize * Integer.parseInt(array.tam().toString());
	}

	@Override
	public void procesa(Record record) {
		record.campos().procesa(this);
		record.size = record.campos().size;
	}

	@Override
	public void procesa(Pointer pointer) {
		pointer.tipo().procesa(this);
		pointer.size = 1;
		pointer.basesize = pointer.tipo().size;
	}
	
}
