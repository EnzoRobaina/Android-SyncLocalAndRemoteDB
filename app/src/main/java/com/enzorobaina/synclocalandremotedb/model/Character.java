package com.enzorobaina.synclocalandremotedb.model;

import com.google.gson.annotations.Expose;

import java.util.Locale;

public class Character {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private int strength;
    @Expose
    private int dexterity;
    @Expose
    private int constitution;
    @Expose
    private int intelligence;
    @Expose
    private int wisdom;
    @Expose
    private int charisma;
    private boolean isSynced;

    public Character(int id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = false;
    }

    public Character(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = false;
    }

    public Character(int id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, boolean isSynced) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = isSynced;
    }

    public Character(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, boolean isSynced) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = isSynced;
    }

    public Character(int id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int isSynced) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = isSynced == 1;
    }

    public Character(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int isSynced) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = isSynced == 1;
    }

    public Character(){}

    public void setId(int id){
        this.id = id;
    }

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

    public boolean isSynced(){
        return this.isSynced;
    }

    public int isSyncedAsInt(){
        return (this.isSynced) ? 1 : 0;
    }

    public void setSynced(boolean synced){
        this.isSynced = synced;
    }

    public void setSynced(int synced){
        this.isSynced = synced == 1;
    }
}
