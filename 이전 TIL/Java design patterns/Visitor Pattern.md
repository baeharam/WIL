# 방문자 패턴

<img src="https://user-images.githubusercontent.com/35518072/39336691-dea1c7e0-49f3-11e8-809f-1d5c271d68d8.png" height="400px">

> 출처 : https://commons.wikimedia.org/wiki/File:VisitorDiagram.svg

Visitor pattern은 알고리즘과 객체의 구조를 분리시키는 디자인 패턴이다. 이렇게 보면서 나는 Strategy pattern과 굉장히 유사해서 헷갈렸는데 [Difference between visitor pattern and strategy pattern](https://stackoverflow.com/a/8722819/9437175) 에서 아주 잘 설명해줘서 이해했다. 

> *Strategy pattern은 1개 type의 객체에 여러개의 알고리즘(메소드)을 사용할 경우 적용되는 패턴이며, Visitor pattern은 여러개 type의 객체에 여러개의 알고리즘(메소드)을 사용할 경우 적용되는 패턴이다.*

위의 UML을 보면 실행을 주관하는 Client가 Element와 Visiotr 인터페이스를 가지고 있는데 이들은 각각 Concrete하게 구현된 서브클래스를 갖는다. Visiotr 인터페이스에는 Element 인터페이스의 서브객체 type을 인자로 받는 여러개의 visit 메소드가 overloading 될 수 있고, ConcreteVisitor도 용도에 따라 여러개가 만들어질 수 있으며 overloading 된 visit 메소드를 실제로 구현한다.

Element 인터페이스는 Visitor 인터페이스의 서브객체들을 인자로 받는 accept 메소드가 선언되어있고, ConcreteElement가 accept 메소드를 구현하며 인자로 받은 Visitor 서브객체들을 통해 visit 메소드를 호출한다.



# 코드를 통한 이해(OODP 수업)

```java
interface Element {
	public void accept( Visitor v );
}
```

```java
interface Visitor {
	public void visit( This e );
	public void visit( That e );
	public void visit( TheOther e );
}
```

Element와 Visitor 인터페이스는 보다시피 아주 간단하다. 위에서 설명한 것처럼 Element에는 Visitor의 서브객체들을 인자로 전달받는 accept 메소드가 있고 Visitor에는 여러개의 Element 서브객체들을 인자로 전달받는 visit 메소드가 overloading 되어 있다. 여기서 한가지 의문점은 왜 둘 다 인터페이스인데, visit 메소드만 overloading으로 구현했을까? This, That, TheOther를 하나의 Element로 바꾸면 안될까? 이건 프로그램이 어떻게 동작하느냐에 따라 다를 것 같은데 일단 보도록 하자.

```java
class This implements Element {
    public void   accept( Visitor v ) {
		v.visit( this );
	}
	public String thiss() {
		return "This";
	}
}
```

Element의 서브클래스인 This는 accept 메소드를 구현해서 visit 메소드를 호출하고 thiss 메소드는 "This"라는 String을 리턴한다. That과 TheOther도 이런식으로 구현되어있기 때문에 Visitor의 서브클래스를 보도록 하자.

```java
class UpVisitor implements Visitor {
	public void visit( This e ) {
	   System.out.println( "do Up on " + e.thiss() );
	}
	public void visit( That e ) {
	   System.out.println( "do Up on " + e.that() );
	}
	public void visit( TheOther e ) {
	   System.out.println( "do Up on " + e.theOther() );
	}
}
```

UpVisitor/DownVisitor가 있는데 "do Up on"을 "do Down on"으로 바꾸기만 한것이므로 이것만 보면, overloading된 visit메소드가 인자로 전달받은 객체가 가지고 있는 각각의 메소드를 불러서 리턴받은 String을 "do Up on"과 같이 출력하고 있다. 여기서 드는 의문점은 String을 리턴하는 메소드를 정의하기 보다는 toString 메소드를 이용하면 모든 객체가 똑같은 이름의 메소드이기 때문에 굳이 overloading 하지 않고 부를 수 있을텐데 왜 이렇게 했는지는 수업을 한번 들어봐야 할 것 같다.

```java
class VisitorDemo {
	public static Element[] list = { new This(), new That(), new TheOther() };
	public static void main( String[] args ) {
	   UpVisitor    up   = new UpVisitor();
	   DownVisitor  down = new DownVisitor();
	   for (int i=0; i < list.length; i++) {
	      list[i].accept( up );
	   }
        for (int i=0; i < list.length; i++) {
	      list[i].accept( down );
	   }
	}
}
```

이제 main 메소드가 있는 VisitorDemo를 보면 list라는 Element 서브객체들의 배열을 만들고, UpVisitor/DownVisitor의 객체를 만들어서 Element의 서브객체들로 하여금 accept 메소드로 visit 메소드를 호출하고 있는 것을 알 수 있다. 결과는 다음과 같이 나오는 것을 한눈에 알 수 있을 것이다.

![default](https://user-images.githubusercontent.com/35518072/39337432-da49f6e6-49f7-11e8-8f0a-8833efcb98c3.PNG)