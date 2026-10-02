package cat.paucasesnovescifp.unitat1.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class RellotgeServidor {

    private final DateTimeFormatter formatDataHora;

    public RellotgeServidor(DateTimeFormatter formatDataHora) {
        this.formatDataHora = formatDataHora;
    }

    public String dataHoraActual() {
        return LocalDateTime.now().format(formatDataHora);
    }
}
