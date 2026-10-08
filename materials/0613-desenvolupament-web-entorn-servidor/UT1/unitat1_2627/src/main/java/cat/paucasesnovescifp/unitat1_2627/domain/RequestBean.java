package cat.paucasesnovescifp.unitat1_2627.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestBean {
    private final String id;

    public RequestBean(){
        this.id = UUID.randomUUID().toString();
    }
    public String getId(){
        return this.id;
    }

}
