package Model;

public class Enfermedad {

    public int idEnfermedad;
    public int diasDuracion;
    public String nombreEnfermedad;
    public String descripcionEnfermedad;
    public String sintomas;
    public String cuidadosPreventivos;
    public String duracion;

    public Enfermedad(int idEnfermedad, String nombreEnfermedad, String descripcionEnfermedad,
                      String sintomas, String cuidadosPreventivos, String duracion, int diasDuracion) {

        this.idEnfermedad = idEnfermedad;
        this.nombreEnfermedad = nombreEnfermedad;
        this.descripcionEnfermedad = descripcionEnfermedad;
        this.sintomas = sintomas;
        this.cuidadosPreventivos = cuidadosPreventivos;
        this.duracion = duracion;
        this.diasDuracion = diasDuracion;
    }


//getters
    public int getIdEnfermedad() {
        return idEnfermedad;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public String getDescripcionEnfermedad() {
        return descripcionEnfermedad;
    }

    public String getSintomas() {
        return sintomas;
    }

    public String getCuidadosPreventivos() {
        return cuidadosPreventivos;
    }

    public String getDuracion() {
        return duracion;
    }

    public int getDiasDuracion() {
        return diasDuracion;
    }


    //settewrs
    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public void setDescripcionEnfermedad(String descripcionEnfermedad) {
        this.descripcionEnfermedad = descripcionEnfermedad;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void setCuidadosPreventivos(String cuidadosPreventivos) {
        this.cuidadosPreventivos = cuidadosPreventivos;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setDiasDuracion(int diasDuracion) {
        this.diasDuracion = diasDuracion;
    }

}