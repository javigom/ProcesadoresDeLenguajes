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
import asint.TinyASint.Exps;
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
import asint.TinyASint.Tipo_Id;
import asint.TinyASint.True;
import asint.TinyASint.While_inst;
import asint.TinyASint.Write;
import maquinaP.MaquinaP;
import procesamientos.ComprobacionTipos.tNodo;

public class GeneracionCodigo extends ProcesamientoPorDefecto{

	private MaquinaP p;
	
	public GeneracionCodigo(MaquinaP p) {
		this.p = p;
	}
	
	private void checkNinstsi(Nodo g) {
		if (p.tamcodigo() != g.etqi) {
			System.err.println("Warning: El numero de instrucciones no coincide con la etqi en " + g);
			System.err.printf("  numero de instrucciones:" + p.tamcodigo() + "etqi: " + g.etqi);
		}
	}
	
	private void checkNinsts(Nodo g) {
		if (p.tamcodigo() != g.etqs) {
			System.err.println("Warning: El numero de instrucciones no coincide con la etqs en " + g);
			System.err.printf("  numero de instrucciones:" + p.tamcodigo() + "etqs: " + g.etqs);
		}
	}
		
	// Programa

	public void procesa(Programa prog) {
		if(prog.declaraciones() != null) {
			prog.declaraciones().procesa(this);
		}
		prog.instrucciones().procesa(this);
		checkNinsts(prog);
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
		dec.bloque().procesa(this);
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
		p.ponInstruccion(p.activa(call.getVinculo().nivel, call.getVinculo().size, call.etqs));
		//call.exps();
		int i = 0;
		Exps exps = call.exps();
		if(exps instanceof Exp_una){
			p.ponInstruccion(p.dup());
			p.ponInstruccion(p.apilaInt(i));
			p.ponInstruccion(p.suma());
			((Exp_una) exps).expresion().procesa(this);
			p.ponInstruccion(p.desapilaInd());
			i++;
		}
		
		p.ponInstruccion(p.desapilad(call.getVinculo().nivel));
		p.ponInstruccion(p.irA(call.getVinculo().etqi));
	}

	@Override
	public void procesa(Delete delete) {
		delete.exp().procesa(this);
		p.ponInstruccion(p.dealloc(delete.exp().basesize));
		System.out.println(p.dealloc(delete.exp().basesize));
	}

	@Override
	public void procesa(New_cons new_cons) {
		new_cons.exp().procesa(this);
		p.ponInstruccion(p.alloc(new_cons.exp().basesize));
		System.out.println(p.alloc(new_cons.exp().basesize));
		p.ponInstruccion(p.desapilaInd());
		System.out.println(p.desapilaInd());
	}

	@Override
	public void procesa(Nl nl) {
		p.ponInstruccion(p.apilaString("\n"));
		System.out.println(p.apilaString("\n"));
		p.ponInstruccion(p.writeString());
		System.out.println(p.writeString());
	}

