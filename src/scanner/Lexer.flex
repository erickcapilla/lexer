package scanner;
import static scanner.Tokens.*;
%%
%class Lexer
%type Tokens
L = [a-zA-Z_]+
D = [0-9]+
OPE = [> | <]
SIG = [* | / | + | -]
SIM = [= | ( | ) | ; | { | }]
espacio = [ ,\t,\r,\n]+
%{
    public String lexeme;
%}
%%
String |
int |
if |
else |
for |
while {lexeme=yytext(); return Reservada;}
{espacio} {/*Ignore*/}
"//".* {/*Ignore*/}
{OPE}|"<="|">="|"=="|"++"|"--"|"+="|
"-="|"*="|"/=" {lexeme = yytext(); return Operador;}
{SIG} {lexeme = yytext(); return Signo;}
{SIM} {lexeme = yytext(); return Simbolo;}
{L}({L}|{D})* {lexeme = yytext(); return Identificador;}
("(-"{D}+")")|{D}+ {lexeme = yytext(); return Numero;}
 . {return ERROR;}