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

    /**
     * Source annotation used to define different visibility types.
     * <ul>
     * <li>{@link #SHOW_NONE}: every divider won't be shown</li>
     * <li>{@link #SHOW_ITEMS_ONLY}: divider will be shown only between items.
     * When the spanCount is equal to 1 (e.g. LinearLayoutManager), this property has the same effect of {@link #SHOW_NONE}</li>
     * <li>{@link #SHOW_GROUP_ONLY}: divider will be shown only between groups.
     * When the spanCount is equal to 1 (e.g. LinearLayoutManager), this property has the same effect of {@link #SHOW_ALL}</li>
     * <li>{@link #SHOW_ALL}: every divider will be shown</li>
     * </ul>
     */
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
     * @return a new {@link VisibilityFactory} that will now show last divider.
     */
    public static VisibilityFactory getLastItemInvisibleFactory() {
        return new LastItemInvisible();
    }

    /**
     * Defines a visibility for each group of divider
     *
     * @param groupCount number of groups in a list.
     *                   The groupCount value is equal to the list size when the span count is 1 (e.g. LinearLayoutManager).
     * @param groupIndex position of the group. The value is between 0 and groupCount - 1.
     *                   The groupIndex is equal to the item position when the span count is 1 (e.g. LinearLayoutManager).
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

    /**
     * Convenient instance to hide the last divider.
     * <br>
     * Warning: when the spanCount is major than 1, only the divider after the last group will be hidden. This factory will not affect items' dividers.
     */
    private static class LastItemInvisible extends VisibilityFactory {
        LastItemInvisible() {
            // empty constructor
        }

        @Override
        public
        @Show
        int displayDividerForItem(int groupCount, int groupIndex) {
            return groupIndex == groupCount - 1 ? SHOW_ITEMS_ONLY : SHOW_ALL;
        }
    }
}