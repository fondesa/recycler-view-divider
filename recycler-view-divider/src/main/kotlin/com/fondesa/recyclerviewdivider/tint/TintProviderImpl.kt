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

package com.fondesa.recyclerviewdivider.tint

import androidx.annotation.ColorInt
import androidx.annotation.VisibleForTesting
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid

/**
 * Implementation of [TintProvider] which provides the same tint color for each divider.
 *
 * @param dividerTintColor the tint color of each divider or null if the dividers shouldn't be tinted.
 */
internal class TintProviderImpl(@get:VisibleForTesting @ColorInt internal val dividerTintColor: Int?) : TintProvider {

    @ColorInt
    override fun getDividerTintColor(grid: Grid, divider: Divider): Int? = dividerTintColor
}
