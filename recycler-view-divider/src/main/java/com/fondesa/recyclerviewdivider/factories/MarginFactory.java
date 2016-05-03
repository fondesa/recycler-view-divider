package com.fondesa.recyclerviewdivider.factories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fondesa.recycler_view_divider.R;

/**
 * Factory used to specify a custom logic to set different margins to the divider.
 * <br>
 * Custom margins will be set equally right/left with horizontal divider and top/bottom with vertical divider.
 * <br>
 * You can add a custom {@link MarginFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#marginFactory(MarginFactory)} method
 */
public abstract class MarginFactory {

    private static MarginFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link MarginFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    public static synchronized MarginFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    /**
     * Defines a custom margin size for each divider
     *
     * @param listSize size of the list in the adapter
     * @param position current position
     * @return right/left margin with horizontal divider or top/bottom margin with vertical divider
     */
    public abstract int marginSizeForItem(int listSize, int position);

    /**
     * Default instance of a {@link MarginFactory}
     */
    private static class Default extends MarginFactory {
        private int defaultMarginSize;

        Default(Context context) {
            defaultMarginSize = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_margin_size);
        }

        @Override
        public int marginSizeForItem(int listSize, int position) {
            return defaultMarginSize;
        }
    }
}