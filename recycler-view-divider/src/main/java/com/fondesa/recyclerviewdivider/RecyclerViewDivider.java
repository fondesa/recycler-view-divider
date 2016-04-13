package com.fondesa.recyclerviewdivider;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by antoniolig on 07/09/15.
 * Class that draws a divider between RecyclerView's elements
 */
@SuppressWarnings("unused")
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_COLOR = 0xDDDDDD;
    private Drawable mDivider;
    private int mOrientation;

    /**
     * Draws a divider with the default divider's color of material design and a size of 1dp
     */
    public RecyclerViewDivider() {
        this(DEFAULT_COLOR);
    }

    /**
     * Draws a divider with a custom color and a size of 1dp
     *
     * @param color color of the divider
     */
    public RecyclerViewDivider(@ColorInt int color) {
        this(color, 1);
    }

    /**
     * Draws a divider with a custom color and a custom size
     *
     * @param color color of the divider
     * @param dp    size in dp
     */
    public RecyclerViewDivider(@ColorInt int color, int dp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        final int size = dpToPx(dp);
        drawable.setSize(size, size);
        drawable.setColor(color);
        mDivider = drawable;
        mOrientation = -1;
    }

    /**
     * Draws a divider using a custom drawable
     *
     * @param drawable drawable used as divider
     */
    public RecyclerViewDivider(@NonNull Drawable drawable) {
        mDivider = drawable;
        mOrientation = -1;
    }

    /**
     * Convert dp to pixels
     *
     * @param dp dp to convert
     * @return result pixels
     */
    private static int dpToPx(int dp) {
        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawDividerBetweenItems(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            if (getRecycleOrientation(parent) == RecyclerView.VERTICAL) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

    /**
     * Draws a divider between items
     *
     * @param c      Canvas to draw views on
     * @param parent RecyclerView
     */
    private void drawDividerBetweenItems(Canvas c, RecyclerView parent) {
        final int orientation = getRecycleOrientation(parent);

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        if (orientation == RecyclerView.VERTICAL) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
        } else {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (orientation == RecyclerView.VERTICAL) {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mDivider.getIntrinsicHeight();
            } else {
                left = child.getRight() + params.rightMargin;
                right = left + mDivider.getIntrinsicHeight();
            }

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * Get the RecyclerView orientation based on layout manager
     *
     * @param recyclerView RecyclerView to use
     * @return RecyclerView.VERTICAL or RecyclerView.HORIZONTAL
     */
    private int getRecycleOrientation(RecyclerView recyclerView) {
        if (mOrientation == -1) {
            int orientation;

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            } else {
                orientation = RecyclerView.VERTICAL;
            }

            mOrientation = orientation;
        }
        return mOrientation;
    }
}