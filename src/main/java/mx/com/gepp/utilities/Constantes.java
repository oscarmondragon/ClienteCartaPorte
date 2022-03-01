package mx.com.gepp.utilities;

import org.json.simple.JSONObject;

public class Constantes {

    public static String URL_REST_SERVICE;
    public static String USER_GEPP;
    public static String USER_PASS_GEPP;
    public static String NUMERO_PROVEEDOR;

    public static void asignarConstantes(JSONObject constan) {

        Constantes.URL_REST_SERVICE = constan.get("url").toString();
        Constantes.USER_GEPP = constan.get("user").toString();
        Constantes.USER_PASS_GEPP = constan.get("password").toString();
        Constantes.NUMERO_PROVEEDOR = constan.get("numeroProveedor").toString();
    }
}
