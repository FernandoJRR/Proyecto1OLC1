package com.universidad.servidorproyecto1.analisis;

import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.HashMap;
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
L=[a-zA-Z]
D=[0-9]
espacio=[ \t]
salto=[\n\r]

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

    public void setErroresEncontrados(ArrayList<CompileError> erroresEncontrados){
        this.erroresEncontrados = erroresEncontrados;
    }
    
    private HashMap<String, Integer> comentarios = new HashMap<>();
    
    public HashMap<String, Integer> getComentarios(){
        return comentarios;
    }
    
    private void agregarComentario(String lexema){
        lexema = lexema.trim();

        if (comentarios.get(lexema)!=null) {
            comentarios.put(lexema, comentarios.get(lexema)+1);
        } else {
            comentarios.put(lexema, 1);
        }
    }

    public void comentariosEncontrados(){
        System.out.println("Comentarios Encontrados Repetidos");
        for (String comentario : comentarios.keySet()) {
            if (comentarios.get(comentario)>1) {
                System.out.println(comentario+" "+comentarios.get(comentario));
            }
        }
    }

    private ArrayList<CompileError> erroresEncontrados = new ArrayList<>();
    
    public ArrayList<CompileError> getErroresEncontrados(){
        return erroresEncontrados;
    }

    private void agregarError(Symbol symbolError, String mensajeError){
        erroresEncontrados.add(new CompileError(symbolError, "Lexico", mensajeError));
    }
%}

%eofval{
      return new Symbol(sym.EOF, yycolumn+1, yyline+1, null);
%eofval}

%%

<YYINITIAL> {
    //Se declaran las palabras reservadas
    import {return symbol(sym.IMPORT, yytext());}

    int {return symbol(sym.PR_INT, yytext());}
    boolean {return symbol(sym.PR_BOOLEAN, yytext());}
    String {return symbol(sym.PR_STRING, yytext());}
    char {return symbol(sym.PR_CHAR, yytext());}
    double {return symbol(sym.PR_DOUBLE, yytext());}
    Object {return symbol(sym.PR_OBJECT, yytext());}

    if {return symbol(sym.IF, yytext());}
    else {return symbol(sym.ELSE, yytext());}
    for {return symbol(sym.FOR, yytext());}
    while {return symbol(sym.WHILE, yytext());}
    do {return symbol(sym.DO, yytext());}
    switch {return symbol(sym.SWITCH, yytext());}
    default {return symbol(sym.DEFAULT, yytext());}
    
    public {return symbol(sym.PUBLIC, yytext());}
    private {return symbol(sym.PRIVATE, yytext());}
    protected {return symbol(sym.PROTECTED, yytext());}
    final {return symbol(sym.FINAL, yytext());}

    class {return symbol(sym.CLASS, yytext());}
    this {return symbol(sym.THIS, yytext());}
    super {return symbol(sym.SUPER, yytext());}
    
    new {return symbol(sym.NEW, yytext());}
    
    case {return symbol(sym.CASE, yytext());}
    break {return symbol(sym.BREAK, yytext());}
    return {return symbol(sym.RETURN, yytext());}

    //Se declaran simbolos
    
    "." {return symbol(sym.PUNTO, yytext());}
    "," {return symbol(sym.COMA, yytext());}
    ":" {return symbol(sym.DOS_PUNTOS, yytext());}
    ";" {return symbol(sym.PUNTO_COMA, yytext());}
    "{" {return symbol(sym.LLAVE_IZQ, yytext());}
    "}" {return symbol(sym.LLAVE_DER, yytext());}
    "(" {return symbol(sym.PAR_IZQ, yytext());}
    ")" {return symbol(sym.PAR_DER, yytext());}
    "[" {return symbol(sym.CORCH_IZQ, yytext());}
    "]" {return symbol(sym.CORCH_DER, yytext());}
    
    "+" {return symbol(sym.CRUZ, yytext());}
    "*" {return symbol(sym.ASTERISCO, yytext());}
    "-" {return symbol(sym.GUION, yytext());}
    "/" {return symbol(sym.BARRA, yytext());}
    "%" {return symbol(sym.MODULO, yytext());}
    "=" {return symbol(sym.IGUAL, yytext());}
    "+=" {return symbol(sym.IGUAL_INCREMENTO, yytext());}
    "-=" {return symbol(sym.IGUAL_DECREMENTO, yytext());}
    
    ">" {return symbol(sym.MAYOR_QUE, yytext());}
    "<" {return symbol(sym.MENOR_QUE, yytext());}
    "==" {return symbol(sym.IGUALDAD, yytext());}
    "!=" {return symbol(sym.NO_IGUALDAD, yytext());}
    ">=" {return symbol(sym.MAYOR_IGUAL, yytext());}
    "<=" {return symbol(sym.MENOR_IGUAL, yytext());}

    "!" {return symbol(sym.NOT, yytext());}
    "&&" {return symbol(sym.AND, yytext());}
    "||" {return symbol(sym.OR, yytext());}
    
    "++" {return symbol(sym.INCREMENT, yytext());}
    "--" {return symbol(sym.DECREMENT, yytext());}

    //Se declaran tokens con regex

    "\"" {yybegin(STRING);}

    "-"?{D}+ {return symbol(sym.ENTERO, yytext());}
    "-"?{D}+("f"|"F"|"d"|"D") {return symbol(sym.DECIMAL, yytext());}
    "-"?{D}+"."{D}+("f"|"F"|"d"|"D")? {return symbol(sym.DECIMAL, yytext());}


    "//" {yybegin(COMENTARIO_LINEA);}
    "/*" {yybegin(COMENTARIO_MULTILINEA);}
    
    ({L}|"$"|"_")({L}|{D}|"$"|"_")* {return symbol(sym.VARIABLE_IDENTIFICADOR, yytext());}

    //Se ignoraran espacios sueltos
    {espacio}|{salto} {/*Ignorar*/}
    [^] { agregarError(symbol(sym.EOF, yytext()), "Caracter no definido"); buffer.delete(0, buffer.length());}
}

<STRING> {
    ({L}|{D}|{espacio}) {buffer.append(yytext());}
    "\"" {String lexema = buffer.toString();resetBuffer();yybegin(YYINITIAL);return symbol(sym.STRING, lexema);}
    {salto}|[^] { agregarError(symbol(sym.STRING, buffer.toString()),"STRING incompleto"); buffer.delete(0, buffer.length()); yybegin(YYINITIAL);}
}

<COMENTARIO_LINEA> {
    {salto} {String lexema = buffer.toString();resetBuffer();yybegin(YYINITIAL);
                agregarComentario(lexema);}
    [^] {buffer.append(yytext());}
}

<COMENTARIO_MULTILINEA> {
    "*/" {String lexema = buffer.toString();resetBuffer();yybegin(YYINITIAL);
            agregarComentario(lexema);}
    [^] {buffer.append(yytext());}
}