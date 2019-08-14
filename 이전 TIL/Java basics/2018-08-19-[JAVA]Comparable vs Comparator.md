---
layout: post
category: java
title: "[JAVA]Comparable과 Comparator"
---

자바에서 정렬을 할 때 사용하는 메소드는 배열과 컬렉션에 따라서 2가지이다.

```java
Arrays.sort(array); // 배열
Collections.sort(collection); // 컬렉션
```

이 때 `sort()`가 사용하는 비교기준은 int, float, String, char 등과 같은 기본적인 타입이나 클래스에 대해서 일반적으로 생각하는 정렬방식을 적용한다. 문자는 알파벳 순으로, 숫자는 작은 순에서 높은 순으로 (오름차순이라면)... 하지만 특정 인터페이스를 구현해서 정렬 기준을 바꿀 수 있다.

<br>

# Comparable

[Comparable](https://docs.oracle.com/javase/9/docs/api/java/lang/Comparable.html)은 일반적인 정렬기준을 적용하지만 사용자가 정의한 클래스를 정렬할 때 사용하는 것이 보통이다. `compareTo()` 를 구현함으로 해당 객체를 정렬할 수 있다. 예를 들어, Person 클래스가 있다고 하고 나이값 기준으로 정렬한다고 하자. 기본적으로 `sort()`가 사용할 수 있는 비교 메소드에 정의되어 있지 않은 클래스이므로 새로 정의를 해주어야 한다.

```java
class Person implements Comparable<Person> {
    private int age;
    
    Person(int age) { this.age = age; }
    
    @Override
    public int compareTo(Person o) {
        return age-o.age;
    }
}
```

`compareTo()`는 리턴값이 중요하므로 반드시 알아둬야 한다. 현재 클래스 기준으로 말하면, 자신이 더 작을 경우 음수를, 같을 경우 0을, 클 경우 양수를 리턴한다. 그렇기 때문에 `age-o.age`와 같이 리턴을 해주는 것이다.

<br>

# Comparator

[Comparator](https://docs.oracle.com/javase/9/docs/api/java/util/Comparator.html)와 Comparable이 자꾸 헷갈려서 뭐가 다른지 찾아봤더니, 일반적인 정렬기준이 아닌 좀더 복잡하게 정렬기준을 적용하고 싶을 때 사용한다고 한다. `compareTo()` 를 구현하는 Comparable과는 다르게 직접 2개의 객체를 인자로 받는 `compare()` 를 구현해야 한다. Comparator를 implements하는 클래스를 생성하고 해당 메소드를 구현해야 하는 것이기 때문에 변수들로 다른 객체나 값들을 사용할 수 있다는 장점이 있다.

```java
class Check implements Comparator<Person> {

    private String name1;
    private String name2;

    Check(String n1, String n2) {
        name1 = n1;
        name2 = n2;
    }

    @Override
    public int compare(Person o1, Person o2) {
        if(name1.equals(name2)) {
            return o1.getAge() - o2.getAge();
        }
        return o2.getAge() - o1.getAge();
    }
}
```

위와 같이 Comparator를 implements하는 Check라는 클래스를 만들고 이름 2개를 변수로 갖게 한다. 그 다음 비교를 할 때 이름이 같으면 오름차순으로, 이름이 다르면 내림차순으로 정렬하고 있다. 이런식으로 필드값을 이용할 수 있다는 장점이 있다. 물론 보다시피 `compare()` 는 `compareTo()`와 리턴값 기준이 같다. 예제가 구리긴 하지만...어쨌든 좀더 복잡한 정렬할 수 있다는 것이다.

<br>

# 마무리 & 참고

Comparable은 일단 비교를 해야하는 클래스가 implements 해야 하기 때문에 정렬기준이 1개로 정해지지만 Comparator는 여러가지의 정렬기준을 갖고 있는 클래스들을 생성할 수 있기 때문에 조금 더 다양하게 활용할 수 있다. 

* [What is the difference between compare() and compareTo()](https://stackoverflow.com/a/420240/10238194)
* [자바 정렬 Java Comparable Comparator 확실히 알고 넘어가기](http://cwondev.tistory.com/15)