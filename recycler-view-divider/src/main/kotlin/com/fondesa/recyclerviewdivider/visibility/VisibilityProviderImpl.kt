/*
 * Copyright (c) 2020 Giorgio Antonioli
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

package com.fondesa.recyclerviewdivider.visibility

import androidx.annotation.VisibleForTesting
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Implementation of [VisibilityProvider] which shows all the dividers in the middle and controls the visibility of the dividers on the
 * sides with [isFirstDividerVisible], [isLastDividerVisible] and [areSideDividersVisible].
 *
 * @param isFirstDividerVisible true if the first divider of the grid should be shown, false otherwise.
 * @param isLastDividerVisible true if the last divider of the grid should be shown, false otherwise.
 * @param areSideDividersVisible true if the sides dividers of the grid should be shown, false otherwise.
 */
internal class VisibilityProviderImpl(
    @get:VisibleForTesting internal val isFirstDividerVisible: Boolean,
    @get:VisibleForTesting internal val isLastDividerVisible: Boolean,
    @get:VisibleForTesting internal val areSideDividersVisible: Boolean
) : VisibilityProvider {

    override fun isDividerVisible(grid: Grid, divider: Divider): Boolean = when {
        divider.isFirstDivider -> isFirstDividerVisible
        divider.isLastDivider -> isLastDividerVisible
        divider.isSideDivider -> areSideDividersVisible
        else -> true
    }
}
