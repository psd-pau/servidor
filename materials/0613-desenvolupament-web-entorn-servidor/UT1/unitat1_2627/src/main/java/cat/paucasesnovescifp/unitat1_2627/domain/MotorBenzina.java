package cat.paucasesnovescifp.unitat1_2627.domain;

import org.springframework.stereotype.Component;

@Component
public class MotorBenzina implements Motor{

    @Override
    public String engega() {
        return "Motor benzina engega";
    }
}
