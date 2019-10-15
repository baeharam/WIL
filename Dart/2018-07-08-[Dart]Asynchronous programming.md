---
layout: post
category: dart
title: "[Dart]비동기(Asynchronous) 프로그래밍"
---

Dart에서 asynchronous programming을 하는 방법은 Future 클래스 부터 알아야 그 원리를 이해할 수 있기 때문에 이것부터 알아보자.

# Future 클래스

모든 과정이 끝나고 결국 결과값은 도출되지만 언제 도출될지는 모르는 상태를 나타낸 클래스이다. 대표적인 예로 HttpRequest.getString() 메소드가 Future<String\> 타입 객체를 리턴하는데, 웹서버에 http 요청을 보내서 언제받을지 모르니 미리 Future 객체를 리턴하고 나중에 완료되면 값을 보여주는 형태이다. 한번 시연해보자. 먼저 HttpRequest를 사용하기 위해선 dart:html 라이브러리를 import 시켜야 한다. 이 블로그에 요청을 보내보자.

```dart
import 'dart:html';

void main() {
    var res = HttpRequest.getString("https://baeharam.github.io");
    print(res);
}
```

실행하면 "Instance of '_Future<String\>'"이라는 값이 나온다. 일단 Future 객체를 리턴한다고 아까 말했으니 이제 이해가 될 것이다. 그러면 실제 값을 어떻게 받아올 수 있을까? 2가지 방법이 있는데 Future 클래스에서 제공하는 메소드를 사용하거나 아니면 dart:async라는 라이브러리를 사용하면 된다.

<br>

# then과 catchError

Future 클래스는 then과 catchError라는 메소드를 가진다. then은 해당 Future객체를 리턴하는 메소드의 작업이 완료되었을 때 실행할 작업들을 의미하고 catchError는 then에 의해서 에러가 발생했을 경우 try~catch의 catch 문과 같은 역할을 한다. 이제 한번 제대로된 결과값을 받아오도록 하자.

```dart
import 'dart:html';

void main() {
    var res = HttpRequest.getString("https://baeharam.github.io");
    res.then((String response) => print(response));
}
```

결국 String 타입을 리턴하기 때문에 받아서 그걸 그대로 출력하는 메소드를 정의했다. 이제 그 메소드를 then 메소드에 인자로 넘겨주면 모든 작업이 끝났을 때 then 메소드가 사용자 정의 메소드를 실행하게 되는 과정이다. 결과는 잘 나온다.

```html
<!DOCTYPE html>
<html lang="en-us">

  <head>
  <link href="http://gmpg.org/xfn/11" rel="profile" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />

  <!-- Enable responsiveness on mobile devices-->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1" />

  <title>
    
      배하람 &middot; A Jekyll theme
    
  </title>
      ....
```

하지만 마지막 io에서 o를 빼먹었다면?? 에러가 발생할 것이니 에러핸들링을 하자.

```dart
import 'dart:html';

void main() {
    var res = HttpRequest.getString("https://baeharam.github.i");
    res.then((String response) => print(response));
    res.catchError((error)=>print("에러 발생!!"));
}
```

역시나 "에러 발생!!"이라는 메시지가 출력됨을 알 수 있다.

<br>

# dart:async

다음 라이브러리를 이용하면 보다 간단하게 결과값을 받을 수 있다. 또한 여기선 비동기 프로그래밍의 중요성을 알아볼 수 있다.

```dart
import 'dart:html';
import 'dart:async';

void main() async {
    var res = await HttpRequest.getString("https://baeharam.github.io");
    print(res);
}
```

비동기를 적용하고 싶은 메소드 끝에 `async`라는 키워드를 붙여야 하며 결과값을 받을 때까지 기다리기 위해선 해당 메소드 앞에 `await` 키워드를 붙여주면 된다. 이러면 결과값이 잘 나오는데 여기서 중요한 것은 비동기 프로그래밍의 목적이다.

<br>

# 비동기 프로그래밍의 목적

일단 선형적으로 동작하는 synchronous 프로그래밍을 앱에서 이용하게 되면 프리징이 걸릴 위험성이 있다. 왜냐하면 메소드 하나가 계속 종료되지 못한다면 다른 메소드들을 실행할 수 없기 때문이다. 그래서 메소드를 실행해놓고 다른 작업을 할 수 있게 asynchronous 프로그래밍을 하는 것이다. 위의 http 요청을 보내는 것은 언제 해당 웹페이지의 string 값을 가져올지 모르기 때문에 일단 가져올 때까지 기다리긴 하지만 다른 작업들을 실행할 수 있게 해준다. 예를 들어, http 요청 말고도 밑에 다른 메소드들이 있다고 하자.

```dart
import 'dart:html';
import 'dart:async';

void main() {
  	printAsync();
    print("method1");
    print("method2");
    print("method3");
}

void printAsync() async {
  	var res = await HttpRequest.getString("https://baeharam.github.io");
    print(res);
}
```

원래대로라면 해당 웹페이지의 결과값을 출력하고 method 1,2,3을 출력하지만 결과는 아래와 같다.

```html
method1
method2
method3
<!DOCTYPE html>
<html lang="en-us">

  <head>
  <link href="http://gmpg.org/xfn/11" rel="profile" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />

  <!-- Enable responsiveness on mobile devices-->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1" />

  <title>
    
      배하람 &middot; A Jekyll theme
```

맨 위에 method 1,2,3이 보이는 것을 알 수 있으므로 비동기 프로그래밍의 장점이 바로 이것이라고 할 수 있다.

