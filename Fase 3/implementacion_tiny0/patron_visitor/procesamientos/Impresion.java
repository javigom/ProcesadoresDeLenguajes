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
import asint.TinyASint.Exp;


public class Impresion extends ProcesamientoPorDefecto {
   public Impresion() {
   }
   
   public void procesa(Programa prog) {
       System.out.println("evalua");
       System.out.print("  ");
       prog.declaraciones().procesa(this);
       System.out.println();
       System.out.println("donde");
       prog.instrucciones().procesa(this);
       System.out.println();       
   }    
   public void procesa(Decs_muchas decs) {
       decs.declaraciones().procesa(this);
       System.out.println(",");
       decs.declaracion().procesa(this);
   }
   public void procesa(Decs_una decs) {
       decs.declaracion().procesa(this);
   }
   public void procesa(Declaracion dec) {
       System.out.print("  "+dec.id()+"="+dec.val());
   }
   public void procesa(Suma exp) {
      imprime_arg(exp.arg0(),0); 
      System.out.print("+");
      imprime_arg(exp.arg1(),1);       
   }
   public void procesa(Resta exp) {
      imprime_arg(exp.arg0(),0); 
      System.out.print("+");
      imprime_arg(exp.arg1(),1);       
   }
   public void procesa(Mul exp) {
      imprime_arg(exp.arg0(),1); 
      System.out.print("*");
      imprime_arg(exp.arg1(),2);       
   }
   public void procesa(Div exp) {
      imprime_arg(exp.arg0(),1); 
      System.out.print("/");
      imprime_arg(exp.arg1(),2);       
   }
   private void imprime_arg(Exp arg, int p) {
       if (arg.prioridad() < p) {
           System.out.print("(");
           arg.procesa(this);
           System.out.print(")");
       }
       else {
           arg.procesa(this);
       }
   }
   public void procesa(Id exp) {
       System.out.print(exp.id());
   }
   public void procesa(LitEnt exp) {
       System.out.print(exp.num());
   }
}   

            