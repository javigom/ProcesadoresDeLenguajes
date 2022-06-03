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

public class GeneraCodigo extends ProcesamientoPorDefecto{

	private MaquinaP p;
	
	public GeneraCodigo(MaquinaP p) {
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
	public void procesa(Delete delete) {
		delete.exp().procesa(this);
		p.ponInstruccion(p.dealloc(delete.exp().basesize));
	}

	@Override
	public void procesa(New_cons new_cons) {
		new_cons.exp().procesa(this);
		p.ponInstruccion(p.alloc(new_cons.exp().basesize));
		p.ponInstruccion(p.desapilaInd());
	}

	@Override
	public void procesa(Nl nl) {
		p.ponInstruccion(p.apilaString("\n"));
		p.ponInstruccion(p.writeString());
	}

	@Override
	public void procesa(Write write) {
		write.exp().procesa(this);
		if (write.exp().esDesignador()) {
			p.ponInstruccion(p.apilaInd());
		}
		if (write.exp().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.writeInt());
		} else if (write.exp().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.writeReal());
		} else if (write.exp().getTipo() == tNodo.BOOL) {
			p.ponInstruccion(p.writeBool());
		} else if (write.exp().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.writeString());
		} else {
			throw new IllegalStateException("Esto no debería pasar");
		}
	}

	@Override
	public void procesa(Read read) {
		read.exp().procesa(this);
		if (read.exp().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.readInt());
		} else if (read.exp().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.readReal());
		} else if (read.exp().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.readString());
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
		while_inst.instrucciones().procesa(this);
		p.ponInstruccion(p.irA(while_inst.etqi));
		checkNinstsi(while_inst);
	}

	@Override
	public void procesa(If_else if_else) {
		if_else.exp().procesa(this);
		p.ponInstruccion(p.irF(if_else.instrucciones_else().etqi));
		if_else.instrucciones().procesa(this);
		p.ponInstruccion(p.irA(if_else.etqs));
		if_else.instrucciones_else().procesa(this);
	}

	@Override
	public void procesa(If_inst if_inst) {
		if_inst.exp().procesa(this);
		p.ponInstruccion(p.irF(if_inst.etqs));
		if_inst.instrucciones().procesa(this);
	}

	@Override
	public void procesa(Asig asig) {
		checkNinstsi(asig);
		asig.exp0().procesa(this);
		asig.exp1().procesa(this);
		
		if (asig.exp1().esDesignador()) {
			p.ponInstruccion(p.mueve(asig.exp1().size));
		} else {
			p.ponInstruccion(p.desapilaInd());
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
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.suma());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.sumaR());
		}
	}

	public void procesa(Resta exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.restaR());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.restaR());
		}
	}

	// Nivel 1
	public void procesa(And exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.and());
	}

	public void procesa(Or exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.or());
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
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.menorR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.menorString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.and());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
		
		checkNinsts(exp);
	}

	public void procesa(Mayor exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.mayor());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.mayorR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.mayorString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.and());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
	}

	public void procesa(MenorIgual exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.menorIg());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.menorIgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.menorIgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.or());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
	}

	public void procesa(MayorIgual exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.mayorIg());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.mayorIgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.mayorIgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.or());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
	}

	public void procesa(Igual exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		if (exp.arg0().getTipo() == tNodo.BOOL) p.ponInstruccion(p.not());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.Ig());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.IgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.IgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL){
			p.ponInstruccion(p.and());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
	}

	public void procesa(Distinto exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.arg0().getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.Ig());
		} else if (exp.arg0().getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.IgR());
		} else if (exp.arg0().getTipo() == tNodo.STRING) {
			p.ponInstruccion(p.IgString());
		} else if (exp.arg0().getTipo() == tNodo.BOOL) {
			p.ponInstruccion(p.and());
		}
	
		p.ponInstruccion(p.not());
	}

	// Nivel 3
	public void procesa(Mul exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.mul());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.mulR());
		}
	}

	public void procesa(Div exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.div());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.divR());
		}
	}

	@Override
	public void procesa(Percent exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		exp.arg1().procesa(this);
		if (exp.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		
		p.ponInstruccion(p.percent());
	}

	// Nivel 4
	public void procesa(MenosUnario exp) {
		if (exp.getTipo() == tNodo.LIT_ENT) {
			p.ponInstruccion(p.apilaInt(0));
			exp.arg0().procesa(this);
			if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
			p.ponInstruccion(p.restaR());
		} else if (exp.getTipo() == tNodo.LIT_REAL) {
			p.ponInstruccion(p.apilaReal(0));
			exp.arg0().procesa(this);
			if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
			p.ponInstruccion(p.restaR());
		} else {
			throw new IllegalStateException("Hubo un error de tipos no detectado");
		}
	}

	public void procesa(Not exp) {
		exp.arg0().procesa(this);
		if (exp.arg0().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.not());
	}
	
	//Nivel 5
	
	@Override
	public void procesa(Corchete corchete) {
		corchete.arg0().procesa(this);
		corchete.arg1().procesa(this);
		if (corchete.arg1().esDesignador()) p.ponInstruccion(p.apilaInd());
		p.ponInstruccion(p.apilaInt(corchete.arg0().basesize));
		p.ponInstruccion(p.mul());
		p.ponInstruccion(p.suma());
	}

	@Override
	public void procesa(Punto punto) {
		punto.exp().procesa(this);
		// Apilar desplazamiento del campo
		int despl = punto.exp().getCampos().get(punto.id().toString()).despl;
		p.ponInstruccion(p.apilaInt(despl));
		p.ponInstruccion(p.suma());
	}

	@Override
	public void procesa(Flecha flecha) {
		flecha.exp().procesa(this);
		p.ponInstruccion(p.apilaInd());
		int despl = flecha.exp().getCampos().get(flecha.id().toString()).despl;
		p.ponInstruccion(p.apilaInt(despl));
		p.ponInstruccion(p.suma());
	}

	//Nivel 6
	
	@Override
	public void procesa(Star star) {
		star.arg0().procesa(this);
		p.ponInstruccion(p.apilaInd());
	}

	// Nivel 7
	public void procesa(True exp) {
		p.ponInstruccion(p.apilaBool(true));
	}

	public void procesa(False exp) {
		p.ponInstruccion(p.apilaBool(false));
	}

	public void procesa(LitReal exp) {
		p.ponInstruccion(p.apilaInt(Integer.parseInt(exp.num().toString())));
	}

	public void procesa(Id exp) {
		if (exp.nivel == 0) {
			p.ponInstruccion(p.apilaInt(exp.dir));
		} else {
			p.ponInstruccion(p.apilad(exp.nivel));
			p.ponInstruccion(p.apilaInt(exp.dir));
			p.ponInstruccion(p.suma());
		}
	}

	public void procesa(LitEnt exp) {
		p.ponInstruccion(p.apilaInt(Integer.parseInt(exp.num().toString())));
	}

	@Override
	public void procesa(LitNull exp) {
		p.ponInstruccion(p.apilaInt(-1));
	}

	@Override
	public void procesa(LitCad exp) {
		p.ponInstruccion(p.apilaString(exp.cad().toString()));
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
