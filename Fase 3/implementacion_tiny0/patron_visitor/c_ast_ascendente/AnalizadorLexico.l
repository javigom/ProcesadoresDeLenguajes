package c_ast_ascendente;

%%
%cup
%line
%column
%class AnalizadorLexico
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErrores errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int col()  {return yycolumn+1;}
  public void fijaGestionErrores(GestionErrores errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.token(ClaseLexica.EOF);
%eofval}

%init{
  ops = new ALexOperations(this);
%init}


letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b]
saltoLinea = \n
comentario = #[^\n]* 
evalua = evalua
donde = donde
identificador = {letra}({letra}|{digito})*
numero = [\+\-]?{parteEntera}(\.{parteDecimal})?
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
parentesisApertura = \(
parentesisCierre = \)
igual = \=
coma  = \,
%%
{separador}               {}
{saltoLinea}              {} 
{comentario}              {}
{evalua}                  {return ops.token(ClaseLexica.EVALUA);}
{donde}                   {return ops.token(ClaseLexica.DONDE);}
{identificador}           {return ops.token(ClaseLexica.ID);}
{numero}                  {return ops.token(ClaseLexica.NUM);}
{operadorSuma}            {return ops.token(ClaseLexica.MAS);}
{operadorResta}           {return ops.token(ClaseLexica.MENOS);}
{operadorMultiplicacion}  {return ops.token(ClaseLexica.POR);}
{operadorDivision}        {return ops.token(ClaseLexica.DIV);}
{parentesisApertura}      {return ops.token(ClaseLexica.PAP);}
{parentesisCierre}        {return ops.token(ClaseLexica.PCIERRE);} 
{igual}                   {return ops.token(ClaseLexica.IGUAL);} 
{coma}                    {return ops.token(ClaseLexica.COMA);}
[^]                       {errores.errorLexico(fila(),lexema());}  