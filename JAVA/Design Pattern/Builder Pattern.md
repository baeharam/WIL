# Builder Pattern (빌더 패턴)

빌더패턴이란 creational design pattern의 하나로 다양한 종류의 객체를 유연하게 생성하도록 만들어진 패턴이다. 따라서 그 목적은 객체의 복잡한 생성과정과 그 표현을 분리하는 것이며 아래와 같은 문제들을 해결할 때 사용된다.

* **클래스는 어떻게 다양하게 표현되는 복잡한 객체를 생성할 수 있는가?**
* **클래스의 복잡한 객체 생성 과정을 어떻게 단순화할 수 있는가?**

클래스의 생성자에서 직접 객체의 복잡한 생성을 하는 것은 유연하지 않은데, 그 이유는 객체의 생성과정이 복잡한 것과 더불어 다양하게 표현될 수 있기 때문이다. 결국 생성자에서 특정 표현에 대해서만 정의되었다면 다르게 표현되는 객체를 위해 다시 바꿔야 되고 클래스에 종속적이게 된다. 

이러한 문제들에 대해서 다음의 방법으로 문제를 해결한다.

**"복잡한 객체 생성 과정을 가진 클래스는 직접 객체를 생성하는 대신 `Builder`라는 분리된 객체에 객체의 생성을 맡긴다."**

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Builder_UML_class_diagram.svg/1050px-Builder_UML_class_diagram.svg.png">

위의 UML을 보면 Director 클래스는 Builder 객체를 가지며 Builder 객체는 다양한 종류로 구체적인 구현을 가진 ConcreteBuilder 객체를 가지며 이 객체가 Product 객체를 생성한다는 것을 알 수 있다. 이제 위키백과의 예제코드를 통해서 어떻게 동작하는지 알아보자.



# 코드를 보자 (위키백과)

먼저 Product의 역할인 Pizza 클래스이다.

```java
// Product
class Pizza {
    // 피자에는 도우, 소스, 토핑이 있다.
	private String dough = "";
	private String sauce = "";
	private String topping = "";

    // 위의 재료 종류에 따라 피자의 종류가 달라지기 때문에 setter 메소드가 있다.
	public void setDough(String dough) {
		this.dough = dough;
	}

	public void setSauce(String sauce) {
		this.sauce = sauce;
	}

	public void setTopping(String topping) {
		this.topping = topping;
	}
}
```

다음은 여러개의 피자를 만들기 Builder 역할인 PizzaBuilder 클래스이다.

```java
// Builder
abstract class PizzaBuilder {
	protected Pizza pizza;

	public Pizza getPizza() {
		return pizza;
	}

	public void createNewPizzaProduct() {
		pizza = new Pizza();
	}

	public abstract void buildDough();
	public abstract void buildSauce();
	public abstract void buildTopping();
}

```

이 클래스의 역할은 피자의 틀을 생성만 해주고 도우나 소스, 토핑은 아직 정하지 않은 상황이다. 구체적인 구현은 서브클래스에서 맡게 된다. 바로 피자의 종류임을 알 수 있다.

```java
// Concrete Builder
// 하와이안 피자를 만드는 재료들이 들어간다.
class HawaiianPizzaBuilder extends PizzaBuilder {
	public void buildDough() {
		pizza.setDough("cross");
	}

	public void buildSauce() {
		pizza.setSauce("mild");
	}

	public void buildTopping() {
		pizza.setTopping("ham+pineapple");
	}
}

// Concrete Builder
// 스파이시 피자를 만드는 재료들이 들어간다.
class SpicyPizzaBuilder extends PizzaBuilder {
	public void buildDough() {
		pizza.setDough("pan baked");
	}

	public void buildSauce() {
		pizza.setSauce("hot");
	}

	public void buildTopping() {
		pizza.setTopping("pepperoni+salami");
	}
}
```

이렇게 피자를 만들 준비 역할을 하는 Builder 클래스를 상속받는 여러개의 클래스들을 통해서 특정한 종류의 피자를 만들 수 있는 것이다. 이제 피자를 생성시키는 Director 역할의 Cook 클래스를 보자

```java
// Director
// 특정한 종류의 피자를 생성하는 Pizzabuilder의 서브 객체를 받아서 생성과정을 거친다.
class Cook {
	private PizzaBuilder pizzaBuilder;

	public void setPizzaBuilder(PizzaBuilder pizzaBuilder) {
		this.pizzaBuilder = pizzaBuilder;
	}

	public Pizza getPizza() {
		return pizzaBuilder.getPizza();
	}

	public void constructPizza() {
		pizzaBuilder.createNewPizzaProduct();
		pizzaBuilder.buildDough();
		pizzaBuilder.buildSauce();
		pizzaBuilder.buildTopping();
	}
}
```

`constructPizza()` 메소드를 보면 피자를 만드는 절차들이 각각의 메소드로 존재한다는 것을 알 수 있다. 또한 `pizzaBuilder`라는 추상 클래스의 레퍼런스가 존재하여 서브 클래스들의 객체로 정해진다. 따라서, 정해진 서브객체(특정 피자 종류)로 `constructPizza()` 메소드를 호출하면 해당 피자가 생성되는 것이다. 마지막으로 메인 함수를 보자.

```java
// 주어진 특정 종류의 피자가 생성된다.
public class BuilderExample {
	public static void main(String[] args) {
		Cook cook = new Cook();
		PizzaBuilder hawaiianPizzaBuilder = new HawaiianPizzaBuilder();
		PizzaBuilder spicyPizzaBuilder = new SpicyPizzaBuilder();

		cook.setPizzaBuilder(hawaiianPizzaBuilder);
		cook.constructPizza();

		Pizza pizza = cook.getPizza();
	}
}
```

하와이안 피자와 스파이시 피자의 객체를 생성했고 직접 요리를 하는 객체인 cook으로 어떤 피자를 만들지 정하고 `constructPizza()` 메소드로 요리를 한 것이다. 마지막에 `pizza`에 요리가 완료된 피자의 객체가 리턴된다.

UML을 보면 조금 더 명확하게 이해될 것이다.

![default](https://user-images.githubusercontent.com/35518072/39763034-08c26e4a-5317-11e8-99c3-7f459360faff.PNG)