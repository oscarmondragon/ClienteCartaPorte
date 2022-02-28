/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.cliente;

import java.io.FileOutputStream;

import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import static javafx.scene.input.KeyCode.T;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
//import jdk.nashorn.internal.parser.JSONParser;

import mx.com.gepp.beans.TripsResponse;
import mx.com.gepp.beans.RenderTabla;

import mx.com.gepp.utilities.Encriptador;
import mx.com.gepp.utilities.Constantes;
import mx.com.gepp.utilities.Descomprimir;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 *
 * @author dspace
 */
public class obtenerViajes extends javax.swing.JFrame {

    TableModel tm;
    static TableRowSorter<TableModel> tr;

    //obtener dia de hoy
//    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
//    Calendar calendar = Calendar.getInstance();
//    Date dateObj = calendar.getTime();
//    String formattedDate = dtf.format(dateObj);
    //botones de tabla ver viajes
    static JButton botonVer = new JButton("Ver JSON");
    static JButton botonCargar = new JButton("Cargar");

    //Nombre de carpeta y zip
    // String nombreZip = "viajes_" + formattedDate + ".zip";
    //String nombreCarpeta = "viajes_" + formattedDate;
    String nombreZip = "viajes.zip";
    String nombreCarpeta = "viajes";
    Descomprimir descompresorArchivo = new Descomprimir();

    public static int columna, fila;

