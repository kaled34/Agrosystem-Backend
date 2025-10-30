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
        if (reporte.getFechaReporte() == null) {
            throw new IllegalArgumentException("La fecha del reporte es obligatoria.");
        }

        if (reporte.getDescripcionReporte() == null || reporte.getDescripcionReporte().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del reporte es obligatoria.");
        }

        return reporteMedicoRepository.crear(reporte);
    }

    public List<ReporteMedico> obtenerTodos() {
        return reporteMedicoRepository.obtenerTodos();
    }

    public Optional<ReporteMedico> buscarPorId(int id) {
        ReporteMedico reporte = reporteMedicoRepository.buscarPorId(id);
        return Optional.ofNullable(reporte);
    }

    public ReporteMedico actualizarReporte(ReporteMedico reporteActualizado) {
        if (reporteMedicoRepository.buscarPorId(reporteActualizado.getIdReporte()) == null) {
            return null;
        }

        if (reporteActualizado.getDescripcionReporte() == null || reporteActualizado.getDescripcionReporte().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del reporte es obligatoria.");
        }

        return reporteMedicoRepository.actualizar(reporteActualizado);
    }

    public boolean eliminarReporte(int id) {
        return reporteMedicoRepository.eliminar(id);
    }

    public List<ReporteMedico> buscarPorAnimal(int idAnimal) {
        return reporteMedicoRepository.buscarPorAnimal(idAnimal);
    }
}