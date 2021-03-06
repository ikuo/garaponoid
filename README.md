# Garaponoid
An Android client for [Garapon TV](http://garapon.tv/).
Simply search programs by keyword and show them in cards style as follows:

![](http://drive.google.com/uc?export=view&id=0B13qn3aDmWCOSnBIZDVuTm01YVk)

Distributed on Google Play: https://play.google.com/store/apps/details?id=com.github.ikuo.garaponoid

## Building
Garaponoid is written in [Scala](http://www.scala-lang.org/)
and built with [sbt](http://www.scala-sbt.org/).

At first, publish android-error-dialog to local repository by following readme of [android-error-dialog](https://github.com/ikuo/android-error-dialog/blob/master/README.md)

Then, build garaponoid:

```
$ git clone git@github.com:ikuo/garaponoid.git
$ android update project -p . -t android-19
$ git co proguard-project.txt
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

## License

Apache License 2.0

Copyright (c) 2014, Ikuo Matsumura

## Credits
Garaponoid is powered by the following works:

- [Garapon4S](https://github.com/ikuo/garapon4s) - Apache License 2.0
- [Scaloid](https://github.com/pocorall/scaloid) - Apache License 2.0
- [Cards Library](https://github.com/gabrielemariotti/cardslib) - Apache License 2.0
- [sbt android-sdk-plugin](https://github.com/pfn/android-sdk-plugin) (build time)
- [Android flow layout](https://github.com/ApmeM/android-flowlayout) - Apache License 2.0
- [Android error dialog](https://github.com/ikuo/android-error-dialog) - Apache License 2.0

The logo (tv.svg) is derived from:

- [A work of Justin Ternet](http://openclipart.org/detail/182928/tv-cartoon-empty-by-justin-ternet-182928) - Public Domain
