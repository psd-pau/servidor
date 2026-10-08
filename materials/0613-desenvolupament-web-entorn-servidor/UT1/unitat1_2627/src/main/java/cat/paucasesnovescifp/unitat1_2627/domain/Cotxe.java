package cat.paucasesnovescifp.unitat1_2627.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Cotxe {
    private final Motor motor;
    private Gps gps;

    @Autowired
    public Cotxe(@Qualifier("motorBenzina") Motor motor){
        this.motor = motor;
    }

    public String engega(){
        String resultat = "Engegant motor ->" + motor.engega();
        if (this.gps != null){
            resultat += " | " + this.gps.getPosition();
        }
        //Ternaria
        //resultat += (this.gps != null) ? this.gps.getPosition() : "";
        return resultat;
    }

    @Autowired(required = false)
    public void setGps(Gps gps){
        this.gps = gps;
    }
}
