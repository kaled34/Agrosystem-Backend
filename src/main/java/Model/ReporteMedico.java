package Model;
import java.time.LocalDate;

public class ReporteMedico {
    public int idReporte;
    public Animales idAnimales;
    public Enfermedad idEnfermedad;
    public Tratamiento idTratamiento;
    public LocalDate fechaReporte;
    public String descripcionReporte;

    public ReporteMedico(){}


    public ReporteMedico(int idReporte, LocalDate fechaReporte, Animales idAnimales,
                         Enfermedad idEnfermedad, Tratamiento idTratamiento, String descripcionReporte) {

        this.idReporte = idReporte;
        this.idAnimales = idAnimales;
        this.idEnfermedad = idEnfermedad;
        this.idTratamiento = idTratamiento;
        this.fechaReporte = fechaReporte;
        this.descripcionReporte = descripcionReporte;

    }

//getters
    public int getIdReporte() { return idReporte; }
    public LocalDate getFechaReporte() { return fechaReporte; }
    public String getDescripcionReporte() { return descripcionReporte; }

    public Animales getIdAnimales() {
        return idAnimales;
    }

    public Enfermedad getIdEnfermedad() {
        return idEnfermedad;
    }

    public Tratamiento getIdTratamiento() {
        return idTratamiento;
    }

    //setters
    public void setDescripcionReporte(String descripcionReporte) {
        this.descripcionReporte = descripcionReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
}