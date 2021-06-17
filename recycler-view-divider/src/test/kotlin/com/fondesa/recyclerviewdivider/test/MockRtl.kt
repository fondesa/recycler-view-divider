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

import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy

/**
 * Mocks the [View] layout direction.
 * Using Robolectric, the RTL APIs are no-op.
 * https://github.com/robolectric/robolectric/issues/3910
 *
 * @return the [View] with the mocked layout direction.
 */
internal inline fun <reified T : View> T.rtl(): T = spy(this) {
    if (Build.VERSION.SDK_INT >= 17) {
        on(it.layoutDirection) doReturn ViewCompat.LAYOUT_DIRECTION_RTL
    }
}
