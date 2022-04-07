package com.universidad.appproyecto1.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import java_cup.runtime.Symbol;

import com.universidad.appproyecto1.analisis.CompileError;
import com.universidad.appproyecto1.analisis.analisisCopy.LexerCopy;
import com.universidad.appproyecto1.analisis.analisisCopy.ParserCopy;
import com.universidad.appproyecto1.exceptions.NoArchivoException;
import com.universidad.appproyecto1.exceptions.NoDestinoException;
import com.universidad.appproyecto1.modelo.ModelEdicion;

public class ManejadorProyectos {
    static Path directorioPrimerProyecto;
    static Path directorioSegundoProyecto;

    static Path directorioGuardado;
    private static String nombreProyecto;
    
    private static String jsonProyecto;
    
    public static String getNombreProyecto() {
        return nombreProyecto;
    }
    public static String getJsonProyecto() {
        return jsonProyecto;
    }
    public static void setJsonProyecto(String jsonProyecto) {
        ManejadorProyectos.jsonProyecto = jsonProyecto;
    }
    public static Path getDirectorioGuardado() {
        return directorioGuardado;
    }
    
    public static void seleccionarProyecto(Path directorioProyecto, int numeroProyecto){
        if (numeroProyecto==1) {
            directorioPrimerProyecto = directorioProyecto;
        } else if (numeroProyecto==2) {
            directorioSegundoProyecto = directorioProyecto;
        }
    }
    
    public static void seleccionarDestino(Path directorioDestino){
        directorioGuardado = directorioDestino;
        String nombre = directorioDestino.toString().split("/")[directorioDestino.toString().split("/").length-1];
        nombreProyecto = nombre+"-Proyecto";
    }

    public static String enviarArchivo() throws IOException, NoArchivoException, ConnectException, NoDestinoException{
        if (directorioPrimerProyecto==null || directorioSegundoProyecto==null) { throw new NoArchivoException(); }
        if (directorioGuardado==null) { throw new NoDestinoException(); }

        File[] contenidoPrimerProyecto = directorioArchivos(directorioPrimerProyecto);
        File[] contenidoSegundoProyecto = directorioArchivos(directorioSegundoProyecto);
        
        Cliente.getInstance();
        return Cliente.enviarInformacion(contenidoPrimerProyecto,contenidoSegundoProyecto);
    }
    
    private static File[] directorioArchivos(Path directorio) throws IOException{
        File[] array = null;
        
        Collection<File> archivos = FileUtils.listFiles(directorio.toFile(), new String[]{"java"}, true);
        array = new File[archivos.size()];

        int i = 0;
        for (Iterator<File> iterator = archivos.iterator(); iterator.hasNext();) {
          File archivoActual = iterator.next();
          array[i] = archivoActual;
          i++;
        }
        return array;
    }
    
