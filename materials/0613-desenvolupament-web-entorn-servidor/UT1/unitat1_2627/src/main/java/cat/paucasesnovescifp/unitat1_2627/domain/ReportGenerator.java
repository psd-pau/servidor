package cat.paucasesnovescifp.unitat1_2627.domain;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ReportGenerator {
    /*
        Exemple de component on requerim de mètodes postconstruct i predestroy
        -Casos:
            - Conexions a BBDD i caches (redis...)
            - Servei de missatgeria (Kafka, RabbitMQ...)
            - Microserveis i cloud (Eureka, Consul...)
            - Thread Pools (tasques en segon pla)
            - Seguretat i criptografia (certificats de fitxer, vault...)
     */

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
