package cat.paucasesnovescifp.solucio_practica_1.config;

import org.apache.commons.csv.CSVFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfig {
    @Bean
    public CSVFormat csvFormat() {
        return CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader("id", "contingut")
                .get();
    }
}
