package cat.paucasesnovescifp.solucio_practica_1.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class ExportEngine {
    private final ReportCache reportCache;

    public ExportEngine(ReportCache reportCache) {
        this.reportCache = reportCache;
        System.out.println("Inicialitzant ExportEngine...");
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("S'ha interromput la inicialització d'ExportEngine", e);
        }
        System.out.println("ExportEngine inicialitzat.");
    }

    public String exportAll() {
        var reports = reportCache.getAll();
        if (reports.isEmpty()) {
            return "No hi ha informes per exportar.";
        }

        StringBuilder export = new StringBuilder();
        reports.forEach((id, content) -> export.append("Informe ")
                .append(id).append(":\n")
                .append(content).append("\n\n"));
        return export.toString();
    }
}
