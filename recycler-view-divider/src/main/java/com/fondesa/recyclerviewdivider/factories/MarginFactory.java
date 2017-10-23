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
import android.support.annotation.NonNull;

import com.fondesa.recycler_view_divider.R;
import com.fondesa.recyclerviewdivider.manager.inset.InsetManager;

/**
 * Factory used to specify a custom logic to set different margins to the divider.
 * <br>
 * Custom margins will be set equally right/left with horizontal divider and top/bottom with vertical divider.
 * <br>
 * You can add a custom {@link MarginFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#marginFactory(MarginFactory)} method
 */
@Deprecated
public abstract class MarginFactory implements InsetManager {

    private static MarginFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link MarginFactory} to avoid multiple instance of the same class
     *
     * @param context current context
     * @return factory with default values
     */
    @Deprecated
    public static synchronized MarginFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = getGeneralFactory(context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_margin_size));
        }
        return defaultFactory;
    }

    /**
     * Creates a new {@link MarginFactory} with equal margin size for all dividers
     *
     * @param marginSize margins' size of the dividers
     * @return factory with same values for each divider
     */
    @Deprecated
    public static MarginFactory getGeneralFactory(final int marginSize) {
        return new MarginFactory() {
            @Override
            public int marginSizeForItem(int groupCount, int groupIndex) {
                return marginSize;
            }
        };
    }

    /**
     * Defines a custom margin size for each group of divider
     *
     * @param groupCount number of groups in a list.
     *                   The groupCount value is equal to the list size when the span count is 1 (e.g. LinearLayoutManager).
     * @param groupIndex position of the group. The value is between 0 and groupCount - 1.
     *                   The groupIndex is equal to the item position when the span count is 1 (e.g. LinearLayoutManager).
     * @return right/left margin with horizontal divider or top/bottom margin with vertical divider
     */
    @Deprecated
    public abstract int marginSizeForItem(int groupCount, int groupIndex);

    @Override
    public final int itemInsetBefore(int groupCount, int groupIndex) {
        return marginSizeForItem(groupCount, groupIndex);
    }

    @Override
    public final int itemInsetAfter(int groupCount, int groupIndex) {
        return marginSizeForItem(groupCount, groupIndex);
    }
}