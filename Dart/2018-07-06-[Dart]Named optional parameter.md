---
layout: post
category: dart
title: "[Dart]Named optional parameter"
---

원래 dart에서 알고 있던 선택적 매개변수는 positional optional paramter로 `[]` 로 둘러싸인 매개변수를 말한다. 하지만 flutter를 하다보니 나오는 optional parameter가 전부 named optional parameter라는 것이어서 차이점이 뭔지 정리하려고 한다. [Stackoverflow](https://stackoverflow.com/a/13264231/9437175) 의 설명이 너무 자세하고 깔끔해서 공부하고 정리한다.

# Positional optional parameter의 문제점

예를 들어, 다음과 같은 메소드가 있다고 하자.

```dart
void plus(int a, [int b = 0, int c = 0]){
    print(a + b + c);
}
```

b와 c는 optional parameter임으로 생략하고 `plus(3)`과 같이 호출할 수 있다. 하지만 만약 a와 b의 값만 주고 싶다면 어떻게 해야 될까? `plus(3, 5)`와 같이 쓸 수 있을까? 불가능하다. 5가 b인지 c인지 구분할 수 없기 때문이다.



# Named optional parameter

말 그대로 이름을 가진 optional parameter로 가독성까지 좋아지기 때문에 일석이조의 효과를 얻을 수 있다.

```dart
void plus(int a, {int b = 0, int c = 0}){
    print(a + b + c);
}
```

호출할 때 이름을 명시해야 하기 때문에 `plus(3, b: 5)`와 같이 사용할 수 있다. 

> 주의! 한 메소드에서 positional/named optional parameter를 둘 다 사용할 수는 없다.