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

package com.fondesa.recyclerviewdivider.sample.grid

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.sample.R
import com.fondesa.recyclerviewdivider.sample.StringAdapter
import com.fondesa.recyclerviewdivider.sample.mapAsStrings

internal class GridSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val items = (1..15).mapAsStrings()

        /* ----------- VERTICAL ----------- */

        val verticalRecyclerView = findViewById<RecyclerView>(R.id.verticalRecyclerView)
        val verticalLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        verticalRecyclerView.layoutManager = verticalLayoutManager
        verticalRecyclerView.adapter = StringAdapter(isRecyclerViewVertical = true).apply { submitList(items) }

        dividerBuilder()
            .color(Color.RED)
            .size(1, TypedValue.COMPLEX_UNIT_DIP)
            .showFirstDivider()
            .showLastDivider()
            .showSideDividers()
            .build()
            .addTo(verticalRecyclerView)

        /* ----------- HORIZONTAL ----------- */

        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontalRecyclerView)
        val horizontalLayoutManager = GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false)
        horizontalRecyclerView.layoutManager = horizontalLayoutManager
        horizontalRecyclerView.adapter = StringAdapter(isRecyclerViewVertical = false).apply { submitList(items) }

        dividerBuilder()
            .color(Color.RED)
            .size(1, TypedValue.COMPLEX_UNIT_DIP)
            .showFirstDivider()
            .showLastDivider()
            .showSideDividers()
            .build()
            .addTo(horizontalRecyclerView)
    }
}
