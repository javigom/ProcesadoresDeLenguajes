package procesamientos;

import asint.TinyASint.Suma;
import asint.TinyASint.Resta;
import asint.TinyASint.Mul;
import asint.TinyASint.Div;
import asint.TinyASint.Id;
import asint.TinyASint.Num;
import asint.TinyASint.Dec;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Prog_sin_decs;
import asint.TinyASint.Prog_con_decs;
import asint.ProcesamientoPorDefecto;
import java.util.HashMap;

class Valores extends HashMap<String,Double> {}

public class Evaluacion extends ProcesamientoPorDefecto {
   private Valores valores;
   private double resul;
   public Evaluacion() {
       valores = new Valores();
   }
   public void procesa(Prog_sin_decs prog) {
       prog.exp().procesa(this);
       System.out.println(">>>>"+resul);
   }    
   public void procesa(Prog_con_decs prog) {
       prog.decs().procesa(this);
       prog.exp().procesa(this);
       System.out.println(">>>>"+resul);
   }    
   public void procesa(Decs_muchas decs) {
       decs.decs().procesa(this);
       decs.dec().procesa(this);
   }
   public void procesa(Decs_una decs) {
       decs.dec().procesa(this);
   }
   public void procesa(Dec dec) {
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
   public void procesa(Num exp) {
       resul = Double.valueOf(exp.num().toString()).doubleValue();
   }
}   
 
