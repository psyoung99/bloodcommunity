package com.example.bloodcommunity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Camera_afterdialog extends Dialog {

    public String barcode_name;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(mTitle);
        Button save_as_btn = findViewById(R.id.save_as_btn);

        //바코드 저장버튼 클릭
        save_as_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcode_name = ((EditText) (findViewById(R.id.barcodename))).getText().toString();
                if (barcode_name != null) {
                    Log.d("정보", barcode_name);
                    cancel();
                }
            }
        });
    }


    public Camera_afterdialog(Context context,String title) {
        super(context,android.R.style.Theme_Translucent);
        this.mTitle =title;
    }

//    public String getBarcode_name() {
//        return barcode_name;
//    }

}
