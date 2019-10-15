---
layout: post
category: dart
title: "[Dart]typedef와 underscore parameter"
---

Dart에서의 typedef는 기존에 알던 typedef와는 다르게 함수의 인터페이스를 정의하는 키워드이다. 예를 들어, typedef를 하지 않고 어떤 함수 형태를 다른 함수에 할당한다고 생각해보자.

<iframe src="https://dartpad.dartlang.org/0cbdfd67eca1814a01f439f5f52606b9" width="800px" height="500px"></iframe>

<br>

함수 f의 타입은 원래 Object 객체를 2개 받아서 int형을 리턴하지만 Function 객체로 받기 때문에 compare가 Function이라는 것은 알 수 있지만 어떤 타입인지는 알 수 없게 된다. 이런 문제 때문에 typedef를 사용한다.

<br>

<iframe src="https://dartpad.dartlang.org/40863067bccd390b42232d37982ffb48" width="800px" height="500px"></iframe>

<br>

따라서 위와 같이 사용할 수 있으며 compare 함수는 typedef로 정의된 Compare이기도 하면서 Function도 되는 것이다. 이제 짤막하게 underscore parameter에 대해 알아보자. 이건 함수의 매개변수로 _를 전달하는 것인데 해당 함수 인터페이스에 정의된 매개변수를 받기는 하지만 사용하지 않는다는 의미이다. 따라서 위 코드에서 sort 함수를 정의할 때 매개변수를 (\_, \_\_) 로 정의하고 compare에 할당해도 상관없는 것이 된다. 

생각해보니 당연한 것이 dart에서 변수의 시작은 $, _ 아니면 문자로 시작해야 하니까 그냥 사용안한다는 측면에서 _를 써주는 것이 맞다. 보니까 _를 \$로 바꿔도 상관이 없다...ㅋㅋㅋ

<br>

## 출처

* https://stackoverflow.com/a/25517120/10047925
* https://www.dartlang.org/guides/language/language-tour#typedefs
* https://stackoverflow.com/a/33147014/10047925

