/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.utilities;

/**
 *
 * @author dspace
 */

package com.decodigo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author oscarmondragon
 */

public class Descomprimir {
    List listaArchivos;
    private String archivo_ZIP ;
    private String ruta_archivo ;
    

    public void unZip(String archivoZip, String rutaSalida) {
        
        this.archivo_ZIP = archivoZip;
        this.ruta_archivo = rutaSalida;
        
        byte[] buffer = new byte[1024];
        try {
            File folder = new File(this.ruta_archivo);
            if (!folder.exists()) {
                folder.mkdir();
            }

            ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip));
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String nombreArchivo = ze.getName();
                File archivoNuevo = new File(rutaSalida + File.separator + nombreArchivo);
                System.out.println("archivo descomprimido : " + archivoNuevo.getName());
                new File(archivoNuevo.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(archivoNuevo);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            System.out.println("Listo se ha descomprimido el ZIP");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}