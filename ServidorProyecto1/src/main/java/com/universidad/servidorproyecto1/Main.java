package com.universidad.servidorproyecto1;


import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.universidad.servidorproyecto1.analisis.LexerJava;
import com.universidad.servidorproyecto1.analisis.ParserJava;
import com.universidad.servidorproyecto1.analisis.tablasimbolos.TablaDeSimbolos;
import com.universidad.servidorproyecto1.interfaz.Interfaz;

import java_cup.runtime.Symbol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fernanrod
 */
public class Main {
    public static void main(String[] args) {
        //Interfaz interfaz = new Interfaz();
        int numero = 0;
        try {
            String contenido = Files.readString(Paths.get("/home/fernanrod/prueba.java"));
            LexerJava lexer = new LexerJava(new StringReader(contenido));
            /*
            while (!lexer.yyatEOF()) {
                Symbol simbolo = lexer.next_token();
                System.out.println(simbolo.sym);
                System.out.println(simbolo.value);
            }
            */
            ParserJava parser = new ParserJava(lexer);
            parser.parse();
            System.out.println("Parsing terminado");
            TablaDeSimbolos tablaSimbolos = parser.getTablaDeSimbolos();
            System.out.println("Tabla Obtenida");
            tablaSimbolos.recorrerTabla();
            tablaSimbolos.clasesEncontradas();
            tablaSimbolos.metodosEncontrados();
            tablaSimbolos.variablesEncontradas();
            
            lexer.comentariosEncontrados();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}