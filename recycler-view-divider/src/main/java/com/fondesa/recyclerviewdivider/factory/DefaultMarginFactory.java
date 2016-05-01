package com.fondesa.recyclerviewdivider.factory;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * The default margin factory for the {@link RecyclerViewDivider}
 */
final class DefaultMarginFactory extends MarginFactory {
    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return 0, by default the divider hasn't margin
     */
    @Override
    public int marginSizeForItem(int listSize, int position) {
        return 0;
    }
}