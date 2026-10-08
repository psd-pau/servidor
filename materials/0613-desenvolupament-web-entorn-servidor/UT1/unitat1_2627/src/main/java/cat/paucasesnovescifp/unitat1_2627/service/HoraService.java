package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.RellotgeServidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoraService {
    private final RellotgeServidor rellotgeServidor;

    @Autowired
    public HoraService(RellotgeServidor rellotgeServidor){
        this.rellotgeServidor = rellotgeServidor;
    }

    public String consultarHora(){
        return "Data i hora del servidor: " + rellotgeServidor.dataHoraActual();
    }

}
