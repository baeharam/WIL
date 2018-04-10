# 어댑터 패턴

어댑터 패턴이란 기존의 코드가 있고 그걸 사용하고 싶은데 사용자의 인터페이스에 맞게 사용하고 싶다면 하나의 어댑터를 만들어서 기존의 코드와 사용자의 인터페이스를 연결시켜주는 개념이다. 

![adapter pattern](https://user-images.githubusercontent.com/35518072/37783953-13b27a0e-2e3a-11e8-86dc-0ac3c25246c4.jpg)

다음과 같이 사용자가 기대하는 인터페이스에 맞게 어댑터를 구현함으로서 기존의 코드를 바꾸지 않고 사용하면서 인터페이스는 맞출 수 있게 된 것이다.

![classadapter](https://user-images.githubusercontent.com/35518072/38547049-29d40c04-3ce9-11e8-9262-0d0f175e9bcb.png)

UML이 없어서 추가한다. 어댑터 패턴은 2가지로 나뉘는데 **Object/Class Adapter Pattern** 이다. 위의 UML은 class adapter pattern으로 Adaptee가 되는 클래스들을 상속함으로 adapter 역할을 하는 클래스를 사용하는 패턴이다. 그렇다면 object adapter pattern은 무엇일까? 살짝 정리하면서 헷갈리기는 하는데 확실히 다른 것을 한번 찾아보자.

![objectadapter](https://user-images.githubusercontent.com/35518072/38547448-100755e6-3cea-11e8-9e5b-3a87ad91801f.png)

Object adapter patter은 adapter 역할을 하는 객체가 adaptee 객체를 포함하여 사용하는 것을 말한다. 즉, 상속이 아닌 것이다. 위 2개의 UML에서 Adapter 부분을 보면 class adapter는 *methodA()* 안에서 *method1()* 과 *method2*() 를 호출하고 있고 object adapter는 *methodA()* 안에서 호출하는 것은 똑같지만 상속을 해주지 않았기 때문에 *adaptee.methodB()* 형식으로 호출해주고 있는 것을 알 수 있다.

OODP 수업에서 배운 것은 Class adapter pattern이다. 상속을 이용해서 메소드 이름으로만 호출한다. 아래의 코드를 통해 알 수 있다.



## 코드를 보자 (OODP 수업 - Yuki)

```java
// 기존코드인 Banner 클래스
public class Banner{
    private String string;
    public Banner(String string){
        this.string=string;
    }
    public void showWithParen(){
        System.out.println("(" + string + ")");
    }
    public void showWithAster(){
        System.out.println("*" + string + "*");
    }
}
```

```java
// 사용자가 원하는 인터페이스
public interface Print{
    public abstract void printWeak();
    public abstract void printStrong();
}
```

```java
// Banner와 Print를 연결시켜주는 어댑터
public class PrintBanner extends Banner implements Print{
    public PrintBanner(String string){
        super(string);
    }
    public void printWeak(){
        showWithParen();
    }
    public void printStrong(){
        showWithAster();
    }
}
```

```java
public class Adaptor{
    public static void main(String[] args){
        Print p = new PrintBanner("Hello");
        p.printWeak();
        p.printStrong();
    }
}
```

어댑터를 만들어 놓았기 때문에 단순히 Print 인터페이스로 함수를 구현하는 어댑터의 객체를 생성하여 원하는 함수를 사용하면 기존의 Banner 클래스를 바꾸지 않고 간단하게 사용할 수 있게 된다.
