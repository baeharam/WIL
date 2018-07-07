---
layout: post
category: flutter
title: "[Flutter]LoginForm과 Hero를 이용한 화면전환-3"
---

**다음 글은 유튜브 [Kodeversitas](https://www.youtube.com/watch?v=efbB8-x9T2c) 님의 강의를 보고 정리하는 포스팅입니다.**



# Route와 Navigator

Hero를 이용한 전환을 하기 위해선 위 개념을 알아야 한다. 앱은 알다시피 여러개의 화면 또는 페이지로 구성되며 왔다갔다를 반복한다. 플러터에선 이러한 화면/페이지를 추상화시켜 route라고 부르며 route를 관리하는 위젯을 navigator라고 부른다. Navigator는 사용자가 요청한 route를 찾아서 띄워주는 역할을 하는 것이다.

여기선 MaterialApp 위젯을 이용해서 route를 설정할 것인데 [routes](https://docs.flutter.io/flutter/material/MaterialApp/routes.html)라는 속성값을 가지고 있기 때문에 route의 이름과 해당 위젯을 map으로 전달해주면 된다. 무슨 소린지 모르겠으니 한번 코드를 보자.

```dart
final routes = <String, WidgetBuilder>{
    HomePage.tag: (context) => HomePage()
};
```

HomePage 클래스에는 static 변수 tag의 값을 지정해 놓고 그 값과 [WidgetBuilder](https://docs.flutter.io/flutter/widgets/WidgetBuilder.html)라는 값을 mapping시켜주면 된다. WidgetBuilder는 context를 인자값으로 받는 간단한 함수 형식이다. 잠깐 HomePage 클래스를 보자면 아래와 같이 되어 있다.

```dart
class HomePage extends StatelessWidget {
    static String tag = "home-page";
    // ...
}
```

<br>

# Route 전환하기

먼저 main.dart 파일에서 MaterialApp에 routes 속성값을 정해주어야 한다.

```dart
return MaterialApp(
	// ...
    route: routes
)
```

이제 로그인 버튼을 눌렀을 때 route를 전환시켜보자. loginButton은 MaterialButton 위젯으로 구현했고 아직 onPressed 메소드의 값을 주지 않은 상태이기 때문에 다음과 같이 주자.

```dart
onPressed() {
    Navigator.of(context).pushNamed(HomePage.tag)
}
```

[pushNamed](https://docs.flutter.io/flutter/widgets/Navigator/pushNamed.html) 메소드를 통해 navigaotr로 하여금 route를 전환시키는 것으로 해당 route의 이름을 넘겨주면 로그인 버튼을 눌렀을 때 그 route로 이동하게 된다.

<br>

이렇게 하면 이전에 Hero를 이용해 tag 속성 값을 동일한 "hero"로 정해주었기 때문에 자동으로 hero animation이 적용된다. 움짤로라도 첨부하고 싶으나 오버헤드이므로 여기서 끝내겠다..상당히 양이 많아 힘들었지만 그래도 많은 것들을 배웠으니 감사하다.