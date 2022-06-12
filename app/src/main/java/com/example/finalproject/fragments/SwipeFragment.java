package com.example.finalproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;


import android.widget.Toast;

import com.example.finalproject.R;

import com.example.finalproject.cards.CardArrayAdapter;
import com.example.finalproject.cards.Cards;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class SwipeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private List<Cards> rowItems;
    private Context mContext;
    private String userSex;
    private String oppositeUserSex;
    private double lat, lon, distance, oppoLat, oppoLon, oppoDistance;
    private CardArrayAdapter arrayAdapter;
    private DatabaseReference usersDb;
    private String currentUid, enemyUid;
    private ViewPager2 viewPager2;
    private FloatingActionButton fabBack,fabLike, fabSkip;
    private View viewDialog, viewMatch;
    private Toast toast;
    private Cards prevObj;
    int nowYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkUserSex();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.view_pager);
        fabBack = view.findViewById(R.id.fabBack);
        fabLike = view.findViewById(R.id.fabLike);
        fabSkip = view.findViewById(R.id.fabSkip);
//        ImageView btnInfo = view.findViewById(R.id.btnInfo);
        mContext = getContext();
        nowYear = Calendar.getInstance().get(Calendar.YEAR);
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
//        viewDialog = getLayoutInflater().inflate(R.layout.info_page,null);
        viewMatch = getLayoutInflater().inflate(R.layout.match_announce,(ViewGroup) view.findViewById(R.id.match_layout));
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        checkUserSex();
        rowItems = new ArrayList<Cards>();
        arrayAdapter = new CardArrayAdapter(mContext, R.layout.item, rowItems );
        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        //Match announce
        toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(viewMatch);

//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
//        bottomSheetDialog.setContentView(viewDialog);
        // Info bottom sheet
//        btnInfo.setOnClickListener(view1 -> {
//            bottomSheetDialog.show();
//
//            Button cancel = viewDialog.findViewById(R.id.btnBack);
//            cancel.setOnClickListener(view2 ->{
//                bottomSheetDialog.dismiss();
//
//            });
//        });
        // Button action
        fabLike.setOnClickListener(view1 -> {
            flingContainer.getTopCardListener().selectRight();
        });
        fabSkip.setOnClickListener(view1 -> {
            flingContainer.getTopCardListener().selectLeft();
        });
        fabBack.setOnClickListener(view1 -> {
            try {
                DatabaseReference yepDb = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(enemyUid).child("connections").child("yep").child(currentUid);
                yepDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            yepDb.removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                DatabaseReference nopeDb = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(enemyUid).child("connections").child("nope").child(currentUid);
                nopeDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            nopeDb.removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(enemyUid).child("connections").child("matches").child(currentUid);
                matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            matchDb.removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                DatabaseReference userMatchDb = FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(currentUid).child("connections").child("matches").child(enemyUid);
                userMatchDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            userMatchDb.removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                rowItems.removeAll(rowItems);
                arrayAdapter.notifyDataSetChanged();
                new Handler().postDelayed(() -> {
                    getOppositeUsers();
                },500);

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Swipe card

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }


            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Cards cards = (Cards) dataObject;
                prevObj = cards;
                enemyUid = cards.getUserid();
                usersDb.child(enemyUid).child("connections").child("nope").child(currentUid).setValue(true);

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards cards = (Cards) dataObject;
                enemyUid = cards.getUserid();
                prevObj = cards;
                usersDb.child(enemyUid).child("connections").child("yep").child(currentUid).setValue(true);
                isConnectionMatch(enemyUid);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
            }
        });

    }

    public void checkUserSex() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("userSex") != null && snapshot.child("enemySex") != null) {
                        userSex = snapshot.child("userSex").getValue().toString();
                        oppositeUserSex = snapshot.child("enemySex").getValue().toString();
                    }
                    if(snapshot.child("lat") !=null && snapshot.child("long") != null &&
                            snapshot.child("lat").getValue() !=null && snapshot.child("long").getValue() != null){
                        lat = Double.parseDouble(snapshot.child("lat").getValue().toString());
                        lon = Double.parseDouble(snapshot.child("long").getValue().toString());
                        distance = Double.parseDouble(snapshot.child("distance").getValue().toString());
                    }
                    getOppositeUsers();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean findOpposite(DataSnapshot snapshot){
        if(!snapshot.child("connections").child("yep").hasChild(currentUid) &&
                !snapshot.child("connections").child("nope").hasChild(currentUid) &&
                snapshot.child("userSex").getValue().toString().equals(oppositeUserSex) &&
                !snapshot.getKey().equals(currentUid)){
            if(snapshot.child("lat") != null && snapshot.child("long")!= null && lat != 0.0 && lon != 0.0){
                if(snapshot.child("lat").getValue()!=null && snapshot.child("long").getValue() != null) {
                    oppoLat = Double.parseDouble(snapshot.child("lat").getValue().toString());
                    oppoLon = Double.parseDouble(snapshot.child("long").getValue().toString());
                    oppoDistance = calDistance(oppoLat, oppoLon, lat, lon, 'K');
                    Log.d("Distance: ", String.valueOf(distance - oppoDistance));
                    if ((distance - oppoDistance) < 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public void getOppositeUsers(){
        rowItems.removeAll(rowItems);
        arrayAdapter.notifyDataSetChanged();
        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists() && snapshot.child(currentUid).child("userSex")!=null) {

                    if (findOpposite(snapshot)) {
                        String profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String birthDay = snapshot.child("birthDay").getValue().toString();
                        String school = snapshot.child("school").getValue().toString();
                        String hobbies = snapshot.child("hobbies").getValue().toString()
                                .replace("[","").replace("]","");
                        String address = "";
                        String introduce = "";
                        String userSex = snapshot.child("userSex").getValue().toString();
                        if(snapshot.child("address")!=null && snapshot.child("address").getValue()!=null){
                            address = Objects.requireNonNull(snapshot.child("address").getValue()).toString();
                        }
                        if(snapshot.child("introduce")!=null && snapshot.child("introduce").getValue()!=null){
                            introduce = Objects.requireNonNull(snapshot.child("introduce").getValue()).toString();
                        }
                        int year = Integer.parseInt(birthDay.substring(birthDay.length() - 4, birthDay.length()));
                        String age = String.valueOf(nowYear - year);
                        Cards item = new Cards(snapshot.getKey(), name, profileImageUrl, age,address,school,hobbies,introduce,userSex);
                        rowItems.add(item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void isConnectionMatch(String uid){
        DatabaseReference currentUserConnectionDb = usersDb.child(currentUid).child("connections")
                .child("yep").child(uid);
        currentUserConnectionDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    toast.show();

                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    usersDb.child(currentUid).child("connections").child("matches")
                            .child(snapshot.getKey()).child("chatId").setValue(key);
                    usersDb.child(snapshot.getKey()).child("connections").child("matches")
                            .child(currentUid).child("chatId").setValue(key);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private double calDistance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return Math.round(dist*100.0)/100.0;
    }

}