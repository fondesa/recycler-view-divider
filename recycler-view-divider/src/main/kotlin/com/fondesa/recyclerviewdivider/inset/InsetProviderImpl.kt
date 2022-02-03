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

package com.fondesa.recyclerviewdivider.inset

import androidx.annotation.Px
import androidx.annotation.VisibleForTesting
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Implementation of [InsetProvider] which provides the same insets for each divider.
 *
 * @param dividerInsetStart the start inset of each divider.
 * @param dividerInsetEnd the end inset of each divider.
 */
internal class InsetProviderImpl(
    @get:VisibleForTesting @Px internal val dividerInsetStart: Int,
    @get:VisibleForTesting @Px internal val dividerInsetEnd: Int
) : InsetProvider {

    @Px
    override fun getDividerInsetStart(grid: Grid, divider: Divider): Int = dividerInsetStart

    @Px
    override fun getDividerInsetEnd(grid: Grid, divider: Divider): Int = dividerInsetEnd
}
