package com.fondesa.recyclerviewdivider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fondesa.recyclerviewdivider.factories.DrawableFactory;
import com.fondesa.recyclerviewdivider.factories.MarginFactory;
import com.fondesa.recyclerviewdivider.factories.SizeFactory;
import com.fondesa.recyclerviewdivider.factories.TintFactory;
import com.fondesa.recyclerviewdivider.factories.VisibilityFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * Class that draws a divider between RecyclerView's elements
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    private static final String TAG = "RecyclerViewDivider";

    private static final int TYPE_SPACE = -1;
    private static final int TYPE_COLOR = 0;
    private static final int TYPE_DRAWABLE = 1;

    private static final int REMAINDER_FIRST_ELEMENT = 1;
    private static final int REMAINDER_LAST_ELEMENT = 0;

    private Builder mBuilder;
    private boolean mIsDividerAdded;

    /**
     * Set the {@link Builder} for this {@link RecyclerViewDivider}
     *
     * @param builder {@link Builder} with properties initialized
     */
    private RecyclerViewDivider(@NonNull Builder builder) {
        mBuilder = builder;
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
     * Add this divider on the RecyclerView
     */
    public void attach() {
        // get the value of RecyclerView from the WeakReference
        RecyclerView recyclerView = mBuilder.recyclerViewRef.get();
        if (recyclerView != null) {
            if (!mIsDividerAdded) {
                recyclerView.addItemDecoration(this);
                mIsDividerAdded = true;
            } else {
                recyclerView.invalidateItemDecorations();
            }
        }
    }

    /**
     * Remove this divider from the RecyclerView
     */
    public void detach() {
        // get the value of RecyclerView from the WeakReference
        RecyclerView recyclerView = mBuilder.recyclerViewRef.get();
        if (recyclerView != null) {
            if (mIsDividerAdded) {
                recyclerView.removeItemDecoration(this);
                mIsDividerAdded = false;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final RecyclerView.Adapter adapter = parent.getAdapter();
        final int listSize;

        // if the divider isn't a simple space, it will be drawn
        if (mBuilder.type == TYPE_SPACE || adapter == null || (listSize = adapter.getItemCount()) == 0)
            return;

        int left;
        int top;
        int right;
        int bottom;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int itemPosition = parent.getChildAdapterPosition(child);
            final int groupIndex = RecyclerViewDividerUtils.getGroupIndex(parent, itemPosition);
            final int groupCount = RecyclerViewDividerUtils.getGroupCount(parent, listSize);

            Drawable divider = mBuilder.drawableFactory.drawableForItem(groupCount, groupIndex);
            @VisibilityFactory.Show int showDivider = mBuilder.visibilityFactory.displayDividerForItem(groupCount, groupIndex);

            if (divider == null || showDivider == VisibilityFactory.SHOW_NONE) continue;

            final int orientation = mBuilder.orientation;
            final int spanCount = mBuilder.spanCount;
            final int spanSize = RecyclerViewDividerUtils.getSpanSize(parent, groupIndex);

            final int margin = mBuilder.marginFactory.marginSizeForItem(groupCount, groupIndex);
            int size = mBuilder.sizeFactory.sizeForItem(divider, orientation, groupCount, groupIndex);
            TintFactory tintFactory = mBuilder.tintFactory;
            if (tintFactory != null) {
                final int tint = tintFactory.tintForItem(groupCount, groupIndex);
                Drawable wrappedDrawable = DrawableCompat.wrap(divider);
                DrawableCompat.setTint(wrappedDrawable, tint);
                divider = wrappedDrawable;
            }

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int halfSize = size < 2 ? size : size / 2;

            size = showDivider == VisibilityFactory.SHOW_ITEMS_ONLY ? 0 : size;
            halfSize = showDivider == VisibilityFactory.SHOW_GROUP_ONLY ? 0 : halfSize;

            // the remainder is calculated to know where the item is inside the group
            final int remainderInSpan = (itemPosition + spanSize) % spanCount;

            final int childBottom = child.getBottom();
            final int childTop = child.getTop();
            final int childRight = child.getRight();
            final int childLeft = child.getLeft();

            // if the last element in the span doesn't complete the span count, its size will be full, not the half
            // halfSize * 2 is used instead of size to handle the case Show.ITEMS_ONLY in which size will be == 0
            final int lastElementInSpanSize = itemPosition == listSize - 1 ? halfSize * 2 : halfSize;

            final boolean useCellMargin = margin == 0;

            int marginToAddBefore, marginToAddAfter;
            marginToAddBefore = marginToAddAfter = 0;

            if (orientation == RecyclerView.VERTICAL) {
                if (spanCount > 1) {
                    top = childTop + margin;
                    // size is added to draw filling point between horizontal and vertical dividers
                    bottom = childBottom - margin;

                    if (useCellMargin) {
                        if (groupIndex > 0) {
                            top -= params.topMargin;
                        }
                        if (groupIndex < groupCount - 1 || size > 0) {
                            bottom += params.bottomMargin;
                        }
                        bottom += size;
                    }

                    if (remainderInSpan == REMAINDER_FIRST_ELEMENT) {
                        // first element in the group
                        left = childRight + margin + params.rightMargin;
                        right = left + lastElementInSpanSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        if (useCellMargin) {
                            marginToAddAfter = params.rightMargin;
                        }
                    } else if (remainderInSpan == REMAINDER_LAST_ELEMENT) {
                        // last element in the group
                        right = childLeft - margin - params.leftMargin;
                        left = right - halfSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        if (useCellMargin) {
                            marginToAddBefore = params.leftMargin;
                        }
                    } else {
                        // element in the middle
                        // left half divider
                        right = childLeft - margin - params.leftMargin;
                        left = right - halfSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        // right half divider
                        left = childRight + margin + params.rightMargin;
                        right = left + lastElementInSpanSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        if (useCellMargin) {
                            marginToAddAfter = params.rightMargin;
                            marginToAddBefore = params.leftMargin;
                        }
                    }
                }

                // draw bottom divider
                top = childBottom + params.bottomMargin;
                bottom = top + size;
                left = childLeft + margin - marginToAddBefore;
                right = childRight - margin + marginToAddAfter;

                setBoundsAndDraw(divider, c, left, top, right, bottom);

            } else {
                if (spanCount > 1) {
                    left = childLeft + margin;
                    // size is added to draw filling point between horizontal and vertical dividers
                    right = childRight - margin;
                    if (useCellMargin) {
                        if (groupIndex > 0) {
                            left -= params.leftMargin;
                        }
                        if (groupIndex < groupCount - 1 || size > 0) {
                            right += params.rightMargin;
                        }
                        right += size;
                    }

                    if (remainderInSpan == REMAINDER_FIRST_ELEMENT) {
                        // first element in the group
                        top = childBottom + margin + params.bottomMargin;
                        bottom = top + lastElementInSpanSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        if (useCellMargin) {
                            marginToAddAfter = params.bottomMargin;
                        }
                    } else if (remainderInSpan == REMAINDER_LAST_ELEMENT) {
                        // last element in the group
                        bottom = childTop - margin - params.topMargin;
                        top = bottom - halfSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        if (useCellMargin) {
                            marginToAddBefore = params.topMargin;
                        }
                    } else {
                        // element in the middle
                        // top half divider
                        bottom = childTop - margin - params.topMargin;
                        top = bottom - halfSize;

                        divider.setBounds(left, top, right, bottom);
                        divider.draw(c);

                        // bottom half divider
                        top = childBottom + margin + params.bottomMargin;
                        bottom = top + lastElementInSpanSize;

                        setBoundsAndDraw(divider, c, left, top, right, bottom);

                        if (useCellMargin) {
                            marginToAddAfter = params.bottomMargin;
                            marginToAddBefore = params.topMargin;
                        }
                    }
                }

                // draw right divider
                bottom = childBottom - margin + marginToAddAfter;
                top = childTop + margin - marginToAddBefore;
                left = childRight + params.rightMargin;
                right = left + size;

                setBoundsAndDraw(divider, c, left, top, right, bottom);
            }
        }
    }

    /**
     * Set the Drawable's bounds and draw it on a Canvas
     *
     * @param drawable Drawable to draw
     * @param canvas   Canvas used to show the Drawable
     * @param left     left position in px
     * @param top      top position in px
     * @param right    right position in px
     * @param bottom   bottom position in px
     */
    private void setBoundsAndDraw(@NonNull Drawable drawable, @NonNull Canvas canvas, int left, int top, int right, int bottom) {
        drawable.setBounds(left, top, right, bottom);
        drawable.draw(canvas);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int listSize = parent.getAdapter().getItemCount();
        if (listSize > 0) {
            int itemPosition = parent.getChildAdapterPosition(view);
            final int groupPosition = RecyclerViewDividerUtils.getGroupIndex(parent, itemPosition);
            final int groupCount = RecyclerViewDividerUtils.getGroupCount(parent, listSize);

            @VisibilityFactory.Show int showDivider = mBuilder.visibilityFactory.displayDividerForItem(groupCount, groupPosition);
            if (showDivider != VisibilityFactory.SHOW_NONE) {
                final int orientation = mBuilder.orientation;
                final int spanCount = mBuilder.spanCount;
                final int spanSize = RecyclerViewDividerUtils.getSpanSize(parent, groupPosition);

                final Drawable divider = mBuilder.drawableFactory.drawableForItem(groupCount, groupPosition);
                int size = mBuilder.sizeFactory.sizeForItem(divider, orientation, groupCount, groupPosition);
                int marginSize = mBuilder.marginFactory.marginSizeForItem(groupCount, groupPosition);

                // the remainder is calculated to know where the item is inside the group
                final int remainderInSpan = (itemPosition + spanSize) % spanCount;
                int halfSize = size / 2 + marginSize;

                size = showDivider == VisibilityFactory.SHOW_ITEMS_ONLY ? 0 : size;
                halfSize = showDivider == VisibilityFactory.SHOW_GROUP_ONLY ? 0 : halfSize;

                if (orientation == RecyclerView.VERTICAL) {
                    if (spanCount == 1) {
                        // LinearLayoutManager or GridLayoutManager with 1 column
                        outRect.set(0, 0, 0, size);
                    } else if (remainderInSpan == REMAINDER_FIRST_ELEMENT) {
                        // first element in the group
                        outRect.set(0, 0, halfSize, size);
                    } else if (remainderInSpan == REMAINDER_LAST_ELEMENT) {
                        // last element in the group
                        outRect.set(halfSize, 0, 0, size);
                    } else {
                        // element in the middle
                        outRect.set(halfSize, 0, halfSize, size);
                    }
                } else {
                    if (spanCount == 1) {
                        // LinearLayoutManager or GridLayoutManager with 1 row
                        outRect.set(0, 0, size, 0);
                    } else if (remainderInSpan == REMAINDER_FIRST_ELEMENT) {
                        // first element in the group
                        outRect.set(0, 0, size, halfSize);
                    } else if (remainderInSpan == REMAINDER_LAST_ELEMENT) {
                        // last element in the group
                        outRect.set(0, halfSize, size, 0);
                    } else {
                        // element in the middle
                        outRect.set(0, halfSize, size, halfSize);
                    }
                }
            }
        }
    }

    /**
     * {@link Builder} class for {@link RecyclerViewDivider}.
     * <br>
     * This class can set these custom properties:
     * <ul>
     * <li><b>Color:</b> {@link #color(int)}</li>
     * <li><b>Drawable:</b> {@link #drawable(Drawable)}</li>
     * <li><b>Tint of the drawable:</b> {@link #tint(int)}</li>
     * <li><b>Size:</b> {@link #size(int)}</li>
     * <li><b>Margins:</b> {@link #marginSize(int)}</li>
     * </ul>
     * <br>
     * And use these custom factories:
     * <ul>
     * <li><b>{@link VisibilityFactory}:</b> {@link #visibilityFactory(VisibilityFactory)}</li>
     * <li><b>{@link DrawableFactory}:</b> {@link #drawableFactory(DrawableFactory)}</li>
     * <li><b>{@link TintFactory}:</b> {@link #tintFactory(TintFactory)}</li>
     * <li><b>{@link SizeFactory}:</b> {@link #sizeFactory(SizeFactory)}</li>
     * <li><b>{@link MarginFactory}:</b> {@link #marginFactory(MarginFactory)}</li>
     * </ul>
     */
    public static class Builder {
        private static final int INT_DEF = -1;

        private WeakReference<Context> contextRef;
        private WeakReference<RecyclerView> recyclerViewRef;
        private int orientation;
        private int spanCount;

        @ColorInt
        private Integer color;
        private Drawable drawable;
        private Integer tint;
        private int size;
        private int marginSize;

        private VisibilityFactory visibilityFactory;
        private DrawableFactory drawableFactory;
        private TintFactory tintFactory;
        private SizeFactory sizeFactory;
        private MarginFactory marginFactory;

        @Type
        private int type;

        /**
         * Initialize this {@link Builder} with a context.
         * The Context object will be stored in a WeakReference to avoid memory leak
         *
         * @param context current context
         */
        @SuppressWarnings("WeakerAccess")
        public Builder(@NonNull Context context) {
            contextRef = new WeakReference<>(context);
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
         * Set the type of the divider as a space
         *
         * @return {@link Builder} instance
         */
        public Builder asSpace() {
            type = TYPE_SPACE;
            return this;
        }

        /**
         * Set the color of all dividers. This method can't be used with {@link #drawable(Drawable)} or {@link #tint(int)}
         * <br>
         * To set a custom color for each divider use {@link #drawableFactory(DrawableFactory)} instead
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
         * Set the drawable of all dividers. This method can't be used with {@link #color(int)}.
         * If you want to color the drawable, you have to use {@link #tint(int)} instead.
         * <br>
         * To set a custom drawable for each divider use {@link #drawableFactory(DrawableFactory)} instead
         *
         * @param drawable custom drawable for this divider
         * @return {@link Builder} instance
         */
        public Builder drawable(@NonNull Drawable drawable) {
            this.drawable = drawable;
            type = TYPE_DRAWABLE;
            return this;
        }

        /**
         * Set the tint color of all dividers' drawables.
         * If you want to create a plain divider with a single color, {@link #color(int)} is recommended.
         * <br>
         * To set a custom tint color for each divider's drawable use {@link #tintFactory(TintFactory)} instead
         *
         * @param color color that will be used as drawable's tint
         * @return {@link Builder} instance
         */
        public Builder tint(@ColorInt int color) {
            tint = color;
            return this;
        }

        /**
         * Set the size of all dividers. The divider's final size will depend on RecyclerView's orientation:
         * <ul>
         * <li><b>RecyclerView.VERTICAL:</b> the height will be equal to the size and the width will be equal to the sum of container's width and the margin size</li>
         * <li><b>RecyclerView.HORIZONTAL:</b> the width will be equal to the size and the height will be equal to the sum of container's height and the margin size</li>
         * </ul>
         * <br>
         * To set a custom size for each divider use {@link #sizeFactory(SizeFactory)} instead.
         *
         * @param size size in pixels for this divider
         * @return {@link Builder} instance
         */
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        /**
         * Set the margin size for all dividers. They will depend on RecyclerView's orientation:
         * <ul>
         * <li><b>RecyclerView.VERTICAL:</b> margins will be added equally to the left and to the right</li>
         * <li><b>RecyclerView.HORIZONTAL:</b> margins will be added equally to the top and to the bottom</li>
         * </ul>
         * <br>
         * To set a custom margin size for each divider use {@link #sizeFactory(SizeFactory)} instead.
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
         * Set the divider's custom {@link DrawableFactory}
         *
         * @param drawableFactory custom {@link DrawableFactory} to set
         * @return {@link Builder} instance
         */
        public Builder drawableFactory(@Nullable DrawableFactory drawableFactory) {
            this.drawableFactory = drawableFactory;
            return this;
        }

        /**
         * Set the divider's custom {@link TintFactory}
         *
         * @param tintFactory custom {@link TintFactory} to set
         * @return {@link Builder} instance
         */
        public Builder tintFactory(@Nullable TintFactory tintFactory) {
            this.tintFactory = tintFactory;
            return this;
        }

        /**
         * Set the divider's custom {@link SizeFactory}
         *
         * @param sizeFactory custom {@link SizeFactory} to set
         * @return {@link Builder} instance
         */
        public Builder sizeFactory(@Nullable SizeFactory sizeFactory) {
            this.sizeFactory = sizeFactory;
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

        /**
         * Creates a new {@link RecyclerViewDivider} with given configurations and initializes all values.
         * There are three common cases in the choice of factories:
         * <ul>
         * <li><b>Property not set</b>: the default factory will be used</li>
         * <li><b>Property set for all divider</b>: the general factory will be used</li>
         * <li><b>Property set differently for each divider</b>: the custom factory will be used</li>
         * </ul>
         *
         * @return a new {@link RecyclerViewDivider} with these {@link Builder} configurations
         */
        @SuppressLint("SwitchIntDef")
        public RecyclerViewDivider build() {
            // get the value of RecyclerView from the WeakReference
            RecyclerView recyclerView = recyclerViewRef.get();
            if (recyclerView != null) {
                // get RecyclerView's orientation
                orientation = RecyclerViewDividerUtils.getOrientation(recyclerView);
                spanCount = RecyclerViewDividerUtils.getSpanCount(recyclerView);
            }

            // get the value of Context from the WeakReference
            Context context = contextRef.get();
            if (context != null) {

                 /* -------------------- VISIBILITY FACTORY -------------------- */

                if (visibilityFactory == null) {
                    visibilityFactory = VisibilityFactory.getDefault();
                }

                 /* -------------------- SIZE FACTORY -------------------- */

                if (sizeFactory == null) {
                    if (size == INT_DEF) {
                        sizeFactory = SizeFactory.getDefault(context);
                    } else {
                        sizeFactory = SizeFactory.getGeneralFactory(size);
                    }
                }

                // init default values
                if (type != TYPE_SPACE) {

                    /* -------------------- DRAWABLE FACTORY -------------------- */

                    if (drawableFactory == null) {
                        Drawable currDrawable = null;
                        // all drawing properties will be set if RecyclerViewDivider is used as a divider, not as a space
                        switch (type) {
                            case TYPE_COLOR:
                                if (color != null) {
                                    currDrawable = RecyclerViewDividerUtils.colorToDrawable(color);
                                }
                                break;

                            case TYPE_DRAWABLE:
                                if (drawable != null) {
                                    if (spanCount > 1) {
                                        Log.e(TAG, "if your span count is major than 1, the drawable won't be shown correctly");
                                    }
                                    currDrawable = drawable;
                                }
                                break;
                        }
                        if (currDrawable == null) {
                            drawableFactory = DrawableFactory.getDefault(context);
                        } else {
                            drawableFactory = DrawableFactory.getGeneralFactory(currDrawable);
                        }
                    }

                    /* -------------------- TINT FACTORY -------------------- */

                    if (tintFactory == null) {
                        if (tint != null) {
                            tintFactory = TintFactory.getGeneralFactory(tint);
                        }
                    }

                    /* -------------------- MARGIN FACTORY -------------------- */

                    if (marginFactory == null) {
                        if (marginSize == INT_DEF) {
                            marginFactory = MarginFactory.getDefault(context);
                        } else {
                            marginFactory = MarginFactory.getGeneralFactory(marginSize);
                        }
                    }
                } else {

                    /* -------------------- DRAWABLE FACTORY -------------------- */

                    drawableFactory = DrawableFactory.getDefault(context);

                    // help GC to dealloc other values or bring them to default
                    tintFactory = null;
                    marginFactory = null;
                }
            }

            // creates divider for this mBuilder
            return new RecyclerViewDivider(this);
        }
    }

    /**
     * Source annotation used to define different dividers' types.
     * <ul>
     * <li><b>TYPE_SPACE</b>: divider used only as a space</li>
     * <li><b>TYPE_COLOR</b>: plain divider with one color</li>
     * <li><b>TYPE_DRAWABLE</b>: divider with a drawable resource</li>
     * </ul>
     */
    @IntDef({TYPE_SPACE, TYPE_COLOR, TYPE_DRAWABLE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Type {
        // empty annotation body
    }
}