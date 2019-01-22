---
layout: post
title: "[Flutter] Row"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

<img src="https://cdn-images-1.medium.com/max/1000/1*nyRvV8qDCG13aUTJdjvGeg.png">

컨테이너와 같이 부모 위젯이 한개의 위젯만 가질 수 있는데 여러개의 위젯을 가지고 싶을 경우 row나 column 위젯을 사용할 수 있다. Row는 이름 그대로 수평으로 위젯들을 나타내기 위한 위젯이다. 하지만 그 크기가 한정되있기 때문에 스크롤로 나타내고 싶다면 [ListView](https://docs.flutter.io/flutter/widgets/ListView-class.html) 위젯을 쓰는 것이 낫고 Row를 쓰는데 자식 위젯을 1개만 가진다면 [Align](https://docs.flutter.io/flutter/widgets/Align-class.html)이나 [Center](https://docs.flutter.io/flutter/widgets/Center-class.html) 위젯을 쓰는 것이 낫다.



# 속성

* **children : List<Widget\>**

Row는 여러개의 위젯을 가질 수 있기 때문에 List 형태로 위젯을 가지며 어떤 위젯도 들어갈 수 있다. 그러나 위에서 말했다시피 크기에 제한이 있다.

```dart
return Container(
     color: Colors.yellow,
     child: new Row(
        children: <Widget>[
          // Text 위젯을 그냥 쓰면 범위를 넘어가버려서 폰트크기를 제한함
          new Text("haram",style: TextStyle(fontSize: 20.0),),
          new FlutterLogo(),
          new Icon(Icons.stars, size: 50.0,),
        ],
     ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42297337-beb9893c-8039-11e8-9ecb-6ec32fab1232.png" width="200px">

공간 제약이 있기 때문에 여유 공간 크기 이상의 위젯을 사용하기 위해선 해당 위젯의 크기를 줄이거나 [Expanded](https://docs.flutter.io/flutter/widgets/Expanded-class.html) 클래스를 사용해야 한다. 

* **Expanded**

Expanded 위젯은 main axis를 기준으로 여유공간을 채워주는 역할을 하며 Row, Column 또는 Flex 위젯 하위에 사용된다. 위 예제에서 "haram"을 Expanded 위젯으로 감싸보자.

```dart
return Container(
     color: Colors.yellow,
     child: new Row(
        children: <Widget>[
          // Expanded 위젯으로 감쌌기 때문에 폰트 크기를 지정하지 않음
          new Expanded(child: Text("haram")),
          new FlutterLogo(),
          new Icon(Icons.stars, size: 50.0,),
        ],
     ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42297486-b5713fcc-803a-11e8-93f1-1a55e256d000.png" width="200px">

"haram"이 여유공간을 모두 차지 하는 것을 알 수 있다. 그렇다면 오른쪽 2개의 로고와 아이콘도 동일한 여유공간을 차지하게 할 수는 없을까? 모두 Expanded 위젯으로 감싸면 전체 main axis를 기준으로 3분할이 되기 때문에 가능하다.

```dart
return Container(
     color: Colors.yellow,
     child: new Row(
        children: <Widget>[
          new Expanded(child: Text("haram")),
          new Exapnded(child: FlutterLogo()),
          new Expanded(child: new Icon(Icons.stars, size: 50.0,)),
        ],
     ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42297578-77137618-803b-11e8-9719-dcca59316cfb.png" width="200px">

* **mainAxisAlignment**

Row의 중요한 속성 중 하나로 children의 정렬 여부를 결정하며 [MainAxisAlignment](https://docs.flutter.io/flutter/rendering/MainAxisAlignment-class.html) 클래스를 사용한다. 가장 오른쪽의 스타 아이콘을 가운데 정렬 시켜보자.

```dart
return Container(
     color: Colors.yellow,
     child: new Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          new Icon(Icons.stars, size: 100.0,)
        ],
     ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42297648-e8eaa6d0-803b-11e8-8b43-1e35cb543e35.png" width="200px">

* **mainAxisSize**

이 속성은 main Axis인 행을 기준으로 여유 공간의 크기를 정하는 것으로 [MainAxisSize](https://docs.flutter.io/flutter/rendering/MainAxisSize-class.html) 클래스를 사용하며 min과 max로 정하는 것에 따라 달라진다. 먼저 max로 정하는 경우이다.

```dart
return new Center(
      child: new Container(
        color: Colors.yellow,
        child: new Row(
          mainAxisSize: MainAxisSize.max,
          children: [
            Icon(Icons.stars, size: 50.0),
            Icon(Icons.stars, size: 100.0),
            Icon(Icons.stars, size: 50.0),
        ],
     ),
   ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42298179-8bc86fba-803f-11e8-8a2f-e96be6073891.png" width="200px">

그 다음 min일 경우로 해당 children이 차지하는 공간만큼 줄어든다는 것을 알 수 있다. max를 min으로 바꾸면 되니 따로 코드는 적지 않겠다.

<img src="https://user-images.githubusercontent.com/35518072/42298224-cb149efa-803f-11e8-8dac-78a17542e2de.png" width="200px">

* **crossAxisAlignment**

Cross axis면 main axis가 행이므로 열이라는 것을 알 수 있고, 열 기준 정렬이란 것도 알 수 있을 것이다. [CrossAxisAlignment](https://docs.flutter.io/flutter/rendering/CrossAxisAlignment-class.html) 클래스를 사용하며 start, end, center, baseline 등의 속성이 있다. start를 적용한 경우이다.

```dart
return new Container(
      color: Colors.yellow,
      child: new Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(Icons.stars, size: 50.0),
          Icon(Icons.stars, size: 100.0),
          Icon(Icons.stars, size: 50.0),
      ],
   ),
);
```

<img src="https://user-images.githubusercontent.com/35518072/42298496-21139aa8-8041-11e8-9917-81a9f63e72c6.png" width="200px">

## 출처

* https://flutterdoc.com/widgets-row-4ff6c5cfb9e0
* https://medium.com/jlouage/flutter-row-column-cheat-sheet-78c38d242041
* https://docs.flutter.io/flutter/widgets/Row-class.html