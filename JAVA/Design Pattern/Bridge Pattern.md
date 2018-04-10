# 브릿지 패턴

예를 들어서 생각해보자. 어떤 도형을 그리는 API 인터페이스가 있고 모든 도형 클래스는 Shape이라는 인터페이스를 구현하여 공통적인 기능을 가진다. 그러나 도형을 그리는 방식은 다르게 하고 싶다고 하면, 어떻게 해야 될까? 기본적으로 생각해본다면 그리고 싶은 도형클래스를 API 인터페이스를 각각 다르게 구현하는 클래스에서 상속하여 그리면 된다. 결국 다음과 같이 동작하게 되는 것이다.

**Shape 인터페이스 -> 임의의 도형 클래스 - > API 인터페이스에 대한 임의의 API 클래스** 

하지만 이는 도형이 굉장히 많아질 경우 각 도형을 그리는 API 클래스가 다양하기 때문에 상당히 복잡해지고 유지보수가 어려워진다.

이런 어려움을 다음과 같은 방식으로 해결하는 것이 바로 브릿지 패턴이다.

**Shape 인터페이스 -> 임의의 도형 클래스 : 아래의 API클래스를 사용!**

**API 인터페이스 -> 임의의 API 클래스**

이렇게 리팩토링하면 연속적인 상속관계를 이용하지 않고도 임의의 도형 클래스 자체에서 그리는 방식을 다양하게 할 수 있고 단순히 API클래스의 기능만 바꿔주면 되기 때문에 유지보수가 상당히 간결해지는 것이다.

![bridge_uml_class_diagram svg](https://user-images.githubusercontent.com/35518072/38529666-4b750f0e-3ca1-11e8-8bb1-bd1987b29eb1.png)

위키백과에서 가져온 브릿지 패턴의 UML인데 Abstraction과 Implementation 부분이 확실히 나뉘어져 있는 것을 알 수 있고 각각은 또 2개의 다른 부분으로 나뉘어진 것을 알 수 있다. 이제 이게 어떤 말인지 코드를 보면서 살펴보자.



## 위키백과의 코드

```java
// specification(abstraction)
interface Shape {
   public void draw();                         // low-level
   public void resizeByPercentage(double pct);     // high-level
}
```

abstraction 부분인 Shape클래스는 최종적으로 실행할 기능을 단순히 인터페이스로 보여준다.

```java
// Refined abstraction
// DrawingAPI의 implementation이 필요하다.
class CircleShape implements Shape {
   private double x, y, radius;
   private DrawingAPI drawingAPI;
   public CircleShape(double x, double y, double radius, DrawingAPI drawingAPI) {
       this.x = x;  this.y = y;  this.radius = radius;
       this.drawingAPI = drawingAPI;
   }
 
   // low-level i.e. Implementation specific
   public void draw() {
        drawingAPI.drawCircle(x, y, radius);
   }
   // high-level i.e. Abstraction specific
   public void resizeByPercentage(double pct) {
        radius *= pct;
   }
}

```

이제 Shape 인터페이스를 구현하는 Refined abstraction인 CircleShape 클래스이다. 

처음에 왜 이게 implementation이 아니고 abstraction인지 헷갈렸는데 여기서 DrawingAPI 인터페이스의 객체를 사용할 때

아직 구현이 안되었기 때문이다. 그래서 Shape을 구현하긴 했지만 부분적으로는 abstraction인 것이다.

```java
// Implementor
interface DrawingAPI {
    public void drawCircle(double x, double y, double radius);
}
 
// Concrete Implementation
class DrawingAPI1 implements DrawingAPI {
   public void drawCircle(double x, double y, double radius) {
        System.out.printf("API1.circle at %f:%f radius %f\n", x, y, radius);
   }
}
class DrawingAPI2 implements DrawingAPI {
   public void drawCircle(double x, double y, double radius) {
        System.out.printf("API2.circle at %f:%f radius %f\n", x, y, radius);
   }
}
```

이제 DrawingAPI1과 DrawingAPI2가 구체적으로 인터페이스를 구현하여서 Concrete implementation이 되고 DrawingAPI는 Implementor가 된다.

뭔가 복잡해 보이지만 단순하게 생각하면 된다.

원 모양을 그리고 싶은데 2가지 방법으로 그리고 싶어서 다음의 단계를 진행한 것이다.

**1) 도형이 기능할 수 있는 specification 설정 : abstraction**

**2) 도형 중에 원을 사용하고 싶음 : refined abstraction**

**3) 원 모양을 그리고 싶음 : implementor**

**4) 그리는 방법을 다양하게 하고 싶음 : concrete implementation**