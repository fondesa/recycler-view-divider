package com.fondesa.recyclerviewdivider.factory;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * Used to define a custom drawing logic for the left/right margins in an horizontal {@link RecyclerViewDivider}
 * and top/bottom margins in a vertical {@link RecyclerViewDivider} based on its position
 */
public abstract class MarginFactory {

    private static MarginFactory defaultFactory;

    /**
     * Creates a new instance of {@link DefaultMarginFactory} if system hasn't initialized it yet.
     *
     * @return default {@link MarginFactory} factory of the system
     */
    public static synchronized MarginFactory getDefault() {
        if (defaultFactory == null) {
            defaultFactory = new DefaultMarginFactory();
        }
        return defaultFactory;
    }

    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return left/right margins in an horizontal {@link RecyclerViewDivider} and top/bottom margins in a vertical {@link RecyclerViewDivider}
     */
    public abstract int marginSizeForItem(int listSize, int position);
}