package semops;

import asint.TinyASint;

public class SemOps extends TinyASint {
	public Exp exp(char op, Exp arg0, Exp arg1) {
		switch (op) {
		case '+':
			return suma(arg0, arg1);
		case '-':
			return resta(arg0, arg1);
		case '*':
			return mul(arg0, arg1);
		case '%':
			return percent(arg0, arg1);
		case '/':
			return div(arg0, arg1);
		case '<':
			return mayor(arg0, arg1);
		case '>':
			return menor(arg0, arg1);
		}
		throw new UnsupportedOperationException("exp " + op);
	}

	public Exp exp(String op, Exp arg0, Exp arg1) {
		switch (op) {
		case "and":
			return and_cons(arg0, arg1);
		case "or":
			return or_cons(arg0, arg1);
		case "<=":
			return menorIgual(arg0, arg1);
		case ">=":
			return mayorIgual(arg0, arg1);
		case "==":
			return igual(arg0, arg1);
		case "!=":
			return distinto(arg0, arg1);
		}
		throw new UnsupportedOperationException("exp " + op);
	}

	public Exp exp(String op, Exp arg0) {
		switch (op) {
		case "-":
			return menosUnario(arg0);
		case "not":
			return not(arg0);
		}
		throw new UnsupportedOperationException("exp " + op);
	}
	
	public Exp exp(Exp arg0, Exp arg1) {
		return expN5(arg0, arg1);
	}

	public Programa prog(Instrucciones ins, Declaraciones decs) {
		return P_decs(decs, ins);
	}
	

	public Programa prog_nodecs(Instrucciones ins) {
		return P_nodecs(ins);
	}

}
