package com.enzorobaina.synclocalandremotedb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.adapter.CharacterViewModelAdapter;
import com.enzorobaina.synclocalandremotedb.api.Syncer;
import com.enzorobaina.synclocalandremotedb.model.CharacterViewModel;

public class ListCharacterActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CharacterViewModelAdapter adapter;
    private CharacterViewModel characterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_character2);
        recyclerView = findViewById(R.id.listCharacterRecyclerView);
        adapter = new CharacterViewModelAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);

        characterViewModel.getAllCharacters().observe(this, characters -> adapter.setCharacters(characters));

        Syncer.getInstance(this.getApplication()).fetchAndInsert();
    }

    public void addCharacter(View view) {
        startActivity(new Intent(this, CreateCharacterActivity.class));
    }
}
