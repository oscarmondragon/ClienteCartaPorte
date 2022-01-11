package mx.com.gepp.beans;

public class ReceptionResponse
{
    private String codigoError;
    private String mensajeError;

    public ReceptionResponse() {
        super();
    }

    public ReceptionResponse(String codigoError, String mensajeError) {
        super();
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
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

    @Override
    public String toString() {
        return "ReceptionResponse [codigoError=" + codigoError + ", mensajeError=" + mensajeError + "]";
    }
}
