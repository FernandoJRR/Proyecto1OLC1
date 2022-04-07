package com.universidad.servidorproyecto1.modelo;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import com.universidad.servidorproyecto1.analisis.CompileError;
import com.universidad.servidorproyecto1.analisis.LexerJava;
import com.universidad.servidorproyecto1.analisis.ParserJava;
import com.universidad.servidorproyecto1.analisis.TablaDeSimbolos;

public class ModelAnalisis {
    private ArrayList<AnalysisError> errores = new ArrayList<>();
    private LexerJava lexer;
    private ParserJava parser;
    
    private TablaDeSimbolos tablaTemp;
    private ArrayList<CompileError> erroresTemp;
    
    //Hashmap que guarda las clases que se repiten y la cantidad de veces que se repiten
    HashMap<String,Integer> clasesProyecto = new HashMap<>();
    //Hashmap que guarda el metodo que se repite con la cantidad de veces que se repite y la cantidad de parametros que tiene
    HashMap<String,int[]> metodosProyecto = new HashMap<>();
    //Hashmap que guarda la variable que se repite y donde se repite
    HashMap<String,ArrayList<String>> variablesProyecto = new HashMap<>();
    //Hashmap que guarda el comentario que se repite y cuantas veces se repite
    HashMap<String,Integer> comentariosProyecto = new HashMap<>();
    
    float punteo;
    
    public String analizar(String input){
        String output = "";
        ArrayList<CompileError> erroresAnalisis = new ArrayList<>();
        output += "Analisis Iniciado\n";
        StringReader stringReader = new StringReader(input);
        lexer = new LexerJava(stringReader);
        lexer.setErroresEncontrados(erroresAnalisis);
        parser = new ParserJava(lexer);
        parser.setErroresEncontrados(erroresAnalisis);
        output += "Iniciando Analisis Lexico/Sintactico\n";
        try {
            parser.parse();
            tablaTemp = parser.getTablaDeSimbolos();
            erroresTemp = parser.getErroresEncontrados();
        } catch (Exception e) {
            output += "Error Fatal. El analisis no ha podido ser terminado";
            tablaTemp = null;
            erroresTemp = parser.getErroresEncontrados();
            e.printStackTrace();
        }
        output += "Analisis Lexico/Sintactico Terminado Exitosamente\n";
        
        return output;
    }
    
    
    public String[] analizarProyectos(File[][] input){
        String output = "";
        resetAnalisis();

        String archivosConError = "";

        File[] contenidoPrimerProyecto = input[0];
        File[] contenidoSegundoProyecto = input[1];
        ArrayList<TablaDeSimbolos> tablasPrimerProyecto = new ArrayList<>();
        ArrayList<TablaDeSimbolos> tablasSegundoProyecto = new ArrayList<>();

        ArrayList<ArrayList<CompileError>> erroresPrimerProyecto = new ArrayList<>();
        ArrayList<ArrayList<CompileError>> erroresSegundoProyecto = new ArrayList<>();

        output += "Inicio Analisis Primer Proyecto\n";
        for (File archivo : contenidoPrimerProyecto) {
            try {
                output += "Inicio Analisis Archivo: "+archivo.getName()+"\n";
                String contenido = Files.readString(archivo.toPath());
                analizar(contenido);
                tablasPrimerProyecto.add(tablaTemp);
                erroresPrimerProyecto.add(erroresTemp);

                if (erroresTemp.size()>0) { archivosConError += archivo.getName()+" "; }

            } catch (IOException e) {
                e.printStackTrace();
                archivosConError += archivo.getName()+" ";
            }
        }
        output += "Primer Proyecto Analizado Exitosamente\n";

        output += "Inicio Analisis Segundo Proyecto\n";
        for (File archivo : contenidoSegundoProyecto) {
            try {
                output += "Inicio Analisis Archivo: "+archivo.getName()+"\n";
                String contenido = Files.readString(archivo.toPath());
                analizar(contenido);
                tablasSegundoProyecto.add(tablaTemp);
                erroresSegundoProyecto.add(erroresTemp);
                
                if (erroresTemp.size()>0) { archivosConError += archivo.getName()+" "; }

            } catch (IOException e) {
                e.printStackTrace();
                archivosConError += archivo.getName()+" ";
            }
        }
        output += "Segundo Proyecto Analizado Exitosamente\n";
        
        boolean hayErroresPrimerProyecto = false;
        boolean hayErroresSegundoProyecto = false;
        for (ArrayList<CompileError> errores : erroresPrimerProyecto) {
            if (errores.size()>0) {hayErroresPrimerProyecto=true;break;}
        }
        for (ArrayList<CompileError> errores : erroresSegundoProyecto) {
            if (errores.size()>0) {hayErroresSegundoProyecto=true;break;}
        }
        
        output += hayErroresPrimerProyecto? "Errores en primer proyecto\n" : "";
        output += hayErroresSegundoProyecto? "Errores en segundo proyecto\n" : "";
        
        if (!hayErroresPrimerProyecto) {
            unirTablasProyecto(tablasPrimerProyecto);
        }
        if (!hayErroresSegundoProyecto) {
            unirTablasProyecto(tablasSegundoProyecto);
        }
        
        String JSON = "";
        if (!hayErroresPrimerProyecto && !hayErroresSegundoProyecto) {
            calcularPunteo();
            output += "Punteo Calculado\n";
            JSON = generarJSON();
            output += "JSON Generado\n";
        } else {
            output += "Hay errores en los proyectos, no se puede calcular el resultado";
            output += "Errores Primer Proyecto\n";
            for (ArrayList<CompileError> listaErrores : erroresPrimerProyecto) {
                for (CompileError error : listaErrores) {
                    output += error.toString()+"\n";
                }
            }
            
            output += "Errores Segundo Proyecto\n";
            for (ArrayList<CompileError> listaErrores : erroresPrimerProyecto) {
                for (CompileError error : listaErrores) {
                    output += error.toString()+"\n";
                }
            }
        }

        return new String[]{output,JSON,archivosConError};
    }
    
