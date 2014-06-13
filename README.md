# Garaponoid
(work in progress) An Android client for Garapon TV.
Simply search programs by keyword and show them in cards style as follows:

![](http://drive.google.com/uc?export=view&id=0B13qn3aDmWCOSnBIZDVuTm01YVk)

# Build
Install a tweaked version (1.2.16-bypasspc) of android-sdk-plugin as follows:
```
$ git clone git@github.com:ikuo/android-sdk-plugin.git
$ cd android-sdk-plugin
$ git co bypasspc
$ sbt
$ sbt publish-local
```

Create a file `./res/values/key.xml` with the following content:

```xml
<resources>
  <string name="gtv_dev_id">my_developer_id</string>
</resources>
```

Replace `my_developer_id` with your developer ID of Garapon TV API.

Make a package and start the app by sbt:

```
$ sbt
> android:package
> run
```

# Coding Style
Using [Scala Style Guide](http://docs.scala-lang.org/style/)
and the followings:

## Method declaration
When many arguments, break lines for each end of argument declaration:

```scala
def method1(
 argument1: String,
 argument2: String,
 ...
): Unit = {
  ...
}
```

# License
Apache License 2.0

# Credits
Garaponoid is powered by the following works:

- [Garapon4S](https://github.com/ikuo/garapon4s) - Apache License 2.0
- [Scaloid](https://github.com/pocorall/scaloid) - Apache License 2.0
- [Cards Library](https://github.com/gabrielemariotti/cardslib) - Apache License 2.0
- [Android flow layout](https://github.com/ApmeM/android-flowlayout) - Apache License 2.0
- [sbt android-sdk-plugin](https://github.com/pfn/android-sdk-plugin) (build time)

The logo (TV_icon.svg and tv.png) is derived from:

- [A work of Justin Ternet](http://openclipart.org/detail/182928/tv-cartoon-empty-by-justin-ternet-182928) - Public Domain
