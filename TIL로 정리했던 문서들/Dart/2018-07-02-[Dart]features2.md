---
layout: post
category: dart
title: "[Dart]특이한 특징들2"
---

# 인터페이스

Dart에선 모든 클래스가 implicit 하게 인터페이스로 취급되기 때문에 어떤 클래스를 상속하지 않고 그 클래스의 메서드나 변수를 사용하고 싶다면 간단하게 implements하면 된다.

```dart
class A{
    void p(){
        print("haram is good");
    }
}

class B implements A{
    void p(){
        print("haram is bad");
    }
}

void main(){
    new B().p(); // haram is bad
}
```

물론 abstract class로 생성한다음 implements 할수도 있다.



# noSuchMethod()

해당 객체에 없는 메서드를 부를 경우 기본값으로는 NoSuchMethodError를 발생시키지만 이 메서드를 오버라이딩 할 경우 정해진 내용이 호출된다. 단, 런타임시에 결정되는 dynamic이 적용된 부분이 있어야 한다. 예를 들어, abstract class의 내용을 구현해서 런타임에 바인딩되는 경우이다.

```dart
abstract class A{
    foo();
}
class B implements A{
    @override
    void noSuchMethod(Invocation invocation){
        print("There is no such method like ${invocation.memberName}");
    }
}
```

`invocation.memberName`은 Invocation 객체의 getter 메서드로 symbol을 리턴하기 때문에 "There is no such method like Symbol("foo")"라는 메시지가 출력된다.



## enum에 index getter가 있다.

enum은 별로 특별할 게 없었지만 index를 가져오는 메서드가 있었다.

```dart
enum Color { red, green, blue }
if(Color.red.index==0) print("really red!");
```



# mixin

다른 언어에서도 몇번 본 것 같은데 여러개의 클래스 속성을 사용할 수 있는 개념이다. 하지만 상속을 해야 한다는 조건이 있으므로 그냥 사용하기 위해선 Object 클래스를 상속하면 된다. with라는 키워드를 통해 사용한다.

```dart
class A {
    String a="haram";
}
class B {
    String b=" is good";
}
class C extends Object with A,B {
    void p() => print(a+b);
}
void main(){
    new C().p(); // haram is good
}
```

그러면 여기서 나올수 있는 질문이 모든 클래스가 인터페이스로 여겨지고 여러개의 인터페이스를 구현할 수 있는데 굳이 mixin을 써야 하는 이유는 뭘까? 인터페이스를 implements 할 경우 인터페이스에 있는 메서드를 구현해야 하지만 mixin을 쓰면 단지 해당 클래스가 가진 메서드와 변수를 공유하는 것이다. 또한 인터페이스를 implements 할 경우 해당 클래스의 객체 한 개만 생성하지만 mixin을 쓸 경우 해당 클래스의 객체와 mixin 되는 객체가 전부 생성된다. 자세하게 설명된 것은 [Stackoverflow](https://stackoverflow.com/a/45903671)를 참조하도록 하자.