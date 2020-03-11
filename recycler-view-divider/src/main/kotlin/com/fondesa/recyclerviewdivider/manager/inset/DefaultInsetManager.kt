/*
 * Copyright (c) 2020 Fondesa
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

package com.fondesa.recyclerviewdivider.manager.inset

import androidx.annotation.Px

/**
 * Default implementation of [InsetManager] that will use the same inset for each item.
 *
 * @param insetBefore the inset that will be applied before each item.
 * @param insetAfter the inset that will be applied after each item.
 */
class DefaultInsetManager(
    @Px private val insetBefore: Int,
    @Px private val insetAfter: Int
) : FixedInsetManager() {

    /**
     * Constructor that assigns a default inset equal to 0.
     */
    constructor() : this(0, 0)

    @Px
    override fun itemInsetBefore(): Int = insetBefore

    @Px
    override fun itemInsetAfter(): Int = insetAfter
}
