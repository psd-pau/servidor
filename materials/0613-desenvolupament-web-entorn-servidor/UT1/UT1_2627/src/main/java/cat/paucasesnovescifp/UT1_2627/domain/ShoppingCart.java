package cat.paucasesnovescifp.UT1_2627.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope("prototype")
public class ShoppingCart {

    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}