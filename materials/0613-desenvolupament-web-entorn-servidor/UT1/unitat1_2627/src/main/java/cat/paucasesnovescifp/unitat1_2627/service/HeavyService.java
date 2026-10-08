package cat.paucasesnovescifp.unitat1_2627.service;

import cat.paucasesnovescifp.unitat1_2627.domain.HeavyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class HeavyService {
    private final HeavyBean heavyBean;

    @Autowired
    public HeavyService(@Lazy HeavyBean heavyBean){
        //Lazy tant a injecció com a bean
        this.heavyBean = heavyBean;
    }

    public String doWork(){
        return heavyBean.process();
    }

}
