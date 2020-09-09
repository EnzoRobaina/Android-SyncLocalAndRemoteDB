package com.enzorobaina.synclocalandremotedb.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.enzorobaina.synclocalandremotedb.R;
import com.enzorobaina.synclocalandremotedb.model.Character;
import java.text.SimpleDateFormat;
import java.util.List;

public class CharacterViewModelAdapter extends RecyclerView.Adapter<CharacterViewModelAdapter.CharacterViewHolder>{

    class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView idTextView;
        ImageView isSyncedImageView;
        TextView uuidTextView;
        TextView lastModifiedAtTextView;
        OnCharacterListener onCharacterListener;

        CharacterViewHolder(@NonNull View itemView, OnCharacterListener onCharacterListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
            isSyncedImageView = itemView.findViewById(R.id.isSyncedImageView);
            uuidTextView = itemView.findViewById(R.id.uuidTextView);
            lastModifiedAtTextView = itemView.findViewById(R.id.lastModifiedAtTextView);
            this.onCharacterListener = onCharacterListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCharacterListener.onCharacterClick(getAdapterPosition());
        }
    }

    public interface OnCharacterListener {
        void onCharacterClick(int position);
    }

    private List<Character> characters;
    private OnCharacterListener onCharacterListener;
    public CharacterViewModelAdapter(OnCharacterListener onCharacterListener) { this.onCharacterListener = onCharacterListener; }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_character, parent, false), onCharacterListener);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        if (characters != null) {
            Character current = characters.get(position);
            holder.nameTextView.setText(current.getName());
            holder.idTextView.setText(String.valueOf(current.getId()));
            holder.lastModifiedAtTextView.setText(
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(current.getLastModifiedAt())
            );
            if (current.getUuid() != null){
                holder.uuidTextView.setText(current.getUuid().isEmpty() ? "TBD" : current.getUuid());
            }
            else {
                holder.uuidTextView.setText("TBD");
            }
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
