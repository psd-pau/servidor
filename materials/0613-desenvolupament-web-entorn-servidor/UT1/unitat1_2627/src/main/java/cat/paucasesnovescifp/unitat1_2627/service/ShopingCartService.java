package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.ShopingCartFactory;
import cat.paucasesnovescifp.unitat1_2627.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopingCartService {

    private final ShopingCartFactory cartFactory;

    @Autowired
    public ShopingCartService(ShopingCartFactory cartFactory){
        this.cartFactory = cartFactory;
    }

    public String startCheckout(){
        ShoppingCart shoppingCart = cartFactory.createCart();
        return shoppingCart.getId().toString();
    }
}
