package com.universidad.servidorproyecto1.interfaz;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.UIManager;

import com.universidad.servidorproyecto1.api.ManejadorProyectos;
import com.universidad.servidorproyecto1.api.Servidor;
import com.universidad.servidorproyecto1.modelo.ModelAnalisis;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author fernanrod
 */
public class Interfaz extends javax.swing.JFrame {
    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }

        initComponents();
        this.setVisible(true);
        agregarTexto(">> Iniciando Servidor");
        agregarTexto(">> Esperando Informacion del Cliente");
        Servidor.iniciar();
    }
    
    private void agregarTexto(String texto){
        consolaErroresTextArea.setText(consolaErroresTextArea.getText()+texto+"\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainConsolePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        analizarButton = new javax.swing.JButton();
        textConsolePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consolaErroresTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Consola de Errores:");

        analizarButton.setText("Analizar");
        analizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analizarButtonActionPerformed(evt);
            }
        });

        textConsolePanel.setLayout(new java.awt.BorderLayout());

        consolaErroresTextArea.setEditable(false);
        consolaErroresTextArea.setColumns(20);
        consolaErroresTextArea.setRows(5);
        jScrollPane1.setViewportView(consolaErroresTextArea);

        textConsolePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout mainConsolePanelLayout = new javax.swing.GroupLayout(mainConsolePanel);
        mainConsolePanel.setLayout(mainConsolePanelLayout);
        mainConsolePanelLayout.setHorizontalGroup(
            mainConsolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainConsolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                .addComponent(analizarButton)
                .addContainerGap())
            .addComponent(textConsolePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainConsolePanelLayout.setVerticalGroup(
            mainConsolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainConsolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainConsolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(analizarButton))
                .addGap(18, 18, 18)
                .addComponent(textConsolePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
        );

        NumeradorLinea numerador = new NumeradorLinea(consolaErroresTextArea, 2);
        textConsolePanel.add(numerador, BorderLayout.WEST);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainConsolePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainConsolePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void analizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analizarButtonActionPerformed
        ModelAnalisis modelAnalisis = new ModelAnalisis();
        Path directorioArchivo = Paths.get("/home/fernanrod/Programacion/Universidad/OLC1/Proyecto1OLC1/ServidorProyecto1/prueba.java");
        String contenidoArchivo;
        try {
            contenidoArchivo = Files.readString(directorioArchivo);
            String resultadoAnalisis = modelAnalisis.analizar(contenidoArchivo);
            agregarTexto(resultadoAnalisis);
        } catch (IOException e) {
            agregarTexto("Ha ocurrido un error al leer el archivo");
            e.printStackTrace();
        }
    }//GEN-LAST:event_analizarButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton analizarButton;
    public javax.swing.JTextArea consolaErroresTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainConsolePanel;
    private javax.swing.JPanel textConsolePanel;
    // End of variables declaration//GEN-END:variables
}
