package cat.paucasesnovescifp.unitat1.domain;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ReportGenerator {
    public ReportGenerator(){
        System.out.println(">> Constructor ReportGenerator");
    }
    @PostConstruct
    public void init(){

        System.out.println(">> Inicialitzant recursos de ReportGenerator");
    }
    @PreDestroy
    public void cleanup(){

        System.out.println(">> Alliberant recursos de ReportGenerator");
    }
    public String generate(){

        return "Informe generat!";
    }
}
