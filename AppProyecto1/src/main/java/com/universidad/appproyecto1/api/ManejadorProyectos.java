package com.universidad.appproyecto1.api;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.universidad.appproyecto1.exceptions.NoArchivoException;

public class ManejadorProyectos {
    //TODO: Manejar directorios, no archivos
    static Path directorioPrimerProyecto;
    static Path directorioSegundoProyecto;
    
    public static void seleccionarProyecto(Path directorioProyecto, int numeroProyecto){
        if (numeroProyecto==1) {
            directorioPrimerProyecto = directorioProyecto;
        } else if (numeroProyecto==2) {
            directorioSegundoProyecto = directorioProyecto;
        }
    }

    public static boolean enviarArchivo() throws IOException, NoArchivoException, ConnectException{
        if (directorioPrimerProyecto==null || directorioSegundoProyecto==null) { throw new NoArchivoException(); }

        String contenidoArchivo = Files.readString(directorioPrimerProyecto);
        
        Cliente.getInstance();
        return Cliente.enviarInformacion(contenidoArchivo);
    }
}