    public static void abrirProyecto(Path archivoCopy) {
        String contenidoCopy;
        try {
            contenidoCopy = Files.readString(archivoCopy);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        ArrayList<CompileError> erroresCopy = new ArrayList<>();
        LexerCopy lexer = new LexerCopy(new StringReader(contenidoCopy));
        lexer.setErrores(erroresCopy);
        ParserCopy parser = new ParserCopy(lexer);
        parser.setErroresEncontrados(erroresCopy);

        Symbol symbolNombreProyecto = null;
        Symbol directorioJSON = null;
        Symbol directorioDef = null;
        try {
            ModelEdicion.agregarMensajeConsola(">> Analizando Archivo .copy\n");

            parser.parse();
            ArrayList<Symbol[]> campos = parser.getCampos();
            for (Symbol[] campo : campos) {
                //campo[0] = nombreCampo ---- campo[1] = informacionCampo
                switch (campo[0].value.toString()) {
                    case "nombre-proyecto":
                        if (symbolNombreProyecto==null) {
                            symbolNombreProyecto = campo[1];
                        } else {
                            erroresCopy.add(new CompileError(campo[1], "Semantico", "Se ha declarado el campo 'nombre-proyecto' mas de una vez"));
                        }
                        break;
                    case "directorio-json":
                        if (directorioJSON==null) {
                            directorioJSON = campo[1];
                        } else {
                            erroresCopy.add(new CompileError(campo[1], "Semantico", "Se ha declarado el campo 'directorio-json' mas de una vez"));
                        }
                        break;
                    case "directorio-def":
                        if (directorioDef==null) {
                            directorioDef = campo[1];
                        } else {
                            erroresCopy.add(new CompileError(campo[1], "Semantico", "Se ha declarado el campo 'directorio-def' mas de una vez"));
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Path pathJSON = null;
        Path pathDef = null;
        String stringJSON = null;
        String stringDef = null;
        
        if (symbolNombreProyecto!=null) {
            nombreProyecto = symbolNombreProyecto.value.toString();
        } else {
            erroresCopy.add(new CompileError("Semantico", "No se ha especificado el nombre del proyecto"));
        }
        if (directorioJSON!=null) {
            pathJSON = Paths.get(directorioJSON.value.toString());
            try {
                stringJSON = Files.readString(pathJSON);
            } catch (IOException e) {
                erroresCopy.add(new CompileError("Semantico", "El directorio del archivo JSON es invalido"));
                e.printStackTrace();
            }
            
        } else {
            erroresCopy.add(new CompileError("Semantico", "No se ha definido el directorio del archivo JSON"));
        }

        if (directorioDef!=null) {
            pathDef = Paths.get(directorioDef.value.toString());

            try {
                stringDef = Files.readString(pathDef);
            } catch (IOException e) {
                erroresCopy.add(new CompileError("Semantico", "El directorio del archivo Def es invalido"));
                e.printStackTrace();
            }
        
        } else {
            erroresCopy.add(new CompileError("Semantico", "No se ha definido el directorio del archivo Def"));
        }
        if (erroresCopy.size()>0) {
            System.out.println("Se encontraron errores en el archivo copy:");
            for (CompileError compileError : erroresCopy) {
                Symbol simbolo = compileError.getSimbolo();
                String tipoError = compileError.getTipoError();
                String mensajeError = compileError.getMensajeError();
                
                String consolaError = ">>> Ha ocurrido un error "+tipoError;
                if (simbolo!=null) {
                    consolaError += " en simbolo "+simbolo.value+", columna:"+simbolo.right+" y linea:"+simbolo.left;
                }
                consolaError += " | "+mensajeError+"\n";

                ModelEdicion.agregarMensajeConsola(consolaError);
            }
            ModelEdicion.agregarMensajeConsola(">> Debido a los errores el proyecto no ha sido cargado\n");
        } else {
            ModelEdicion.agregarMensajeConsola(">> Se ha cargado el proyecto '"+getNombreProyecto()+"' exitosamente\n");
            ModelEdicion.setContenidoJSON(stringJSON);
            ModelEdicion.setContenidoDef(stringDef);
            ModelEdicion.escribirJSON(stringJSON);
            ModelEdicion.escribirDef(stringDef);
        }
    }
    
    public static void guardarProyecto() throws IOException{
        String direccionArchivos = directorioGuardado.toString()+"/"+nombreProyecto;
        FileWriter fileWriter = new FileWriter(direccionArchivos+".json");
        fileWriter.write(jsonProyecto);
        fileWriter.close();
        System.out.println("JSON Escrito");
        fileWriter = new FileWriter(direccionArchivos+".def");
        fileWriter.write("");
        fileWriter.close();
        System.out.println("def Escrito");
        fileWriter = new FileWriter(direccionArchivos+".copy");
        fileWriter.write(generarArchivoCopy(direccionArchivos));
        fileWriter.close();
        System.out.println("copy Escrito");
    }

    private static String generarArchivoCopy(String direccionArchivos){
        String archivoCopy = "";
        archivoCopy += "nombre-proyecto:\""+nombreProyecto+"\",\n";
        archivoCopy += "directorio-def:\""+direccionArchivos+".def\",\n";
        archivoCopy += "directorio-json:\""+direccionArchivos+".json\"\n";
        
        return archivoCopy;
    }
}