    public void agregarError(AnalysisError error){
        errores.add(error);
    }

    public ArrayList<AnalysisError> getErrores() {
        return errores;
    }
    
    private void resetAnalisis(){
        //Hashmap que guarda las clases que se repiten y la cantidad de veces que se repiten
        clasesProyecto = new HashMap<>();
        //Hashmap que guarda el metodo que se repite con la cantidad de veces que se repite y la cantidad de parametros que tiene
        metodosProyecto = new HashMap<>();
        //Hashmap que guarda la variable que se repite y donde se repite
        variablesProyecto = new HashMap<>();
        //Hashmap que guarda los comentarios que se repites y cuantas veces se repiten
        comentariosProyecto = new HashMap<>();
    }
    
    private void unirTablasProyecto(ArrayList<TablaDeSimbolos> tablaProyecto){
        for (TablaDeSimbolos tablaDeSimbolos : tablaProyecto) {
            tablaDeSimbolos.recorrerTabla();

            HashMap<String,Integer> clasesTabla = tablaDeSimbolos.getClases();
            HashMap<String,int[]> metodosTabla = tablaDeSimbolos.getMetodos();
            HashMap<String,ArrayList<String>> variablesTabla = tablaDeSimbolos.getVariables();
            HashMap<String,Integer> comentariosTabla = tablaDeSimbolos.getComentarios();
            
            for (String clase : clasesTabla.keySet()) {
                if (clasesProyecto.get(clase)!=null) {
                    clasesProyecto.put(clase, clasesProyecto.get(clase) + clasesTabla.get(clase));
                } else {
                    clasesProyecto.put(clase, clasesTabla.get(clase));
                }
            }
            for (String metodo : metodosTabla.keySet()) {
                if (metodosProyecto.get(metodo)!=null) {
                    metodosProyecto.get(metodo)[0] += metodosTabla.get(metodo)[0];
                } else {
                    metodosProyecto.put(metodo, metodosTabla.get(metodo));
                }
            }
            for (String variable : variablesTabla.keySet()) {
                if (variablesProyecto.get(variable)!=null) {
                    variablesProyecto.get(variable).addAll(variablesTabla.get(variable));
                } else {
                    variablesProyecto.put(variable, variablesTabla.get(variable));
                }
            }
            for (String comentario : comentariosTabla.keySet()) {
                if (comentariosProyecto.get(comentario)!=null) {
                    comentariosProyecto.put(comentario, comentariosProyecto.get(comentario) + comentariosTabla.get(comentario));
                } else {
                    comentariosProyecto.put(comentario, comentariosTabla.get(comentario));
                }
            }
        }
        
    }
    
    public void recorrerOcurrencias(){
        System.out.println("Score");
        System.out.println(punteo);

        System.out.println("Clases Encontradas Repetidas");
        for (String clase : clasesProyecto.keySet()) {
            if (clasesProyecto.get(clase)>1) {
                System.out.println(clase.split(" ")[0]);
            }
        }
    
        System.out.println("Variables Encontradas Repetidas");
        for (String variable : variablesProyecto.keySet()) {
            if (variablesProyecto.get(variable).size()>1) {
                System.out.println(variable+" "+variablesProyecto.get(variable).toString());
            }
        }
        
        System.out.println("Metodos Encontrados Repetidos");
        for (String metodo : metodosProyecto.keySet()) {
            if (metodosProyecto.get(metodo)[0]>1) {
                System.out.println(metodo+" "+metodosProyecto.get(metodo)[1]);
            }
        }

        System.out.println("Comentarios Encontrados Repetidos");
        for (String comentario : comentariosProyecto.keySet()) {
            if (comentariosProyecto.get(comentario)>1) {
                System.out.println(comentario+" "+comentariosProyecto.get(comentario));
            }
        }
    }
    
