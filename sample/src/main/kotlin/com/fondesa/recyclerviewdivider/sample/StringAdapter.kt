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

package com.fondesa.recyclerviewdivider.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

internal class StringAdapter(
    private val isRecyclerViewVertical: Boolean,
    private val fullSpanPositions: List<Int> = emptyList()
) : ListAdapter<String, StringAdapter.ViewHolder>(StringDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent, isRecyclerViewVertical)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position in fullSpanPositions)
    }

    private object StringDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem.hashCode() == newItem.hashCode()
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, isFullSpan: Boolean) {
            (itemView.layoutParams as? StaggeredGridLayoutManager.LayoutParams)?.isFullSpan = isFullSpan
            (itemView as TextView).text = item
        }

        companion object {
            operator fun invoke(parent: ViewGroup, isRecyclerViewVertical: Boolean): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                @LayoutRes val layoutRes = if (isRecyclerViewVertical) {
                    R.layout.cell_vertical_recycler
                } else {
                    R.layout.cell_horizontal_recycler
                }
                val inflatedView = inflater.inflate(layoutRes, parent, false)
                return ViewHolder(inflatedView)
            }
        }
    }
}
