package cat.paucasesnovescifp.unitat1_2627.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hash")
public class ProvaHashController {

    @GetMapping
    public void hash(){
        //Instanciam HashMap<K, V>
        HashMap<Integer, String> productes = new HashMap<>();

        //Afegir elements
        productes.put(1, "Ceba");
        productes.put(2, "Patata");
        productes.put(3, "Pastanaga");

        //Obtenir element .get(K)
        String producte = productes.get(1);
        System.out.println(producte);

        //Conte element?
        boolean producteExisteix = productes.containsKey(4); //també containsValue
        System.out.println(producteExisteix);

        //Eliminam element
        System.out.println(productes.size());
        productes.remove(3);
        System.out.println(productes.size());

        //Recorregut. Recomanada a Java modern. Expressió lambda
        productes.forEach((id, nom) -> {
            System.out.println("Producte amb id " + id + " i nom " + nom);
        });

        //recorregut tradicional
        for(Map.Entry<Integer, String> element : productes.entrySet()){
            System.out.println(element.getKey().toString() + element.getValue());
        }


    }

}
