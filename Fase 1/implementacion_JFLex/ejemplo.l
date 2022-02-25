package alex;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode

%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
caracter = ^[\"\r\b\n ]
signo = [\+,\-]
parteEntera = {digitoPositivo}{digito}*
parteDecimal = \.{digito}* {digitoPositivo}
parteExponencial = [e, E] {signo}? {numeroEntero}
separador = [ \t\r\b\n]
and = and
or = or
not = not
false = false
true = true
identificador = {letra}({letra}|{digito}|{\_})*
cadena = {\"} ({caracter})* {\"}
numeroEntero = {signo}?{parteEntera}
numeroReal = {signo}?{parteEntera} ({parteDecimal} | {parteExponencial} | {parteDecimal} {parteExponencial})
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
parentesisApertura = \(
parentesisCierre = \)
igual = \=
coma  = \,
menor = \<
mayor = \>
menorIgual = {menor} {igual}
mayorIgual = {mayor} {igual}
exclamacion = \!
diferente = {exclamacion} {igual}
sep = \&
separador = {sep} {sep}
sepPc = \;

%%
{separador}               {}
{false}                   {return ops.unidadFalse();}
{true}                    {return ops.unidadTrue();}
{and}                     {return ops.unidadAnd();}
{or}                      {return ops.unidadOr();}
{not}                     {return ops.unidadNot();}
{identificador}           {return ops.unidadId();} 
{cadena}	           	  {return ops.unidadLitCad();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{igual}                   {return ops.unidadIgual();} 
{coma}                    {return ops.unidadComa();}
{menor}                   {return ops.unidadMenor();}
{mayor}                   {return ops.unidadMayor();}
{mayorIgual}              {return ops.unidadMayorIgual();}
{menorIgual}              {return ops.unidadMenorIgual();}
{diferente}               {return ops.unidadDif();}
{separador}				  {return ops.unidadSep();}
{sepPc}				      {return ops.unidadSep_Pc();}


[^]                       {ops.error();}  