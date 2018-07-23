---
layout: post
category: flutter
title: "[Flutter]WillPopScope"
---

대부분의 앱이 첫 화면에서 뒤로 가기 버튼을 누르면 "정말로 종료하시겠습니까?"와 같은 확인 창이 뜬다. 이런 기능은 어떻게 구현할 수 있을까? [WillPopScope](https://docs.flutter.io/flutter/widgets/WillPopScope-class.html) 위젯을 사용하면 간단하게 구현할 수 있다.

> Registers a callback to veto attempts by the user to dismiss the enclosing [ModalRoute](https://docs.flutter.io/flutter/widgets/ModalRoute-class.html). 

레퍼런스에는 위와 같이 ModalRoute를 없애는 사용자의 시도를 거부하는 콜백함수를 등록한다고 정의되어 있다. 이해하기에는 좀 어려운 문장이지만 뒤로 가기 버튼을 눌렀을 경우를 생각해보면 좀 더 쉬울 것 같다. 뒤로가기 버튼을 누른다는 것은 현재 화면에서 나간다는 시도이고 그걸 거부하는 창을 띄워주는 것이 WillPopScope의 역할로 볼 수 있게 된다.

이 기능을 구현하려면 route에 해당하는 위젯을 WillPopScope로 감싸주고 그에 해당하는 콜백함수를 정의해주면 끝난다. 보통 Scaffold를 하나의 route로 취급하니까 Scaffold를 감싸기로 해보자.

```dart
@override
Widget build(BuildContext context) {
    return WillPopScope(
    	onWillPop: _yno // yes or no
        child: Scaffold(...)
    );
}
```

이제 콜백함수인 `_yno`를 2개의 버튼으로 정의하면 다음과 같다.

```dart
Future<bool> _yon() {
    return showDialog(
    	context: context,
        builder: (_) => AlertDialog(
        	title: Text("Will you really quit?"),
            actions: <Widget>[
                FlatButton(
                	child: Text("Yes"),
                    onPressed: () => Navigator.pop(context, true)
                ),
                FlatButton(
                	child: Text("No"),
                    onPressed: () => Navigator.pop(context, false)
                )
            ]
        )
    );
}
```

콜백함수의 리턴타입은 Future\<bool>임을 잊지말자. 또한 Navigator.pop을 사용할 때 2번째 인자로 true를 주면 pop되고 false를 주면 원래의 route로 돌아간다.

