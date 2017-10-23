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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fondesa.recycler_view_divider.R;
import com.fondesa.recyclerviewdivider.manager.drawable.DrawableManager;

import org.jetbrains.annotations.NotNull;

/**
 * Factory used to specify a custom logic to use different drawables as divider.
 * <br>
 * You can add a custom {@link DrawableFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#drawableFactory(DrawableFactory)} method
 */
@Deprecated
public abstract class DrawableFactory implements DrawableManager {

    private static DrawableFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link DrawableFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    @Deprecated
    public static synchronized DrawableFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = getGeneralFactory(new ColorDrawable(ContextCompat.getColor(context, R.color.recycler_view_divider_color)));
        }
        return defaultFactory;
    }

    /**
     * Creates a new {@link DrawableFactory} with equal drawable resource for all dividers
     *
     * @param drawable resource for all dividers
     * @return factory with same values for each divider
     */
    @Deprecated
    public static DrawableFactory getGeneralFactory(@NonNull final Drawable drawable) {
        return new DrawableFactory() {
            @Override
            public Drawable drawableForItem(int groupCount, int groupIndex) {
                return drawable;
            }
        };
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
    @Deprecated
    public abstract Drawable drawableForItem(int groupCount, int groupIndex);

    @NotNull
    @Override
    public final Drawable itemDrawable(int groupCount, int groupIndex) {
        return drawableForItem(groupCount, groupIndex);
    }
}