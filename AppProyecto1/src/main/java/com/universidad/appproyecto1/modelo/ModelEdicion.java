package com.universidad.appproyecto1.modelo;

public class ModelEdicion {
    private static javax.swing.JTextArea jsonTextArea;
    private static javax.swing.JTextArea defTextArea;
    private static javax.swing.JTextArea consolaTextArea;
    private static javax.swing.JEditorPane reportePane;
    
    static String contenidoJSON;
    static String contenidoDef;
    
    public static void setJsonTextArea(javax.swing.JTextArea jsonTextArea) {
        ModelEdicion.jsonTextArea = jsonTextArea;
    }
    public static void setDefTextArea(javax.swing.JTextArea defTextArea) {
        ModelEdicion.defTextArea = defTextArea;
    }
    public static void setConsolaTextArea(javax.swing.JTextArea consolaTextArea) {
        ModelEdicion.consolaTextArea = consolaTextArea;
    }
    public static void setContenidoJSON(String contenidoJSON) {
        ModelEdicion.contenidoJSON = contenidoJSON;
    }
    public static void setContenidoDef(String contenidoDef) {
        ModelEdicion.contenidoDef = contenidoDef;
    }
    public static void setReportePane(javax.swing.JEditorPane reportePane) {
        ModelEdicion.reportePane = reportePane;
    }
    
    public static void escribirJSON(String contenido){
        jsonTextArea.setText(contenido);
    }
    
    public static void escribirDef(String contenido){
        defTextArea.setText(contenido);
    }
    
    public static void escribirConsola(String contenido){
        consolaTextArea.setText(contenido);
    }
    public static void agregarMensajeConsola(String mensaje){
        consolaTextArea.setText(consolaTextArea.getText()+mensaje);
    }
    public static void ponerReporte(String html) {
        reportePane.setText(html);
    }
}
