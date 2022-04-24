package asint;

public class TinyASint {

	public static abstract class Exp {
		public Exp() {
		}

		public abstract int prioridad();

		public abstract void procesa(Procesamiento procesamiento);
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
		
		public final int prioridad() {
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
	
	
	// Nivel 5

	public static class True extends Exp {
		public True() {
			
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		
		public final int prioridad() {
			return 5;
		}
	}
	
	public static class False extends Exp {
		public False() {
			
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
		
		public final int prioridad() {
			return 5;
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
			return 5;
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
			return 5;
		}
	}

	public static class Id extends Exp {
		private StringLocalizado id;

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
			return 5;
		}
	}
	
	

	// Tipo
	
	public static class Tipo {
		private StringLocalizado tipo;

		public Tipo(StringLocalizado tipo) {
			this.tipo = tipo;
		}

		public StringLocalizado tipo() {
			return tipo;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	public static class Bool extends Tipo {
		public Bool(StringLocalizado tipo) {
			super(tipo);
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Int extends Tipo {
		public Int(StringLocalizado tipo) {
			super(tipo);
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class Real extends Tipo {
		public Real(StringLocalizado tipo) {
			super(tipo);
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	
	// Declaraciones

	public static class Declaracion {
		private StringLocalizado id;
		private Tipo val;

		public Declaracion(StringLocalizado id, Tipo val) {
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

	public static abstract class Declaraciones {
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
	
	// Instrucciones
	
	public static class Instruccion {
		private StringLocalizado id;
		private Exp exp;

		public Instruccion(StringLocalizado id, Exp exp) {
			this.id = id;
			this.exp = exp;
		}

		public StringLocalizado id() {
			return id;
		}

		public Exp exp() {
			return exp;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static abstract class Instrucciones {
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
	
	// Programa

	public static class Programa {
		private Declaraciones declaraciones;
		private Instrucciones instrucciones;

		public Programa(Declaraciones declaraciones, Instrucciones instrucciones) {
			super();
			this.declaraciones = declaraciones;
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
	
	public Programa Programa(Declaraciones declaraciones, Instrucciones instrucciones) {
		return new Programa(declaraciones, instrucciones);
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
	
	public Exp menosUnario(Exp arg0) {
		return new MenosUnario(arg0);
	}

	public Exp true_cons() {
		return new True();
	}
	
	public Exp false_cons() {
		return new False();
	}
	
	public Exp litEnt(StringLocalizado arg0) {
		return new LitEnt(arg0);
	}
	
	public Exp litReal(StringLocalizado arg0) {
		return new LitReal(arg0);
	}
	
	public Exp id(StringLocalizado arg0) {
		return new Id(arg0);
	}
	
	public Tipo Bool(StringLocalizado arg0) {
		return Bool(arg0);
	}
	
	public Tipo Int(StringLocalizado arg0) {
		return Int(arg0);
	}

	public Tipo Real(StringLocalizado arg0) {
		return Real(arg0);
	}
	
	public Declaracion declaracion(Tipo tipo, StringLocalizado id) {
		return new Declaracion(id, tipo);
	}

	public Declaraciones decs_una(Declaracion declaracion) {
		return new Decs_una(declaracion);
	}

	public Declaraciones decs_muchas(Declaraciones declaraciones, Declaracion declaracion) {
		return new Decs_muchas(declaraciones, declaracion);
	}
	
	public Instruccion instruccion(StringLocalizado id, Exp exp) {
		return new Instruccion(id, exp);
	}

	public Instrucciones insts_una(Instruccion instruccion) {
		return new Insts_una(instruccion);
	}

	public Instrucciones insts_muchas(Instrucciones instrucciones, Instruccion instruccion) {
		return new Insts_muchas(instrucciones, instruccion);
	}
	
	public StringLocalizado str(String s, int fila, int col) {
		return new StringLocalizado(s, fila, col);
	}
}
