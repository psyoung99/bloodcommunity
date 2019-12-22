package com.example.bloodcommunity.ui.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bloodcommunity.Camera_afterdialog;
import com.example.bloodcommunity.CommunityActivity;
import com.example.bloodcommunity.GifticonAdapter;
import com.example.bloodcommunity.GifticonDB;
import com.example.bloodcommunity.GifticonData;
import com.example.bloodcommunity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class GalleryFragment extends Fragment {
    private DatabaseReference mDatabase;
    String mId;
    IntentResult result;

    //액티비티 통합
//    private GifticonAdapter adapter;
//    private ListView GifticonList;
//    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mId = getActivity().getIntent().getExtras().getString("id");
        Log.d("정보", "barcode -- create" + mId);


        //바코드 Reader 호출
//        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
        return view;
    }

    //스캔 결과값 가져와서 DB 저장
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {

        Log.d("정보", "barcode -- dialog show");
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(),"카메라가 취소되었습니다",Toast.LENGTH_SHORT).show();
                return;
            }
            //바코드 이름(사용자 직접 입력) -> dialog로 구현
            final Camera_afterdialog dialog = new Camera_afterdialog(getContext(), "바코드 이름 저장");
            dialog.setContentView(R.layout.camera_after_dialog);
            //dialog size
            ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

            dialog.show();

            //dialog 저장버튼 후 DB 저장
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialogInterface) {

                    //realtime db 인스턴스 생성
                    FirebaseDatabase firebaseInstance = FirebaseDatabase.getInstance();
                    mDatabase = firebaseInstance.getReference("GifticonInfo");
                    //데이터 저장
                    //        Log.d("정보",dialog.barcode_name);
                    GifticonDB GifticonInfo = new GifticonDB(mId, result.getContents(),
                            result.getBarcodeImagePath(), dialog.barcode_name);

                    mDatabase.child(result.getContents()).setValue(GifticonInfo);
                    Toast.makeText(getContext(), dialog.barcode_name + " 바코드가 저장되었습니다", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
