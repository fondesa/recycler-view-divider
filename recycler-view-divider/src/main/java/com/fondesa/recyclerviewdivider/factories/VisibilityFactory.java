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

import android.support.annotation.IntDef;

import com.fondesa.recyclerviewdivider.manager.visibility.VisibilityManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Factory used to specify a custom logic to set visibility for each divider.
 * <br>
 * You can add a custom {@link VisibilityFactory} in your {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder} using
 * {@link com.fondesa.recyclerviewdivider.RecyclerViewDivider.Builder#visibilityFactory(VisibilityFactory)} method
 */
@Deprecated
@SuppressWarnings("WeakerAccess")
public abstract class VisibilityFactory implements VisibilityManager {

    @Deprecated
    public static final int SHOW_NONE = (int) VisibilityManager.SHOW_NONE;
    @Deprecated
    public static final int SHOW_ITEMS_ONLY = (int) VisibilityManager.SHOW_ITEMS_ONLY;
    @Deprecated
    public static final int SHOW_GROUP_ONLY = (int) VisibilityManager.SHOW_GROUP_ONLY;
    @Deprecated
    public static final int SHOW_ALL = (int) VisibilityManager.SHOW_ALL;

    /**
     * Source annotation used to define different visibility types.
     * <ul>
     * <li>{@link #SHOW_NONE}: every divider won't be shown</li>
     * <li>{@link #SHOW_ITEMS_ONLY}: divider will be shown only between items.
     * When the spanCount is equal to 1 (e.g. LinearLayoutManager), this property has the same effect of {@link #SHOW_NONE}</li>
     * <li>{@link #SHOW_GROUP_ONLY}: divider will be shown only between groups.
     * When the spanCount is equal to 1 (e.g. LinearLayoutManager), this property has the same effect of {@link #SHOW_ALL}</li>
     * <li>{@link #SHOW_ALL}: every divider will be shown</li>
     * </ul>
     */
    @IntDef({SHOW_NONE, SHOW_ITEMS_ONLY, SHOW_GROUP_ONLY, SHOW_ALL})
    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface Show {
        // empty annotation body
    }

    private static VisibilityFactory defaultFactory;

    /**
     * Creates a singleton instance of a default {@link VisibilityFactory} to avoid multiple instance of the same class
     *
     * @return factory with default values
     */
    @Deprecated
    public static synchronized VisibilityFactory getDefault() {
        if (defaultFactory == null) {
            defaultFactory = new VisibilityFactory() {
                @Override
                public int displayDividerForItem(int groupCount, int groupIndex) {
                    return SHOW_ALL;
                }
            };
        }
        return defaultFactory;
    }

    /**
     * @return a new {@link VisibilityFactory} that will now show last divider.
     */
    @Deprecated
    public static VisibilityFactory getLastItemInvisibleFactory() {
        return new VisibilityFactory() {
            @Override
            public int displayDividerForItem(int groupCount, int groupIndex) {
                return groupIndex == groupCount - 1 ? SHOW_ITEMS_ONLY : SHOW_ALL;
            }
        };
    }


    /**
     * Defines a visibility for each group of divider
     *
     * @param groupCount number of groups in a list.
     *                   The groupCount value is equal to the list size when the span count is 1 (e.g. LinearLayoutManager).
     * @param groupIndex position of the group. The value is between 0 and groupCount - 1.
     *                   The groupIndex is equal to the item position when the span count is 1 (e.g. LinearLayoutManager).
     * @return true if the divider will be visible, false instead
     */
    @Deprecated
    public abstract
    @Show
    int displayDividerForItem(int groupCount, int groupIndex);

    @Override
    public final long itemVisibility(int groupCount, int groupIndex) {
        return displayDividerForItem(groupIndex, groupIndex);
    }
}