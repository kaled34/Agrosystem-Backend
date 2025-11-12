package Model;

import java.util.Date;

public class Medicamento {

    public int idMedicamento;
    public String nombreMedicamento;
    public String solucion;
    public  float dosis;
    public Date caducidad;
    public String viaAdministracion;
    public String composicion;
    public String indicaciones;
    public  String frecuenciaAplicacion;

    public Medicamento(){
    }

    public Medicamento(int idMedicamento, String nombreMedicamento, String solucion,float dosis, Date caducidad, String viaAdministracion,String composicion, String indicaciones, String frecuenciaAplicacion) {
        this.idMedicamento = idMedicamento;
        this.nombreMedicamento = nombreMedicamento;
        this.solucion = solucion;
        this.dosis = dosis;
        this.caducidad = caducidad;
        this.viaAdministracion = viaAdministracion;
        this.composicion = composicion;
        this.indicaciones = indicaciones;
        this.frecuenciaAplicacion = frecuenciaAplicacion;
    }

    //Getters
    public int getIdMedicamento() {
        return idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public String getSolucion() {
        return solucion;
    }

    public float getDosis() {
        return dosis;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public String getComposicion() {
        return composicion;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public String getFrecuenciaAplicacion() {
        return frecuenciaAplicacion;
    }

    //Setters


    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public void setDosis(float dosis) {
        this.dosis = dosis;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public void setFrecuenciaAplicacion(String frecuenciaAplicacion) {
        this.frecuenciaAplicacion = frecuenciaAplicacion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

}