package asint;

import asint.TinyASint.Suma;
import asint.TinyASint.Resta;
import asint.TinyASint.Mul;
import asint.TinyASint.Div;
import asint.TinyASint.Id;
import asint.TinyASint.Num;
import asint.TinyASint.Dec;
import asint.TinyASint.Decs_muchas;
import asint.TinyASint.Decs_una;
import asint.TinyASint.Prog_con_decs;
import asint.TinyASint.Prog_sin_decs;


public class ProcesamientoPorDefecto implements Procesamiento {
   public void procesa(Suma exp) {}
   public void procesa(Resta exp) {}
   public void procesa(Mul exp) {}
   public void procesa(Div exp) {}
   public void procesa(Id exp) {}
   public void procesa(Num exp) {}
   public void procesa(Dec dec) {}
   public void procesa(Decs_muchas decs) {}
   public void procesa(Decs_una decs) {}
   public void procesa(Prog_sin_decs prog) {}    
   public void procesa(Prog_con_decs prog) {}    
}
