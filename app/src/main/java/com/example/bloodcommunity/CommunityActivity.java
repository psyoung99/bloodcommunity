package com.example.bloodcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodcommunity.assist.BackPressCloseHandler;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bloodcommunity.ui.gallery.GalleryFragment;
import com.example.bloodcommunity.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * 커뮤니티 메인 화면
 */

public class CommunityActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;
    private AppBarConfiguration mAppBarConfiguration;
    private String name;
    private String id;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        backPressCloseHandler = new BackPressCloseHandler(this);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DrawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_document, R.id.nav_barcode, R.id.nav_information, R.id.nav_detailInformation)
                .setDrawerLayout(drawer)
                .build();


        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        //NavController(navigation -> mobile_navigation 으로 fragment 관리)
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navigationFunction();

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void navigationFunction() {
        TextView txtHeaderName = findViewById(R.id.txtHeaderName);
        txtHeaderName.setText("ID : " + id);
        //마이페이지 이동
        findViewById(R.id.linearHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("정보","header");
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class).putExtra("id", id));
                Toast.makeText(CommunityActivity.this, "마이페이지 이동 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
