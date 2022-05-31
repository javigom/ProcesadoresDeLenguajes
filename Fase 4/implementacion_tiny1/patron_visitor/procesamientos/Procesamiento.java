package procesamientos;

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
import asint.TinyASint.ExpN5;
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

public interface Procesamiento {
	default void procesa(Programa prog) {
		throw new UnsupportedOperationException();
	}
	
	default void procesa(Decs_muchas dec) {
		throw new UnsupportedOperationException();
	}
	
	default void procesa(Decs_una dec) {
		throw new UnsupportedOperationException();
	}
	
	default void procesa(DecVar var) {
		throw new UnsupportedOperationException();
	}
	
	default void procesa(DecTipo type) {
		throw new UnsupportedOperationException();
	}
	
	default void procesa(DecProc proc) {
		throw new UnsupportedOperationException();
	}
	
	default void procesa(Bloque_inst bloque_inst) {
		throw new UnsupportedOperationException();
	}
	
	
	default void procesa(Call call) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Delete delete) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(New_cons new_cons) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Nl nl) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Write write) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Read read) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(While_inst while_inst) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(If_else if_else) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(If_inst if_inst) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Asig asig) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Insts_muchas insts) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Insts_una insts) {
		throw new UnsupportedOperationException();
	}
	
	
	default void procesa(Lista_inst_empty lista_inst_empty) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Lista_inst_una lista_inst_una) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Lista_inst_muchas lista_inst_muchas) {
		throw new UnsupportedOperationException();
	}
	
	
	// Param Formales


	
	default void procesa(ParamForm paramForm) {
		throw new UnsupportedOperationException();
	}
	

	
	default void procesa(Pformal_ref paramForm) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(ParamForms_muchos paramForms_muchos) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(ParamForms_uno paramForms_uno) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(ParamForms_empty paramForms_empty) {
		throw new UnsupportedOperationException();
	}
	
	// Campos
	
	
	default void procesa(Campos_muchos campos_muchos) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Campo_uno campo_uno) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Camp camp) {
		throw new UnsupportedOperationException();
	}
	
	
	// Bloque 
	
	
	default void procesa(Bloque_prog bloque_prog) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(No_bloque no_bloque) {
		throw new UnsupportedOperationException();
	}

	
	// Expresiones
	
	
	default void procesa(Lista_exp_empty lista_exp_empty) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Exp_muchas exp_muchas) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Exp_una exp_una) {
		throw new UnsupportedOperationException();
	}

	// Operadores

	// Nivel 0
	default void procesa(Suma exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Resta exp) {
		throw new UnsupportedOperationException();
	}

	// Nivel 1
	default void procesa(And exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Or exp) {
		throw new UnsupportedOperationException();
	}

	// Nivel 2
	default void procesa(Menor exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Mayor exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(MenorIgual exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(MayorIgual exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Igual exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Distinto exp) {
		throw new UnsupportedOperationException();
	}

	// Nivel 3
	default void procesa(Mul exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Div exp) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Percent exp) {
		throw new UnsupportedOperationException();
	}

	// Nivel 4
	default void procesa(MenosUnario exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Not exp) {
		throw new UnsupportedOperationException();
	}
	
	//Nivel 5
	
	
	default void procesa(ExpN5 expN5) {
		throw new UnsupportedOperationException();
	}
	
	
	default void procesa(Corchete corchete) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Punto punto) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Flecha flecha) {
		throw new UnsupportedOperationException();
		
	}

	//Nivel 6
	
	
	default void procesa(Star star) {
		throw new UnsupportedOperationException();
	}

	// Nivel 7
	default void procesa(True exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(False exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(LitReal exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(Id exp) {
		throw new UnsupportedOperationException();
	}

	default void procesa(LitEnt exp) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(LitNull exp) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(LitCad exp) {
		throw new UnsupportedOperationException();
	}

	// Tipo

	
	default void procesa(Bool bool) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Int int1) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Real real) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(String_cons string_cons) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Tipo_Id tipo_Id) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Array array) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Record record) {
		throw new UnsupportedOperationException();
	}

	
	default void procesa(Pointer pointer) {
		throw new UnsupportedOperationException();
	}
	
}