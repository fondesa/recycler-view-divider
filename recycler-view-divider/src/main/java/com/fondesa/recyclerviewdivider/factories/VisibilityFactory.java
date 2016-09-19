package com.fondesa.recyclerviewdivider.factories;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Factory used to specify a custom logic to set visibility for each divider.
 * <br>
 * You can add a custom {@link VisibilityFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#visibilityFactory(VisibilityFactory)} method
 */
@SuppressWarnings("WeakerAccess")
public abstract class VisibilityFactory {

    public static final int SHOW_NONE = 0;
    public static final int SHOW_ITEMS_ONLY = 1;
    public static final int SHOW_GROUP_ONLY = 2;
    public static final int SHOW_ALL = 3;

    @IntDef({SHOW_NONE, SHOW_ITEMS_ONLY, SHOW_GROUP_ONLY, SHOW_ALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Show {
        // empty annotation body
    }

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
     * Defines a visibility for each group of divider
     *
     * @param groupCount size of the list in the adapter
     * @param groupIndex current position
     * @return true if the divider will be visible, false instead
     */
    public abstract
    @Show
    int displayDividerForItem(int groupCount, int groupIndex);

    /**
     * Default instance of a {@link VisibilityFactory}
     */
    private static class Default extends VisibilityFactory {
        Default() {
            // empty constructor
        }

        @Override
        public
        @Show
        int displayDividerForItem(int groupCount, int groupIndex) {
            return SHOW_ALL;
        }
    }
}