# 싱글톤 패턴

어떤 클래스의 객체를 생성할 때 각자 다른 객체를 계속 생성하는 것이 아니라 처음 생성자의 호출에 대해서만 객체를 생성하고 나머지 호출에 대해서는 이미 생성한 객체를 리턴하는 패턴을 말한다. 이 말은 많은 객체가 동일한 객체를 공유하는 것을 말하는데 이것은 static class가 아닌가? 하는 생각이 들고 헷갈려서 뭐가 다른지 찾아보았다.

> 1. singleton 객체는 힙에 저장되고 static 객체는 스택에 저장된다.
> 2. singleton 객체는 복사할 수 있지만 static 객체는 복사할 수 없다.
> 3. singleton 클래스는 Object-Oriented principles를 따르지만 static 클래스는 그렇지 않다.
> 4. singleton 클래스로 interface를 구현할 수 있지만 class의 static methods는 불가능하다.

[Difference between static class and singleton pattern?](https://stackoverflow.com/a/12367609/9437175)에서 이해하기 쉬운 글을 따왔다.  다시 말해서, singleton 클래스는 static 클래스와는 다르게 일반 클래스처럼 여겨진다는 것이다. 다른 분의 글을 보면 함께 묶여야 할 functions가 존재한다면 static 클래스로 만들고 그게 아니라 어떤 resources에 대해 single access를 필요로 한다면 singleton 클래스로 만들라고 한다. 아직까지 정확하게 짜본 경험이 없어서 완벽하게 와닿지는 않지만 일단 정의는 이 정도로 정리하자.

## 전통적인 싱글톤 코드

```java
public class Singleton{
    private static Singleton singleton;
    private Singleton(){}
    // static인 singleton 객체가 존재하지 않는 경우에만 생성하고
    // 이미 생성된 경우면 그 객체를 리턴해준다.
    public static Singleton createSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }
}
```

## Yuki의 코드

```java
public class TicketMaker{
    private int ticket = 1000;
    private static TicketMaker singleton = new TicketMaker();
    private TicketMaker(){}
    // 전통적인 코드와는 다르게 TicketMaker의 객체를 생성할 때마다 static객체를 생성한다
    // 이런 방법이 전통적인 방법보다 덜 효율적이라고 생각한다. 과연 진짜 그럴까?
    public static TicketMaker getInstance(){
        return singleton;
    }
    public synchronized int getNextTicketNumber(){
        return ticket++;
    }
}
```

```java
public class Singleton{
    public static void main(String[] args){
        //....
        // TicketMaker클래스로 static메소드를 호출해서 singleton객체를 받아
        // 계속 ticket의 값을 증가시킨다.
        TicketMaker.getInstance().getNextTicketNumber();
        //....
    }
}
```

이외에도 참 많은 싱글톤 패턴의 구현이 있고 멀티쓰레드의 관점에서 볼 때의 코드도 많이 존재하며 이 패턴의 단점을 없앤 구현도 존재한다. 하지만 아직은 초기 단계이니 나중에 더 추가하도록 하자.

