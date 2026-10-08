package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportGenerator reportGenerator;

    @Autowired
    public ReportService(ReportGenerator reportGenerator){
        this.reportGenerator = reportGenerator;
    }

    public String generateReport(){
        return reportGenerator.generate();
    }

}
