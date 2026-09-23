package cat.paucasesnovescifp.unitat1.controller;


import cat.paucasesnovescifp.unitat1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Unitat1Controller {

    private final Cotxe cotxe;
    private final ContadorServiceA serviceA;
    private final ContadorServiceB serviceB;
    private final TicketService ticketService;
    private final RequestService requestService;
    private final SessionService sessionService;
    private final HeavyService heavyService;
    private final ReportService reportService;

    @Autowired
    public Unitat1Controller(Cotxe cotxe,
                             ContadorServiceA serviceA,
                             ContadorServiceB serviceB,
                             TicketService ticketService,
                             RequestService requestService,
                             SessionService sessionService,
                             HeavyService heavyService,
                             ReportService reportService) {
        this.cotxe = cotxe;
        this.serviceA = serviceA;
        this.serviceB = serviceB;
        this.ticketService = ticketService;
        this.requestService = requestService;
        this.sessionService = sessionService;
        this.heavyService = heavyService;
        this.reportService = reportService;
    }

    /* Exposam a "/" un endpoint que retorna Hola Món*/
    @GetMapping("/")
    public String holaMon(){
        return "Hola món!";
    }

    @GetMapping("/cotxe")
    public String conduirCotxe(){
        return cotxe.conduir();
    }

    @GetMapping("/singleton")
    public String singleton(){
        String a = serviceA.print();
        String b = serviceB.print();
        return a + b;
    }

    @GetMapping("/prototype")
    public String prototype(){
        return ticketService.printTicket();
    }

    @GetMapping("/request")
    public String request(){
        return requestService.getRequestBeanId();
    }

    @GetMapping("/session")
    public String session(){
        return sessionService.getSessionBeanId();
    }
    @GetMapping("/heavy")
    public String heavy(){
        return heavyService.doWork();
    }

    @GetMapping("/report")
    public String report(){
        return reportService.createReport();
    }
}
