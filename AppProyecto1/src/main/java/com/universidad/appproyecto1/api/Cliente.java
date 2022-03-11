package com.universidad.appproyecto1.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    private static Socket socket;

    public static Socket getInstance() throws UnknownHostException, IOException{
        if (socket == null) {
            socket = new Socket("localhost", 5252);
        }
        return socket;
    }

    public static boolean enviarInformacion(String informacion) throws IOException{
        DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());  
        dataOutput.writeUTF(informacion);  
        dataOutput.flush();  
        return esperarRespuesta();
    }
    
    public static boolean esperarRespuesta(){
        try {
            getInstance();
            DataInputStream dis = new DataInputStream(socket.getInputStream());  
            String  str=(String)dis.readUTF();  
            System.out.println("reponse= "+str);  
            socket.close();  
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
