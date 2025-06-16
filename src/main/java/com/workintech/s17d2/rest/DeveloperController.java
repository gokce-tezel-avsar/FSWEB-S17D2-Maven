package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.Developer;
import org.springframework.web.bind.annotation.*;
import com.workintech.s17d2.tax.Taxable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class DeveloperController {
    public Map<Integer, Developer> developers;
    private Taxable taxCalculate;

    public DeveloperController(Taxable taxCalculate) {
        this.taxCalculate = taxCalculate;
    }

   @GetMapping("/")
    public List<Developer> getDevelopers(){
        List<Developer> developers1 = new ArrayList<>();
        return developers1;
   }

   @GetMapping("/{id}")
    public Developer getDevelopers(@PathVariable int id){
return developers.get(id);
   }


    @PostMapping
    public Developer createDeveloper(@RequestBody Developer dev) {
        double salary = dev.getSalary();

        switch (dev.getExperience()) {
            case JUNIOR:
                salary -= salary * taxCalculate.getSimpleTaxRate();
                break;
            case MID:
                salary -= salary * taxCalculate.getMiddleTaxRate();
                break;
            case SENIOR:
                salary -= salary * taxCalculate.getUpperTaxRate();
                break;
            default:
                throw new IllegalArgumentException("Geçersiz deneyim seviyesi: " + dev.getExperience());
        }

        dev.setSalary(salary);
        developers.put(dev.getId(), dev);
        return dev;
    }

    @DeleteMapping("/{id}")
    public String deleteDeveloper(@PathVariable int id) {
        developers.remove(id);
        return "Developer with id " + id + " deleted.";
    }

    public void init() {
    }
}
