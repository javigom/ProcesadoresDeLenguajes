package procesamientos;

import asint.TinyASint.Prog;
import asint.TinyASint.Exp;
import asint.TinyASint.Decs;
import asint.TinyASint.Dec;
import asint.TinyASint.TNodo;

public class Impresion {
    public static void imprime(Prog prog) {
       System.out.println("EVALUA");
       System.out.print("   ");
       imprime_exp(prog.exp());
       System.out.println();
       if (prog.tipo() == TNodo.PROG_CON_DECS) {
           System.out.println("DONDE");
           imprime_decs(prog.decs());
           System.out.println();
       }
    }
    
    private static void imprime_exp(Exp exp) {
        if (exp.tipo() == TNodo.NUM) {
            System.out.print(exp.num());
        }
        else if (exp.tipo() == TNodo.ID) {
            System.out.print(exp.id());
        }
        else if (exp_aditiva(exp)) {
            imprime_exp(exp.arg0());
            imprime_op(exp);
            if (exp_aditiva(exp.arg1())) {
                imprime_exp_entre_parentesis(exp.arg1());
            }
            else {
                imprime_exp(exp.arg1());
            }
        }
        else {
            if (exp_aditiva(exp.arg0())) {
                imprime_exp_entre_parentesis(exp.arg0());
            }
            else {
               imprime_exp(exp.arg0()); 
            }
            imprime_op(exp);
            if (exp_aditiva(exp.arg1()) || exp_multiplicativa(exp.arg1())) {
                imprime_exp_entre_parentesis(exp.arg1());
            }
            else {
                imprime_exp(exp.arg1());
            }
        }
    }  
    
    private static void imprime_exp_entre_parentesis(Exp exp) {
        System.out.print("(");
        imprime_exp(exp);
        System.out.print(")");
    }
    
    private static void imprime_op(Exp exp) {
        switch(exp.tipo()) {
            case SUMA: System.out.print("+"); break;
            case RESTA: System.out.print("-"); break;
            case MUL: System.out.print("*"); break;
            case DIV: System.out.print("/"); break;
         }
    }
    
    private static boolean exp_aditiva(Exp exp) {
        return exp.tipo() == TNodo.SUMA ||
               exp.tipo() == TNodo.RESTA;
    }
    private static boolean exp_multiplicativa(Exp exp) {
        return exp.tipo() == TNodo.MUL ||
               exp.tipo() == TNodo.DIV;
    }
    
    private static void imprime_decs(Decs decs) {
        if (decs.tipo() == TNodo.DECS_MUCHAS) {
          imprime_decs(decs.decs());
          System.out.println(",");
          imprime_dec(decs.dec());    
        }
        else {
          imprime_dec(decs.dec());   
        }
    }
    
    private static void imprime_dec(Dec dec) {
       System.out.print("  "+dec.id()+"="+dec.val());  
       
    }
            
}            
            