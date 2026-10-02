package cat.paucasesnovescifp.unitat1.service;

import cat.paucasesnovescifp.unitat1.domain.RellotgeServidor;
import org.springframework.stereotype.Service;

@Service
public class HoraService {

    private final RellotgeServidor rellotgeServidor;

    public HoraService(RellotgeServidor rellotgeServidor) {
        this.rellotgeServidor = rellotgeServidor;
    }

    public String consultarHora() {
        return "Data i hora del servidor: " + rellotgeServidor.dataHoraActual();
    }
}
