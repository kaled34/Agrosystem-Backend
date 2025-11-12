package Model;
import java.time.LocalDate;

public class Tratamiento {
    public int idTratamiento;
    public Animales idAnimal;
    public ReporteMedico idReporte;
    public  Usuario idUsuario;
    public String nombreTratamiento;
    public LocalDate fechaInicio;
    public LocalDate fechaFinal;
    public Medicamento idMedicamento;
    public String observaciones;

    public  Tratamiento(){}

    public Tratamiento(int idTratamiento, Animales idAnimal, ReporteMedico idReporte, Usuario idUsuario , String nombreTratamiento,
                       LocalDate fechaInicio, LocalDate fechaFinal,Medicamento idMedicamento, String observaciones  ) {
        this.idTratamiento = idTratamiento;
        this.idAnimal = idAnimal;
        this.idReporte = idReporte;
        this.idUsuario = idUsuario;
        this.nombreTratamiento = nombreTratamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.idMedicamento = idMedicamento;
        this.observaciones = observaciones;


    }

//getterasn

    public int getIdTratamiento() {
        return idTratamiento;
    }
    public Animales getIdAnimales() {
        return idAnimal;
    }

    public ReporteMedico getIdReporte() {
        return idReporte;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public Medicamento getIdMedicamento() {
        return idMedicamento;
    }

    public String getObservaciones() {
        return observaciones;
    }
    //setters

    public void setIdAnimales(Animales idAnimales) {
        this.idAnimal = idAnimales;
    }

    public void setIdReporte(ReporteMedico idReporte) {
        this.idReporte = idReporte;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }


    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }



    public void setIdMedicamento(Medicamento idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
