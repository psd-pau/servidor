package cat.paucasesnovescifp.unitat1.service;

import cat.paucasesnovescifp.unitat1.domain.Contador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContadorServiceB {
    private final Contador counter;

    @Autowired
    public ContadorServiceB(Contador counter){
        this.counter = counter;
    }

    public String print(){
        return "A " + counter.next();
    }

}
