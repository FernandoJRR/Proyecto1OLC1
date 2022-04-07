package com.universidad.appproyecto1.analisis.analisisCopy;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.HashMap;
import com.universidad.appproyecto1.analisis.CompileError;
%%

%class LexerCopy
%public
%type java_cup.runtime.Symbol
%cup
%unicode
%line
%column
%state STRING
%state COMENTARIO_LINEA
%state COMENTARIO_MULTILINEA
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
%}

%eofval{
      return new Symbol(sym.EOF, yycolumn+1, yyline+1, null);
%eofval}

%%

<YYINITIAL> {
    //Se declaran las palabras reservadas
    nombre-proyecto {return symbol(sym.NOMBRE_PROYECTO, yytext());}
    directorio-json {return symbol(sym.DIRECTORIO_JSON, yytext());}
    directorio-def {return symbol(sym.DIRECTORIO_DEF, yytext());}

    //Se declaran simbolos
    ":" {return symbol(sym.DOS_PUNTOS, yytext());}
    "," {return symbol(sym.COMA, yytext());}

    //Se declaran tokens con regex
    "\"" {yybegin(STRING);}

    //Se ignoraran espacios sueltos
    {espacio}|{salto} {/*Ignorar*/}
    [^] {System.out.println("Caracter no definido en:"+(yyline+1)+", column:"+(yycolumn+1)+" "+buffer.toString());resetBuffer();}
}

<STRING> {  
    "\"" {String lexema = buffer.toString();yybegin(YYINITIAL);resetBuffer();return symbol(sym.STRING,lexema);}
    {salto}+ {System.out.println("Error: Cadena incompleta");yybegin(YYINITIAL);resetBuffer();}
    [^] {buffer.append(yytext());}
}