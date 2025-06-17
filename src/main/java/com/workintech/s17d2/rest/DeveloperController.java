package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.workintech.s17d2.tax.Taxable;

import java.util.*;

@RestController//
@RequestMapping("/developers")
public class DeveloperController {
    public Map<Integer, Developer> developers;//normalde Mapleri private yaparız ama testlerde koşmamız gerektiği için public
    private Taxable taxable;

    @Autowired//taxable interface ancak autowired ile component annotation olan classı inject ediyor
    //interface sadee bir sınıfa implement oluyorsa qualifier annatation kullanmaya gerek yok. singleton kuralına göre sadece bir sınfıtan instance alacağını biliyor
    //ancak taxabla developertax harici başka bir claasda daha impement olsaydı injection yapardan @qualifier ile bunun belirtmemiz gerkirdi
    public DeveloperController(/*@Qualifier("developerTax")*/Taxable taxable) {
        this.taxable = taxable;
    }
    @PostConstruct
    public void init(){
        this.developers = new HashMap<>();
        this.developers.put(1,new JuniorDeveloper(1,"gökçe",1000d));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//201 gelmesi için
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        if (Objects.nonNull(createdDeveloper)){
            developers.put(createdDeveloper.getId(),createdDeveloper);
        }
        return new DeveloperResponse(createdDeveloper,HttpStatus.CREATED.value(), "create işlmei başarılı");
    }

    @GetMapping
    public List<Developer> getAllDevelopers(){
     return developers.values().stream().toList();
   }

   @GetMapping("/{id}")
    public DeveloperResponse getDevelopersById(@PathVariable int id){
        Developer foundedDeveloper = this.developers.get(id);
        if(foundedDeveloper == null){
            return new DeveloperResponse(null, HttpStatus.NOT_FOUND.value(), id + "ile search sonuuc kayıt bulunamdı.");
        }
        return  new DeveloperResponse(foundedDeveloper, HttpStatus.OK.value(), "id ile serach başarılı.");
   }

   @PutMapping("/{id}")
   public DeveloperResponse update(@PathVariable int id, @RequestBody Developer developer){
        developer.setId(id);
        Developer newDeveloper = DeveloperFactory.createDeveloper(developer,taxable);
        this.developers.put(id,newDeveloper);
        return  new DeveloperResponse(newDeveloper, HttpStatus.OK.value(), "update başarılı");
   }

   @DeleteMapping("/{id}")
   public DeveloperResponse delete(@PathVariable int id){
        Developer removedDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(removedDeveloper, HttpStatus.NO_CONTENT.value(), "silme  başarılı");
   }
}
