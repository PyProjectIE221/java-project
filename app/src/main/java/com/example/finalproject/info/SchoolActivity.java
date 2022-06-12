package com.example.finalproject.info;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.RegisterInfo.ItemClickListener;
import com.example.finalproject.adapters.SchoolAdapter;
import com.example.finalproject.utils.VerticalSpaceItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class SchoolActivity extends DialogFragment {
    private List<String> schools;
    private ItemClickListener itemClickListener;

    public SchoolActivity(List<String> schools, ItemClickListener itemClickListener){
        this.schools = schools;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        Dialog bottomSheetDialog = (Dialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_school,null);
        bottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SearchView mFilter = view.findViewById(R.id.filter);

        SchoolAdapter schoolAdapter = new SchoolAdapter(schools, new ItemClickListener() {
            @Override
            public void onClick(String s) {
                itemClickListener.onClick(s);
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onRemove(String s) {

            }
        });
        recyclerView.setAdapter(schoolAdapter);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10,0));

        mFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                schoolAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                schoolAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return bottomSheetDialog;
    }
}