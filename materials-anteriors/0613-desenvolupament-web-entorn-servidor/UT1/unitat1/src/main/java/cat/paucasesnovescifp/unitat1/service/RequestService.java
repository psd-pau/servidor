package cat.paucasesnovescifp.unitat1.service;

import cat.paucasesnovescifp.unitat1.domain.RequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private final RequestBean requestBean;

    @Autowired
    public RequestService(RequestBean requestBean){
        this.requestBean = requestBean;
    }

    public String getRequestBeanId() {
        return requestBean.getId();
    }
}
