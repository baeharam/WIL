---
layout: post
category: flutter
title: "[Flutter]페이지 전환하면서 데이터 가져오기"
---

앱에서의 화면/페이지 전환은 굉장히 자주 일어나기 때문에 기본 중에 기본이라고 할 수 있다. 전에 많이 봤던 Navigator 위젯을 활용하면 push/pop 메소드를 통해 왔다갔다 할 수 있다. 그런데 만약 해당 페이지에서 데이터를 가져오기 위해선 어떻게 해야 할까? push 메소드의 레퍼런스를 보면 pop 메소드를 수행했을 때의 result 값을 받는다고 나와있다.

> Returns a [Future](https://docs.flutter.io/flutter/dart-async/Future-class.html) that completes to the `result` value passed to [pop](https://docs.flutter.io/flutter/widgets/NavigatorState/pop.html) when the pushed route is popped off the navigator. 

pop에선 positional optional parameter로 result 값을 받으며 데이터를 전달하기 위한 용도이다. 따라서 원리는 push로 다른 route(페이지)로 갔다 pop으로 올 때 result 값을 받는 것이다. 그런데 Future 타입으로 받기 때문에 해당 result 값을 띄우기 위해선 async를 사용해야 한다.

전체적인 코드는 플러터 쿡북에 있으니 거기서 보고 여기선 중요한 부분만 보도록 하자. 먼저 push하는 부분이다.

```dart
_navigateAndDisplaySelection(BuildContext context) async {
    final result = await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => SelectionScreen()),
    );
 }
```

`async`와 `await`을 이용하여 해당 route로 이동하고 pop했을 때의 결과값을 받고 있다. pop에선 단순히 두번째 인자값으로 리턴할 데이터를 넣어주면 된다. 이동한 route에 버튼이 있다고 하고 그 버튼의 onPressed를 구현할 때 pop을 사용한다.

```dart
onPressed: () => Navigator.pop(context, "리턴 값!!");
```

이제 원래 route로 돌아와서 해당 결과값을 스낵바로 띄워주든지 하면 데이터를 가져왔는지 알 수 있다. 결과는 아래와 같다.

<img src="https://user-images.githubusercontent.com/35518072/42485913-ac20b0bc-8434-11e8-8973-eb744003fef3.gif" width="300px">