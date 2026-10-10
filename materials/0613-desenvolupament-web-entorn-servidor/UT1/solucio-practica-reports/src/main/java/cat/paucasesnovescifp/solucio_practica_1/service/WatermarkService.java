package cat.paucasesnovescifp.solucio_practica_1.service;


import org.springframework.stereotype.Service;

/**
 * Servei encarregat d'afegir una "marca d'aigua" (watermark)
 * al contingut dels informes generats.
 *
 *     - Servei independent i reutilitzable.
 *     - Demostra la injecció de dependències entre serveis:
 *         ReportService → WatermarkService
 *     - Mostra com una tasca petita pot encapsular-se en un bean propi.
 */
@Service
public class WatermarkService {

    private static final String WATERMARK = "\n\n---\nInforme generat per WatermarkService";

    /**
     * Afegeix una marca d'aigua textual al contingut d'un informe.
     *
     * @param content contingut original de l'informe
     * @return contingut amb la marca d'aigua afegida
     */
    public String apply(String content) {
        System.out.println(" Afegint watermark a l’informe...");
        return content + WATERMARK;
    }
}
