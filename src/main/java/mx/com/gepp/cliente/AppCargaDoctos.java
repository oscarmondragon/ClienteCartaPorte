package mx.com.gepp.cliente;

import java.io.File;
import java.io.FileInputStream;

import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import mx.com.gepp.beans.RecepCartaPorteGepp;
import mx.com.gepp.beans.ReceptionResponse;

import mx.com.gepp.utilities.Encriptador;
import mx.com.gepp.utilities.Constantes;

public class AppCargaDoctos
{
	public static void main(String[] args) {
		String urlRestService = Constantes.URL_REST_SERVICE;
		String passUser = Constantes.USER_PASS_GEPP;
		
		Encriptador encoder = new Encriptador();
		
		//>Integramos la url
		String url = "https://www.google.com.mx/";
		String urlEncriptada = null;
		
		//Encriptacion de URL
        try {
        	urlEncriptada = new String(encoder.encriptar(url, passUser));
        } catch(Exception e) {
        	System.out.println("Ocurrió un error al encriptar el archivo.");
        }

		//>Integramos el archivo
		String encodedBase64 = null;
		String archivoEncriptado = null;
		
		//Conversion de archivo zip a Base64
		try {
			File originalFile = new File("C:\\20350.zip");
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.getEncoder().encode(bytes));
            fileInputStreamReader.close();
		} catch(Exception e) {
			System.out.println("Ocurrió un error al convertir a Base64.");
		}
		
		//Encriptacion de archivo
        try {
        	archivoEncriptado = new String(encoder.encriptar(encodedBase64, passUser));
        } catch(Exception e) {
        	System.out.println("Ocurrió un error al encriptar el archivo.");
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
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
        
		WebResource webResource = client.resource(urlRestService);
		
		ClientResponse response = webResource.path("/recepcionCartaPorte")
				                             .type("application/json")
				                             .post(ClientResponse.class, cartaPorte);
		
		System.out.println("Estatus respuesta: " + response.getStatus());
		System.out.println("");
		
		ReceptionResponse output = response.getEntity(ReceptionResponse.class);
		
		System.out.println("Código Error: " + output.getCodigoError());
		System.out.println("Mensaje Error: " + output.getMensajeError());
	}
}
