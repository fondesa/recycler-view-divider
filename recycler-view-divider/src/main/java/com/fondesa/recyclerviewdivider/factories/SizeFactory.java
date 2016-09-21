package com.fondesa.recyclerviewdivider.factories;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.fondesa.recycler_view_divider.R;

/**
 * Factory used to specify a custom logic to set different sizes to the divider.
 * <br>
 * Size is referred to the height of an horizontal divider and to the width of a vertical divider.
 * <br>
 * You can add a custom {@link SizeFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#sizeFactory(SizeFactory)} method
 */
public abstract class SizeFactory {

    private static Default defaultFactory;

    /**
     * Creates a singleton instance of a default {@link SizeFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    public static synchronized SizeFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    /**
     * Creates a new {@link SizeFactory} with equal size for all dividers
     *
     * @param size dividers' size
     * @return factory with same values for each divider
     */
    public static SizeFactory getGeneralFactory(int size) {
        return new General(size);
    }

    /**
     * Defines a custom size for each group of divider
     *
     * @param drawable    current divider's drawable
     * @param orientation RecyclerView.VERTICAL or RecyclerView.HORIZONTAL
     * @param groupCount  number of groups in a list.
     *                    The groupCount value is equal to the list size when the span count is 1 (e.g. LinearLayoutManager).
     * @param groupIndex  position of the group. The value is between 0 and groupCount - 1.
     *                    The groupIndex is equal to the item position when the span count is 1 (e.g. LinearLayoutManager).
     * @return height for an horizontal divider, width for a vertical divider
     */
    public abstract int sizeForItem(@Nullable Drawable drawable, int orientation, int groupCount, int groupIndex);

    /**
     * Default instance of a {@link SizeFactory}
     */
    private static class Default extends SizeFactory {
        private int defaultSize;

        Default(@NonNull Context context) {
            defaultSize = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
        }

        @Override
        public int sizeForItem(@Nullable Drawable drawable, int orientation, int groupCount, int groupIndex) {
            int size;
            if (drawable != null) {
                size = (orientation == RecyclerView.VERTICAL) ? drawable.getIntrinsicHeight() : drawable.getIntrinsicWidth();
            } else {
                size = -1;
            }
            // if the size is equals to -1, it means that the drawable is null or drawable's sizes can't be defined, e.g. ColorDrawable
            if (size == -1) {
                size = defaultSize;
            }
            return size;
        }
    }

    /**
     * General instance of a {@link SizeFactory} used when the size is set with {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#size(int)}
     */
    private static class General extends SizeFactory {
        private int size;

        General(int size) {
            this.size = size;
        }

        @Override
        public int sizeForItem(@Nullable Drawable drawable, int orientation, int groupCount, int groupIndex) {
            return size;
        }
    }
}