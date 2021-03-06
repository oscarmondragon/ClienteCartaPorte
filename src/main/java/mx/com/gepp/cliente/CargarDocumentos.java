/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import mx.com.gepp.beans.RecepCartaPorteGepp;
import mx.com.gepp.utilities.Comprimir;
import mx.com.gepp.utilities.Constantes;
import mx.com.gepp.utilities.Encriptador;

/**
 *
 * @author dspace
 */
public class CargarDocumentos extends javax.swing.JFrame {

    private List<File> archivosSeleccionados;
    JFrame CargarDocumentos;

    /**
     * Creates new form CargarDocumentos
     */
    public CargarDocumentos() {

        initComponents();
        archivosSeleccionados = new ArrayList<>();
        txt_usuario.setText(Constantes.USER_GEPP);
        txt_proveedor.setText(Constantes.NUMERO_PROVEEDOR);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelContenido = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_proveedor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_url = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_selecionarArchivos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_archivos = new javax.swing.JTable();
        btn_regresar = new javax.swing.JButton();
        btn_enviar = new javax.swing.JButton();
        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        text_idViaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelContenido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        jLabel2.setText("Usuario");

        txt_usuario.setEditable(false);

        jLabel4.setText("Numero Proveedor");

        txt_proveedor.setEditable(false);

        jLabel5.setText("URL de archivos");

        jLabel6.setText("Cargar archivos");

        btn_selecionarArchivos.setText("Seleccionar");
        btn_selecionarArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selecionarArchivosActionPerformed(evt);
            }
        });

        tbl_archivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Ruta", "Tama??o"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_archivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_archivosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_archivos);

        btn_regresar.setText("Regresar");
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });

        btn_enviar.setText("Enviar");
        btn_enviar.setEnabled(false);
        btn_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_url)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(btn_selecionarArchivos)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_regresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addContainerGap(93, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(btn_selecionarArchivos))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_regresar)
                    .addComponent(btn_enviar))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelContenidoLayout = new javax.swing.GroupLayout(panelContenido);
        panelContenido.setLayout(panelContenidoLayout);
        panelContenidoLayout.setHorizontalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelContenidoLayout.setVerticalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );

        panelEncabezado.setBackground(java.awt.SystemColor.activeCaptionBorder);
        panelEncabezado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        jLabel1.setText("Cargar documentos de carta porte");

        jLabel7.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel7.setText("Folio GEPP:");

        text_idViaje.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        text_idViaje.setForeground(new java.awt.Color(0, 102, 204));
        text_idViaje.setText("Identificador del Viaje");

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(text_idViaje))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(text_idViaje))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviarActionPerformed
        // TODO add your handling code here:
        String nombreCarpeta = "viajes";
        int respuesta = JOptionPane.showConfirmDialog(this, "??Esta seguro de enviarlo?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (respuesta) {
            case JOptionPane.YES_OPTION:
                Encriptador encoder = new Encriptador();
                String passUser = Constantes.USER_PASS_GEPP;
                String urlEncriptada = null;
                String urlRestService = Constantes.URL_REST_SERVICE;
                String url = txt_url.getText();
                FileWriter fichero = null;
                PrintWriter pw = null;

                //Conversion de archivo zip a Base64
                Comprimir zip = new Comprimir();
                String folioGepp = text_idViaje.getText();
                //  String nombreZip = "C:\\\\ProgramaGEPP/CartasPorteEnviadas/" + text_idViaje.getText() + ".zip";
                String nombreZip = "CartasPorteEnviadas/" + folioGepp + ".zip";
                zip.comprimir(archivosSeleccionados, nombreZip);

                //Encriptacion de URL
                try {
                    urlEncriptada = new String(encoder.encriptar(url, passUser));
                } catch (Exception e) {
                    System.out.println("Ocurri?? un error al encriptar el archivo.");
                }
                //>Integramos el archivo
                String encodedBase64 = null;
                String archivoEncriptado = null;
                //Conversion de archivo zip a Base64
                try {
                    File originalFile = new File(nombreZip);
                    try (FileInputStream fileInputStreamReader = new FileInputStream(originalFile)) {
                        byte[] bytes = new byte[(int) originalFile.length()];
                        fileInputStreamReader.read(bytes);
                        encodedBase64 = new String(Base64.getEncoder().encode(bytes));
                    }
                    System.out.println("Se convirtio a base64");
                } catch (IOException e) {
                    System.out.println("Ocurri?? un error al convertir a Base64.");
                }   //Encriptacion de archivo
                try {
                    archivoEncriptado = new String(encoder.encriptar(encodedBase64, passUser));
                    System.out.println("Se encrypto");
                } catch (Exception e) {
                    System.out.println("Ocurri?? un error al encriptar el archivo.");
                }   //Escribir folioGepp en archivo de texto

                //Encriptacion de archivo
                try {
                    archivoEncriptado = new String(encoder.encriptar(encodedBase64, passUser));
                } catch (Exception e) {
                    System.out.println("Ocurri?? un error al encriptar el archivo.");
                }
                //Creamos objeto para envio
                RecepCartaPorteGepp cartaPorte = new RecepCartaPorteGepp(Constantes.USER_GEPP,
                        Constantes.NUMERO_PROVEEDOR,
                        "12",
                        "00",
                        "SIN ERROR",
                        urlEncriptada,
                        archivoEncriptado
                );

                System.out.println("usuarioGEPP: " + cartaPorte.getUsuarioGEPP());
                System.out.println("numProveedor: " + cartaPorte.getNumProveedor());
                System.out.println("numViaje: " + cartaPorte.getNumViaje());
                System.out.println("codigoError: " + cartaPorte.getCodigoError());
                System.out.println("mensajeError: " + cartaPorte.getMensajeError());
                System.out.println("urlDocumentos: " + cartaPorte.getUrlDocumentos());
                System.out.println("zipBase64: " + cartaPorte.getZipBase64());
                System.out.println("");

                //Invocacion WS
//                ClientConfig clientConfig = new DefaultClientConfig();
//                clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//                Client client = Client.create(clientConfig);
//
//                WebResource webResource = client.resource(urlRestService);
//
//                ClientResponse response = webResource.path("/recepcionCartaPorte")
//                        .type("application/json")
//                        .post(ClientResponse.class, cartaPorte);
//
//                System.out.println("Estatus respuesta: " + response.getStatus());
//                System.out.println("");
//
//                ReceptionResponse output = response.getEntity(ReceptionResponse.class);
//
//                System.out.println("C??digo Error: " + output.getCodigoError());
//                System.out.println("Mensaje Error: " + output.getMensajeError());
                String hola = "00";
                // if("00".equals(output.getCodigoError())){
                if ("00".equals(hola)) {
//                    JOptionPane.showMessageDialog(null,"??Envio exitoso!\n" + "Estatus respuesta: " + response.getStatus() +
//                            "\nError: " + output.getCodigoError()
//                        + "\nMensaje:\n" + output.getMensajeError());
                    JOptionPane.showMessageDialog(null, "??Envio exitoso!\n");
                    //Registrar folio en archivo txt de folios enviados
                    try {
                        fichero = new FileWriter("foliosGEPP.txt", true);
                        pw = new PrintWriter(fichero);
                        pw.print("");
                        pw.println(text_idViaje.getText());

                    } catch (IOException e) {
                    } finally {
                        try {
                            // Nuevamente aprovechamos el finally para 
                            // asegurarnos que se cierra el fichero.
                            if (null != fichero) {
                                fichero.close();
                            }
                        } catch (IOException e2) {
                        }
                    }

                    //  JOptionPane.showMessageDialog(null, "Envio completado.");
                    archivosSeleccionados.clear();
                    //Actualizamos el estado de los viajes enviados
                    // obtenerViajes viajes = new obtenerViajes();
                    //obtenerViajes.setVisible(true);
                    obtenerViajes.MostrarTabla(nombreCarpeta);

                    this.dispose();

                } else {
//                    JOptionPane.showMessageDialog(null,"??Ocurrio un error!\nEstatus respuesta: " + response.getStatus() + 
//                            "\nError: " + output.getCodigoError()
//                        + "\nMensaje:\n" + output.getMensajeError());
                }

                break;

            case JOptionPane.NO_OPTION:
                break;
            case JOptionPane.CLOSED_OPTION:
                break;
            default:
                break;
        }

    }//GEN-LAST:event_btn_enviarActionPerformed

    private void tbl_archivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_archivosMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel m = (DefaultTableModel) tbl_archivos.getModel();
            int filaSeleccionada = tbl_archivos.getSelectedRow();

            //Borrar archivo de la lista
            archivosSeleccionados.remove(filaSeleccionada);
            if (archivosSeleccionados.isEmpty()) {
                btn_enviar.setEnabled(false);
            }
            //BORRAR LA FILA
            // JOptionPane.showMessageDialog(rootPane, "Se elimino fila: " + tbl_archivos.getSelectedRow());
            m.removeRow(filaSeleccionada);

        }
    }//GEN-LAST:event_tbl_archivosMouseClicked

    private void btn_selecionarArchivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_selecionarArchivosActionPerformed

        JFileChooser selectorArchivos = new JFileChooser("../../");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Archivos de texto", "pdf", "xml");
        selectorArchivos.setFileFilter(fnef);

        selectorArchivos.setMultiSelectionEnabled(true);

        if (selectorArchivos.showOpenDialog(CargarDocumentos) == JFileChooser.APPROVE_OPTION) {
            File[] archivos = selectorArchivos.getSelectedFiles();
            archivosSeleccionados.addAll(Arrays.asList(archivos));

            //  JOptionPane.showMessageDialog(CargarDocumentos, String.format("Se han agregado %d archivos.", archivos.length, "Informacion", JOptionPane.INFORMATION_MESSAGE));
            DefaultTableModel model = (DefaultTableModel) tbl_archivos.getModel();

            for (File file : archivos) {
                String tamagnio = "";
                if (file.length() < 1_000_000) {
                    tamagnio = String.format("%.2fKB", file.length() / 1024.0);
                } else {
                    tamagnio = String.format("%.2fMB", file.length() / 1024.0 / 1024.0);
                }

                model.addRow(new Object[]{file.getName(), file.getParent(), tamagnio});
            }

        }

        if (!archivosSeleccionados.isEmpty()) {
            btn_enviar.setEnabled(true);
        }
    }//GEN-LAST:event_btn_selecionarArchivosActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        // TODO add your handling code here:

        this.dispose();
    }//GEN-LAST:event_btn_regresarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CargarDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargarDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargarDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargarDocumentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargarDocumentos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_enviar;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JButton btn_selecionarArchivos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JTable tbl_archivos;
    public static javax.swing.JLabel text_idViaje;
    private javax.swing.JTextField txt_proveedor;
    private javax.swing.JTextField txt_url;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
