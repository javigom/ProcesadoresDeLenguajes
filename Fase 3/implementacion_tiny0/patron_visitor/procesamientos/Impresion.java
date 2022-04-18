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
import asint.TinyASint.Exp;


public class Impresion extends ProcesamientoPorDefecto {
   public Impresion() {
   }
   public void procesa(Prog_sin_decs prog) {
       System.out.println("evalua");
       System.out.print("  ");
       prog.exp().procesa(this);
       System.out.println();
   }    
   public void procesa(Prog_con_decs prog) {
       System.out.println("evalua");
       System.out.print("  ");
       prog.exp().procesa(this);
       System.out.println();
       System.out.println("donde");
       prog.decs().procesa(this);
       System.out.println();       
   }    
   public void procesa(Decs_muchas decs) {
       decs.decs().procesa(this);
       System.out.println(",");
       decs.dec().procesa(this);
   }
   public void procesa(Decs_una decs) {
       decs.dec().procesa(this);
   }
   public void procesa(Dec dec) {
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
   public void procesa(Num exp) {
       System.out.print(exp.num());
   }
}   

            