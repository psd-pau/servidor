package cat.paucasesnovescifp.unitat1.service;

import cat.paucasesnovescifp.unitat1.domain.Gps;
import cat.paucasesnovescifp.unitat1.domain.Motor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Cotxe {

    private final Motor motor;
    private Gps gps;


    @Autowired(required = false)
    public Cotxe(@Qualifier("motorBenzina") Motor motor){
        this.motor = motor;
    }

    @Autowired(required = false)
    public void setGps(Gps gps){
        this.gps = gps;
    }


    public String conduir(){
        String resultat = "Conduint -> " + motor.engega();
        if (this.gps != null){
            resultat += " | " + this.gps.getPosition();
        }
        return resultat;
    }

}
