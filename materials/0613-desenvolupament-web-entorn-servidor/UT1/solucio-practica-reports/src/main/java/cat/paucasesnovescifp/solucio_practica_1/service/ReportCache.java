package cat.paucasesnovescifp.solucio_practica_1.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Servei que actua com a "cache" temporal per als informes generats.
 *
 *     - Simula una capa de persistència en memòria.
 *     - Mostra clarament el cicle de vida d’un bean:
 *         ➜ @PostConstruct → inicialització amb dades
 *         ➜ @PreDestroy    → alliberament de recursos
 */
@Service
public class ReportCache {

    private final Map<String, String> cache = new HashMap<>();

    /**
     * Mètode executat automàticament per Spring
     *     després de crear el bean i injectar totes les dependències.
     *
     * Aquí carregam dades de mostra per simular
     * que la cache ja conté informes anteriors.
     */
    @PostConstruct
    public void init() {
        cache.put("html_1", "<html><body><h1>Informe inicial HTML</h1></body></html>");
        cache.put("pdf_1", "[PDF] Informe inicial PDF");
        cache.put("json_1", "{ 'report': 'informe inicial JSON' }");
        cache.put("xml_1", "<report>Informe inicial XML</report>");

        System.out.println("ReportCache inicialitzat amb informes de mostra:");
        cache.keySet().forEach(k -> System.out.println(" - " + k.toUpperCase()));
    }

    /**
     *  Mètode executat automàticament quan el context Spring es tanca.
     *
     * En aquest cas, buidam completament la cache per simular
     * l’alliberament de recursos o tancament de connexions.
     */
    @PreDestroy
    public void shutdown() {
        System.out.println("Destruint ReportCache. Contingut actual abans de buidar:");
        cache.forEach((k, v) -> System.out.println(" - " + k + " : " + v.length() + " caràcters"));
        cache.clear();
        System.out.println("Cache buidada completament abans del tancament de l’aplicació.");
    }

    /** Desa un informe a la cache */
    public void store(String id, String content) {
        cache.put(id, content);
        System.out.println("Informe " + id.toUpperCase() + " desat a la cache.");
    }

    /** Recupera un informe desat per format */
    public String get(String format) {
        return cache.get(format);
    }

    /** Retorna el contingut complet de la cache (per proves) */
    public Map<String, String> getAll() {
        return cache;
    }

    /** Buida la cache completament (manualment) */
    public void clear() {
        cache.clear();
        System.out.println("Cache buidada manualment.");
    }
}