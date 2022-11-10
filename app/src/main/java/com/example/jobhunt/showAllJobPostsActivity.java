package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.net.URI;

public class showAllJobPostsActivity extends AppCompatActivity {

    //private RecyclerView recycler_view_all_jobs;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar app_bar_custom_toolbar;
    //private final int RESULT_OK = 100;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_job_posts);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigation_drawer);
        app_bar_custom_toolbar=findViewById(R.id.app_custom_toolbar);
        //Setup Toolbar
        setSupportActionBar(app_bar_custom_toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,app_bar_custom_toolbar,R.string.openDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    loadFragment(new AFragment());
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