package com.example.bloodcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPageActivity extends AppCompatActivity {
    MainActivity ma = new MainActivity();
    String idMypage;
    String mId;
    Button MyPageGifticon;
    Button MyPageChange;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        myPageFunction();

        //기프티콘 메뉴 클릭
        MyPageGifticon = findViewById(R.id.MyPageGifticon);
        MyPageGifticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPageActivity.this, GifticonActivity.class);
                i.putExtra("ID",idMypage);
                startActivity(i);
            }
        });

        //사용자 정보 수정 클릭
        MyPageChange = findViewById(R.id.MyPageChange);
        MyPageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPageActivity.this, ChangeActivity.class);
                i.putExtra("ID",idMypage);
                startActivity(i);
            }
        });
    }

    //회원 탈퇴 버튼
    private void myPageFunction() {
        idMypage = getIntent().getStringExtra("id");
        Log.d("정보","myPageFunction"+idMypage);
        findViewById(R.id.MyPageOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivity.this);
                builder.setTitle("안내");
                builder.setTitle("탈퇴 하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child("user_list").child(idMypage).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "탈퇴가 완료되었습니다.", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
