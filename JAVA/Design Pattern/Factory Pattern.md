# Factory Pattern (팩토리 패턴)

![factory_method_uml_class_diagram](https://user-images.githubusercontent.com/35518072/39612361-b24628fc-4f99-11e8-932c-67653ad993ee.png)

> 부모 클래스에 알려지지 않은 구체 클래스를 생성하는 패턴이며, 자식 클래스가 어떤 객체를 생성할지를 결정하도록 하는 패턴이다. 부모 클래스 코드에 구체 클래스 이름을 감추기 위한 방법으로도 사용한다.
>
> 위키백과(정의), [사진출처](https://stackoverflow.com/a/5740080/9437175) 

정확하게 말하면 팩토리 메소드 패턴이지만 일단 팩토리 패턴이라고 하자. 이 패턴은 객체를 생성하는 하나의 팩토리를 가지는 패턴으로 부모 클래스인 팩토리를 상속하는 다양한 종류의 팩토리를 통해 각 클래스의 객체를 생성하는 패턴이다. 즉, 객체 생성에 대한 인터페이스를 제공하는 것이라고 할 수 있다.

위의 UML에서 `Creator` 클래스는 인터페이스 역할을 하는 팩토리 클래스이고 `ConcreteCreator` 클래스는 구체적으로 특정 종류의 객체를 생성하는 팩토리 클래스이다. 각 팩토리 클래스가 생성하는 객체는 `ConcreteProduct` 클래스의 객체라고 할 수 있는데 그 이유는 특정 종류의 객체를 생성한다는 것 자체가 `Product` 인터페이스를 구현하는 구체적인 `Product` 클래스 즉, `ConcreteProduct` 클래스의 객체를 생성한다는 것이기 때문이다.



# 코드를 보자 (Yuki)

```java
// 팩토리 클래스, 특정클래스의 객체를 생성하는 모든 팩토리 클래스의 부모 클래스
public abstract class Factory {
    // 템플릿 메소드 패턴, createProduct()와 registerProduct() 메소드를 create() 메소드로 묶었다.
    public final Product create(String owner) {
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }
    protected abstract Product createProduct(String owner);
    protected abstract void registerProduct(Product product);
}
```

먼저 패턴의 중심적인 역할을 하는 Factory 클래스는 abstract 클래스이므로 polymorphic function을 가져 자식 클래스에서 특정 클래스의 객체를 생성할 수 있게 만든다. 그 역할을 하는 것이 바로 `createProduct` 와 `registerProduct` 이다. 자식클래스에서 `createProduct` 는 실제로 new 연산자를 통해 owner를 인자로 받아 특정 클래스의 객체를 생성하고 `registerProduct` 는 생성한 객체를 `List` 에 추가함으로 등록시킨다.

```java
// 팩토리 클래스의 자식클래스, IDCard의 객체생성과 등록을 담당한다.
public class IDCardFactory extends Factory {
    private List<String> owners = new ArrayList<String>();
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }
    protected void registerProduct(Product product) {
        owners.add(((IDCard)product).getOwner());
    }
    public List<String> getOwners() {
        return owners;
    }
}
```

위에서 설명한 `createProduct`와 `registerProduct`가 다음과 같은 형태로 구성되며 IDCard의 객체를 인자로 전달받지 않고 Product의 객체를 전달받는 이유는 Product의 하위 클래스도 여러 종류가 있을 수 있으므로 polymorphism을 보장하기 위해서이다.

```java
// 생성할 객체의 부모 클래스
public abstract class Product {
    public abstract void use();
}

// 생성할 객체의 클래스

public class IDCard extends Product {
    private String owner;
    IDCard(String owner) {
        System.out.println(owner + "의 카드를 만듭니다.");
        this.owner = owner;
    }
    public void use() {
        System.out.println(owner + "의 카드를 사용합니다.");
    }
    public String getOwner() {
        return owner;
    }
}
```

다음은 abstract 클래스인 Product와 그걸 상속하는 IDCard 클래스이다. Product 클래스는 단 하나의 메소드인 `use` 만 있고 이 메소드를 여러개의 자식 클래스가 구체적으로 구현하게 된다. Product의 자식 클래스마다 쓰임새가 다를것이니 말이다.

```java
public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("홍길동");
        Product card2 = factory.create("이순신");
        Product card3 = factory.create("강감찬");
        card1.use();
        card2.use();
        card3.use();
    }
}
```

마지막으로 main 메소드를 보면 Factory 클래스의 객체를 생성해서 사용하지만 특정 종류의 객체를 생성하는 IDCardFactory의 생성자를 사용한다. 이제 factory를 가지고 Product의 하위 클래스인 IDCard의 객체를 생성하며 각각 `use` 메소드를 사용하는 것을 볼 수 있다.

여기서 위의 UML을 적용시켜보면 Creator는 `Factory`이며, ConcreteFactory는 `IDCardFactory`이고, Product는 `Product` 그대로, ConcreteProduct는 `IDCard` 이다.

이 패턴의 장점은 구체적인 하위 클래스의 이름에 대한 속박으로부터 부모 클래스를 자유롭게 만들어준다는 것이다. 사용자에게 보여지는 것은 `Factory`와 `Product` 뿐이지만 `create` 메소드와 `use` 메소드 안에서 일어나는 일은 구체적인 부분으로 숨겨져있다. 이것이 팩토리 메소드 패턴의 핵심이다.