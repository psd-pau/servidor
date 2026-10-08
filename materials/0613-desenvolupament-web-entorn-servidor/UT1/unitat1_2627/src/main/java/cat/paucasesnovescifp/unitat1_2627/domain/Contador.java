package cat.paucasesnovescifp.unitat1_2627.domain;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Contador {

    private AtomicInteger i = new AtomicInteger();

    public int next() {return i.incrementAndGet();}

}
