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

import android.graphics.Rect
import org.junit.Assert.assertEquals

/**
 * Asserts the actual list of [Rect] is equal to the expected one.
 *
 * @param first the first expected [Rect].
 * @param others the others expected [Rect].
 */
internal fun List<Rect>.assertEquals(first: Rect, vararg others: Rect) {
    val expected = listOf(first) + others
    assertEquals(expected, this)
}
