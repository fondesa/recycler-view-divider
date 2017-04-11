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

/**
 * Plugin used to have a common configuration between Android modules.
 * This plugin use the constants defined in {@code android-config.properties} file.
 * The default behavior can be extended through the {@code config} closure.
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class AndroidSharedPlugin extends ConfiguredProjectPlugin {
    public static final int LIBRARY = 1
    public static final int APP = 2

    private int type
    private Closure config

    @Override
    void onProjectConfigured() {
        // Load Android properties.
        def androidProps = loadProps("android-config")

        // Apply plugin type.
        if (type == LIBRARY) {
            applyPlugin('com.android.library')
        } else if (type == APP) {
            applyPlugin('com.android.application')
        }

        // Add Android extension.
        project.android {
            compileSdkVersion prop(androidProps, "COMPILE_SDK").toInteger()
            buildToolsVersion prop(androidProps, "BUILD_TOOLS")

            defaultConfig {
                minSdkVersion prop(androidProps, "MIN_SDK").toInteger()
                targetSdkVersion prop(androidProps, "TARGET_SDK").toInteger()
            }
            if (config != null) {
                // Set the delegate to the Android extension.
                config.delegate = project.android
                // Add the additional behavior.
                config.call()
            }
        }
    }
}