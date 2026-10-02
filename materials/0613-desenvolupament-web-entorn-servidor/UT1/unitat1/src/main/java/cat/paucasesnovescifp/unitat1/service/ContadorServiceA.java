package cat.paucasesnovescifp.unitat1.service;

import cat.paucasesnovescifp.unitat1.domain.Contador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContadorServiceA {
    private final Contador counter;

    @Autowired
    public ContadorServiceA(Contador counter){
        this.counter = counter;
    }

    public String print(){
        return "A " + counter.next();
    }

}
