package Model;

import java.time.LocalDate;

/**
 * Representa un animal en el sistema de gestión ganadera Agrosystem.
 * Esta clase almacena toda la información relevante de un animal incluyendo
 * datos genealógicos, características físicas y de identificación.
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class Animales {

    /** Identificador único del animal en la base de datos */
    public int idAnimal;

    /** Nombre del animal */
    public String nombreAnimal;

    /** Número de arete (identificación física del animal) */
    public int numArete;

    /** Nombre del rebaño al que pertenece el animal */
    public String rebaño;

    /** Fecha de nacimiento del animal */
    public LocalDate fechaNacimiento;

    /** Peso inicial del animal en kilogramos */
    public double pesoInicial;

    /** Características físicas o distintivas del animal */
    public String caracteristica;

    /** Edad del animal en meses */
    public int edad;

    /** Procedencia u origen del animal */
    public String procedencia;

    /** Sexo del animal (true = macho, false = hembra) */
    public boolean sexo;

    /** ID del animal padre (referencia genealógica) */
    public int idPadre;

    /** ID del animal madre (referencia genealógica) */
    public int idMadre;

    /** ID del propietario del animal */
    public int idPropiertario;

    /**
     * Constructor por defecto.
     * Crea una instancia vacía de Animales.
     */
    public Animales() {
    }

    /**
     * Constructor completo para crear un animal con todos sus atributos.
     * 
     * @param idAnimal        Identificador único del animal
     * @param nombreAnimal    Nombre asignado al animal
     * @param numArete        Número de arete de identificación
     * @param rebaño          Nombre del rebaño al que pertenece
     * @param fechaNacimiento Fecha de nacimiento del animal
     * @param pesoInicial     Peso inicial en kilogramos
     * @param caracteristica  Características físicas distintivas
     * @param edad            Edad del animal en meses
     * @param procedencia     Origen o procedencia del animal
     * @param sexo            Sexo del animal (true = macho, false = hembra)
     * @param idPadre         ID del animal padre
     * @param idMadre         ID del animal madre
     * @param idPropiertario  ID del propietario del animal
     */
    public Animales(int idAnimal, String nombreAnimal, int numArete, String rebaño, LocalDate fechaNacimiento,
            double pesoInicial, String caracteristica, int edad, String procedencia, boolean sexo, int idPadre,
            int idMadre, int idPropiertario) {

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

    /**
     * Obtiene el nombre del animal.
     * 
     * @return Nombre del animal
     */
    public String getNombreAnimal() {
        return nombreAnimal;
    }

    /**
     * Obtiene el número de arete del animal.
     * 
     * @return Número de arete
     */
    public int getNumArete() {
        return numArete;
    }

    /**
     * Obtiene el nombre del rebaño al que pertenece el animal.
     * 
     * @return Nombre del rebaño
     */
    public String getRebaño() {
        return rebaño;
    }

    /**
     * Obtiene la fecha de nacimiento del animal.
     * 
     * @return Fecha de nacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Obtiene el peso inicial del animal en kilogramos.
     * 
     * @return Peso inicial en kg
     */
    public double getPesoInicial() {
        return pesoInicial;
    }

    /**
     * Obtiene las características físicas del animal.
     * 
     * @return Descripción de características
     */
    public String getCaracteristica() {
        return caracteristica;
    }

    /**
     * Obtiene la edad del animal en meses.
     * 
     * @return Edad en meses
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Obtiene la procedencia u origen del animal.
     * 
     * @return Procedencia del animal
     */
    public String getProcedencia() {
        return procedencia;
    }

    /**
     * Verifica el sexo del animal.
     * 
     * @return true si es macho, false si es hembra
     */
    public boolean isSexo() {
        return sexo;
    }

    /**
     * Obtiene el ID del animal padre.
     * 
     * @return ID del padre
     */
    public int getIdPadre() {
        return idPadre;
    }

    /**
     * Obtiene el ID del animal madre.
     * 
     * @return ID de la madre
     */
    public int getIdMadre() {
        return idMadre;
    }

    /**
     * Obtiene el ID del propietario del animal.
     * 
     * @return ID del propietario
     */
    public int getIdPropiertario() {
        return idPropiertario;
    }
    //setters

    /**
     * Establece el nombre del animal.
     * 
     * @param nombreAnimal Nuevo nombre del animal
     */
    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    /**
     * Establece el número de arete del animal.
     * 
     * @param numArete Nuevo número de arete
     */
    public void setNumArete(int numArete) {
        this.numArete = numArete;
    }

    /**
     * Establece el nombre del rebaño.
     * 
     * @param rebaño Nuevo nombre del rebaño
     */
    public void setRebaño(String rebaño) {
        this.rebaño = rebaño;
    }

    /**
     * Establece las características del animal.
     * 
     * @param caracteristica Nuevas características
     */
    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    /**
     * Establece la fecha de nacimiento del animal.
     * 
     * @param fechaNacimiento Nueva fecha de nacimiento
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Establece el peso inicial del animal.
     * 
     * @param pesoInicial Nuevo peso inicial en kilogramos
     */
    public void setPesoInicial(double pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    /**
     * Establece la edad del animal.
     * 
     * @param edad Nueva edad en meses
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Establece la procedencia del animal.
     * 
     * @param procedencia Nueva procedencia
     */
    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    /**
     * Establece el sexo del animal.
     * 
     * @param sexo true para macho, false para hembra
     */
    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    /**
     * Establece el ID del animal padre.
     * 
     * @param idPadre Nuevo ID del padre
     */
    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    /**
     * Establece el ID del animal madre.
     * 
     * @param idMadre Nuevo ID de la madre
     */
    public void setIdMadre(int idMadre) {
        this.idMadre = idMadre;
    }

    /**
     * Establece el ID del propietario.
     * 
     * @param idPropiertario Nuevo ID del propietario
     */
    public void setIdPropiertario(int idPropiertario) {
        this.idPropiertario = idPropiertario;
    }

}
