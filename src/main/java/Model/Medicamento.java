package Model;
public class Medicamento {

    public int idMedicamento;
    public String nombreMedicamento;
    public String principioActivo;
    public String descripcionMedicamento;
    public String fechaCaducidad;
    public float cantidadMedicamento;
    public String viaAdministracion;
    public Animales idAnimal;

    public Medicamento(){
    }

    public Medicamento(int idMedicamento, Animales idAnimal, String nombreMedicamento, String principioActivo, String descripcionMedicamento, String fechaCaducidad, float cantidadMedicamento, String viaAdministracion, Animales idAnimales) {
        this.idMedicamento = idMedicamento;
        this.nombreMedicamento = nombreMedicamento;
        this.principioActivo = principioActivo;
        this.descripcionMedicamento = descripcionMedicamento;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidadMedicamento = cantidadMedicamento;
        this.viaAdministracion = viaAdministracion;
        this.idAnimal = idAnimal;
    }

    //Getters
    public int getIdMedicamento() {
        return idMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getDescripcionMedicamento() {
        return descripcionMedicamento;
    }

    public Animales getIdAnimal() {return idAnimal; }

    //Setters
    public void setDescripcionMedicamento(String descripcionMedicamento) {
        this.descripcionMedicamento = descripcionMedicamento;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public float getCantidadMedicamento() {
        return cantidadMedicamento;
    }

    public void setCantidadMedicamento(float cantidadMedicamento) {
        this.cantidadMedicamento = cantidadMedicamento;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

}