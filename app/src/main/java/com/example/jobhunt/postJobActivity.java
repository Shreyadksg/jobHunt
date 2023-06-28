package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class postJobActivity extends AppCompatActivity {
    DrawerLayout drawerLayoutJobPost2;
    NavigationView navigationView2;
    Toolbar app_bar_custom_toolbar;
    ImageView imgProfile;
    private static final String CHANNEL_ID="My Channel";
    private static final int NOTIFICATION_ID=1;
    private static final int PICK_IMAGE_REQUEST=1313;
    private Uri imagePath;
    private Bitmap imageToStore;
    DatabaseReference databaseReference;
    View headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        FirebaseApp.initializeApp(this);

        drawerLayoutJobPost2=findViewById(R.id.drawerLayoutPostJob2);

        //navigation drawer and get profile imageview from navigation drawer
        navigationView2=findViewById(R.id.navigation_drawer2);
        headerView=navigationView2.getHeaderView(0);
        imgProfile=headerView.findViewById(R.id.profileImage);


        //toolbar setup
        app_bar_custom_toolbar=findViewById(R.id.app_custom_toolbar);
        setSupportActionBar(app_bar_custom_toolbar);

        //drawer toggle
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayoutJobPost2,app_bar_custom_toolbar,R.string.openDrawer,R.string.CloseDrawer);
        drawerLayoutJobPost2.addDrawerListener(toggle);
        toggle.syncState();


        //reference to the firebase storage location of profile image
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String imageUrl=snapshot.child("eimage").getValue(String.class);
                    if(imageUrl!=""){     //image is uploaded by user already
                    Glide.with(headerView).load(imageUrl).into(imgProfile);}
                else{
                    Glide.with(headerView).load(R.drawable.ic_person).into(imgProfile);  //default image
                }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imgProfile.setOnClickListener(v->{
            choseImage();
        });
        loadFragment(new AFragment());

        navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.ViewProfile){
                    Intent intent=new Intent(postJobActivity.this,profileEmployee.class);
                    startActivity(intent);
                }
                else if(id==R.id.Notifications){


                }
                else if(id==R.id.bookmarks)
                {

                }
                else if(id==R.id.image){
                    Intent intent=new Intent(postJobActivity.this,image.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(postJobActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(postJobActivity.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
                }
                drawerLayoutJobPost2.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void choseImage() {
        try{
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayoutJobPost2.isDrawerOpen(GravityCompat.START)){
            drawerLayoutJobPost2.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private void loadFragment(AFragment aFragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.container,aFragment);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null)
            {
                imagePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                imgProfile.setImageBitmap(imageToStore);
                drawerLayoutJobPost2.openDrawer(GravityCompat.START);
                uploadImageToFirebaseStorage(imagePath,FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    //Connect application to firebase storage to store profile photo
    private void uploadImageToFirebaseStorage(Uri imageUri,String uid)
    {
        String filename= UUID.randomUUID().toString();
        StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("profile_images").child(filename);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                               // DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("eimage").setValue(uri.toString());

                                Glide.with(headerView).load(uri).into(imgProfile);
                            }
                        });
                    }
                });
    }
}