    public obtenerViajes() {
        initComponents();
        botonVer.setName("btnVer");
        botonCargar.setName("btnCargar");
        // String nombreZip = "C:\\\\ProgramaGEPP/ZIPs/viajes_" + formattedDate + ".zip";
        // String nombreCarpeta = "C:\\\\ProgramaGEPP/viajes/viajes_" + formattedDate;

        //Cargar constantes
        //Asignar valores a constantes desde el archivo JSON
        try {

            Object ob = new JSONParser().parse(new FileReader("constantes.json"));

            JSONObject constantes = (JSONObject) ob;
            // System.out.println(constantes);
            Constantes.asignarConstantes(constantes);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Eliminamos los archivos de la carpeta de viajes si existe para evitar duplicados
        File carpeta = new File("viajes");
        System.out.println("car:" + nombreCarpeta);
        File[] files = carpeta.listFiles();
        if (carpeta.exists()) {
            for (File f : files) {
                f.delete();
                System.out.println("Eliminados");
            }
        }
        // Descomprimir archivo de viajes y mostrar tabla al iniciar programa
           descompresorArchivo.unZip(nombreZip, nombreCarpeta);
           
           //Cambiamos nombres de los archivos json
        String[] listado = carpeta.list();
        JSONParser parser = new JSONParser();
        File[] archivos = carpeta.listFiles();

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {

            for (File archivo1 : archivos) {
                try {
                    FileReader leerFolio = new FileReader(carpeta + "/" + archivo1.getName());
                    JSONArray a = (JSONArray) parser.parse(leerFolio);
                    System.out.println(carpeta + "/" + archivo1.getName());
                    JSONObject cabecera = (JSONObject) a.get(0);
                    String folioGEPP = (String) cabecera.get("FolioGEPP");
                    leerFolio.close();
                    String nvo = archivo1.getParent() + "/" + folioGEPP + ".json";
                    System.out.println("nuevo:" + nvo);
                    File archivo = new File(nvo);
                    boolean correcto = archivo1.renameTo(archivo);
                    if (correcto == true) {
                        System.out.println("Se cambio");
                    } else {
                        System.out.println("Error");
                    }
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
          MostrarTabla(nombreCarpeta);
    }

    public static void MostrarTabla(String nombreCarpeta) {

        DefaultTableModel modeloViajes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
       

        String[] columnas = {"FolioGEPP", "Fecha Generación", "Estado", "Ver datos", "Cargar documentos"};

        modeloViajes.setColumnIdentifiers(columnas);
        obtenerViajes.tablaViajes.setDefaultRenderer(Object.class, new RenderTabla());

        tablaViajes.setModel(modeloViajes);

        obtenerViajes.tablaViajes.setRowHeight(40);

        Object[] datos = new Object[5];

        File carpeta = new File(nombreCarpeta);
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {
            JSONParser parser = new JSONParser();

            for (String listado1 : listado) {

                try {
                    
                    FileReader lectorJson= new FileReader(nombreCarpeta + "/" + listado1);
                    JSONArray a = (JSONArray) parser.parse(lectorJson);
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
                                //System.out.println("Linea " + numeroDeLinea + ": " + linea);
                                contiene = true;
                                datos[2] = "Enviado";
                            }
                            numeroDeLinea++; //se incrementa el contador de líneas
                        }
                        if (!contiene) { //si el archivo no contienen el texto se muestra un mensaje indicándolo
                            datos[2] = "Pendiente";
                            // System.out.println(folioGEPP + " no se ha encontrado en el archivo");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println(e.toString());
                    } catch (NullPointerException e) {
                        //System.out.println(e.toString() + "No ha seleccionado ningún archivo");
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        if (entrada != null) {
                            entrada.close();
                        }
                        lectorJson.close();
                    }

                    //Cargar datos a la tabla
                    datos[0] = folioGEPP;
                    datos[1] = fechaGeneracion;

                    datos[3] = botonVer;
                    datos[4] = botonCargar;
                    modeloViajes.addRow(datos);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //Hacemos que la tabla se pueda ordenar
        tr = new TableRowSorter<>(modeloViajes);
        tablaViajes.setRowSorter(tr);

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
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaViajes = new javax.swing.JTable();
        buscar_txt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuConsultar = new javax.swing.JMenu();
        menuDescargar = new javax.swing.JMenuItem();
        smenuEditar = new javax.swing.JMenu();
        menuSalir = new javax.swing.JMenuItem();
        submenuSalir = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jLabel2.setText("jLabel2");

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(872, 780));

        panelEncabezado.setBackground(new java.awt.Color(204, 204, 204));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("CONSULTA DE VIAJES DISPONIBLES");

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablaViajes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaViajes.setFont(new java.awt.Font("Cantarell", 0, 16)); // NOI18N
        tablaViajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaViajes.setCellSelectionEnabled(true);
        tablaViajes.setColumnSelectionAllowed(true);
        tablaViajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaViajesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaViajes);

        buscar_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar_txtActionPerformed(evt);
            }
        });

        jLabel3.setText("Filtro:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(buscar_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 872, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
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

        smenuEditar.setText("Opciones");

        menuSalir.setText("Editar constantes");
        menuSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSalirMouseClicked(evt);
            }
        });
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        smenuEditar.add(menuSalir);

        submenuSalir.setText("Salir");
        submenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submenuSalirActionPerformed(evt);
            }
        });
        smenuEditar.add(submenuSalir);

        jMenuBar1.add(smenuEditar);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaViajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaViajesMouseClicked
        // TODO add your handling code here:
        columna = tablaViajes.getColumnModel().getColumnIndexAtX(evt.getX());
        fila = evt.getY() / tablaViajes.getRowHeight();

        if (columna <= tablaViajes.getColumnCount() && columna >= 0 && fila <= tablaViajes.getRowCount() && fila >= 0) {
            Object objeto = tablaViajes.getValueAt(fila, columna);
            if (objeto instanceof JButton) {
                ((JButton) objeto).doClick();
                JButton botones = (JButton) objeto;

                String datosFila[] = new String[5];
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
                        verDetalles(nombreCarpeta + "/" + datosFila[0] + ".json");
                        System.out.println(listado[filaSeleccionada]);

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

        //Nombres de los botones de la tabla principal
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

                    // MostrarTabla(nombreCarpeta);
                } catch (Exception e) {
                    System.out.println("Ocurrió un error al grabar el archivo.");
                    JOptionPane.showMessageDialog(null, "Erro al crear el archivo ZIP");
                }
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("No hay archivo zipBase64 para trabajar.");
        }
        //Eliminamos los archivos de la carpeta de viajes si existe para evitar duplicados
        File carpeta = new File("viajes");
        System.out.println("car:" + nombreCarpeta);
        File[] files = carpeta.listFiles();
        if (carpeta.exists()) {
            for (File f : files) {
                f.delete();
                System.out.println("Eliminados");
            }
        }
        //Descomprimimos nuevo zip descargado y mostramos tabla
        descompresorArchivo.unZip(nombreZip, nombreCarpeta);

        //Cambiamos nombres de los archivos json
        String[] listado = carpeta.list();
        JSONParser parser = new JSONParser();
        File[] archivos = carpeta.listFiles();

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return;
        } else {

            for (File archivo1 : archivos) {
                try {
                    FileReader leerFolio = new FileReader(carpeta + "/" + archivo1.getName());
                    JSONArray a = (JSONArray) parser.parse(leerFolio);
                    System.out.println(carpeta + "/" + archivo1.getName());
                    JSONObject cabecera = (JSONObject) a.get(0);
                    String folioGEPP = (String) cabecera.get("FolioGEPP");
                    leerFolio.close();
                    String nvo = archivo1.getParent() + "/" + folioGEPP + ".json";
                    System.out.println("nuevo:" + nvo);
                    File archivo = new File(nvo);
                    boolean correcto = archivo1.renameTo(archivo);
                    if (correcto == true) {
                        System.out.println("Se cambio");
                    } else {
                        System.out.println("Error");
                    }
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        MostrarTabla(nombreCarpeta);
    }//GEN-LAST:event_menuDescargarActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        //Mostrar ventana
        EditarConstantes vistaConstantes = new EditarConstantes();
        vistaConstantes.setLocationRelativeTo(null);
        vistaConstantes.setVisible(true);

        //Leer archivo constantes y escribir en formulario
        try {
            // TODO add your handling code here:
            Object ob = new JSONParser().parse(new FileReader("constantes.json"));

            JSONObject constantes = (JSONObject) ob;

            EditarConstantes.url_text.setText(constantes.get("url").toString());
            EditarConstantes.usuario_text.setText(constantes.get("user").toString());
            EditarConstantes.password_text.setText(constantes.get("password").toString());
            EditarConstantes.numProveedor_text.setText(constantes.get("numeroProveedor").toString());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSalirMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_menuSalirMouseClicked

    private void buscar_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar_txtActionPerformed
        // TODO add your handling code here:
        String filtro = buscar_txt.getText();
        if (!filtro.equals("")) {
            tr.setRowFilter(RowFilter.regexFilter(filtro));
        } else {
            tr.setRowFilter(null);
        }
    }//GEN-LAST:event_buscar_txtActionPerformed

    private void submenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submenuSalirActionPerformed
        // TODO add your handling code here:
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta seguro de cerrar el programa?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (respuesta) {
            case JOptionPane.YES_OPTION:

                System.exit(0);

                break;
            case JOptionPane.NO_OPTION:
                break;
            case JOptionPane.CLOSED_OPTION:
                break;
            default:
                break;
        }
    }//GEN-LAST:event_submenuSalirActionPerformed

    public static void verDetalles(String rutaJson) {

        JSONParser parser = new JSONParser();

        try {

            //Declaramos como vacias todas las variables
            //cabecera
            String oc = "";
            String folioGEPP = "";
            String fechaGeneracion = "";
            String usoCfdi = "";
            String version = "";
            String transpInternac = "";
            Long totalDistRec = null;
            //Ubicaciones origen
            String tipoUbicacion = "";
            String idUbicacion = "";
            String rfcRemitenteDestinatario = "";
            String fechaHoraSalidaLlegada = "";
            Long distanciaRecorridaOrigen = null;
            String calleOrigen = "";
            String numeroExteriorOrigen = "";
            String coloniaOrigen = "";
            String localidadOrigen = "";
            String municipioOrigen = "";
            String estadoOrigen = "";
            String paisOrigen = "";
            String cpOrigen = "";
            //Ubicaciones destino
            String tipoUbicacionDestino = "";
            String idUbicacionDestino = "";
            String rfcRemitenteDestinatarioDestino = "";
            String fechaHoraSalidaLlegadaDestino = "";
            Long distanciaDestino = null;
            String calleDestino = "";
            String numeroExteriorDestino = "";
            String coloniaDestino = "";
            String localidadDestino = "";
            String municipioDestino = "";
            String estadoDestino = "";
            String paisDestino = "";
            String cpDestino = "";
            //MERCANCIAS
            String pesoBrutoTotal = "";
            String unidadPeso = "";
            String pesoNetoTotal = "";
            Long numTotalMercancias = null;

            //Creamos el arreglo de JSON con la ruta recibida
            JSONArray a = (JSONArray) parser.parse(new FileReader(rutaJson));
            //EL Json Array esta compuesto por 2: Cabecera y Carta Porte
            //Creamos el objeto de la cabecera
            JSONObject cabecera = (JSONObject) a.get(0);
            //asignamos valores de la cabecera solo si no son null
            
               //Enviamos cabecera a vista DetallesViaje
             oc = (String) cabecera.get("OC");
             
             if (oc != null) {
                DetallesViaje.oc_text.setText(oc);
            }
             folioGEPP = cabecera.get("FolioGEPP").toString();
            if (folioGEPP != null) {
                 DetallesViaje.folioGEPP_text.setText(folioGEPP);
            }
            fechaGeneracion = cabecera.get("FechaGeneracion").toString();
            
            if (fechaGeneracion != null) {
                DetallesViaje.fechaGen_text.setText(fechaGeneracion);
            }
            usoCfdi = cabecera.get("UsoCFDI").toString();
            if (usoCfdi != null) {
                 DetallesViaje.usoCFDI_text.setText(usoCfdi);
            }

            //Los DocumentosGepp vienen en un objeto json
            JSONObject documentosGepp = (JSONObject) cabecera.get("DocumentosGEPP");
            //los documentos estan en un arreglo, creamos el arreglo
            JSONArray docsGepp = (JSONArray) documentosGepp.get("DocsGEPP");

            //EL objeto esta compuesto por dos arreglos: ubicacion origen y ubicacion destino
            JSONArray ubicacionesGepp = (JSONArray) cabecera.get("UbicacionesGEPP");

         
           // DetallesViaje.oc_text.setText(oc);
           
           
          

            //Comprobamos si hay documentos
            if (docsGepp == null) {

            } else {
                for (Object string : docsGepp) {
                    // Enviamos docs a vista DetallesViaje
                    DetallesViaje.docsGEPP_text.append(string.toString());
                }

            }
            //asignar valores a campos de ubicacionesGepp
            if(ubicacionesGepp != null ){
                  DetallesViaje.OR_text.setText(ubicacionesGepp.get(1).toString());
            
             DetallesViaje.DE_text.setText(ubicacionesGepp.get(0).toString());
            }
          


//            if (ubicacionesGepp == null) {
//
//            } else {
//                Iterator<String> iteratorUbicaciones = ubicacionesGepp.iterator();
//                while (iteratorUbicaciones.hasNext()) {
//                    System.out.println(iteratorUbicaciones.next());
//                  //  DetallesViaje.or_text.append(string.toString());
//
//                }
//            }
            //Obtenemos el objeto Json para Carta porte del arreglo que viene en el archivo .json
            JSONObject carta = (JSONObject) a.get(1);
            //Creamos objeto CartaPorte
            JSONObject cartaPorte = (JSONObject) carta.get("CartaPorte");

            //Escribimos valores faltantes en cabecera
            if (cartaPorte.get("Version").toString() != null) {
                version = (String) cartaPorte.get("Version");
            }
            if (cartaPorte.get("TranspInternac").toString() != null) {
                transpInternac = (String) cartaPorte.get("TranspInternac");
            }
            if ((Long) cartaPorte.get("TotalDistRec") != null) {
                totalDistRec = (Long) cartaPorte.get("TotalDistRec");
            }
            //Enviamos datos a vista DetallesViaje
            DetallesViaje.version_text.setText(version);
            DetallesViaje.traspInter_text.setText(transpInternac);
            if (totalDistRec == null) {
                DetallesViaje.totalDist_text.setText("");
            } else {
                DetallesViaje.totalDist_text.setText(totalDistRec.toString());

            }

            //Creamos el objeto y el arreglo para las ubicaciones
            //EL objeto esta compuesto por dos arreglos: ubicacion origen y ubicacion destino
            JSONObject ubicaciones = (JSONObject) cartaPorte.get("Ubicaciones");
            JSONArray ubicacionesArray = (JSONArray) ubicaciones.get("Ubicacion");

            //UBICACION origen
            JSONObject origen = (JSONObject) ubicacionesArray.get(0);

            if ((String) origen.get("TipoUbicacion") != null) {
                tipoUbicacion = (String) origen.get("TipoUbicacion");
            }
            if ((String) origen.get("IDUbicacion") != null) {
                idUbicacion = (String) origen.get("IDUbicacion");
            }
            if ((String) origen.get("RFCRemitenteDestinatario") != null) {
                rfcRemitenteDestinatario = (String) origen.get("RFCRemitenteDestinatario");
            }
            if ((String) origen.get("FechaHoraSalidaLlegada") != null) {
                fechaHoraSalidaLlegada = (String) origen.get("FechaHoraSalidaLlegada");
            }

            if ((Long) origen.get("DistanciaRecorrida") != null) {
                distanciaRecorridaOrigen = (Long) origen.get("DistanciaRecorrida");
            }

            //objeto con el domicilio origen
            JSONObject domicilioOrigen = (JSONObject) origen.get("Domicilio");

            if ((String) domicilioOrigen.get("Calle") != null) {
                calleOrigen = (String) domicilioOrigen.get("Calle");
            }
            if ((String) domicilioOrigen.get("NumeroExterior") != null) {
                numeroExteriorOrigen = (String) domicilioOrigen.get("NumeroExterior");
            }
            if ((String) domicilioOrigen.get("Colonia") != null) {
                coloniaOrigen = (String) domicilioOrigen.get("Colonia");
            }
            if ((String) domicilioOrigen.get("Localidad") != null) {
                localidadOrigen = (String) domicilioOrigen.get("Localidad");
            }
            if ((String) domicilioOrigen.get("Municipio") != null) {
                municipioOrigen = (String) domicilioOrigen.get("Municipio");
            }
            if ((String) domicilioOrigen.get("Estado") != null) {
                estadoOrigen = (String) domicilioOrigen.get("Estado");
            }
            if ((String) domicilioOrigen.get("Pais") != null) {
                paisOrigen = (String) domicilioOrigen.get("Pais");
            }
            if ((String) domicilioOrigen.get("CodigoPostal") != null) {
                cpOrigen = (String) domicilioOrigen.get("CodigoPostal");
            }

            //UBICACION destino
            JSONObject destino = (JSONObject) ubicacionesArray.get(1);
            if ((String) destino.get("TipoUbicacion") != null) {
                tipoUbicacionDestino = (String) destino.get("TipoUbicacion");
            }
            if ((String) destino.get("IDUbicacion") != null) {
                idUbicacionDestino = (String) destino.get("IDUbicacion");
            }
            if ((String) destino.get("RFCRemitenteDestinatario") != null) {
                rfcRemitenteDestinatarioDestino = (String) destino.get("RFCRemitenteDestinatario");
            }
            if ((String) destino.get("FechaHoraSalidaLlegada") != null) {
                fechaHoraSalidaLlegadaDestino = (String) destino.get("FechaHoraSalidaLlegada");
            }
            if ((Long) destino.get("DistanciaRecorrida") != null) {
                distanciaDestino = (Long) destino.get("DistanciaRecorrida");
            }

            //objeto con el domicilio DESTINO
            JSONObject domicilioDestino = (JSONObject) destino.get("Domicilio");

            if ((String) domicilioDestino.get("Calle") != null) {
                calleDestino = (String) domicilioDestino.get("Calle");
            }
            if ((String) domicilioDestino.get("NumeroExterior") != null) {
                numeroExteriorDestino = (String) domicilioDestino.get("NumeroExterior");
            }
            if ((String) domicilioDestino.get("Colonia") != null) {
                coloniaDestino = (String) domicilioDestino.get("Colonia");
            }
            if ((String) domicilioDestino.get("Localidad") != null) {
                localidadDestino = (String) domicilioDestino.get("Localidad");
            }
            if ((String) domicilioDestino.get("Municipio") != null) {
                municipioDestino = (String) domicilioDestino.get("Municipio");
            }
            if ((String) domicilioDestino.get("Estado") != null) {
                estadoDestino = (String) domicilioDestino.get("Estado");
            }
            if ((String) domicilioDestino.get("Pais") != null) {
                paisDestino = (String) domicilioDestino.get("Pais");
            }
            if ((String) domicilioDestino.get("CodigoPostal") != null) {
                cpDestino = (String) domicilioDestino.get("CodigoPostal");
            }

            //Enviar datos de ubicacion origen a vista detalles 
            DetallesViaje.tipoUbi_text.setText(tipoUbicacion);
            DetallesViaje.idUbi_text.setText(idUbicacion);
            DetallesViaje.rfcUbi_text.setText(rfcRemitenteDestinatario);
            DetallesViaje.fechaHora_text.setText(fechaHoraSalidaLlegada);
            if (distanciaRecorridaOrigen == null) {
                DetallesViaje.distanciaRec_text.setText("");
            } else {
                DetallesViaje.distanciaRec_text.setText(distanciaRecorridaOrigen.toString());

            }

            DetallesViaje.calle_text.setText(calleOrigen);
            DetallesViaje.numExt_text.setText(numeroExteriorOrigen);
            DetallesViaje.colonia_text.setText(coloniaOrigen);
            DetallesViaje.localidad_text.setText(localidadOrigen);
            DetallesViaje.municipio_text.setText(municipioOrigen);
            DetallesViaje.estado_text.setText(estadoOrigen);
            DetallesViaje.pais_text.setText(paisOrigen);
            DetallesViaje.cp_text.setText(cpOrigen);
            //Enviar datos de ubicacion destino a  vista detalles 
            DetallesViaje.tipoDestino_text.setText(tipoUbicacionDestino);
            DetallesViaje.idDestino_text.setText(idUbicacionDestino);
            DetallesViaje.rfcDestino_text.setText(rfcRemitenteDestinatarioDestino);
            DetallesViaje.fechaHoraDestino_text.setText(fechaHoraSalidaLlegadaDestino);

            if (distanciaDestino == null) {
                DetallesViaje.distaRecDestino_text.setText("");
            } else {
                DetallesViaje.distaRecDestino_text.setText(distanciaDestino.toString());

            }

            DetallesViaje.calleDestino_text.setText(calleDestino);
            DetallesViaje.numExtDes_text.setText(numeroExteriorDestino);
            DetallesViaje.coloniaDestino_text.setText(coloniaDestino);
            DetallesViaje.localidadDestino_text.setText(localidadDestino);
            DetallesViaje.municipioDestino_text.setText(municipioDestino);
            DetallesViaje.estadoDestino_text.setText(estadoDestino);
            DetallesViaje.paisDestino_text.setText(paisDestino);
            DetallesViaje.cpDestino_text.setText(cpDestino);

            //MERCANCIAS
            // System.out.println("MERCANCIAS");
            JSONObject mercancias = (JSONObject) cartaPorte.get("Mercancias");
            if ((String) mercancias.get("PesoBrutoTotal") != null) {
                pesoBrutoTotal = (String) mercancias.get("PesoBrutoTotal");
            }
            if ((String) mercancias.get("UnidadPeso") != null) {
                unidadPeso = (String) mercancias.get("UnidadPeso");
            }
            if ((String) mercancias.get("PesoNetoTotal") != null) {
                pesoNetoTotal = (String) mercancias.get("PesoNetoTotal");
            }
            if ((Long) mercancias.get("NumTotalMercancias") != null) {
                numTotalMercancias = (Long) mercancias.get("NumTotalMercancias");
            }

            //Enviar datos generales de mercancias a vista DetallesViaje
            DetallesViaje.pesoBruto_text.setText(pesoBrutoTotal);
            DetallesViaje.unidadPeso_text.setText(unidadPeso);
            DetallesViaje.unidadPeso_text.setText(unidadPeso);
            DetallesViaje.pesoNeto_text.setText(pesoNetoTotal);
            DetallesViaje.totalMercan_text.setText(numTotalMercancias.toString());

            //DATOS ESPECIFICOS POR MERCANCIA
            JSONArray mercanciasArray = (JSONArray) mercancias.get("Mercancia");

            //Inicializamos tabla Mercancias
            DefaultTableModel model = (DefaultTableModel) DetallesViaje.tbl_mercancias.getModel();

            for (Object string : mercanciasArray) {

                //DEclaramos variables default por si no vienen en el json
                JSONObject mercanciaObjeto = (JSONObject) string;
                String bienesTransp = "";
                String descripcion = "";
                String cantidad = "";
                String claveUnidad = "";
                String unidad = "";
                String materialPeligroso = "";
                String pesoEnKg = "";
                String valorMercancia = "";
                String moneda = "";
                String fraccionArancelaria = "";
                String uUIDComercioExt = "";

                //Asignamos solo los valores diferentes a null
                if (mercanciaObjeto.get("BienesTransp") != null) {
                    bienesTransp = mercanciaObjeto.get("BienesTransp").toString();
                }
                if (mercanciaObjeto.get("Descripcion") != null) {
                    descripcion = mercanciaObjeto.get("Descripcion").toString();
                }
                if (mercanciaObjeto.get("Cantidad") != null) {
                    cantidad = mercanciaObjeto.get("Cantidad").toString();
                }
                if (mercanciaObjeto.get("ClaveUnidad") != null) {
                    claveUnidad = mercanciaObjeto.get("ClaveUnidad").toString();
                }
                if (mercanciaObjeto.get("Unidad") != null) {
                    unidad = mercanciaObjeto.get("Unidad").toString();
                }
                if (mercanciaObjeto.get("MaterialPeligroso") != null) {
                    materialPeligroso = mercanciaObjeto.get("MaterialPeligroso").toString();
                }
                if (mercanciaObjeto.get("PesoEnKg") != null) {
                    pesoEnKg = mercanciaObjeto.get("PesoEnKg").toString();
                }
                if (mercanciaObjeto.get("ValorMercancia") != null) {
                    valorMercancia = mercanciaObjeto.get("ValorMercancia").toString();
                }
                if (mercanciaObjeto.get("Moneda") != null) {
                    moneda = mercanciaObjeto.get("Moneda").toString();
                }
                if (mercanciaObjeto.get("FraccionArancelaria") != null) {
                    fraccionArancelaria = mercanciaObjeto.get("FraccionArancelaria").toString();
                }
                if (mercanciaObjeto.get("UUIDComercioExt") != null) {
                    uUIDComercioExt = mercanciaObjeto.get("UUIDComercioExt").toString();
                }

                //Insertar valores en tabla
                model.addRow(new Object[]{bienesTransp, descripcion, cantidad, claveUnidad,
                    unidad, materialPeligroso, pesoEnKg, valorMercancia, moneda, fraccionArancelaria, uUIDComercioExt});

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(obtenerViajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
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
    private javax.swing.JTextField buscar_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuConsultar;
    private javax.swing.JMenuItem menuDescargar;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JMenu smenuEditar;
    private javax.swing.JMenuItem submenuSalir;
    public static javax.swing.JTable tablaViajes;
    // End of variables declaration//GEN-END:variables
}
