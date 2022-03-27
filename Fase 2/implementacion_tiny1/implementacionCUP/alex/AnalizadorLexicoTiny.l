package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoTiny
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores){
  	this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

and = and
or = or
not = not
false = false
true = true
int = int
real = real
string = string
bool = bool
null = null
proc = proc
if = if
then = then
else = else
endif = endif
while = while
do = do
endwhile = endwhile
call = call
record = record
array = array
of = of
pointer = pointer
new = new
delete = delete
read = read
write = write
nl = nl
var = var
type = type
letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = [0-9]
signo = [\+,\-]
parteEntera = ({digitoPositivo} {digito}* | 0)
parteDecimal = \.({digito}* {digitoPositivo} | 0)
litEnt = {signo}?{parteEntera}
parteExponencial = [e, E] {litEnt}
separador = [ \t\r\b\n]
comentario = #[^\n]* 
litReal = {litEnt} ({parteDecimal} | {parteExponencial} | {parteDecimal} {parteExponencial})
identificador = {letra}({letra}|{digito}|\_)*
flecha = \-\>
comillas = \"
litCad = {comillas}([^\"\r\b\n])*{comillas}
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
parentesisApertura = \(
parentesisCierre = \)
corchetesApertura = \[
corchetesCierre = \]
llavesApertura = \{
llavesCierre = \}
igual = \=
porcent = \%
coma  = \,
punto = \.
menor = \<
mayor = \>
menorIgual = {menor} {igual}
mayorIgual = {mayor} {igual}
exclamacion = \!
digual = {igual} {igual}
diferente = {exclamacion} {igual}
ampersand = \&
dobleAmpersand = {ampersand} {ampersand}
puntoComa = \;
%%
{separador}					{}
{comentario}            	{}
{false}                 	{return ops.unidadFalse();}
{true}                  	{return ops.unidadTrue();}
{and}                   	{return ops.unidadAnd();}
{or}                      	{return ops.unidadOr();}
{not}                     	{return ops.unidadNot();}
{int}                     	{return ops.unidadInt();}
{real}                     	{return ops.unidadReal();}
{string}                    {return ops.unidadString();}
{bool}						{return ops.unidadBool();}
{null}						{return ops.unidadNull();}
{proc}						{return ops.unidadProc();}
{if}						{return ops.unidadIf();}
{then}						{return ops.unidadThen();}
{else}						{return ops.unidadElse();}
{endif}						{return ops.unidadEndif();}
{while}						{return ops.unidadWhile();}
{do}						{return ops.unidadDo();}
{endwhile}					{return ops.unidadEndwhile();}
{call}						{return ops.unidadCall();}
{record}					{return ops.unidadRecord();}
{array}						{return ops.unidadArray();}
{of}						{return ops.unidadOf();}
{pointer}					{return ops.unidadPointer();}
{new}						{return ops.unidadNew();}
{delete}					{return ops.unidadDelete();}
{read}						{return ops.unidadRead();}
{write}						{return ops.unidadWrite();}
{nl}						{return ops.unidadNl();}
{var}						{return ops.unidadVar();}
{type}						{return ops.unidadType();}
{identificador}           	{return ops.unidadId();} 
{flecha}   		        	{return ops.unidadFlecha();} 
{litEnt}        	    	{return ops.unidadLitEnt();}
{litReal}     	         	{return ops.unidadLitReal();}
{litCad}	           	  	{return ops.unidadLitCad();}
{operadorSuma}            	{return ops.unidadSuma();}
{operadorResta}           	{return ops.unidadResta();}
{operadorMultiplicacion}  	{return ops.unidadMul();}
{operadorDivision}        	{return ops.unidadDiv();}
{parentesisApertura}      	{return ops.unidadPAp();}
{parentesisCierre}        	{return ops.unidadPCierre();} 
{corchetesApertura}			{return ops.unidadCap();}
{corchetesCierre}			{return ops.unidadCcierre();}
{llavesApertura}			{return ops.unidadLlap();}
{llavesCierre}				{return ops.unidadLlcierre();}
{igual}                   	{return ops.unidadIgual();} 
{digual}                   	{return ops.unidadDigual();} 
{coma}                    	{return ops.unidadComa();}
{porcent}                   {return ops.unidadPercent();}
{punto}						{return ops.unidadPunto();}
{puntoComa}				    {return ops.unidadPuntoComa();}
{menor}                   	{return ops.unidadMenor();}
{mayor}                   	{return ops.unidadMayor();}
{mayorIgual}              	{return ops.unidadMayorIgual();}
{menorIgual}              	{return ops.unidadMenorIgual();}
{diferente}               	{return ops.unidadDif();}
{ampersand}				  	{return ops.unidadAmp();}
{dobleAmpersand}		  	{return ops.unidadDAmp();}
[^]                         {errores.errorLexico(fila(), lexema());}  