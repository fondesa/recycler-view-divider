package com.fondesa.recyclerviewdivider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.fondesa.recycler_view_divider.R;
import com.fondesa.recyclerviewdivider.factory.DrawableFactory;
import com.fondesa.recyclerviewdivider.factory.MarginFactory;
import com.fondesa.recyclerviewdivider.factory.VisibilityFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * Class that draws a divider between RecyclerView's elements
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private static final String TAG = "RecyclerViewDivider";

    private Builder builder;

    /**
     * Set the {@link Builder} for this {@link RecyclerViewDivider}
     *
     * @param builder {@link Builder} with properties initialized
     */
    private RecyclerViewDivider(@NonNull Builder builder) {
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
        // get the value of RecyclerView from the WeakReference
        RecyclerView recyclerView = builder.recyclerViewRef.get();
        if (recyclerView != null) {
            recyclerView.addItemDecoration(this);
        }
    }

    /**
     * Remove this divider from the RecyclerView
     */
    public void remove() {
        // get the value of RecyclerView from the WeakReference
        RecyclerView recyclerView = builder.recyclerViewRef.get();
        if (recyclerView != null) {
            recyclerView.removeItemDecoration(this);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // if the divider isn't a simple space, it will be drawn
        if (builder.type != TYPE_SPACE) {
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter != null) {
                int listSize = adapter.getItemCount();
                if (listSize > 0) {
                    int left;
                    int top;
                    int right;
                    int bottom;

                    int childCount = parent.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        final View child = parent.getChildAt(i);
                        int margin = builder.marginFactory.marginSizeForItem(listSize, parent.getChildAdapterPosition(child));
                        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                        if (builder.orientation == RecyclerView.VERTICAL) {
                            top = child.getBottom() + params.bottomMargin;
                            bottom = top + builder.size;
                            left = parent.getPaddingLeft() + margin;
                            right = parent.getWidth() - parent.getPaddingRight() - margin;
                        } else {
                            top = parent.getPaddingTop() + margin;
                            bottom = parent.getHeight() - parent.getPaddingBottom() - margin;
                            left = child.getRight() + params.rightMargin;
                            right = left + builder.size;
                        }

                        builder.divider.setBounds(left, top, right, bottom);
                        builder.divider.draw(c);
                    }
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int listSize = parent.getAdapter().getItemCount();
        if (listSize > 0) {
            if (builder.visibilityFactory.displayDividerForItem(listSize, parent.getChildAdapterPosition(view))) {
                if (builder.orientation == RecyclerView.VERTICAL) {
                    outRect.set(0, 0, 0, builder.size);
                } else {
                    outRect.set(0, 0, builder.size, 0);
                }
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
        private static final int INT_DEF = -1;

        private WeakReference<Context> contextRef;
        private WeakReference<RecyclerView> recyclerViewRef;
        private int orientation;

        @ColorInt
        private int color;
        @ColorInt
        private Drawable drawable;
        @LayoutRes
        private int layout;
        private int tint;
        private int size;
        private int marginSize;
        private VisibilityFactory visibilityFactory;
        private MarginFactory marginFactory;
        private DrawableFactory drawableFactory;

        @Type
        private int type;

        private Drawable divider;

        /**
         * Initialize this {@link Builder} with a context.
         * The Context object will be stored in a WeakReference to avoid memory leak
         *
         * @param context current context
         */
        public Builder(@NonNull Context context) {
            contextRef = new WeakReference<>(context);
            color = INT_DEF;
            tint = INT_DEF;
            size = INT_DEF;
            marginSize = INT_DEF;
            type = TYPE_COLOR;
        }

        /**
         * Add the {@link RecyclerViewDivider} to the {@link Builder}'s instance.
         * The RecyclerView object will be stored in a WeakReference to avoid memory leak
         *
         * @param recyclerView RecyclerView on which the divider will be displayed on
         * @return {@link Builder} instance
         */
        public Builder addTo(@NonNull RecyclerView recyclerView) {
            recyclerViewRef = new WeakReference<>(recyclerView);
            return this;
        }

        /**
         * Set the type of {@link RecyclerViewDivider} as a space
         *
         * @return {@link Builder} instance
         */
        public Builder asSpace() {
            type = TYPE_SPACE;
            return this;
        }

        /**
         * Set the divider's color. This method can't be used with {@link #drawable(Drawable)} or {@link #tint(int)}
         *
         * @param color resolved color for this divider, not a resource
         * @return {@link Builder} instance
         */
        public Builder color(@ColorInt int color) {
            this.color = color;
            type = TYPE_COLOR;
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
            this.drawable = drawable;
            type = TYPE_DRAWABLE;
            return this;
        }


        public Builder layout(@LayoutRes int layout) {
            this.layout = layout;
            type = TYPE_LAYOUT;
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
            tint = color;
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
         * Set the divider's custom {@link VisibilityFactory}
         *
         * @param visibilityFactory custom {@link VisibilityFactory} to set
         * @return {@link Builder} instance
         */
        public Builder visibilityFactory(@Nullable VisibilityFactory visibilityFactory) {
            this.visibilityFactory = visibilityFactory;
            return this;
        }

        /**
         * Set the divider's custom {@link MarginFactory}
         *
         * @param marginFactory custom {@link MarginFactory} to set
         * @return {@link Builder} instance
         */
        public Builder marginFactory(@Nullable MarginFactory marginFactory) {
            this.marginFactory = marginFactory;
            return this;
        }

        public Builder drawableFactory(@Nullable DrawableFactory drawableFactory) {
            this.drawableFactory = drawableFactory;
            return this;
        }

        /**
         * Creates a new {@link RecyclerViewDivider} with given configurations and initializes default values.
         * Default values will be initialized in two different ways if the builder uses a custom Drawable or a plain divider.
         * <br>
         * <b>Plain divider:</b>
         * <ul>
         * <li>Color: if {@link #color(int)} wasn't used, the default is #CCCCCC that can be overriden with the colors resource <i>recycler_view_divider_color</i></li>
         * <li>Size: if {@link #size(int)} wasn't used, the default is 1dp that can be overriden with the dimens resource <i>recycler_view_divider_size</i></li>
         * </ul>
         * <b>Custom Drawable:</b>
         * <ul>
         * <li>Size: if {@link #size(int)} wasn't used, the size will be determined by Drawable's intrinsic size.
         * If the size can't be determined yet, the default is 1dp that can be overriden with the dimens resource <i>recycler_view_divider_size</i></li>
         * <li>Tint: if {@link #tint(int)} wasn't used, the drawable won't be tinted</li>
         * </ul>
         * <br>
         * The orientation will be determined by LayoutManager's orientation
         *
         * @return a new {@link RecyclerViewDivider} with these {@link Builder} configurations
         */
        @SuppressLint("SwitchIntDef")
        public RecyclerViewDivider build() {
            // get the value of RecyclerView from the WeakReference
            RecyclerView recyclerView = recyclerViewRef.get();
            if (recyclerView != null) {
                // get RecyclerView's orientation
                orientation = RecyclerUtils.getRecyclerViewOrientation(recyclerView);
            }
            // init default values
            if (type != TYPE_SPACE) {
                // get the value of Context from the WeakReference
                Context context = contextRef.get();
                if (context != null) {
                    // all drawing properties will be set if RecyclerViewDivider is used as a divider, not a space
                    switch (type) {
                        case TYPE_COLOR:
                            // in this case a custom drawable wasn't specified
                            // init default color if not specified
                            if (color == INT_DEF) {
                                color = ContextCompat.getColor(context, R.color.recycler_view_divider_color);
                            }
                            // creates a custom color drawable with this color
                            divider = new ColorDrawable(color);
                            break;

                        case TYPE_DRAWABLE:
                            // tint drawable if specified
                            divider = drawable;
                            break;
                        case TYPE_LAYOUT:
                            View view = LayoutInflater.from(context).inflate(layout, null);
                            view.setDrawingCacheEnabled(true);

                            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            view.measure(measureSpec, measureSpec);

                            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

                            view.buildDrawingCache(true);
                            Bitmap realBitmap = Bitmap.createBitmap(view.getDrawingCache());
                            view.setDrawingCacheEnabled(false);

                            divider = new BitmapDrawable(context.getResources(), realBitmap);
                            break;
                    }

                    // init default size if not specified
                    if (size == INT_DEF) {
                        // get the size from the drawable's size
                        size = (orientation == RecyclerView.VERTICAL) ? divider.getIntrinsicHeight() : divider.getIntrinsicWidth();
                        if (size == -1) {
                            size = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
                        }
                    }

                    // if margins arent' overriden, the divider will use the default margins factory
                    if (marginFactory == null) {
                        // if marginSize is specified, it will be used as default size for all items
                        if (marginSize == INT_DEF) {
                            marginFactory = MarginFactory.getDefault(context);
                        } else {
                            marginFactory = new MarginFactory() {
                                @Override
                                public int marginSizeForItem(int listSize, int position) {
                                    return marginSize;
                                }
                            };
                        }
                    }

                    if (tint != INT_DEF) {
                        Drawable wrappedDrawable = DrawableCompat.wrap(divider);
                        DrawableCompat.setTint(wrappedDrawable, tint);
                        divider = wrappedDrawable;
                    }
                }
            } else {
                // help GC to dealloc other values or bring them to default
                if (marginFactory != null) {
                    marginFactory = null;
                }
            }
            // if the VisibilityFactory is still null, the divider will use the default factory
            if (visibilityFactory == null) {
                visibilityFactory = VisibilityFactory.getDefault();
            }
            // help GC to dealloc other values or bring them to default
            if (color != INT_DEF) {
                color = INT_DEF;
            }

            if (tint != INT_DEF) {
                tint = INT_DEF;
            }

            if (drawable != null) {
                drawable = null;
            }
            // creates divider for this builder
            return new RecyclerViewDivider(this);
        }
    }

    public static final int TYPE_COLOR = 0;
    public static final int TYPE_DRAWABLE = 1;
    public static final int TYPE_LAYOUT = 2;
    public static final int TYPE_SPACE = 3;

    @IntDef({TYPE_COLOR, TYPE_DRAWABLE, TYPE_LAYOUT, TYPE_SPACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
}