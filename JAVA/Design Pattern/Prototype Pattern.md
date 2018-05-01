# Prototype Pattern (원형 패턴)

![618px-prototype_pattern_zp svg](https://user-images.githubusercontent.com/35518072/39462366-eb35c0e6-4d4b-11e8-8656-c6bc3064a4ee.png)

> 생성할 객체들의 타입인 프로토타입인 인스턴스로부터 결정되도록 하며, 인스턴스는 새 객체를 만들기 위해 자신을 복제(clone) 하게 된다.
>
> *위키백과(사진,정의)*

다시 말해서 계속해서 객체(=인스턴스)를 생성하는 것이 프로그램의 로직이라면, 생성 비용이 꽤나 크기 때문에 객체를 생성해놓고, 그 객체에 관련된 기본 기능들이 필요할 때마다 복제(clone)하는 패턴으로 CPU time을 줄일 수 있는 것이 특징이다. 메모리 공간을 절약하는 것으로 오해할 수 있는데, 생성 비용을 줄임으로서 효율성을 높이는 것이다. 복제를 하는 것이 핵심이기 때문에 clone() 메소드가 반드시 필요하니 이 메소드에 대해서 알아보자.

Object 클래스의 메소드이며 다음과 같이 선언되어 있다. ([clone() 레퍼런스](https://docs.oracle.com/javase/9/docs/api/java/lang/Object.html#clone--))

```java
protected Object clone() throws CloneNotSupportedException
```

clone() 메소드를 사용하는 클래스는 Cloneable 인터페이스를 구현해야 하며 구현하지 않을 경우 위와 같은 예외가 던져진다. 기본적으로 clone() 은 shallow copy이며 <u>deep copy를 구현하기 위해선 override</u>해야 한다. **Java에서는 shallow copy가 reference, 즉 주소의 복사이며 deep copy가 객체 자체의 복사라고 한다.**



# 코드를 통한 이해 (OODP 수업)

```java
public class Car implements Cloneable {
  private String str;
  private Date date;

  public Car(String str) {
     this.str = str;
  }
  public String getStr(){
    return str;
  }
  public void setDate(Date date) {
    this.date = new Date(date.getTime());
  }
  public Date getDate() {
    return date;
  }
  @Override
  public Object clone() throws CloneNotSupportedException {
    Car tmp = (Car)super.clone();
    return tmp;
  }
}
```

먼저 Car 클래스를 보면 Cloneable 인터페이스를 implements 하고 있는 것을 볼 수 있고 clone() 메소드를 overriding 하고 있기 때문에 여기서의 clone()은 deep copy로 값까지 복사한다는 것을 알 수 있다.

기능은 [Date()](https://docs.oracle.com/javase/9/docs/api/java/sql/Date.html#Date-int-int-int-) 객체에 값을 정하고 리턴받는 형식이다. 이게 어떻게 prototype pattern이 되는지 보도록 하자.

```java
public class Test {
public static void main(String[] args) {
    Car car = new Car("캡티바");
  
    try{
      Car car1 = (Car)car.clone();
      car1.setDate(new Date(2015,0,1));
      Car car2 = (Car)car.clone();
      car2.setDate(new Date(2014,2,1));
      
      System.out.println(car1.hashCode());
      System.out.println(car2.hashCode());

      System.out.println(car1.getDate());
      System.out.println(car2.getDate());
      
      Car car3=car;
      System.out.println(car.hashCode());
      System.out.println(car3.hashCode());
      
    }catch(CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }
}
```

 Car의 객체인 car1과 car2는 override 된 clone() 메소드를 통해서 객체를 복사했기 때문에 deep copy이며 각각 다른 memory address를 가진다. 하지만 car와 car3는 shallow copy이므로 같은 memory address를 가지는 것을 알 수 있다. 알 수 있는 방법은 [hashCode()](https://docs.oracle.com/javase/9/docs/api/java/util/Arrays.html#hashCode-java.lang.Object:A-) 메소드를 이용해서 각 객체의 hash code를 보면 된다.

여기서 중요한 점은 다음과 같다.

* Prototypical instance는 car 객체 (=캡티바) 이다.
* clone() 메소드를 사용하기 위해선 try~catch 문을 써주어야 한다.
* hashCode() 메소드로 shallow/deep copy를 구별할 수 있다.

![default](https://user-images.githubusercontent.com/35518072/39463199-7a1db37c-4d51-11e8-949f-74d2132f7b22.PNG)

결과는 다음과 같이 나오고 hash code를 보면 알 수 있듯이, car와 car3는 같은 memory address를 공유하며, car1과 car2는 다른 address를 가진다는 것을 알 수 있다.

