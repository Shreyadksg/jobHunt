package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class postJobActivity extends AppCompatActivity {
    DrawerLayout drawerLayoutJobPost2;
    NavigationView navigationView2;
    Toolbar app_bar_custom_toolbar;
    private static final String CHANNEL_ID="My Channel";
    private static final int NOTIFICATION_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        drawerLayoutJobPost2=findViewById(R.id.drawerLayoutPostJob2);
        navigationView2=findViewById(R.id.navigation_drawer2);
        app_bar_custom_toolbar=findViewById(R.id.app_custom_toolbar);
        setSupportActionBar(app_bar_custom_toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayoutJobPost2,app_bar_custom_toolbar,R.string.openDrawer,R.string.CloseDrawer);
        drawerLayoutJobPost2.addDrawerListener(toggle);
        toggle.syncState();
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
                else{

                }
                drawerLayoutJobPost2.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
}