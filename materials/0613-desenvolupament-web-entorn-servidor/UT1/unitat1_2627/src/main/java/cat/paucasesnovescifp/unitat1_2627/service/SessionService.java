package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.RequestBean;
import cat.paucasesnovescifp.unitat1_2627.domain.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionBean sessionBean;

    @Autowired
    public SessionService(SessionBean sessionBean){
        this.sessionBean = sessionBean;
    }

    public String getSessionBeanId(){
        return sessionBean.getId();
    }

}
