package com.example.bloodcommunity.assist;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler {
    private long backKeyPRessedTime = 0;
    private Toast toast;
    private Activity activity;

    //생성자
    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPRessedTime + 2000) {
            backKeyPRessedTime = System.currentTimeMillis();
            showGuide();
        } else if (System.currentTimeMillis() <= backKeyPRessedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}