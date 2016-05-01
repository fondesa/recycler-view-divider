package com.fondesa.recyclerviewdivider.factory;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

/**
 * Used to define a custom drawing logic for the visibility of the {@link RecyclerViewDivider} based on its position
 */
public abstract class VisibilityFactory {

    private static DefaultVisibilityFactory defaultFactory;

    /**
     * Creates a new instance of {@link DefaultVisibilityFactory} if system hasn't initialized it yet.
     *
     * @return default {@link VisibilityFactory} factory of the system
     */
    public static synchronized VisibilityFactory getDefault() {
        if (defaultFactory == null) {
            defaultFactory = new DefaultVisibilityFactory();
        }
        return defaultFactory;
    }

    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return true if the divider will be displayed at the bottom/left of the current position, false instead
     */
    public abstract boolean displayDividerForItem(int listSize, int position);
}