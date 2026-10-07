package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MotorElectric implements Motor{

    @Override
    public String engega() {
        return "Motor Elèctric engega";
    }
}
