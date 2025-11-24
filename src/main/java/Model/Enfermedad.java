package Model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enfermedad {

    public int idEnfermedad;
    public String nombreEnfermedad;
    public  String tipoEnfermedad;
    public  String sintomas;
    public int duracionEstimada;
    public String tratamientosRecomendados;
    public  Medicamento idMedicamento;
    public String nivelRiesgo;
    public  String modoTransmision;
    public Analisis idAnalisis;

    public Enfermedad (){

    }


    public Enfermedad(int idEnfermedad, String nombreEnfermedad, String tipoEnfermedad,
                      String sintomas, int duracionEstimada, String tratamientosRecomendados, Medicamento idMedicamento, String descripcionEnfermedad,String nivelRiesgo, String modoTransmision ,Analisis idAnalisis) {

        this.idEnfermedad = idEnfermedad;
        this.nombreEnfermedad = nombreEnfermedad;
        this.tipoEnfermedad = tipoEnfermedad;
        this.sintomas = sintomas;
        this.duracionEstimada = duracionEstimada;
        this.tratamientosRecomendados = tratamientosRecomendados;
        this.idMedicamento = idMedicamento;
        this.nivelRiesgo = nivelRiesgo;
        this.modoTransmision = modoTransmision;
        this.idAnalisis = idAnalisis;
    }


//getters
    public int getIdEnfermedad() {
        return idEnfermedad;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public String getTipoEnfermedad() {
        return tipoEnfermedad;
    }

    public String getSintomas() {
        return sintomas;
    }

    public int getDuracionEstimada() {
        return duracionEstimada;
    }

    public String getTratamientosRecomendados() {
        return tratamientosRecomendados;
    }

    public Medicamento getIdMedicamento() {
        return idMedicamento;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getModoTransmision() {
        return modoTransmision;
    }

    public Analisis getIdAnalisis() {
        return idAnalisis;
    }


    //settewrs
    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public void setTipoEnfermedad(String tipoEnfermedad) {
        this.tipoEnfermedad = tipoEnfermedad;
    }

    public void setDuracionEstimada(int duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public void setTratamientosRecomendados(String tratamientosRecomendados) {
        this.tratamientosRecomendados = tratamientosRecomendados;
    }

    public void setIdMedicamento(Medicamento idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public void setModoTransmision(String modoTransmision) {
        this.modoTransmision = modoTransmision;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }


    public void setIdAnalisis(Analisis idAnalisis) {
        this.idAnalisis = idAnalisis;
    }
}