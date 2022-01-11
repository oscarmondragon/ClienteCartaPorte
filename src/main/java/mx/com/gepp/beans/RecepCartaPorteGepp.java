package mx.com.gepp.beans;

public class RecepCartaPorteGepp
{    
    private String usuarioGEPP;
    private String numProveedor;
    private String numViaje;
    private String codigoError;
    private String mensajeError;
    private String urlDocumentos;
    private String zipBase64;

    public RecepCartaPorteGepp() {
        super();
    }
    
    public RecepCartaPorteGepp(String usuarioGEPP, String numProveedor, String numViaje, String codigoError, String mensajeError, String urlDocumentos, String zipBase64) {
        super();
        
        this.usuarioGEPP = usuarioGEPP;
        this.numProveedor = numProveedor;
        this.numViaje = numViaje;
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
        this.urlDocumentos = urlDocumentos;
        this.zipBase64 = zipBase64;
    }
    
    public void setUsuarioGEPP(String usuarioGEPP) {
        this.usuarioGEPP = usuarioGEPP;
    }
    
    public String getUsuarioGEPP() {
        return usuarioGEPP;
    }
    
    public void setNumProveedor(String numProveedor) {
        this.numProveedor = numProveedor;
    }
    
    public String getNumProveedor() {
        return numProveedor;
    }
    
    public void setNumViaje(String numViaje) {
        this.numViaje = numViaje;
    }
    
    public String getNumViaje() {
        return numViaje;
    }
    
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }
    
    public String getCodigoError() {
        return codigoError;
    }
    
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
    public String getMensajeError() {
        return mensajeError;
    }
    
    public void setUrlDocumentos(String urlDocumentos) {
        this.urlDocumentos = urlDocumentos;
    }
    
    public String getUrlDocumentos() {
        return urlDocumentos;
    }
    
    public void setZipBase64(String zipBase64) {
        this.zipBase64 = zipBase64;
    }
    
    public String getZipBase64() {
        return zipBase64;
    }
    
    @Override
    public String toString() {
        return "RecepCartaPorteGepp [usuarioGEPP=" + usuarioGEPP + ", numProveedor=" + numProveedor + ", numViaje=" + numViaje + ", codigoError=" + codigoError + 
               ", mensajeError=" + mensajeError +  ", urlDocumentos=" + urlDocumentos + ", zipBase64=" + zipBase64 + "]";
    }
}

