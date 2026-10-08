package cat.paucasesnovescifp.unitat1_2627.domain;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopingCartFactory {
    public final ObjectProvider<ShoppingCart> provider;

    @Autowired
    public ShopingCartFactory(ObjectProvider<ShoppingCart> provider){
        this.provider = provider;
    }

    public ShoppingCart createCart(){
        return provider.getObject();
    }

}
