# 자바 기초

## Primitive Types

* boolean : true/false
* char : 16비트 유니코드
* byte : 8비트 integer
* short/int/long : 16/32/64 비트 integer
* float/double : 32/64 비트 floating-point (IEEE-754)



## Reference Types

자바에는 class와 array가 레퍼런스 타입이며 call by value가 아닌 call by reference로 전달된다. 다른 글을 보다가 이런 call by reference가 적용되는 것도 call by value라고 하는데 이 개념은 나중에 좀 더 알아보자. 또한 모든 class는 Object class의 subclass이다.



## Widening and Narrowing

```java
int i = 10;
long m = 10000L;
double d = Math.PI;
i = (int)m;
m = i;
m = (long)d;
d = m;
```

위 코드처럼 넓은 범위의 타입에서 좁은 범위로 캐스팅하는 것을 Narrowing이라 하고 좁은 범위의 타입에서 넓은 범위로 캐스팅하는 것을 Widening이라고 한다. **Widening을 할 때는 캐스팅이 필요없다.** 이걸 **Automatic Widening**이라고 한다.



## Escape Sequence

1. \n : NEW LINE 추가
2. \t : TAB 추가
3. \b : BACKSPACE 추가
4. \r : CARRIAGE RETURN 추가
5. \f : FORMFEED 추가
6. \' : SINGLE QUOTE CHARACTER 추가
7. \" : DOUBLE QUOTE CHARACTER 추가
8. \\\ : BACKSLASCH CHARACTER 추가 



## Array

```java
int[] ia = new int[3];
int ia[] = new int[3];
int[] ia = {1, 2, 3};
```

위와 같이 array가 표현될 수 있으며 bound-check는 dynamic checking이다. 만약 정해진 인덱스의 범위를 넘는 값을 참조했다면 **ArrayIndexOutOfBoundsException**이 발생한다.



## toString() 메소드

toString()은 클래스에 기본적으로 있는 함수이다. 객체를 출력한다고 했을 때 기본적으로 다음과 같이 적용된다. 

>Returns a string representation of the object. In general, the toString method returns a string that "textually represents" this object. The result should be a concise but informative representation that is easy for a person to read. It is recommended that all subclasses override this method. The toString method for class Object returns a string consisting of the name of the class of which the object is an instance, the at-sign character `@', and the unsigned hexadecimal representation of the hash code of the object. In other words, this method returns a string equal to the value of:
>
>```java
>getClass().getName() + '@' + Integer.toHexString(hashCode())
>```

[Object.toString()](https://docs.oracle.com/javase/6/docs/api/java/lang/Object.html#toString%28%29)에서 이렇게 정의하고 있는 것이다. 따라서 이런 이해할 수 없는 것들을 출력하지 않고 자신만의 정의된 객체를 표현하는 문장을 출력하고 싶다면 함수를 재정의하면 된다.

```java
class Point{
    int x;
    int y;
    public String toString(){
        return "x: "+x+" y : "+y;
    }
}
System.out.println(" Point --> " + p);
```

이런 방식으로 재정의하면 자신만의 문장으로 객체를 표현할 수 있다.

## Method와 Field의 Modifers

1. Method와 Field 둘 다 되는 경우

public, protected, private, static, final

2. Method만 되는 경우

abstract, synchronized, native

3. Field만 되는 경우

volatile, transient

### synchronized/native/volatile/transient

* synchronized

synchronized는 여러개의 쓰레드가 하나의 메소드에 접근하려고 할 때 한 쓰레드가 그 메소드를 사용하고 있을 경우 다른 메소드의 접근을 막는 modifier이다.

* native

native는 JAVA로 작성된 코드가 아닌 다른 언어로 작성된 코드를 JAVA에서 사용할 때 쓰는 modifier로 JNI(Java Native Interface)를 통해 implement된다.

* volatile

여러개의 쓰레드가 하나의 필드 값을 참조할 때 항상 최신으로 업데이트 된 값을 보게 해주는 키워드이다. [stackoverflow](https://stackoverflow.com/a/106641/9437175)에서 나온 구절을 인용하면,

> *“… the volatile modifier guarantees that any thread that reads a field will see the most recently written value.”* **- Josh Bloch**

Prevent the compiler from optimizing a volatile variable. (OODP 수업)

* transient

native와는 반대로 JAVA를 다른 곳에서 사용할려면 직렬화(Serializing)을 거쳐야 한다. 하지만 그렇게 직렬화 될 때 transient 키워드가 붙은 필드 값은 직렬화시키지 않는 개념이다.

이렇게 익숙하지 않은 키워들에 대해 정리해봤는데 너무 간단하게 설명했기 때문에 나중에 다시 한 번 자세히 다뤄보기로 하자.
