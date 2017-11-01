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

import android.support.annotation.ColorInt;

import com.fondesa.recyclerviewdivider.manager.tint.TintManager;

/**
 * Factory used to specify a custom logic to use different tint colors to tint divider's drawables.
 * <br>
 * You can add a custom {@link TintFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#tintFactory(TintFactory)} method
 */
@Deprecated
public abstract class TintFactory implements TintManager{

    /**
     * Creates a new {@link TintFactory} with equal tint color for all dividers's drawables
     *
     * @param tint tint color for dividers' drawables
     * @return factory with same values for each divider
     */
    @Deprecated
    public static TintFactory getGeneralFactory(@ColorInt final int tint) {
        return new TintFactory() {
            @Override
            public int tintForItem(int groupCount, int groupIndex) {
                return tint;
            }
        };
    }

    /**
     * Defines a custom tint color for each group of divider
     *
     * @param groupCount number of groups in a list.
     *                   The groupCount value is equal to the list size when the span count is 1 (e.g. LinearLayoutManager).
     * @param groupIndex position of the group. The value is between 0 and groupCount - 1.
     *                   The groupIndex is equal to the item position when the span count is 1 (e.g. LinearLayoutManager).
     * @return tint color for the divider's drawable in the current position
     */
    @Deprecated
    public abstract int tintForItem(int groupCount, int groupIndex);

    @Override
    public final int itemTint(int groupCount, int groupIndex) {
        return tintForItem(groupCount, groupIndex);
    }
}