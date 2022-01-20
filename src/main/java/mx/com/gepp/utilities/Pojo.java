/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dspace
 */
public class Pojo {

private String FolioGEPP;

private String FechaGeneracion;

private String UsoCFDI;

private Object DocumentosGEPP;

private Object UbicacionesGEPP;
private Object CartaPorte;

    public Pojo() {
    }

    public Pojo(String FolioGEPP, String FechaGeneracion, String UsoCFDI, Object DocumentosGEPP, Object UbicacionesGEPP, Object CartaPorte) {
        this.FolioGEPP = FolioGEPP;
        this.FechaGeneracion = FechaGeneracion;
        this.UsoCFDI = UsoCFDI;
        this.DocumentosGEPP = DocumentosGEPP;
        this.UbicacionesGEPP = UbicacionesGEPP;
        this.CartaPorte = CartaPorte;
    }

    public Object getUbicacionesGEPP() {
        return UbicacionesGEPP;
    }

    public Object getCartaPorte() {
        return CartaPorte;
    }

    public void setUbicacionesGEPP(Object UbicacionesGEPP) {
        this.UbicacionesGEPP = UbicacionesGEPP;
    }

    public void setCartaPorte(Object CartaPorte) {
        this.CartaPorte = CartaPorte;
    }

   

    public String getFolioGEPP() {
        return FolioGEPP;
    }

    public String getFechaGeneracion() {
        return FechaGeneracion;
    }

    public String getUsoCFDI() {
        return UsoCFDI;
    }

    public Object getDocumentosGEPP() {
        return DocumentosGEPP;
    }

    public void setFolioGEPP(String FolioGEPP) {
        this.FolioGEPP = FolioGEPP;
    }

    public void setFechaGeneracion(String FechaGeneracion) {
        this.FechaGeneracion = FechaGeneracion;
    }

    public void setUsoCFDI(String UsoCFDI) {
        this.UsoCFDI = UsoCFDI;
    }

    public void setDocumentosGEPP(Object DocumentosGEPP) {
        this.DocumentosGEPP = DocumentosGEPP;
    }



}
