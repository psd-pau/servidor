package cat.paucasesnovescifp.solucio_practica_1.service;

import cat.paucasesnovescifp.solucio_practica_1.domain.ReportGenerator;
import cat.paucasesnovescifp.solucio_practica_1.domain.ReportJob;
import cat.paucasesnovescifp.solucio_practica_1.domain.ReportJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportGenerator pdfGenerator;
    private final ReportGenerator htmlGenerator;
    private final ReportGenerator csvGenerator;
    private final ReportJobFactory reportJobFactory;
    private final ReportCache reportCache;
    private final ReportJobService reportJobService;
    private WatermarkService watermarkService;

    public ReportService(ReportGenerator pdfGenerator,
                         @Qualifier("htmlReportGenerator") ReportGenerator htmlGenerator,
                         @Qualifier("csvReportGenerator") ReportGenerator csvGenerator,
                         ReportJobFactory reportJobFactory,
                         ReportCache reportCache,
                         ReportJobService reportJobService) {
        this.pdfGenerator = pdfGenerator; // @Primary selecciona el generador PDF.
        this.htmlGenerator = htmlGenerator;
        this.csvGenerator = csvGenerator;
        this.reportJobFactory = reportJobFactory;
        this.reportCache = reportCache;
        this.reportJobService = reportJobService;
    }

    @Autowired(required = false)
    public void setWatermarkService(WatermarkService watermarkService) {
        this.watermarkService = watermarkService;
    }

    public String generateReport(String format, String data) {
        ReportGenerator generator = switch (format) {
            case "pdf" -> pdfGenerator;
            case "html" -> htmlGenerator;
            case "csv" -> csvGenerator;
            default -> throw new IllegalArgumentException("Format no suportat: " + format);
        };

        ReportJob job = reportJobFactory.createJob();
        job.init(format);
        String content = watermarkService != null ? watermarkService.apply(data) : data;
        String report = generator.generateReport(job.getId(), content);
        reportCache.store(format + "_" + job.getId(), report);
        reportJobService.logReportGenerated(job);
        return report;
    }
}
