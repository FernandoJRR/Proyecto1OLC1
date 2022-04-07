package com.universidad.appproyecto1.modelo;

import java.io.StringReader;
import java.util.ArrayList;

import com.universidad.appproyecto1.analisis.CompileError;
import com.universidad.appproyecto1.analisis.ParJSON;
import com.universidad.appproyecto1.analisis.TablaDeSimbolos;
import com.universidad.appproyecto1.analisis.analisisDef.LexerDef;
import com.universidad.appproyecto1.analisis.analisisDef.ParserDef;
import com.universidad.appproyecto1.analisis.analisisJSON.LexerJSON;
import com.universidad.appproyecto1.analisis.analisisJSON.ParserJSON;

import java_cup.runtime.Symbol;

public class ModelAnalisis {
    static Float punteo;
    static ArrayList<String> clasesRepetidas;
    static ArrayList<Metodo> metodosRepetidos;
    static ArrayList<Variable> variablesRepetidas;
    static ArrayList<String> comentariosRepetidos;
    static ArrayList<CompileError> erroresJSON;
    static ArrayList<CompileError> erroresDef;

    public static void analizaJSON(String contenidoJSON) throws Exception{
        punteo = 0f;
        clasesRepetidas = new ArrayList<>();
        metodosRepetidos = new ArrayList<>();
        variablesRepetidas = new ArrayList<>();
        comentariosRepetidos = new ArrayList<>();
        erroresJSON = new ArrayList<>();

        LexerJSON lexer = new LexerJSON(new StringReader(contenidoJSON));
        lexer.setErrores(erroresJSON);
        ParserJSON parser = new ParserJSON(lexer);
        parser.setErroresEncontrados(erroresJSON);
        
        parser.parse();
        
        //Si existen errores Lexicos y sintacticos no se realizara el analisis semantico
        if (erroresJSON.size()==0) {
            ParJSON[] objetosJSON = parser.getObjetosJSON();   
            recorrerListaPares(objetosJSON, 0);
        }
    }
    
    private static void recorrerListaPares(ParJSON[] lista, int indentacion){
        for (ParJSON par : lista) {
            Object llave = par.getLlave();
            Object valor = par.getValor();
            
            System.out.print(indentar(indentacion)+"└λ"+((Symbol)llave).value.toString()+":");

            if (valor instanceof ParJSON[]) {
                System.out.println();
                recorrerListaPares((ParJSON[])valor, indentacion+1);
            }
            if (valor instanceof Object[]) {
                System.out.println();
                recorrerListaValores((Object[])valor, indentacion+1);
            }
            if (valor instanceof Symbol) {
                String contenidoValor = ((Symbol)valor).value.toString();
                System.out.println(contenidoValor);
            }
            
            if (((Symbol)llave).value.toString().equals("Score")) {
                try {
                    punteo = Float.parseFloat(((Symbol)valor).value.toString());
                } catch (Exception e) {
                    if (valor instanceof Symbol) {
                        agregarError((Symbol)valor, "No se pudo obtener el punteo");
                    } else {
                        agregarError("No se pudo obtener el punteo");
                    }
                }
            } else if (((Symbol)llave).value.toString().equals("Clases")) {
                try {
                    obtenerClases((Object[])valor);
                } catch (Exception e) {
                    agregarError("No se pudo obtener las clases");
                }
            } else if (((Symbol)llave).value.toString().equals("Variables")) {
                try {
                    obtenerVariables((Object[])valor);
                } catch (Exception e) {
                    agregarError("No se pudo obtener las variables");
                }
            } else if (((Symbol)llave).value.toString().equals("Metodos")) {
                try {
                    obtenerMetodos((Object[])valor);
                } catch (Exception e) {
                    agregarError("No se pudo obtener los metodos");
                }
            } else if (((Symbol)llave).value.toString().equals("Comentarios")) {
                try {
                    obtenerComentarios((Object[])valor);
                } catch (Exception e) {
                    agregarError("No se pudo obtener los comentarios");
                }
            }
        }
    }

