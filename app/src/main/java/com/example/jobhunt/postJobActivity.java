package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class postJobActivity extends AppCompatActivity {
    private FloatingActionButton add_job_btn;
    private RecyclerView recycler_view_job_post;
    MainAdapter adapter;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar app_bar_custom_toolbar;
    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        drawerLayout = findViewById(R.id.drawerLayout);
        add_job_btn = findViewById(R.id.add_job_btn);
        navigationView = findViewById(R.id.navigation_drawer);
        app_bar_custom_toolbar = findViewById(R.id.app_custom_toolbar);
        setSupportActionBar(app_bar_custom_toolbar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        recycler_view_job_post = (RecyclerView) findViewById(R.id.recycler_view_job_post);
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference().child("Job Post").child(mAuth.getUid());
        add_job_btn.setOnClickListener(v -> {
            startActivity(new Intent(this, insertJobPostActivity.class));
        });
        recycler_view_job_post.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(refer, Data.class)
                        .build();
        adapter = new MainAdapter(options);
        recycler_view_job_post.setAdapter(adapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, app_bar_custom_toolbar, R.string.openDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        loadFragment(new AFragment());
        //when clicking on options in navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.ViewProfile) {
                    Intent intent = new Intent(postJobActivity.this, profileEmployee.class);
                    startActivity(intent);
                } else if (id == R.id.Notifications) {
                    loadFragment(new AFragment());
                } else if (id == R.id.bookmarks) {
                    Toast.makeText(postJobActivity.this, "No Bookmarks", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(postJobActivity.this, "No Bookmarks", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, fragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
//tools:openDrawer="start"