package com.universidad.servidorproyecto1.analisis.tablasimbolos;

import java.util.ArrayList;
import java.util.Hashtable;

import com.universidad.servidorproyecto1.analisis.tablasimbolos.contenido.ContenidoEntrada;

import java_cup.runtime.Symbol;

public class InformacionEntrada {
    private ArrayList<Symbol> simbolos; //Simbolos que componen la entrada en la tabla
    
    private TipoContenido tipoEntrada;
    
    private ContenidoEntrada contenidoEntrada;
    
    public InformacionEntrada(ArrayList<Symbol> simbolos, TipoContenido tipoContenido, ContenidoEntrada contenidoEntrada){
        this.simbolos = simbolos;
        this.tipoEntrada = tipoContenido;
        this.contenidoEntrada = contenidoEntrada;
    }
    
    public ContenidoEntrada getContenidoEntrada() {
        return contenidoEntrada;
    }
}