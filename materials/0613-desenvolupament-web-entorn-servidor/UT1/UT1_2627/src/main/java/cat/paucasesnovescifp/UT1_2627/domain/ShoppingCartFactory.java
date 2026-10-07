package cat.paucasesnovescifp.UT1_2627.domain;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartFactory {

    private final ObjectProvider<ShoppingCart> provider;

    public ShoppingCartFactory(ObjectProvider<ShoppingCart> provider) {
        this.provider = provider;
    }

    public ShoppingCart createCart() {
        return provider.getObject();
    }
}
