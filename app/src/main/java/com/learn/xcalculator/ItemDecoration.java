package com.learn.xcalculator;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    int spaceHoz;
    int spaceVer;

    public ItemDecoration(int spaceHoz, int spaceVer) {
        this.spaceHoz = spaceHoz;
        this.spaceVer = spaceVer;

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = spaceVer;
        outRect.top = spaceVer;
        outRect.right = spaceHoz;
        outRect.left = spaceHoz;

    }
}
