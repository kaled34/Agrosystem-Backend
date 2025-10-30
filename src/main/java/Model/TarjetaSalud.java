package Model;

public class TarjetaSalud {

    public int idTarjeta;
    public Animales idAnimal;
    public Enfermedad idEnfermedad;
    public Tratamiento idTratamiento;
    public String historialEnfermedades;
    public String historialTratamientos;
    public String descripcionReporte;

    public TarjetaSalud(int idTarjeta, Animales idAnimal, Enfermedad idEnfermedad, Tratamiento idTratamiento,
                        String historialEnfermedades, String historialTratamientos,
                        String descripcionReporte) {

        this.idTarjeta = idTarjeta;
        this.idAnimal = idAnimal;
        this.idEnfermedad = idEnfermedad;
        this.idTratamiento = idTratamiento;
        this.historialEnfermedades = historialEnfermedades;
        this.historialTratamientos = historialTratamientos;
        this.descripcionReporte = descripcionReporte;
    }

    //getters
    public int getIdTarjeta() {
        return idTarjeta;
    }

    public Animales getIdAnimal() {
        return idAnimal;
    }

    public Enfermedad getIdEnfermedad() {
        return idEnfermedad;
    }

    public Tratamiento getIdTratamiento() {
        return idTratamiento;
    }

    public String getHistorialEnfermedades() {
        return historialEnfermedades;
    }

    public String getHistorialTratamientos() {
        return historialTratamientos;
    }

    public String getDescripcionReporte() {
        return descripcionReporte;
    }

    //setters


    public void setIdAnimal(Animales idAnimal) {
        this.idAnimal = idAnimal;
    }

    public void setIdEnfermedad(Enfermedad idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public void setIdTratamiento(Tratamiento idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public void setHistorialEnfermedades(String historialEnfermedades) {
        this.historialEnfermedades = historialEnfermedades;
    }

    public void setHistorialTratamientos(String historialTratamientos) {
        this.historialTratamientos = historialTratamientos;
    }

    public void setDescripcionReporte(String descripcionReporte) {
        this.descripcionReporte = descripcionReporte;
    }

}
