package Repository;
import Model.ReporteMedico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteMedicoRepository {
    private List<ReporteMedico> reportes;

    public ReporteMedicoRepository() {
        this.reportes = new ArrayList<>();
    }

    public ReporteMedico crear(ReporteMedico reporte) {
        reportes.add(reporte);
        return reporte;
    }

    public ReporteMedico buscarPorId(int idReporte) {
        for (ReporteMedico reporte : reportes) {
            if (reporte.getIdReporte() == idReporte) {
                return reporte;
            }
        }
        return null;
    }

    public List<ReporteMedico> obtenerTodos() {
        return new ArrayList<>(reportes);
    }

    public ReporteMedico actualizar(ReporteMedico reporteActualizado) {
        for (int i = 0; i < reportes.size(); i++) {
            if (reportes.get(i).getIdReporte() == reporteActualizado.getIdReporte()) {
                reportes.set(i, reporteActualizado);
                return reporteActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idReporte) {
        for (int i = 0; i < reportes.size(); i++) {
            if (reportes.get(i).getIdReporte() == idReporte) {
                reportes.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<ReporteMedico> buscarPorAnimal(int idAnimal) {
        List<ReporteMedico> resultado = new ArrayList<>();
        for (ReporteMedico reporte : reportes) {
            if (reporte.getIdAnimales().getIdAnimal() == idAnimal) {
                resultado.add(reporte);
            }
        }
        return resultado;
    }

    public List<ReporteMedico> buscarPorFecha(LocalDate fecha) {
        List<ReporteMedico> resultado = new ArrayList<>();
        for (ReporteMedico reporte : reportes) {
            if (reporte.getFechaReporte().equals(fecha)) {
                resultado.add(reporte);
            }
        }
        return resultado;
    }
    public int obtenerTotal() {
        return reportes.size();
    }
}
