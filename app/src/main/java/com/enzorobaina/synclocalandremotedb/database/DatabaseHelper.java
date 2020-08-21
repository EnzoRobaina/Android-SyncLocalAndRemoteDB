package com.enzorobaina.synclocalandremotedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.enzorobaina.synclocalandremotedb.model.Character;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;

    public static final String DB_NAME = "synclocalandremotedb";
    public static final int DB_VERSION = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    public static final String CHARACTER_TABLE_NAME = "character";
    public static final String CHARACTER_KEY_STRENGTH = "strength";
    public static final String CHARACTER_KEY_DEXTERITY = "dexterity";
    public static final String CHARACTER_KEY_CONSTITUTION = "constitution";
    public static final String CHARACTER_KEY_INTELLIGENCE = "intelligence";
    public static final String CHARACTER_KEY_WISDOM = "wisdom";
    public static final String CHARACTER_KEY_CHARISMA = "charisma";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";
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

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null){
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                String.format(
                        "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER)",
                        CHARACTER_TABLE_NAME,
                        KEY_ID,
                        KEY_NAME,
                        CHARACTER_KEY_STRENGTH,
                        CHARACTER_KEY_DEXTERITY,
                        CHARACTER_KEY_CONSTITUTION,
                        CHARACTER_KEY_INTELLIGENCE,
                        CHARACTER_KEY_WISDOM,
                        CHARACTER_KEY_CHARISMA
                )
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.dropTable(CHARACTER_TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        this.dropTable(CHARACTER_TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    private void deleteAllValuesFrom(String tableName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+ tableName);
    }

    private void dropTable(String tableName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + tableName);
    }

    public long createCharacter(Character character){
        try (SQLiteDatabase sqLiteDatabase = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, character.getId());
            values.put(KEY_NAME, character.getName());
            values.put(CHARACTER_KEY_STRENGTH, character.getStrength());
            values.put(CHARACTER_KEY_DEXTERITY, character.getDexterity());
            values.put(CHARACTER_KEY_CONSTITUTION, character.getConstitution());
            values.put(CHARACTER_KEY_INTELLIGENCE, character.getIntelligence());
            values.put(CHARACTER_KEY_WISDOM, character.getWisdom());
            values.put(CHARACTER_KEY_CHARISMA, character.getCharisma());
            return sqLiteDatabase.insert(CHARACTER_TABLE_NAME,null, values);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Character getCharacter(int id) {
        Character character = null;
        try (
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery(
                        "SELECT * FROM " + CHARACTER_TABLE_NAME + " WHERE " + KEY_ID + " =? " + "LIMIT 1",
                        new String[]{String.valueOf(id)}
                )
        ) {
            if (cursor != null) {
                cursor.moveToFirst();
                character = new Character(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return character;
    }
}
