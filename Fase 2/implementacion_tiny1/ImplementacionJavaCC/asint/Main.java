package implementacionJavaCC.asint;

import java.io.FileReader;

public class Main {
	public static void main(String[] args) throws Exception {
		AnalizadorSintacticoTinyCC asint = new AnalizadorSintacticoTinyCC(new FileReader(args[0]));
		asint.PROGRAMAp();
	}
}