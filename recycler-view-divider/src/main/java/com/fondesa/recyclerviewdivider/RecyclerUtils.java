package com.fondesa.recyclerviewdivider;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by antoniolig on 30/04/2016.
 */
public class RecyclerUtils {
    public static int getRecyclerViewOrientation(RecyclerView recyclerView) {
        int orientation;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else {
            orientation = RecyclerView.VERTICAL;
        }
        return orientation;
    }


//    /**
//     * Convert dp to pixels
//     *
//     * @param dp dp to convert
//     * @return result pixels
//     */
//    private static int dpToPx(int dp) {
//        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
//        return (int) ((dp * displayMetrics.density) + 0.5);
//    }

}
