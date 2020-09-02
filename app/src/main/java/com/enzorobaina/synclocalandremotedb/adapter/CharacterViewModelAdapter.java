package com.enzorobaina.synclocalandremotedb.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

public class CharacterViewModelAdapter extends RecyclerView.Adapter<CharacterViewModelAdapter.CharacterViewHolder>{
    class CharacterViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView idTextView;
        ImageView isSyncedImageView;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
            isSyncedImageView = itemView.findViewById(R.id.isSyncedImageView);
        }
    }

    private final LayoutInflater inflater;
    private List<Character> characters;

    public CharacterViewModelAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_character, parent, false));
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        if (characters != null) {
            Character current = characters.get(position);
            holder.nameTextView.setText(current.getName());
            holder.idTextView.setText(String.valueOf(current.getId()));
            if (current.isSynced()){
                holder.isSyncedImageView.setImageResource(android.R.drawable.presence_online);
            }
            else {
                holder.isSyncedImageView.setImageResource(android.R.drawable.presence_busy);
            }
        }
    }

    public void setCharacters(List<Character> characters){
        this.characters = characters;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (characters != null)
            return characters.size();
        else return 0;
    }
}
