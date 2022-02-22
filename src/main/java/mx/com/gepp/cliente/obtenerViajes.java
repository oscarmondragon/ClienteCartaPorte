/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.cliente;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileOutputStream;

import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.zip.ZipEntry;
//import static javafx.scene.input.KeyCode.T;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import jdk.nashorn.internal.parser.JSONParser;

import mx.com.gepp.beans.TripsResponse;
import mx.com.gepp.beans.RenderTabla;

import mx.com.gepp.utilities.Comprimir;
import mx.com.gepp.utilities.Encriptador;
import mx.com.gepp.utilities.Constantes;
import mx.com.gepp.utilities.Descomprimir;
import mx.com.gepp.utilities.Pojo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dspace
 */
public class obtenerViajes extends javax.swing.JFrame {

    //obtener dia de hoy
//    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
//    Calendar calendar = Calendar.getInstance();
//    Date dateObj = calendar.getTime();
//    String formattedDate = dtf.format(dateObj);
    //botones de tabla ver viajes
    JButton botonVer = new JButton("Ver JSON");
    JButton botonCargar = new JButton("Cargar");

    //Nombre de carpeta y zip
    // String nombreZip = "viajes_" + formattedDate + ".zip";
    //String nombreCarpeta = "viajes_" + formattedDate;
    String nombreZip = "viajes.zip";
    String nombreCarpeta = "viajes";

    public static int columna, fila;

    public obtenerViajes() {
        initComponents();
        botonVer.setName("btnVer");
        botonCargar.setName("btnCargar");
        Descomprimir descompresorArchivo = new Descomprimir();
        // String nombreZip = "C:\\\\ProgramaGEPP/ZIPs/viajes_" + formattedDate + ".zip";
        // String nombreCarpeta = "C:\\\\ProgramaGEPP/viajes/viajes_" + formattedDate;

        descompresorArchivo.unZip(nombreZip, nombreCarpeta);
        MostrarTabla(nombreCarpeta);
    }

