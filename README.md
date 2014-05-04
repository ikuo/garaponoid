# Garaponoid
(work in progress) An Android client for Garapon TV.

# Build
Create a file `src/main/res/values/key.xml` with the following content:

```
<resources>
  <string name="gtv_dev_id">my_developer_id</string>
</resources>
```

Replace `my_developer_id` with your developer ID of Garapon TV API.

Make package and start the app by sbt:

```
$ sbt
> package
> start
```

# License
Apache License 2.0

# Credits
Garaponoid is built on top of the following works:

- [Garapon4S](https://github.com/ikuo/garapon4s) - Apache License 2.0
- [Scaloid](https://github.com/pocorall/scaloid) - Apache License 2.0

The logo (TV_icon.svg and tv.png) is derived from:

- [A work of Justin Ternet](http://openclipart.org/detail/182928/tv-cartoon-empty-by-justin-ternet-182928) - Public Domain
