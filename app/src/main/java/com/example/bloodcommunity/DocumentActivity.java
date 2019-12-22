package com.example.bloodcommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//TODO! 뒤로 가면 작성중인 데이터를 잃는다는 alert 생성

public class DocumentActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    private EditText edtDocTitle, edtDocContent;
    private String title, content, id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        documentFunction();
    }

    private void documentFunction() {
        initialVariable();

        //취소
        findViewById(R.id.btnDocCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DocumentActivity.this, "글 작성 취소", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //작성
        findViewById(R.id.submitDocument).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialVariable();

                if(!(title.isEmpty() && content.isEmpty())) {
                    postFirebaseDatabase();
                    Toast.makeText(DocumentActivity.this, "글 작성 완료", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else {
                    Toast.makeText(DocumentActivity.this, "제목과 내용을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialVariable() {
        edtDocTitle = findViewById(R.id.titleDocument);
        title = edtDocTitle.getText().toString();

        edtDocContent = findViewById(R.id.contentDocument);
        content = edtDocContent.getText().toString();

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
    }

    private void postFirebaseDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        DocumentData documentData = new DocumentData(title, content, id);
        postValues = documentData.toMap();

        String key = "/document_list/" + id + "/" + title;
        childUpdates.put(key, postValues);
        databaseReference.updateChildren(childUpdates);
    }
}
