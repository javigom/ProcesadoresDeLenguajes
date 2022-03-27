import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import implementacionCUP.alex.AnalizadorLexicoTiny;
import implementacionCUP.asint.AnalizadorSintacticoTiny;
import implementacionJavaCC.asint.AnalizadorSintacticoTinyCC;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
     
     if(args[1].equals("asc")) {
    	 AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
    	 AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);
    	//asint.setScanner(alex);
    	 asint.parse();
    	 System.out.println("OK (CUP)");
     }
     
     else if (args[1].equals("desc")){
    	AnalizadorSintacticoTinyCC asint = new AnalizadorSintacticoTinyCC(input);
 		asint.PROGRAMAp();
 		 System.out.println("OK (JavaCC)");
     }
     
     else {
    	 System.err.println("Parameter error. Usage: <FILE> <OP>");
     }
     
 }
}   
   
