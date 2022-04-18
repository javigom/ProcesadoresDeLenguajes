package c_ast_descendente_manual;

import java.io.IOException;
import java.io.Reader;
import asint.TinyASint.Prog;
import asint.TinyASint.Decs;
import asint.TinyASint.Dec;
import asint.TinyASint.Exp;
import semops.SemOps;


public class ConstructorAST {
   private UnidadLexica anticipo;
   private AnalizadorLexico alex;
   private GestionErrores errores;
   private SemOps sem;
   
   public ConstructorAST (Reader input) throws IOException {
      errores = new GestionErrores();
      alex = new AnalizadorLexico(input,errores);
      sigToken();
      sem = new SemOps();
   }

   public Prog Init() {
      Prog prog = Prog();
      empareja(ClaseLexica.EOF);
      return prog;
   }
   
   private Prog Prog() {
     switch(anticipo.clase()) {
         case EVALUA:          
              empareja(ClaseLexica.EVALUA);
              Exp exp = E0();
              Decs decs = PDonde();
              return sem.prog(exp,decs);
         default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                          ClaseLexica.EVALUA);     
              return null;
   }
   }
   
   private Decs PDonde() {
      switch(anticipo.clase()) {
          case DONDE:
              empareja(ClaseLexica.DONDE);
              return Decs();
          case EOF: return null;
          default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                          ClaseLexica.DONDE,ClaseLexica.EOF);
                   return null;
      } 
   }
   
   private Decs Decs() {
      switch(anticipo.clase()) {
       case IDEN:    
           Dec dec = Dec();
           return RDecs(sem.decs_una(dec));
       default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                         ClaseLexica.IDEN);                                      
                 return null;
   }
   }   
   
   private Decs RDecs(Decs decsh) {
      switch(anticipo.clase()) {
       case COMA:    
           empareja(ClaseLexica.COMA);
           Dec dec = Dec();
           return RDecs(sem.decs_muchas(decsh,dec));
       case EOF: return decsh;    
       default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                         ClaseLexica.COMA, ClaseLexica.EOF);                                
                 return null;        
      }          
   }  
   
   private Dec Dec() {
     switch(anticipo.clase()) {       
       case IDEN:   
        UnidadLexica tkIden = anticipo;
        empareja(ClaseLexica.IDEN);
        empareja(ClaseLexica.IGUAL);
        UnidadLexica tkNum = anticipo;
        empareja(ClaseLexica.NUM);
        return sem.dec(sem.str(tkIden.lexema(),tkIden.fila(),tkIden.columna()),
                       sem.str(tkNum.lexema(),tkNum.fila(),tkNum.columna()));
       default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                         ClaseLexica.IDEN);                 
                return null;
     }
   }
   

   private Exp E0() {
     switch(anticipo.clase()) {
         case IDEN: case NUM: case PAP:
             Exp exp1 = E1();
             return RE0(exp1);
         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                           ClaseLexica.IDEN,ClaseLexica.NUM,
                                           ClaseLexica.PAP);
                   return null; 
     }  
   }
   private Exp RE0(Exp exph) {
      switch(anticipo.clase()) {
          case MAS: case MENOS: 
             char op = Op0();
             Exp exp1 = E1();
             return RE0(sem.exp(op, exph,exp1));
          case DONDE: case PCIERRE: case EOF: case COMA: return exph;
          default:    
              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                      ClaseLexica.MAS,ClaseLexica.MENOS);
                      return null;
      } 
   }
   private Exp E1() {
     switch(anticipo.clase()) {
         case IDEN: case NUM: case PAP:
             Exp exp2 = E2();
             return RE1(exp2);
         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                           ClaseLexica.IDEN,ClaseLexica.NUM,
                                           ClaseLexica.PAP);                                   
             return null;
     }  
   }
   private Exp RE1(Exp exph) {
      switch(anticipo.clase()) {
          case POR: case DIV: 
             char op = Op1();
             Exp exp2 = E2();
             return RE1(sem.exp(op, exph, exp2));
          case DONDE: case PCIERRE: case EOF: case MAS: case MENOS: case COMA: return exph;
          default:    
              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                      ClaseLexica.POR,ClaseLexica.DIV,
                                      ClaseLexica.MAS, ClaseLexica.MENOS);                                 
              return null;
      } 
   }
   private Exp E2() {
      switch(anticipo.clase()) {
          case NUM: UnidadLexica tkNum = anticipo;
                    empareja(ClaseLexica.NUM); 
                    return sem.num(sem.str(tkNum.lexema(), tkNum.fila(), 
                                           tkNum.columna()));
          case IDEN: UnidadLexica tkIden = anticipo;
                     empareja(ClaseLexica.IDEN); 
                     return sem.id(sem.str(tkIden.lexema(), tkIden.fila(), 
                                           tkIden.columna()));
          case PAP: 
               empareja(ClaseLexica.PAP); 
               Exp exp = E0(); 
               empareja(ClaseLexica.PCIERRE); 
               return exp;
          default:     
              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                      ClaseLexica.NUM,
                                      ClaseLexica.PAP);
                      return null;
   }   
   }
   private char Op0() {
     switch(anticipo.clase()) {
         case MAS: empareja(ClaseLexica.MAS); return '+';  
         case MENOS: empareja(ClaseLexica.MENOS); return '-';  
         default:    
              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                      ClaseLexica.MAS,ClaseLexica.MENOS);
              return '?';
     }  
   }
   private char Op1() {
     switch(anticipo.clase()) {
         case POR: empareja(ClaseLexica.POR); return '*';  
         case DIV: empareja(ClaseLexica.DIV); return '/';  
         default:    
              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                      ClaseLexica.POR,ClaseLexica.DIV);
              return '?';
     }  
   }
   
   private void empareja(ClaseLexica claseEsperada) {
      if (anticipo.clase() == claseEsperada)
          sigToken();
      else errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),claseEsperada);
   }
   private void sigToken() {
      try {
        anticipo = alex.sigToken(); 
      }
      catch(IOException e) {
        errores.errorFatal(e);
      }
   }
   
}
