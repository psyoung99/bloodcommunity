package com.example.bloodcommunity.ui.slideshow;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bloodcommunity.CommunityActivity;
import com.example.bloodcommunity.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Show Crawling Information
 */
public class SlideshowFragment extends Fragment {
    WebView web;
    String urlpath;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        slideshowFunction(view);
        return view;
    }

    private void slideshowFunction(View view) {
        web = view.findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);

        JsoupAsyncTask jsoup = new JsoupAsyncTask();
        jsoup.execute();
    }

    private class JsoupAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Document doc = Jsoup.connect("https://www.bloodinfo.net/event.do?action=detail&eventno=154213&current_pagenum01=1&current_pagenum02=1&current_pagenum03=1&eventclscode=&eventtargetlist=").get();
                String title = doc.title();
                Element UrlEle=doc.select("img[alt=전국 헌혈의 집 이벤트 상세정보는 아래 내용을 참고하세요]").first();
                urlpath = UrlEle.attr("src");

            } catch (IOException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            web.loadUrl(urlpath);
            Log.d("정보","Open: "+urlpath);
        }
    }

}
