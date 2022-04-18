package semops;

import asint.TinyASint;

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
}
