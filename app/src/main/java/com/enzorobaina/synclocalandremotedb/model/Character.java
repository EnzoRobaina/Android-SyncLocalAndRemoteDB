package com.enzorobaina.synclocalandremotedb.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import java.util.Locale;

@Entity(tableName = Character.tableName)
public class Character implements Parcelable {
    public static final String tableName = "character";
    public static final int UNSYNCED = 0;
    public static final int SYNCED = 1;
    @Expose @PrimaryKey(autoGenerate = true)
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
                "ID: %d, Name: %s, Sync: %s, St: %d, Dex: %d, Const: %d, Intel: %d, Wisd: %d, Char: %d",
                this.id,
                this.name,
                this.isSynced,
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

    protected Character(Parcel in) {
        name = in.readString();
        strength = in.readInt();
        dexterity = in.readInt();
        constitution = in.readInt();
        intelligence = in.readInt();
        wisdom = in.readInt();
        charisma = in.readInt();
        isSynced = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(strength);
        dest.writeInt(dexterity);
        dest.writeInt(constitution);
        dest.writeInt(intelligence);
        dest.writeInt(wisdom);
        dest.writeInt(charisma);
        dest.writeByte((byte) (isSynced ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}