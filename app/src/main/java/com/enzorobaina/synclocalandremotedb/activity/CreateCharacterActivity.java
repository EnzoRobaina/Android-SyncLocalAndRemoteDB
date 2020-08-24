package com.enzorobaina.synclocalandremotedb.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.api.Syncer;
import com.enzorobaina.synclocalandremotedb.api.VoidCallback;
import com.enzorobaina.synclocalandremotedb.database.DatabaseHelper;
import com.enzorobaina.synclocalandremotedb.model.Character;

public class CreateCharacterActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText nameEditText;
    EditText strengthEditText;
    EditText dexterityEditText;
    EditText constitutionEditText;
    EditText intelligenceEditText;
    EditText wisdomEditText;
    EditText charismaEditText;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        initComponents();
    }

    private void initComponents(){
        nameEditText = findViewById(R.id.nameEditText);
        strengthEditText = findViewById(R.id.strengthEditText);
        dexterityEditText = findViewById(R.id.dexterityEditText);
        constitutionEditText = findViewById(R.id.constitutionEditText);
        intelligenceEditText = findViewById(R.id.intelligenceEditText);
        wisdomEditText = findViewById(R.id.wisdomEditText);
        charismaEditText = findViewById(R.id.charismaEditText);
        submit = findViewById(R.id.btnSubmit);
        databaseHelper = DatabaseHelper.getInstance(this);
    }

    private String _s(EditText editText){
        return editText.getText().toString();
    }

    private int _i(EditText editText){
        return Integer.parseInt(editText.getText().toString());
    }

    public void onSubmit(View view){
        Character character = new Character();
        character.setName(_s(nameEditText));
        character.setStrength(_i(strengthEditText));
        character.setDexterity(_i(dexterityEditText));
        character.setConstitution(_i(constitutionEditText));
        character.setIntelligence(_i(intelligenceEditText));
        character.setWisdom(_i(wisdomEditText));
        character.setCharisma(_i(charismaEditText));

        long recentlyCreatedCharacterId = databaseHelper.createCharacter(character);

        if (recentlyCreatedCharacterId > 0){
            character.setId((int) recentlyCreatedCharacterId); // TODO: Figure a better way to set the id
            Syncer syncer = Syncer.getInstance(this);
            syncer.syncOne(character, new VoidCallback() {
                @Override
                public void onSuccess() {
                    _startListActivity();
                }

                @Override
                public void onFail() {
                    _startListActivity();
                }
            });
        }
        else {
            Log.d("recentlyCreatedCharId", "< 0");
        }
    }

    private void _startListActivity(){
        startActivity(new Intent(this, ListCharacterActivity.class));
    }
}
