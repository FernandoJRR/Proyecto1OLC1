package com.universidad.servidorproyecto1.modelo;

import java.io.StringReader;
import java.util.ArrayList;

import com.universidad.servidorproyecto1.analisis.LexerJava;
import com.universidad.servidorproyecto1.analisis.ParserJava;

public class ModelAnalisis {
    private ArrayList<AnalysisError> errores = new ArrayList<>();
    private LexerJava lexer;
    private ParserJava parser;
    
    public String analizar(String input){
        String output = "";
        output += "Analisis Iniciado\n";
        StringReader stringReader = new StringReader(input);
        lexer = new LexerJava(stringReader);
        parser = new ParserJava(lexer);
        output += "Iniciadno Analisis Lexico/Sintactico\n";
        try {
            parser.debug_parse();
        } catch (Exception e) {
            output += "Error Fatal. El analisis no ha podido ser terminado";
            e.printStackTrace();
        }
        output += "Analisis Lexico/Sintactico Terminado Exitosamente\n";
        
        return output;
    }
    
    public void agregarError(AnalysisError error){
        errores.add(error);
    }

    public ArrayList<AnalysisError> getErrores() {
        return errores;
    }
}
