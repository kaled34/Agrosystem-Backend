package Model;

import java.time.LocalDate;

public class HistorialParto {
    public int idHistorialParto;
    public LocalDate fecha1Monta;
    public int numPartos;
    public int numCrias;

    public HistorialParto(){}

    public HistorialParto (int idHistorialParto, LocalDate fecha1Monta, int numPartos, int numCrias ){
        this.idHistorialParto = idHistorialParto;
        this.fecha1Monta = fecha1Monta;
        this.numPartos = numPartos;
        this.numCrias = numCrias;
    }

    public int getIdHistorialParto() {
        return idHistorialParto;
    }

    public LocalDate getFecha1Monta() {
        return fecha1Monta;
    }

    public int getNumPartos() {
        return numPartos;
    }

    public int getNumCrias() {
        return numCrias;
    }


    public void setIdHistorialParto(int idHistorialParto) {
        this.idHistorialParto = idHistorialParto;
    }

    public void setFecha1Monta(LocalDate fecha1Monta) {
        this.fecha1Monta = fecha1Monta;
    }

    public void setNumPartos(int numPartos) {
        this.numPartos = numPartos;
    }

    public void setNumCrias(int numCrias) {
        this.numCrias = numCrias;
    }
}
