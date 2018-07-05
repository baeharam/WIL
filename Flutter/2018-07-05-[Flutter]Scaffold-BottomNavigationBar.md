---
layout: post
category: flutter
title: "[Flutter]Scaffold에서 BottomNavigationBar 구현하기"
---

<img src="https://user-images.githubusercontent.com/1096485/32510613-7bcce9f2-c3f1-11e7-8e6f-49df37b1109c.png">



AppBar가 위에 있었다면 BottomNavigationBar는 위 그림과 같이 아래 있는 메뉴라고 생각하면 된다. 이제 구현해가면서 살펴보도록 하자.

# BottomNavigationBar

*	**items**

items는 [List\<BottomNavigationBarItem>](https://docs.flutter.io/flutter/widgets/BottomNavigationBarItem-class.html) 타입을 가지는 속성으로 해당 클래스를 이용해서 각각의 item들을 구성해야 한다. 아이콘으로 3가지 item을 구성해보자.

```dart
return new Scaffold(
      bottomNavigationBar: new BottomNavigationBar(
          items: <BottomNavigationBarItem>[
            new BottomNavigationBarItem(
                icon: Icon(Icons.trending_up),
                title: new Text("Icon 1")
            ),
            new BottomNavigationBarItem(
                icon: Icon(Icons.accessibility),
                title: new Text("Icon 2")
            ),
            new BottomNavigationBarItem(
                icon: Icon(Icons.account_balance),
                title: new Text("Icon 3")
            )
        ]
    ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42303171-9c4b085a-805a-11e8-9b5b-5e70c277d938.png" width="200px">

3개의 아이콘이 아래쪽에 생긴것을 볼 수 있는데 첫번째 아이콘이 파란색으로 활성화가 되었다. 이건 현재 해당 item에 있다는 상태로 다음 속성에서 볼 수 있다.

* **currentIndex**

현재 어느 item이 활성화되어있는가를 알려주는 것으로 임의로 설정할 수 있다. 인덱스 값은 0부터 시작하며 2로 바꿔보면 세번째 아이콘이 활성화 된다.

```dart
return new Scaffold(
      bottomNavigationBar: new BottomNavigationBar(
          items: <BottomNavigationBarItem>[
            new BottomNavigationBarItem(
                icon: Icon(Icons.trending_up),
                title: new Text("Icon 1")
            ),
            new BottomNavigationBarItem(
                icon: Icon(Icons.accessibility),
                title: new Text("Icon 2")
            ),
            new BottomNavigationBarItem(
                icon: Icon(Icons.account_balance),
                title: new Text("Icon 3")
            )
        ],
          currentIndex: 2
    ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42303243-fd8f4ce8-805a-11e8-818b-2fa1067b1a06.png" width="200px">

* **iconSize, fixedColor**

아이콘의 크기를 바꿀 수도 있고 색깔을 고정시킬 수도 있다.

```dart
return new Scaffold(
      bottomNavigationBar: new BottomNavigationBar(
          items: <BottomNavigationBarItem>[
            new BottomNavigationBarItem(
                icon: Icon(Icons.trending_up),
                title: new Text("Icon 1")
            ),
            new BottomNavigationBarItem(
                icon: Icon(Icons.accessibility),
                title: new Text("Icon 2")
            ),
            new BottomNavigationBarItem(
                icon: Icon(Icons.account_balance),
                title: new Text("Icon 3")
            )
        ],
          currentIndex: 2,
          iconSize: 50.0,
          fixedColor: Colors.red
    ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42303349-a46269ce-805b-11e8-9622-757809bf4586.png" width="200px">



## 출처

* https://github.com/flutter/flutter/issues/12099
* https://docs.flutter.io/flutter/material/BottomNavigationBar-class.html