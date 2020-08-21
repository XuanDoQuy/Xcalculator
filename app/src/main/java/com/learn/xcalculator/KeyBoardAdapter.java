package com.learn.xcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KeyBoardAdapter extends RecyclerView.Adapter<KeyBoardAdapter.ViewHolder> {

    ArrayList<String> listButton;
    Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public KeyBoardAdapter(ArrayList<String> listButton, Context context) {
        this.listButton = listButton;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_button,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.btn.setText(listButton.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onClick(holder.itemView,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listButton.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        Button btn;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.buttonKeyBoard);

        }
    }
    public interface OnItemClickListener{
        void onClick(View v, int position);
    }
}
