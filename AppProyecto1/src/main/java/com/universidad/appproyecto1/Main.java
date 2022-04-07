/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.universidad.appproyecto1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.universidad.appproyecto1.interfaz.Interfaz;
import com.universidad.appproyecto1.modelo.ModelAnalisis;

/**
 *
 * @author fernanrod
 */
public class Main {
    public static void main(String[] args) {
        /*
        Interfaz interfaz = new Interfaz();
        */
        try {
            ModelAnalisis.analizaJSON(Files.readString(Paths.get("/home/fernanrod/pruebaOLC1/default/default-Proyecto.json")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
