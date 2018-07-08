---
layout: post
category: dart
title: "[Dart]Cascaded method와 Method Chaining"
---

https://news.dartlang.org/2012/02/method-cascades-in-dart-posted-by-gilad.html를 참고하면 아주 쉽게 이해할 수 있다.



# Cascaded method

하나의 객체에 여러개의 메소드를 사용할 때 객체를 한번만 쓸 수는 없을까 해서 고안된 것으로 이런 형태를 cascaded method라고 한다. 메소드를 여러번 쓰는 것이기 때문에 리턴값이 없는 메소드에 사용하며 점(.) 2개로 표현한다. 출력하는 메소드가 무지하게 많은 객체가 있다고 하면 다음과 같이 쓸 수 있다.

```dart
void main() {
    A a = A();
    a
        ..printA()
        ..printA()
        ..printA();
}

class A {
    void printA() => print("A!");
}
```

결과는 "A!"가 3번 나온다. 이런식으로 캐스케이딩을 해서 메소드를 호출하면 좀 더 직관적이고 객체를 쓰는 일을 줄일 수 있다.

<br>

# Method chaining

리턴 값이 있는 메소드에 여러개의 메소드를 연결해서 쓰는 방식으로 캐스케이딩과 헷갈릴 수 있지만 리턴 값 유무만 파악한다면 헷갈릴 일은 없을 것 같다.

```dart
void main() {
    A a = A();
    print(
    a
        .chain()
        .chain()
        .chain()
    );
}

class A {
    B chain() => B();
}

class B {
    C chain() => C();
}

class C {
    int chain() => 3;
}
```

결과는 3이다.