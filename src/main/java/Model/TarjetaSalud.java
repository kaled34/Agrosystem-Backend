package Model;

public class TarjetaSalud {

    public int idTarjeta;
    public Animales idAnimal;
    public Enfermedad idEnfermedad;
    public Tratamiento idTratamiento;

    public TarjetaSalud(){}

    public TarjetaSalud(int idTarjeta, Animales idAnimal, Enfermedad idEnfermedad, Tratamiento idTratamiento) {

        this.idTarjeta = idTarjeta;
        this.idAnimal = idAnimal;
        this.idEnfermedad = idEnfermedad;
        this.idTratamiento = idTratamiento;
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
}
