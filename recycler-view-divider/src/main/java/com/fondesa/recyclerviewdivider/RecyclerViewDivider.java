package com.fondesa.recyclerviewdivider;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;

import com.fondesa.recycler_view_divider.R;

/**
 * Created by antoniolig on 07/09/15.
 * Class that draws a divider between RecyclerView's elements
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mOrientation;

    private RecyclerViewDivider.Builder builder;

    private RecyclerViewDivider(RecyclerViewDivider.Builder builder) {
        this.builder = builder;
        mDivider = builder.dividerDrawable;
        mOrientation = -1;
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public void show() {
        builder.recyclerView.addItemDecoration(this);
    }

    public void hide() {
        builder.recyclerView.removeItemDecoration(this);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawDividerBetweenItems(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            if (getRecycleOrientation(parent) == RecyclerView.VERTICAL) {
                outRect.set(0, 0, 0,builder.size);
            } else {
                outRect.set(0, 0, builder.size, 0);
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
        int margin = builder.marginSize;

        if (orientation == RecyclerView.VERTICAL) {
            left = parent.getPaddingLeft() + margin;
            right = parent.getWidth() - parent.getPaddingRight() - margin;
        } else {
            top = parent.getPaddingTop() + margin;
            bottom = parent.getHeight() - parent.getPaddingBottom() - margin;
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (orientation == RecyclerView.VERTICAL) {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + builder.size;
            } else {
                left = child.getRight() + params.rightMargin;
                right = left + builder.size;
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
            mOrientation = RecyclerUtils.getRecyclerViewOrientation(recyclerView);
        }
        return mOrientation;
    }

    public static class Builder {
        private Context context;
        private RecyclerView recyclerView;
        @ColorInt
        private int color;
        @ColorInt
        private int tint;
        private Drawable drawable;
        private int size;
        private int marginSize;

        private RecyclerViewDivider divider;
        private Drawable dividerDrawable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder addTo(@NonNull RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder color(@ColorInt int color) {
            if (drawable != null) {
                throw new RuntimeException("You can specify a color or a drawable, not both. If you want to change drawable's color, use tint() instead");
            }
            this.color = color;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder marginSize(int marginSize) {
            this.marginSize = marginSize;
            return this;
        }

        public Builder drawable(@NonNull Drawable drawable) {
            if (color != 0) {
                throw new RuntimeException("You can specify a color or a drawable, not both. If you want to change drawable's color, use tint() instead");
            }
            this.drawable = drawable;
            return this;
        }

        public Builder tint(@ColorInt int color) {
            if (drawable == null) {
                throw new RuntimeException("You have to specify a drawable to use tint(), if you want to set a color, use color() instead");
            }
            this.tint = color;
            return this;
        }

        public RecyclerViewDivider build() {
            if (drawable == null) {
                if (color == 0) {
                    color = ContextCompat.getColor(context, R.color.recycler_view_divider_color);
                }
                if (size == 0) {
                    size = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
                }
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setColor(color);
                dividerDrawable = gradientDrawable;
            } else {
                if (size == 0) {
                    size = drawable.getIntrinsicHeight();
                    if (size == -1) {
                        size = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
                    }
                }
                if (tint != 0) {
                    Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(wrappedDrawable, tint);
                    dividerDrawable = wrappedDrawable;
                }
                dividerDrawable = drawable;
            }

            divider = new RecyclerViewDivider(this);
            return divider;
        }
    }
}