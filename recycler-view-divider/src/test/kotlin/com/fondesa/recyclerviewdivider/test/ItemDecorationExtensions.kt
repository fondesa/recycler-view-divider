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

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doCallRealMethod
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever

/**
 * Invokes the method [getItemOffsets] creating a test [RecyclerView].
 *
 * @param layoutManager the [RecyclerView.LayoutManager] attached to the [RecyclerView].
 * @param isRTL if the [RecyclerView] should be right-to-left.
 * @param views the [RecyclerView]'s cells.
 */
internal fun RecyclerView.ItemDecoration.getItemOffsets(
    layoutManager: RecyclerView.LayoutManager,
    isRTL: Boolean,
    vararg views: View
): List<Rect> {
    val recyclerView = recyclerView(layoutManager, isRTL, views)
    return views.mapIndexed { index, view ->
        doReturn(index).whenever(recyclerView).getChildAdapterPosition(view)
        val rect = Rect()
        getItemOffsets(rect, view, recyclerView, RecyclerView.State())
        rect
    }
}

/**
 * Invokes the method [onDraw] creating a test [RecyclerView].
 *
 * @param layoutManager the [RecyclerView.LayoutManager] attached to the [RecyclerView].
 * @param isRTL if the [RecyclerView] should be right-to-left.
 * @param views the [RecyclerView]'s cells.
 */
internal fun RecyclerView.ItemDecoration.onDraw(
    layoutManager: RecyclerView.LayoutManager,
    isRTL: Boolean,
    vararg views: View
): List<Rect> {
    val recyclerView = recyclerView(layoutManager, isRTL, views)
    val leftCaptor = argumentCaptor<Float>()
    val topCaptor = argumentCaptor<Float>()
    val rightCaptor = argumentCaptor<Float>()
    val bottomCaptor = argumentCaptor<Float>()
    val canvas = spy(Canvas()) {
        doCallRealMethod().whenever(it)
            .drawRect(leftCaptor.capture(), topCaptor.capture(), rightCaptor.capture(), bottomCaptor.capture(), any())
    }
    onDraw(canvas, recyclerView, RecyclerView.State())
    // Since the argument captors are invoked in the same method, their size is equal.
    return (leftCaptor.allValues.indices).map { index ->
        Rect(
            leftCaptor.allValues[index].toInt(),
            topCaptor.allValues[index].toInt(),
            rightCaptor.allValues[index].toInt(),
            bottomCaptor.allValues[index].toInt()
        )
    }
}

private fun recyclerView(
    layoutManager: RecyclerView.LayoutManager,
    isRTL: Boolean,
    views: Array<out View>
): RecyclerView {
    val recyclerView = RecyclerView(context).let {
        if (isRTL) it.rtl() else spy(it)
    }
    doReturn(views.size).whenever(recyclerView).childCount
    recyclerView.layoutManager = layoutManager
    val adapter = mock<RecyclerView.Adapter<*>> {
        on(it.itemCount) doReturn views.size
    }
    recyclerView.adapter = adapter
    views.forEachIndexed { index, view ->
        doReturn(view).whenever(recyclerView).getChildAt(index)
        doReturn(index).whenever(recyclerView).getChildAdapterPosition(view)
    }
    return recyclerView
}
