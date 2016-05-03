package com.fondesa.recyclerviewdivider.factories;

/**
 * Factory used to specify a custom logic to set visibility for each divider.
 * <br>
 * You can add a custom {@link VisibilityFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#visibilityFactory(VisibilityFactory)} method
 */
public abstract class VisibilityFactory {

    private static VisibilityFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link VisibilityFactory} to avoid multiple instance of the same class
     *
     * @return factory with default values
     */
    public static synchronized VisibilityFactory getDefault() {
        if (defaultFactory == null) {
            defaultFactory = new Default();
        }
        return defaultFactory;
    }

    /**
     * Defines a visibility for each divider
     *
     * @param listSize size of the list in the adapter
     * @param position current position
     * @return true if the divider will be visible, false instead
     */
    public abstract boolean displayDividerForItem(int listSize, int position);

    /**
     * Default instance of a {@link VisibilityFactory}
     */
    private static class Default extends VisibilityFactory {
        Default() {
            // empty constructor
        }

        @Override
        public boolean displayDividerForItem(int listSize, int position) {
            return true;
        }
    }
}