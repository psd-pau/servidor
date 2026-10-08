package cat.paucasesnovescifp.unitat1_2627.domain;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy //Es crea a la primera solicitud
public class HeavyBean {
    public HeavyBean(){
        System.out.println(">> Constructor de HeavyBean: Inicialitzant...");
        try{
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        System.out.println(">> HeavyBean inicialitzat");

    }
    public String process(){
        return "HeavyBean process completat!";
    }
}
