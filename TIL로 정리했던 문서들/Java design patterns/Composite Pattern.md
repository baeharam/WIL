# Composite Pattern(컴포지트 패턴)

**"컴포지트 패턴이란 객체들의 관계를 트리 구조로 구성하여 부분-전체 계층을 표현하는 패턴으로, 사용자가 단일 객체와 복합 객체(Composite) 모두 동일하게 다루도록 한다. (위키백과)"**

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Composite_UML_class_diagram_%28fixed%29.svg/900px-Composite_UML_class_diagram_%28fixed%29.svg.png" height="350px">

* **Component (인터페이스)**
  * Composite 하나를 포함하는 모든 component의 추상화이다.
  * Composite에서 객체들을 위한 인터페이스를 정의한다.
* **Leaf (단일 객체)**
  * Component의 모든 메소드를 구현한다.
  * Composite에서 leaf 객체들을 나타낸다.
* **Composite (복합 객체)**
  * 자식들을 다루는 메소드를 제공한다.
  * 일반적으로 component의 메소드를 자식들에게 위임함으로서 구현한다.

쉽게 예를 들자면, 단일 객체와 복합 객체를 동일하게 다룬다는 것은 파일이라는 단일 객체가 있으면 파일의 집합인 디렉토리를 복합 객체라고 할 수 있다. 그러나, 디렉토리도 특정 디렉토리 안에 속할 수 있기 때문에 동일하게 다뤄질 수 있다는 것이다.



# 코드를 보자 (위키백과)

```java
// Component
interface Graphic{
    public void print();
}
```

먼저 Component 역할을 하는 `Graphic` 인터페이스이다.

```java
// Composite
class CompositeGraphic implements Graphic{
    private List<Graphic> childGraphics = new ArrayList<Graphic>();
    
    public void print(){
        for(Graphic graphic : childGraphics){
            graphic.print();
        }
    }
    // Composite에 추가하는데 composite 자체가 Graphic을 구현하기 때문에
    // 인자로 composite이 전달 될 수 있다.
    public void add(Graphic graphic){
        childGraphics.add(graphic);
    }
    
    // Composite 제거
    public void remove(Graphic graphic){
        childGraphics.remove(graphic);
    }
}
```

`childGraphics`로 component인 `Graphic`을 구현하는 모든 객체들을 저장하고 결국 그걸 가지고 있는 `CompositeGraphic` 클래스가 composite의 역할을 한다고 볼 수 있다.

```java
// Leaf
class Ellipse implements Graphic{
    public void print(){
        System.out.println("Ellipse");
    }
}
```

Leaf 클래스인 `Ellipse`는 단순하게 `Graphic` 인터페이스를 구현하고 있다. 이제 leaf와 composite이 어떻게 동작하는지 보도록 하자.

```java
public static void main(String[] args) {
    //Initialize four ellipses(leaf를 4개 만들었다.)
	Ellipse ellipse1 = new Ellipse();
    Ellipse ellipse2 = new Ellipse();
    Ellipse ellipse3 = new Ellipse();
    Ellipse ellipse4 = new Ellipse();

    //Initialize three composite graphics (composite을 3개만들었다.)
    CompositeGraphic graphic = new CompositeGraphic();
    CompositeGraphic graphic1 = new CompositeGraphic();
    CompositeGraphic graphic2 = new CompositeGraphic();

    //Composes the graphics
    // 먼저 leaf 3개를 모드 composite graphic1에 넣는다.
    graphic1.add(ellipse1);
    graphic1.add(ellipse2);
    graphic1.add(ellipse3);

    // 나머지 leaf 1개를 composite graphic2에 넣는다.
    graphic2.add(ellipse4);

    // 마지막으로 위의 composite 2개, graphic1,graphic2를 graphic에 넣는다.
    graphic.add(graphic1);
    graphic.add(graphic2);

    //Prints the complete graphic (Four times the string "Ellipse").
    graphic.print();
}
```

`graphic.add(graphic1)`을 보면 composite 안에 composite을 넣고 있다. 이걸 통해서 composite도 leaf와 동일하게 다뤄지고 있다는 것을 알 수 있는데, 특히 composite 안에서도 `print()`를 구현하고 있기 때문에 `Graphic`의 객체 기준으로 `print()`를 하게 되면 composite과 leaf모두 똑같이 기능하게 된다.



# 언제 사용하는가?

* 객체의 부분-전체 구조를 표현하고 싶을 때
* 클라이언트로 하여금 단일 객체와 복합 객체의 차이점을 못 느끼게 하고 싶을 때

가장 흔하게 사용되는 곳은 그래픽 시스템으로 그래픽 창은 이미지나 텍스트와 같은 다른 그래픽 요소들을 포함할 수 있는 경우가 있다. 또한 위에서 말했던 것처럼 디렉토리 안에 디렉토리가 들어갈 수 있는 것이 있고 Java의 라이브러리에는 다음과 같은 것들이 있다.

* [`java.awt.Container#add(Component)`](https://docs.oracle.com/javase/8/docs/api/java/awt/Container.html#add-java.awt.Component-)
* [`javax.faces.component.UIComponent#getChildren()`](https://docs.oracle.com/javaee/7/api/javax/faces/component/UIComponent.html#getChildren--)



# 참고

* [위키백과](https://en.wikipedia.org/wiki/Composite_pattern)
* [컴포지트 패턴](http://www.incodom.kr/%EC%BB%B4%ED%8F%AC%EC%A7%80%ED%8A%B8_%ED%8C%A8%ED%84%B4)
* [컴포지트 패턴(Composite Pattern)](http://jdm.kr/blog/228)
* [When should I use composite design pattern?](https://stackoverflow.com/a/5338862/9437175)
* [Examples of GoF Design Patterns in Java's core libraries](https://stackoverflow.com/a/2707195/9437175)