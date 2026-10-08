package cat.paucasesnovescifp.unitat1_2627.controller;

import cat.paucasesnovescifp.unitat1_2627.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Unitat1Contoller {

    private final CotxeService cotxeService;
    private final ContadorServiceA contadorServiceA;
    private final ContadorServiceB contadorServiceB;
    private final TicketService ticketService;
    private final RequestService requestService;
    private final SessionService sessionService;
    private final HeavyService heavyService;
    private final HoraService horaService;
    private final ReportService reportService;

    @Autowired
    public Unitat1Contoller(CotxeService cotxeService,
                            ContadorServiceA contadorServiceA,
                            ContadorServiceB contadorServiceB,
                            TicketService ticketService,
                            RequestService requestService,
                            SessionService sessionService,
                            HeavyService heavyService,
                            HoraService horaService,
                            ReportService reportService) {
        this.cotxeService = cotxeService;
        this.contadorServiceA = contadorServiceA;
        this.contadorServiceB = contadorServiceB;
        this.ticketService = ticketService;
        this.requestService = requestService;
        this.sessionService = sessionService;
        this.heavyService = heavyService;
        this.horaService = horaService;
        this.reportService = reportService;
    }

    @GetMapping("/")
    public String holaMon(){
        return "hola mon";
    }

    @GetMapping("/cotxe")
    public String conduirCotxe(){
        return cotxeService.conduir();
    }

    @GetMapping("/singleton")
    public String singleton(){
        String a = contadorServiceA.print();
        String b = contadorServiceB.print();

        return a + b;
    }

    @GetMapping("/prototype")
    public String prototype(){
        return ticketService.printTickets();
    }

    @GetMapping("/request")
    public String request(){
        return requestService.getRequestBeanId();
    }

    @GetMapping("/session")
    public String session(){
        return sessionService.getSessionBeanId();
    }

    @GetMapping("/lazy")
    public String lazy(){
        return heavyService.doWork();
    }

    @GetMapping("/hora")
    public String hora(){
        return horaService.consultarHora();
    }

    @GetMapping("/report")
    public String report(){
        return reportService.generateReport();
    }

}
