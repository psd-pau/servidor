package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MotorBenzina implements Motor{

    @Override
    public String engega() {
        return "Motor de benzina engega.";
    }
}
