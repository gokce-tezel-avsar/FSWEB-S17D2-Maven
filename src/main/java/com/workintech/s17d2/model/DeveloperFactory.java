package com.workintech.s17d2.model;

import com.workintech.s17d2.tax.Taxable;

//experince'a göre developerleri yönettimiz class
public class DeveloperFactory {

    public static Developer createDeveloper(Developer developer, Taxable taxable) {
        Developer createdDeveloper = null;
//createdDeveloper experince levelina göre
// if ile kontrol edilip
// taxabladan gelen taxrate ile
// maaşları hesaplanarak developer yaratılıyor
        if (developer.getExperience().equals(Experience.JUNIOR)) {
            createdDeveloper = new JuniorDeveloper(developer.getId(), developer.getName(),
                    developer.getSalary() - (developer.getSalary() * taxable.getSimpleTaxRate()) / 100);

        } else if (developer.getExperience().equals(Experience.MID)) {
            createdDeveloper = new MidDeveloper(developer.getId(), developer.getName(),
                    developer.getSalary() - (developer.getSalary() * taxable.getMiddleTaxRate()) / 100);

        } else if (developer.getExperience().equals(Experience.SENIOR)) {
            createdDeveloper = new SeniorDeveloper(developer.getId(), developer.getName(),
                    developer.getSalary() - (developer.getSalary() * taxable.getUpperTaxRate()) / 100);

        } else {
            return null;
        }
        return createdDeveloper;
    }
}
