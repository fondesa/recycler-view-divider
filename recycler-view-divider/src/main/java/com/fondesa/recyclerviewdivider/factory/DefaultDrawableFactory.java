package com.fondesa.recyclerviewdivider.factory;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fondesa.recycler_view_divider.R;

/**
 * Created by antoniolig on 02/05/2016.
 */
final class DefaultDrawableFactory extends DrawableFactory {
    private Drawable defaultDrawable;

    public DefaultDrawableFactory(@NonNull Context context) {
        defaultDrawable = new ColorDrawable(ContextCompat.getColor(context, R.color.recycler_view_divider_color));
    }

    @Override
    public Drawable drawableForItem(int listSize, int position) {
        return defaultDrawable;
    }
}
