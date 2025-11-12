package Model;

import java.time.LocalDate;

public class Parto {
    public int idParto;
    public Animales idMadre;
    public LocalDate fecha;
    public int cantidadCrias;

    public Parto (){}

    public Parto (int idParto, Animales idMadre, LocalDate fecha, int cantidadCrias){
        this.idParto = idParto;
        this.idMadre = idMadre;
        this.fecha = fecha;
        this.cantidadCrias = cantidadCrias;
    }

    public int getIdParto() {
        return idParto;
    }

    public Animales getIdMadre() {
        return idMadre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getCantidadCrias() {
        return cantidadCrias;
    }

    //setters

    public void setCantidadCrias(int cantidadCrias) {
        this.cantidadCrias = cantidadCrias;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
