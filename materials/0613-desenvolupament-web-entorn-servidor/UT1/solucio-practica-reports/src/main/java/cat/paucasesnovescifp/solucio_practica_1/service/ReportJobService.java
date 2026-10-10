package cat.paucasesnovescifp.solucio_practica_1.service;

import cat.paucasesnovescifp.solucio_practica_1.domain.ReportJob;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servei encarregat de gestionar els jobs de generació d'informes.
 *
 * - Rep cada ReportJob creat pel servei de generació i el desa a l'historial.
 *
 * - També actualitza les estadístiques globals mitjançant ReportStatistics.
 */
@Service
public class ReportJobService {

    private final List<ReportJob> history = new ArrayList<>();
    private final ReportStatistics statistics;

    public ReportJobService(ReportStatistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Afegeix el treball generat a l'historial i actualitza les estadístiques.
     */
    public void logReportGenerated(ReportJob job) {
        history.add(job); // Desa a historial intern
        statistics.registerJob(job); // Actualitza estadístiques globals
        System.out.println(job.describe()); //Mostra log de proves (ToDo: Eliminar a producció)
    }

    /** Retorna l'historial de tots els jobs generats */
    public List<String> getHistoryDescriptions() {
        return history.stream()
                .map(ReportJob::describe)
                .toList();
    }

    /** Retorna el resum de les estadístiques globals */
    public String getStatisticsSummary() {
        return statistics.summary();
    }
}
