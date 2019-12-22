package com.example.bloodcommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodcommunity.R;
import com.example.bloodcommunity.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ChangeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String mId;
    TextView Id;
    EditText pwd;
    EditText phonenum;
    EditText email;
    EditText address;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        //realtime db 인스턴스 생성
        FirebaseDatabase firebaseInstance = FirebaseDatabase.getInstance();
        //firebaseInstance.setPersistenceEnabled(true);
        mDatabase = firebaseInstance.getReference();

        Id = findViewById(R.id.changeId);
        pwd = (EditText) findViewById(R.id.changePassword);
        phonenum = (EditText) findViewById(R.id.changePhonenum);
        email = (EditText) findViewById(R.id.changeEmail);
        name = (EditText) findViewById(R.id.changeName);
        address = (EditText) findViewById(R.id.changeAddress);

        //Id 가져오기
        mId = getIntent().getExtras().getString("ID");

        //데이터 수신
        databaseReference.child("user_list").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    //성공
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            UserData userInfo = userSnapshot.getValue(UserData.class);
                            Log.d("정보", "onDataChange success" + userInfo.toString());
                            if (userInfo.getId().equals(mId)) {
                                String ChangeId = userInfo.getId();
                                Log.d("정보", "id:" + ChangeId + userInfo.getPassword() + userInfo.getAddress());
                                Id.setText(ChangeId);

                                pwd.setText(userInfo.getPassword());
                                name.setText(userInfo.getName());
                                phonenum.setText(userInfo.getPhonenumber());
                                address.setText(userInfo.getAddress());
                            }
                        }
                    }

                    //실패
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("정보", "loadUser:onCancelled", databaseError.toException());
                    }
                });


    }

    //수정 클릭시
    public void onClick(View v) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        UserData userInfo = new UserData(Id.getText().toString(), pwd.getText().toString(),
                phonenum.getText().toString(), name.getText().toString(),
                address.getText().toString());
        childUpdates.put("/UserInfo/" + Id.getText().toString(), userInfo.toMap());
        databaseReference.updateChildren(childUpdates);

        Toast.makeText(ChangeActivity.this,"수정되었습니다",Toast.LENGTH_LONG).show();
        finish();
    }
}







