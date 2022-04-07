package com.universidad.servidorproyecto1.analisis;

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
    
    private HashMap<String,Integer> comentarios;

    public HashMap<String, Integer> getComentarios() {
        return comentarios;
    }
    public void setComentarios(HashMap<String, Integer> comentarios) {
        this.comentarios = comentarios;
    }
    
    //Hashmap que guarda las clases que se repiten y la cantidad de veces que se repiten
    private static HashMap<String, Integer> clases;
    //Hashmap que guarda el metodo que se repite con la cantidad de veces que se repite y la cantidad de parametros que tiene
    private static HashMap<String, int[]> metodos;

    //Hashmap que guarda la variable que se repite y donde se repite
    private static HashMap<String, ArrayList<String>> variables;

    private static String claseActual = "";
    private static String metodoActual = "";
    private static ArrayList<String> metodosClase = new ArrayList<>();
    private static ArrayList<String> parametrosMetodo = new ArrayList<>();
    private static String tipoVariableActual;
    
    public static HashMap<String, Integer> getClases() {
        return clases;
    }
    public static HashMap<String, int[]> getMetodos() {
        return metodos;
    }
    public static HashMap<String, ArrayList<String>> getVariables() {
        return variables;
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
            String metodo = "";
            for (Object parte : simbolos.keySet()) {
                switch (simbolos.get(parte)) {
                    case "Tipo":
                        if (parte instanceof String) {
                            metodoActual += parte;
                            metodo += parte;
                        } else {
                            metodoActual += ((Symbol)parte).value.toString();
                            metodo += ((Symbol)parte).value.toString();
                        }
                        break;
                    case "Identificador":
                        metodoActual += " "+((Symbol)parte).value.toString();
                        metodo += " "+((Symbol)parte).value.toString();
                        metodosClase.add(metodo);
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
                        String variable = ((Symbol)parte).value.toString()+" "+tipoVariableActual;
                        String encontradoEn = metodoActual.equals("")? "Clase "+claseActual : "Metodo "+metodoActual;

                        if (variables.get(variable)!=null) {
                            variables.get(variable).add(encontradoEn);
                        } else {
                            ArrayList<String> ocurrencia = new ArrayList<>();
                            ocurrencia.add(encontradoEn);
                            variables.put(variable, ocurrencia);
                        }
                        break;
                    default:
                        break;
                }               
            }
        }
        for (Object simbolo : simbolos.keySet()){
            //System.out.println(imprimirIndentacion(nivelActual)+simbolos.get(simbolo).toString());
            if (simbolo instanceof TablaDeSimbolos) {
                ((TablaDeSimbolos)simbolo).recorrerTabla(nivelActual+1);
            } else if (simbolo instanceof Symbol) {
                //System.out.println(imprimirIndentacion(nivelActual)+((Symbol)simbolo).value);
            } else if (simbolo instanceof String) {
                //System.out.println(imprimirIndentacion(nivelActual)+simbolo.toString());
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
            int cantidadParametros = parametrosMetodo.size();
            Collections.sort(parametrosMetodo);
            for (String parametro : parametrosMetodo) {
                parametros += parametro+" ";
            }
            parametros = parametros.trim();
            String metodo = metodoActual+" "+parametros;
            metodo = metodo.trim();
            if (metodos.get(metodo)!=null) {
                metodos.get(metodo)[0]++;
            } else {
                metodos.put(metodo, new int[]{1,cantidadParametros});
            }

            metodoActual = "";
            parametrosMetodo = new ArrayList<>();
        }
    }
    
    private void recorrerParametro(TablaDeSimbolos parametro){
        String tipoParametro = null;
        
        String encontradoEn = metodoActual.equals("")? "Clase "+claseActual : "Metodo "+metodoActual;
        for (Object parte : parametro.getSimbolos().keySet()) {
            switch (parametro.getSimbolos().get(parte)) {
                case "Tipo":
                    tipoParametro = ((Symbol)parte).value.toString();
                    break;
                case "Identificador":
                    String variable = ((Symbol)parte).value.toString()+" "+tipoParametro;
                    parametrosMetodo.add(variable);
                    if (variables.get(variable)!=null) {
                        variables.get(variable).add(encontradoEn);
                    } else {
                        ArrayList<String> ocurrencia = new ArrayList<>();
                        ocurrencia.add(encontradoEn);
                        variables.put(variable, ocurrencia);
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
                System.out.println(clase.split(" ")[0]);
            }
        }
    }

    public void metodosEncontrados(){
        System.out.println("Metodos Encontrados Repetidos");
        for (String metodo : metodos.keySet()) {
            if (metodos.get(metodo)[0]>1) {
                System.out.println(metodo+" "+metodos.get(metodo)[1]);
            }
        }
    }
    
    public void variablesEncontradas(){
        System.out.println("Variables Encontradas Repetidas");
        for (String variable : variables.keySet()) {
            if (variables.get(variable).size()>1) {
                System.out.println(variable+" "+variables.get(variable).toString());
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
}
