package com.fondesa.recyclerviewdivider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fondesa.recycler_view_divider.R;

/**
 * Class that draws a divider between RecyclerView's elements
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private RecyclerViewDivider.Builder builder;

    /**
     * Set the {@link Builder} for this {@link RecyclerViewDivider}
     *
     * @param builder {@link Builder} with properties initialized
     */
    private RecyclerViewDivider(@NonNull RecyclerViewDivider.Builder builder) {
        this.builder = builder;
    }

    /**
     * Creates a new {@link Builder} for the current context
     *
     * @param context current context
     * @return a new {@link Builder} instance
     */
    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    /**
     * Show this divider on the RecyclerView
     */
    public void show() {
        builder.recyclerView.addItemDecoration(this);
    }

    /**
     * Remove this divider from the RecyclerView
     */
    public void remove() {
        builder.recyclerView.removeItemDecoration(this);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawDividerBetweenItems(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            if (builder.orientation == RecyclerView.VERTICAL) {
                outRect.set(0, 0, 0, builder.size);
            } else {
                outRect.set(0, 0, builder.size, 0);
            }
        }
    }

    /**
     * Draws a divider between items based on Builder's properties
     *
     * @param c      Canvas to draw views on
     * @param parent RecyclerView added to the builder
     */
    private void drawDividerBetweenItems(Canvas c, RecyclerView parent) {
        final int orientation = builder.orientation;

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

            builder.divider.setBounds(left, top, right, bottom);
            builder.divider.draw(c);
        }
    }

    /**
     * {@link Builder} class for {@link RecyclerViewDivider}. This class can set these custom properties:
     * <ul>
     * <li><b>Size:</b> {@link #size(int)}</li>
     * <li><b>Margins:</b> {@link #marginSize(int)}</li>
     * <li><b>Color:</b> {@link #color(int)}</li>
     * <li><b>Drawable:</b> {@link #drawable(Drawable)}</li>
     * <li><b>Tint of the drawable:</b> {@link #tint(int)}</li>
     * </ul>
     */
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
        private int orientation;

        private Drawable divider;

        /**
         * Initialize this {@link Builder} with a context.
         *
         * @param context current context
         */
        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * Add the {@link RecyclerViewDivider} to the {@link Builder}'s instance.
         *
         * @param recyclerView RecyclerView on which the divider will be displayed on
         * @return {@link Builder} instance
         */
        public Builder addTo(@NonNull RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        /**
         * Set the divider's color. This method can't be used with {@link #drawable(Drawable)} or {@link #tint(int)}
         *
         * @param color resolved color for this divider, not a resource
         * @return {@link Builder} instance
         */
        public Builder color(@ColorInt int color) {
            if (drawable != null) {
                throw new RuntimeException("You can specify a color or a drawable, not both. If you want to change drawable's color, use tint() instead");
            }
            this.color = color;
            return this;
        }

        /**
         * Set the divider's size. The divider's final size will depend on RecyclerView's orientation:
         * <ul>
         * <li><b>RecyclerView.VERTICAL:</b> the height will be equal to the size and the width will be equal to container's width + {@link #marginSize(int)}</li>
         * <li><b>RecyclerView.HORIZONTAL:</b> the width will be equal to the size and the height will be equal to container's height + {@link #marginSize(int)}</li>
         * </ul>
         *
         * @param size size in pixels for this divider
         * @return {@link Builder} instance
         */
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        /**
         * Set the divider's margins. They will depend on RecyclerView's orientation:
         * <ul>
         * <li><b>RecyclerView.VERTICAL:</b> margins will be added equally to the left and to the right</li>
         * <li><b>RecyclerView.HORIZONTAL:</b> margins will be added equally to the top and to the bottom</li>
         * </ul>
         *
         * @param marginSize margins' size in pixels for this divider
         * @return {@link Builder} instance
         */
        public Builder marginSize(int marginSize) {
            this.marginSize = marginSize;
            return this;
        }

        /**
         * Set the divider's drawable. This method can't be used with {@link #color(int)}.
         * If you want to color the drawable, you have to use {@link #tint(int)} instead.
         *
         * @param drawable custom drawable for this divider
         * @return {@link Builder} instance
         */
        public Builder drawable(@NonNull Drawable drawable) {
            if (color != 0) {
                throw new RuntimeException("You can specify a color or a drawable, not both. If you want to change drawable's color, use tint() instead");
            }
            this.drawable = drawable;
            return this;
        }

        /**
         * Set the divider's drawable tint color. This method can't be used with {@link #color(int)} and without {@link #drawable(Drawable)}.
         * If you want to create a plain divider with a single color you can use {@link #color(int)} instead.
         *
         * @param color color that will be used as drawable's tint
         * @return {@link Builder} instance
         */
        public Builder tint(@ColorInt int color) {
            if (drawable == null) {
                throw new RuntimeException("You have to specify a drawable to use tint(), if you want to set a color, use color() instead");
            }
            this.tint = color;
            return this;
        }

        public RecyclerViewDivider build() {
            orientation = RecyclerUtils.getRecyclerViewOrientation(recyclerView);

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
                divider = gradientDrawable;
            } else {
                if (size == 0) {
                    size = (orientation == RecyclerView.VERTICAL) ? drawable.getIntrinsicHeight() : drawable.getIntrinsicWidth();
                    if (size == -1) {
                        size = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
                    }
                }
                if (tint != 0) {
                    Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(wrappedDrawable, tint);
                    divider = wrappedDrawable;
                }
                divider = drawable;
            }

            return new RecyclerViewDivider(this);
        }
    }
}