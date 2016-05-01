package com.fondesa.recyclerviewdivider;

/**
 * Used to define a custom drawing logic for the visibility of the {@link RecyclerViewDivider} based on its position
 */
public abstract class PositionFactory {
    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return true if the divider will be displayed at the bottom/left of the current position, false instead
     */
    public abstract boolean displayDividerForPosition(int listSize, int position);
}