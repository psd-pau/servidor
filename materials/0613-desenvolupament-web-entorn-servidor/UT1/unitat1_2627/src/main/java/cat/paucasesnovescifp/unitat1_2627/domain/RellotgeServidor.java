package cat.paucasesnovescifp.unitat1_2627.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RellotgeServidor {
    private final DateTimeFormatter formatDataHora;

    @Autowired
    public RellotgeServidor(DateTimeFormatter formatDataHora){
        //Injecció amb Bean extern
        this.formatDataHora = formatDataHora;
    }

    public String dataHoraActual(){
        return LocalDateTime.now().format(formatDataHora);
    }


}
