---
layout: post
category: flutter
title: "[Flutter]ClipOval로 이미지를 둥글게 만들기"
---

[ClipOval](https://docs.flutter.io/flutter/widgets/ClipOval-class.html)은 child를 타원형으로 잘라주는 위젯인데, 어떻게 자르냐는 속성 값을 정할 수 있다. clipper라는 속성이며 기본값은 해당 위젯이 속한 bounding box의 축 기준으로 자른다는데, 위젯을 감싸고 있는 다른 위젯 기준으로 자른다는 말과 동일한 것 같다.

> If the [clipper](https://docs.flutter.io/flutter/widgets/ClipOval/clipper.html) delegate is null, then the oval uses the widget's bounding box (the layout dimensions of the render object) instead. 

한번 ClipOval을 기본으로 사용했을 때 어떻게 잘라지는지 보도록 하자.

```dart
// Scaffold의 body
body: Center(
	child: ClipOval(
    	child: Image.network("이미지주소")
    )
)
```

<img src="https://user-images.githubusercontent.com/35518072/42549348-9ae3b18c-8506-11e8-8326-2744f227ffe9.png" width="300px">

단순히 이미지 박스에 맞게 잘라지는 것을 알 수 있다. 이제 이걸 원으로 만들기 위해선 clipper 속성을 커스터마이징 해야 한다.

> The delegate returns a rectangle that describes the axis-aligned bounding box of the oval. The oval's axes will themselves also be axis-aligned. 

여기서 axis-aligned bounding box(aabb)라는 용어가 나오는데 axis-aligned라는 것은 말 그대로 축 기준으로 정렬한다는 말이다. x축, y축으로 항상 정렬되어 있는 bounding box로 대각선이나 이상하게 놓일 수 없다는 것이다. 따라서 커스터마이징한 clipper가 하는 일은 타원의 aabb를 형성하는 사각형을 리턴하는 것이다. clipper를 커스터마이징 하기 위해선 [CustomClipper](https://docs.flutter.io/flutter/rendering/CustomClipper-class.html)를 상속한 또 하나의 클래스를 만들어 주어야 하며 이 클래스는 getClip과 shouldReclip을 오버라이딩 해야 한다.

```dart
class CircleClipper extends CustomClipper<Rect> {
	@override
    Rect getClip(Size size) {
        return Rect.fromCircle(
        	center: Offset(size.width/2, size.height/2),
            radius: min(size.width, size.height) / 2
        )
    }
}
```

위 코드가 뜻하는 바를 살펴보자. 먼저 Rect.fromCircle 생성자가 하는 역할은 주어진 원으로부터 center와 radius에 따라 사각형을 생성한다. [Rect](https://docs.flutter.io/flutter/dart-ui/Rect-class.html)가 axis-aligned 사각형을 생성하는 위젯이다. 여기서 center는 [Offset](https://docs.flutter.io/flutter/dart-ui/Offset-class.html)을 지정해서 넘겨주어야 하는데 사각형이 생성될 중심을 말하며 size.width/2, size.height/2라는 말은 주어진 원의 크기를 가로/세로 1/2씩 쪼갠 것이다. 마지막으로 radius는 반지름으로 가로/세로 중 작은 값을 1/2로 쪼갠 것인데 이렇게 하는 이유는 큰 값에 맞추면 원이 잘리기 때문이다. 이제 커스터마이징한 clipper를 적용하면,

```dart
body: Center(
	child: ClipOval(
        clipper: CircleClipper(),
    	child: Image.network("이미지주소")
    )
)
```

<img src="https://user-images.githubusercontent.com/35518072/42549904-8897b098-8509-11e8-972c-0e11109f3ab2.png" width="300px">