package cat.paucasesnovescifp.solucio_practica_1.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HtmlReportGenerator implements ReportGenerator {


    @Override
    public String generateReport(String id, String data) {
        return "<html><body><h1>Informe " + id + "</h1><p>" + data + "</p></body></html>";
    }

}
