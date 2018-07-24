---
layout: post
category: flutter
title: "[Flutter]SizedBox"
---

특정한 크기를 갖는 박스로 레퍼런스에는 다음과 같이 나와있다.

> *자식 위젯이 있는 경우, 부모 위젯이 허용하는 범위 내에서 크기를 강제한다. 만약 너비나 높이가 null이라면 자식 위젯의 크기에 맞춰진다.*

즉, 자식은 원래 위젯의 크기로 강제되고 원래 위젯은 부모의 크기로 강제된다는 말이다. SizedBox의 사용법은 여러가지가 있다.

# SizedBox

```dart
return SizedBox(
	width: 100.0,
    height: 100.0,
    child: FlutterLogo()
);
```

FlutterLogo의 크기는 100x100이 되고 자신의 크기를 갖는다 해도 SizedBox의 크기로 정해진다.

# SizedBox.fromSize

만약 디바이스에 맞게 사이즈를 조정하고 싶다면 MediaQuery를 이용해서 사이즈를 얻어온 뒤에 이 생성자를 사용해서 지정할 수 있다.

```dart
return SizedBox.fromSize(
	size: MediaQuery.of(context).size / 2.0,
    child: FlutterLogo()
);
```

# SizedBox.expand

단순히 부모 위젯의 크기에 맞추고 싶다면 SizedBox.expand를 사용하면 된다.

```dart
return SizedBox.expand(
	child: FlutterLogo()
);
```

# SizedOverflowBox

자식 위젯의 크기를 원래 위젯의 크기보다 크게끔 설정하고 싶다면 SizedOverflowBox를 사용하자.

```dart
return SizedOverflowBox(
	size: Size(100.0, 100.0),
    child: FlutterLogo(size: 200.0)
);
```

# FractionallySizedBox

원래 위젯의 크기를 부모 위젯 기준으로 정하고 싶다면 FractionallySizedBox를 사용하자.

```dart
return FractionallySizedBox(
    // 부모 위젯 대비 1/2로 줄임
	widthFactor: 0.5,
    heightFactor: 0.5
);
```

# 여백

위젯과 위젯 사이에 여백을 주고 싶을 때가 있다. 물론 Padding을 사용하면 되지만 nesting이 심할 경우엔 안 좋을수 있고 특정 방향에만 여백을 주고자 할 땐 직관적이지 않을 수 있다. 그러면 Container를 사용하면 안될까? 이것도 가능하지만 속성이 굉장히 많고 비용이 얼마나 드는지 알 수가 없다. 이 때 직관적이고 간단하게 사용하는 것이 SizedBox이다. 수직으로 공백을 넣고 싶다면 height 값만 지정해주면 된다.

```dart
return ListView(
    children: [
        Text("SizedBox 1"),
        SizedBox(height: 20.0),
        Text("SizedBox 2")
    ]
);
```