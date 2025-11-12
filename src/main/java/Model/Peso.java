package Model;

import java.time.LocalDate;

public class Peso {

    public int idPeso;
    public double pesoNacimiento;
    public double pesoActual;
    public Animales idAnimal;
    public LocalDate fechaMedicion;
    public String condicionCorporal;
    public String observaciones;
public Peso(){}

    public Peso(int idPeso,Animales idAnimal, double pesoNacimiento, double pesoActual, LocalDate fechaMedicion, String condicionCorporal, String observaciones) {
        this.pesoNacimiento = pesoNacimiento;
        this.pesoActual = pesoActual;
        this.idPeso = idPeso;
        this.idAnimal = idAnimal;
        this.fechaMedicion = fechaMedicion;
        this.condicionCorporal = condicionCorporal;
        this.observaciones = observaciones;

    }

//getters


    public int getIdPeso() {
        return idPeso;
    }

    public Animales getIdAnimal() {
        return idAnimal;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public double getPesoNacimiento() {
        return pesoNacimiento;
    }

    public LocalDate getFechaMedicion() {
        return fechaMedicion;
    }

    public String getCondicionCorporal() {
        return condicionCorporal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    //setters


    public void setIdPeso(int idPeso) {
        this.idPeso = idPeso;
    }

    public void setIdAnimal(Animales idAnimal) {
        this.idAnimal = idAnimal;
    }

    public void setPesoActual(double pesoActual) {
        this.pesoActual = pesoActual;
    }

    public void setPesoNacimiento(double pesoNacimiento) {
        this.pesoNacimiento = pesoNacimiento;
    }

    public void setFechaMedicion(LocalDate fechaMedicion) {
        this.fechaMedicion = fechaMedicion;
    }

    public void setCondicionCorporal(String condicionCorporal) {
        this.condicionCorporal = condicionCorporal;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