    private static void recorrerListaValores(Object[] lista, int indentacion){
        for (Object valor : lista) {
            
            if (valor instanceof ParJSON[]) {
                recorrerListaPares((ParJSON[])valor, indentacion+1);
            }
            if (valor instanceof Object[]) {
                recorrerListaValores((Object[])valor, indentacion+1);
            }
            if (valor instanceof Symbol) {
                System.out.println(indentar(indentacion)+"└λ"+((Symbol)valor).value);
            }
        }
    }   
    
    private static String indentar(int numeroIndentacion){
        String indentacion = "";
        for (int i = 0; i < numeroIndentacion; i++) {
            indentacion += " ";
        }
        return indentacion;
    }
    
    private static void obtenerClases(Object[] listaClases){
        for (Object objeto : listaClases) {
            //Se comprueba que el objeto sea la especificacion de una clase
            if (objeto instanceof ParJSON[]) {
                for (ParJSON par : (ParJSON[])objeto) {
                    //Se comprueba que la llave sea correcta
                    if (((Symbol)par.getLlave()).value.toString().equals("Nombre")) {
                        //Se comprueba el casteo del simbolo
                        try {
                            Symbol simboloValor = ((Symbol)par.getValor());
                            clasesRepetidas.add(simboloValor.value.toString()); //Si todo sale bien se agrega el nombre de la clase a la lista
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no es el nombre de una clase");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else {
                        agregarError((Symbol)par.getLlave(), "La llave del par no es 'Nombre'");
                    }
                }               
            } else {
                if (objeto instanceof Symbol) {
                    agregarError((Symbol)objeto, "El objeto no es la especificacion de una clase");
                } else {
                    agregarError("El objeto no es la especificacion de una clase");
                }
            }
        }
    } 
    
    private static void obtenerVariables(Object[] listaVariables) {
        for (Object objeto : listaVariables) {
            //Se comprueba que el objeto sea la definicion de una variable
            if (objeto instanceof ParJSON[]) {
                String nombre = null;
                String tipo = null;
                String ocurrencia = null;
                for (ParJSON par : (ParJSON[])objeto) {
                    //Se comprueba si la llave es alguna parte de la definicion de una clase
                    if (((Symbol)par.getLlave()).value.toString().equals("Nombre")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorNombre = ((Symbol)par.getValor()).value.toString();
                            nombre = valorNombre;
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no es el nombre de una variable");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else if(((Symbol)par.getLlave()).value.toString().equals("Tipo")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorTipo = ((Symbol)par.getValor()).value.toString();
                            tipo = valorTipo;
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no es el tipo de una variable");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else if (((Symbol)par.getLlave()).value.toString().equals("Funcion")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorOcurrencia = ((Symbol)par.getValor()).value.toString();
                            ocurrencia = valorOcurrencia;
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no son las ocurrencias de una variable");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else {
                        if (par.getLlave() instanceof Symbol) {
                            agregarError((Symbol)par.getLlave(), "El valor no corresponde con ningun parametro de una variable");
                        } else {
                            agregarError("La llave no corresponde con ningun parametro de una variable");
                        }
                    }
                }
                //Se agrega a la lista un metodo con los valores ingresados
                if (nombre == null) {
                    agregarError("No se definio el nombre de la variable");
                }
                if (tipo == null) {
                    agregarError("No se definio el tipo de la variable");
                }
                if (ocurrencia == null) {
                    agregarError("No se definio el tipo de la variable");
                }
                
                variablesRepetidas.add(new Variable(nombre, tipo, ocurrencia));
            }
        }
    }
    
    private static void obtenerMetodos(Object[] listaMetodos){
        for (Object objeto : listaMetodos) {
            //Se comprueba que el objeto sea la definicion de un metodo
            if (objeto instanceof ParJSON[]) {
                String nombre = null;
                String tipo = null;
                String parametros = null;
                for (ParJSON par : (ParJSON[])objeto) {
                    //Se comprueba si la llave es alguna parte de la definicion de un metodo
                    if (((Symbol)par.getLlave()).value.toString().equals("Nombre")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorNombre = ((Symbol)par.getValor()).value.toString();
                            nombre = valorNombre;
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no es el nombre de una variable");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else if(((Symbol)par.getLlave()).value.toString().equals("Tipo")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorTipo = ((Symbol)par.getValor()).value.toString();
                            tipo = valorTipo;
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no es el tipo de una variable");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else if (((Symbol)par.getLlave()).value.toString().equals("Parametros")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorParametros = ((Symbol)par.getValor()).value.toString();
                            parametros = valorParametros;
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no son las ocurrencias de una variable");
                            } else {
                                agregarError("El valor de la llave no es el nombre de una clase");
                            }
                        }
                    } else {
                        if (par.getLlave() instanceof Symbol) {
                            agregarError((Symbol)par.getLlave(), "El valor no corresponde con ningun parametro de un metodo");
                        } else {
                            agregarError("La llave no corresponde con ningun parametro de un metodo");
                        }
                    }
                }
                //Se agrega a la lista un metodo con los valores ingresados
                metodosRepetidos.add(new Metodo(nombre, tipo, parametros));
            }
        }
    }
    
    private static void obtenerComentarios(Object[] listaComentarios) {
        for (Object objeto : listaComentarios) {
            //Se comprueba que el objeto sea la definicion de un comentario
            if (objeto instanceof ParJSON[]) {
                for (ParJSON par : (ParJSON[])objeto) {
                    //Se comprueba si la llave es el comentario
                    if (((Symbol)par.getLlave()).value.toString().equals("Texto")) {
                        //Se comprueba el casteo del Simbolo encontrado
                        try {
                            String valorComentario = ((Symbol)par.getValor()).value.toString();
                            //Se agrega a la lista un comentario
                            comentariosRepetidos.add(valorComentario);
                        } catch (Exception e) {
                            //Si el objeto encontrado es un simbolo se usara para el error
                            if (par.getValor() instanceof Symbol) {
                                agregarError((Symbol)par.getValor(), "El valor de la llave no es un comentario");
                            } else {
                                agregarError("El valor de la llave no es un comentario");
                            }
                        }
                    } else {
                        if (par.getLlave() instanceof Symbol) {
                            agregarError((Symbol)par.getLlave(), "El valor no corresponde a un comentario");
                        } else {
                            agregarError("El valor no corresponde a un comentario");
                        }
                    }
                }
            }
        }
    }
    
    public static void analizarDef(String contenidoDef) throws Exception{
        erroresDef = new ArrayList<>();
        LexerDef lexer = new LexerDef(new StringReader(contenidoDef));
        lexer.setErrores(erroresDef);
        ParserDef parser = new ParserDef(lexer);
        parser.setErroresEncontrados(erroresDef);
        
        parser.parse();
        
        if (erroresDef.size()==0) {
            System.out.println("Parseo exitoso");
            interpretarDef(parser.getTablaDeSimbolos());
            System.out.println(TablaDeSimbolos.getHtmlGenerado());
        } else {
            for (CompileError error : erroresDef) {
                System.out.println(error);
            }
        }
    }
    
    public static void interpretarDef(TablaDeSimbolos tablaDef){
        tablaDef.recorrerTabla();
    }
    
    public static void generarReporte(){
        try {
            analizaJSON("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void agregarError(Symbol symbolError, String mensajeError){
            erroresJSON.add(new CompileError(symbolError, "Semantico", mensajeError));
    }
    
    private static void agregarError(String mensajeError){
        erroresJSON.add(new CompileError("Semantico", mensajeError));
    }
}
