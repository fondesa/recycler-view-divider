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

package com.fondesa.recyclerviewdivider.sample.staggered

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fondesa.recyclerviewdivider.sample.R
import com.fondesa.recyclerviewdivider.sample.StringAdapter
import com.fondesa.recyclerviewdivider.sample.mapAsStrings
import com.fondesa.recyclerviewdivider.staggeredDividerBuilder

class StaggeredSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        /* ----------- VERTICAL ----------- */

        val verticalItems = (1..15).mapAsStrings().mapIndexed { index, s ->
            if (index % 4 == 0) s.expandVertically() else s
        }
        val verticalRecyclerView = findViewById<RecyclerView>(R.id.verticalRecyclerView)
        val verticalLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        verticalRecyclerView.layoutManager = verticalLayoutManager
        verticalRecyclerView.adapter = StringAdapter(
            isRecyclerViewVertical = true,
            fullSpanPositions = listOf(3)
        ).apply { submitList(verticalItems) }

        staggeredDividerBuilder()
            .color(Color.RED)
            .size(1, TypedValue.COMPLEX_UNIT_DIP)
            .build()
            .addTo(verticalRecyclerView)

        /* ----------- HORIZONTAL ----------- */

        val horizontalItems = (1..15).mapAsStrings().mapIndexed { index, s ->
            if (index % 4 == 0) s.expandHorizontally() else s
        }
        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontalRecyclerView)
        val horizontalLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL)
        horizontalRecyclerView.layoutManager = horizontalLayoutManager
        horizontalRecyclerView.adapter = StringAdapter(
            isRecyclerViewVertical = false,
            fullSpanPositions = listOf(3)
        ).apply { submitList(horizontalItems) }

        staggeredDividerBuilder()
            .color(Color.RED)
            .size(1, TypedValue.COMPLEX_UNIT_DIP)
            .build()
            .addTo(horizontalRecyclerView)
    }

    private fun String.expandVertically(): String = "$this\n\n\n\n\n\n\n"

    private fun String.expandHorizontally(): String = "$this\t\t\t\t\t\t\t"
}
