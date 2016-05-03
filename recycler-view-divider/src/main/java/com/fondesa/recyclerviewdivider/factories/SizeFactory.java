package com.fondesa.recyclerviewdivider.factories;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.fondesa.recycler_view_divider.R;

/**
 * Factory used to specify a custom logic to set different sizes to the divider.
 * <br>
 * Size is referred to the height of an horizontal divider and to the width of a vertical divider.
 * <br>
 * You can add a custom {@link SizeFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#sizeFactory(SizeFactory)} method
 */
public abstract class SizeFactory {

    private static Default defaultFactory;

    /**
     * Creates a singleton instance of a default {@link SizeFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    public static synchronized SizeFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    /**
     * Defines a custom size for each divider
     *
     * @param drawable    current divider's drawable
     * @param orientation RecyclerView.VERTICAL or RecyclerView.HORIZONTAL
     * @param listSize    size of the list in the adapter
     * @param position    current position
     * @return height for an horizontal divider, width for a vertical divider
     */
    public abstract int sizeForItem(Drawable drawable, int orientation, int listSize, int position);

    /**
     * Default instance of a {@link SizeFactory}
     */
    private static class Default extends SizeFactory {
        private int defaultSize;

        Default(@NonNull Context context) {
            defaultSize = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
        }

        @Override
        public int sizeForItem(Drawable drawable, int orientation, int listSize, int position) {
            int size = (orientation == RecyclerView.VERTICAL) ? drawable.getIntrinsicHeight() : drawable.getIntrinsicWidth();
            // if the intrinsic size method returns -1, it means that the drawable's sizes can't be defined, e.g. ColorDrawable
            if (size == -1) {
                size = defaultSize;
            }
            return size;
        }
    }
}