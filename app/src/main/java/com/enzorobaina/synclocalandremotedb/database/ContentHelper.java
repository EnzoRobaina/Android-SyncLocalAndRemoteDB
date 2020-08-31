package com.enzorobaina.synclocalandremotedb.database;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import com.enzorobaina.synclocalandremotedb.model.Character;
import java.util.ArrayList;
import java.util.List;

public class ContentHelper {
    private ContentResolver contentResolver;
    private static ContentHelper contentHelper;
    public static synchronized ContentHelper getInstance(@NonNull Context context){
        if (contentHelper == null){
            contentHelper = new ContentHelper(context);
        }
        return contentHelper;
    }

    private ContentHelper(Context context){
        contentResolver = context.getContentResolver();
    }

    public long createCharacter(Character character){
        ContentValues characterValues = new ContentValues();
        characterValues.put(DatabaseHelper.KEY_NAME, character.getName());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_STRENGTH, character.getStrength());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_DEXTERITY, character.getDexterity());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_CONSTITUTION, character.getConstitution());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_INTELLIGENCE, character.getIntelligence());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_WISDOM, character.getWisdom());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_CHARISMA, character.getCharisma());
        characterValues.put(DatabaseHelper.CHARACTER_KEY_SYNC, character.isSyncedAsInt());
        Uri createdCharacterURI = contentResolver.insert(CharacterContentProvider.CONTENT_URI, characterValues);
        if (createdCharacterURI == null || createdCharacterURI.getLastPathSegment() == null){
            return -1;
        }
        return Long.parseLong(createdCharacterURI.getLastPathSegment());
    }

    public List<Character> getAllCharacters() {
        List<Character> characters = new ArrayList<>();
        try (Cursor cursor = contentResolver.query(CharacterContentProvider.CONTENT_URI, null, null, null, "ID ASC")) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    characters.add(
                        new Character(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getInt(6),
                            cursor.getInt(7),
                            cursor.getInt(8)
                        )
                    );
                }

            }
        }
        catch (SQLException e) { e.printStackTrace(); }
        return characters;
    }

    public List<Character> getAllCharactersWithSync(boolean syncStatus) {
        String selection = DatabaseHelper.CHARACTER_KEY_SYNC + "= ?";
        String[] selectionArgs = new String[]{ (syncStatus) ? "1" : "0" };
        List<Character> characters = new ArrayList<>();
        try (Cursor cursor = contentResolver.query(CharacterContentProvider.CONTENT_URI, null, selection, selectionArgs, "ID ASC")) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    characters.add(
                        new Character(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getInt(6),
                            cursor.getInt(7),
                            cursor.getInt(8)
                        )
                    );
                }
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
        return characters;
    }

    public boolean updateSync(int characterId, boolean syncStatus){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CHARACTER_KEY_SYNC, (syncStatus) ? 1 : 0); // TODO: Refactor me
        Uri characterURI = ContentUris.withAppendedId(CharacterContentProvider.CONTENT_URI, characterId);
        return contentResolver.update(characterURI, values, null, null) > 0;
    }
}
