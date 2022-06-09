package com.example.finalproject.RegisterInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<String> arrayList;
    ItemClickListener itemClickListener;
    int selectedPosition = -1;
    public MainAdapter(ArrayList<String> arrayList, ItemClickListener itemClickListener){
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hobbies,parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
//        int height = parent.getMeasuredHeight() / 4;
//        view.setMinimumHeight(height);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkBox.setText(arrayList.get(position));
//        holder.checkBox.setChecked(position == selectedPosition);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
//                    selectedPosition = holder.getAdapterPosition();
                    itemClickListener.onClick(holder.checkBox.getText().toString());
                }else{
                    itemClickListener.onRemove(holder.checkBox.getText().toString());
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.hobbies1);
        }
    }
}
