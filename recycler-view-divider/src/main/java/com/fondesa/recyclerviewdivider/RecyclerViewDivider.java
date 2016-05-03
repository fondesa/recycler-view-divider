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

    private Builder builder;

    private boolean isDividerAdded;

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
            if (!isDividerAdded) {
                recyclerView.addItemDecoration(this);
                isDividerAdded = true;
            } else {
                recyclerView.invalidateItemDecorations();
            }
        }
    }

    /**
     * Remove this divider from the RecyclerView
     */
    public void remove() {
        // get the value of RecyclerView from the WeakReference
        RecyclerView recyclerView = builder.recyclerViewRef.get();
        if (recyclerView != null) {
            if (isDividerAdded) {
                recyclerView.removeItemDecoration(this);
                isDividerAdded = false;
            }
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
                        int position = parent.getChildAdapterPosition(child);
                        final int orientation = builder.orientation;
                        final int margin = builder.marginFactory.marginSizeForItem(listSize, position);
                        Drawable divider = builder.drawableFactory.drawableForItem(listSize, position);
                        final int size = builder.sizeFactory.sizeForItem(divider, orientation, listSize, position);
                        TintFactory tintFactory = builder.tintFactory;
                        if (tintFactory != null) {
                            final int tint = tintFactory.tintForItem(listSize, position);
                            Drawable wrappedDrawable = DrawableCompat.wrap(divider);
                            DrawableCompat.setTint(wrappedDrawable, tint);
                            divider = wrappedDrawable;
                        }

                        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                        if (orientation == RecyclerView.VERTICAL) {
                            top = child.getBottom() + params.bottomMargin;
                            bottom = top + size;
                            left = parent.getPaddingLeft() + margin;
                            right = parent.getWidth() - parent.getPaddingRight() - margin;
                        } else {
                            top = parent.getPaddingTop() + margin;
                            bottom = parent.getHeight() - parent.getPaddingBottom() - margin;
                            left = child.getRight() + params.rightMargin;
                            right = left + size;
                        }

                        divider.setBounds(left, top, right, bottom);
                        divider.draw(c);
                    }
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int listSize = parent.getAdapter().getItemCount();
        if (listSize > 0) {
            final int orientation = builder.orientation;
            final int position = parent.getChildAdapterPosition(view);
            if (builder.visibilityFactory.displayDividerForItem(listSize, position)) {
                final Drawable divider = builder.drawableFactory.drawableForItem(listSize, position);
                final int size = builder.sizeFactory.sizeForItem(divider, orientation, listSize, position);

                if (orientation == RecyclerView.VERTICAL) {
                    outRect.set(0, 0, 0, size);
                } else {
                    outRect.set(0, 0, size, 0);
                }
            }
        }
    }

    /**
     * {@link Builder} class for {@link RecyclerViewDivider}.
     * <br>
     * This class can set these custom properties:
     * <ul>
     * <li><b>Size:</b> {@link #size(int)}</li>
     * <li><b>Margins:</b> {@link #marginSize(int)}</li>
     * <li><b>Color:</b> {@link #color(int)}</li>
     * <li><b>Drawable:</b> {@link #drawable(Drawable)}</li>
     * <li><b>Tint of the drawable:</b> {@link #tint(int)}</li>
     * </ul>
     * <br>
     * And use these custom factories:
     * <ul>
     * <li><b>{@link VisibilityFactory}:</b> {@link #visibilityFactory(VisibilityFactory)}</li>
     * <li><b>{@link DrawableFactory}:</b> {@link #drawableFactory(DrawableFactory)}</li>
     * <li><b>{@link TintFactory}:</b> {@link #tintFactory(TintFactory)}</li>
     * <li><b>{@link SizeFactory}:</b> {@link #sizeFactory(SizeFactory)}</li>
     * <li><b>{@link MarginFactory}:</b> {@link #marginFactory}</li>
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
        private int tint;
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
                orientation = RecyclerViewDividerUtils.getRecyclerViewOrientation(recyclerView);
            }

            /* -------------------- VISIBILITY FACTORY -------------------- */

            if (visibilityFactory == null) {
                visibilityFactory = VisibilityFactory.getDefault();
            }

            // init default values
            if (type != TYPE_SPACE) {
                // get the value of Context from the WeakReference
                Context context = contextRef.get();
                if (context != null) {

                    /* -------------------- DRAWABLE FACTORY -------------------- */

                    if (drawableFactory == null) {
                        Drawable currDrawable = null;
                        // all drawing properties will be set if RecyclerViewDivider is used as a divider, not as a space
                        switch (type) {
                            case TYPE_COLOR:
                                if (color != INT_DEF) {
                                    currDrawable = RecyclerViewDividerUtils.colorToDrawable(color);
                                }
                                break;

                            case TYPE_DRAWABLE:
                                if (drawable != null) {
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
                        if (tint != INT_DEF) {
                            tintFactory = TintFactory.getGeneralFactory(tint);
                        }
                    }

                    /* -------------------- SIZE FACTORY -------------------- */

                    if (sizeFactory == null) {
                        if (size == INT_DEF) {
                            sizeFactory = SizeFactory.getDefault(context);
                        } else {
                            sizeFactory = SizeFactory.getGeneralFactory(size);
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
                }
            } else {
                // help GC to dealloc other values or bring them to default
                if (drawableFactory != null) {
                    drawableFactory = null;
                }

                if (tintFactory != null) {
                    tintFactory = null;
                }

                if (marginFactory != null) {
                    marginFactory = null;
                }
            }

            // creates divider for this builder
            return new RecyclerViewDivider(this);
        }
    }

    private static final int TYPE_SPACE = -1;
    private static final int TYPE_COLOR = 0;
    private static final int TYPE_DRAWABLE = 1;

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
    }
}