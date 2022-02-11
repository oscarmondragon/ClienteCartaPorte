/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import mx.com.gepp.cliente.CargarDocumentos;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author oscarmondragon
 */
public class Comprimir {

    private List<File> archivos;
    private String NOMBRE_ARCHIVO_ZIP;

    public void comprimir(List<File> archivos, String nombreZip) {
        
        this.archivos = archivos;
        this.NOMBRE_ARCHIVO_ZIP = nombreZip;
        
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(nombreZip);
            
            ZipOutputStream zipOut = new ZipOutputStream(fos);
		for (File srcFile : this.archivos) {
			//File fileToZip = new File(srcFile);
			FileInputStream fis = new FileInputStream(srcFile);
			ZipEntry zipEntry = new ZipEntry(srcFile.getName());
			zipOut.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		}
		zipOut.close();
		fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CargarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }

//        this.archivos = archivos;
//        this.NOMBRE_ARCHIVO_ZIP = nombreZip;
//        try {
//            new ZipFile(NOMBRE_ARCHIVO_ZIP).addFiles(archivos);
//            System.out.println("ZIP creado exitosamente");
//        } catch (ZipException ex) {
//            Logger.getLogger(CargarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

//    private String NOMBRE_ARCHIVO;
//    private String NOMBRE_ARCHIVO_ZIP;
//    
//    public void comprimir(String nombreArchivo, String nombreZip){
//        
//        this.NOMBRE_ARCHIVO = nombreArchivo;
//        this.NOMBRE_ARCHIVO_ZIP = nombreZip;
//         try {
//            FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO_ZIP);
//            ZipOutputStream zos = new ZipOutputStream(fos);
//            
//            File archivoLenguajes = new File(NOMBRE_ARCHIVO);
//            FileInputStream fis = new FileInputStream(archivoLenguajes);
//            
//            ZipEntry ze = new ZipEntry(archivoLenguajes.getName());
//            zos.putNextEntry(ze);
//            
//            byte[] bytes = new byte[1024];
//            int longitud;
//            
//            while((longitud = fis.read(bytes)) >= 0){
//                zos.write(bytes, 0, longitud);
//            }
//            
//            zos.close();
//            fis.close();
//            fos.close();
//            System.out.println("Se ha creado el ZIP exitosamente");
//        } catch (IOException e) {
//            System.err.println("Error -> " + e.getMessage());
//        }
//    }
}
