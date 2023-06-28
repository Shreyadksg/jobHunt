package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class showAllJobPostsActivity extends AppCompatActivity {

    //private RecyclerView recycler_view_all_jobs;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar app_bar_custom_toolbar;
    private static final String CHANNEL_ID="My Channel";
    private static final int NOTIFICATION_ID=1;
    private static final int PICK_IMAGE_REQUEST=1313;
    private Uri imagePath;
    private Bitmap imageToStore;
    ImageView imgProfile;
    DatabaseReference databaseReference;
    View headerview;
    ImageView profileImage;
    Button btn;
    //private final int RESULT_OK = 100;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_all_job_posts);


        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigation_drawer);
        headerview=navigationView.getHeaderView(0);
        imgProfile=headerview.findViewById(R.id.profileImage);

        //Setup Toolbar
        app_bar_custom_toolbar=findViewById(R.id.app_custom_toolbar);
        setSupportActionBar(app_bar_custom_toolbar);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,app_bar_custom_toolbar,R.string.openDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String imageUrl=snapshot.child("eimage").getValue(String.class);
                    if(imageUrl!=""){     //image is uploaded by user already
                        Glide.with(headerview).load(imageUrl).into(imgProfile);}
                    else{
                        Glide.with(headerview).load(R.drawable.ic_person).into(imgProfile);  //default image
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Notifications

        Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.nice,null);

        BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;
        Bitmap largeIcon=bitmapDrawable.getBitmap();
        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification NewjobAdded=new Notification.Builder(this)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.hopeso)
                .setContentText("New Message")
                .setSubText("New Message from Raman")
                .setChannelId(CHANNEL_ID)
                .build();


        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel1",NotificationManager.IMPORTANCE_HIGH));
        imgProfile.setOnClickListener(v->{
            choseImage();
        });

    loadFragment(new BFragment());
        //when clicking on options in navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.ViewProfile) {
                    Intent intent=new Intent(showAllJobPostsActivity.this,profileEmployee.class);
                    startActivity(intent);
                }
                else if(id==R.id.Notifications){
                    loadFragment(new AFragment());
                    nm.notify(NOTIFICATION_ID,NewjobAdded);
                }
                else if(id==R.id.bookmarks)
                {
                    Toast.makeText(showAllJobPostsActivity.this,"No Bookmarks", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.image)
                {
                    Intent intent=new Intent(showAllJobPostsActivity.this,image.class);
                    startActivity(intent);
                }


                else{
                    Intent intent = new Intent(showAllJobPostsActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(showAllJobPostsActivity.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null)
            {
                imagePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                imgProfile.setImageBitmap(imageToStore);
                drawerLayout.openDrawer(GravityCompat.START);
                System.out.println("till");
                uploadImageToFirebaseStorage(imagePath,FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //Connect application to firebase storage to store profile photos
    private void uploadImageToFirebaseStorage(Uri imageUri,String uid)
    {
        String filename= UUID.randomUUID().toString();
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("profile_images").child(filename);
        System.out.println(filename);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                                System.out.println("here too");
                                databaseReference.child("eimage").setValue(uri.toString());

                                Glide.with(headerview).load(uri).into(imgProfile);
                            }
                        });
                    }
                }).addOnFailureListener(e->{
                    System.out.println(e.getMessage());
                });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.container,fragment);
        ft.commit();
    }



}