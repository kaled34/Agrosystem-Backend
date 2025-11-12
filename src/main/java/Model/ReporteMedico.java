package Model;
import java.time.LocalDate;

public class ReporteMedico {
    public int idReporte;
    public Animales idAnimales;
    public Usuario idUsuario;
    public double temperatura;
    public String condicionCorporal;
    public int frecuenciaRespiratoria;
    public LocalDate fecha;
    public String diagnosticoPresuntivo;
    public  String diagnosticoDefinitivo;

    public ReporteMedico(){}


    public ReporteMedico(int idReporte, Animales idAnimales, Usuario idUsuario, double temperatura, String condicionCorporal, int frecuenciaRespiratoria,LocalDate fecha, String diagnosticoPresuntivo, String diagnosticoDefinitivo) {

        this.idReporte = idReporte;
        this.idAnimales = idAnimales;
        this.idUsuario = idUsuario;
        this.temperatura = temperatura;
        this.condicionCorporal = condicionCorporal;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.fecha = fecha;
        this.diagnosticoPresuntivo = diagnosticoPresuntivo;
        this.diagnosticoDefinitivo = diagnosticoDefinitivo;


    }

//getters
    public int getIdReporte() { return idReporte; }

    public Animales getIdAnimales() {
        return idAnimales;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public String getCondicionCorporal() {
        return condicionCorporal;
    }

    public int getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDiagnosticoPresuntivo() {
        return diagnosticoPresuntivo;
    }

    public String getDiagnosticoDefinitivo() {
        return diagnosticoDefinitivo;
    }

    //setters

    public void setIdAnimales(Animales idAnimales) {
        this.idAnimales = idAnimales;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setCondicionCorporal(String condicionCorporal) {
        this.condicionCorporal = condicionCorporal;
    }

    public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setDiagnosticoPresuntivo(String diagnosticoPresuntivo) {
        this.diagnosticoPresuntivo = diagnosticoPresuntivo;
    }

    public void setDiagnosticoDefinitivo(String diagnosticoDefinitivo) {
        this.diagnosticoDefinitivo = diagnosticoDefinitivo;
    }
}