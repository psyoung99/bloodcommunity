package com.example.bloodcommunity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO! 개설된 채팅방 리스트를 만들고자 생성했는데, 갈피를 못잡고 있는 듯 하다.
 */

public class UserChatList {
    private ArrayList<String> titles = new ArrayList<>();
    private HashMap<String, String> hashMap = new HashMap<>();

    public UserChatList() {

    }

    //제목들(1개 이상의 데이터가 들어옴)
    public UserChatList(String ... v) {
        for (int i = 0; i < v.length; i++) {
            titles.add(v[i]);
        }
    }

    //HashMap 타입으로 변환해서 반환함
    public HashMap<String, String> toMap(String title) {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("title", title);

        return hashMap;
    }

}
