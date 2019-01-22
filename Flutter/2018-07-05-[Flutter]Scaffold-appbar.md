---
layout: post
title: "[Flutter]Scaffold에서 AppBar 사용하기"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

# Scaffold 위젯

<img src="https://storage.googleapis.com/material-design/publish/material_v_11/assets/0Bx4BSt6jniD7T0hfM01sSmRyTG8/layout_structure_regions_mobile.png">

> Implement the basic material design visual layout structure.

레퍼런스를 보면 알 수 있듯이 기본적인 material design의 레이아웃 구조를 구현하는 위젯으로 다양한 위젯들을 하나의 화면에 알맞게 배치시킬 수 있는 것이 큰 특징이다. 상당히 많은 위젯들을 지원하는데 AppBar, BottomAppBar, FloatingActionButton, Drawer, BottomNavigationBar, SnackBar 등 이 있다. 많은 위젯들 중에 이번엔 AppBar에 대해 알아보도록 하자.



# AppBar

<img src="https://flutter.github.io/assets-for-api-docs/assets/material/app_bar.png">

AppBar는 말 그대로 위쪽에 위치하는 툴바형태의 메뉴를 말하며 다양한 속성이 존재한다. 기본적인 것들 몇개만 알아보고 구현하도록 하자.

* **title & leading**

title은 appbar의 제목이고 leading은 제목 왼쪽에 위치하는 위젯이다. 둘 다 위젯으로 구현한다.

```dart
return new Scaffold(
	appBar: new AppBar(
    	title: new Text(
        	"HARAM"
        ),
        leading: new IconButton(
        	Icon(Icons.star)
        )
    )
);
```

<img src="https://user-images.githubusercontent.com/35518072/42302689-52692576-8057-11e8-8771-be1d8781fe23.png" width="200px">

깔끔하게 구현된 것을 볼 수 있다.

* **actions**

actions는 오른쪽 상단에 달리는 위젯으로 List\<Widget> 타입의 위젯이기 때문에 다양한 위젯들이 올 수 있다. 여기선 search 아이콘을 달아보자.

```dart
return new Scaffold(
	appBar: new AppBar(
    	title: new Text(
        	"HARAM"
        ),
        leading: new IconButton(
        	Icon(Icons.star)
        ),
        actions: <Widget>[
            IconButtion(
            	icon: Icon(
                    Icons.search,
                    size: 30.0
                )
            )
        ]
    )
);
```

<img src="https://user-images.githubusercontent.com/35518072/42302785-fae5b39a-8057-11e8-97cb-dd72e0ffe251.png" width="200px">

오른쪽 상단에 작게나마 검색 아이콘이 생긴것을 확인할 수 있다.

* **backgroundColor**

배경색도 바꿀 수 있으며 한번 갈색으로 바꿔보자.

```dart
return new Scaffold(
	appBar: new AppBar(
    	title: new Text(
        	"HARAM"
        ),
        leading: new IconButton(
        	Icon(Icons.star)
        ),
        actions: <Widget>[
            IconButtion(
            	icon: Icon(
                    Icons.search,
                    size: 30.0
                )
            )
        ],
        backgroundColor: Colors.brown
    )
);
```

<img src="https://user-images.githubusercontent.com/35518072/42302898-d21b6d8c-8058-11e8-867e-bb5c9371104b.png" width="200px">

이외에도 flexibleSpace, bottom, bottomOpacity 등 많은 속성들이 있지만 지금 단계에선 이 정도면 충분한 것 같기 때문에 나중에 기회가 된다면 심화적으로 알아보기로 한다.



## 출처

* https://docs.flutter.io/flutter/material/AppBar-class.html