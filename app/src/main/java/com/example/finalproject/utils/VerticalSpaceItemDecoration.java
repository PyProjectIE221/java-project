package com.example.finalproject.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;
    private final int verticalSpaceHorizon;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight, int verticalSpaceHorizon) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.verticalSpaceHorizon = verticalSpaceHorizon;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
        outRect.left =  verticalSpaceHorizon;
    }
}
