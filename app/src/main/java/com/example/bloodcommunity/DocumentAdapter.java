package com.example.bloodcommunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentAdapter extends BaseAdapter {
    private ArrayList<DocumentData> arrayList = new ArrayList<>();

    public DocumentAdapter() { }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void resetArrayList() {
        arrayList = new ArrayList<>();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        final int position = i;
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_document, parent, false);
        }

        TextView txtItemTitle = view.findViewById(R.id.txtItemTitle);
        TextView txtItemName = view.findViewById(R.id.txtItemName);

        DocumentData documentData = arrayList.get(position);

        txtItemTitle.setText(documentData.getTitle());
        txtItemName.setText(documentData.getId());

        return view;
    }

    public void addItem(String title, String id, String content) {
        DocumentData documentData = new DocumentData();

        documentData.setTitle(title);
        documentData.setId(id);
        documentData.setContent(content);

        arrayList.add(documentData);
    }
}
