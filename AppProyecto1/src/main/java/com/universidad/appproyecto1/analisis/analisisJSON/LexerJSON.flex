package com.universidad.appproyecto1.analisis.analisisJSON;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.HashMap;
import com.universidad.appproyecto1.analisis.CompileError;
%%

%class LexerJSON
%public
%type java_cup.runtime.Symbol
%cup
%unicode
%line
%column
%state STRING
L=[a-zA-Z]
D=[0-9]
espacio=[ \t]
salto=[\n\r]
%{
    private ArrayList<CompileError> errores;

    public void setErrores(ArrayList<CompileError> errores){
        this.errores = errores;
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    StringBuffer buffer = new StringBuffer();
    
    private void resetBuffer(){
        buffer.delete(0, buffer.length());
    }

    private ArrayList<CompileError> erroresEncontrados; 

    public ArrayList<CompileError> getErroresEncontrados(){
            return erroresEncontrados;
    }
    public void setErroresEncontrados(ArrayList<CompileError> erroresEncontrados){
    this.erroresEncontrados = erroresEncontrados;
    }

    private void agregarError(Symbol symbolError, String mensajeError){
            erroresEncontrados.add(new CompileError(symbolError, "Lexico", mensajeError));
    }
%}
%eofval{
    return symbol(sym.EOF, null);
%eofval}


integer = -?(0|[1-9][0-9]*)
decimal = ["."][0-9]+
notacion_cientifica = (e|E)("+"|-)[0-9]+
numeros = {integer}{decimal}?{notacion_cientifica}?

%%

<YYINITIAL> {

    "," { return symbol(sym.COMA, yytext()); }
    "[" { return symbol(sym.L_CORCHETE, yytext()); }
    "]" { return symbol(sym.R_CORCHETE, yytext()); }
    "{" { return symbol(sym.L_LLAVE, yytext()); }
    "}" { return symbol(sym.R_LLAVE, yytext()); }
    ":" { return symbol(sym.DOS_PUNTOS, yytext()); }


    "true" { return symbol(sym.TRUE, yytext()); }
    "false" { return symbol(sym.FALSE, yytext()); }
    "null" { return symbol(sym.NULL, yytext()); }

    {numeros} { return symbol(sym.NUMERO, yytext()); }
    "\"" {yybegin(STRING);}

    //Se ignoraran espacios sueltos
    {espacio}|{salto} {/*Ignorar*/}
    [^] { System.out.println("Caracter no definido en:"+(yyline+1)+", column:"+(yycolumn+1)+" "+yytext()); agregarError(symbol(sym.EOF, yytext()), "El caracter ingresado ha esta definido"); resetBuffer(); }

}


<STRING> {  
    "\"" {String lexema = buffer.toString();yybegin(YYINITIAL);resetBuffer();return symbol(sym.STRING,lexema);}
    {salto}+ {System.out.println("Error: Cadena incompleta");yybegin(YYINITIAL);resetBuffer();}
    [^] {buffer.append(yytext());}
}