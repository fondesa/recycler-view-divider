RecyclerViewDivider
===============

A simple divider for a RecyclerView used as an item decoration.

<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/screenshots/screenshot_1.png" height="500">
<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/screenshots/screenshot_2.png" height="500">
<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/screenshots/screenshot_3.png" height="500">

Usage
------

###Basic divider###

```java
RecyclerViewDivider.with(context).addTo(recyclerView).build().show();
```

###Colored divider with all properties###

```java
RecyclerViewDivider.with(context)
                .addTo(recyclerView)
                .color(color)
                .size(size)
                .marginSize(marginSize)
                .positionFactory(new PositionFactory() {
                    @Override
                    public boolean displayDividerForItem(int listSize, int position) {
                        // the divider will be not displayed after the last list's item
                        return position != listSize -1;
                    }
                })
                .build()
                .show();
```

###Custom drawable divider with all properties###

```java
RecyclerViewDivider.with(context)
                .addTo(recyclerView)
                .drawable(drawable)
                .tint(color)
                .size(size)
                .marginSize(marginSize)
                .positionFactory(new PositionFactory() {
                    @Override
                    public boolean displayDividerForItem(int listSize, int position) {
                        // the divider will be not displayed after the last list's item
                        return position != listSize -1;
                    }
                })
                .build()
                .show();
```

###Simple space###

```java
RecyclerViewDivider.with(context).addTo(recyclerView).asSpace().build().show();
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
    compile 'com.github.fondesa:recycler-view-divider:1.1.2'
}
```

###Maven###

```xml
<dependency>
  <groupId>com.github.fondesa</groupId>
  <artifactId>recycler-view-divider</artifactId>
  <version>1.1.2</version>
  <type>pom</type>
</dependency>
```