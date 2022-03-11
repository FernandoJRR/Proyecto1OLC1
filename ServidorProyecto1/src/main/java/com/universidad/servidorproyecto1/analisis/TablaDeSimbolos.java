package com.universidad.servidorproyecto1.analisis;

import java.util.ArrayList;

import java_cup.runtime.Symbol;

public class TablaDeSimbolos {
    private String nombreArchivo;
    private ArrayList<Symbol> simbolos;
    private ArrayList<Symbol> comentarios;
    
    public TablaDeSimbolos(String nombreArchivo){
        this.simbolos = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }
    
    public void agregarSimbolo(Symbol simbolo){
        simbolos.add(simbolo);
    }
    public void agregarComentario(Symbol simbolo){
        comentarios.add(simbolo);
    }
}
