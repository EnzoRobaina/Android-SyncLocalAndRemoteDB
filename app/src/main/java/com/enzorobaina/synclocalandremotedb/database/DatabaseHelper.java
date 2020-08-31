package com.enzorobaina.synclocalandremotedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import androidx.annotation.Nullable;

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
    public static final String CHARACTER_KEY_SYNC = "isSynced";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";

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
                        "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TINYINT)",
                        CHARACTER_TABLE_NAME,
                        KEY_ID,
                        KEY_NAME,
                        CHARACTER_KEY_STRENGTH,
                        CHARACTER_KEY_DEXTERITY,
                        CHARACTER_KEY_CONSTITUTION,
                        CHARACTER_KEY_INTELLIGENCE,
                        CHARACTER_KEY_WISDOM,
                        CHARACTER_KEY_CHARISMA,
                        CHARACTER_KEY_SYNC
                )
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.dropTable(CHARACTER_TABLE_NAME, sqLiteDatabase);
        this.onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        this.dropTable(CHARACTER_TABLE_NAME, sqLiteDatabase);
        this.onCreate(sqLiteDatabase);
    }

    private void deleteAllValuesFrom(String tableName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+ tableName);
    }

    public boolean isCharacterDatabaseEmpty(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + CHARACTER_TABLE_NAME + "'", null)) {
            return (cursor.getCount() <= 0) || (DatabaseUtils.queryNumEntries(sqLiteDatabase, CHARACTER_TABLE_NAME) <= 0);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private void dropTable(String tableName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + tableName);
    }

    private void dropTable(String tableName, SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(SQL_DROP_TABLE + tableName);
    }

    public long createCharacter(ContentValues values){
        try (SQLiteDatabase sqLiteDatabase = getWritableDatabase()) {
            return sqLiteDatabase.insert(CHARACTER_TABLE_NAME,null, values);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int updateSync(String characterId, ContentValues values){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update(CHARACTER_TABLE_NAME, values, KEY_ID + " = ?", new String[]{ String.valueOf(characterId) });
    }

    public Cursor getCharacterCursor(@Nullable String[] selectionArgs, @Nullable String sortOrder){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CHARACTER_TABLE_NAME);
        Cursor cursor = null;
        try {
            cursor = queryBuilder.query(getReadableDatabase(), null, KEY_ID + "=?", selectionArgs, null, null, sortOrder);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }

    public Cursor getMultipleCharacterCursor(@Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CHARACTER_TABLE_NAME);
        Cursor cursor = null;
        try {
            cursor = queryBuilder.query(getReadableDatabase(), null, selection, selectionArgs, null, null, sortOrder);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return cursor;
    }
}
