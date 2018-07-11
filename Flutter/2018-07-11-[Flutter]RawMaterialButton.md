---
layout: post
category: flutter
title: "[Flutter]RawMaterialButton으로 버튼 커스터마이징 하기"
---

이제까지 종종 사용했던 버튼들은 RaisedButton이나 FlatButton이었지만 실제적으로 버튼을 커스터마이징하려면 [RawMaterialButton](https://docs.flutter.io/flutter/material/RawMaterialButton-class.html)을 사용해야 한다. 다양한 속성들이 있으니 한번 보자.

* shape : 버튼의 모양을 결정한다.
* fillColor : 버튼의 배경색을 결정한다.
* elevation/highlightElevation : 버튼의 그림자/클릭시 그림자를 결정한다.
* splashColor/highlightColor : 2개의 계층으로 나뉘는 InkWell로 버튼 클릭시 퍼지는 색깔 결정

```dart
// Scaffold 안의 body
body: Container(
	color: Colors.red.withOpacity(0.5) // 버튼의 그림자를 잘 보이게 하기 위해
    child: Center(
    	child: RawMaterialButton(
        	shape: CircleBorder(), // 원모양으로 지정
            fillColor: Colors.white,
            child: Padding(
            	padding: const EdgeInsets.all(15.0),
                child: Icon(Icons.android, size: 100.0, color: Colors.black)
            ),
            elevation: 10.0,
            highlightElevation: 20.0,
            splashColor: Colors.red,
            highlightColor: Colors.transparent,
            onPressed: (){}
        )
    )
)
```

한번 훑어보면 어떤 속성들이 지정되었는지 알 수 있을 것이다. 어떻게 동작하는지 gif로 보도록 하자.

<img src="https://user-images.githubusercontent.com/35518072/42551069-4e7438ee-8510-11e8-95a2-8d5a3fef348f.gif" width="300px">

보면 기본적으로 그림자가 지정되어있고, 누르면 더 큰 그림자가 생긴다는 것을 알 수 있다. 또한 splashColor가 빨간색으로 설정되어있기 때문에 눌렀을 시 빨간색이 보인다. 하지만 여기서 highlightColor를 투명이 아닌 다른 색으로 지정할 경우는 두 번째 계층이므로 그 색이 보인다.