    public void MostrarTabla(String nombreCarpeta) {

        DefaultTableModel modeloViajes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        String[] columnas = {"FolioGEPP", "Fecha Generación", "Estado", "Ver datos", "Cargar documentos"};

        modeloViajes.setColumnIdentifiers(columnas);
        this.tablaViajes.setDefaultRenderer(Object.class, new RenderTabla());

        tablaViajes.setModel(modeloViajes);

        this.tablaViajes.setRowHeight(40);

        Object[] datos = new Object[5];

        File carpeta = new File(nombreCarpeta);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {
            for (String listado1 : listado) {
                JSONParser parser = new JSONParser();
                try {
                    JSONArray a = (JSONArray) parser.parse(new FileReader(nombreCarpeta + "/" + listado1));
                    JSONObject cabecera = (JSONObject) a.get(0);
                    String folioGEPP = (String) cabecera.get("FolioGEPP");
                    String fechaGeneracion = (String) cabecera.get("FechaGeneracion");

                    //Leer archivo foliosGEPP para determinar los que ya han enviado documentos
                    Scanner entrada = null;
                    String linea;
                    int numeroDeLinea = 1;
                    boolean contiene = false;
                    Scanner sc = new Scanner(System.in);

                    try {

                        //creamos un objeto File asociado al fichero seleccionado
                        File f = new File("foliosGEPP.txt");
                        //creamos un Scanner para leer el fichero
                        entrada = new Scanner(f);

                        while (entrada.hasNext()) { //mientras no se llegue al final del fichero
                            linea = entrada.nextLine();  //se lee una línea
                            if (linea.contains(folioGEPP)) {   //si la línea contiene el texto buscado se muestra por pantalla         
                                System.out.println("Linea " + numeroDeLinea + ": " + linea);
                                contiene = true;
                                datos[2] = "Enviado";
                            }
                            numeroDeLinea++; //se incrementa el contador de líneas
                        }
                        if (!contiene) { //si el archivo no contienen el texto se muestra un mensaje indicándolo
                            datos[2] = "Pendiente";
                            System.out.println(folioGEPP + " no se ha encontrado en el archivo");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println(e.toString());
                    } catch (NullPointerException e) {
                        System.out.println(e.toString() + "No ha seleccionado ningún archivo");
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        if (entrada != null) {
                            entrada.close();
                        }
                    }

                    //Cargar datos a la tabla
                    datos[0] = folioGEPP;
                    datos[1] = fechaGeneracion;

                    datos[3] = botonVer;
                    datos[4] = botonCargar;
                    modeloViajes.addRow(datos);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jLabel2 = new javax.swing.JLabel();
        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        btn_consultarViajes = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaViajes = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuConsultar = new javax.swing.JMenu();
        menuDescargar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuEditarConstantes = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelEncabezado.setBackground(new java.awt.Color(204, 204, 204));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("CONSULTA DE VIAJES DISPONIBLES");

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btn_consultarViajes.setText("Actualizar Tabla");
        btn_consultarViajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultarViajesActionPerformed(evt);
            }
        });

        jButton2.setText("Prueba abrir json");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btn_consultarViajes)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_consultarViajes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2))
        );

        tablaViajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaViajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaViajesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaViajes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
        );

        menuConsultar.setText("Consultar");

        menuDescargar.setText("Descargar viajes");
        menuDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDescargarActionPerformed(evt);
            }
        });
        menuConsultar.add(menuDescargar);

        jMenuBar1.add(menuConsultar);

        jMenu3.setText("Editar");

        menuEditarConstantes.setText("Constantes");
        menuEditarConstantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarConstantesActionPerformed(evt);
            }
        });
        jMenu3.add(menuEditarConstantes);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_consultarViajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultarViajesActionPerformed
        // TODO add your handling code here:
        botonVer.setName("btnVer");
        botonCargar.setName("btnCargar");
        Descomprimir descompresorArchivo = new Descomprimir();
        // String nombreZip = "C:\\\\ProgramaGEPP/ZIPs/viajes_" + formattedDate + ".zip";
        // String nombreCarpeta = "C:\\\\ProgramaGEPP/viajes/viajes_" + formattedDate;

        descompresorArchivo.unZip(nombreZip, nombreCarpeta);
        MostrarTabla(nombreCarpeta);
    }//GEN-LAST:event_btn_consultarViajesActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader("prueba.json"));

            JSONObject cabecera = (JSONObject) a.get(0);

            String folioGEPP = (String) cabecera.get("FolioGEPP");
            String fechaGeneracion = (String) cabecera.get("FechaGeneracion");
            String usoCfdi = (String) cabecera.get("UsoCFDI");

            JSONObject documentosGepp = (JSONObject) cabecera.get("DocumentosGEPP");
            JSONArray docsGepp = (JSONArray) documentosGepp.get("DocsGEPP");

            JSONArray ubicacionesGepp = (JSONArray) cabecera.get("UbicacionesGEPP");

            System.out.println(folioGEPP);
            System.out.println(fechaGeneracion);
            System.out.println(usoCfdi);

            if (docsGepp == null) {

            } else {
                Iterator<String> iterator = docsGepp.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }

            }

            if (ubicacionesGepp == null) {

            } else {
                Iterator<String> iteratorUbicaciones = ubicacionesGepp.iterator();
                while (iteratorUbicaciones.hasNext()) {
                    System.out.println(iteratorUbicaciones.next());
                }
            }

            JSONObject carta = (JSONObject) a.get(1);

            JSONObject cartaPorte = (JSONObject) carta.get("CartaPorte");

            String version = (String) cartaPorte.get("Version");
            String transpInternac = (String) cartaPorte.get("TranspInternac");
            Long totalDistRec = (Long) cartaPorte.get("TotalDistRec");

            System.out.println(version);
            System.out.println(transpInternac);
            System.out.println(totalDistRec);

            JSONObject ubicaciones = (JSONObject) cartaPorte.get("Ubicaciones");
            JSONArray ubicacionesArray = (JSONArray) ubicaciones.get("Ubicacion");

            //UBICACION origen
            JSONObject origen = (JSONObject) ubicacionesArray.get(0);

            String tipoUbicacion = (String) origen.get("TipoUbicacion");
            String idUbicacion = (String) origen.get("IDUbicacion");
            String rfcRemitenteDestinatario = (String) origen.get("RFCRemitenteDestinatario");
            String fechaHoraSalidaLlegada = (String) origen.get("FechaHoraSalidaLlegada");
            JSONObject domicilioOrigen = (JSONObject) origen.get("Domicilio");

            //UBICACION destino
            JSONObject destino = (JSONObject) ubicacionesArray.get(1);
            String tipoUbicacionDestino = (String) destino.get("TipoUbicacion");
            String idUbicacionDestino = (String) destino.get("IDUbicacion");
            String rfcRemitenteDestinatarioDestino = (String) destino.get("RFCRemitenteDestinatario");
            String fechaHoraSalidaLlegadaDestino = (String) destino.get("FechaHoraSalidaLlegada");
            Long distanciaDestino = (Long) destino.get("DistanciaRecorrida");

            JSONObject domicilioDestino = (JSONObject) destino.get("Domicilio");

            System.out.println(tipoUbicacion);
            System.out.println(idUbicacion);

            System.out.println(rfcRemitenteDestinatario);

            System.out.println(fechaHoraSalidaLlegada);

            System.out.println(domicilioOrigen.get("Estado"));
            System.out.println(domicilioOrigen.get("Pais"));
            System.out.println(domicilioOrigen.get("CodigoPostal"));

            System.out.println(tipoUbicacionDestino);
            System.out.println(idUbicacionDestino);

            System.out.println(rfcRemitenteDestinatarioDestino);

            System.out.println(fechaHoraSalidaLlegadaDestino);
            System.out.println(distanciaDestino);

            System.out.println(domicilioDestino.get("Estado"));
            System.out.println(domicilioDestino.get("Pais"));
            System.out.println(domicilioDestino.get("CodigoPostal"));

            //mercancias
            System.out.println("MERCANCIAS");

            JSONObject mercancias = (JSONObject) cartaPorte.get("Mercancias");
            String pesoBrutoTotal = (String) mercancias.get("PesoBrutoTotal");
            String unidadPeso = (String) mercancias.get("UnidadPeso");
            String pesoNetoTotal = (String) mercancias.get("PesoNetoTotal");
            Long numTotalMercancias = (Long) mercancias.get("NumTotalMercancias");

            System.out.println(pesoBrutoTotal);

            System.out.println(unidadPeso);

            System.out.println(pesoNetoTotal);
            System.out.println(numTotalMercancias);

            JSONArray mercanciasArray = (JSONArray) mercancias.get("Mercancia");

            for (int i = 0; i < mercanciasArray.size(); i++) {
                JSONObject mercanciaObjeto = (JSONObject) mercanciasArray.get(i);
                String bienesTransp = mercanciaObjeto.get("BienesTransp").toString();
                String descripcion = mercanciaObjeto.get("Descripcion").toString();
                String cantidad = mercanciaObjeto.get("Cantidad").toString();
                String claveUnidad = mercanciaObjeto.get("ClaveUnidad").toString();
                String unidad = mercanciaObjeto.get("Unidad").toString();
                String pesoEnKg = mercanciaObjeto.get("PesoEnKg").toString();

                System.out.println("Mercancia NUm: " + (i + 1));
                System.out.println(bienesTransp);
                System.out.println(descripcion);
                System.out.println(cantidad);
                System.out.println(claveUnidad);
                System.out.println(unidad);
                System.out.println(pesoEnKg);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablaViajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaViajesMouseClicked
        // TODO add your handling code here:
        columna = tablaViajes.getColumnModel().getColumnIndexAtX(evt.getX());
        fila = evt.getY() / tablaViajes.getRowHeight();

        if (columna <= tablaViajes.getColumnCount() && columna >= 0 && fila <= tablaViajes.getRowCount() && fila >= 0) {
            Object objeto = tablaViajes.getValueAt(fila, columna);
            if (objeto instanceof JButton) {
                ((JButton) objeto).doClick();
                JButton botones = (JButton) objeto;

                String datosFila[] = new String[3];
                int filaSeleccionada = tablaViajes.getSelectedRow();
                datosFila[0] = tablaViajes.getValueAt(filaSeleccionada, 0).toString();

                if (botones.getName().equals("btnVer")) {
                    // JOptionPane.showMessageDialog(null, "se preisono ver");
                    //Obtenemos el nombre del json de la primera columna de la tabla
                    DetallesViaje datos = new DetallesViaje();
                    datos.setLocationRelativeTo(null);
                    datos.setVisible(true);

                    if (filaSeleccionada >= 0) {

                        File carpeta = new File(nombreCarpeta);
                        String[] listado = carpeta.list();
                        DetallesViaje.tituloJson.setText(datosFila[0]);
                        verDetalles(nombreCarpeta + "/" + listado[filaSeleccionada]);

                    }

                } else if (botones.getName().equals("btnCargar")) {
                    //JOptionPane.showMessageDialog(null, "se preisono cargar");
                    CargarDocumentos cargar = new CargarDocumentos();
                    cargar.setLocationRelativeTo(null);
                    cargar.setVisible(true);
                    if (filaSeleccionada >= 0) {
                        CargarDocumentos.text_idViaje.setText(datosFila[0]);
                    }
                }

            }
        }
    }//GEN-LAST:event_tablaViajesMouseClicked

    private void menuDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDescargarActionPerformed
        // TODO add your handling code here:      
        botonVer.setName("btnVer");
        botonCargar.setName("btnCargar");

        String urlRestService = Constantes.URL_REST_SERVICE;
        String passUser = Constantes.USER_PASS_GEPP;

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);

        WebResource webResource = client.resource(urlRestService);
        ClientResponse response = webResource.path("/verViajes")
                .queryParam("wsUser", Constantes.USER_GEPP)
                .queryParam("numProveedor", Constantes.NUMERO_PROVEEDOR)
                .get(ClientResponse.class);

        System.out.println("Estatus respuesta: " + response.getStatus());
        System.out.println("");

        TripsResponse output = response.getEntity(TripsResponse.class);

        // dato1.setText("Código Error: " + output.getCodigoError() + "\nMensaje Error: " + output.getMensajeError() + "\nzipBase64: " + output.getZipBase64());
        System.out.println("Código Error: " + output.getCodigoError());
        System.out.println("Mensaje Error: " + output.getMensajeError());
        System.out.println("zipBase64: " + output.getZipBase64());

        if (output.getZipBase64() != null) {
            try {
                //Desencriptacion de archivo
                String archivoDesencriptado = (new Encriptador()).desencriptar(output.getZipBase64(), passUser);

                //Obtener los bytes desde base 64
                byte[] bytes = Base64.getDecoder().decode(archivoDesencriptado);

                //Escribir archivo
                try {
                    //FileOutputStream fos = new FileOutputStream("C:\\\\ProgramaGEPP/ZIPs/viajes_" + formattedDate + ".zip");
                    FileOutputStream fos = new FileOutputStream("viajes.zip");

                    fos.write(bytes);
                    fos.close();
                    System.out.println("Archivo creado exitosamente.");
                    JOptionPane.showMessageDialog(null, "Descarga completada.");
                    //Mostra en la
                    MostrarTabla(nombreCarpeta);
                } catch (Exception e) {
                    System.out.println("Ocurrió un error al grabar el archivo.");
                    JOptionPane.showMessageDialog(null, "Erro al crear el archivo ZIP");
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No hay archivo zipBase64 para trabajar.");
        }
    }//GEN-LAST:event_menuDescargarActionPerformed

    private void menuEditarConstantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarConstantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuEditarConstantesActionPerformed

    public static void verDetalles(String rutaJson) {

        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(rutaJson));

            JSONObject cabecera = (JSONObject) a.get(0);

            String folioGEPP = (String) cabecera.get("FolioGEPP");
            String fechaGeneracion = (String) cabecera.get("FechaGeneracion");
            String usoCfdi = (String) cabecera.get("UsoCFDI");

            JSONObject documentosGepp = (JSONObject) cabecera.get("DocumentosGEPP");
            JSONArray docsGepp = (JSONArray) documentosGepp.get("DocsGEPP");

            JSONArray ubicacionesGepp = (JSONArray) cabecera.get("UbicacionesGEPP");

            System.out.println(folioGEPP);
            DetallesViaje.folioGEPP_text.setText(folioGEPP);
            DetallesViaje.fechaGen_text.setText(fechaGeneracion);
            DetallesViaje.usoCFDI_text.setText(usoCfdi);

            System.out.println(fechaGeneracion);
            System.out.println(usoCfdi);
            //   DetallesViaje.vistaDatos.append("DocumentosGEPP:");

            if (docsGepp == null) {

            } else {

                for (Object string : docsGepp) {
                    // System.out.println(string);
                    DetallesViaje.docsGEPP_text.append(string.toString());
                }

            }

            if (ubicacionesGepp == null) {

            } else {
                Iterator<String> iteratorUbicaciones = ubicacionesGepp.iterator();
                while (iteratorUbicaciones.hasNext()) {
                    System.out.println(iteratorUbicaciones.next());
                }
            }

            JSONObject carta = (JSONObject) a.get(1);

            JSONObject cartaPorte = (JSONObject) carta.get("CartaPorte");

            String version = (String) cartaPorte.get("Version");
            String transpInternac = (String) cartaPorte.get("TranspInternac");
            Long totalDistRec = (Long) cartaPorte.get("TotalDistRec");

            System.out.println(version);
            System.out.println(transpInternac);
            System.out.println(totalDistRec);

            DetallesViaje.version_text.setText(version);
            DetallesViaje.traspInter_text.setText(transpInternac);
            DetallesViaje.totalDist_text.setText(totalDistRec.toString());

            JSONObject ubicaciones = (JSONObject) cartaPorte.get("Ubicaciones");
            JSONArray ubicacionesArray = (JSONArray) ubicaciones.get("Ubicacion");

            //UBICACION origen
            JSONObject origen = (JSONObject) ubicacionesArray.get(0);

            String tipoUbicacion = (String) origen.get("TipoUbicacion");
            String idUbicacion = (String) origen.get("IDUbicacion");
            String rfcRemitenteDestinatario = (String) origen.get("RFCRemitenteDestinatario");
            String fechaHoraSalidaLlegada = (String) origen.get("FechaHoraSalidaLlegada");
            JSONObject domicilioOrigen = (JSONObject) origen.get("Domicilio");

            //UBICACION destino
            JSONObject destino = (JSONObject) ubicacionesArray.get(1);
            String tipoUbicacionDestino = (String) destino.get("TipoUbicacion");
            String idUbicacionDestino = (String) destino.get("IDUbicacion");
            String rfcRemitenteDestinatarioDestino = (String) destino.get("RFCRemitenteDestinatario");
            String fechaHoraSalidaLlegadaDestino = (String) destino.get("FechaHoraSalidaLlegada");
            Long distanciaDestino = (Long) destino.get("DistanciaRecorrida");

            String estadoOrigen = domicilioOrigen.get("Estado").toString();
            String paisOrigen = domicilioOrigen.get("Pais").toString();
            String cpOrigen = domicilioOrigen.get("CodigoPostal").toString();

            JSONObject domicilioDestino = (JSONObject) destino.get("Domicilio");

            System.out.println(tipoUbicacion);

            System.out.println(idUbicacion);

            System.out.println(rfcRemitenteDestinatario);

            System.out.println(fechaHoraSalidaLlegada);

            System.out.println(domicilioOrigen.get("Estado"));
            System.out.println(domicilioOrigen.get("Pais"));
            System.out.println(domicilioOrigen.get("CodigoPostal"));

            DetallesViaje.tipoUbi_text.setText(tipoUbicacion);
            DetallesViaje.idUbi_text.setText(idUbicacion);
            DetallesViaje.idUbi_text.setText(rfcRemitenteDestinatario);
            DetallesViaje.fechaHora_text.setText(fechaHoraSalidaLlegada);

            DetallesViaje.estado_text.setText(estadoOrigen);
            DetallesViaje.pais_text.setText(paisOrigen);
            DetallesViaje.cp_text.setText(cpOrigen);

            DetallesViaje.estado_text.setText(estadoOrigen);

            System.out.println(tipoUbicacionDestino);
            System.out.println(idUbicacionDestino);

            System.out.println(rfcRemitenteDestinatarioDestino);

            System.out.println(fechaHoraSalidaLlegadaDestino);
            System.out.println(distanciaDestino);

            System.out.println(domicilioDestino.get("Estado"));
            System.out.println(domicilioDestino.get("Pais"));
            System.out.println(domicilioDestino.get("CodigoPostal"));

            //mercancias
            System.out.println("MERCANCIAS");

            JSONObject mercancias = (JSONObject) cartaPorte.get("Mercancias");
            String pesoBrutoTotal = (String) mercancias.get("PesoBrutoTotal");
            String unidadPeso = (String) mercancias.get("UnidadPeso");
            String pesoNetoTotal = (String) mercancias.get("PesoNetoTotal");
            Long numTotalMercancias = (Long) mercancias.get("NumTotalMercancias");

            System.out.println(pesoBrutoTotal);

            System.out.println(unidadPeso);

            System.out.println(pesoNetoTotal);
            System.out.println(numTotalMercancias);

            JSONArray mercanciasArray = (JSONArray) mercancias.get("Mercancia");

            for (int i = 0; i < mercanciasArray.size(); i++) {
                JSONObject mercanciaObjeto = (JSONObject) mercanciasArray.get(i);
                String bienesTransp = mercanciaObjeto.get("BienesTransp").toString();
                String descripcion = mercanciaObjeto.get("Descripcion").toString();
                String cantidad = mercanciaObjeto.get("Cantidad").toString();
                String claveUnidad = mercanciaObjeto.get("ClaveUnidad").toString();
                String unidad = mercanciaObjeto.get("Unidad").toString();
                String pesoEnKg = mercanciaObjeto.get("PesoEnKg").toString();

                System.out.println("Mercancia NUm: " + (i + 1));
                System.out.println(bienesTransp);
                System.out.println(descripcion);
                System.out.println(cantidad);
                System.out.println(claveUnidad);
                System.out.println(unidad);
                System.out.println(pesoEnKg);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
            java.util.logging.Logger.getLogger(obtenerViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(obtenerViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(obtenerViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(obtenerViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                obtenerViajes principal = new obtenerViajes();
                principal.setLocationRelativeTo(null);
                principal.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_consultarViajes;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuConsultar;
    private javax.swing.JMenuItem menuDescargar;
    private javax.swing.JMenuItem menuEditarConstantes;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelEncabezado;
    public static javax.swing.JTable tablaViajes;
    // End of variables declaration//GEN-END:variables
}
