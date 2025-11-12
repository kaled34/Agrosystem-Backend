package Model;
public class Peso {

    public int idPeso;
    public long pesoNacimiento;
    public double pesoActual;
    public Animales idAnimal;
public Peso(){}

    public Peso(int idPeso,int idAnimal, long pesoNacimiento, long pesoActual, String raza, boolean sexo) {
        this.pesoNacimiento = pesoNacimiento;
        this.pesoActual = pesoActual;

    }

//getters
    public int getIdPeso() {return idPeso;}

    public long getPesoNacimiento() {
        return pesoNacimiento;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public void setIdAnimal(Animales idAnimal) {
        this.idAnimal = idAnimal;
    }

    //setters


    public void setIdPeso(int idPeso) {this.idPeso = idPeso;}

    public void setPesoActual(long pesoActual) {
        this.pesoActual = pesoActual;
    }

}
