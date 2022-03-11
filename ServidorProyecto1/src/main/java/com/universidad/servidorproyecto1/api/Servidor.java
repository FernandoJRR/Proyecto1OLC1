/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.universidad.servidorproyecto1.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fernanrod
 */
public class Servidor {

    private static ServerSocket serverSocket;
    private static Socket socket;

    public static void iniciar(){
        while (true) {
            try {
                serverSocket = new ServerSocket(5252);
                socket = serverSocket.accept();
                DataInputStream dis=new DataInputStream(socket.getInputStream());  
                String  str=(String)dis.readUTF();  
                System.out.println("message= "+str);  
                enviarRespuesta("proyecto recibido exitosamente");
                serverSocket.close();  
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void enviarRespuesta(String respuesta){
        DataOutputStream dataOutput;
        try {
            dataOutput = new DataOutputStream(socket.getOutputStream());
            dataOutput.writeUTF(respuesta);  
            dataOutput.flush();  
            dataOutput.close();  
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }
}
