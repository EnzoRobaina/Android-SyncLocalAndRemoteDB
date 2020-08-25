package com.enzorobaina.synclocalandremotedb.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class CharacterContentProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;
    private static final int SINGLE_CHARACTER = 1;
    private static final int ALL_CHARACTERS = 2;
    private static final String AUTHORITY = "com.enzorobaina.synclocalandremotedb";
    private static final String CURSOR_DIR = "vnd.android.cursor.dir/";
    private static final String CURSOR_ITEM = "vnd.android.cursor.item/";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DatabaseHelper.CHARACTER_TABLE_NAME);

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DatabaseHelper.CHARACTER_TABLE_NAME, ALL_CHARACTERS);
        uriMatcher.addURI(AUTHORITY, DatabaseHelper.CHARACTER_TABLE_NAME + "/#", SINGLE_CHARACTER);
    }
    @Override
    public boolean onCreate() {
        databaseHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)){
            case ALL_CHARACTERS:
                return databaseHelper.getMultipleCharacterCursor(selection, selectionArgs, sortOrder);
            case SINGLE_CHARACTER:
                return databaseHelper.getCharacterCursor(selectionArgs, sortOrder == null ? "ASC" : sortOrder);
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALL_CHARACTERS:
                return CURSOR_DIR + AUTHORITY + "/" + DatabaseHelper.CHARACTER_TABLE_NAME;
            case SINGLE_CHARACTER:
                return CURSOR_ITEM + AUTHORITY + "/" + DatabaseHelper.CHARACTER_TABLE_NAME;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (uriMatcher.match(uri)){
            case SINGLE_CHARACTER:
                long id = databaseHelper.createCharacter(contentValues);
                if (id > 0){
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(CONTENT_URI, id);
                }
                else {
                    throw new SQLException("Insertion error for: " + uri);
                }
            default:
                return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (uriMatcher.match(uri)){
            case SINGLE_CHARACTER:
                String id = uri.getPathSegments().get(1);
                int affectedRows = databaseHelper.updateSync(id, contentValues);
                if (affectedRows > 0){
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                    return affectedRows;
                }
            default:
                return 0;
        }
    }
}
