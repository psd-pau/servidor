package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Gps {
    public String getPosition(){
        return "GPS 0.0N, 0.0E";
    }
}
