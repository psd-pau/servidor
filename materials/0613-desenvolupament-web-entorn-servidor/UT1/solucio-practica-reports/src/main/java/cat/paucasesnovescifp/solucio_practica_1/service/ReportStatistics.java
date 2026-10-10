package cat.paucasesnovescifp.solucio_practica_1.service;

import cat.paucasesnovescifp.solucio_practica_1.domain.ReportJob;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servei encarregat de mantenir estadístiques globals sobre
 * tots els informes generats durant el cicle de vida de l'aplicació.
 *
 * - És un bean singleton (només una instància per aplicació)
 * - Emmagatzema dades acumulades: nombre total, per format i últim generat
 * - És actualitzat per ReportJobService cada vegada que es crea un nou ReportJob
 */

@Service
@Scope("singleton") //Si o si ha d'esser singleton
public class ReportStatistics {

    //Contadors globals
    private int totalReports = 0;
    private int htmlCount = 0;
    private int pdfCount = 0;
    private int csvCount = 0;
    private LocalDateTime lastGenerated;

    public void registerJob(ReportJob job) {
        totalReports++;
        lastGenerated = job.getTimestamp();

        if ("html".equalsIgnoreCase(job.getFormat())) {
            htmlCount++;
        }
        if ("pdf".equalsIgnoreCase(job.getFormat())) {
            pdfCount++;
        }
        if ("csv".equalsIgnoreCase(job.getFormat())) {
            csvCount++;
        }
    }

    public String summary() {
        return String.format("""
                Estadístiques globals:
                - Total informes: %d
                - HTML: %d
                - PDF: %d
                - CSV: %d
                - Últim generat: %s
                """,
                totalReports, htmlCount, pdfCount, csvCount,
                lastGenerated != null ? lastGenerated : "Encara cap");
    }
}
