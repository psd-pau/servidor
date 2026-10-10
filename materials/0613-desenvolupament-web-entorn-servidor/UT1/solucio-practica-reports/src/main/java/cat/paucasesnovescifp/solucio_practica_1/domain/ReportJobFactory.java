package cat.paucasesnovescifp.solucio_practica_1.domain;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class ReportJobFactory {
    private final ObjectProvider<ReportJob> provider;

    public ReportJobFactory(ObjectProvider<ReportJob> provider) {
        this.provider = provider;
    }

    public ReportJob createJob() {
        return provider.getObject();
    }
}
