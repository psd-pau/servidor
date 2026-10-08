package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.RequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton") //El servei segueix essent singleton malgrat els beans injectats siguin request
public class RequestService {

    private final RequestBean requestBean;

    @Autowired
    public RequestService(RequestBean requestBean){
        this.requestBean = requestBean;
    }

    public String getRequestBeanId(){
        return requestBean.getId();
    }

}
