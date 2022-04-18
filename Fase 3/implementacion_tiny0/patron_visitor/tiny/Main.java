package tiny;

import c_ast_descendente_manual.AnalizadorLexicoTiny;
import c_ast_descendente_manual.ConstructorAST;
import asint.TinyASint.Prog;
import c_ast_descendente_manual.ClaseLexica;
import errors.GestionErroresTiny;
import c_ast_descendente_manual.UnidadLexica;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import procesamientos.Evaluacion;
import procesamientos.Impresion;

public class Main {
   public static void main(String[] args) throws Exception {
     if (args[0].equals("-lex")) {  
         ejecuta_lexico(args[1]);
     }
     else {
         Prog prog = null;
         prog = ejecuta_descendente_manual(args[1]);
         prog.procesa(new Impresion());
         prog.procesa(new Evaluacion());         
     }
   }
   
   private static void ejecuta_lexico(String in) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(in));
     AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
     GestionErroresTiny errores = new GestionErroresTiny();
     UnidadLexica t = (UnidadLexica) alex.sigToken();
     while (t.clase() != ClaseLexica.EOF) {
         System.out.println(t);
         t = (UnidadLexica) alex.sigToken();   
     }
   }
   
   private static Prog ejecuta_descendente_manual(String in) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(in));
     c_ast_descendente_manual.ConstructorAST constructorast = new c_ast_descendente_manual.ConstructorAST(input);
     return constructorast.Init();
   }
}   
   
