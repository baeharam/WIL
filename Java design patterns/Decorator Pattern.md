# Decorator Pattern(데코레이터 패턴)

> decorator pattern is a design pattern that allows behavior to be added to an individual object, either statically or dynamically, without affecting the behavior of other objects from the same class.

위키백과의 설명을 보면서 이해를 해보면 데코레이터 패턴은 하나의 객체에 정적으로, 혹은 동적으로 기능을 추가해나가는 패턴이며 기능을 추가할 때는 다른 객체에게 영향을 주지 않는다.

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Decorator_UML_class_diagram.svg/600px-Decorator_UML_class_diagram.svg.png" height="350px">

ConcreteDecorator 객체들을 이용하여 ConcreteComponent 객체의 기능을 추가해나가는 방식이라고 보면 된다. 



# 코드를 보자(Yuki)

카페에서 커피를 주문한다고 생각해보면 우유, 스프링클, 휘핑크림 등을 추가할 수 있다. 이걸 데코레이터 패턴으로 하나씩 추가해보는 실습이다. 먼저 첫번째로 Component의 역할을 하는 `Coffee` 클래스이다.

```java
public interface Coffee{
    public double getCost(); // 얼만지 반환
    public String getIngredient(); // 커피에 뭘 추가했는지 반환
}
```

`Coffee` 를 구현하는 클래스는 2가지인데 UML에 있는 것처럼 Decorator와 ConcreteComponent이다. 먼저 ConcreteComponent 역할을 하는 `SimpleCoffee` 클래스를 보자.

```java
public class SimpleCoffee implements Coffee{
    double cost;
    String ingredient;

    // Constructor, 기본값은 1과 Coffee이다.
    public SimpleCoffee(){
        cost = 1;
        ingredient = "Coffee";
    }

    public double getCost(){
        return cost;
    }
    public String getIngredient(){
        return ingredient;
    }
}
```

이제 위 클래스에서 나온 객체들에 옵션을 추가해주는 클래스인 Decorator `CoffeeDecorator` 클래스를 보자.

```java
abstract public class CoffeeDecorator implements Coffee
{
	protected Coffee decoratedCoffee;
	protected String ingredientSeparator;
	
	// Constructor, 이 클래스를 상속하는 모든 서브클래스들의 객체가 생성될 때
	// 그 객체에 추가될 요소가 decoratedCoffee에 저장한다.
    public CoffeeDecorator(Coffee decoratedCoffee){
    	this.decoratedCoffee = decoratedCoffee;
    	ingredientSeparator = ", ";
    }
    
  // 이전에 커피에 추가했던 요소부터 재귀적으로 getCost()와 getIngredient()가 호출된다.
    public double getCost(){
    	return decoratedCoffee.getCost();
    }
    public String getIngredient(){
    	return decoratedCoffee.getIngredient();
    }
}
```

위의 `CoffeeDecorator`는 어떻게 적용될까? 상속하는 서브클래스들 중에 `Milk` 하나만 보자.

```java
public class Milk extends CoffeeDecorator{
    double cost;
    String ingredient;
    
    public Milk(Coffee decoratedCoffee){
        super(decoratedCoffee);
        cost = 0.5;
        ingredient = "Milk";
    }
    public double getCost(){
        return super.getCost() + cost;
    }
    public String getIngredient(){
        return super.getIngredient() + super_ingredientSeparator + ingredient;
    }
}
```

핵심은 바로 `super`를 모든 메소드에 써주어서 재귀적으로 구현하는 것이다. 그러면 커피에 어떤 것들이 추가됬는지 알 수 있고 합한 값 또한 알 수 있다. 마지막으로 메인함수를 보고 끝내도록 하자.

```java
public class Main{
    public static void main(String[] args){
        Coffee sampleCoffee = new SimpleCoffee();
        System.out.println("Cost: " + sampleCoffee.getCost() 
                           + " Ingredient: " + sampleCoffee.getIngredient());

        sampleCoffee = new Milk(sampleCoffee);
        System.out.println("Cost: " + sampleCoffee.getCost() 
                           + " Ingredient: " + sampleCoffee.getIngredient());

        sampleCoffee = new Sprinkles(sampleCoffee);
        System.out.println("Cost: " + sampleCoffee.getCost() 
                           + " Ingredient: " + sampleCoffee.getIngredient());

        sampleCoffee = new Whip(sampleCoffee);
        System.out.println("Cost: " + sampleCoffee.getCost() 
                           + " Ingredient: " + sampleCoffee.getIngredient());
    }
}
```

처음에 `sampleCoffee`라는 이름의 객체를 만들고 계속해서 `Milk`, `Sprinkles`, `Whip` 이라는 옵션을 추가해나가고 있다. 어떻게 동작하는지가 중요하기 때문에 `sampleCoffee = new Milk(sampleCoffee);` 부분을 보면서 과정을 살펴보도록 하자.

1. `sampleCoffee` 의 객체가 `Milk`의 객체를 할당받았다. 생성자에 이전 객체를 전달해서 `decoratedCoffee`에 등록을 한다.
2. `sampleCoffee.getCost()`를 하게 되면 `Milk`의 `getCost()`가 호출되지만 안에서 `super.getCost()`가 다시 호출되고 현재 `sampleCoffee`의 cost와 더해서 리턴한다.
3. `getIngredient()`도 비슷하게 작동한다.
4. 이런식으로 꼬리에 꼬리를 무는 방식인데 1번째 `sampleCoffee`는 `Coffee`의 객체이므로 `decoratedCoffee`가 없지만 2번째 부터는 `CoffeeDecorator`의 객체이기 때문에 `decoratedCoffee`를 가질 수 있고 기능을 추가할 때마다 `decoratedCoffee` 객체에 저장을 시켜준다. 그래서 `cost`와 `ingredient`를 누적시켜 출력할 수 있게 된다.



# UML과 출력

![default](https://user-images.githubusercontent.com/35518072/40214313-f8a2f144-5a94-11e8-83e8-c26926ce6da8.PNG)

![2](https://user-images.githubusercontent.com/35518072/40214333-0cbc61b0-5a95-11e8-9acb-63cb4ad2f120.PNG)