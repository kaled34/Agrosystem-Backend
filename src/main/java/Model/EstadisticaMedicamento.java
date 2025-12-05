package Model;

public class EstadisticaMedicamento {
    public int cantidadUsos;
    public Medicamento idMedicamento;
    public EstadisticaMedicamento() {}

public EstadisticaMedicamento (int cantidadUsos, Medicamento idMedicamento) {
        this.cantidadUsos = cantidadUsos;
        this.idMedicamento = idMedicamento;
}
            //getters
    public Medicamento getIdMedicamento() {
        return idMedicamento;
    }

    public int getCantidadUsos() {
        return cantidadUsos;
    }

    public void setIdMedicamento(Medicamento idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public void setCantidadUsos(int cantidadUsos) {
        this.cantidadUsos = cantidadUsos;
    }
}
