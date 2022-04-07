package com.universidad.appproyecto1.modelo;

public class Variable {
    private String nombre;
    private String tipo;
    private String ocurrencia;
    
    public Variable(String nombre, String tipo, String ocurrencia){
        this.nombre = nombre;
        this.tipo = tipo;
        this.ocurrencia = ocurrencia;
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public String getOcurrencia() {
        return ocurrencia;
    }
}
