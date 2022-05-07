package asint;

import asint.TinyASint.Suma;
import asint.TinyASint.Tipo;
import asint.TinyASint.True;
import asint.TinyASint.Resta;
import asint.TinyASint.Star;
import asint.TinyASint.Mul;
import asint.TinyASint.No_bloque;
import asint.TinyASint.Not;
import asint.TinyASint.Div;
import asint.TinyASint.ExpN5;
import asint.TinyASint.Exp_muchas;
import asint.TinyASint.Exp_una;
import asint.TinyASint.False;
import asint.TinyASint.Flecha;
import asint.TinyASint.Id;
import asint.TinyASint.Igual;
import asint.TinyASint.Instruccion;
import asint.TinyASint.Insts_muchas;
import asint.TinyASint.Insts_una;
import asint.TinyASint.Lista_exp;
import asint.TinyASint.Lista_exp_empty;
import asint.TinyASint.Lista_inst;
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
import asint.TinyASint.Pformales_lista;
import asint.TinyASint.And;
import asint.TinyASint.Bloque;
import asint.TinyASint.Bloque_prog;
import asint.TinyASint.Camp;
import asint.TinyASint.Campo_uno;
import asint.TinyASint.Campos_muchos;
import asint.TinyASint.Declaracion;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Distinto;
import asint.TinyASint.Programa;
import asint.TinyASint.Punto;

public class ProcesamientoPorDefecto implements Procesamiento {

	public void procesa(Suma exp) {
	}

	public void procesa(Resta exp) {
	}

	public void procesa(Mul exp) {
	}

	public void procesa(Div exp) {
	}

	public void procesa(Id exp) {
	}

	public void procesa(Decs_muchas decs) {
	}

	public void procesa(Decs_una decs) {
	}

	@Override
	public void procesa(And exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Or exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Menor exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Mayor exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(MenorIgual exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(MayorIgual exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Igual exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Distinto exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(MenosUnario exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Not exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(True exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(False exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(LitEnt exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(LitReal exp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Declaracion declaracion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Instruccion instruccion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Insts_muchas insts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Insts_una insts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Programa programa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void procesa(Tipo tipo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(ExpN5 expN5) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Punto punto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Flecha flecha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Star star) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(LitNull litNull) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(LitCad litCad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Lista_inst lista_inst) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Lista_inst_empty lista_inst_empty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Lista_inst_una lista_inst_una) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Lista_inst_muchas lista_inst_muchas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(ParamForm paramForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Pformales_lista pformales_lista) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(ParamForms_muchos paramForms_muchos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(ParamForms_uno paramForms_uno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(ParamForms_empty paramForms_empty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Lista_exp lista_exp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Lista_exp_empty lista_exp_empty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Exp_muchas exp_muchas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Exp_una exp_una) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Campos_muchos campos_muchos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Campo_uno campo_uno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Camp camp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Bloque_prog bloque_prog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(No_bloque no_bloque) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Bloque bloque) {
		// TODO Auto-generated method stub
		
	}
}
