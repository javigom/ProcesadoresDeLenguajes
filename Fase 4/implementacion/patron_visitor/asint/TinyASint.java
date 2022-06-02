package asint;

import procesamientos.ComprobacionTipos.Tipo_Nodo;

public class TinyASint {


	public static abstract class Genero {
		public int line = -1;
		public int col = -1;
		
		public int dir = -1;
		public int nivel = -1;
		public int size = -1;
		public int basesize = -1;
		
		public int etqi = -1;
		public int etqs = -1;
		
		public String toString() {
			return getClass().getSimpleName()+"@"+line+":"+col;
		}
		
		private Tipo_Nodo tipo;
		public Tipo_Nodo getTipo() { return tipo; }
		public void setTipo(Tipo_Nodo t) { tipo = t ; }
	}


	public static abstract class Prog extends Genero {
		public abstract void procesa(Procesamiento p);
	}
		
	public static abstract class Exp extends Genero {
		public abstract int prioridad();
		public boolean esDesignador() { return false; }
		public abstract void procesa(Procesamiento procesamiento);
	}
	
	public static abstract class Exps extends Genero {
		public Exps() {
		}

		public abstract void procesa(Procesamiento p);
	}
	
	public static class Lista_exp_empty extends Exps {

		public Lista_exp_empty() {
			super();
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Exp_una extends Exps {
		private Exp expresion;

		public Exp_una(Exp expresion) {
			super();
			this.expresion = expresion;
		}

		public Exp expresion() {
			return expresion;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Exp_muchas extends Exps {
		private Exp expresion;
		private Exps expresiones;

		public Exp_muchas(Exps expresiones, Exp expresion) {
			super();
			this.expresiones = expresiones;
			this.expresion = expresion;
		}

		public Exps expresiones() {
			return expresiones;
		}

		public Exp expresion() {
			return expresion;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	public static class StringLocalizado {
		private String s;
		private int fila;
		private int col;

		public StringLocalizado(String s, int fila, int col) {
			this.s = s;
			this.fila = fila;
			this.col = col;
		}

		public int fila() {
			return fila;
		}

		public int col() {
			return col;
		}

		public String toString() {
			return s;
		}

		public boolean equals(Object o) {
			return (o == this) || ((o instanceof StringLocalizado) && (((StringLocalizado) o).s.equals(s)));
		}

		public int hashCode() {
			return s.hashCode();
		}
	}
	
	// Operadores

	private static abstract class ExpBin extends Exp {
		private Exp arg0;
		private Exp arg1;

		public Exp arg0() {
			return arg0;
		}

		public Exp arg1() {
			return arg1;
		}

		public ExpBin(Exp arg0, Exp arg1) {
			super();
			this.arg0 = arg0;
			this.arg1 = arg1;
		}
	}
	

	// Nivel 0

	private static abstract class ExpAditiva extends ExpBin {
		public ExpAditiva(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 0;
		}
	}

	public static class Suma extends ExpAditiva {
		public Suma(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Resta extends ExpAditiva {
		public Resta(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	
	// Nivel 1

	private static abstract class ExpLogica extends ExpBin {
		public ExpLogica(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 1;
		}
	}

	public static class And extends ExpLogica {
		public And(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Or extends ExpLogica {
		public Or(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	
	// Nivel 2

	private static abstract class ExpRelacional extends ExpBin {
		public ExpRelacional(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 2;
		}
	}

	public static class Menor extends ExpRelacional {
		public Menor(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Mayor extends ExpRelacional {
		public Mayor(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class MenorIgual extends ExpRelacional {
		public MenorIgual(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class MayorIgual extends ExpRelacional {
		public MayorIgual(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Igual extends ExpRelacional {
		public Igual(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Distinto extends ExpRelacional {
		public Distinto(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	// Nivel 3

	private static abstract class ExpMultiplicativa extends ExpBin {
		public ExpMultiplicativa(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 3;
		}
	}

	public static class Mul extends ExpMultiplicativa {
		public Mul(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Div extends ExpMultiplicativa {
		public Div(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Percent extends ExpMultiplicativa {
		public Percent(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	// Nivel 4
	
	private static abstract class ExpUnaria extends Exp {
		private Exp arg0;

		public Exp arg0() {
			return arg0;
		}

		public ExpUnaria(Exp arg0) {
			super();
			this.arg0 = arg0;
		}
		
		public int prioridad() {
			return 4;
		}
	}

	public static class MenosUnario extends ExpUnaria {
		public MenosUnario(Exp arg0) {
			super(arg0);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Not extends ExpUnaria {
		public Not(Exp arg0) {
			super(arg0);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	//Nivel 5
	
	public static class Corchete extends ExpBin {

		public Corchete(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}
	
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	
		public final int prioridad() {
			return 5;
		}
		
		public boolean esDesignador() {
			return true;
		}
	}
	
	public static class Punto extends Exp {
		private Exp exp;
		private StringLocalizado id;

		public Punto(Exp exp, StringLocalizado id) {
			this.exp = exp;
			this.id = id;
		}
		
		public Exp exp() {
			return exp;
		}
	
		public StringLocalizado id() {
			return id;
		}
	
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	
		public final int prioridad() {
			return 5;
		}
		
		public boolean esDesignador() {
			return true;
		}
	}
	
	public static class Flecha extends Exp {
		private Exp exp;
		private StringLocalizado id;

		public Flecha(Exp exp, StringLocalizado id) {
			this.exp = exp;
			this.id = id;
		}
		
		public Exp exp() {
			return exp;
		}
	
		public StringLocalizado id() {
			return id;
		}
	
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	
		public final int prioridad() {
			return 5;
		}
		
		public boolean esDesignador() {
			return true;
		}
	}
	
	
	//Nivel 6
	
	public static class Star extends ExpUnaria {
		public Star(Exp arg0) {
			super(arg0);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		
		@Override
		public int prioridad() {
			return 6;
		}
		
		public boolean esDesignador() {
			return true;
		}
	}
	
	
	// Nivel 7

	public static class True extends Exp {
		public True() {
			
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		
		public final int prioridad() {
			return 7;
		}
	}
	
	public static class False extends Exp {
		public False() {
			
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		
		public final int prioridad() {
			return 7;
		}
	}

	public static class LitEnt extends Exp {
		private StringLocalizado ent;

		public LitEnt(StringLocalizado ent) {
			super();
			this.ent = ent;
		}

		public StringLocalizado num() {
			return ent;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public final int prioridad() {
			return 7;
		}
	}
	
	public static class LitReal extends Exp {
		private StringLocalizado real;

		public LitReal(StringLocalizado real) {
			super();
			this.real = real;
		}

		public StringLocalizado num() {
			return real;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public final int prioridad() {
			return 7;
		}
	}
	
	
	public static class LitCad extends Exp {
		private StringLocalizado cadena;

		public LitCad(StringLocalizado cadena) {
			super();
			this.cadena = cadena;
		}

		public StringLocalizado cad() {
			return cadena;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public final int prioridad() {
			return 7;
		}
	}
	
	public static class LitNull extends Exp {

		public LitNull() {
			super();
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public final int prioridad() {
			return 7;
		}
	}

	public static class Id extends Exp {
		private StringLocalizado id;
		private DecVar vinculo;

		public Id(StringLocalizado id) {
			super();
			this.id = id;
		}

		public StringLocalizado id() {
			return id;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public final int prioridad() {
			return 7;
		}
		
		@Override
		public boolean esDesignador() {
			return true;
		}
		
		public void setVinculo(DecVar v) {
			vinculo = v;
		}
		
		public DecVar getVinculo() {
			return vinculo;
		}
	}
	
	

	// Tipo
	
	public static abstract class Tipo extends Genero{

		public Tipo() {
		}

		public abstract void procesa(Procesamiento p);
	}
	
	
	public static class Bool extends Tipo {
		
		public Bool() {
			super();
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Int extends Tipo {
		
		public Int() {
			super();
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Real extends Tipo {
		
		public Real() {
			super();
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class String_cons extends Tipo {
		
		public String_cons() {
			super();
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Tipo_Id extends Tipo {
		private StringLocalizado tipo;
		private DecTipo vinculo;
		
		public Tipo_Id(StringLocalizado tipo) {
			super();
			this.tipo = tipo;
		}
		
		public StringLocalizado tipo() {
			return tipo;
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		
		public void setVinculo(DecTipo d) {
			vinculo = d;
		}
		
		public DecTipo getVinculo() {
			return vinculo;
		}
	}
	
	public static class Array extends Tipo {
		private Tipo tipo;
		private StringLocalizado tam;
		
		public Array(StringLocalizado tam, Tipo tipo) {
			super();
			this.tam = tam;
			this.tipo = tipo;
		}
		
		public Tipo tipo_array() {
			return tipo;
		}
		
		public StringLocalizado tam() {
			return tam;
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	public static class Record extends Tipo {
		private Camps c;
		
		public Record(Camps c) {
			super();
			this.c = c;
		}
		
		public Camps campos() {
			return this.c;
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Pointer extends Tipo {
		private Tipo tipo;
		
		public Pointer(Tipo t) {
			super();
			this.tipo = t;
		}
		
		public Tipo tipo() {
			return tipo;
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	// Declaraciones

	public static abstract class Declaracion extends Genero {
		public Declaracion() {
		}

		public abstract void procesa(Procesamiento p);
	}

	public static abstract class Declaraciones extends Genero{
		public Declaraciones() {
		}

		public abstract void procesa(Procesamiento p);
	}

	public static class Decs_una extends Declaraciones {
		private Declaracion declaracion;

		public Decs_una(Declaracion declaracion) {
			super();
			this.declaracion = declaracion;
		}

		public Declaracion declaracion() {
			return declaracion;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Decs_muchas extends Declaraciones {
		private Declaracion declaracion;
		private Declaraciones declaraciones;

		public Decs_muchas(Declaraciones declaraciones, Declaracion declaracion) {
			super();
			this.declaraciones = declaraciones;
			this.declaracion = declaracion;
		}

		public Declaracion declaracion() {
			return declaracion;
		}

		public Declaraciones declaraciones() {
			return declaraciones;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	


	public static class DecVar extends Declaracion {
		private StringLocalizado id;
		private Tipo val;

		public DecVar(StringLocalizado id, Tipo val) {
			super();
			this.id = id;
			this.val = val;
		}

		public StringLocalizado id() {
			return id;
		}

		public Tipo val() {
			return val;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class DecTipo extends Declaracion {
		private StringLocalizado id;
		private Tipo val;

		public DecTipo(StringLocalizado id, Tipo val) {
			super();
			this.id = id;
			this.val = val;
		}

		public StringLocalizado id() {
			return id;
		}

		public Tipo val() {
			return val;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class DecProc extends Declaracion {
		private StringLocalizado id;
		private ParamForms pforms;
		private Bloque b;

		public DecProc(StringLocalizado id, ParamForms pforms, Bloque b) {
			super();
			this.id = id;
			this.pforms = pforms;
			this.b = b;
		}

		public ParamForms pforms() {
			return pforms;
		}

		public Bloque bloque() {
			return b;
		}

		public StringLocalizado id() {
			return id;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		

		public Tipo_Nodo getTipo() { return Tipo_Nodo.OK; }
	}
	
	// Instrucciones
	
	public static abstract class Instruccion extends Genero {

		public Instruccion() {
		}

		public abstract void procesa(Procesamiento p);
	}
	
	public static class Asig extends Instruccion {
		private Exp exp;
		private Exp exp1;

		public Asig(Exp exp, Exp exp1) {
			super();
			this.exp = exp;
			this.exp1 = exp1;
		}

		public Exp exp0() {
			return exp;
		}
		
		public Exp exp1() {
			return exp1;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class If_inst extends Instruccion {
		private Exp exp;
		private Instrucciones instrucciones;

		public If_inst(Exp exp, Instrucciones instrucciones) {
			super();
			this.exp = exp;
			this.instrucciones = instrucciones;
		}

		public Exp exp() {
			return exp;
		}
		
		public Instrucciones instrucciones() {
			return instrucciones;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class If_else extends Instruccion {
		private Exp exp;
		private Instrucciones instrucciones;
		private Instrucciones instrucciones_else;

		public If_else(Exp exp, Instrucciones instrucciones, Instrucciones instrucciones_else) {
			super();
			this.exp = exp;
			this.instrucciones = instrucciones;
			this.instrucciones_else = instrucciones_else;
		}

		public Exp exp() {
			return exp;
		}
		
		public Instrucciones instrucciones() {
			return instrucciones;
		}
		
		public Instrucciones instrucciones_else() {
			return instrucciones_else;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class While_inst extends Instruccion {
		private Exp exp;
		private Instrucciones instrucciones;

		public While_inst(Exp exp, Instrucciones instrucciones) {
			super();
			this.exp = exp;
			this.instrucciones = instrucciones;
		}

		public Exp exp() {
			return exp;
		}
		
		public Instrucciones instrucciones() {
			return instrucciones;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Read extends Instruccion {
		private Exp exp;

		public Read(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Write extends Instruccion {
		private Exp exp;

		public Write(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Nl extends Instruccion {

		public Nl() {
			super();
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class New_cons extends Instruccion {
		private Exp exp;

		public New_cons(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Delete extends Instruccion {
		private Exp exp;

		public Delete(Exp exp) {
			super();
			this.exp = exp;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Call extends Instruccion {
		private Exps exps;
		private StringLocalizado s;
		private DecProc vinculo;

		public Call(StringLocalizado s,Exps exps) {
			super();
			this.exps = exps;
			this.s = s;
		}

		public Exps exps() {
			return exps;
		}
		
		public StringLocalizado string() {
			return s;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		

		public void setVinculo(DecProc v) {
			vinculo = v;
		}
		
		public DecProc getVinculo() {
			return vinculo;
		}
	}
	
	
	public static class Bloque_inst extends Instruccion {
		private Bloque b;

		public Bloque_inst(Bloque b) {
			super();
			this.b = b;
		}

		public Bloque bloque() {
			return b;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	
	public static abstract class Instrucciones extends Genero{
		public Instrucciones() {
		}

		public abstract void procesa(Procesamiento p);
	}

	public static class Insts_una extends Instrucciones {
		private Instruccion instruccion;

		public Insts_una(Instruccion instruccion) {
			super();
			this.instruccion = instruccion;
		}

		public Instruccion instruccion() {
			return instruccion;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Insts_muchas extends Instrucciones {
		private Instruccion instruccion;
		private Instrucciones instrucciones;

		public Insts_muchas(Instrucciones instrucciones, Instruccion instruccion) {
			super();
			this.instrucciones = instrucciones;
			this.instruccion = instruccion;
		}

		public Instrucciones instrucciones() {
			return instrucciones;
		}

		public Instruccion instruccion() {
			return instruccion;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Lista_inst_empty extends Instrucciones {

		public Lista_inst_empty() {
			super();
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Lista_inst_una extends Instrucciones {
		private Instruccion in;

		public Lista_inst_una(Instruccion in) {
			super();
			this.in = in;
		}

		public Instruccion instruccion() {
			return in;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Lista_inst_muchas extends Instrucciones {
		private Instruccion in;
		private Instrucciones ins;

		public Lista_inst_muchas(Instrucciones ins, Instruccion in) {
			super();
			this.ins = ins;
			this.in = in;
		}

		public Instrucciones instrucciones() {
			return ins;
		}

		public Instruccion instruccion() {
			return in;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	// Bloque
	
		public static abstract class Bloque extends Genero {

			public Bloque() {
			
			}

			public abstract void procesa(Procesamiento p);
		}

		public static class Bloque_prog extends Bloque {
			private Programa p;

			public Bloque_prog(Programa p) {
				super();
				this.p = p;
			}

			public Programa programa() {
				return p;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}

		public static class No_bloque extends Bloque {

			public No_bloque() {
				super();
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}
		
		
		// Param Formales
		
		public static class ParamForm extends Declaracion {
			private Tipo t;
			private StringLocalizado id;

			public ParamForm(Tipo t, StringLocalizado id) {
				this.id = id;
				this.t = t;
			}

			public StringLocalizado id() {
				return id;
			}

			public Tipo tipo() {
				return t;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}
		
		public static class Pformal_ref extends ParamForm{
			
			public Pformal_ref(Tipo t, StringLocalizado id) {
				super(t, id);
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}
		
		
		
		public static abstract class ParamForms extends Declaraciones{
			public ParamForms() {
			}

			public abstract void procesa(Procesamiento p);
		}
		
		public static class ParamForms_empty extends ParamForms {

			public ParamForms_empty() {
				super();
			}
			
			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}

		public static class ParamForms_uno extends ParamForms {
			private ParamForm param;

			public ParamForms_uno(ParamForm param) {
				super();
				this.param = param;
			}

			public ParamForm param() {
				return param;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}

		public static class ParamForms_muchos extends ParamForms {
			private ParamForm param;
			private ParamForms params;

			public ParamForms_muchos(ParamForms params, ParamForm param) {
				super();
				this.params = params;
				this.param = param;
			}

			public ParamForms params() {
				return params;
			}

			public ParamForm param() {
				return param;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}
		
	// Camps
		
		
		public static class Camp extends Genero {
			private Tipo t;
			private StringLocalizado id;
			public int despl = -1;

			public Camp(Tipo t, StringLocalizado id) {
				this.id = id;
				this.t = t;
			}

			public StringLocalizado id() {
				return id;
			}

			public Tipo tipo() {
				return t;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}
		
		
		public static abstract class Camps extends Genero{
			

			private Tipo_Nodo tipo;
			
			public Camps() {
			}
			
			public void setRecord(Tipo_Nodo r) {
				setTipo(r);
				tipo = r;
			}
			
			public Tipo_Nodo getRecord() {
				return tipo;
			}

			public abstract void procesa(Procesamiento p);
		}

		public static class Campo_uno extends Camps {
			private Camp campo;

			public Campo_uno(Camp campo) {
				super();
				this.campo = campo;
			}

			public Camp campo() {
				return campo;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}

		public static class Campos_muchos extends Camps {
			private Camp campo;
			private Camps campos;

			public Campos_muchos(Camps campos, Camp campo) {
				super();
				this.campos = campos;
				this.campo = campo;
			}

			public Camps campos() {
				return campos;
			}

			public Camp campo() {
				return campo;
			}

			public void procesa(Procesamiento p) {
				p.procesa(this);
			}
		}
	
	// Programa

	public static class Programa extends Genero {
		private Declaraciones declaraciones;
		private Instrucciones instrucciones;

		public Programa(Declaraciones declaraciones, Instrucciones instrucciones) {
			super();
			this.declaraciones = declaraciones;
			this.instrucciones = instrucciones;
		}
		
		public Programa(Instrucciones instrucciones) {
			super();
			this.declaraciones = null;
			this.instrucciones = instrucciones;
		}

		public Declaraciones declaraciones() {
			return declaraciones;
		}
		
		public Instrucciones instrucciones() {
			return instrucciones;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	

	// Constructoras
	
	public Programa P_decs(Declaraciones declaraciones, Instrucciones instrucciones) {
		return new Programa(declaraciones, instrucciones);
	}
	
	public Programa P_nodecs(Instrucciones instrucciones) {
		return new Programa(instrucciones);
	}

	public Exp suma(Exp arg0, Exp arg1) {
		return new Suma(arg0, arg1);
	}

	public Exp resta(Exp arg0, Exp arg1) {
		return new Resta(arg0, arg1);
	}
	
	public Exp and_cons(Exp arg0, Exp arg1) {
		return new And(arg0, arg1);
	}
	
	public Exp or_cons(Exp arg0, Exp arg1) {
		return new Or(arg0, arg1);
	}
	
	public Exp menor(Exp arg0, Exp arg1) {
		return new Menor(arg0, arg1);
	}
	
	public Exp mayor(Exp arg0, Exp arg1) {
		return new Mayor(arg0, arg1);
	}
	
	public Exp menorIgual(Exp arg0, Exp arg1) {
		return new MenorIgual(arg0, arg1);
	}
	
	public Exp mayorIgual(Exp arg0, Exp arg1) {
		return new MayorIgual(arg0, arg1);
	}
	
	public Exp igual(Exp arg0, Exp arg1) {
		return new Igual(arg0, arg1);
	}
	
	public Exp distinto(Exp arg0, Exp arg1) {
		return new Distinto(arg0, arg1);
	}
	
	public Exp mul(Exp arg0, Exp arg1) {
		return new Mul(arg0, arg1);
	}

	public Exp div(Exp arg0, Exp arg1) {
		return new Div(arg0, arg1);
	}
	
	public Exp percent(Exp arg0, Exp arg1) {
		return new Percent(arg0, arg1);
	}
	
	public Exp menosUnario(Exp arg0) {
		return new MenosUnario(arg0);
	}

	public Exp star(Exp arg0) {
		return new Star(arg0);
	}
	
	public Exp not(Exp arg0) {
		return new Not(arg0);
	}

	public Exp litTrue() {
		return new True();
	}
	
	public Exp litFalse() {
		return new False();
	}
	
	public Exp litEnt(StringLocalizado arg0) {
		return new LitEnt(arg0);
	}
	
	public Exp litReal(StringLocalizado arg0) {
		return new LitReal(arg0);
	}
	
	public Exp litCad(StringLocalizado arg0) {
		return new LitCad(arg0);
	}
	
	public Exp litNull() {
		return new LitNull();
	}
	
	public Exp id(StringLocalizado arg0) {
		return new Id(arg0);
	}
	
	public Exp corchete(Exp arg0, Exp arg1) {
		return new Corchete(arg0, arg1);
	}
	
	public Exp punto(Exp arg0, StringLocalizado arg1) {
		return new Punto(arg0, arg1);
	}
	
	public Exp flecha(Exp arg0, StringLocalizado arg1) {
		return new Flecha(arg0, arg1);
	}
	
	public Tipo bool_cons() {
		return new Bool();
	}
	
	public Tipo int_cons() {
		return new Int();
	}

	public Tipo real_cons() {
		return new Real();
	}
	
	public Tipo string_cons() {
		return new String_cons();
	}
	
	public Tipo tipo_id(StringLocalizado id) {
		return new Tipo_Id(id);
	}
	
	public Tipo tipo_array(StringLocalizado tam, Tipo t) {
		return new Array(tam, t);
	}
	
	public Tipo tipo_record(Camps c) {
		return new Record(c);
	}
	
	public Tipo tipo_pointer(Tipo t) {
		return new Pointer(t);
	}
	
	public Declaracion decvar(Tipo tipo, StringLocalizado id) {
		return new DecVar(id, tipo);
	}
	
	public Declaracion dectipo(Tipo tipo, StringLocalizado id) {
		return new DecTipo(id, tipo);
	}
	
	public Declaracion decproc(StringLocalizado id, ParamForms pforms, Bloque b) {
		return new DecProc(id, pforms, b);
	}

	public Declaraciones decs_una(Declaracion declaracion) {
		return new Decs_una(declaracion);
	}

	public Declaraciones decs_muchas(Declaraciones declaraciones, Declaracion declaracion) {
		return new Decs_muchas(declaraciones, declaracion);
	}
	
	public ParamForms pformales_empty() {
		return new ParamForms_empty();
	}
	
	public ParamForms pformales_muchos(ParamForms pforms, ParamForm pform) {
		return new ParamForms_muchos(pforms, pform);
	}
	
	public ParamForms pformales_uno(ParamForm pform) {
		return new ParamForms_uno(pform);
	}
	
	public Camps campos_muchos(Camps camps, Camp camp) {
		return new Campos_muchos(camps, camp);
	}
	
	public Camps campo_uno(Camp camp) {
		return new Campo_uno(camp);
	}
	
	public Camp campo(Tipo t, StringLocalizado id) {
		return new Camp(t, id);
	}

	public Instrucciones insts_una(Instruccion instruccion) {
		return new Insts_una(instruccion);
	}

	public Instrucciones insts_muchas(Instrucciones instrucciones, Instruccion instruccion) {
		return new Insts_muchas(instrucciones, instruccion);
	}
	
	public Instruccion asig(Exp exp, Exp exp1) {
		return new Asig(exp, exp1);
	}
	
	public Instruccion if_inst(Exp exp, Instrucciones instrucciones) {
		return new If_inst(exp, instrucciones);
	}
	
	public Instruccion if_else(Exp exp, Instrucciones instrucciones, Instrucciones instrucciones_else) {
		return new If_else(exp, instrucciones, instrucciones_else);
	}
	
	public Instruccion while_inst(Exp exp, Instrucciones instrucciones) {
		return new While_inst(exp, instrucciones);
	}
	
	public Instruccion read(Exp exp) {
		return new Read(exp);
	}
	
	public Instruccion write(Exp exp) {
		return new Write(exp);
	}
	
	public Instruccion nl() {
		return new Nl();
	}
	public Instruccion new_cons(Exp exp) {
		return new New_cons(exp);
	}
	
	public Instruccion delete(Exp exp) {
		return new Delete(exp);
	}
	
	public Instruccion call(StringLocalizado s, Exps exps) {
		return new Call(s, exps);
	}
	
	public Instruccion bloque_inst(Bloque b) {
		return new Bloque_inst(b);
	}
	
	public Instrucciones lista_inst_empty () {
		return new Lista_inst_empty();
	}
	
	public Instrucciones lista_inst_muchas (Instrucciones ins, Instruccion in) {
		return new Lista_inst_muchas(ins, in);
	}
	
	public Instrucciones lista_inst_una(Instruccion in) {
		return new Lista_inst_una(in);
	}
	
	public ParamForm pformal_ref(Tipo t, StringLocalizado id) {
		return new Pformal_ref(t, id);
	}
	
	public ParamForm pformal(Tipo t, StringLocalizado id) {
		return new ParamForm(t, id);
	}
	
	public Exps lista_exp_empty() {
		return new Lista_exp_empty();
	}
	
	public Exps exp_muchas(Exps exps, Exp exp) {
		return new Exp_muchas(exps, exp);
	}
	
	public Exps exp_una(Exp exp) {
		return new Exp_una(exp);
	}
	
	public Bloque bloque_prog(Programa p) {
		return new Bloque_prog(p);
	}
	
	public Bloque no_bloque() {
		return new No_bloque();
	}
	
	public StringLocalizado str(String s, int fila, int col) {
		return new StringLocalizado(s, fila, col);
	}
}