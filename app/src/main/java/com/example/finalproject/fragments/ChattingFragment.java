package com.example.finalproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.finalproject.Chat.ChatObject;
import com.example.finalproject.Matches.MatchesAdapter;
import com.example.finalproject.Matches.MatchesAdapterHorizon;
import com.example.finalproject.Matches.MatchesObject;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChattingFragment extends Fragment {
    private RecyclerView recyclerView, recyclerViewHorizontal;
    private MatchesAdapterHorizon mMatchesAdapterHorizon;
    private MatchesAdapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManager, mMatchesLayoutManagerHorizontal;
    private String currentUserId;
    private Context mContext;
    private SearchView mFilter;


    @Override
    public void onResume() {
        super.onResume();
        mFilter.setQuery("", false);
        getUserMatchId();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getContext();
        mFilter = view.findViewById(R.id.filter);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        mMatchesLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mMatchesLayoutManager);
        mMatchesAdapter = new MatchesAdapter(getMatches(),mContext);
        recyclerView.setAdapter(mMatchesAdapter);


        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);
        recyclerViewHorizontal.setNestedScrollingEnabled(false);
        recyclerViewHorizontal.setHasFixedSize(true);
        mMatchesLayoutManagerHorizontal = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewHorizontal.setLayoutManager(mMatchesLayoutManagerHorizontal);
        mMatchesAdapterHorizon = new MatchesAdapterHorizon(getMatches(),mContext);
        recyclerViewHorizontal.setAdapter(mMatchesAdapterHorizon);

        getUserMatchId();


        mFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMatchesAdapter.getFilter().filter(newText);
                mMatchesAdapterHorizon.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void getUserMatchId() {
        resultMatches.removeAll(resultMatches);
        mMatchesAdapter.notifyDataSetChanged();
        mMatchesAdapterHorizon.notifyDataSetChanged();

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(currentUserId).child("connections").child("matches");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot match: snapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void FetchMatchInformation(String key) {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(key);
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String userId = snapshot.getKey();
                    String userName = "";
                    String profileImageUrl = "";
                    if(snapshot.child("name").getValue() != null){
                        userName = snapshot.child("name").getValue().toString();
                    }
                    if(snapshot.child("profileImageUrl").getValue() != null){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    MatchesObject obj = new MatchesObject(userId,userName,profileImageUrl);
                    resultMatches.add(obj);
                    mMatchesAdapterHorizon.notifyDataSetChanged();
                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    ArrayList<MatchesObject> resultMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getMatches() {
        return resultMatches;
    }

}