package procesamientos;

import asint.TinyASint.Suma;
import asint.TinyASint.Resta;
import asint.TinyASint.Mul;
import asint.TinyASint.Div;
import asint.TinyASint.Id;
import asint.TinyASint.LitEnt;
import asint.TinyASint.Declaracion;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Programa;
import asint.ProcesamientoPorDefecto;
import java.util.HashMap;

class Valores extends HashMap<String,Double> {}

public class Evaluacion extends ProcesamientoPorDefecto {
   private Valores valores;
   private double resul;
   public Evaluacion() {
       valores = new Valores();
   }   
   public void procesa(Programa prog) {
       prog.declaraciones().procesa(this);
       prog.instrucciones().procesa(this);
       System.out.println(">>>>"+resul);
   }    
   public void procesa(Decs_muchas decs) {
       decs.declaraciones().procesa(this);
       decs.declaracion().procesa(this);
   }
   public void procesa(Decs_una decs) {
       decs.declaracion().procesa(this);
   }
   public void procesa(Declaracion dec) {
       if (valores.containsKey(dec.id().toString())) {
          throw new RuntimeException("Constante ya definida "+dec.id()+
                                        ".Fila: "+dec.id().fila()+", col: "+dec.id().col());
       }
        else {
           valores.put(dec.id().toString(), Double.valueOf(dec.val().toString()).doubleValue());
        }   
   }
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
   public void procesa(Id exp) {
       Double val = valores.get(exp.id().toString());
       if (val == null)
          throw new RuntimeException("Constante no definida:"+exp.id().toString()+
                                    ". Fila: "+exp.id().fila()+", col: "+exp.id().col());
       else 
         resul = val; 
   }
   public void procesa(LitEnt exp) {
       resul = Double.valueOf(exp.num().toString()).doubleValue();
   }
}   
 
