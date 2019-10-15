# Nested Class(중첩 클래스)

메멘토 패턴을 공부하다가 Originator 클래스가 내부에 Memento 클래스를 정의하고 있기에 처음봐서 찾아봤더니 nested class라고 불리는 중첩 클래스였다. 오라클에서 제공하는 튜토리얼 문서를 정리하려고 했으나 스택오버플로우의 글이 더 좋은 것 같아서 이걸 번역하는 것으로 정리를 대체하고자 한다. ([링크](https://stackoverflow.com/questions/70324/java-inner-class-and-static-nested-class))

## 중첩 클래스를 사용하는 이유

**조직화** : 클래스가 다른 어떤 context에서도 쓰이지 않을 경우 다른 클래스의 namespace로 분류하는 것이 좋아 보인다.

**접근** : 중첩 클래스는 그것이 포함하고 있는 클래스들의 변수/필드에 대하여 특별한 접근권한을 가진다. (inner 혹은 static, 중첩 클래스의 유형에 따라 다르다.)

**편리함** : 모든 새로운 타입에 대해서 새로운 파일을 만드는 것은 고통스럽고 특히 하나의 context에서만 쓰이는 경우엔 더욱 그렇다. 따라서 중첩 클래스를 사용하면 편리하다.

## 중첩 클래스의 유형

* static class : 다른 클래스의 static 멤버로 선언된 중첩 클래스
* inner class : 다른 클래스의 instance 멤버로 선언된 중첩 클래스
* local inner class : 다른 클래스의 instance 메소드 안에 선언된 중첩 클래스
* anonymous inner class : local inner class와 비슷하지만 일회용 객체를 반환하는 클래스

## Static Classes

Static 클래스는 중첩 클래스가 포함하고 있는 instance와는 아무런 관련이 없기 때문에 가장 이해하기 쉽다.

Static 클래스는 다른 클래스의 static 멤버로 선언된 클래스이며 다른 static 멤버와 동일하게, 포함하는 클래스를 namespace로 사용하는 행거일 뿐이다. 예를 들어, `Goat` 라는 static 클래스는 `Rhino` 클래스 안에 선언되어있고, `Rhino`는 package `pizza`에 있다.

```java
package pizza;
public class Rhino{
    ...
        public static class Goat{
            ...
        }
}
```

솔직히, static 클래스는 패키지로 인해 이미 namespace가 나뉘었기 때문에 가치가 없다. 사용하는 유일한 이유는 포함하는 클래스의 private static 멤버에 접근할 수 있기 때문이지만 그렇게 설득력 있지는 않다.