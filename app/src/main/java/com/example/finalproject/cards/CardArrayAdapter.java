package com.example.finalproject.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;

import java.util.List;

public class CardArrayAdapter extends ArrayAdapter<Cards> {

    public CardArrayAdapter(@NonNull Context context, int resource, List<Cards> item) {
        super(context, resource,item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cards card_items = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView age = convertView.findViewById(R.id.age);
        ImageView profileImageUrl = convertView.findViewById(R.id.image);
        name.setText(card_items.getName());
        age.setText(card_items.getAge());
        Glide.with(getContext()).load(card_items.getProfileImageUrl()).into(profileImageUrl);
        return convertView;
    }


}
