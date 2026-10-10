package cat.paucasesnovescifp.solucio_practica_1.controller;


import cat.paucasesnovescifp.solucio_practica_1.service.ReportJobService;
import cat.paucasesnovescifp.solucio_practica_1.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportJobService reportJobService;

    public ReportController(ReportService reportService, ReportJobService reportJobService) {
        this.reportService = reportService;
        this.reportJobService = reportJobService;

    }

    @GetMapping("/html")
    public String generateHtmlReport() {
        return reportService.generateReport("html", "Dades de prova");
    }

    @GetMapping("/pdf")
    public String generatePdfReport() {
        return reportService.generateReport("pdf", "Dades de prova");
    }

    @GetMapping(value = "/csv", produces = "text/csv;charset=UTF-8")
    public String generateCsvReport() {
        return reportService.generateReport("csv", "Dades de prova");
    }

    @GetMapping("/stats")
    public String getStatistics() {
        return reportJobService.getStatisticsSummary();
    }

}
