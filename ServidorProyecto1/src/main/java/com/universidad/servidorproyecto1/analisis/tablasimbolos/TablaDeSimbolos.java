package com.universidad.servidorproyecto1.analisis.tablasimbolos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import java_cup.runtime.Symbol;

public class TablaDeSimbolos {
    private String scope;
    private LinkedHashMap<Object, String> simbolos;
    private static HashMap<String, Integer> clases;
    private static HashMap<String, Integer> metodos;
    private static HashMap<String, Integer> variables;
    private static String claseActual = "";
    private static String metodoActual = "";
    private static ArrayList<String> metodosClase = new ArrayList<>();
    private static ArrayList<String> parametrosMetodo = new ArrayList<>();
    private static String tipoVariableActual;

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
        clases = new HashMap<>();
        metodos = new HashMap<>();
        variables = new HashMap<>();
        recorrerTabla(0);
    }
    
    public void recorrerTabla(int nivelActual){
        // Acciones Preorden
        if (getScope().equals("Clase")) {
            for (Object parte : simbolos.keySet()) {
                switch (simbolos.get(parte)) {
                    case "Nombre":
                        claseActual = ((Symbol)parte).value.toString();
                        break;
                    default:
                        break;
                }               
            }
        }
        if (getScope().equals("Declaracion Variables")) {
            for (Object parte : simbolos.keySet()) {
                switch (simbolos.get(parte)) {
                    case "Tipo":
                        tipoVariableActual = ((Symbol)parte).value.toString();
                        break;
                    default:
                        break;
                }               
            }
        }
        if (getScope().equals("Declaracion Metodo")) {
            for (Object parte : simbolos.keySet()) {
                switch (simbolos.get(parte)) {
                    case "Tipo":
                        metodoActual += ((Symbol)parte).value.toString();
                        metodosClase.add(((Symbol)parte).value.toString());
                        break;
                    case "Identificador":
                        metodoActual += " "+((Symbol)parte).value.toString();
                        metodosClase.add(((Symbol)parte).value.toString());
                        break;
                    default:
                        break;
                }               
            }
        }
        if (getScope().equals("Parametros")) {
            for (Object parte : simbolos.keySet()) {
                switch (simbolos.get(parte)) {
                    case "Parametro":
                        recorrerParametro((TablaDeSimbolos)parte);
                        break;
                    default:
                        break;
                }               
            }
        }
        if (getScope().equals("Variable")) {
            for (Object parte : simbolos.keySet()) {
                switch (simbolos.get(parte)) {
                    case "Identificador":
                        String variable = tipoVariableActual+" "+((Symbol)parte).value.toString();
                        if (variables.get(variable)!=null) {
                            variables.put(variable, variables.get(variable)+1);
                        } else {
                            variables.put(variable, 1);
                        }
                        break;
                    default:
                        break;
                }               
            }
        }
        for (Object simbolo : simbolos.keySet()){
            System.out.println(imprimirIndentacion(nivelActual)+simbolos.get(simbolo).toString());
            if (simbolo instanceof TablaDeSimbolos) {
                ((TablaDeSimbolos)simbolo).recorrerTabla(nivelActual+1);
            } else if (simbolo instanceof Symbol) {
                System.out.println(imprimirIndentacion(nivelActual)+((Symbol)simbolo).value);
            } else if (simbolo instanceof String) {
                System.out.println(imprimirIndentacion(nivelActual)+simbolo.toString());
            }
            
        }
        // Acciones Postorden
        if (getScope().equals("Clase")) {
            String metodos = "";
            Collections.sort(metodosClase);
            for (String metodo : metodosClase) {
                metodos += metodo+" ";
            }
            metodos = metodos.trim();
            String clase = claseActual+" "+metodos;
            if (clases.get(clase)!=null) {
                clases.put(clase, clases.get(clase)+1);
            } else {
                clases.put(clase, 1);
            }

            claseActual = "";
            metodosClase = new ArrayList<>();
        }
        if (getScope().equals("Declaracion Metodo")) {
            String parametros = "";
            Collections.sort(parametrosMetodo);
            for (String parametro : parametrosMetodo) {
                parametros += parametro+" ";
            }
            parametros = parametros.trim();
            String metodo = metodoActual+" "+parametros;
            metodo = metodo.trim();
            if (metodos.get(metodo)!=null) {
                metodos.put(metodo, metodos.get(metodo)+1);
            } else {
                metodos.put(metodo, 1);
            }

            metodoActual = "";
            parametrosMetodo = new ArrayList<>();
        }
    }
    
    private void recorrerParametro(TablaDeSimbolos parametro){
        String tipoParametro = null;
        for (Object parte : parametro.getSimbolos().keySet()) {
            switch (parametro.getSimbolos().get(parte)) {
                case "Tipo":
                    tipoParametro = ((Symbol)parte).value.toString();
                    break;
                case "Identificador":
                    String variable = tipoParametro+" "+((Symbol)parte).value.toString();
                    parametrosMetodo.add(variable);
                    if (variables.get(variable)!=null) {
                        variables.put(variable, variables.get(variable)+1);
                    } else {
                        variables.put(variable, 1);
                    }
                    break;
                default:
                    break;
            }
        }      
    }

    public void clasesEncontradas(){
        System.out.println("Clases Encontradas Repetidas");
        for (String clase : clases.keySet()) {
            if (clases.get(clase)>1) {
                System.out.println(clase+" "+clases.get(clase));
            }
        }
    }

    public void metodosEncontrados(){
        System.out.println("Metodos Encontrados Repetidos");
        for (String metodo : metodos.keySet()) {
            if (metodos.get(metodo)>1) {
                System.out.println(metodo+" "+metodos.get(metodo));
            }
        }
    }
    
    public void variablesEncontradas(){
        System.out.println("Variables Encontradas Repetidas");
        for (String variable : variables.keySet()) {
            if (variables.get(variable)>1) {
                System.out.println(variable+" "+this.variables.get(variable));
            }
        }
    }
    
    public String imprimirIndentacion(int cantidadIndentacion){
        String indentacion = "";
        for (int i = 0; i < cantidadIndentacion; i++) {
            indentacion+=" ";
        }
        indentacion+="└λ";
        return indentacion;
    }
    
    /*
    private Hashtable<Symbol, InformacionEntrada> simbolos; //Indica los simbolos que componen la tabla
    
    public TablaDeSimbolos(){
        this.simbolos = new Hashtable<>();
    }
    
    public void agregarSimbolo(Symbol simbolo, InformacionEntrada contenido){
        simbolos.put(simbolo, contenido);
    }
    public InformacionEntrada getSimbolo(Symbol simbolo){
        return simbolos.get(simbolo);
    }
    */
}
