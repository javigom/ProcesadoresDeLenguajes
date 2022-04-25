package procesamientos;

import asint.TinyASint.Suma;
import asint.TinyASint.Tipo;
import asint.TinyASint.True;
import asint.TinyASint.Resta;
import asint.TinyASint.Mul;
import asint.TinyASint.Not;
import asint.TinyASint.Or;
import asint.TinyASint.Div;
import asint.TinyASint.False;
import asint.TinyASint.Id;
import asint.TinyASint.Igual;
import asint.TinyASint.Instruccion;
import asint.TinyASint.Insts_muchas;
import asint.TinyASint.Insts_una;
import asint.TinyASint.LitEnt;
import asint.TinyASint.LitReal;
import asint.TinyASint.Mayor;
import asint.TinyASint.MayorIgual;
import asint.TinyASint.Menor;
import asint.TinyASint.MenorIgual;
import asint.TinyASint.MenosUnario;
import asint.TinyASint.And;
import asint.TinyASint.Declaracion;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Distinto;
import asint.TinyASint.Programa;
import asint.ProcesamientoPorDefecto;
import java.util.HashMap;

class Valores extends HashMap<String,Double> {}
class ValoresB extends HashMap<String,Boolean> {}

public class Evaluacion extends ProcesamientoPorDefecto {
   private Valores valores;
   private ValoresB valoresB;
   private double resul;
   private String resulTipo;
   private boolean resulBool;
   public Evaluacion() {
       valores = new Valores();
       valoresB = new ValoresB();
   }   
	
   // Programa
   
   public void procesa(Programa prog) {
       prog.declaraciones().procesa(this);
       prog.instrucciones().procesa(this);
       System.out.println(">>>>"+resul);
   }    
   
	// Declaraciones
   
   public void procesa(Decs_muchas decs) {
       decs.declaraciones().procesa(this);
       decs.declaracion().procesa(this);
   }
   public void procesa(Decs_una decs) {
       decs.declaracion().procesa(this);
   }
   
   ////////// Aunque  sean de distinto tipo mismo nimbre no se puede
   public void procesa(Declaracion dec) {
       if (valores.containsKey(dec.id().toString()) || valoresB.containsKey(dec.id().toString())) {
          throw new RuntimeException("Constante ya definida "+dec.id()+
                                        ".Fila: "+dec.id().fila()+", col: "+dec.id().col());
       }
        else {
        	dec.val().procesa(this);
        	if(resulTipo == "bool") 
        		valoresB.put(dec.id().toString(), false);
        	else 
        		valores.put(dec.id().toString(), 0.0);
        }   
   }

	// Instrucciones

	public void procesa(Insts_muchas insts) {
		insts.instrucciones().procesa(this);
		insts.instruccion().procesa(this);
	}

	public void procesa(Insts_una insts) {
		insts.instruccion().procesa(this);
	}
	

	public void procesa(Instruccion instruccion) {
		if (!valores.containsKey(instruccion.id().toString())) {
			if (!valoresB.containsKey(instruccion.id().toString()))
				throw new RuntimeException("Constante no definida:"+instruccion.id().toString()+
                            ". Fila: "+instruccion.id().fila()+", col: "+instruccion.id().col());
			else
				instruccion.exp().procesa(this);
				valoresB.replace(instruccion.id().toString(), resulBool);
        }  
		else
			instruccion.exp().procesa(this);
			valores.replace(instruccion.id().toString(), resul);
	}
   

	// Operadores

	// Nivel 0
	public void procesa(Suma exp) {
		exp.arg0().procesa(this);
       	double v0 = resul;
       	exp.arg1().procesa(this);
       	resul += v0;
	}
	public void procesa(Resta exp) {
		exp.arg0().procesa(this);
		double v0 = resul;
		exp.arg1().procesa(this);
		resul = v0 - resul;
	}

	// Nivel 1
	public void procesa(And exp) {
        exp.arg0().procesa(this);
        boolean v0 = resulBool;
        exp.arg1().procesa(this);
   		resulBool = v0 && resulBool;
	}

	public void procesa(Or exp) {
        exp.arg0().procesa(this);
        boolean v0 = resulBool;
        exp.arg1().procesa(this);
   		resulBool = v0 || resulBool;
	}
	
	// Nivel 2
   	public void procesa(Menor exp) {
        exp.arg0().procesa(this);
        double v0 = resul;
        exp.arg1().procesa(this);
   		if(v0 < resul) {
   			resulBool = true;
   		}
   		else {
   			resulBool = false;
   		}
   }

   	public void procesa(Mayor exp) {
        exp.arg0().procesa(this);
        double v0 = resul;
        exp.arg1().procesa(this);
   		if(v0 > resul) {
   			resulBool = true;
   		}
   		else {
   			resulBool = false;
   		}
	}

   	public void procesa(MenorIgual exp) {
        exp.arg0().procesa(this);
        double v0 = resul;
        exp.arg1().procesa(this);
   		if(v0 <= resul) {
   			resulBool = true;
   		}
   		else {
   			resulBool = false;
   		}
	}

   	public void procesa(MayorIgual exp) {
        exp.arg0().procesa(this);
        double v0 = resul;
        exp.arg1().procesa(this);
   		if(v0 >= resul) {
   			resulBool = true;
   		}
   		else {
   			resulBool = false;
   		}
	}

   	public void procesa(Igual exp) {
        exp.arg0().procesa(this);
        double v0 = resul;
        exp.arg1().procesa(this);
   		if(v0 == resul) {
   			resulBool = true;
   		}
   		else {
   			resulBool = false;
   		}
	}

   	public void procesa(Distinto exp) {
        exp.arg0().procesa(this);
        double v0 = resul;
        exp.arg1().procesa(this);
   		if(v0 != resul) {
   			resulBool = true;
   		}
   		else {
   			resulBool = false;
   		}
	}
   
	// Nivel 3
   	public void procesa(Mul exp) {
       exp.arg0().procesa(this);
       double v0 = resul;
       exp.arg1().procesa(this);
       resul *= v0;
   	}
   	public void procesa(Div exp) {
       exp.arg0().procesa(this);
       double v0 = resul;
       exp.arg1().procesa(this);
       resul = v0 / resul;
   	}
   	
	// Nivel 4
   	public void procesa(MenosUnario exp) {
        exp.arg0().procesa(this);
        resul = -(resul);
	}

	public void procesa(Not exp) {
        exp.arg0().procesa(this);
        resulBool = !resulBool;
	}
   	
	// Nivel 5
   	public void procesa(True exp) {
	     resulBool = true;
	}

	public void procesa(False exp) {
	     resulBool = false;
	}
   	
   	public void procesa(Id exp) {
       Double val = valores.get(exp.id().toString());
       if (val == null) {
    	   Boolean valB = valoresB.get(exp.id().toString());
	       if (valB == null)
	          throw new RuntimeException("Constante no definida:"+exp.id().toString()+
	                                    ". Fila: "+exp.id().fila()+", col: "+exp.id().col());
	       else
	    	   resulBool = valB;
       }  
       else
         resul = val;
   	}
   	public void procesa(LitEnt exp) {
	   resul = Double.valueOf(exp.num().toString()).doubleValue();
   	}

   	public void procesa(LitReal exp) {
       resul = Double.valueOf(exp.num().toString()).doubleValue();
   	}
   	
	// Tipo
    public void procesa(Tipo tipo) {
    	resulTipo = tipo.tipo().toString();
    }
}   
 
