/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny;
import alex.ClaseLexica;
import errors.GestionErroresTiny;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoTiny {
   private UnidadLexica anticipo;
   private AnalizadorLexicoTiny alex;
   private GestionErroresTiny errores;
   public AnalizadorSintacticoTiny(Reader input) {
      errores = new GestionErroresTiny();
      alex = new AnalizadorLexicoTiny(input);
      alex.fijaGestionErrores(errores);
      sigToken();
   }
   public void PROGRAMAp() {
	   PROGRAMA();
      empareja(ClaseLexica.EOF);
   }
   private void PROGRAMA () {   
      DECLARACIONES();
      DAMP();
      INSTRUCCIONES();                                            
   }
   
   private void DAMP() {
	      switch(anticipo.clase()) {
	          case DAMP:
	              empareja(ClaseLexica.DAMP);
	              break;
	          case EOF: break;
	          default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                          ClaseLexica.DAMP,ClaseLexica.EOF);                                       
	      } 
	   }
   
   private void DECLARACIONES() {
	   DECLARACION();
	   RDEC();
   }
   
   private void INSTRUCCIONES() {
	   INSTRUCCION();
	   RINS();
   }
   
   private void DECLARACION() {
	   switch(anticipo.clase()) {
	      case BOOL:  
	      case INT:  
	      case REAL:    
	          empareja(ClaseLexica.TIPO);
	          empareja(ClaseLexica.ID);
	          break;
	      case EOF: break;    
	      default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                        ClaseLexica.TIPO, ClaseLexica.EOF);                                       
	     }
   }
   
   private void INSTRUCCION() {
	   switch(anticipo.clase()) {
	      case ID:    
	          empareja(ClaseLexica.ID);
	          empareja(ClaseLexica.IGUAL);
	          E0();
	          break;
	      case EOF: break;    
	      default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                        ClaseLexica.ID, ClaseLexica.EOF);                                       
	     }
   }
   
   private void RDEC() {
     switch(anticipo.clase()) {
      case PCOMA:    
          empareja(ClaseLexica.PCOMA);
          DECLARACION();
          RDEC();
          break;
      case EOF: break;    
      default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                        ClaseLexica.PCOMA, ClaseLexica.EOF);                                       
     }          
  } 
   
   
   private void RINS() {
	     switch(anticipo.clase()) {
	      case PCOMA:    
	          empareja(ClaseLexica.PCOMA);
	          INSTRUCCION();
	          RINS();
	          break;
	      case EOF: break;    
	      default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                        ClaseLexica.PCOMA, ClaseLexica.EOF);                                       
	     }          
	  }
   
   
   
   private void E0() {
	     switch(anticipo.clase()) {
	         case IGUAL: case NUM_ENT: case NUM_REAL: case PAP:
	         case NOT: case TRUE: case FALSE: case MENOS:
	             E1();
	             RE0();
	             break;
	         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                           ClaseLexica.IGUAL,ClaseLexica.NUM_ENT,
	                                           ClaseLexica.NUM_REAL, ClaseLexica.PAP,
	                                           ClaseLexica.NOT,ClaseLexica.TRUE,
	                                           ClaseLexica.FALSE, ClaseLexica.MENOS);                                    
	     }  
	   }
   
   private void E1() {
	     switch(anticipo.clase()) {
	         case IGUAL: case NUM_ENT: case NUM_REAL: case PAP:
	         case NOT: case TRUE: case FALSE: case MENOS:
	             E2();
	             RE1();
	             break;
	         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                           ClaseLexica.IGUAL,ClaseLexica.NUM_ENT,
	                                           ClaseLexica.NUM_REAL, ClaseLexica.PAP,
	                                           ClaseLexica.NOT,ClaseLexica.TRUE,
	                                           ClaseLexica.FALSE, ClaseLexica.MENOS);                                    
	     }  
	   }
   
   private void E2() {
	     switch(anticipo.clase()) {
	         case IGUAL: case NUM_ENT: case NUM_REAL: case PAP:
	         case NOT: case TRUE: case FALSE: case MENOS:
	             E3();
	             RE2();
	             break;
	         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                           ClaseLexica.IGUAL,ClaseLexica.NUM_ENT,
	                                           ClaseLexica.NUM_REAL, ClaseLexica.PAP,
	                                           ClaseLexica.NOT,ClaseLexica.TRUE,
	                                           ClaseLexica.FALSE, ClaseLexica.MENOS);                                    
	     }  
	   }
   
   private void E3() {
	     switch(anticipo.clase()) {
	         case IGUAL: case NUM_ENT: case NUM_REAL: case PAP:
	         case NOT: case TRUE: case FALSE: case MENOS:
	             E4();
	             RE3();
	             break;
	         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                           ClaseLexica.IGUAL,ClaseLexica.NUM_ENT,
	                                           ClaseLexica.NUM_REAL, ClaseLexica.PAP,
	                                           ClaseLexica.NOT,ClaseLexica.TRUE,
	                                           ClaseLexica.FALSE, ClaseLexica.MENOS);                                    
	     }  
	   }
   
   private void E4() {
	     switch(anticipo.clase()) {
	         case IGUAL: case NUM_ENT: case NUM_REAL: case PAP:
	         case TRUE: case FALSE:
	             E5();
	             break;
	         case MENOS:
		         empareja(ClaseLexica.MENOS);
	             E5();
	             break;
	         case NOT:
		         empareja(ClaseLexica.NOT);
	             E5();
	             break;
	         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                           ClaseLexica.IGUAL,ClaseLexica.NUM_ENT,
	                                           ClaseLexica.NUM_REAL, ClaseLexica.PAP,
	                                           ClaseLexica.NOT,ClaseLexica.TRUE,
	                                           ClaseLexica.FALSE, ClaseLexica.MENOS);                                    
	     }  
	   }
   
   private void E5() {
		     switch(anticipo.clase()) {
		         case NUM_ENT: case NUM_REAL:
		         case TRUE: case FALSE:
		             EXPRESION();
		             break;

		         case PAP:
			         empareja(ClaseLexica.PAP);
		             E0();
			         empareja(ClaseLexica.PCIERRE);
		             break;    
		         
		         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
		                                           ClaseLexica.NUM_ENT, ClaseLexica.NUM_REAL, 
		                                           ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.PAP);                                    
		     }  
		   }
   
   //////////////////////////////////////////////////////////////

   
   
	   private void RE0() {
	      switch(anticipo.clase()) {
	          case MAS: 
		         empareja(ClaseLexica.MAS);
	             E1();
	             RE0();
	             break;
	          case MENOS:
			      empareja(ClaseLexica.MENOS);
			      E1();
	        	  break;
	          case ID: case PCIERRE: case EOF: break;
	          default:    
	              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                      ClaseLexica.MAS,ClaseLexica.MENOS);                                              
	      } 
	   }
	   
	   private void RE1() {
	      switch(anticipo.clase()) {
	          case POR: case DIV: 
	             Op1();
	             E2();
	             RE1();
	             break;
	          case DONDE: case PCIERRE: case EOF: case MAS: case MENOS: case COMA: break;
	          default:    
	              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                      ClaseLexica.POR,ClaseLexica.DIV,
	                                      ClaseLexica.MAS, ClaseLexica.MENOS);                                              
	      } 
	   }
	   private void E2() {
	      switch(anticipo.clase()) {
	          case ENT: empareja(ClaseLexica.ENT); break;
	          case REAL: empareja(ClaseLexica.REAL); break; 
	          case IDEN: empareja(ClaseLexica.IDEN); break;
	          case PAP: 
	               empareja(ClaseLexica.PAP); 
	               E0(); 
	               empareja(ClaseLexica.PCIERRE); 
	               break;
	          default:     
	              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                      ClaseLexica.ENT,ClaseLexica.REAL,
	                                      ClaseLexica.PAP);
	   }   
	   }
	   private void Op0() {
	     switch(anticipo.clase()) {
	         case MAS: empareja(ClaseLexica.MAS); break;  
	         case MENOS: empareja(ClaseLexica.MENOS); break;  
	         default:    
	              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                      ClaseLexica.MAS,ClaseLexica.MENOS);
	     }  
	   }
	   private void Op1() {
	     switch(anticipo.clase()) {
	         case POR: empareja(ClaseLexica.POR); break;  
	         case DIV: empareja(ClaseLexica.DIV); break;  
	         default:    
	              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
	                                      ClaseLexica.POR,ClaseLexica.DIV);
	     }  
	   }
   
   
   
   private void LDs() {
      switch(anticipo.clase()) {
       case IDEN:    
           D();
           RLDs();
           break;
       default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                         ClaseLexica.IDEN);                                       
   }
   }    
   private void RLDs() {
      switch(anticipo.clase()) {
       case COMA:    
           empareja(ClaseLexica.COMA);
           D();
           RLDs();
           break;
       case EOF: break;    
       default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                         ClaseLexica.COMA, ClaseLexica.EOF);                                       
      }          
   }   
   private void D() {
     switch(anticipo.clase()) {       
       case IDEN:   
        empareja(ClaseLexica.IDEN);
        empareja(ClaseLexica.IGUAL);
        E0();
        break;
       default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                         ClaseLexica.IDEN);                                       
     }
   }
   
   
   private void empareja(ClaseLexica claseEsperada) {
      if (anticipo.clase() == claseEsperada)
          sigToken();
      else errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),claseEsperada);
   }
   private void sigToken() {
      try {
        anticipo = alex.yylex();
      }
      catch(IOException e) {
        errores.errorFatal(e);
      }
   }
   
}
