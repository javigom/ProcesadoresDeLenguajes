package implementacion_jflex;

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
caracter = [^[\"] | [\b]]
signo = [\+,\-]
parteEntera = ({digitoPositivo}{digito}*|0)
parteDecimal = \.{digito}* {digitoPositivo}
parteExponencial = [e, E] {numeroEntero}
separador = [ \t\r\b\n]
comentario = #[^\n]* 
and = and
or = or
not = not
false = false
true = true
identificador = {letra}({letra}|{digito}|\_)*
comillas = \"
cadena = {comillas}({caracter})*{comillas}
numeroEntero = {signo}?{parteEntera}
numeroReal = {numeroEntero} ({parteDecimal} | {parteExponencial} | {parteDecimal} {parteExponencial})
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
coma  = \,
punto  = \.
menor = \<
mayor = \>
menorIgual = {menor} {igual}
mayorIgual = {mayor} {igual}
exclamacion = \!
diferente = {exclamacion} {igual}
ampersand = \&
dobleAmpersand = {ampersand} {ampersand}
sepPc = \;
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


%%
{cadena}	           	  	{return ops.unidadLitCad();}
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
{call}						{return ops.unidadBool();}
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
{numeroEntero}            	{return ops.unidadLitEnt();}
{numeroReal}              	{return ops.unidadLitReal();}
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
{coma}                    	{return ops.unidadComa();}
{punto}                    	{return ops.unidadPunto();}
{menor}                   	{return ops.unidadMenor();}
{mayor}                   	{return ops.unidadMayor();}
{mayorIgual}              	{return ops.unidadMayorIgual();}
{menorIgual}              	{return ops.unidadMenorIgual();}
{diferente}               	{return ops.unidadDif();}
{ampersand}				  	{return ops.unidadAmp();}
{dobleAmpersand}		  	{return ops.unidadDAmp();}
{sepPc}				      	{return ops.unidadSepPc();}
{separador}					{}
{comentario}            	{}

[^]                       {ops.error();}  