package cat.paucasesnovescifp.unitat1.service;

import cat.paucasesnovescifp.unitat1.domain.HeavyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service //singleton, eager
public class HeavyService {
    private final HeavyBean heavyBean;

    @Autowired
    public HeavyService(@Lazy HeavyBean heavyBean){
        this.heavyBean = heavyBean;
    }
    /*@Autowired
    public HeavyService(HeavyBean heavyBean){
        this.heavyBean = heavyBean;
    }*/

    public String doWork(){
        return heavyBean.process();
    }
}
