package procesamientos;

import asint.TinyASint.Prog;
import asint.TinyASint.Exp;
import asint.TinyASint.Decs;
import asint.TinyASint.TNodo;
import java.util.HashMap;

class Valores extends HashMap<String,Double> {}

public class Evaluacion {
    public static void evalua(Prog prog) {
        Valores vals = new Valores();
        if (prog.tipo() == TNodo.PROG_CON_DECS) {
            recolecta_valores(prog.decs(),vals);
        }
        System.out.println(">>>>>"+evalua(prog.exp(),vals));
         }
    
    private static double evalua(Exp exp,Valores vals) {
        if (exp.tipo() == TNodo.NUM) {
            return Double.valueOf(exp.num().toString()).doubleValue();
        }
        else if (exp.tipo() == TNodo.ID) {
            Double val = vals.get(exp.id().toString());
            if (val == null)
               throw new RuntimeException("Constante no definida:"+exp.id().toString()+
                                          ". Fila: "+exp.id().fila()+", col: "+exp.id().col());
            else 
               return val; 
        }
        else { 
            double arg0 = evalua(exp.arg0(),vals);
            double arg1 = evalua(exp.arg1(),vals);
            double resul;
            switch(exp.tipo()) {
                case SUMA: resul = arg0+arg1; break;
                case RESTA: resul = arg0-arg1; break;
                case MUL: resul = arg0*arg1; break;
                case DIV: resul = arg0/arg1; break;
                default: resul = -1;
            }
            return resul;
        }    
    }
    
    
    private static void recolecta_valores(Decs decs, Valores vals) {
        if (decs.tipo() == TNodo.DECS_MUCHAS) {
          recolecta_valores(decs.decs(),vals);
        }
        if (vals.containsKey(decs.dec().id().toString())) {
            throw new RuntimeException("Constante ya definida "+decs.dec().id()+
                                        ".Fila: "+decs.dec().id().fila()+", col: "+decs.dec().id().col());
        }
        else {
           vals.put(decs.dec().id().toString(), Double.valueOf(decs.dec().val().toString()).doubleValue());
        }   
    }    
            
}            
