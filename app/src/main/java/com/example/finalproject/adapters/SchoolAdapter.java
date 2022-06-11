package com.example.finalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Matches.MatchesObject;
import com.example.finalproject.R;
import com.example.finalproject.RegisterInfo.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder> implements Filterable {
    private List<String> schools, schoolsAll;
    private ItemClickListener itemClickListener;
    public SchoolAdapter(List<String> schools, ItemClickListener itemClickListener){
        this.schools = schools;
        this.itemClickListener = itemClickListener;
        this.schoolsAll = schools;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String school = schools.get(position);
        holder.mSchool.setText(school);
        holder.mSchool.setOnClickListener(view -> {
            itemClickListener.onClick(school);
        });
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(schoolsAll);
            }else{
                for(String school: schoolsAll){
                    if(school.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(school);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            schools = (List<String>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mSchool;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSchool = itemView.findViewById(R.id.school);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