    private void calcularPunteo(){
        float punteoClases;
        float punteoMetodos;
        float punteoVariables;
        float punteoComentarios;
        
        Integer cantidadClases = 0;
        Integer cantidadClasesRepetidas = 0;
        for (String clase : clasesProyecto.keySet()) {
            cantidadClases += clasesProyecto.get(clase);
            if (clasesProyecto.get(clase)>1) {
                cantidadClasesRepetidas += clasesProyecto.get(clase);
            }
        }
        
        Integer cantidadMetodos = 0;
        Integer cantidadMetodosRepetidos = 0;
        for (String metodo : metodosProyecto.keySet()) {
            cantidadMetodos += metodosProyecto.get(metodo)[0];
            if (metodosProyecto.get(metodo)[0]>1) {
                cantidadMetodosRepetidos += metodosProyecto.get(metodo)[0];
            }
        }
        
        Integer cantidadVariables = 0;
        Integer cantidadVariablesRepetidas = 0;
        for (String variable : variablesProyecto.keySet()) {
            cantidadVariables += variablesProyecto.get(variable).size();
            if (variablesProyecto.get(variable).size()>1) {
                cantidadVariablesRepetidas += variablesProyecto.get(variable).size();
            }
        }
        
        Integer cantidadComentarios = 0;
        Integer cantidadComentariosRepetidos = 0;
        for (String comentario : comentariosProyecto.keySet()) {
            cantidadComentarios += comentariosProyecto.get(comentario);
            if (comentariosProyecto.get(comentario)>1) {
                cantidadComentariosRepetidos += comentariosProyecto.get(comentario);
            }
        }
        
        punteoClases = (cantidadClasesRepetidas.floatValue()/cantidadClases.floatValue())*0.25f;
        punteoMetodos = (cantidadMetodosRepetidos.floatValue()/cantidadMetodos.floatValue())*0.25f;
        punteoVariables = (cantidadVariablesRepetidas.floatValue()/cantidadVariables.floatValue())*0.25f;
        punteoComentarios = (cantidadComentariosRepetidos.floatValue()/cantidadComentarios.floatValue())*0.25f;
        
        punteo = punteoClases+punteoVariables+punteoMetodos+punteoComentarios;
    }
    
    public String generarJSON(){
        boolean esPrimero = true; //Monitoreo trailing comma
        String JSON = "";
        JSON += "{\n";

        JSON += "\t\"Score\": \""+punteo+"\",\n";

        JSON += "\t\"Clases\": [\n";

        for (String clase : clasesProyecto.keySet()) {
            if (clasesProyecto.get(clase)>1) {
                if (!esPrimero) {
                    JSON += ",\n";
                } else { esPrimero = false; }
                
                JSON += "\t\t{ \"Nombre\": \""+clase.split(" ")[0]+"\" }";
            }
        }

        esPrimero = true;

        JSON += "\n\t],\n";
        
        JSON += "\t\"Variables\": [\n";

        for (String variable : variablesProyecto.keySet()) {
            if (variablesProyecto.get(variable).size()>1) {
                StringBuilder ocurrencias = new StringBuilder(variablesProyecto.get(variable).toString());
                ocurrencias.deleteCharAt(0);
                ocurrencias.deleteCharAt(ocurrencias.length()-1);

                if (!esPrimero) {
                    JSON += ",\n";
                } else { esPrimero = false; }

                JSON += "\t\t{ \"Nombre\": \""+variable.split(" ")[0]+"\", \"Tipo\": \""+variable.split(" ")[1]+"\", \"Funcion\": \""+ocurrencias.toString()+"\"}";
            }
        }
        
        esPrimero = true;

        JSON += "\n\t],\n";

        JSON += "\t\"Metodos\": [\n";

        for (String metodo : metodosProyecto.keySet()) {
            if (metodosProyecto.get(metodo)[0]>1) {
                String tipo = metodo.split(" ")[0].equals("-")? "" : metodo.split(" ")[0];

                if (!esPrimero) {
                    JSON += ",\n";
                } else { esPrimero = false; }
                
                JSON += "\t\t{ \"Nombre\": \""+metodo.split(" ")[1]+"\", \"Tipo\": \""+tipo+"\", \"Parametros\": \""+metodosProyecto.get(metodo)[1]+"\"}";
            }
        }
        
        esPrimero = true;

        JSON += "\n\t],\n";
        
        JSON += "\t\"Comentarios\": [\n";

        for (String comentario : comentariosProyecto.keySet()) {
            if (comentariosProyecto.get(comentario)>1) {

                if (!esPrimero) {
                    JSON += ",\n";
                } else { esPrimero = false; }
                
                JSON += "\t\t{ \"Texto\": \""+comentario+"\"}";
            }
        }

        JSON += "\n\t]\n";

        JSON += "}";
        return JSON;
    }
}
