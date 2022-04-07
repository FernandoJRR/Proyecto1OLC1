package com.universidad.appproyecto1.api;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public static String enviarInformacion(File[] informacionPrimerProyecto, File[] informacionSegundoProyecto) throws IOException{
        File[][] informacionProyectos = new File[][]{informacionPrimerProyecto,informacionSegundoProyecto};

        ObjectOutputStream dataOutput = new ObjectOutputStream(socket.getOutputStream());  
        dataOutput.writeObject(informacionProyectos);
        dataOutput.flush();  
        return esperarRespuesta();
    }
    
    public static String esperarRespuesta(){
        try {
            getInstance();
            ObjectInputStream dis = new ObjectInputStream(socket.getInputStream());  
            String[] respuesta = (String[])dis.readObject();
            ManejadorProyectos.setJsonProyecto(respuesta[0]);
            System.out.println("reponse= "+respuesta[0]);  
            socket.close();  
            return respuesta[1];
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
