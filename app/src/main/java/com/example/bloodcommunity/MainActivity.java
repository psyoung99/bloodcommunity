package com.example.bloodcommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodcommunity.assist.BackPressCloseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

/**
 * 로그인 화면
 * TODO! MyPageActivity 연동해야함
 * TODO! 크롤링한 이미지 스크롤 뷰 안에 compact 하게 넣어야 함
 */

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;
    private DatabaseReference databaseReference;
    private String id, pwd;
    private EditText edtId, edtPwd;

    private static final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);

        mainFunction();
    }

    private void mainFunction() {
        initialVariable();

        //로그인
        findViewById(R.id.MainLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialVariable();
                getFirebaseDatabase();
            }
        });

        //회원가입
        findViewById(R.id.MainSignUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtId.setText(EMPTY_STRING);
                edtPwd.setText(EMPTY_STRING);

                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private void getFirebaseDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("user_list");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag = false;
                String name = EMPTY_STRING;

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("id").getValue().equals(id) && snapshot.child("password").getValue().equals(pwd)) {
                        flag = true;
                        name = snapshot.child("name").getValue().toString();
                        Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                        break;
                    }
                }

                if(!flag) {
                    Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), CommunityActivity.class)
                            .putExtra("name", name)
                            .putExtra("id", id));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MAIN", databaseError.getMessage());
            }
        });
    }

    private void initialVariable() {
        edtId = findViewById(R.id.edtMainId);
        id = edtId.getText().toString();

        edtPwd = findViewById(R.id.edtMainPwd);
        pwd = edtPwd.getText().toString();
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
