# ArrayList 배열은 만들 수 없다.

ArrayList로 그래프를 만들려고 ArrayList배열을 생성하려 했으나 에러가 뜨길래 왜 안돼지 하고 있었는데  [다음](https://stackoverflow.com/a/8559102)을 참조해서 이해할 수 있었다. ArrayList의 배열은 만들 수 없고, 중첩해서 사용해야 한다는 것이다.

```java
ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(10);
```

댓글에선 List가 더 general하기 때문에 ArrayList가 가지는 API와 혼동하지 않아서 List를 사용하는 것이 더 좋다고 한다.

```java
List<List<Integer>> graph = new ArrayList<List<Integer>>(10);
```



# Scanner로 char 받기

[참고](https://stackoverflow.com/a/13942707)

```java
char c = reader.next().charAt(0);
char c = reader.findInLine(".").charAt(0); // 패턴찾는 메소드
char c = reader.next(".").charAt(0);
```

두번째, 세번째에서 인자로 점(.)을 전달하는데 정규표현식(regex)으로 문자 하나를 의미한다. 두 번째는 그냥 문자 하나를 패턴으로 가지는 것을 찾고 0번째 인덱스 값을 리턴하기 때문에 문자 1개를 입력받을 수 있다. 세번째는 패턴을 인자로 받아 정확히 그 값과 일치할 경우 해당 문자열을 리턴받는다. 이것도 문자 하나를 가리키는 정규식을 인자로 받음을 알 수 있다.



# 배열 정렬하기

```java
Arrays.sort(array);
```

너무 간단하지만 까먹으니 정리해둔다.



# 자음/모음 확인하기

```java
"aeiou".indexOf('a');
```

문자 'a'가 있는 인덱스를 반환하는 것으로 모음인지 확인할 수 있다. 실패하면 -1을 리턴하므로 자음까지 확인 가능.



# Wrapper Class

[참고](http://qr.ae/TUIcV1)

Primitive type들을 객체로 표현하기 위한 것으로 primitive type을 클래스로 감싸주기 때문에 wrapper class라고 한다. 예를 들어 int는 Integer로, double은 Double로 만들 수 있다. JDK 1.5 전엔 명시적인 변환이 필요했지만 JDK 1.5부터 자동변환(autoboxing)이 되었다. 하지만 이펙티브 자바에선 자동변환을 하게 되면 위험의 여지가 있으므로 명시적 변환을 하라고 권유한다.



# Wildcard와 Type Parameter

[참고](http://www.angelikalanger.com/GenericsFAQ/FAQSections/TypeArguments.html#FAQ203)

제네릭을 사용할 때 와일드카드와 타입 파라미터를 모두 사용할 수 있는데 계속해서 어떤게 다른지 헷갈렸다.

* 와일드카드는 1가지 상한만 가능하지만 타입 파라미터는 여러개의 상한이 가능하다.

* 와일드카드는 상한/하한을 가질수도 있지만 타입 파라미터는 상한만 가질 수 있다.



# Varargs

[자바 공식 문서](https://docs.oracle.com/javase/1.5.0/docs/guide/language/varargs.html)

jdk 5.0에 추가된 것으로 variable arguments의 약자로 varargs라 부른다. 가변인자로 정해지지 않은 개수의 매개변수에 아주 유용하다. 예전에 여러개의 인자를 넘기기 위해선 배열을 사용했지만 유연하게 varargs를 사용할 수 있다. 문서에서 제시하는 예제를 보면서 이해해보자.

```java
public class Test {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;
        for (String className : args) {
            try {
                Class c = Class.forName(className);
                c.getMethod("test").invoke(c.newInstance());
                passed++;
            } catch (Exception ex) {
                System.out.printf("%s failed: %s%n", className, ex);
                failed++;
            }
        }
        System.out.printf("passed=%d; failed=%d%n", passed, failed);
    }
}
```

프로그램 자체는 굳이 이해할 필요는 없고 varargs를 언제 사용하는지만 보면 된다. 여기서 매개변수가 varargs로 되어있는 함수는 3가지로 `getMethod()`와 `invoke()` 그리고 `printf()`이다. 원래 명시적으로 배열을 선언해서 넘겨주어야 하지만 단순 값을 넘겨주고 있다. 실제적으론 컴파일러가 배열을 생성하기 때문에 배열과 동일한 것으로 본다. ([참고](https://stackoverflow.com/questions/2426455/javas-varargs-performance))



# Underscores in Numeric Literals

[자바 공식 문서](https://docs.oracle.com/javase/7/docs/technotes/guides/language/underscores-literals.html)

> This feature enables you, for example, to separate groups of digits in numeric literals, which can improve the readability of your code. 

jdk 7.0에 나왔고 단순히 숫자를 가독성있게 표기하기 위한 방법으로 _를 사용하여 숫자를 나타낼 수 있다. 예를 들어, 1억을 표시하고 싶은데 자리수를 콤마로 보통 표시하니까 콤마를 _로 바꿔서 나타낼 수 있는 것이다.

```java
int a = 100_000_000;
```


# CharSequence vs String

계속 헷갈렸는데 CharSequence는 인터페이스로 String보다 상위 개념이다. [Stackoverflow](https://stackoverflow.com/questions/1049228/charsequence-vs-string-in-java)에 따르면 안드로이드가 이걸 권유하는 이유는 StringBuffer와 같은 것도 인자로 넘길 수 있도록 음.. 그니까 유연하게 할려는 것이라고 한다. 그림을 보면 더 쉽게 이해할 수 있을 것이다. (출처는 위 링크)

<img src="https://i.stack.imgur.com/PIFk9.png">