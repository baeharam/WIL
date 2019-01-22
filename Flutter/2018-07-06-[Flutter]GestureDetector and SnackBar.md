---
layout: post
title: "[Flutter]GestureDetector를 통해 SnackBar 띄우기"
category: Flutter(플러터)
permalink: /flutter/:year/:month/:day/:title/
tags: [플러터, 다트]
---

# GestureDetector

제스쳐를 감지하는 위젯으로 해당 제스쳐에 따라서 콜백 함수를 실행한다. 플러터에서 제스쳐를 감지할 때는 좌표 또는 의미있는 제스쳐 단위로 감지하는데 의미있는 제스쳐를 감지할 때 사용하는 위젯에 해당한다. 제스쳐에 대한 자세한 내용은 [다음](https://flutter.io/gestures/)을 참고하자. GestureDetector의 특징은 여러가지가 있는데 한번 훑고 넘어가면 좋을 것 같다.

* 만약 자식 위젯이 있으면 사이즈가 그 위젯에 맞춰지고 없으면 부모에게 맞춰진다.
* 기본적으로 자식 위젯이 보이지 않을 경우 사용자의 터치를 무시하며 [behavior](https://docs.flutter.io/flutter/widgets/GestureDetector/behavior.html) 클래스에 의해 제어된다.
* 접근 이벤트(accessibility event)도 감지하여 콜백함수를 실행하며 이걸 무시하기 위해선 [execludeFromSemantics](https://docs.flutter.io/flutter/widgets/GestureDetector/excludeFromSemantics.html)를 true 값으로 설정해주면 된다.
* Material design 어플리케이션은 전형적으로 터치를 할 때 스플래쉬가 생기며 [InkWell](https://docs.flutter.io/flutter/material/InkWell-class.html) 클래스가 이걸 구현하므로 GestureDetector에서 사용할 수 있다.





# SnackBar

<img src="https://i.stack.imgur.com/ABQM6.png">

스낵바는 위와 같이 잠깐동안 하단에 뜨는 메시지 바를 말한다. 스낵바를 보여주기 위해선 `Scaffold.of(context).showSnackBar()` 메소드를 호출해야 하며 인자값으로 SnackBar의 객체를 전달해주어야 한다. 얼마나 지속할 것인지에 대한 duration 값이나 backgroundColor 또는 스낵바가 나타나고 사라질 때의 animation 등을 지정할 수 있다.

여기서 주의깊게 봐야할 것은 스낵바를 보여주는 메소드에서 `Scaffold.of` 메소드의 인자값으로 전달되는 context이다. 고생했던 부분이 Scaffold와 SnackBar가 같은 빌드 함수에서 구현되었을 경우 실행이 안된다는 점이었다. 그래서 레퍼런스를 찾아보니 다음과 같이 명시하고 있었다.

> > When the [Scaffold](https://docs.flutter.io/flutter/material/Scaffold-class.html) is actually created in the same `build` function, the `context` argument to the `build`function can't be used to find the [Scaffold](https://docs.flutter.io/flutter/material/Scaffold-class.html) (since it's "above" the widget being returned). In such cases, the following technique with a [Builder](https://docs.flutter.io/flutter/widgets/Builder-class.html) can be used to provide a new scope with a [BuildContext](https://docs.flutter.io/flutter/widgets/BuildContext-class.html) that is "under" the [Scaffold](https://docs.flutter.io/flutter/material/Scaffold-class.html)...

요약하자면, 같은 `build` 함수 내에서 사용하게 되면 `Scaffold.of` 메소드는 Scaffold를 찾을 수 없기 때문에 이런 경우엔 Builder 클래스를 이용해야 한다는 것이다. 글로만 설명하면 이해가 안되니 어떤 경우인지 살펴보자.

아래와 같이 스낵바를 보여주는 메소드가 분리되어있으면 상관이 없다.

```dart
@override
Widget build(BuildContext context) {
  return new RaisedButton(
    child: new Text('SHOW A SNACKBAR'),
    onPressed: () {
      Scaffold.of(context).showSnackBar(new SnackBar(
        content: new Text('Hello!'),
      ));
    },
  );
}
```

하지만 같은 `build` 함수 안에서 사용하면 보여줄 수가 없으므로 다음과 같이 사용해야 한다.

```dart
@override
Widget build(BuildContext context) {
  return new Scaffold(
    appBar: new AppBar(
      title: new Text('Demo')
    ),
    body: new Builder(
      // Builder 위젯으로 묶은 뒤에 builder 속성을 이용해 새로운 context를 넘겨주어
      // Scaffold를 참조할 수 있게 만든다.
      builder: (BuildContext context) {
        return new Center(
          child: new RaisedButton(
            child: new Text('SHOW A SNACKBAR'),
            onPressed: () {
              Scaffold.of(context).showSnackBar(new SnackBar(
                content: new Text('Hello!'),
              ));
            },
          ),
        );
      },
    ),
  );
}
```



# 스낵바를 띄워보자!

이제 실제 구현을 해보도록 하자. 먼저 다른 `build` 함수 안에서 사용한다고 가정하고 GestureDetector를 만들어보자. 여기서는 onTap 속성을 이용해 자식 위젯을 탭 했을 때 스낵바를 보여주는 설정을 해야 한다.

```dart
return new GestureDetector(
    onTap: (){ // anonymous function
        final snackBar = SnackBar(
        	content: Text("Hello Gestures!"),
            duration: Duration
            (hours:0, minutes:0, seconds:0, milliseconds: 500, microseconds: 0)
        );
        Scaffold.of(context).showSnackBar(snackBar);
    }
)
```

0.5초의 duration을 주어 "Hello Gestures!"라는 메시지를 보여주는 스낵바를 띄우도록 콜백함수를 정의했다. 이제 child 위젯을 생성하게 되면 탭에 따라 콜백함수가 실행되는 것이다.

```dart
// GestureDetector 위젯 안이다.
child: new Container(
	child: new Text("First Buttons!")
)
```

<img src="https://user-images.githubusercontent.com/35518072/42354192-33bb17ac-8100-11e8-8b9d-f387f166d129.png" width="200px">



## 출처

* https://stackoverflow.com/a/45948913/9437175
* https://docs.flutter.io/flutter/material/Scaffold/of.html