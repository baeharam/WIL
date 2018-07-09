---
layout: post
category: flutter
title: "[Flutter]FutureBuilder를 사용해서 위젯 업데이트 하기"
---

Future 클래스를 이용해서 데이터를 받고 그 데이터를 이용해서 위젯을 생성하고 싶을 때 FutureBuilder 클래스를 이용할 수 있다. FutureBuilder는 다음과 같은 속성들을 지닌다.

* **future** : Future 타입을 가진 데이터이다.
* **builder** : context와 snapshot을 인자로 받아 위젯을 생성하는 메소드이다.
* **initialData** : future가 완료될 때까지 사용하는 데이터이다.

http.get()과 같은 메소드를 이용해서 Future 타입의 데이터를 받는 경우를 생각해보자. 데이터를 받기 전까지는 initialData의 값을 쓰게 되고 데이터를 받게 되면 해당 값을 사용할 수 있게 된다. 이 값은 builder 메소드에서 이용해서 위젯을 생성하는데 사용된다.

builder 메소드에선 snapshot의 상태에 따라 switch문을 통해서 다양한 경우를 고려할 수 있다. 여기서 snapshot이란 future 데이터의 최신 정보라고 보면 된다. 데이터를 가져오지 않은 경우, 가져오는 중인 경우, 가져온 경우로 크게 나눌 수 있으며 그에 따라 생성하는 위젯을 다르게 할 수 있다. 레퍼런스의 예제코드를 확인해보자.

```dart
new FutureBuilder<String>(
  future: _calculation, // a Future<String> or null
  builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
    switch (snapshot.connectionState) {
      case ConnectionState.none: return new Text('Press button to start');
      case ConnectionState.waiting: return new Text('Awaiting result...');
      default:
        if (snapshot.hasError)
          return new Text('Error: ${snapshot.error}');
        else
          return new Text('Result: ${snapshot.data}');
    }
  },
)
```

snapshot을 받아서 현재 connectionState 값이, 즉 연결상태가 안된 경우는 "Press button to start"로 Text 위젯을 생성하고 연결을 기다리고 있는 경우는 "Awaiting result..."로 Text 위젯을 생성하고 있다. 마지막으로 둘 다 아닌 경우 에러 핸들링과 정상 결과값을 보여줌을 알 수 있다.