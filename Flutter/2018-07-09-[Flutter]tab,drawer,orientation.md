---
layout: post
title: "[Flutter]Tab, Drawer, Orientation"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

# Tab

Scaffold의 AppBar에는 bottom 속성이 있고 TabBar위젯을 이용해서 탭을 여러개 만들 수 있다. 단 Scaffold를 [DefaultTabController](https://docs.flutter.io/flutter/material/DefaultTabController-class.html) 위젯으로 감싸주어야 하는데 TabBar와 TabBarView 위젯들로 하여금 TabController를 공유하게 하는 기능을 한다.

```dart
DefaultTabController(
  length: 3,
  child: Scaffold(
    appBar: AppBar(
      bottom: TabBar(
        tabs: [
          Tab(icon: Icon(Icons.directions_car)),
          Tab(icon: Icon(Icons.directions_transit)),
          Tab(icon: Icon(Icons.directions_bike)),
        ],
      ),
    ),
  ),
);
```

각 탭에 대해서 뭔가를 보여주도록 하는 것이 TabBarView 위젯이며 Scaffold의 body 속성 값으로 지정할 수 있다. 그냥 TabBarView를 써주면 되니 코드는 생략한다.

<img src="https://flutter.io/images/cookbook/tabs.gif">

<br>

# Drawer

Scaffold에 있는 drawer 속성값으로 왼쪽 상단 메뉴바에 의해서 나타나는 메뉴에 해당하는 위젯이다. [Drawer](https://docs.flutter.io/flutter/material/Drawer-class.html) 위젯을 사용하며 child 속성 값으로 보통 ListView를 쓴다. ListView는 children 위젯으로 DrawerHeader 위젯을 가져서 drawer의 헤더부분을 맡긴다.

```dart
Drawer(
  child: ListView(
    padding: EdgeInsets.zero,
    children: <Widget>[
      DrawerHeader(
        child: Text('Drawer Header'),
        decoration: BoxDecoration(
          color: Colors.blue
        )
      ),
      ListTile(
        title: Text('Item 1'),
        onTap: () {}
      ),
      ListTile(
        title: Text('Item 2'),
        onTap: () {}
      ),
    ],
  ),
);
```

Scaffold의 drawer 속성 부분만 따온 코드이다.

<img src="https://flutter.io/images/cookbook/drawer.png" width="350px">

<br>

# Orientation

Orientation은 현재 스마트폰의 방향이 세로인지 가로인지를 말해주는 값으로 portrait이면 세로, landscape이면 가로를 말한다. GridView 위젯을 생성할 때를 생각해보자. GridView.count 생성자를 사용하면 crossAxisCount 속성으로 열 값을 지정할 수 있으므로 세로/가로 방향을 나눠서 얼마나 보여줄 건지를 고려해야 한다. 만약 열을 2개로 설정했다면 기본 방향일 때는 2개 열만 보이게 하고 세로 방향일 때는 3개의 열을 보여주는 방식으로 할 수 있다. [OrientationBuilder](https://docs.flutter.io/flutter/widgets/OrientationBuilder-class.html) 위젯을 이용해야 orientation에 따라 자식 위젯들에 대한 반응을 지정할 수 있다.

```dart
OrientationBuilder(
  builder: (context, orientation) {
    return GridView.count(
      crossAxisCount: orientation == Orientation.portrait ? 2 : 3,
    );
  },
);
```

Scaffold의 body 속성 안이며 세로/가로에 따라서 `crossAxisCount` 값을 2/3으로 지정해주고 있다.

<img src="https://flutter.io/images/cookbook/orientation.gif">

<br>

## 출처

* https://flutter.io/cookbook/design/tabs/
* https://flutter.io/cookbook/design/drawer/
* https://flutter.io/cookbook/design/orientation/