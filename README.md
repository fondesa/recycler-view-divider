RecyclerViewDivider
===============
[![Build Status](https://travis-ci.org/Fondesa/RecyclerViewDivider.svg?branch=master)](https://travis-ci.org/Fondesa/RecyclerViewDivider)

A RecyclerView's divider that can be customized with simple properties or advanced ones.

<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/art/screenshot_div_draw.png" height="500">   <img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/art/screenshot_div_simple.png" height="500">   <img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/art/screenshot_grid.png" height="500">

It supports completely LinearLayoutManager, GridLayoutManager and partially StaggeredGridLayoutManager.

Usage
------

If you want to use the basic version of this divider you can just add this line of code after your <i>setAdapter()</i> method:

```java
RecyclerViewDivider.with(context).build().addTo(recyclerView);
```
### Customization ###
All dividers in the app have default values in xml resources:
<ul>
<li><i>Color</i> → R.color.recycler_view_divider_color (default is #CFCFCF)</li>
<li><i>Size</i> → R.dimen.recycler_view_divider_size (default is 1dp)</li>
<li><i>Inset before</i> → R.dimen.recycler_view_divider_inset_before (default is 0dp)</li>
<li><i>Inset after</i> → R.dimen.recycler_view_divider_inset_after (default is 0dp)</li>
</ul>

It can be customized in code with properties equals for each divider:
<ul>
<li><i>color(int)</i> → change color</li>
<li><i>drawable(Drawable)</i> → change drawable</li>
<li><i>tint(int)</i> → change drawables' tint</li>
<li><i>size(int)</i> → change height for an horizontal divider, width for a vertical one</li>
<li><i>inset(int, int)</i> → change left/right inset for an horizontal divider, top/bottom for a vertical one</li>
</ul>

##### Example with all general properties set: #####

```java
RecyclerViewDivider.with(context)
                .color(color)
                // OR
                .drawable(drawable)
                .tint(tint)
                .size(size)
                .inset(insetBefore, insetAfter)
                .hideLastDivider()
                .build()
                .addTo(recyclerView);
```

It can also use custom managers to have a different logic for each divider:
<ul>
<li><i>drawableManager(DrawableManager)</i> → set color/drawable</li>
<li><i>insetManager(InsetManager)</i> → set inset</li>
<li><i>sizeManager(SizeManager)</i> → set size</li>
<li><i>tintManager(TintManager)</i> → set tint</li>
<li><i>visibilityManager(VisibilityManager)</i> → set visibility</li>
</ul>

##### Example with all managers set: #####

```java
RecyclerViewDivider.with(context)
                .drawableManager(new DrawableManager() {
                    @Override
                    public Drawable itemDrawable(int groupCount, int groupIndex) {
                        return position % 2 == 0 ? new ColorDrawable(Color.BLACK) : new ColorDrawable(Color.BLUE);
                    }
                })
                .insetManager(new InsetManager() {
                     @Override
                    public int itemInsetAfter(int groupCount, int groupIndex) {
                        return position % 2 == 0 ? 10 : 10 * 2;
                    }
        
                    @Override
                    public int itemInsetBefore(int groupCount, int groupIndex) {
                        return position % 2 == 0 ? 15 : 15 * 2;
                    }
                })
                .sizeManager(new SizeManager() {
                    @Override
                    public int itemSize(@Nullable Drawable drawable, int orientation, int groupCount, int groupIndex) {
                        return position % 2 == 0 ? 45 : 78;
                    }
                })
                .tintManager(new TintManager() {
                    @Override
                    public int itemTint(int groupCount, int groupIndex) {
                        return position == 0 ? Color.YELLOW : Color.GRAY;
                    }
                })
                .visibilityManager(new VisibilityManager() {
                    @Override
                    public int itemVisibility(int groupCount, int groupIndex) {
                        return groupCount % groupIndex == 0 ? SHOW_ITEMS_ONLY : SHOW_ALL;
                    }
                })
                .build()
                .addTo(recyclerView);
```

You can also use this divider as a simple space between RecyclerView's items:

##### Example of divider as space: #####

```java
RecyclerViewDivider.with(context).asSpace().build().addTo(recyclerView);
```

Compatibility
------

**Android SDK**: RecyclerViewDivider requires a minimum API level of 14 (the same of RecyclerView).

Integration
------

You can download a jar from GitHub's [releases page][2] or grab it from ```jcenter()``` or ```mavenCentral()```.

### Gradle ###

```gradle
dependencies {
    compile 'com.github.fondesa:recycler-view-divider:1.4.1'
}
```

### Maven ###

```xml
<dependency>
  <groupId>com.github.fondesa</groupId>
  <artifactId>recycler-view-divider</artifactId>
  <version>1.4.1</version>
  <type>pom</type>
</dependency>
```

[2]: https://github.com/Fondesa/RecyclerViewDivider/releases
