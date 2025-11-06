package Model;

public class Analisis {
    public int idAnalisis;
    public String tipoAnalisis;
    public  String resultado;
    public  String interpretacion;
    public Analisis(){}

    public Analisis( int idAnalisis, String tipoAnalisis, String resultado, String interpretacion){
        this.idAnalisis = idAnalisis;
        this.tipoAnalisis = tipoAnalisis;
        this.resultado = resultado;
        this.interpretacion = interpretacion;
    }

    public int getIdAnalisis() {
        return idAnalisis;
    }

    public String getTipoAnalisis() {
        return tipoAnalisis;
    }

    public String getResultado() {
        return resultado;
    }

    public String getInterpretacion() {
        return interpretacion;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    public void setTipoAnalisis(String tipoAnalisis) {
        this.tipoAnalisis = tipoAnalisis;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setInterpretacion(String interpretacion) {
        this.interpretacion = interpretacion;
    }
}
