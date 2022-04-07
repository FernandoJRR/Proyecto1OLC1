package com.universidad.appproyecto1.analisis;

import java_cup.runtime.Symbol;

public class CompileError {
    private Symbol simbolo;
    private String tipoError;
    private String mensajeError;
    
    public CompileError(Symbol simbolo, String tipoError, String mensajeError){
        this.simbolo = simbolo;
        this.tipoError = tipoError;
        this.mensajeError = mensajeError;
    }
    
    public CompileError(String tipoError, String mensajeError){
        this(null, tipoError, mensajeError);
    }
    
    public Symbol getSimbolo() {
        return simbolo;
    }
    public String getTipoError() {
        return tipoError;
    }
    public String getMensajeError() {
        return mensajeError;
    }
    
    public String toString(){
        String cadena = "";
        
        if (simbolo != null) {
            cadena += "Simbolo: '"+simbolo.value.toString()+"' Linea:"+simbolo.left+" Columna:"+simbolo.right+" ";
        }
        cadena += "Error "+tipoError+" ";
        cadena += "| "+mensajeError;
        
        return cadena;
    }
}