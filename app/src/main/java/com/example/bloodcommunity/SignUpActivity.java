package com.example.bloodcommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 회원가입 화면
 */

public class SignUpActivity extends AppCompatActivity {
    private String id, pwd, pwdChk, phoneNum, name, location;
    private EditText edtId,edtPwd, edtPwdChk, edtPhoneNum, edtName, edtLocation;
    private DatabaseReference databaseReference;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    private static final String EMPTY_STRING = "";
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //주소검색
        findViewById(R.id.SignUpFind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(i);
                count++;
            }
        });

        if(count >0) {
            Intent intent = getIntent();
            EditText edt;
            edt = findViewById(R.id.edtSignUpLocation);
            location = intent.getExtras().getString("location");
            edt.setText(location);
            location = edt.getText().toString();

        }

        signUpFunction();
    }

    private void signUpFunction() {
        //회원가입
        findViewById(R.id.txtSignUpSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialVariable();

                if(isEmpty()) { //1. 모든 항목이 입력되었는가?
                    Toast.makeText(SignUpActivity.this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidPwd()) { //3. 비밀번호가 일치하는가?
                    edtPwdChk.setText(EMPTY_STRING);
                    edtPwdChk.requestFocus();

                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidFormat(pwd)) { //4. 비밀번호 형식이 올바른가?
                    edtPwd.setText(EMPTY_STRING);
                    edtPwdChk.setText(EMPTY_STRING);
                    edtPwd.requestFocus();

                    Toast.makeText(SignUpActivity.this, "비밀번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else { //5. 정상 회원가입
                    postFirebaseDatabase();
                    Toast.makeText(SignUpActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void postFirebaseDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        UserData userData = new UserData(id, pwd, phoneNum, name, location);
        postValues = userData.toMap();

        childUpdates.put("/user_list/" + id, postValues);
        databaseReference.updateChildren(childUpdates);
    }

    private void initialVariable() {
        edtId = findViewById(R.id.edtSignUpId);
        id = edtId.getText().toString();

        edtPwd = findViewById(R.id.edtSignUpPwd);
        pwd = edtPwd.getText().toString();

        edtPwdChk = findViewById(R.id.edtSignUpPwdChk);
        pwdChk = edtPwdChk.getText().toString();

        edtPhoneNum = findViewById(R.id.edtSignUpPhoneNum);
        phoneNum = edtPhoneNum.getText().toString();

        edtName = findViewById(R.id.edtSignUpName);
        name = edtName.getText().toString();

        edtLocation = findViewById(R.id.edtSignUpLocation);
        location = edtLocation.getText().toString();
    }

    private boolean isEmpty() { //true 반환시 비어있는 것
        return id.isEmpty() || pwd.isEmpty() || pwdChk.isEmpty() || phoneNum.isEmpty() || name.isEmpty() || location.isEmpty();
    }

    private boolean isValidPwd() { //true 반환시 올바른 것
        return pwd.equals(pwdChk);
    }

    private boolean isValidFormat(String pwd) { //true 반환시 올바른 것
        if(PASSWORD_PATTERN.matcher(pwd).matches())
            return true;
        else
            return false;
    }
}
