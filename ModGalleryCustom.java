package com.ekant.justbiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class ModGalleryCustom extends Gallery {

    public ModGalleryCustom(Context ctx, AttributeSet attrSet)
    {
        super(ctx,attrSet);
    }

    @SuppressWarnings("unused")
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2)
    {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        return true;
    }
}