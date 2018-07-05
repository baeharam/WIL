---
layout: post
category: flutter
title: "[Flutter]Scaffold에서 FloatingActionButton 구현하기"
---

<img src="https://cocoacontrols-production.s3.amazonaws.com/uploads/control_image/image/6540/iOS_Simulator_Screen_Shot_25.05.2015_20.54.49.png" width="200px">

FloatingActionButton은 flutter 프로젝트를 생성하게 되면 구현되는 위젯 중 하나로 scaffold에서 화면 하단 우측에 나타나는 버튼형 위젯을 말한다. 속성을 살펴보면서 하나하나 익혀가도록 하자. 너무 단어가 길어서 fab라고 부르겠다.

[FloatingActionButton](https://docs.flutter.io/flutter/material/FloatingActionButton-class.html)

* **backgroundColor, foregroundColor**

backgroundColor는 fab의 배경색을 바꿔주며 foregroundColor는 fab에 들어가는 아이콘이나 텍스트의 색깔을 바꿔주는 역할을 한다. 하얀색 카메라 아이콘, 주황색 배경의 fab를 만들어보자.

```dart
return new Scaffold(
	floatingActionButton: new FloatingActionButton(
    	child: new Icon(Icons.add_a_photo),
        backgroundColor: Colors.deepOrangeAccent,
        foregroundColor: Colors.white
    )
)
```

<img src="https://user-images.githubusercontent.com/35518072/42326123-2cd95316-80a3-11e8-9b22-232ce0c16627.png" width="200px">

* **elevation, highlightElevation**

구현하다 안되서 고생한 부분인데 elevation은 z축 위치를 정해주는 것으로 사용자 입장에서 얼마나 사용자 쪽으로 떠있는지 효과를 주는 부분이다. highlightElevation은 사용자가 fab를 눌렀을 때 z축 위치를 정해준다. <u>고생했던 부분은 onPressed 속성이 null이면 안된다는 것이었다. 최소, 메소드 기본 형태는 주어야 작동했으니 잘 기억해두자.</u>

```dart
return new Scaffold(
	floatingActionButton: new FloatingActionButton(
    	child: new Icon(Icons.add_a_photo),
        backgroundColor: Colors.deepOrangeAccent,
        foregroundColor: Colors.white,
        elevation: 30.0, // 기본값 6.0
        highlightElevation: 30.0 // 기본값 12.0
    )
)
```

highlightElevation의 효과는 액션이라 보여줄 수는 없다 ㅠㅠ elevation 효과라도 보자.

<img src="https://user-images.githubusercontent.com/35518072/42326338-d4b5dea6-80a3-11e8-8ce1-db7441b2b7ec.png" width="200px">

z 축 방향으로 그림자가 생겼다는 것을 확인할 수 있다.

* **mini, tooltip**

mini는 fab가 default 크기보다 작아지는 속성이고 tooltip은 fab를 길게 눌렀을 때 어떤 기능을 수행하는지 텍스트를 보여주는 역할을 한다.

```dart
return new Scaffold(
	floatingActionButton: new FloatingActionButton(
    	child: new Icon(Icons.add_a_photo),
        backgroundColor: Colors.deepOrangeAccent,
        foregroundColor: Colors.white,
        elevation: 30.0,
        highlightElevation: 30.0,
        tooltip: "Good FAB",
        mini: true
    )
)
```

<img src="https://user-images.githubusercontent.com/35518072/42326588-872bd6f8-80a4-11e8-9aff-8f45b2e90b22.png" width="200px">

## 출처

* https://www.cocoacontrols.com/search?q=floating+action+button
* https://docs.flutter.io/flutter/material/FloatingActionButton-class.html