# RxMarkdown

[![License](http://img.shields.io/:license-apache-blue.svg)](LICENSE.txt) [![API](https://img.shields.io/badge/API-9%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=9)  [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxMarkdown-green.svg?style=true)](https://android-arsenal.com/details/1/3967)

RxMarkdown 是一个运用 RxJava API 在 `android.widget.TextView` 或 `android.widget.EditText` 中编辑和（实时）预览基本 markdown 语法的 Android 库，同时支持代码高亮。 

注：RxMarkdown 暂时不支持 HTML 标签。

Demo apk : [下载](https://github.com/yydcdut/RxMarkdown/blob/master/apk/demo.apk?raw=true)

二维码 : [传送门](http://fir.im/nh4c)

更新日志 : [传送门](./CHANGELOG.md)

![RxMarkdown.gif](http://7xs03u.com1.z0.glb.clouddn.com/rxmarkdown.gif)

# Gradle

```groovy
compile 'com.yydcdut:rxmarkdown:0.1.1-beta'
```

## 支持语法

RxMarkdown 目前提供两种解析 markdown 的解析方式， `TextFactory` 和 `EditFactory` 。

`TextFactory` : 支持大部分语法，但是会破坏内容的完整性，适用于解析后在 `TextView` 中渲染。

`EditFactory` : 支持部分语法，不会破坏内容的完整性且速度比 `TextFactory` 快，适用于在 `EditView` 中实时预览。

### TextFactory

- [x] 标题 `# ` / `## ` / `### ` / `#### ` / `##### ` / `####### `
- [x] 区域引用 `> `
- [x] 嵌套区域引用 `> > `
- [x] 粗体 `**` `__`
- [x] 斜体 `*` `_`
- [x] 粗体和斜体嵌套
- [x] 有序列表 `1. `
- [x] 嵌套有序列表 
- [x] 无序列表 `* ` /  `+ ` / `- `
- [x] 嵌套无序列表
- [x] 图片 `![]()`
- [x] 链接 `[]()`
- [x] 行内代码 
- [x] 代码区块 
- [x] 反斜杠 `\`
- [x] 分割线 `***` / `*****` / `---` / `-----------------`
- [x] 删除线 `~~`
- [x] 注脚 `[^]`
- [x] Todo `- [ ] ` / `- [x]`
- [ ] 表格 `| 表格 | 表格 |`
- [x] 代码高亮

#### 其他语法

- [x] 居中 `[ ]`


### EditFactory

- [x] 标题 `# ` / `## ` / `### ` / `#### ` / `##### ` / `####### `
- [x] 区域引用 `> `
- [x] 嵌套区域引用 `> > `
- [x] 粗体 `**` `__`
- [x] 斜体 `*` `_`
- [x] 粗体和斜体嵌套
- [x] 有序列表 `1. `
- [x] 嵌套有序列表 
- [x] 无序列表 `* ` /  `+ ` / `- `
- [x] 嵌套无序列表
- [ ] 图片 `![]()`
- [ ] 链接 `[]()`
- [x] 行内代码 
- [x] 代码区块 
- [ ] 反斜杠 `\`
- [x] 分割线 `***` / `*****` / `---` / `-----------------`
- [x] 删除线 `~~`
- [ ] 注脚 `[^]`
- [ ] Todo `- [ ] ` / `- [x]`
- [ ] 表格 `| 表格 | 表格 |`
- [ ] 代码高亮

#### 其他语法

- [x] 居中 `[ ]`

### HtmlFactory

//TODO

## 开始

### 引用

```groovy
compile 'com.yydcdut:rxmarkdown:0.1.1-beta'

compile 'io.reactivex:rxandroid:1.2.0'
compile 'io.reactivex:rxjava:1.1.5'
```

### 配置

`RxMDConfiguration` 的作用是告诉 RxMarkdown 如何展示 markdown 内容。

`RxMDConfiguration#Builder` 中的所有参数都是非必须的，只需要配置所需要的便可，没有配置的会设置上默认值。

```java
RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(context)
        .setDefaultImageSize(100, 100)//default image width & height
        .setBlockQuotesColor(Color.LTGRAY)//default color of block quotes
        .setHeader1RelativeSize(1.6f)//default relative size of header1
        .setHeader2RelativeSize(1.5f)//default relative size of header2
        .setHeader3RelativeSize(1.4f)//default relative size of header3
        .setHeader4RelativeSize(1.3f)//default relative size of header4
        .setHeader5RelativeSize(1.2f)//default relative size of header5
        .setHeader6RelativeSize(1.1f)//default relative size of header6
        .setHorizontalRulesColor(Color.LTGRAY)//default color of horizontal rules's background
        .setInlineCodeBgColor(Color.LTGRAY)//default color of inline code's background
        .setCodeBgColor(Color.LTGRAY)//default color of code's background
        .setTodoColor(Color.DKGRAY)//default color of todo
        .setTodoDoneColor(Color.DKGRAY)//default color of done
        .setUnOrderListColor(Color.BLACK)//default color of unorder list
        .setLinkColor(Color.RED)//default color of link text
        .setLinkUnderline(true)//default value of whether displays link underline
        .setRxMDImageLoader(new DefaultLoader(context))//default image loader
        .setDebug(true)//default value of debug
        .setOnLinkClickCallback(new OnLinkClickCallback() {//link click callback
        	@Override
        	public void onLinkClicked(View view, String link) {
        		Toast.makeText(view.getContext(), link, Toast.LENGTH_SHORT).show();
        	}
        })
        .build();
```

### 使用

* `EditText` 实时预览

  ```java
  RxMarkdown.live(rxMDEditText)
          .config(rxMDConfiguration)
          .factory(EditFactory.create())
          .intoObservable()
          .subscribe();
  ```

* 取消实时预览

  ```java
  rxMDEditText.clear();
  ```

* `TextView` 预览

  ```java
  RxMarkdown.with(content, this)
          .config(rxMDConfiguration)
          .factory(TextFactory.create())
          .intoObservable()
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<CharSequence>() {
              @Override
              public void onCompleted() {}

              @Override
              public void onError(Throwable e) {}

              @Override
              public void onNext(CharSequence charSequence) {
                  rxMDTextView.setText(charSequence, TextView.BufferType.SPANNABLE);
              }
          });
  ```

### 注意

#### RxMDImageLoader

* 支持的 URI 格式：

  ```c
  "http://web.com/image.png" // from Web
  "file:///mnt/sdcard/image.png" // from SD card
  "assets://image.png" // from assets
  "drawable://" + R.drawable.img // from drawables (non-9patch images)
  ```

* 自定义 imageLoader

  ```java
  public class MDLoader implements RxMDImageLoader {
      @Nullable
      @Override
      public byte[] loadSync(@NonNull String url) throws IOException {
          return new byte[0];
      }
  }
  ```

#### 图片尺寸

需要在屏幕上显示高度和宽度为 320 * 320 像素的图片：

```markdown
![image](http://web.com/image.png/320$320)
```

#### 代码高亮主题

该库提供了一些代码高亮的主题， `ThemeDefault`, `ThemeDesert`, `ThemeSonsOfObsidian` 和 `ThemeSunburst`。

|               ThemeDefault               |              ThemeDesert               |           ThemeSonsOfObsidian            |              ThemeSunburst               |
| :--------------------------------------: | :------------------------------------: | :--------------------------------------: | :--------------------------------------: |
| ![ThemeDefault](https://raw.githubusercontent.com/yydcdut/RxMarkdown/master/art/ThemeDefault.png) | ![ThemeDesert](https://raw.githubusercontent.com/yydcdut/RxMarkdown/master/art/ThemeDesert.png) | ![ThemeSonsOfObsidian](https://raw.githubusercontent.com/yydcdut/RxMarkdown/master/art/ThemeSonsOfObsidian.png) | ![ThemeSunburst](https://raw.githubusercontent.com/yydcdut/RxMarkdown/master/art/ThemeSunburst.png) |

同时也可以实现 `Theme` 接口来配置自己想要的主题。

```java
public class CodeHighLightTheme implements Theme {

    @Override
    public int getBackgroundColor() {//background color
        return 0xffcccccc;
    }

    @Override
    public int getTypeColor() {//color for type
        return 0xff660066;
    }

    @Override
    public int getKeyWordColor() {//color for keyword
        return 0xff000088;
    }

    @Override
    public int getLiteralColor() {//color for literal
        return 0xff006666;
    }

    @Override
    public int getCommentColor() {//color for comment
        return 0xff880000;
    }

    @Override
    public int getStringColor() {//color for string
        return 0xff008800;
    }

    @Override
    public int getPunctuationColor() {//color for punctuation
        return 0xff666600;
    }

    @Override
    public int getTagColor() {//color for html/xml tag
        return 0xff000088;
    }

    @Override
    public int getPlainTextColor() {//color for a plain text
        return 0xff000000;
    }

    @Override
    public int getDecimalColor() {//color for a markup declaration such as a DOCTYPE
        return 0xff000000;
    }

    @Override
    public int getAttributeNameColor() {//color for html/xml attribute name
        return 0xff660066;
    }

    @Override
    public int getAttributeValueColor() {//color for html/xml attribute value
        return 0xff008800;
    }

    @Override
    public int getOpnColor() {//color for opn
        return 0xff666600;
    }

    @Override
    public int getCloColor() {//color for clo
        return 0xff666600;
    }

    @Override
    public int getVarColor() {//color for var
        return 0xff660066;
    }

    @Override
    public int getFunColor() {//color for fun
        return Color.RED;
    }

    @Override
    public int getNocodeColor() {color for nocode
        return 0xff000000;
    }
}
```

# License

Copyright 2016 yydcdut

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.