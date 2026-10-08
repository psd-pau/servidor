package cat.paucasesnovescifp.unitat1_2627.controller;

import cat.paucasesnovescifp.unitat1_2627.domain.ShoppingCart;
import cat.paucasesnovescifp.unitat1_2627.service.ShopingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final ShoppingCart directCart;
    private final ShopingCartService shopingCartService;

    /*
        Cart de tipus prototype amb injecció directa sense Factory.
        Hauriem d'emprar un servei, però per simplicitat en aquest
        exemple no s'ha posat
     */
    @Autowired
    public CartController(ShoppingCart directCart,
                          ShopingCartService shopingCartService){
        this.directCart = directCart;
        this.shopingCartService = shopingCartService;
    }

    @GetMapping("/direct")
    public String direct(){
        return "Cart injectat directament: " + directCart.getId();
    }

    @GetMapping("/factory")
    public String factory(){
        return "Cart creat amb factory: " + shopingCartService.startCheckout();
    }


}
