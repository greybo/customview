package com.example.m.customviewtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m.customviewtest.views.LineView;
import com.example.m.customviewtest.views.SquareView;

public class Page1Fragment extends Fragment {
    private LineView lineView;
    private SquareView squareView;

    public Page1Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1_fragment, container, false);
        lineView = view.findViewById(R.id.indicator_view);
        squareView = view.findViewById(R.id.circle_view);
        lineView.setOnClickListener(listener);
        squareView.setOnClickListener(listener);
        return view;
    }

    View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.indicator_view:
                lineView.init();
                break;
            case R.id.circle_view:
                squareView.init();
                break;
        }
    };


}
