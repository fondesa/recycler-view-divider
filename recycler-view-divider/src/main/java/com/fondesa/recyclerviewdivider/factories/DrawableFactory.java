/*
 * Copyright (c) 2017 Fondesa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fondesa.recyclerviewdivider.factories;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fondesa.recycler_view_divider.R;
import com.fondesa.recyclerviewdivider.RecyclerViewDividerUtils;

/**
 * Factory used to specify a custom logic to use different drawables as divider.
 * <br>
 * You can add a custom {@link DrawableFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#drawableFactory(DrawableFactory)} method
 */
public abstract class DrawableFactory {

    private static DrawableFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link DrawableFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    public static synchronized DrawableFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    /**
     * Creates a new {@link DrawableFactory} with equal drawable resource for all dividers
     *
     * @param drawable resource for all dividers
     * @return factory with same values for each divider
     */
    public static DrawableFactory getGeneralFactory(@NonNull Drawable drawable) {
        return new General(drawable);
    }

    /**
     * Defines a custom Drawable for each group of divider
     *
     * @param groupCount number of groups in a list.
     *                   The groupCount value is equal to the list size when the span count is 1 (e.g. LinearLayoutManager).
     * @param groupIndex position of the group. The value is between 0 and groupCount - 1.
     *                   The groupIndex is equal to the item position when the span count is 1 (e.g. LinearLayoutManager).
     * @return Drawable resource for the divider int the current position
     */
    public abstract Drawable drawableForItem(int groupCount, int groupIndex);

    /**
     * Default instance of a {@link DrawableFactory}
     */
    private static class Default extends DrawableFactory {
        private final Drawable defaultDrawable;

        Default(@NonNull Context context) {
            defaultDrawable = RecyclerViewDividerUtils.colorToDrawable(ContextCompat.getColor(context, R.color.recycler_view_divider_color));
        }

        @Override
        public Drawable drawableForItem(int groupCount, int groupIndex) {
            return defaultDrawable;
        }
    }

    /**
     * General instance of a {@link DrawableFactory} used when the drawable is set with {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#color(int)}
     * or with {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#drawable(Drawable)}
     */
    private static class General extends DrawableFactory {
        private final Drawable drawable;

        General(@NonNull Drawable drawable) {
            this.drawable = drawable;
        }

        @Override
        public Drawable drawableForItem(int groupCount, int groupIndex) {
            return drawable;
        }
    }
}