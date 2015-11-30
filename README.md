# GridLayout 
![](https://img.shields.io/github/tag/qiugang/GridLayout.svg?label=JitPack)
修改自Dave Smith大神[custom-view-examples](https://github.com/devunwired/custom-view-examples/blob/master/app/src/main/java/com/example/customview/widget/BoxGridLayout.java)的例子，你可以用它可以轻松实现新浪微博图片区域的效果。
### Usage

```groovy
repositories {
        // ...
        maven { url "https://jitpack.io" }
 }
dependencies {
   compile 'com.github.qiugang:GridLayout:v0.1.0'
}
```

### Step 1

* 在gridlayout中添加childview

```xml
<me.originqiu.library.GridLayout
        android:id="@+id/grid_box"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:background="#fff"
        android:layout_marginRight="16dp"
        android:layout_marginLeft="16dp"
        app:numColumns="3"
        app:separatorWidth="2dp">

        <include layout="@layout/layout_image"/>

        <include layout="@layout/layout_image"/>

        <include layout="@layout/layout_image"/>

        <include layout="@layout/layout_image"/>

    </me.originqiu.library.GridLayout>
```

### Step 2

* 设置childcount ,取出childview设置数据

```java
mGridLayout.setChildCount(7);
//取出view
ArrayList<View> views = mGridLayout.getAllChildView();
        ImageView imageView = (ImageView) views.get(2);
imageView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
```
###License
    The MIT License (MIT)

        Copyright (c) 2015 OriginQiu

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.



