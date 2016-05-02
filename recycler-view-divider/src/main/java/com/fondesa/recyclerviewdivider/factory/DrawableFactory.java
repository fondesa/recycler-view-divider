package com.fondesa.recyclerviewdivider.factory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * Created by antoniolig on 02/05/2016.
 */
public abstract class DrawableFactory {

    private static DrawableFactory defaultFactory;

    public static synchronized DrawableFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new DefaultDrawableFactory(context);
        }
        return defaultFactory;
    }

    public abstract Drawable drawableForItem(int listSize, int position);
}
