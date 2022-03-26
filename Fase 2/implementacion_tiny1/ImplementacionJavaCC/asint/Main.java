package asint;

import java.io.FileReader;

public class Main {
	public static void main(String[] args) throws Exception {
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(new FileReader(args[0]));
		asint.PROGRAMAp();
	}
}