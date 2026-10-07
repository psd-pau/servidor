package cat.paucasesnovescifp.UT1_2627.controller;

import cat.paucasesnovescifp.UT1_2627.domain.ShoppingCart;
import cat.paucasesnovescifp.UT1_2627.domain.ShoppingCartFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cart")
class CartController {
    private final ShoppingCart directCart;
    private final ShoppingCartFactory cartFactory;

    public CartController(
            ShoppingCart directCart,
            ShoppingCartFactory cartFactory) {
        this.directCart = directCart;
        this.cartFactory = cartFactory;
    }

    @GetMapping("/direct")
    public String direct() {
        return "Cart injectat directament: " + directCart.getId();
    }

    @GetMapping("/factory")
    public String factory() {
        ShoppingCart cart = cartFactory.createCart();
        return "Cart creat amb factory: " + cart.getId();
    }

}
