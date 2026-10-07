package cat.paucasesnovescifp.UT1_2627.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimerControlador {
    @GetMapping("/")
    public String holaMon(){
        return "Hola món!!!";
    }
}
