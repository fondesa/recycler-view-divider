package com.fondesa.recyclerviewdivider;

/**
 * The default drawing factory for the {@link RecyclerViewDivider}
 */
final class DefaultPositionFactory extends PositionFactory {
    /**
     * Factory method called for each item in RecyclerView's Adapter
     *
     * @param listSize list's total size
     * @param position current position
     * @return true, by default the divider is displayed for each position
     */
    @Override
    public boolean displayDividerForPosition(int listSize, int position) {
        return true;
    }
}