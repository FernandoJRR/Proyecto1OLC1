package com.universidad.servidorproyecto1.analisis;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
%%

%class LexerJava
%public
%type java_cup.runtime.Symbol
%cup
%unicode
%line
%column
%state STRING
%state COMENTARIO_LINEA
%state COMENTARIO_MULTILINEA
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ \t]+
salto=[\n\r]+

%{
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
%}

%eofval{
      return new Symbol(sym.EOF, yycolumn+1, yyline+1, null);
%eofval}

%%

<YYINITIAL> {
    //Se declaran las palabras reservadas
    //Se declaran simbolos
    //Se declaran tokens con regex
    "\"" {yybegin(STRING);}
/*
    "-"?{D}+ {return new Symbol(sym.Entero, yycolumn+1, yyline+1, yytext());}
    "-"?{D}+"."{D}+ {return new Symbol(sym.Decimal, yycolumn+1, yyline+1, yytext());}
    {L}.({L}|{D})* {System.out.println("Identifier at line:"+(yyline+1)+", column:"+(yycolumn+1)+" "+yytext());}
*/
    //Se ignoraran espacios sueltos
    {espacio}+ {/*Ignorar*/}

    "//" {yybegin(COMENTARIO_LINEA);}
    "/*" {yybegin(COMENTARIO_MULTILINEA);}
    ({L}|{D})+ {}
    [^] {System.out.println("Non defined character at line:"+(yyline+1)+", column:"+(yycolumn+1)+" "+buffer.toString()); buffer.delete(0, buffer.length());}
}

<STRING> {
    ({L}|{D}|{espacio}) {buffer.append(yytext());}
    "\"" {System.out.println("String at line:"+(yyline+1)+", column:"+(yycolumn+1)+" "+buffer.toString()); buffer.delete(0, buffer.length()); yybegin(YYINITIAL);}
    {salto}|[^] {System.out.println("Incomplete String at line:"+(yyline+1)+", column:"+(yycolumn+1)+" "+buffer.toString()); buffer.delete(0, buffer.length()); yybegin(YYINITIAL);}
}

<COMENTARIO_LINEA> {
    {salto} {String lexema = buffer.toString();resetBuffer();yybegin(YYINITIAL);return symbol(sym.COMENTARIO_LINEA, lexema);}
    [^] {buffer.append(yytext());}
}

<COMENTARIO_MULTILINEA> {
    "*/" {yybegin(YYINITIAL);}
    [^] {/*Ignorar*/}
}