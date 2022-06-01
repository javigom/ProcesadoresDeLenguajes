package asint;

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
import asint.TinyASint.Div;
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
import asint.TinyASint.Or;
import asint.TinyASint.ParamForm;
import asint.TinyASint.ParamForms_empty;
import asint.TinyASint.ParamForms_muchos;
import asint.TinyASint.ParamForms_uno;
import asint.TinyASint.Percent;
import asint.TinyASint.Pformal_ref;
import asint.TinyASint.Pointer;
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
	
	void procesa(Percent percent);

	// Nivel 4
	void procesa(MenosUnario exp);

	void procesa(Not exp);
	
	//Nivel 5

	void procesa(Corchete corchete);

	void procesa(Punto punto);

	void procesa(Flecha flecha);
	
	//Nivel 6

	void procesa(Star star);

	// Nivel 7
	void procesa(True exp);

	void procesa(False exp);

	void procesa(LitEnt exp);

	void procesa(LitReal exp);

	void procesa(LitNull litNull);

	void procesa(LitCad litCad);

	void procesa(Id exp);

	// Tipo

	void procesa(Bool bool);

	void procesa(Int int1);

	void procesa(Real real);

	void procesa(String_cons string_cons);

	void procesa(Tipo_Id tipo_Id);

	void procesa(Array array);

	void procesa(Record record);

	void procesa(Pointer pointer);

	// Declaraciones

	void procesa(DecProc decProc);

	void procesa(DecTipo decTipo);

	void procesa(DecVar decVar);

	void procesa(Decs_muchas decs);

	void procesa(Decs_una decs);

	// Instrucciones
	
	void procesa(Bloque_inst bloque_inst);

	void procesa(Call call);

	void procesa(Delete delete);

	void procesa(New_cons new_cons);

	void procesa(Nl nl);

	void procesa(Write write);

	void procesa(Read read);

	void procesa(While_inst while_inst);

	void procesa(If_else if_else);

	void procesa(If_inst if_inst);

	void procesa(Asig asig);

	void procesa(Insts_muchas insts);

	void procesa(Insts_una insts);

	void procesa(Lista_inst_empty lista_inst_empty);

	void procesa(Lista_inst_una lista_inst_una);

	void procesa(Lista_inst_muchas lista_inst_muchas);
	
	//Param Formales

	void procesa(ParamForm paramForm);
	
	void procesa(Pformal_ref paramForm);

	void procesa(ParamForms_muchos paramForms_muchos);

	void procesa(ParamForms_uno paramForms_uno);

	void procesa(ParamForms_empty paramForms_empty);
	
	//Expresiones 

	void procesa(Lista_exp_empty lista_exp_empty);

	void procesa(Exp_muchas exp_muchas);

	void procesa(Exp_una exp_una);
	
	// Campos 

	void procesa(Campos_muchos campos_muchos);

	void procesa(Campo_uno campo_uno);

	void procesa(Camp camp);
	
	
	// Bloque 

	void procesa(Bloque_prog bloque_prog);

	void procesa(No_bloque no_bloque);
	

	// Programa
	void procesa(Programa programa);
}