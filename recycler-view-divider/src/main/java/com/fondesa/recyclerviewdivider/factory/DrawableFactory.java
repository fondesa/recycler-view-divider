package com.fondesa.recyclerviewdivider.factory;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fondesa.recycler_view_divider.R;

/**
 * Created by antoniolig on 02/05/2016.
 * Factory used to specify a custom logic to use different drawables as divider.
 * <br>
 * You can add a custom drawable factory in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#drawableFactory(DrawableFactory)} method
 */
public abstract class DrawableFactory {

    private static DrawableFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link DrawableFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    public static synchronized DrawableFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    /**
     * Define a custom Drawable for each divider
     *
     * @param listSize size of the list in the adapter
     * @param position current position
     * @return Drawable resource for the divider int the current position
     */
    public abstract Drawable drawableForItem(int listSize, int position);

    /**
     * Default instance of a {@link DrawableFactory}
     */
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