	@Override
	public void procesa(Write write) {
		write.exp().procesa(this);
		if (write.exp().esDesignador()) {
			p.ponInstruccion(p.apilaInd());
		}
		if (write.exp().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.writeInt());
			System.out.println(p.writeInt());
		} else if (write.exp().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.writeReal());
			System.out.println(p.writeReal());
		} else if (write.exp().getTipo() == tNodo.BOOL) {
			p.ponInstruccion(p.writeBool());
			System.out.println(p.writeBool());
		} else if (write.exp().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.writeString());
			System.out.println(p.writeString());
		} else {
			throw new IllegalStateException("Esto no debería pasar");
		}
	}

	@Override
	public void procesa(Read read) {
		read.exp().procesa(this);
		if (read.exp().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.readInt());
			System.out.println(p.readInt());
		} else if (read.exp().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.readReal());
			System.out.println(p.readReal());
		} else if (read.exp().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.readString());
			System.out.println(p.readString());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no capturado");
		}
		
		p.ponInstruccion(p.desapilaInd());
	}

	@Override
	public void procesa(While_inst while_inst) {
		checkNinstsi(while_inst);
		while_inst.exp().procesa(this);
		p.ponInstruccion(p.irF(while_inst.etqs));
		System.out.println(p.irF(while_inst.etqs));
		while_inst.instrucciones().procesa(this);
		p.ponInstruccion(p.irA(while_inst.etqi));
		System.out.println(p.irA(while_inst.etqi));
		checkNinsts(while_inst);
	}

	@Override
	public void procesa(If_else if_else) {
		checkNinstsi(if_else);
		if_else.exp().procesa(this);
		p.ponInstruccion(p.irF(if_else.instrucciones_else().etqi));
		System.out.println(p.irF(if_else.instrucciones_else().etqi));
		if_else.instrucciones().procesa(this);
		p.ponInstruccion(p.irA(if_else.etqs));
		System.out.println(p.irA(if_else.etqs));
		if_else.instrucciones_else().procesa(this);
		checkNinsts(if_else);
	}

	@Override
	public void procesa(If_inst if_inst) {
		checkNinstsi(if_inst);
		if_inst.exp().procesa(this);
		p.ponInstruccion(p.irF(if_inst.etqs));
		System.out.println(p.irF(if_inst.etqs));
		if_inst.instrucciones().procesa(this);
		checkNinsts(if_inst);
	}

	@Override
	public void procesa(Asig asig) {
		checkNinstsi(asig);
		asig.exp0().procesa(this);
		asig.exp1().procesa(this);
		
		if (asig.exp1().esDesignador()) {
			p.ponInstruccion(p.mueve(asig.exp1().size));
			System.out.println(p.mueve(asig.exp1().size));
		} else {
			p.ponInstruccion(p.desapilaInd());
			System.out.println(p.desapilaInd());
		}
		checkNinsts(asig);
	}

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
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
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.suma());
			System.out.println(p.suma());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.sumaR());
			System.out.println(p.sumaR());
		}
		checkNinsts(exp);
	}

	public void procesa(Resta exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.resta());
			System.out.println(p.resta());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.restaR());
			System.out.println(p.restaR());
		}
		checkNinsts(exp);
	}

	// Nivel 1
	public void procesa(And exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.and());
		System.out.println(p.and());
		checkNinsts(exp);
	}

	public void procesa(Or exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.or());
		System.out.println(p.or());
		checkNinsts(exp);
	}

	// Nivel 2
	public void procesa(Menor exp) {
		checkNinstsi(exp);
		
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.menor());
			System.out.println(p.menor());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.menorR());
			System.out.println(p.menorR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.menorString());
			System.out.println(p.menorString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.and());
			System.out.println(p.and());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		
		checkNinsts(exp);
	}

	public void procesa(Mayor exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.mayor());
			System.out.println(p.mayor());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.mayorR());
			System.out.println(p.mayorR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.mayorString());
			System.out.println(p.mayorString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.and());
			System.out.println(p.and());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		checkNinsts(exp);
	}

	public void procesa(MenorIgual exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.menorIg());
			System.out.println(p.menorIg());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.menorIgR());
			System.out.println(p.menorIgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.menorIgString());
			System.out.println(p.menorIgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.or());
			System.out.println(p.or());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		checkNinsts(exp);
	}

	public void procesa(MayorIgual exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.mayorIg());
			System.out.println(p.mayorIg());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.mayorIgR());
			System.out.println(p.mayorIgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.mayorIgString());
			System.out.println(p.mayorIgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.or());
			System.out.println(p.or());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		checkNinsts(exp);
	}

	public void procesa(Igual exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.Ig());
			System.out.println(p.Ig());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.IgR());
			System.out.println(p.IgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.IgString());
			System.out.println(p.IgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.and());
			System.out.println(p.and());
		} else if (exp.arg0().getTipo() == tNodo.POINTER){
			p.ponInstruccion(p.Ig());
			System.out.println(p.Ig());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		checkNinsts(exp);
	}

	public void procesa(Distinto exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd()); 
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.Ig());
			System.out.println(p.Ig());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.IgR());
			System.out.println(p.IgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.IgString());
			System.out.println(p.IgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL) {
			p.ponInstruccion(p.and());
			System.out.println(p.and());
		} else if (exp.arg0().getTipo() == tNodo.POINTER){
			p.ponInstruccion(p.Ig());
			System.out.println(p.Ig());
		} 
		else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
	
		p.ponInstruccion(p.not());
		System.out.println(p.not());
		checkNinsts(exp);
	}

	// Nivel 3
	public void procesa(Mul exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.mul());
			System.out.println(p.mul());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.mulR());
			System.out.println(p.mulR());
		}
		checkNinsts(exp);
	}

	public void procesa(Div exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd()); 
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.div());
			System.out.println(p.div());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.divR());
			System.out.println(p.divR());
		}
		checkNinsts(exp);
	}

	@Override
	public void procesa(Percent exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		p.ponInstruccion(p.percent());
		System.out.println(p.percent());
		checkNinsts(exp);
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		checkNinstsi(exp);
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.apilaInt(0));
			System.out.println(p.apilaInt(0));
			exp.arg0().procesa(this);
			if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
			p.ponInstruccion(p.resta());
			System.out.println(p.resta());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.apilaReal(0));
			System.out.println(p.apilaReal(0));
			exp.arg0().procesa(this);
			if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
			p.ponInstruccion(p.restaR());
			System.out.println(p.restaR());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		checkNinsts(exp);
	}

	public void procesa(Not exp) {
		checkNinstsi(exp);
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.not());
		System.out.println(p.not());
		checkNinsts(exp);
	}
	
	//Nivel 5
	
	@Override
	public void procesa(Corchete corchete) {
		checkNinstsi(corchete);
		corchete.arg0().procesa(this);
		corchete.arg1().procesa(this);
		if (corchete.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.apilaInt(corchete.arg0().basesize));
		System.out.println(p.apilaInt(corchete.arg0().basesize));
		p.ponInstruccion(p.mul());
		System.out.println(p.mul());
		p.ponInstruccion(p.suma());
		System.out.println(p.suma());
		checkNinsts(corchete);
	}

	@Override
	public void procesa(Punto punto) {
		checkNinstsi(punto);
		punto.exp().procesa(this);
		// Apilar desplazamiento del campo
		int despl = ((Record) punto.exp().getVinculo().val().getVinculo().val()).campos().getCampos().get(punto.id().toString()).despl;
		p.ponInstruccion(p.apilaInt(despl));
		System.out.println(p.apilaInt(despl));
		p.ponInstruccion(p.suma());
		System.out.println(p.suma());
		checkNinsts(punto);
	}

	@Override
	public void procesa(Flecha flecha) {
		checkNinstsi(flecha);
		flecha.exp().procesa(this);
		p.ponInstruccion(p.apilaInd());
		System.out.println(p.apilaInd());
		Exp t = flecha.exp();
		while (!(t instanceof Id)) {
			t = ((Flecha) t).exp();
		}
		int despl = ((Record) t.getVinculo().val().getVinculo().val().tipo().getVinculo().val()).campos().getCampos().get(flecha.id().toString()).despl;
		p.ponInstruccion(p.apilaInt(despl));
		System.out.println(p.apilaInt(despl));
		p.ponInstruccion(p.suma());
		System.out.println(p.suma());
		checkNinsts(flecha);
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		checkNinstsi(star);
		star.arg0().procesa(this);
		p.ponInstruccion(p.apilaInd());
		System.out.println(p.apilaInd());
		checkNinsts(star);
	}

	// Nivel 7
	public void procesa(True exp) {
		checkNinstsi(exp);
		p.ponInstruccion(p.apilaBool(true));
		System.out.println(p.apilaBool(true));
		checkNinsts(exp);
	}

	public void procesa(False exp) {
		p.ponInstruccion(p.apilaBool(false));
		System.out.println(p.apilaBool(false));
		checkNinsts(exp);
	}

	public void procesa(LitReal exp) {
		checkNinstsi(exp);
		p.ponInstruccion(p.apilaInt(Integer.parseInt(exp.num().toString())));
		System.out.println(p.apilaInt(Integer.parseInt(exp.num().toString())));
		checkNinsts(exp);
	}

	public void procesa(Id exp) {
		checkNinstsi(exp);
		if (exp.nivel == 0) {
			p.ponInstruccion(p.apilaInt(exp.dir));
			System.out.println(p.apilaInt(exp.dir));
		} else {
			p.ponInstruccion(p.apilad(exp.nivel));
			System.out.println(p.apilad(exp.nivel));
			p.ponInstruccion(p.apilaInt(exp.dir));
			System.out.println(p.apilaInt(exp.dir));
			p.ponInstruccion(p.suma());
			System.out.println(p.suma());
		}
		checkNinsts(exp);
	}

	public void procesa(LitEnt exp) {
		checkNinstsi(exp);
		p.ponInstruccion(p.apilaInt(Integer.parseInt(exp.num().toString())));
		System.out.println(p.apilaInt(Integer.parseInt(exp.num().toString())));
		checkNinsts(exp);
	}

	@Override
	public void procesa(LitNull exp) {
		checkNinstsi(exp);
		p.ponInstruccion(p.apilaInt(-1));
		System.out.println(p.apilaInt(-1));
		checkNinsts(exp);
	}

	@Override
	public void procesa(LitCad exp) {
		checkNinstsi(exp);
		p.ponInstruccion(p.apilaString(exp.cad().toString()));
		System.out.println(p.apilaString(exp.cad().toString()));
		checkNinsts(exp);
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
