package com.universidad.appproyecto1.analisis.analisisDef;

public class VariableDef {
    private String nombre;
    private String tipo;
    private String valor;

    public VariableDef(String nombre, String tipo, String valor){
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }
    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public String getValor() {
        return valor;
    }
}
