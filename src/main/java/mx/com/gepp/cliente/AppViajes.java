package mx.com.gepp.cliente;

import java.io.FileOutputStream;

import java.util.Base64;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import mx.com.gepp.beans.TripsResponse;

import mx.com.gepp.utilities.Encriptador;
import mx.com.gepp.utilities.Constantes;

public class AppViajes
{
	public static void main(String[] args) throws Exception {		
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
		
		System.out.println("Código Error: " + output.getCodigoError());
		System.out.println("Mensaje Error: " + output.getMensajeError());
		System.out.println("zipBase64: " + output.getZipBase64());
		
		if (output.getZipBase64() != null) {
			//Desencriptacion de archivo
			String archivoDesencriptado = (new Encriptador()).desencriptar(output.getZipBase64(), passUser);
			
			//Obtener los bytes desde base 64
			byte[] bytes = Base64.getDecoder().decode(archivoDesencriptado);
			
			//Escribir archivo
			try {
				FileOutputStream fos = new FileOutputStream("archivoSalida.zip");
	            fos.write(bytes);
	            fos.close();
	            
	            System.out.println("Archivo creado exitosamente.");
			} catch (Exception e) {
				System.out.println("Ocurrió un error al grabar el archivo.");
			}
		} else {
			System.out.println("No hay archivo zipBase64 para trabajar.");
		}
	}
}
