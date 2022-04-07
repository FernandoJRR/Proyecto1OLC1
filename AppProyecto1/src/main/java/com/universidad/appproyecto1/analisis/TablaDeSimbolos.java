package com.universidad.appproyecto1.analisis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.universidad.appproyecto1.analisis.analisisDef.CondicionFor;
import com.universidad.appproyecto1.analisis.analisisDef.VariableDef;

import java_cup.runtime.Symbol;

public class TablaDeSimbolos {
    private String scope;
    private LinkedHashMap<Object, String> simbolos;
    
    private HashMap<String, VariableDef> variables;
    
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
        variables = new HashMap<>();
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
        for (Object simbolo : simbolos.keySet()){
            TablaDeSimbolos tablaActual = (TablaDeSimbolos)simbolo;
            String scope = simbolos.get(simbolo);
            switch (scope) {
                case "Declaracion Variable":
                    obtenerVariable(tablaActual);
                    break;
                case "Asignacion Variable":
                    break;
            }
        }
    }
    
    public void obtenerVariable(TablaDeSimbolos tablaVariable) {
        String tipo = null;
        ArrayList<String> nombreVariables = new ArrayList<>();
        String valor = null;
        for (Object simbolo : simbolos.keySet()){
            TablaDeSimbolos tablaActual = (TablaDeSimbolos)simbolo;
            String scope = simbolos.get(simbolo);
            switch (scope) {
                case "Tipo":
                    tipo = ((Symbol)simbolo).value.toString();
                    break;
                case "Identificador":
                    nombreVariables.add(((Symbol)simbolo).value.toString());
                    break;
                case "Declaracion Valor":
                    break;
            }
        }
        for (String nombreVariabe : nombreVariables) {
            variables.put(nombreVariabe, new VariableDef(nombreVariabe, tipo, valor));
        }
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
                    String variable = obtenerVariableAcceso((TablaDeSimbolos)simbolo);
                    htmlGenerado += indentar(indentacion)+variable+" ";
                    break;
                case "Texto":
                    htmlGenerado += indentar(indentacion)+simbolo.toString()+" ";
                    break;
                case "Tag Header":
                    recorrerHeader((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Table":
                    recorrerTable((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag For":
                    //recorrerFor((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Row":
                    recorrerRow((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Header Tabla":
                    recorrerHeaderTabla((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Data":
                    recorrerData((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag BR":
                    htmlGenerado += "\n";
                    break;
            }
        }
    }
    
    public void recorrerHeader(TablaDeSimbolos tablaHeader, int indentacion){
        for (Object simbolo : tablaHeader.simbolos.keySet()) {
            String scope = tablaHeader.simbolos.get(simbolo);
            switch (scope) {
                case "Tag Abre":
                    htmlGenerado += indentar(indentacion)+"<h1>\n";
                    break;
                case "Hijos":
                    recorrerHijos((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Cierra":
                    htmlGenerado += "\n"+indentar(indentacion)+"</h1>"+"\n";
                    break;
            }
        }
    }
    
    public void recorrerTable(TablaDeSimbolos tablaTable,int indentacion){
        for (Object simbolo : tablaTable.simbolos.keySet()) {
            String scope = tablaTable.simbolos.get(simbolo);
            switch (scope) {
                case "Tag Abre":
                    htmlGenerado += indentar(indentacion)+"<table>\n";
                    break;
                case "Hijos":
                    recorrerHijos((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Cierra":
                    htmlGenerado += "\n"+indentar(indentacion)+"</table>"+"\n";
                    break;
            }
        }
    }
    
    public void recorrerFor(TablaDeSimbolos tablaFor,int indentacion){
        CondicionFor condicion = null;
        for (Object simbolo : tablaFor.simbolos.keySet()) {
            String scope = tablaFor.simbolos.get(simbolo);
            switch (scope) {
                case "Condicion For":
                    condicion = obtenerCondicionFor((TablaDeSimbolos)simbolo);
                    break;
                case "Hijos":
                    while (obtenerValorNumerico(condicion.getIterador()) <= obtenerValorNumerico(condicion.getHasta())) {
                        recorrerHijos((TablaDeSimbolos)simbolo, indentacion+1);
                        cambiarValorNumerico(condicion.getIterador(), obtenerValorNumerico(condicion.getIterador())+1);
                    }
                    break;
                case "Tag Cierra":
                    htmlGenerado += "\n"+indentar(indentacion)+"</table>"+"\n";
                    break;
            }
        }
    }
    
    public void recorrerRow(TablaDeSimbolos tablaRow,int indentacion){
        for (Object simbolo : tablaRow.simbolos.keySet()) {
            String scope = tablaRow.simbolos.get(simbolo);
            switch (scope) {
                case "Tag Abre":
                    htmlGenerado += indentar(indentacion)+"<tr>\n";
                    break;
                case "Hijos":
                    recorrerHijos((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Cierra":
                    htmlGenerado += "\n"+indentar(indentacion)+"</tr>"+"\n";
                    break;
            }
        }
    }
    
    public void recorrerHeaderTabla(TablaDeSimbolos tablaHeader,int indentacion){
        for (Object simbolo : tablaHeader.simbolos.keySet()) {
            String scope = tablaHeader.simbolos.get(simbolo);
            switch (scope) {
                case "Tag Abre":
                    htmlGenerado += indentar(indentacion)+"<th>\n";
                    break;
                case "Hijos":
                    recorrerHijos((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Cierra":
                    htmlGenerado += "\n"+indentar(indentacion)+"</th>"+"\n";
                    break;
            }
        }
    }
    
    public void recorrerData(TablaDeSimbolos tablaData,int indentacion){
        for (Object simbolo : tablaData.simbolos.keySet()) {
            String scope = tablaData.simbolos.get(simbolo);
            switch (scope) {
                case "Tag Abre":
                    htmlGenerado += indentar(indentacion)+"<td>\n";
                    break;
                case "Hijos":
                    recorrerHijos((TablaDeSimbolos)simbolo, indentacion+1);
                    break;
                case "Tag Cierra":
                    htmlGenerado += "\n"+indentar(indentacion)+"</td>"+"\n";
                    break;
            }
        }
    }
    
    public String obtenerVariableAcceso(TablaDeSimbolos tablaAcceso){
        String variable = "";
        for (Object simbolo : tablaAcceso.simbolos.keySet()) {
            String scope = tablaAcceso.simbolos.get(simbolo);
            switch (scope) {
                case "Variable":
                    if (simbolo instanceof Symbol) {
                        variable = ((Symbol)simbolo).value.toString();
                    } else if (simbolo instanceof TablaDeSimbolos) {
                        String scopeVariable = ((TablaDeSimbolos)simbolo).getScope();
                        switch (scopeVariable) {
                            case "Variable Vacia":
                                break;
                            case "Result":
                                break;
                            case "Result Lista":
                                break;
                        }
                    }
                    break;
            }
        }
        return variable;
    }
    
    public CondicionFor obtenerCondicionFor(TablaDeSimbolos tablaFor){
        String iterador = null;
        String hasta = null;
        
        for (Object simbolo : tablaFor.simbolos.keySet()) {
            String scope = tablaFor.simbolos.get(simbolo);
            switch (scope) {
                case "Iterador":
                    if (simbolo instanceof Symbol) {
                        iterador = ((Symbol)simbolo).value.toString();
                    } else {
                        //Error
                    }
                    break;
                case "Hasta":
                    if (simbolo instanceof Symbol) {
                        hasta = ((Symbol)simbolo).value.toString();
                    } else {
                        //Error
                    }
                    break;
            }
        }
        
        return new CondicionFor(iterador, hasta);
    }
    
    public int obtenerIterador(String valor){
        if (esEntero(valor)) {
            return Integer.parseInt(valor);
        } else {
            return obtenerValorNumerico(valor);
        }
    }

    public int obtenerValorNumerico(String variable){
        VariableDef variableIterador = variables.get(variable);
        return Integer.parseInt(variableIterador.getValor());
    }
    
    public boolean esEntero(String valor){
        try {
            Integer.parseInt(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void cambiarValorNumerico(String variable,int nuevoValor){
        VariableDef infoVariable = variables.get(variable);
        int valor = obtenerValorNumerico(variable);
        valor = nuevoValor;
        variables.put(variable, new VariableDef(infoVariable.getNombre(), infoVariable.getTipo(), String.valueOf(valor)));
    }
    
    public String indentar(int cantidadIndentacion){
        String indentacion = "";
        for (int i = 0; i < cantidadIndentacion; i++) {
            indentacion+=" ";
        }
        indentacion+=" ";
        return indentacion;
    }
}
