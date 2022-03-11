package com.universidad.servidorproyecto1.modelo;

import java_cup.runtime.Symbol;

public class AnalysisError {
    private Symbol simbolo;
    private String mensaje;
    
    public AnalysisError(Symbol simbolo, String mensaje){
        this.simbolo = simbolo;
        this.mensaje = mensaje;
    }
    
    public Symbol getSimbolo() {
        return simbolo;
    }
    public String getMensaje() {
        return mensaje;
    }
}
