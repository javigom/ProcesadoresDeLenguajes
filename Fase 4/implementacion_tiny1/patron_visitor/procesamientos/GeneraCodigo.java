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
import maquinaP.MaquinaP;

public class GeneraCodigo implements Procesamiento{

	private MaquinaP p;
	
	public GeneraCodigo(MaquinaP p) {
		p = p;
	}
		
	public void procesa(Programa prog) {
		
	}
	
	public void procesa(Decs_muchas dec) {
		
	}
	
	public void procesa(Decs_una dec) {
		
	}
	
	public void procesa(DecVar var) {
		
	}
	
	public void procesa(DecTipo type) {
		
	}
	
	public void procesa(DecProc proc) {
		
	}
	
	public void procesa(Bloque_inst bloque_inst) {
		
	}
	
	
	public void procesa(Call call) {
		
	}

	
	public void procesa(Delete delete) {
		
	}

	
	public void procesa(New_cons new_cons) {
		
	}

	
	public void procesa(Nl nl) {
		
	}

	
	public void procesa(Write write) {
		
	}

	
	public void procesa(Read read) {
		
	}

	
	public void procesa(While_inst while_inst) {
		
	}

	
	public void procesa(If_else if_else) {
		
	}

	
	public void procesa(If_inst if_inst) {
		
	}

	
	public void procesa(Asig asig) {
		
	}

	public void procesa(Insts_muchas insts) {
		
	}

	public void procesa(Insts_una insts) {
		
	}
	
	
	public void procesa(Lista_inst_empty lista_inst_empty) {
		
	}

	
	public void procesa(Lista_inst_una lista_inst_una) {
		
	}

	
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		
	}
	
	
	// Param Formales


	
	public void procesa(ParamForm paramForm) {
		
	}
	

	
	public void procesa(Pformal_ref paramForm) {
		
	}

	
	public void procesa(ParamForms_muchos paramForms_muchos) {
		
	}

	
	public void procesa(ParamForms_uno paramForms_uno) {
		
	}

	
	public void procesa(ParamForms_empty paramForms_empty) {
		
	}
	
	// Campos
	
	
	public void procesa(Campos_muchos campos_muchos) {
		
	}

	
	public void procesa(Campo_uno campo_uno) {
		
	}

	
	public void procesa(Camp camp) {
		
	}
	
	
	// Bloque 
	
	
	public void procesa(Bloque_prog bloque_prog) {
		
	}

	
	public void procesa(No_bloque no_bloque) {
		
	}

	
	// Expresiones
	
	
	public void procesa(Lista_exp_empty lista_exp_empty) {
		
	}

	
	public void procesa(Exp_muchas exp_muchas) {
		
	}

	
	public void procesa(Exp_una exp_una) {
		
	}

	// Operadores

	// Nivel 0
	public void procesa(Suma exp) {
		
	}

	public void procesa(Resta exp) {
		
	}

	// Nivel 1
	public void procesa(And exp) {
		
	}

	public void procesa(Or exp) {
		
	}

	// Nivel 2
	public void procesa(Menor exp) {
		
	}

	public void procesa(Mayor exp) {
		
	}

	public void procesa(MenorIgual exp) {
		
	}

	public void procesa(MayorIgual exp) {
		
	}

	public void procesa(Igual exp) {
		
	}

	public void procesa(Distinto exp) {
		
	}

	// Nivel 3
	public void procesa(Mul exp) {
		
	}

	public void procesa(Div exp) {
		
	}

	
	public void procesa(Percent exp) {
		
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		
	}

	public void procesa(Not exp) {
		
	}
	
	//Nivel 5
	
	
	public void procesa(ExpN5 expN5) {
		
	}
	
	
	public void procesa(Corchete corchete) {
		
	}

	
	public void procesa(Punto punto) {
		
	}

	
	public void procesa(Flecha flecha) {
		
		
	}

	//Nivel 6
	
	
	public void procesa(Star star) {
		
	}

	// Nivel 7
	public void procesa(True exp) {
		
	}

	public void procesa(False exp) {
		
	}

	public void procesa(LitReal exp) {
		
	}

	public void procesa(Id exp) {
		
	}

	public void procesa(LitEnt exp) {
		
	}

	
	public void procesa(LitNull exp) {
		
	}

	
	public void procesa(LitCad exp) {
		
	}

	// Tipo

	
	public void procesa(Bool bool) {
		
	}

	
	public void procesa(Int int1) {
		
	}

	
	public void procesa(Real real) {
		
	}

	
	public void procesa(String_cons string_cons) {
		
	}

	
	public void procesa(Tipo_Id tipo_Id) {
		
	}

	
	public void procesa(Array array) {
		
	}

	
	public void procesa(Record record) {
		
	}

	
	public void procesa(Pointer pointer) {
		
	}
	
}
