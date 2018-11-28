package com.example.bookshelftop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PageFragment extends Fragment {
    TextView textView;


    public PageFragment() {
        // Required empty public constructor
    }



    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.page_fragment_layout, container,false);
        textView = (TextView)view.findViewById(R.id.textView);
        Bundle bundle = getArguments();
        String message = bundle.getString("text");
        textView.setText(message);

        return view;
    }


}
