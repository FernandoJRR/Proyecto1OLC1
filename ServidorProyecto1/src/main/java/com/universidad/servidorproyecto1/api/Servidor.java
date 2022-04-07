/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.universidad.servidorproyecto1.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.universidad.servidorproyecto1.modelo.ModelAnalisis;

/**
 *
 * @author fernanrod
 */
public class Servidor {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static javax.swing.JTextArea outputArea;
    
    public static void setOutputArea(javax.swing.JTextArea outputArea) {
        Servidor.outputArea = outputArea;
    }

    public static void iniciar(){
        while (true) {
            try {
                ModelAnalisis modelAnalisis = new ModelAnalisis();

                serverSocket = new ServerSocket(5252);
                socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                File[][] contenidoProyectos = (File[][]) objectInputStream.readObject();
                
                String[] resultado = modelAnalisis.analizarProyectos(contenidoProyectos);
                escribirMensaje(resultado[0]);
                if (resultado[2].equals("")) {
                    enviarRespuesta(new String[]{resultado[1],"Proyectos Analizados Exitosamente"});
                } else {
                    enviarRespuesta(new String[]{resultado[1],"Se encontraron errores en los archivos:\n"+resultado[2]});
                }
                serverSocket.close();  
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void enviarRespuesta(String[] respuesta){
        ObjectOutputStream dataOutput;
        try {
            dataOutput = new ObjectOutputStream(socket.getOutputStream());
            dataOutput.writeObject(respuesta);  
            dataOutput.flush();  
            dataOutput.close();  
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
    
    private static void escribirMensaje(String mensaje){
        outputArea.setText(outputArea.getText()+"\n"+mensaje);
    }
}
