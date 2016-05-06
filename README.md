RecyclerViewDivider
===============

A RecyclerView's divider that can be customized with simple properties or advanced ones.

<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/screenshots/screenshot_1.png" height="500">
<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/screenshots/screenshot_2.png" height="500">
<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/screenshots/screenshot_3.png" height="500">

Usage
------

If you want to use the basic version of this divider you can just add this line of code after your <i>setAdapter()</i> method:

```java
RecyclerViewDivider.with(context).addTo(recyclerView).build().attach();
```
###Customization###
All dividers in the app have default values in xml resources:
<ul>
<li><i>Color</i> → R.color.recycler_view_divider_color (default is #CFCFCF)</li>
<li><i>Size</i> → R.dimen.recycler_view_divider_size (default is 1dp)</li>
<li><i>Margin size</i> → R.dimen.recycler_view_divider_margin_size (default is 0dp)</li>
</ul>

It can be customized in code with properties equals for each divider:
<ul>
<li><i>color(int)</i> → change color</li>
<li><i>drawable(Drawable)</i> → change drawable</li>
<li><i>tint(int)</i> → change drawables' tint</li>
<li><i>size(int)</i> → change height for an horizontal divider, width for a vertical one</li>
<li><i>marginSize(int)</i> → change left/right margin for an horizontal divider, top/bottom for a vertical one</li>
</ul>

#####Example with all general properties set:#####

```java
RecyclerViewDivider.with(context)
                .addTo(recyclerView)
                .color(color)
                // OR
                .drawable(drawable)
                .tint(tint)
                .size(size)
                .marginSize(marginSize)
                .build()
                .attach();
```

It can also use custom factories to have a different logic for each divider:
<ul>
<li><i>visibilityFactory(VisibilityFactory)</i> → set visibility</li>
<li><i>drawableFactory(DrawableFactory)</i> → set color/drawable</li>
<li><i>tintFactory(TintFactory)</i> → set tint</li>
<li><i>sizeFactory(SizeFactory)</i> → set size</li>
<li><i>marginFactory(MarginFactory)</i> → set margin</li>
</ul>

#####Example will all factories set:#####

```java
RecyclerViewDivider.with(context)
                .addTo(recyclerView)
                .visibilityFactory(new VisibilityFactory() {
                    @Override
                    public boolean displayDividerForItem(int listSize, int position) {
                        return position != 1;
                    }
                })
                .drawableFactory(new DrawableFactory() {
                    @Override
                    public Drawable drawableForItem(int listSize, int position) {
                        return position % 2 == 0 ? new ColorDrawable(Color.BLACK) : new ColorDrawable(Color.BLUE);
                    }
                })
                .tintFactory(new TintFactory() {
                    @Override
                    public int tintForItem(int listSize, int position) {
                        return position == 0 ? Color.YELLOW : Color.GRAY;
                    }
                })
                .sizeFactory(new SizeFactory() {
                    @Override
                    public int sizeForItem(@Nullable Drawable drawable, int orientation, int listSize, int position) {
                        return position % 2 == 0 ? 45 : 78;
                    }
                })
                .marginFactory(new MarginFactory() {
                    @Override
                    public int marginSizeForItem(int listSize, int position) {
                        return position % 2 == 0 ? 10 : 10 * 2;
                    }
                })
                .build()
                .attach();
```

You can also use this divider as a simple space between RecyclerView's items:

#####Example of divider as space:#####

```java
RecyclerViewDivider.with(context).addTo(recyclerView).asSpace().build().attach();
```

Compatibility
------

**Android SDK**: RecyclerViewDivider requires a minimum API level of 7

Integration
------

Available both on ```jcenter()``` and ```mavenCentral```

###Gradle###

```gradle
dependencies {
    compile 'com.github.fondesa:recycler-view-divider:1.1.3'
}
```

###Maven###

```xml
<dependency>
  <groupId>com.github.fondesa</groupId>
  <artifactId>recycler-view-divider</artifactId>
  <version>1.1.3</version>
  <type>pom</type>
</dependency>
```
