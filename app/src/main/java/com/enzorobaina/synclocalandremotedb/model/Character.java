package com.enzorobaina.synclocalandremotedb.model;

import java.util.Locale;

public class Character {
    /*
        "id": 1,
        "name": "Foo",
        "strength": 1,
        "dexterity": 3,
        "constitution": 2,
        "intelligence": 4,
        "wisdom": 2,
        "charisma": 3,
    */
    private int id;
    private String name;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    public Character(int id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public Character(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public Character(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String toString(){
        return String.format(
                Locale.ENGLISH,
                "ID: %d, Name: %s, St: %d, Dex: %d, Const: %d, Intel: %d, Wisd: %d, Char: %d",
                this.id,
                this.name,
                this.strength,
                this.dexterity,
                this.constitution,
                this.intelligence,
                this.wisdom,
                this.charisma
        );
    }
}
