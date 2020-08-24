package com.enzorobaina.synclocalandremotedb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.adapter.CharacterAdapter;
import com.enzorobaina.synclocalandremotedb.api.Syncer;
import com.enzorobaina.synclocalandremotedb.api.VoidCallback;
import com.enzorobaina.synclocalandremotedb.database.DatabaseHelper;

public class ListCharacterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_character2);
        initComponents();
    }

    private void initComponents(){
        databaseHelper = DatabaseHelper.getInstance(this);

        recyclerView = findViewById(R.id.listCharacterRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Syncer syncer = Syncer.getInstance(this);
        syncer.runFirst(new VoidCallback() {
            @Override
            public void onSuccess() {
                fillRecycler();
                Toast.makeText(getApplicationContext(), "First Sync Done!", Toast.LENGTH_LONG).show(); // TODO: Handle this
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show(); // TODO: Handle this
            }
        });
    }

    private void fillRecycler(){
        recyclerView.setAdapter(new CharacterAdapter(databaseHelper.getAllCharacters()));
    }

    public void addCharacter(View view){
        startActivity(new Intent(this, CreateCharacterActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.fillRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.fillRecycler();
    }

    public void syncCharacters(View view){
        Syncer.getInstance(this).syncRemoteWithLocal(new VoidCallback() {
            @Override
            public void onSuccess() {
                fillRecycler();
                Toast.makeText(getApplicationContext(), "Sync Done!", Toast.LENGTH_LONG).show(); // TODO: Handle this
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "Error Syncing", Toast.LENGTH_LONG).show();
            }
        });
    }
}
