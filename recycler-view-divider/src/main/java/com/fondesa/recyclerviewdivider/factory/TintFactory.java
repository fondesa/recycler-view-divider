package com.fondesa.recyclerviewdivider.factory;

/**
 * Factory used to specify a custom logic to use different tint colors to tint divider's drawables.
 * <br>
 * You can add a custom {@link TintFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#tintFactory(TintFactory)} method
 */
public abstract class TintFactory {

    /**
     * Defines a custom tint color for each divider's drawable
     *
     * @param listSize size of the list in the adapter
     * @param position current position
     * @return tint color for the divider's drawable in the current position
     */
    public abstract int tintForItem(int listSize, int position);
}