package com.enzorobaina.synclocalandremotedb.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.enzorobaina.synclocalandremotedb.converters.DateConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.Locale;

/*
    "id": 4,
    "uuid": "e0054a7b-e431-45a3-9cb6-cf20fc2bda4a",
    "name": "Second",
    "strength": 1,
    "dexterity": 2,
    "constitution": 3,
    "intelligence": 4,
    "wisdom": 5,
    "charisma": 6,
    "proficiency_bonus": 2,
    "created_at": "2020-09-04T19:27:19.212070",
    "last_modified_at": "2020-09-04T19:27:19.211071",
*/

@Entity(tableName = Character.tableName)
public class Character {
    public static final String tableName = "character";
    public static final int UNSYNCED = 0;
    public static final int SYNCED = 1;
    @Expose
    private String uuid;
    @Expose @PrimaryKey(autoGenerate = true)
    private long id;
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
    @SerializedName("created_at") @TypeConverters(DateConverter.class)
    private Date createdAt;
    @SerializedName("last_modified_at") @TypeConverters(DateConverter.class)
    private Date lastModifiedAt;

    public Character(long id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
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

    public Character(long id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, boolean isSynced, Date createdAt, Date lastModifiedAt) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.isSynced = isSynced;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
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

    public Character(long id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, boolean isSynced) {
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

    public Character(long id, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int isSynced) {
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

    public void setId(long id){
        this.id = id;
    }

    public long getId() {
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
                "ID: %d, UUID: %s, Name: %s, Sync: %s, C_At: %s, M_At: %s, St: %d, Dex: %d, Const: %d, Intel: %d, Wisd: %d, Char: %d",
                this.id,
                (this.uuid.isEmpty() ? "TBD" : this.uuid),
                this.name,
                this.isSynced,
                this.createdAt.toString(),
                this.lastModifiedAt.toString(),
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}