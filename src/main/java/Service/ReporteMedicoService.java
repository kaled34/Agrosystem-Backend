package Service;

import Model.ReporteMedico;
import Repository.ReporteMedicoRepository;
import java.util.List;
import java.util.Optional;

public class ReporteMedicoService {

    private final ReporteMedicoRepository reporteMedicoRepository;

    public ReporteMedicoService(ReporteMedicoRepository reporteMedicoRepository) {
        this.reporteMedicoRepository = reporteMedicoRepository;
    }

    public ReporteMedico crearReporte(ReporteMedico reporte) {
        if (reporte.fecha == null) {
            throw new IllegalArgumentException("La fecha del reporte es obligatoria.");
        }

        if (reporte.diagnosticoPresuntivo == null || reporte.diagnosticoPresuntivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnostico es obligatorio.");
        }

        return reporteMedicoRepository.crear(reporte);
    }

    public List<ReporteMedico> obtenerTodos() {
        return reporteMedicoRepository.obtenerTodos();
    }

    public Optional<ReporteMedico> buscarPorId(int id) {
        ReporteMedico reporte = reporteMedicoRepository.obtenerPorId(id);
        return Optional.ofNullable(reporte);
    }

    public ReporteMedico actualizarReporte(ReporteMedico reporteActualizado) {
        if (reporteMedicoRepository.obtenerPorId(reporteActualizado.getIdReporte()) == null) {
            return null;
        }

        if (reporteActualizado.diagnosticoPresuntivo == null || reporteActualizado.diagnosticoPresuntivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnostico es obligatorio");
        }

        return reporteMedicoRepository.actualizar(reporteActualizado);
    }

    public boolean eliminarReporte(int id) {
        return reporteMedicoRepository.eliminar(id);
    }

    public List<ReporteMedico> buscarPorAnimal(int idAnimal) {
        return reporteMedicoRepository.obtenerPorAnimal(idAnimal);
    }
}