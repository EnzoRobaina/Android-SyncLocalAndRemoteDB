package com.enzorobaina.synclocalandremotedb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.model.Character;
import com.enzorobaina.synclocalandremotedb.model.CharacterViewModel;
import com.enzorobaina.synclocalandremotedb.utils.ViewUtils;
import java.util.Arrays;

import static com.enzorobaina.synclocalandremotedb.utils.ViewUtils.fieldsAreOk;

public class UpdateCharacterActivity extends AppCompatActivity { // Todo: Use dataBinding!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    EditText nameEditText;
    EditText strengthEditText;
    EditText dexterityEditText;
    EditText constitutionEditText;
    EditText intelligenceEditText;
    EditText wisdomEditText;
    EditText charismaEditText;
    CharacterViewModel characterViewModel;
    Character instance = new Character();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        instance = getIntent().getParcelableExtra("character");
        assert instance != null;
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
        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);

        nameEditText.setText(instance.getName());
        strengthEditText.setText(String.valueOf(instance.getStrength()));
        dexterityEditText.setText(String.valueOf(instance.getDexterity()));
        constitutionEditText.setText(String.valueOf(instance.getConstitution()));
        intelligenceEditText.setText(String.valueOf(instance.getIntelligence()));
        wisdomEditText.setText(String.valueOf(instance.getWisdom()));
        charismaEditText.setText(String.valueOf(instance.getCharisma()));
    }

    public void onSubmit(View view){
        if (!fieldsAreOk(Arrays.asList(nameEditText, strengthEditText, dexterityEditText, constitutionEditText, intelligenceEditText, wisdomEditText, charismaEditText))){
            return;
        }

        instance.setName(ViewUtils.getString(nameEditText));
        instance.setStrength(ViewUtils.getInt(strengthEditText));
        instance.setDexterity(ViewUtils.getInt(dexterityEditText));
        instance.setConstitution(ViewUtils.getInt(constitutionEditText));
        instance.setIntelligence(ViewUtils.getInt(intelligenceEditText));
        instance.setWisdom(ViewUtils.getInt(wisdomEditText));
        instance.setCharisma(ViewUtils.getInt(charismaEditText));

        characterViewModel.update(instance, () -> runOnUiThread(this::finish));
    }
}
