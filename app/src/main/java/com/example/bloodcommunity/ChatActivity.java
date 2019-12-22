package com.example.bloodcommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 채팅방
 * TODO! 개설된 채팅방을 목록 형식으로 만들어 놓아야 함
 */

public class ChatActivity extends AppCompatActivity {
    private ListView listChat;
    private EditText edtChatText;
    private Button btnChatSend;

    private String name;

    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private static final String EMPTY_STRING = "";
    private DatabaseReference databaseReference;

    private DatabaseReference updateDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatFunction();
    }

    private void chatFunction() {
        //변수 초기화
        initialVariable();

        //전송
        btnChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtChatText.getText().toString().equals(EMPTY_STRING)) {
                    Map<String, Object> result = new HashMap<>();

                    String key = databaseReference.push().getKey();
                    databaseReference.updateChildren(result);

                    DatabaseReference root = databaseReference.child(key);

                    Map<String, Object> objectMap = new HashMap<>();

                    objectMap.put("name", name);
                    objectMap.put("text", edtChatText.getText().toString());

                    root.updateChildren(objectMap);
                    edtChatText.setText(EMPTY_STRING);
                }
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatConversation(dataSnapshot);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatConversation(dataSnapshot);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialVariable() {
        name = getIntent().getStringExtra("name");

        listChat = findViewById(R.id.listChat);
        edtChatText = findViewById(R.id.edtChatText);
        btnChatSend = findViewById(R.id.btnChatSend);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listChat.setAdapter(arrayAdapter);

        String id = getIntent().getStringExtra("listId"); //상대방 아이디
        String myId = getIntent().getStringExtra("id"); //현재 로그인된 유저의 아이디
        String title = getIntent().getStringExtra("title"); //글 제목

        databaseReference = FirebaseDatabase.getInstance().getReference("/chat_list/" + title);

        //사용자의 데이터베이스 갱신
        updateUserList(id, myId, title);
    }

    private void updateUserList(String id, String myId, String title) { //채팅방 이름 등록
        String path = "/user_list/" + id;
        updateDatabase = FirebaseDatabase.getInstance().getReference(path);
        updateDatabase.child("title").setValue(title);

        path = "/user_list/" + myId;
        updateDatabase = FirebaseDatabase.getInstance().getReference(path);

        //TODO! 단순히 setValue 아니고 리스트 형태로 넘겨야 데이터 보존됨 -> 어떻게?
        updateDatabase.child("title").setValue(title);
    }


    private void chatConversation(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()) {
            arrayAdapter.add(i.next().getValue().toString() + " : " + i.next().getValue().toString());
        }

        arrayAdapter.notifyDataSetChanged();
    }
}
