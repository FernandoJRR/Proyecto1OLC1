package com.universidad.appproyecto1.modelo;

public class Metodo {
    private String nombre;
    private String tipo;
    private String ocurrencias;
    
    public Metodo(String nombre, String tipo, String ocurrencias){
        this.nombre = nombre;
        this.tipo = tipo;
        this.ocurrencias = ocurrencias;
    }
    
    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public String getOcurrencias() {
        return ocurrencias;
    }
    
    public String toString(){
        String cadena = "";
        
        cadena += "Nombre: "+this.nombre;
        cadena += "Tipo: "+this.tipo;
        cadena += "Parametros: "+this.ocurrencias;
        
        return cadena;
    }
}
