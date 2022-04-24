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
			return sem.nodecs(decsh);
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
	
   
   private Dec DECLARACION() {
		switch (anticipo.clase()) {
		case BOOL:
		case INT:
		case REAL:
			Type tipo = TIPO();
	        UnidadLexica tkid = anticipo;
			empareja(ClaseLexica.ID);

	        return sem.dec(sem.str(tipo.lexema(),tipo.fila(),tipo.columna()),
	                       sem.str(tkid.lexema(),tkid.fila(),tkid.columna()));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL, 
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}
	
	private Type TIPO() {
		switch (anticipo.clase()) {
		case BOOL:
			UnidadLexica tkbool = anticipo;
			empareja(ClaseLexica.BOOL);
			return sem.Bool(sem.str(tkbool.lexema(), tkbool.fila(), 
					tkbool.columna()));
		case INT:
			UnidadLexica tkbool = anticipo;
			empareja(ClaseLexica.INT);
			return sem.Bool(sem.str(tkbool.lexema(), tkbool.fila(), 
					tkbool.columna()));
		case REAL:
			UnidadLexica tkbool = anticipo;
			empareja(ClaseLexica.REAL);
			return sem.Bool(sem.str(tkbool.lexema(), tkbool.fila(), 
					tkbool.columna()));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.BOOL,
					ClaseLexica.INT, ClaseLexica.REAL);
			return null;
		}
	}
		   

	private Ins INSTRUCCIONES() {
		switch (anticipo.clase()) {
		case ID:
			In in = INSTRUCCION();
	        return RINS(sem.ins_una(in));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID);
			return null;
		}
	}
	
	private Ins RINS(Ins insh) {
		switch (anticipo.clase()) {
		case PCOMA:
			empareja(ClaseLexica.PCOMA);
			In in = INSTRUCCION();
	        return RDINS(sem.ins_muchas(insh,in));
		case EOF:
			return noins(insh);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PCOMA,
					ClaseLexica.EOF);
			return null;
		}
	}

	private In INSTRUCCION() {
		switch (anticipo.clase()) {
		case ID:
	        UnidadLexica tkid = anticipo;
			empareja(ClaseLexica.ID);
			empareja(ClaseLexica.IGUAL);
			Exp exp0 = E0();

	        return sem.ins(sem.str(tkid.lexema(),tkid.fila(),tkid.columna()),
	                       sem.str(exp0));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.ID);
			return null;
		}
	}
   
   private Exp E0() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp1 = E1();
			return RE0(exp1);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
            return null;
		}
	}
   
   private Exp RE0(Exp exph) {
		switch (anticipo.clase()) {
		case MAS:
		case MENOS:
			char op = Op0();
			Exp exp1 = E1();
			return RE0(sem.exp(op, exph,exp1));
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MAS,
					ClaseLexica.MENOS, ClaseLexica.ID);
			return null;
		}
	}
   
   


	private Exp E1() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
            Exp exp2 = E2();
            return RE1(exp2);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);                                   
            return null;
		}
	}
   
   private Exp RE1(Exp exph) {
		switch (anticipo.clase()) {
		case AND:
		case OR:
            char op = OPBN1();
            Exp exp2 = E2();
            return RE1(sem.exp(op, exph, exp2));
		case MAS:
		case MENOS:
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.AND,
					ClaseLexica.OR, ClaseLexica.MAS, ClaseLexica.MENOS);                                 
            return null;
		}
	}
   
   private Exp E2() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp3 = E3();
			return RE2(exp3);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}
   
   
   private Exp RE2(Exp exph) {
		switch (anticipo.clase()) {
		case DIGUAL:
		case DIF:
		case MAYOR_IGUAL:
		case MAYOR:
		case MENOR_IGUAL:
		case MENOR:
			char op = OPBN2();
			Exp exp3 = E3();
			return RE2(sem.exp(op, exph, exp3));
		case AND:
		case MAS:
		case MENOS:
		case OR:
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DIF,
					ClaseLexica.DIGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL, ClaseLexica.MENOR,
					ClaseLexica.AND, ClaseLexica.MAS, ClaseLexica.MENOS, ClaseLexica.OR);
			return null;
		}
	}
   
   
   private Exp E3() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case MENOS:
		case NOT:
		case PAP:
		case TRUE:
			Exp exp4 = E4();
			return RE3(exp4);
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}
   
   
   private Exp RE3(Exp exph) {
		switch (anticipo.clase()) {
		case DIV:
		case POR:
			char op = OPBN3();
			Exp exp4 = E4();
			return sem.expN3(op, exph, exp4);
		case AND:
		case DIGUAL:
		case DIF:
		case MAS:
		case MENOS:
		case MAYOR_IGUAL:
		case MENOR_IGUAL:
		case MAYOR:
		case MENOR:
		case OR:
		case PCIERRE:
		case PCOMA:
		case EOF:
			return exph;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.DIV,
					ClaseLexica.POR, ClaseLexica.AND, ClaseLexica.DIF, ClaseLexica.DIGUAL, ClaseLexica.MAS, 
					ClaseLexica.MENOS, ClaseLexica.MAYOR_IGUAL, ClaseLexica.MENOR_IGUAL, ClaseLexica.OR);
			return null;
		}
	}
   
   
   private Exp E4() {
		switch (anticipo.clase()) {
		case NOT:
			empareja(ClaseLexica.NOT);
			Exp exp4 = E4();
			return sem.not(exp4);
		case MENOS:
			empareja(ClaseLexica.MENOS);
			Exp exp5 = E5();
			return sem.neg(exp5);
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case PAP:
		case TRUE:
			return E5();
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.FALSE,
					ClaseLexica.ID, ClaseLexica.LIT_ENT, ClaseLexica.LIT_REAL, ClaseLexica.MENOS, ClaseLexica.NOT,
					ClaseLexica.PAP, ClaseLexica.TRUE);
			return null;
		}
	}
   
   private Exp E5() {
		switch (anticipo.clase()) {
		case FALSE:
		case ID:
		case LIT_ENT:
		case LIT_REAL:
		case TRUE:
			return EXPRESION();
		case PAP:
			empareja(ClaseLexica.PAP);
			Exp exp0 = E0();
			empareja(ClaseLexica.PCIERRE);
            return exp0;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.LIT_ENT,
					ClaseLexica.ID, ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.PAP);
			return null;
		}
	}
	
	private Exp EXPRESION() {
		switch (anticipo.clase()) {
		case TRUE:
			UnidadLexica tktrue = anticipo;
			empareja(ClaseLexica.TRUE);
			return sem.True(sem.str(tktrue.lexema(), tktrue.fila(), 
					tktrue.columna()));
		case FALSE:
			UnidadLexica tkfalse = anticipo;
			empareja(ClaseLexica.FALSE);
			return sem.False(sem.str(tkfalse.lexema(), tkfalse.fila(), 
					tkfalse.columna()));
		case LIT_REAL:
			UnidadLexica tkreal = anticipo;
			empareja(ClaseLexica.LIT_REAL);
			return sem.real(sem.str(tkreal.lexema(), tkreal.fila(), 
					tkreal.columna()));
		case LIT_ENT:
			UnidadLexica tkNum = anticipo;
			empareja(ClaseLexica.LIT_ENT);
			return sem.num(sem.str(tkNum.lexema(), tkNum.fila(), 
					tkNum.columna()));
		case ID:
			UnidadLexica tkid = anticipo;
			empareja(ClaseLexica.ID);
			return sem.id(sem.str(tkid.lexema(), tkid.fila(), 
					tkid.columna()));
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.LIT_ENT,
					ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE);
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
   
   private char OPBN1() {
	     switch(anticipo.clase()) { 
	     	case AND:
				empareja(ClaseLexica.AND);
				return 'and';
			case OR:
				empareja(ClaseLexica.OR);
				return 'or';
	         case POR: empareja(ClaseLexica.POR); return '*';  
	         case DIV: empareja(ClaseLexica.DIV); return '/';  
	         default:    
	 			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.AND,
	 					ClaseLexica.OR);
	 			return '?';
	     }  
	   }
   
   private char OPBN2() {
	     switch(anticipo.clase()) {
	     	case MENOR:
				empareja(ClaseLexica.MENOR);
				return '<'; 
			case MAYOR:
				empareja(ClaseLexica.MAYOR);
				return '>'; 
			case MENOR_IGUAL:
				empareja(ClaseLexica.MENOR_IGUAL);
				return '<='; 
			case MAYOR_IGUAL:
				empareja(ClaseLexica.MAYOR_IGUAL);
				return '>='; 
			case DIF:
				empareja(ClaseLexica.DIF);
				return '!='; 
			case DIGUAL:
				empareja(ClaseLexica.DIGUAL);
				return '=='; 
	         default:    
	        	 errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MENOR,
	 					ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.DIF,
	 					ClaseLexica.DIGUAL);
	              return '?';
	     }  
	   }
   
   private char OPBN3() {
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
