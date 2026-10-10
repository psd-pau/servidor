package cat.paucasesnovescifp.solucio_practica_1.controller;

import cat.paucasesnovescifp.solucio_practica_1.service.ExportEngine;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report/export")
public class ExportController {
    private final ExportEngine exportEngine;

    public ExportController(@Lazy ExportEngine exportEngine) {
        this.exportEngine = exportEngine;
    }

    @GetMapping
    public String exportAllReports() {
        return exportEngine.exportAll();
    }
}
