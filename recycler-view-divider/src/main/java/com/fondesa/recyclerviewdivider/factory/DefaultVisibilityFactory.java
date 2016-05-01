package com.fondesa.recyclerviewdivider.factory;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * The default visibility factory for the {@link RecyclerViewDivider}
 */
final class DefaultVisibilityFactory extends VisibilityFactory {
    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return true, by default the divider is displayed for each position
     */
    @Override
    public boolean displayDividerForItem(int listSize, int position) {
        return true;
    }
}