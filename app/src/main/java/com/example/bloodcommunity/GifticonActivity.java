package com.example.bloodcommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GifticonActivity extends AppCompatActivity {

    private GifticonAdapter adapter;
    private ListView GifticonList;
    String mId;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifticon);
        adapter = new GifticonAdapter(this, R.layout.item);

        GifticonList = findViewById(R.id.GifticonList);
        GifticonList.setAdapter(adapter);

        //바코드 데이터 가져오기
        mId = getIntent().getExtras().getString("ID");

        databaseReference.child("GifticonInfo").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // dataSnapshot.getValue();
                        Log.d("정보", "Gifticon Activity Create" + mId);

                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            GifticonDB GifticonInfo = d.getValue(GifticonDB.class);
                            GifticonData Gifticondata = new GifticonData();
                            if (GifticonInfo.getId().equals(mId)) {
                                Gifticondata.setImagePath(GifticonInfo.getImagePath());
                                Log.d("정보",Gifticondata.getImagePath());
                                Gifticondata.setName(GifticonInfo.getName());
                                Gifticondata.setBarcode(GifticonInfo.getBarcode());
                                adapter.addItem(Gifticondata);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


}