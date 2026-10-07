package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Contador {
    private AtomicInteger i = new AtomicInteger();

    public int next(){
        return i.incrementAndGet();
    }
}
