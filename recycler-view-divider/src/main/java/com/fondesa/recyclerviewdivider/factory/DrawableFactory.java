package com.fondesa.recyclerviewdivider.factory;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fondesa.recycler_view_divider.R;
import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * Created by antoniolig on 02/05/2016.
 */
public abstract class DrawableFactory {

    private static DrawableFactory defaultFactory;

    public static synchronized DrawableFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    public abstract Drawable drawableForItem(int listSize, int position);

    private static class Default extends DrawableFactory {
        private Drawable defaultDrawable;

        Default(@NonNull Context context) {
            defaultDrawable = new ColorDrawable(ContextCompat.getColor(context, R.color.recycler_view_divider_color));
        }

        @Override
        public Drawable drawableForItem(int listSize, int position) {
            return defaultDrawable;
        }
    }
}
