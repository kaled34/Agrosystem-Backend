package Model;

import java.time.LocalDate;

public class Animales {

    public int idAnimal;
    public String nombreAnimal;
    public int numArete;
    public String rebaño;
    public LocalDate fechaNacimiento;
    public double pesoInicial;
    public String caracteristica;
    public int edad;
    public  String procedencia;
    public boolean sexo;
    public int idPadre;
    public int idMadre;
    public int idPropiertario;

    public Animales() {
    }

    public Animales(int idAnimal, String nombreAnimal,int numArete, String rebaño, LocalDate fechaNacimiento, double pesoInicial, String caracteristica, int edad, String procedencia, boolean sexo, int idPadre, int idMadre, int idPropiertario ) {

        this.idAnimal = idAnimal;
        this.nombreAnimal = nombreAnimal;
        this.numArete = numArete;
        this.rebaño = rebaño;
        this.fechaNacimiento = fechaNacimiento;
        this.pesoInicial = pesoInicial;
        this.caracteristica = caracteristica;
        this.edad = edad;
        this.procedencia = procedencia;
        this.sexo = sexo;
        this.idPadre = idPadre;
        this.idMadre = idMadre;
        this.idPropiertario = idPropiertario;
    }




    //getters

    public int getIdAnimal() {
        return idAnimal;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public int getNumArete() {
        return numArete;
    }

    public String getRebaño() {
        return rebaño;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public double getPesoInicial() {
        return pesoInicial;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public int getEdad() {
        return edad;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public boolean isSexo() {
        return sexo;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public int getIdMadre() {
        return idMadre;
    }

    public int getIdPropiertario() {
        return idPropiertario;
    }
    //setters

    public void setNombreAnimal(String nombreAnimal) { this.nombreAnimal = nombreAnimal; }

    public void setNumArete(int numArete) {
        this.numArete = numArete;
    }

    public void setRebaño(String rebaño) {
        this.rebaño = rebaño;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public void setPesoInicial(double pesoInicial) { this.pesoInicial = pesoInicial; }

    public void setEdad(int edad) { this.edad = edad; }

    public void setProcedencia(String procedencia) { this.procedencia = procedencia; }

    public void setSexo(boolean sexo) { this.sexo = sexo; }

    public void setIdPadre(int idPadre) { this.idPadre = idPadre; }

    public void setIdMadre(int idMadre) { this.idMadre = idMadre; }

    public void setIdPropiertario(int idPropiertario) { this.idPropiertario = idPropiertario; }

}

