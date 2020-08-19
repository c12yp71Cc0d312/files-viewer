package com.example.onsitetask1;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.DirectoryViewHolder> {

    private ArrayList<DirectoryCard> directories;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public static class DirectoryViewHolder extends RecyclerView.ViewHolder {

        public ImageView arrow;
        public TextView fileName;

        public DirectoryViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            arrow = itemView.findViewById(R.id.imageViewArrow);
            fileName = itemView.findViewById(R.id.textViewDirectory);

            fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public AdapterClass(ArrayList<DirectoryCard> directoryCards) {
        directories = directoryCards;
    }

    @NonNull
    @Override
    public DirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_directory, parent, false);
        DirectoryViewHolder directoryViewHolder = new DirectoryViewHolder(v, clickListener);
        return directoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryViewHolder holder, int position) {
        DirectoryCard currentDirectory = directories.get(position);
        holder.fileName.setText(currentDirectory.getDirectoryName());
        holder.arrow.setImageResource(currentDirectory.getImgRsc());
    }

    @Override
    public int getItemCount() {
        return directories.size();
    }


}
