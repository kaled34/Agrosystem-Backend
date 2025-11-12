package Model;
import Model.Raza;

import java.time.LocalDate;

public class Animales {

    public int idAnimal;
    public int numArete;
    public int numCrias;
    public LocalDate fechaNacimiento;
    public LocalDate fechaDestete;
    public LocalDate fecha1erParto;
    public LocalDate fecha1erMonta;
    public String nombreAnimal;
    public Raza raza;
    public String descripcionAnimal;
    public String estadoActual;
    public boolean sexo;
    public HistorialParto idHistorialParto;


    public Animales() {
    }

    public Animales(int idAnimal, int numArete, String nombreAnimal, long pesoNacimiento, long pesoActual,
                    LocalDate fechaNacimiento, LocalDate fechaDestete, LocalDate fecha1erParto,
                    LocalDate fecha1erMonta, Raza raza, boolean sexo, int numCrias,
                    String descripcionAnimal, String estadoActual, HistorialParto idHistorialParto) {

        this.idAnimal = idAnimal;
        this.numArete = numArete;
        this.nombreAnimal = nombreAnimal;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDestete = fechaDestete;
        this.fecha1erParto = fecha1erParto;
        this.fecha1erMonta = fecha1erMonta;
        this.raza = raza;
        this.sexo = sexo;
        this.numCrias = numCrias;
        this.descripcionAnimal = descripcionAnimal;
        this.estadoActual = estadoActual;
        this.idHistorialParto = idHistorialParto;
    }




    //getters
    public int getIdAnimal() { return idAnimal; }
    public int getNumArete() { return numArete; }
    public String getNombreAnimal() { return nombreAnimal; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public LocalDate getFechaDestete() { return fechaDestete; }
    public LocalDate getFecha1erParto() { return fecha1erParto; }
    public LocalDate getFecha1erMonta() { return fecha1erMonta; }
    public Raza getRaza() { return raza; }
    public boolean isSexo() { return sexo; } // Usamos 'is' para booleanos en Java
    public int getNumCrias() { return numCrias; }
    public String getDescripcionAnimal() { return descripcionAnimal; }
    public String getEstadoActual() { return estadoActual; }

    public HistorialParto getIdHistorialParto() {
        return idHistorialParto;
    }
//setters

    public void setNombreAnimal(String nombreAnimal) { this.nombreAnimal = nombreAnimal; }
    public void setFechaDestete(LocalDate fechaDestete) { this.fechaDestete = fechaDestete; }
    public void setFecha1erParto(LocalDate fecha1erParto) { this.fecha1erParto = fecha1erParto; }
    public void setFecha1erMonta(LocalDate fecha1erMonta) { this.fecha1erMonta = fecha1erMonta; }
    public void setNumCrias(int numCrias) { this.numCrias = numCrias; }
    public void setDescripcionAnimal(String descripcionAnimal) { this.descripcionAnimal = descripcionAnimal; }
    public void setEstadoActual(String estadoActual) { this.estadoActual = estadoActual; }

    public void setIdHistorialParto(HistorialParto idHistorialParto) {
        this.idHistorialParto = idHistorialParto;
    }
}

