package Model;
import java.time.LocalDate;

public class Tratamiento {
    public int idTratamiento;
    public Animales idAnimal;
    public Medicamento idMedicamento;
    public LocalDate fechaInicio;
    public LocalDate fechaFinal;
    public String nombreTratamiento;
    public String descripcionReporte;
    public String evolucion;
    public String nombreVeterinario;

    public  Tratamiento(){}

    public Tratamiento(int idTratamiento, String nombreTratamiento, int idMedicamento,
                       LocalDate fechaInicio, LocalDate fechaFinal, String descripcionReporte,
                       String evolucion, String nombreVeterinario, Animales idAnimal) {
        this.idTratamiento = idTratamiento;
        this.nombreTratamiento = nombreTratamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.descripcionReporte = descripcionReporte;
        this.evolucion = evolucion;
        this.nombreVeterinario = nombreVeterinario;
        this.idAnimal = idAnimal;

    }

//getterasn

    public int getIdTratamiento() { return idTratamiento; }
    public String getNombreTratamiento() { return nombreTratamiento; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFinal() { return fechaFinal; }
    public String getDescripcionReporte() { return descripcionReporte; }
    public String getEvolucion() { return evolucion; }
    public String getNombreVeterinario() { return nombreVeterinario; }

    public Animales getIdAnimales() {
        return idAnimal;
    }
    //setters

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setDescripcionReporte(String descripcionReporte) {
        this.descripcionReporte = descripcionReporte;
    }

    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public void setIdAnimales(Animales idAnimales) {
        this.idAnimal = idAnimales;
    }

    public void setIdMedicamento(Medicamento idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
}
