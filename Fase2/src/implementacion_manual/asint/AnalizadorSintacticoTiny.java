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
   public void Sp() {
      S();
      empareja(ClaseLexica.EOF);
   }
   private void S() {
     switch(anticipo.clase()) {
         case EVALUA:          
              empareja(ClaseLexica.EVALUA);
              E0();
              Ds();
              break;
         default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                          ClaseLexica.EVALUA);                                            
   }
   }
   private void Ds() {
      switch(anticipo.clase()) {
          case DONDE:
              empareja(ClaseLexica.DONDE);
              LDs();
              break;
          case EOF: break;
          default: errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                          ClaseLexica.DONDE,ClaseLexica.EOF);                                       
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
   private void E0() {
     switch(anticipo.clase()) {
         case IDEN: case ENT: case REAL: case PAP:
             E1();
             RE0();
             break;
         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                           ClaseLexica.IDEN,ClaseLexica.ENT,
                                           ClaseLexica.REAL, ClaseLexica.PAP);                                    
     }  
   }
   private void RE0() {
      switch(anticipo.clase()) {
          case MAS: case MENOS: 
             Op0();
             E1();
             RE0();
             break;
          case DONDE: case PCIERRE: case EOF: case COMA: break;
          default:    
              errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                      ClaseLexica.MAS,ClaseLexica.MENOS);                                              
      } 
   }
   private void E1() {
     switch(anticipo.clase()) {
         case IDEN: case ENT: case REAL: case PAP:
             E2();
             RE1();
             break;
         default:  errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                                           ClaseLexica.IDEN,ClaseLexica.ENT,
                                           ClaseLexica.REAL, ClaseLexica.PAP);                                    
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
