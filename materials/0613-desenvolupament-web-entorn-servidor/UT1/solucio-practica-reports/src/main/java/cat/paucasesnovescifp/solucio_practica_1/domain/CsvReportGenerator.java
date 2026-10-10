package cat.paucasesnovescifp.solucio_practica_1.domain;

import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

@Component
public class CsvReportGenerator implements ReportGenerator {
    private final CSVFormat csvFormat;

    public CsvReportGenerator(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    @Override
    public String generateReport(String id, String data) {
        StringWriter writer = new StringWriter();
        try (CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {
            printer.printRecord(id, data);
        } catch (IOException e) {
            throw new IllegalStateException("No s'ha pogut generar l'informe CSV", e);
        }
        return writer.toString();
    }
}
