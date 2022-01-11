package mx.com.gepp.beans;

public class TripsResponse
{
    private String codigoError;
    private String mensajeError;
    private String zipBase64;

    public TripsResponse() {
        super();
    }

    public TripsResponse(String codigoError, String mensajeError, String zipBase64) {
        super();
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
        this.zipBase64 = zipBase64;
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

    public void setZipBase64(String zipBase64) {
        this.zipBase64 = zipBase64;
    }

    public String getZipBase64() {
        return zipBase64;
    }

    @Override
    public String toString() {
        return "RespCartaPorteGepp [codigoError=" + codigoError + ", mensajeError=" + mensajeError + ", zipBase64=" + zipBase64 + "]";
    }
}
