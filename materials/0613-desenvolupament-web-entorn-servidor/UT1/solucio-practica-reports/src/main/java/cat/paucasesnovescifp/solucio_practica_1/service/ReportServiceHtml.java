package cat.paucasesnovescifp.solucio_practica_1.service;

import cat.paucasesnovescifp.solucio_practica_1.domain.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;


/*
    Aquí provam a dividir els serveis segons tipus.
    Ràpidament veiem que el codi es comença a duplicar
 */
@Service
public class ReportServiceHtml {
    private ReportGenerator reportGenerator;
    private WatermarkService watermarkService;

    @Autowired
    public ReportServiceHtml(@Qualifier("htmlReportGenerator") ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @Autowired(required = false)
    public void setWatermarkService(WatermarkService watermarkService) {
        this.watermarkService = watermarkService;
    }

    public String generateReport(String data){
        String id = UUID.randomUUID().toString();
        if (watermarkService != null){
            return reportGenerator.generateReport(id, watermarkService.apply(data));
        } else {
            return reportGenerator.generateReport(id, data);
        }
    }

}
