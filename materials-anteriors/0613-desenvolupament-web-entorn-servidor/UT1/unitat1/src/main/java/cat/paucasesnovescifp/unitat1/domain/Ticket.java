package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Ticket {
    private static int seq = 0;
    private int id;

    public Ticket(){
        this.id = ++seq;
    }
    public int getId(){
        return id;
    }

}
