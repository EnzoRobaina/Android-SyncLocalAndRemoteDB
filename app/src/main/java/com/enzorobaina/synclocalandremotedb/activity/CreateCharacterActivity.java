package com.enzorobaina.synclocalandremotedb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.callbacks.LongCallback;
import com.enzorobaina.synclocalandremotedb.callbacks.VoidCallback1;
import com.enzorobaina.synclocalandremotedb.database.ContentHelper;
import com.enzorobaina.synclocalandremotedb.model.Character;
import com.enzorobaina.synclocalandremotedb.model.CharacterViewModel;
import com.enzorobaina.synclocalandremotedb.utils.ViewUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.enzorobaina.synclocalandremotedb.utils.ViewUtils.fieldsAreOk;

public class CreateCharacterActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText strengthEditText;
    EditText dexterityEditText;
    EditText constitutionEditText;
    EditText intelligenceEditText;
    EditText wisdomEditText;
    EditText charismaEditText;
    CharacterViewModel characterViewModel;
    Button submit;
    View spinner;

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
        spinner = findViewById(R.id.spinnerOverlay);
        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);
    }

    private void showSpinner(){
        spinner.bringToFront();
        ViewUtils.animateView(spinner, View.VISIBLE, 0.4f, 200);
    }

    private void hideSpinner(){
        ViewUtils.animateView(spinner, View.GONE, 0, 200);
    }

    private void disableSubmitBtn(){
        if (submit == null){
            return;
        }
        submit.setEnabled(false);
    }

    private void enableSubmitBtn(){
        if (submit == null){
            return;
        }
        submit.setEnabled(true);
    }

    public void onSubmit(View view){
        if (!fieldsAreOk(Arrays.asList(nameEditText, strengthEditText, dexterityEditText, constitutionEditText, intelligenceEditText, wisdomEditText, charismaEditText))){
            return;
        }

        disableSubmitBtn();
        showSpinner();

        Character character = new Character();
        character.setName(ViewUtils.getString(nameEditText));
        character.setStrength(ViewUtils.getInt(strengthEditText));
        character.setDexterity(ViewUtils.getInt(dexterityEditText));
        character.setConstitution(ViewUtils.getInt(constitutionEditText));
        character.setIntelligence(ViewUtils.getInt(intelligenceEditText));
        character.setWisdom(ViewUtils.getInt(wisdomEditText));
        character.setCharisma(ViewUtils.getInt(charismaEditText));

        characterViewModel.insert(character, () -> runOnUiThread(this::finish));
    }

    private void startListActivity(){
        startActivity(new Intent(this, ListCharacterActivity.class));
    }
}
