package com.fondesa.recyclerviewdivider.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fondesa.recycler_view_divider.R;
import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * The default margin factory for the {@link RecyclerViewDivider}
 */
final class DefaultMarginFactory extends MarginFactory {
    private int defaultMarginSize;

    public DefaultMarginFactory(@NonNull Context context) {
        defaultMarginSize = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_margin_size);
    }
    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return 0, by default the divider hasn't margin
     */
    @Override
    public int marginSizeForItem(int listSize, int position) {
        return defaultMarginSize;
    }
}