package com.universidad.appproyecto1.analisis.analisisDef;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.HashMap;
import com.universidad.appproyecto1.analisis.CompileError;
%%

%class LexerDef
%public
%type java_cup.runtime.Symbol
%cup
%unicode
%caseless
%line
%column
%state STRING
%state TEXTO
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

%%

<YYINITIAL> {
    //Se declaran las palabras reservadas
    Integer {return symbol(sym.PR_INTEGER, yytext());}
    String {return symbol(sym.PR_STRING, yytext());}
    
    RESULT {return symbol(sym.RESULT, yytext());}
    
    html {return symbol(sym.TAG_HTML, yytext());}
    h1 {return symbol(sym.TAG_HEADER,yytext());}
    h2 {return symbol(sym.TAG_HEADER,yytext());}
    table {return symbol(sym.TAG_TABLE,yytext());}
    for {return symbol(sym.TAG_FOR,yytext());}
    iterador {return symbol(sym.ITERADOR,yytext());}
    hasta {return symbol(sym.HASTA,yytext());}
    tr {return symbol(sym.TAG_TR,yytext());}
    th {return symbol(sym.TAG_TH, yytext());}
    td {return symbol(sym.TAG_TD, yytext());}
    br {return symbol(sym.TAG_BR, yytext());}

    //Se declaran simbolos
    
    "." {return symbol(sym.PUNTO, yytext());}
    ":" {return symbol(sym.DOS_PUNTOS, yytext());}
    "," {return symbol(sym.COMA, yytext());}
    ";" {return symbol(sym.PUNTO_COMA, yytext());}
    "(" {return symbol(sym.PAR_IZQ, yytext());}
    ")" {return symbol(sym.PAR_DER, yytext());}
    "[" {return symbol(sym.CORCH_IZQ, yytext());}
    "]" {return symbol(sym.CORCH_DER, yytext());}
    
    "+" {return symbol(sym.CRUZ, yytext());}
    "*" {return symbol(sym.ASTERISCO, yytext());}
    "-" {return symbol(sym.GUION, yytext());}
    "/" {return symbol(sym.BARRA, yytext());}
    "=" {return symbol(sym.IGUAL, yytext());}
    

    ">" {return symbol(sym.MAYOR_QUE, yytext());}
    "<" {return symbol(sym.MENOR_QUE, yytext());}
    "</" {return symbol(sym.FIN_TAG, yytext());}
    "$" {return symbol(sym.DOLAR,yytext());}    
    "$$" {return symbol(sym.DOBLE_DOLAR,yytext());}    

    //Se declaran tokens con regex
    "</".*."/>" {/*Ignorar comentarios*/}

    "\"" {yybegin(STRING);}

    "-"?{D}+ {return symbol(sym.ENTERO, yytext());}
    
    ({L}|"_")({L}|{D}|"_")* {return symbol(sym.VARIABLE_IDENTIFICADOR, yytext());}

    
    //Se ignoraran espacios sueltos
    {espacio}|{salto} {/*Ignorar*/}
    [^] { yybegin(TEXTO); buffer.append(yytext());}
}


<STRING> {  
    "\"" {String lexema = buffer.toString();yybegin(YYINITIAL);resetBuffer();return symbol(sym.STRING,lexema);}
    {salto}+ {System.out.println("Error: Cadena incompleta");yybegin(YYINITIAL);resetBuffer();}
    [^] {buffer.append(yytext());}
}

<TEXTO> {
    {espacio}|{salto} {yybegin(YYINITIAL); String lexema = buffer.toString(); resetBuffer(); return symbol(sym.TEXTO, lexema);}
    [^] {buffer.append(yytext());}
}