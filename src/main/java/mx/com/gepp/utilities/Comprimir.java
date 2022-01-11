/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author oscarmondragon
 */
public class Comprimir {
    
    private String NOMBRE_ARCHIVO;
    private String NOMBRE_ARCHIVO_ZIP;
    
    public void comprimir(String nombreArchivo, String nombreZip){
        
        this.NOMBRE_ARCHIVO = nombreArchivo;
        this.NOMBRE_ARCHIVO_ZIP = nombreZip;
         try {
            FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO_ZIP);
            ZipOutputStream zos = new ZipOutputStream(fos);
            
            File archivoLenguajes = new File(NOMBRE_ARCHIVO);
            FileInputStream fis = new FileInputStream(archivoLenguajes);
            
            ZipEntry ze = new ZipEntry(archivoLenguajes.getName());
            zos.putNextEntry(ze);
            
            byte[] bytes = new byte[1024];
            int longitud;
            
            while((longitud = fis.read(bytes)) >= 0){
                zos.write(bytes, 0, longitud);
            }
            
            zos.close();
            fis.close();
            fos.close();
            System.out.println("Se ha creado el ZIP exitosamente");
        } catch (IOException e) {
            System.err.println("Error -> " + e.getMessage());
        }
    }
    
}
