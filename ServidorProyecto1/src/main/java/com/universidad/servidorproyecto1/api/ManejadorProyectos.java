/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.universidad.servidorproyecto1.api;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fernanrod
 */
public class ManejadorProyectos {
    //TODO: manejar directorios y no archivos sueltos
    static String contenidoPrimerProyecto;
    static String contenidoSegundoProyecto;
    
    public static void obtenerContenidos() throws IOException{
        ServerSocket serverSocket = new ServerSocket(4848);
        Socket socket = serverSocket.accept();
        DataInputStream dataInput = new DataInputStream(socket.getInputStream());  
        contenidoPrimerProyecto = (String)dataInput.readUTF();  
        serverSocket.close();  
    }
    
    public static String getContenidoPrimerProyecto() {
        return contenidoPrimerProyecto;
    }
    public static String getContenidoSegundoProyecto() {
        return contenidoSegundoProyecto;
    }
}
