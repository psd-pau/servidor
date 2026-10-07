package cat.paucasesnovescifp.unitat1.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {
    private final String id;

    public SessionBean(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
