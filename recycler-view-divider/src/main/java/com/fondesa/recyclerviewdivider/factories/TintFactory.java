package com.fondesa.recyclerviewdivider.factories;

import android.support.annotation.ColorInt;

/**
 * Factory used to specify a custom logic to use different tint colors to tint divider's drawables.
 * <br>
 * You can add a custom {@link TintFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#tintFactory(TintFactory)} method
 */
public abstract class TintFactory {

    /**
     * Creates a new {@link TintFactory} with equal tint color for all dividers's drawables
     *
     * @param tint tint color for dividers' drawables
     * @return factory with same values for each divider
     */
    public static TintFactory getGeneralFactory(@ColorInt int tint) {
        return new General(tint);
    }

    /**
     * Defines a custom tint color for each divider's drawable
     *
     * @param listSize size of the list in the adapter
     * @param position current position
     * @return tint color for the divider's drawable in the current position
     */
    public abstract int tintForItem(int listSize, int position);

    /**
     * General instance of a {@link TintFactory} used when the tint color is set with {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#tint(int)}
     */
    private static class General extends TintFactory {
        @ColorInt
        private int tint;

        General(@ColorInt int tint) {
            this.tint = tint;
        }

        @Override
        public int tintForItem(int listSize, int position) {
            return tint;
        }
    }
}