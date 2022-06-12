package com.example.finalproject.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.fragments.SwipeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CardArrayAdapter extends ArrayAdapter<Cards> {
    private Context context;
    public CardArrayAdapter(@NonNull Context context, int resource, List<Cards> item) {
        super(context, resource,item);
        this.context = context;
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
        ImageView info = convertView.findViewById(R.id.btnInfo);
        ImageView profileImageUrl = convertView.findViewById(R.id.image);
        name.setText(card_items.getName());
        age.setText(card_items.getAge());
        Glide.with(getContext()).load(card_items.getProfileImageUrl()).into(profileImageUrl);
        // Bottom sheet info
        View infoLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_page,parent,false);
        TextView infoAddress = infoLayout.findViewById(R.id.address);
        TextView infoName = infoLayout.findViewById(R.id.name);
        TextView infoAge = infoLayout.findViewById(R.id.age);
        TextView infoHobbies = infoLayout.findViewById(R.id.hobbies);
        TextView infoIntroduce = infoLayout.findViewById(R.id.introduce);
        TextView infoSex = infoLayout.findViewById(R.id.userSex);
        TextView infoSchool = infoLayout.findViewById(R.id.school);
        infoAddress.setText(card_items.getAddress());
        infoName.setText(card_items.getName());
        infoAge.setText(card_items.getAge());
        infoHobbies.setText(card_items.getHobbies());
        infoIntroduce.setText(card_items.getIntroduce());
        infoSchool.setText(card_items.getSchool());
        infoSex.setText(card_items.getUserSex());
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(infoLayout);
        info.setOnClickListener(v -> {
            bottomSheetDialog.show();
            Button back = infoLayout.findViewById(R.id.btnBack);
            back.setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
            });
        });
        return convertView;
    }


}
