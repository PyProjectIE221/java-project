package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {
    private Uri resultUri;
    private ImageView mProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Button confirm = findViewById(R.id.btnConfirm);
        mProfileImage = findViewById(R.id.profileImage);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        final Uri imageUri = data.getData();
                        resultUri = imageUri;
                        mProfileImage.setImageURI(resultUri);
                    }
                });

        mProfileImage.setOnClickListener(view -> {
            Intent imageIntent = new Intent(Intent.ACTION_PICK);
            imageIntent.setType("image/*");
            someActivityResultLauncher.launch(imageIntent);
        });

        confirm.setOnClickListener(view -> {
            try{
                saveImage();
            }catch (IOException e){
                e.printStackTrace();
            }

        });
    }

    private void saveImage() throws IOException{
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        if(resultUri!=null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid);
            Bitmap bitmap = null;
            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,60,baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map userInfo1 = new HashMap();
                            userInfo1.put("profileImageUrl",uri.toString());
                            db.updateChildren(userInfo1);
                            Toast.makeText(ImageActivity.this, "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}