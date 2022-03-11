package com.universidad.servidorproyecto1.modelo;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.universidad.servidorproyecto1.analisis.LexerJava;

import java_cup.runtime.Symbol;

public class ModelAnalisis {
    private ArrayList<AnalysisError> errores = new ArrayList<>();
    private LexerJava lexer;
    
    public String analizar(String input){
        String output = "";
        output += "Analisis Iniciado\n";
        StringReader stringReader = new StringReader(input);
        lexer = new LexerJava(stringReader);
        output += "Analisis Lexico Iniciado\n";
        while (!lexer.yyatEOF()) {
            try {
                Symbol simbolo = lexer.next_token();
                System.out.println(simbolo.value);
            } catch (IOException e) {
                output += "Error Fatal. Analisis lexico fallido\n";
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        output += "Analisis Lexico Terminado Exitosamente\n";
        
        return output;
    }
    
    public void agregarError(AnalysisError error){
        errores.add(error);
    }

    public ArrayList<AnalysisError> getErrores() {
        return errores;
    }
}
