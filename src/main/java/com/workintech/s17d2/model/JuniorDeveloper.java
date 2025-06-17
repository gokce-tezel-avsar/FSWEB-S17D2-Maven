package com.workintech.s17d2.model;

public class JuniorDeveloper extends Developer {
    public JuniorDeveloper(int id, String name, Double salary) {
        super(id, name, salary, Experience.JUNIOR);
        //experiencesi zaten junor adından belli dışardan parametre olarak almaya gerek yok.
    }



}
