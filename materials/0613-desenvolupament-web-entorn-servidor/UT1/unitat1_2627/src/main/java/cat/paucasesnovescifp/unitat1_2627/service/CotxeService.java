package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.Cotxe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CotxeService {

    private final Cotxe cotxe;

    @Autowired
    public CotxeService(Cotxe cotxe){
        this.cotxe = cotxe;
    }

    public String conduir(){
        String resultat = "Conduint cotxe.\n" + cotxe.engega();
        return resultat;
    }


}
