package cat.paucasesnovescifp.unitat1_2627.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class HoraConfiguration {

    @Bean
    public DateTimeFormatter formatDataHora(){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }

}
