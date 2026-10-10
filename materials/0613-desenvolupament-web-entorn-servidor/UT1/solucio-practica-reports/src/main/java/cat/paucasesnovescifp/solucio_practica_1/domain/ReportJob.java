package cat.paucasesnovescifp.solucio_practica_1.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Scope("prototype")
public class ReportJob {
    private String format;
    private final String id;
    private LocalDateTime timestamp;

    public ReportJob() {
        this.id = UUID.randomUUID().toString();
    }

    public void init(String format) {
        this.format = format;
        this.timestamp = LocalDateTime.now();
    }

    public String describe() {
        return String.format("[%s] Informe %s generat: %s",
                timestamp, format.toUpperCase(), id);
    }

    public String getFormat() { return format; }
    public String getId() { return id; }
    public String getTitle() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }

}
