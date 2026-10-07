package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component //singleton -> eager inicialization
@Lazy
public class HeavyBean {

    public HeavyBean(){
        System.out.println(">> Constructor HeavyBean: Inicialitzant...");
        try{
            Thread.sleep(5000); //Simulam feina "pesada"
        } catch (InterruptedException ignored){
            ignored.printStackTrace();
        }
    }

    public String process(){
        return "HeavyBean process completat!";
    }

}
