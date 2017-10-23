/*
 * Copyright (c) 2017 Fondesa
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

package com.fondesa.recyclerviewdivider.manager.visibility

import android.support.annotation.IntDef

/**
 * Created by antoniolig on 23/10/17.
 */
interface VisibilityManager {
    companion object {
        const val SHOW_NONE = 0L
        const val SHOW_ITEMS_ONLY = 1L
        const val SHOW_GROUP_ONLY = 2L
        const val SHOW_ALL = 3L
    }

    @IntDef(SHOW_NONE, SHOW_ITEMS_ONLY, SHOW_GROUP_ONLY, SHOW_ALL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Show

    @Show
    fun itemVisibility(groupCount: Int, groupIndex: Int): Long
}