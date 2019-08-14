---
layout: post
category: java
title: "[JAVA]Exception과 RuntimeException"
---

[Exception](https://docs.oracle.com/javase/9/docs/api/java/lang/Exception.html) 클래스는 **Checked Exception**이라고 불리며 컴파일 타임에 발생할 수 있는 예외 클래스이다. 어떤 메소드가 이 예외 객체를 던진다면 try~catch 블록으로 감싸서 해결하거나 아니면 해당 클래스가 예외 클래스를 throws(던지기) 해야 한다.

[RuntimeException](https://docs.oracle.com/javase/9/docs/api/java/lang/RuntimeException.html) 클래스는 **Unchecked Exception**이라고 불리며 런타임에 발생할 수 있는 예외 클래스이다. 어떤 메소드가 이 예외 객체를 던져도 따로 처리를 해주지 않아도 된다.

```java
public class ExceptionEx {
    public static void main(String[] args) {
        ExceptionEx.exception1(); // 컴파일 에러
        ExceptionEx.exception2();
    }

    private static void exception1() throws Exception {
        System.out.println("Checked Exception!");
    }

    private static void exception2() throws RuntimeException {
        System.out.println("Unchecked Exception!");
    }
}
```

`exception1()`은 Exception을 throws 하기 때문에 컴파일 할 때 에러가 발생하지만 `exception2()`는 RuntimeException을 throws 하기 때문에 컴파일이 가능하다.

<br>

이걸 공부하면서 throw와 throws의 차이가 헷갈려서 정리를 하고자 한다. **throw는 메소드 내부에서 예외를 발생시키는 것이고 throws는 메소드에서 예외가 발생할 경우 해당 메소드를 호출한 호출대상(caller)에게 예외 처리를 맡기는 것이다.** 위 예제에서 만약 `exception1()`이 내부에서 Checked Exception을 발생시킨다면 throws를 해줄 경우 `main()`에서 예외 처리를 해야 하고 throws를 안해줄 경우는 `exception1()`에서 처리를 해야 한다. 이건 Unchecked Exception의 경우도 마찬가지다.

```java
public class ExceptionEx {
    public static void main(String[] args) {
        ExceptionEx.exception1();
    }

    private static void exception1() throws Exception { // caller에게 예외처리 맡김
        System.out.println("Checked Exception!");
        throw new Exception(); // 예외 발생
    }

    private static void exception2(){
        System.out.println("Unchecked Exception!");
    }
}
```

<br>

## 출처 및 참고

* [위키독스 - 예외처리](https://wikidocs.net/229)

