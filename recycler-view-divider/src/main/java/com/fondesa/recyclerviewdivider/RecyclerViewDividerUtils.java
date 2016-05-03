package com.fondesa.recyclerviewdivider;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
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
    static int getRecyclerViewOrientation(@NonNull RecyclerView recyclerView) {
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
     * Converts a color to a Drawable
     *
     * @param color color to convert
     * @return ColorDrawable from color
     */
    public static Drawable colorToDrawable(@ColorInt int color) {
        return new ColorDrawable(color);
    }
}