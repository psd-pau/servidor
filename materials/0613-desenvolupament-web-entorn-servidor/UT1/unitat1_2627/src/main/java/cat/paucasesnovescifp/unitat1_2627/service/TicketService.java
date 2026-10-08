package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final Ticket ticket1;
    private final Ticket ticket2;

    @Autowired
    public TicketService(Ticket ticket1, Ticket ticket2){
        this.ticket1 = ticket1;
        this.ticket2 = ticket2;
    }

    public String printTickets(){
        return "ticket1 " + ticket1.getId() +
                " ticket2 " + ticket2.getId();
    }

}
