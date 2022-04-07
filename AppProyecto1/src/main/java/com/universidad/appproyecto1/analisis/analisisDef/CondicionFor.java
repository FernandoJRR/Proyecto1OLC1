package com.universidad.appproyecto1.analisis.analisisDef;

public class CondicionFor {
    private String iterador;
    private String hasta;

    public CondicionFor(String iterador, String hasta){
        this.iterador = iterador;
        this.hasta = hasta;
    }
    
    public String getIterador() {
        return iterador;
    }
    public String getHasta() {
        return hasta;
    }
}
