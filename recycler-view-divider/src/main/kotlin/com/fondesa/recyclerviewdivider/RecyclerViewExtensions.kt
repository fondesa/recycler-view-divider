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

package com.fondesa.recyclerviewdivider

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Gets the adapter position of the given child [view].
 *
 * @return the adapter position or null if there isn't a position corresponding to the given [view].
 */
internal fun RecyclerView.getChildAdapterPositionOrNull(view: View, adapterItemCount: Int): Int? {
    val itemPosition = getChildAdapterPosition(view)
    return itemPosition.takeIf { it != RecyclerView.NO_POSITION && it < adapterItemCount }
}

/**
 * Executes the given [action] for each item in a [RecyclerView].
 *
 * @param action the action which should be executed for each item.
 */
internal inline fun RecyclerView.forEachItem(adapterItemCount: Int, action: (itemIndex: Int, itemView: View) -> Unit) {
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        // Don't execute the given action if the adapter position can't be retrieved.
        val itemPosition = getChildAdapterPositionOrNull(view, adapterItemCount) ?: continue
        action(itemPosition, view)
    }
}
