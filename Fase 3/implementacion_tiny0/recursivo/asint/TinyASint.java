package asint;


public class TinyASint {
    public enum TNodo {SUMA,RESTA,MUL,DIV,NUM,ID,DECS_MUCHAS, DECS_UNA, 
                       DEC,PROG_SIN_DECS, PROG_CON_DECS} ;
    
    public static abstract class Exp  {
       public Exp() {
       }   
       public abstract TNodo tipo();
       public Exp arg0() {throw new UnsupportedOperationException("arg0");}
       public Exp arg1() {throw new UnsupportedOperationException("arg1");}
       public StringLocalizado id() {throw new UnsupportedOperationException("id");}
       public StringLocalizado num() {throw new UnsupportedOperationException("num");}
    }
    
    public static class StringLocalizado {
     private String s;
     private int fila;
     private int col;
     public StringLocalizado(String s, int fila, int col) {
         this.s = s;
         this.fila = fila;
         this.col = col;
     }
     public int fila() {return fila;}
     public int col() {return col;}
     public String toString() {
       return s;
     }
     public boolean equals(Object o) {
         return (o == this) || (
                (o instanceof StringLocalizado) &&
                (((StringLocalizado)o).s.equals(s)));                
     }
     public int hashCode() {
         return s.hashCode();
     }
  }

    
    private static abstract class ExpBin extends Exp {
        private Exp arg0;
        private Exp arg1;
        public ExpBin(Exp arg0, Exp arg1) {
            super();
            this.arg0 = arg0;
            this.arg1 = arg1;
        }
        public Exp arg0() {return arg0;}
        public Exp arg1() {return arg1;}
    }
    
    public static class Suma extends ExpBin {
        public Suma(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public TNodo tipo() {return TNodo.SUMA;}
    }
    public static class Resta extends ExpBin {
        public Resta(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public TNodo tipo() {return TNodo.RESTA;}
    }
    public static class Mul extends ExpBin {
        public Mul(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public TNodo tipo() {return TNodo.MUL;}
    }
    public static class Div extends ExpBin {
        public Div(Exp arg0, Exp arg1) {
            super(arg0,arg1);
        }
        public TNodo tipo() {return TNodo.DIV;}
    }
    public static class Num extends Exp {
        private StringLocalizado num;
        public Num(StringLocalizado num) {
            super();
            this.num = num;
        }
        public TNodo tipo() {return TNodo.NUM;}
        public StringLocalizado num() {
            return num;
        }
    }
    public static class Id extends Exp {
        private StringLocalizado id;
        public Id(StringLocalizado id) {
            super();
            this.id = id;
        }
        public TNodo tipo() {return TNodo.ID;}
        public StringLocalizado id() {
            return id;
        }
    }
    public static class Dec  {
        private StringLocalizado id;
        private StringLocalizado val;
        public Dec(StringLocalizado id, StringLocalizado val) {
            this.id = id;
            this.val = val;
        }
        public TNodo tipo() {return TNodo.DEC;}
        public StringLocalizado id() {return id;}
        public StringLocalizado val() {return val;}
    }
    public static abstract class Decs {
       public Decs() {
       }   
       public abstract TNodo tipo(); 
       public Decs decs() {throw new UnsupportedOperationException("decs");}
       public Dec dec() {throw new UnsupportedOperationException("dec");}
    }
    public static class Decs_una extends Decs {
       private Dec dec; 
       public Decs_una(Dec dec) {
          super();
          this.dec = dec;
       }   
       public TNodo tipo() {return TNodo.DECS_UNA;}; 
       public Dec dec() {
           return dec;
       }
    }
    public static class Decs_muchas extends Decs {
       private Dec dec;
       private Decs decs;
       public Decs_muchas(Decs decs, Dec dec) {
          super();
          this.dec = dec;
          this.decs = decs;
       }
       public TNodo tipo() {return TNodo.DECS_MUCHAS;}; 
       public Dec dec() {
           return dec;
       }
       public Decs decs() {
           return decs;
       }
    }
    public static abstract class Prog  {
       public Prog() {
       }   
       public abstract TNodo tipo();  
       public  Exp exp() {throw new UnsupportedOperationException("exp");};
       public Decs decs() {throw new UnsupportedOperationException("decs");};;
    }
    public static class Prog_sin_decs extends Prog {
      private Exp exp;
       public Prog_sin_decs(Exp exp) {
          super();
          this.exp = exp;
       }   
       public TNodo tipo() {return TNodo.PROG_SIN_DECS;}; 
       public Exp exp() {return exp;}
    }
    public static class Prog_con_decs extends Prog {
      private Exp exp;
      private Decs decs;
       public Prog_con_decs(Exp exp, Decs decs) {
          super();
          this.exp = exp;
          this.decs = decs;
       }   
       public TNodo tipo() {return TNodo.PROG_CON_DECS;}; 
       public Exp exp() {return exp;}
       public Decs decs() {return decs;}
    }

     // Constructoras    
    public Prog prog_con_decs(Exp exp, Decs decs) {
        return new Prog_con_decs(exp,decs);
    }
    public Prog prog_sin_decs(Exp exp) {
        return new Prog_sin_decs(exp);
    }
    public Exp suma(Exp arg0, Exp arg1) {
        return new Suma(arg0,arg1);
    }
    public Exp resta(Exp arg0, Exp arg1) {
        return new Resta(arg0,arg1);
    }
    public Exp mul(Exp arg0, Exp arg1) {
        return new Mul(arg0,arg1);
    }
    public Exp div(Exp arg0, Exp arg1) {
        return new Div(arg0,arg1);
    }
    public Exp num(StringLocalizado num) {
        return new Num(num);
    }
    public Exp id(StringLocalizado num) {
        return new Id(num);
    }
    public Dec dec(StringLocalizado id, StringLocalizado val) {
        return new Dec(id,val);
    }
    public Decs decs_una(Dec dec) {
        return new Decs_una(dec);
    }
    public Decs decs_muchas(Decs decs, Dec dec) {
        return new Decs_muchas(decs,dec);
    }
    public StringLocalizado str(String s, int fila, int col) {
        return new StringLocalizado(s,fila,col);
    }
}
