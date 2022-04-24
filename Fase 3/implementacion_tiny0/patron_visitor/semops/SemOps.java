package semops;

import asint.Procesamiento;
import asint.TinyASint;
import asint.TinyASint.Exp;
import asint.TinyASint.ExpAditiva;

public class SemOps extends TinyASint {
   public Exp exp(char op, Exp arg0, Exp arg1) {
       switch(op) {
           case '+': return suma(arg0,arg1);
           case '-': return resta(arg0,arg1);
           case '*': return mul(arg0,arg1);
           case '/': return div(arg0,arg1);
       }
       throw new UnsupportedOperationException("exp "+op);
   }  
   public Prog prog(Exp exp, Decs decs) {
       if (decs == null) return prog_sin_decs(exp);
       else return prog_con_decs(exp,decs);
   }
   
   
   
   ////////////////// Pasar a asint pero no quiero reescribir tu codigo
   
   
   private static abstract class ExpUn extends Exp {
       private Exp arg;
       public Exp arg() {return arg;}
       public ExpUn(Exp arg) {
           super();
           this.arg = arg;
       }
   }
   
   public static class Not extends ExpUn {
       public Not(Exp arg) {
           super(arg);
       }
       public final int prioridad() {
           return 2;
       }
       public void procesa(Procesamiento p) {
          p.procesa(this); 
       }     
   }
   
   public static class Neg extends ExpUn {
       public Neg(Exp arg) {
           super(arg);
       }
       public final int prioridad() {
           return 2;
       }
       public void procesa(Procesamiento p) {
          p.procesa(this); 
       }     
   }
   
   public Exp not(Exp arg) {
	   return new Not(arg);
   }
   
   public Exp neg(Exp arg) {
       return new Neg(arg);
   }
}
