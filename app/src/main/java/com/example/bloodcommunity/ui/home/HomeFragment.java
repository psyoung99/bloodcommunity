package com.example.bloodcommunity.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bloodcommunity.ChatActivity;
import com.example.bloodcommunity.CommunityActivity;
import com.example.bloodcommunity.DocumentActivity;
import com.example.bloodcommunity.DocumentAdapter;
import com.example.bloodcommunity.DocumentData;
import com.example.bloodcommunity.MainActivity;
import com.example.bloodcommunity.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class HomeFragment extends Fragment {
    private ListView listHome;
    private DocumentAdapter documentAdapter; //어댑터

    private String name;
    private String id;

    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeFunction(root);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //intent 로 넘어온 name 값 가져오기
        if(getActivity() != null && getActivity() instanceof CommunityActivity) {
            name = ((CommunityActivity) getActivity()).getIntent().getStringExtra("name");
            id = ((CommunityActivity) getActivity()).getIntent().getStringExtra("id");
        }
    }

    private void homeFunction(View view) {
        //글 작성
        view.findViewById(R.id.btnHomeWrite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DocumentActivity.class).putExtra("id", id));
            }
        });

        //어댑터 생성
        documentAdapter = new DocumentAdapter();

        //리스트뷰 참조 및 adapter 달기
        listHome = view.findViewById(R.id.listHome);
        listHome.setAdapter(documentAdapter);

        //데이터베이스 연동
        databaseReference = FirebaseDatabase.getInstance().getReference("document_list");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                docConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                documentAdapter.resetArrayList();
                docConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                documentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //1. itemClickListener
        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = ((DocumentData)documentAdapter.getItem(i)).getTitle();
                String content = ((DocumentData)documentAdapter.getItem(i)).getContent();

                popDialog(view, title, content);
            }
        });

        //2. longClickListener
        listHome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = ((DocumentData)documentAdapter.getItem(i)).getTitle();
                String listId = ((DocumentData)documentAdapter.getItem(i)).getId();

                if(id.equals(listId)) {
                    Toast.makeText(getContext(), "본인과의 채팅방은 만들 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    popChatDialog(view, listId, title);
                }

                return true;
            }
        });


    }

    //글 정보를 dialog 로 띄움
    private void popDialog(View view, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("제목 : " + title).setMessage("\n" + content);

        builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void popChatDialog(View view, String listId, String title) {
        final String mlistId = listId; //상대방 아이디
        final String mTitle = title;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(listId + "님 과의 채팅방을 개설하시겠습니까?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getContext(), ChatActivity.class)
                        .putExtra("id", id)
                        .putExtra("listId", mlistId)
                        .putExtra("title", mTitle)
                        .putExtra("name", name));
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void docConversation(DataSnapshot snapshot) {
        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
            String title = dataSnapshot.child("title").getValue().toString();
            String content = dataSnapshot.child("content").getValue().toString();
            documentAdapter.addItem(title, snapshot.getKey(), content);
        }

        documentAdapter.notifyDataSetChanged();
    }
}