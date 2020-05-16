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

package com.fondesa.recyclerviewdivider.test

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.Orientation

/**
 * @return a [LinearLayoutManager] with the given params.
 */
internal fun linearLayoutManager(
    orientation: Orientation = Orientation.VERTICAL,
    reverseLayout: Boolean = false
): LinearLayoutManager = LinearLayoutManager(context, orientation.recyclerViewOrientation, reverseLayout)

/**
 * @return a [GridLayoutManager] with the given params.
 */
internal fun gridLayoutManager(
    spanCount: Int = 1,
    orientation: Orientation = Orientation.VERTICAL,
    reverseLayout: Boolean = false
): GridLayoutManager = GridLayoutManager(context, spanCount, orientation.recyclerViewOrientation, reverseLayout)

/**
 * @return a [StaggeredGridLayoutManager] with the given params.
 */
internal fun staggeredLayoutManager(
    spanCount: Int = 1,
    orientation: Orientation = Orientation.VERTICAL,
    reverseLayout: Boolean = false
): StaggeredGridLayoutManager = StaggeredGridLayoutManager(spanCount, orientation.recyclerViewOrientation).apply {
    this.reverseLayout = reverseLayout
}

/**
 * @return a custom [RecyclerView.LayoutManager] to use in tests.
 */
internal fun customLayoutManager(): RecyclerView.LayoutManager = DummyCustomLayoutManager()

private class DummyCustomLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = throw NotImplementedError()
}
