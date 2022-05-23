package maquinaP;

public class GestorMemoriaDinamica {
    private final static boolean DEBUG=false;
    private static class Hueco {
      private int comienzo;
      private int tamanio;
      private Hueco siguiente;
      public Hueco(int comienzo, int tamanio) {
         this.comienzo = comienzo;
         this.tamanio = tamanio;
         siguiente = null;
      }
    }
    private Hueco huecos;
    public GestorMemoriaDinamica(int comienzo, int fin) {
       huecos = new Hueco(comienzo,(fin-comienzo)+1);
       if (DEBUG) {
           System.out.print("INICIO:");
           muestraHuecos();
           System.out.println("----");
       }
    }
    public int alloc(int tamanio) {
      Hueco h = huecos;
      Hueco prev = null;
      while (h != null && h.tamanio < tamanio) {
          prev = h;
          h = h.siguiente;
      }    
      if (h==null) throw new OutOfMemoryError("alloc "+tamanio);
      int dir = h.comienzo;
      h.comienzo += tamanio;
      h.tamanio -= tamanio;
      if (h.tamanio == 0) {
         if (prev == null) huecos = h.siguiente;
         else prev.siguiente = h.siguiente;
      }
      if (DEBUG) {
          System.out.println("alloc("+tamanio+")="+dir);
          muestraHuecos();
          System.out.println("----");
    }
      return dir;
      }
    public void free(int dir, int tamanio) {
     Hueco h = huecos;
     Hueco prev = null;
     while (h != null && h.comienzo < dir) {
        prev = h;
        h = h.siguiente;
     }
     if (prev != null && prev.comienzo + prev.tamanio == dir) {
         prev.tamanio += tamanio;
         if (h != null && prev.comienzo + prev.tamanio == h.comienzo) {
             prev.tamanio += h.tamanio;
             prev.siguiente = h.siguiente;
         }
     }
     else if (h != null && dir + tamanio == h.comienzo) {
         h.comienzo = dir;
         h.tamanio += tamanio;
     }
     else {
        Hueco nuevo = new Hueco(dir,tamanio);
        nuevo.siguiente = h;
        if (prev==null) {
           huecos = nuevo;
        }
        else {
            prev.siguiente = nuevo;
        }
     }  
     if (DEBUG) {
         System.out.println("free("+dir+","+tamanio+")");  
         muestraHuecos();
         System.out.println("----");
     }
  }
  public void muestraHuecos() {
     Hueco h = huecos;
     while (h != null) {
       System.out.print("<"+h.comienzo+","+h.tamanio+","+(h.comienzo+h.tamanio-1)+">");
       h = h.siguiente;
     }
     System.out.println();
  }  
  
  public static void main(String[] args) {
      GestorMemoriaDinamica g = new GestorMemoriaDinamica(0,100);
      g.muestraHuecos();
      int a = g.alloc(1);
      g.muestraHuecos();
      int b = g.alloc(1);
      g.muestraHuecos();
      int c = g.alloc(1);
      g.muestraHuecos();
      int d = g.alloc(1);
      g.muestraHuecos();
      g.free(c, 1);
      g.muestraHuecos();
      c= g.alloc(1);
      g.muestraHuecos();
      g.free(b, 1);
      g.muestraHuecos();
      g.free(d, 1);
      g.muestraHuecos();
      g.free(a, 1);
      g.muestraHuecos();
      int e = g.alloc(10);
      g.muestraHuecos();
      g.free(c, 1);
      g.muestraHuecos();
      g.free(e, 10);
      g.muestraHuecos();
      int w = g.alloc(100);
      g.muestraHuecos();
      int z = g.alloc(1);
      g.muestraHuecos();
      g.free(z, 1);
      g.muestraHuecos();
      g.free(w,100);
      g.muestraHuecos();
  }
}    
         
             
      
     
     
     
     
     