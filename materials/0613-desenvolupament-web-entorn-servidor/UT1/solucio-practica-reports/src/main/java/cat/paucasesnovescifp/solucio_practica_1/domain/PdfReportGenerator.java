package cat.paucasesnovescifp.solucio_practica_1.domain;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Primary
public class PdfReportGenerator implements ReportGenerator {

    @Override
    public String generateReport(String id, String data) {
        return id + "###PDF Report>>" + data;
    }
}
