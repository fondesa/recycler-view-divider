package com.fondesa.recyclerviewdivider;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Utilities class
 */
public final class RecyclerViewDividerUtils {
    private RecyclerViewDividerUtils() {
        // empty constructor to avoid initialization
    }

    /**
     * Get the orientation of a RecyclerView using its layout manager
     *
     * @param recyclerView RecyclerView used to check orientation
     * @return RecyclerView.VERTICAL or RecyclerView.HORIZONTAL
     */
    static int getOrientation(@NonNull RecyclerView recyclerView) {
        int orientation;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // default LayoutManager hasn't getOrientation() method
        if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else {
            orientation = RecyclerView.VERTICAL;
        }
        return orientation;
    }

    /**
     * Get the span count of a RecyclerView.
     * <br/>
     * If the layout manager hasn't a span count (like LinearLayoutManager), the span count will be 1
     *
     * @param recyclerView RecyclerView with the attached divider
     * @return span count of the RecyclerView
     */
    static int getSpanCount(@NonNull RecyclerView recyclerView) {
        int spanCount;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // default LayoutManager hasn't getOrientation() method
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        } else {
            spanCount = 1;
        }
        return spanCount;
    }

    /**
     * Check the span size of the current item.
     * <br/>
     * The span size will be minor than or equal to the span count.
     *
     * @param recyclerView RecyclerView with the attached divider
     * @param itemPosition position of the current item
     * @return span size of the current item
     */
    static int getSpanSize(@NonNull RecyclerView recyclerView, int itemPosition) {
        int spanSize;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // default LayoutManager hasn't getOrientation() method
        if (layoutManager instanceof GridLayoutManager) {
            spanSize = ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(itemPosition);
        } else {
            spanSize = 1;
        }
        return spanSize;
    }

    /**
     * Calculate the group in which the item is.
     * <br/>
     * This value is between 0 and {@link #getGroupCount(RecyclerView, int)} - 1
     *
     * @param recyclerView RecyclerView with the attached divider
     * @param itemPosition position of the current item
     * @return the index of the group
     */
    static int getGroupIndex(@NonNull RecyclerView recyclerView, int itemPosition) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // default LayoutManager hasn't getOrientation() method
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            itemPosition = gridLayoutManager.getSpanSizeLookup().getSpanGroupIndex(itemPosition, gridLayoutManager.getSpanCount());
        }

        return itemPosition;
    }

    /**
     * Calculate the number of items' group in a list.
     * <br/>
     * If the span count is 1 (for example when the layout manager is a LinearLayoutManager), the group count will be equal to the span count.
     *
     * @param recyclerView RecyclerView with the attached divider
     * @param itemCount    number of items in the list
     * @return the number of groups
     */
    static int getGroupCount(@NonNull RecyclerView recyclerView, int itemCount) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // default LayoutManager hasn't getOrientation() method
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            int spanCount = gridLayoutManager.getSpanCount();

            int groupCount = 0, pos;
            for (pos = 0; pos < itemCount; pos++) {
                if (spanSizeLookup.getSpanIndex(pos, spanCount) == 0) {
                    groupCount++;
                }
            }
            return groupCount;
        }

        return itemCount;
    }

    /**
     * Calculate the span accumulated in this line.
     * <br/>
     * This span is calculated through the sum of the previous items' spans in this line and the current item's span
     *
     * @param recyclerView RecyclerView with the attached divider
     * @param spanSize     spanSize of the item
     * @param itemPosition position of the current item
     * @param groupIndex   current index of the group
     * @return accumulated span
     */
    static int getAccumulatedSpanInLine(@NonNull RecyclerView recyclerView, int spanSize, int itemPosition, int groupIndex) {
        int lineAccumulatedSpan = spanSize;
        int tempPos;
        for (tempPos = itemPosition - 1; tempPos >= 0; tempPos--) {
            int tempGroupIndex = RecyclerViewDividerUtils.getGroupIndex(recyclerView, tempPos);
            if (tempGroupIndex == groupIndex) {
                int tempSpanSize = RecyclerViewDividerUtils.getSpanSize(recyclerView, tempPos);
                lineAccumulatedSpan += tempSpanSize;
            } else {
                // if the group index change, it means that line is changed
                break;
            }
        }
        return lineAccumulatedSpan;
    }

    /**
     * Converts a color to a Drawable
     *
     * @param color color to convert
     * @return ColorDrawable from color
     */
    public static Drawable colorToDrawable(@ColorInt int color) {
        return new ColorDrawable(color);
    }
}