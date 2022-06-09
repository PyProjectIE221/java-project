package com.example.finalproject.Matches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Chat.ChatActivity;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> implements Filterable{
    private List<MatchesObject> matchesObjectList, matchesObjectListAll;
    private Context mContext;

    public MatchesAdapter(List<MatchesObject> matchesObjectList, Context mContext){
        this.matchesObjectList = matchesObjectList;
        this.mContext = mContext;
        this.matchesObjectListAll = matchesObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchesObject obj = matchesObjectList.get(position);
        holder.mMatchName.setText(obj.getUserName());
        holder.mMatchId.setText(obj.getUserId());
        Glide.with(mContext).load(obj.getImageUrl()).into(holder.mMatchImage);
    }

    @Override
    public int getItemCount() {
        return matchesObjectList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<MatchesObject> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(matchesObjectListAll);
            }else{
                for (MatchesObject item: matchesObjectListAll){
                    if(item.getUserName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            matchesObjectList = (List<MatchesObject>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mMatchName,mMatchId;
        public ImageView mMatchImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mMatchId = itemView.findViewById(R.id.MatchId);
            mMatchName = itemView.findViewById(R.id.MatchName);
            mMatchImage = itemView.findViewById(R.id.MatchImage);

        }



        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ChatActivity.class);
            Bundle b = new Bundle();
            b.putString("matchId",mMatchId.getText().toString());
            intent.putExtras(b);
            view.getContext().startActivity(intent);
        }
    }
}
