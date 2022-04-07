package com.universidad.appproyecto1.analisis;

import java_cup.runtime.Symbol;

public class ParJSON {
    private Object llave;
    private Object valor;
    
    public ParJSON(String llave, Object valor){
        this.llave = llave;
        this.valor = valor;
    }
    public ParJSON(Symbol llave, Object valor){
        this.llave = llave;
        this.valor = valor;
    }
    public Object getLlave() {
        return llave;
    }
    public Object getValor() {
        return valor;
    }
}
