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
import asint.TinyASint.Prog_sin_decs;
import asint.TinyASint.Prog_con_decs;

public interface Procesamiento {
    void procesa(Suma exp);
    void procesa(Resta exp);
    void procesa(Mul exp);
    void procesa(Div exp);
    void procesa(Id exp);
    void procesa(Num exp);
    void procesa(Dec dec);
    void procesa(Decs_muchas decs);
    void procesa(Decs_una decs);
    void procesa(Prog_sin_decs prog);    
    void procesa(Prog_con_decs prog);    
}