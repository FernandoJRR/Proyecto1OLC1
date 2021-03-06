/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.universidad.appproyecto1.interfaz;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.file.Paths;

import javax.swing.UIManager;

import com.universidad.appproyecto1.api.*;
import com.universidad.appproyecto1.exceptions.NoArchivoException;
import com.universidad.appproyecto1.exceptions.NoDestinoException;
import com.universidad.appproyecto1.modelo.ModelAnalisis;
import com.universidad.appproyecto1.modelo.ModelEdicion;

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
        
        ModelEdicion.setConsolaTextArea(consolaTextArea);
        ModelEdicion.setJsonTextArea(jsonTextArea);
        ModelEdicion.setDefTextArea(defTextArea);
        ModelEdicion.setReportePane(reporteEditorPane);
        
        this.guardarMenuItem.setEnabled(false);
        this.analisisMenu.setEnabled(false);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        seleccionPanel = new javax.swing.JPanel();
        analizarButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        seleccionDirectorioButton1 = new javax.swing.JButton();
        seleccionDirectorioButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        archivoSeleccionadoLabel1 = new javax.swing.JLabel();
        archivoSeleccionadoLabal2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        directorioGuardadoLabel = new javax.swing.JLabel();
        elegirGuardadoButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        nombreProyectoLabel = new javax.swing.JLabel();
        editionPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        editionTabbedPane = new javax.swing.JTabbedPane();
        jsonConsolePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jsonTextArea = new javax.swing.JTextArea();
        defConsolePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        defTextArea = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        reporteEditorPane = new javax.swing.JEditorPane();
        consolaPanel = new javax.swing.JPanel();
        textConsolePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consolaTextArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        archivoMenu = new javax.swing.JMenu();
        abrirMenuItem = new javax.swing.JMenuItem();
        guardarMenuItem = new javax.swing.JMenuItem();
        analisisMenu = new javax.swing.JMenu();
        analizarMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new java.awt.CardLayout());

        seleccionPanel.setPreferredSize(new java.awt.Dimension(861, 500));

        analizarButton.setText("Analizar");
        analizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analizarButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Primer Proyecto");

        jLabel2.setText("Segundo Proyecto");

        seleccionDirectorioButton1.setText("Seleccionar Directorio");
        seleccionDirectorioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionDirectorioButton1ActionPerformed(evt);
            }
        });

        seleccionDirectorioButton2.setText("Seleccionar Directorio");
        seleccionDirectorioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionDirectorioButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Archivo Seleccionado:");

        jLabel4.setText("Archivo Seleccionado:");

        archivoSeleccionadoLabel1.setText("No se ha seleccionado");
        archivoSeleccionadoLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        archivoSeleccionadoLabel1.setMaximumSize(new java.awt.Dimension(210, 28));

        archivoSeleccionadoLabal2.setText("No se ha seleccionado");
        archivoSeleccionadoLabal2.setMaximumSize(new java.awt.Dimension(210, 28));

        jLabel5.setText("Directorio de Guardado:");

        directorioGuardadoLabel.setText("No se ha seleccionado");

        elegirGuardadoButton.setText("Elegir");
        elegirGuardadoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elegirGuardadoButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre Proyecto:");

        nombreProyectoLabel.setText("No se ha definido");

        javax.swing.GroupLayout seleccionPanelLayout = new javax.swing.GroupLayout(seleccionPanel);
        seleccionPanel.setLayout(seleccionPanelLayout);
        seleccionPanelLayout.setHorizontalGroup(
            seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seleccionPanelLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seleccionPanelLayout.createSequentialGroup()
                        .addComponent(elegirGuardadoButton)
                        .addGap(18, 18, 18)
                        .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(seleccionPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(directorioGuardadoLabel))
                            .addGroup(seleccionPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombreProyectoLabel)))
                        .addGap(0, 232, Short.MAX_VALUE))
                    .addGroup(seleccionPanelLayout.createSequentialGroup()
                        .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(seleccionDirectorioButton1)
                            .addComponent(jLabel3)
                            .addComponent(archivoSeleccionadoLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                        .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(seleccionDirectorioButton2)
                            .addComponent(jLabel4)
                            .addComponent(archivoSeleccionadoLabal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(77, 77, 77))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seleccionPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(analizarButton)
                .addGap(367, 367, 367))
        );
        seleccionPanelLayout.setVerticalGroup(
            seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seleccionPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seleccionDirectorioButton1)
                    .addComponent(seleccionDirectorioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(archivoSeleccionadoLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(archivoSeleccionadoLabal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seleccionPanelLayout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(directorioGuardadoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(seleccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreProyectoLabel)
                            .addComponent(jLabel6)))
                    .addGroup(seleccionPanelLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(elegirGuardadoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addComponent(analizarButton)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        mainPanel.add(seleccionPanel, "mainCard");

        jsonConsolePanel.setLayout(new java.awt.BorderLayout());

        jsonTextArea.setColumns(20);
        jsonTextArea.setRows(5);
        jScrollPane2.setViewportView(jsonTextArea);

        jsonConsolePanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        NumeradorLinea jsonNumerador = new NumeradorLinea(jsonTextArea, 2);
        jScrollPane2.setRowHeaderView(jsonNumerador);

        editionTabbedPane.addTab("Resultado JSON", jsonConsolePanel);
        //NumeradorLinea jsonNumerador = new NumeradorLinea(jsonTextArea, 2);
        //jsonConsolePanel.add(jsonNumerador, java.awt.BorderLayout.WEST);

        defConsolePanel.setLayout(new java.awt.BorderLayout());

        defTextArea.setColumns(20);
        defTextArea.setRows(5);
        jScrollPane3.setViewportView(defTextArea);

        defConsolePanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);
        NumeradorLinea defNumerador = new NumeradorLinea(defTextArea, 2);
        jScrollPane3.setRowHeaderView(defNumerador);

        editionTabbedPane.addTab("Reporte def", defConsolePanel);
        //NumeradorLinea defNumerador = new NumeradorLinea(defTextArea, 2);
        //defConsolePanel.add(defNumerador, java.awt.BorderLayout.WEST);

        jScrollPane4.setViewportView(reporteEditorPane);
        reporteEditorPane.setContentType("text/html");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        editionTabbedPane.addTab("Reporte", jPanel4);

        consolaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Consola"));

        textConsolePanel.setLayout(new java.awt.BorderLayout());

        consolaTextArea.setEditable(false);
        consolaTextArea.setColumns(20);
        consolaTextArea.setRows(5);
        jScrollPane1.setViewportView(consolaTextArea);

        textConsolePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        NumeradorLinea numerador = new NumeradorLinea(consolaTextArea, 2);
        jScrollPane1.setRowHeaderView(numerador);

        javax.swing.GroupLayout consolaPanelLayout = new javax.swing.GroupLayout(consolaPanel);
        consolaPanel.setLayout(consolaPanelLayout);
        consolaPanelLayout.setHorizontalGroup(
            consolaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(consolaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(consolaPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(textConsolePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        consolaPanelLayout.setVerticalGroup(
            consolaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
            .addGroup(consolaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(consolaPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(textConsolePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        //NumeradorLinea numerador = new NumeradorLinea(consolaTextArea, 2);
        //textConsolePanel.add(numerador, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editionTabbedPane)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(consolaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(editionTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(consolaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout editionPanelLayout = new javax.swing.GroupLayout(editionPanel);
        editionPanel.setLayout(editionPanelLayout);
        editionPanelLayout.setHorizontalGroup(
            editionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        editionPanelLayout.setVerticalGroup(
            editionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editionPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(editionPanel, "card4");

        archivoMenu.setText("Archivo");

        abrirMenuItem.setText("Abrir");
        abrirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirMenuItemActionPerformed(evt);
            }
        });
        archivoMenu.add(abrirMenuItem);

        guardarMenuItem.setText("Guardar");
        archivoMenu.add(guardarMenuItem);

        jMenuBar1.add(archivoMenu);

        analisisMenu.setText("Analisis");

        analizarMenuItem.setText("Analizar Reporte");
        analizarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analizarMenuItemActionPerformed(evt);
            }
        });
        analisisMenu.add(analizarMenuItem);

        jMenuBar1.add(analisisMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void seleccionDirectorioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionDirectorioButton1ActionPerformed
        seleccionarProyecto(1);
    }//GEN-LAST:event_seleccionDirectorioButton1ActionPerformed

    private void analizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analizarButtonActionPerformed
        try {
            String respuesta = ManejadorProyectos.enviarArchivo();
            if (respuesta.split(" ")[0].equals("Se")) {
                //Caso: Se encontraron errores en los proyectos
                javax.swing.JOptionPane.showMessageDialog(this, respuesta);
            } else {
                //Caso: Proyectos se evaluarion exitosamente
                javax.swing.JOptionPane.showMessageDialog(this, respuesta);
                //Se activa la pantalla de edicion
                ((java.awt.CardLayout)mainPanel.getLayout()).next(mainPanel);
                try {
                    ManejadorProyectos.guardarProyecto();
                } catch (IOException e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Ha ocurrido un error al guardar el proyecto");
                    e.printStackTrace();
                }
                ManejadorProyectos.abrirProyecto(Paths.get(ManejadorProyectos.getDirectorioGuardado().toString()+"/"+ManejadorProyectos.getNombreProyecto()+".copy"));
                this.guardarMenuItem.setEnabled(true);
                this.analisisMenu.setEnabled(true);
            }
        } catch (ConnectException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No se ha podido conectar al servidor");
            e.printStackTrace();
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ha ocurrido un error al enviar los proyectos");
            e.printStackTrace();
        } catch (NoArchivoException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: Falta seleccionar uno o dos proyectos para su analisis");
            e.printStackTrace();
        } catch (NoDestinoException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: No se ha seleccionado directorio de guardado");
            e.printStackTrace();
        }
    }//GEN-LAST:event_analizarButtonActionPerformed

    private void seleccionDirectorioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionDirectorioButton2ActionPerformed
        seleccionarProyecto(2);
    }//GEN-LAST:event_seleccionDirectorioButton2ActionPerformed

    private void elegirGuardadoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elegirGuardadoButtonActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) { 
            // accept option
            java.nio.file.Path directorio = fileChooser.getSelectedFile().toPath();
            try {
                ManejadorProyectos.seleccionarDestino(directorio);
                javax.swing.JOptionPane.showMessageDialog(this, "Se ha seleccionado el destino exitosamente\nAviso: El proyecto tomara el nombre del directorio");
                String nombreArchivo = directorio.toString();
                directorioGuardadoLabel.setText(nombreArchivo);
                nombreProyectoLabel.setText(ManejadorProyectos.getNombreProyecto());
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ha ocurrido un error al seleccionar el directorio");
                e.printStackTrace();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna proyecto");
        }
    }//GEN-LAST:event_elegirGuardadoButtonActionPerformed

    private void abrirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirMenuItemActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("COPY FILES","copy","Copy Project");
        fileChooser.setFileFilter(filter);
        
        if (fileChooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            
            java.nio.file.Path directorio = fileChooser.getSelectedFile().toPath();
            ((java.awt.CardLayout)mainPanel.getLayout()).next(mainPanel);
            ManejadorProyectos.abrirProyecto(directorio);
            this.guardarMenuItem.setEnabled(true);
            this.analisisMenu.setEnabled(true);
        } 
        
}//GEN-LAST:event_abrirMenuItemActionPerformed

    private void analizarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analizarMenuItemActionPerformed
        try {
            ModelAnalisis.analizaJSON(jsonTextArea.getText());
        } catch (Exception e) {
            ModelEdicion.agregarMensajeConsola("Hay errores en el archivo JSON");
        }
        try {
            ModelAnalisis.analizarDef(defTextArea.getText());
        } catch (Exception e) {
            ModelEdicion.agregarMensajeConsola("Hay errores en el archivo Def");
        }
    }//GEN-LAST:event_analizarMenuItemActionPerformed
    
    private void seleccionarProyecto(int numeroProyecto){
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) { 
            // accept option
            java.nio.file.Path directorio = fileChooser.getSelectedFile().toPath();
            try {
                ManejadorProyectos.seleccionarProyecto(directorio, numeroProyecto);
                javax.swing.JOptionPane.showMessageDialog(this, "Se ha seleccionado el proyecto exitosamente");
                String nombreArchivo = directorio.toString();
                if (numeroProyecto==1) {
                    archivoSeleccionadoLabel1.setText(nombreArchivo);
                }
                if (numeroProyecto==2) {
                    archivoSeleccionadoLabal2.setText(nombreArchivo);
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ha ocurrido un error al seleccionar el directorio");
                e.printStackTrace();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se ha seleccionado ningun proyecto");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrirMenuItem;
    private javax.swing.JMenu analisisMenu;
    private javax.swing.JButton analizarButton;
    private javax.swing.JMenuItem analizarMenuItem;
    private javax.swing.JMenu archivoMenu;
    private javax.swing.JLabel archivoSeleccionadoLabal2;
    private javax.swing.JLabel archivoSeleccionadoLabel1;
    private javax.swing.JPanel consolaPanel;
    public javax.swing.JTextArea consolaTextArea;
    private javax.swing.JPanel defConsolePanel;
    public javax.swing.JTextArea defTextArea;
    private javax.swing.JLabel directorioGuardadoLabel;
    private javax.swing.JPanel editionPanel;
    private javax.swing.JTabbedPane editionTabbedPane;
    private javax.swing.JButton elegirGuardadoButton;
    private javax.swing.JMenuItem guardarMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel jsonConsolePanel;
    public javax.swing.JTextArea jsonTextArea;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel nombreProyectoLabel;
    private javax.swing.JEditorPane reporteEditorPane;
    private javax.swing.JButton seleccionDirectorioButton1;
    private javax.swing.JButton seleccionDirectorioButton2;
    private javax.swing.JPanel seleccionPanel;
    private javax.swing.JPanel textConsolePanel;
    // End of variables declaration//GEN-END:variables
}
