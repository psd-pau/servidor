package cat.paucasesnovescifp.UT1_2627.service;

import cat.paucasesnovescifp.UT1_2627.domain.ShoppingCart;
import cat.paucasesnovescifp.UT1_2627.domain.ShoppingCartFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private final ShoppingCartFactory cartFactory;

    public CheckoutService(ShoppingCartFactory cartFactory) {
        this.cartFactory = cartFactory;
    }

    public void startCheckout() {
        ShoppingCart cart = cartFactory.createCart();
        System.out.println(cart.getId());
    }
}
