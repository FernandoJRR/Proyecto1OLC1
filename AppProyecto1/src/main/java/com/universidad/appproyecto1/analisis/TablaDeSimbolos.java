package com.universidad.appproyecto1.analisis;

import java.util.LinkedHashMap;

import java_cup.runtime.Symbol;

public class TablaDeSimbolos {
    private String scope;
    private LinkedHashMap<Object, String> simbolos;
    
    private static String htmlGenerado = "";
    
    public static String getHtmlGenerado() {
        return htmlGenerado;
    }
    
    public TablaDeSimbolos(String scope){
        this.scope = scope;
        this.simbolos = new LinkedHashMap<>();
    }
    
    public LinkedHashMap<Object, String> getSimbolos() {
        return simbolos;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void agregarEntrada(Object llave, String informacionLlave){
        this.simbolos.put(llave, informacionLlave);
    }

    public void agregarEntradaInicio(Object llave, String informacionLlave){
        LinkedHashMap<Object, String> newMap= (LinkedHashMap<Object, String>) simbolos.clone();
        simbolos.clear();
        simbolos.put(llave, informacionLlave);
        simbolos.putAll(newMap);
    }
    
    public void recorrerTabla(){
        htmlGenerado = "";
        recorrerTabla(0);
    }
    
    public void recorrerTabla(int indentacion){
        for (Object simbolo : simbolos.keySet()){
            TablaDeSimbolos tablaActual = (TablaDeSimbolos)simbolo;
            String scope = simbolos.get(simbolo);
            switch (scope) {
                case "Declaracion Variables":
                    recorrerDeclaracionVariables(tablaActual);
                    break;
                case "Definicion HTML":
                    recorrerHTML(tablaActual,indentacion);
                    break;
            }
        }
    }
    
    public void recorrerDeclaracionVariables(TablaDeSimbolos tablaDeclaraciones){
    }
    
    public void recorrerHTML(TablaDeSimbolos tablaHTML, int indentacion){
        for (Object simbolo : tablaHTML.simbolos.keySet()) {
            String scope = tablaHTML.simbolos.get(simbolo);
            switch (scope) {
                case "Tag Abre":
                    htmlGenerado += "<html>\n";
                    break;
                case "Hijos":
                    TablaDeSimbolos tablaActual = (TablaDeSimbolos)simbolo;
                    recorrerHijos(tablaActual,indentacion+1);
                    break;
                case "Tag Cierra":
                    htmlGenerado += "</html>\n";
                    break;
            }
        }
    }
    
    public void recorrerHijos(TablaDeSimbolos tablaHijos, int indentacion){
        for (Object simbolo : tablaHijos.simbolos.keySet()) {
            String scope = tablaHijos.simbolos.get(simbolo);
            switch (scope) {
                case "Acceso Variable":
                    //Se busca agregar el valor de la variable
                    break;
                case "Texto":
                    htmlGenerado += simbolo.toString();
                    break;
            }
        }
    }
    
    public String obtenerVariableAcceso(TablaDeSimbolos tablaAcceso){
        String variable = "";
        for (Object iterable_element : iterable) {
            String scope = tablaHijos.simbolos.get(simbolo);
            switch (scope) {
                case "Acceso Variable":
                    //Se busca agregar el valor de la variable
                    break;
                case "Texto":
                    htmlGenerado += simbolo.toString();
                    break;
            }
        }
        return variable;
    }
    
    public String indentar(int cantidadIndentacion){
        String indentacion = "";
        for (int i = 0; i < cantidadIndentacion; i++) {
            indentacion+=" ";
        }
        indentacion+="└λ";
        return indentacion;
    }
}
