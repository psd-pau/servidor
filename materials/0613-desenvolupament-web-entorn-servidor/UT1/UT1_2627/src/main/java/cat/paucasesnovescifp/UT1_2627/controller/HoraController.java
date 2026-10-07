package cat.paucasesnovescifp.UT1_2627.controller;

import cat.paucasesnovescifp.UT1_2627.service.HoraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HoraController {

    private final HoraService horaService;

    public HoraController(HoraService horaService) {
        this.horaService = horaService;
    }

    @GetMapping("/hora")
    public String hora() {
        return horaService.consultarHora();
    }
}

