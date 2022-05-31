package com.example.finalproject.Chat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;
    private String currentUserId, matchId, chatId;
    private EditText mMessage;
    private TextView mMatchName;
    private ImageView mSend,mMatchImage, btnBack;
    private DatabaseReference userDb, chatDb, matchDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        matchId = getIntent().getExtras().getString("matchId");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches")
                .child(matchId).child("chatId");
        chatDb = FirebaseDatabase.getInstance().getReference().child("Chat");
        matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(matchId);
        getChatId();
        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new ChatAdapter(getChat(),ChatActivity.this);
        recyclerView.setAdapter(mChatAdapter);

        mMessage = findViewById(R.id.message);
        btnBack = findViewById(R.id.btnBack);
        mMatchImage = findViewById(R.id.matchImage);
        mMatchName = findViewById(R.id.matchName);
        mSend = findViewById(R.id.send);
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Glide.with(ChatActivity.this).load(snapshot.child("profileImageUrl").getValue().toString()).into(mMatchImage);
                    mMatchName.setText(snapshot.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mSend.setOnClickListener(view->{
            sendMessage();
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void sendMessage() {
        String sendMessage = mMessage.getText().toString();
        if(!sendMessage.isEmpty()){
            DatabaseReference newMessageDb = chatDb.push();

            Map chat = new HashMap();
            chat.put("createdByUser",currentUserId);
            chat.put("text",sendMessage);
            newMessageDb.setValue(chat);
        }
        mMessage.setText(null);
    }

    private void getChatId(){
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    chatId = snapshot.getValue().toString();
                    chatDb = chatDb.child(chatId);
                    getChatMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessage(){
        chatDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    String message = null;
                    String createdByUser = null;
                    if(snapshot.child("text").getValue()!=null){
                        message = snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("createdByUser").getValue()!=null){
                        createdByUser = snapshot.child("createdByUser").getValue().toString();
                    }
                    if(message!=null && createdByUser!=null){
                        Boolean currentUserBoolean = false;
                        if(createdByUser.equals(currentUserId)){
                            currentUserBoolean = true;
                        }

                        ChatObject obj = new ChatObject(message,currentUserBoolean);
                        resultChat.add(obj);
                        mChatAdapter.notifyDataSetChanged();
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

    ArrayList<ChatObject> resultChat = new ArrayList<ChatObject>();
    private List<ChatObject> getChat() {
        return resultChat;
    }
}