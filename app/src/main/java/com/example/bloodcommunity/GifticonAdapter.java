package com.example.bloodcommunity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.ArrayList;

public class GifticonAdapter extends BaseAdapter {
    public Context mContext;
    int layout;
    private ArrayList<GifticonData> GifticonList = new ArrayList<>();

    public GifticonAdapter(Context mContext, int layout) {
        this.mContext = mContext;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return GifticonList.size();
    }

    @Override
    public GifticonData getItem(int i) {
        return GifticonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate( layout, viewGroup,false);
        }

        //Image경로 불러와 Image 출력
        GifticonData giftcondata = GifticonList.get(i);
        String sBarcode = "0000000000000000";
        ImageView img = view.findViewById(R.id.GifticonImg);

        //position i 전달
        img.setTag(new Integer(i));
        File imgFile = new File(giftcondata.getImagePath());
        if(imgFile.exists()) {
            try {
                //이미지 파일 경로 가져오기
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.toString());

                //이미지 형태 커스텀
                int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
                bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
                        bitmap.getHeight());

                int w = 180;
                int h = 120;
                com.google.zxing.MultiFormatWriter zxingWriter = new MultiFormatWriter();
                BitMatrix bm = zxingWriter.encode(sBarcode, BarcodeFormat.CODE_128, w, h);
                bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                for (int x = 0; x < w; x++) {
                    for (int j = 0; j < h; j++) {
                        bitmap.setPixel(x, j, bm.get(x, j) ? Color.BLACK : Color.WHITE);
                    }
                }
                img.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }

        //바코드 이미지 클릭
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                Dialog dialog = new Dialog(mContext);
//                dialog.setTitle("기프티콘 바코드");
                dialog.setContentView(R.layout.gifticon_image);
                //dialog 사이즈 조절
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                ImageView Giftimage = (ImageView)dialog.findViewById(R.id.image);
                int position = (int)vi.getTag();
                Log.d("정보","position: "+getItem(position).getImagePath());

                File imgFile = new File(getItem(position).getImagePath());
                if(imgFile.exists()) {
                    try {
                        //이미지 파일 경로 가져오기
                        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.toString());
                        String sBarcode = "0000000000000000";
                        //이미지 형태 커스텀
                        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
                        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
                                bitmap.getHeight());

                        int w = 600;
                        int h = 240;
                        com.google.zxing.MultiFormatWriter zxingWriter = new MultiFormatWriter();
                        BitMatrix bm = zxingWriter.encode(sBarcode, BarcodeFormat.CODE_128, w, h);
                        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                        for (int x = 0; x < w; x++) {
                            for (int j = 0; j < h; j++) {
                                bitmap.setPixel(x, j, bm.get(x, j) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        Giftimage.setImageBitmap(bitmap);
                    } catch (Exception e) {

                    }
                }
                dialog.show();
            }
        });

        TextView barcodenum = view.findViewById(R.id.barcodenum);
        barcodenum.setText(getItem(i).getBarcode());
        TextView barcodename = view.findViewById(R.id.barcode_nametv);
        barcodename.setText(getItem(i).getName());

         return view;
    }

    public void addItem(GifticonData Gifticondata) {

        GifticonList.add(Gifticondata);
        Log.d("정보", "add" + Gifticondata.getImagePath());
    }
}
