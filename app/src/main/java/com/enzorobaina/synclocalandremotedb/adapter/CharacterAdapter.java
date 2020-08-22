package com.enzorobaina.synclocalandremotedb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Character> characterList;

    public CharacterAdapter(List<Character> characterList){
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_character, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characterList.get(position);
        holder.idTextView.setText(String.valueOf(character.getId()));
        holder.nameTextView.setText(character.getName());
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView idTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
        }
    }
}
