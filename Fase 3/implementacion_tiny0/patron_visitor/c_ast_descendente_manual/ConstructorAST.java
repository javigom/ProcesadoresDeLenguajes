package c_ast_descendente_manual;

import java.io.IOException;
import java.io.Reader;
import asint.TinyASint.Prog;
import errors.GestionErroresTiny;
import asint.TinyASint.Decs;
import asint.TinyASint.Dec;
import asint.TinyASint.Exp;
import semops.SemOps;


public class ConstructorAST {
   private UnidadLexica anticipo;
   private AnalizadorLexicoTiny alex;
   private GestionErroresTiny errores;
   private SemOps sem;
   
   public ConstructorAST (Reader input) throws IOException {
      errores = new GestionErroresTiny();
		alex = new AnalizadorLexicoTiny(input);
		alex.fijaGestionErrores(errores);
      sigToken();
      sem = new SemOps();
   }

   public Prog PROGRAMAp() {
	   Prog prog = PROGRAMA();
	   empareja(ClaseLexica.EOF);
	   return prog;
   }
   
   private Prog PROGRAMA() {
	   switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			Decs decs = DECLARACIONES();
			empareja(ClaseLexica.DAMP);
			Ins ins =INSTRUCCIONES();
			return sem.prog(decs,ins);
		default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
        		 						 ClaseLexica.BOOL, ClaseLexica.INT, ClaseLexica.REAL);     
			return null;
	   }
   }
   
   private Decs DECLARACIONES() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			Dec dec = DECLARACION();
	        return RDEC(sem.decs_una(dec));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}
   
   
   private Decs RDEC(Decs decsh) {
		switch (anticipo.clase()) {
		case DAMP:
			break;
		case PCOMA:
			empareja(ClaseLexica.PCOMA);
			Dec dec = DECLARACION();
	        return RDEC(sem.decs_muchas(decsh,dec));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DAMP,
					ClaseLexica.PCOMA);
			return null;
		}
	}
	
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
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
