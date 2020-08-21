package com.enzorobaina.synclocalandremotedb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                        KEY_ID, KEY_NAME,
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
}
