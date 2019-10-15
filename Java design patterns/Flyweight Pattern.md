# Flyweight Pattern(플라이웨이트 패턴)

![flyweight](https://user-images.githubusercontent.com/35518072/39463744-8c64ac22-4d54-11e8-9cd2-64cabfb27949.gif)

> 동일하거나 유사한 객체들 사이에 가능한 많은 데이터를 서로 공유하여 사용하도록 하여 메모리 사용량을 최소화하는 디자인 패턴이다.
>
> *위키백과(정의)*

최대한으로 메모리를 절약하는 패턴으로 공유할 수 있는 데이터는 최대한 많이 공유하여 객체를 필요할 때마다 이미 있는 객체, 즉 available 하다면 생성하지 않고 그걸 사용하는 것이다. 블로그에 명확하게 이해할 수 있는 예시가 있어서 그걸 보면 조금 더 직관적으로 이해될 것 같다.

*"당신에게 새로 주어진 일은 실시간 전략 시뮬레이션 게임을 개발하는 일이다. 평소에 게이머로서 스타크래프트 게임의 재미에 푹 빠져있던 당신은 개발자로써 다시 한 번 그 게임에 대하여 놀라게 된다. 어떻게 수백개 이상이 되는 많은 캐릭터들이 성능이 뒤떨어진 컴퓨터에서도 무리 없이 병렬적으로 동작을 하는가?" ([출처](http://palpit.tistory.com/198))*



# 코드를 통한 이해

위의 UML이 적용된 코드를 보면서 어떻게 동작하고 flyweight 패턴의 핵심이 무엇인지 알아보도록 하자.

```java
// Instances of CoffeeFlavour will be the Flyweights
class CoffeeFlavour {
  private final String name;

  CoffeeFlavour(String newFlavor) {
    this.name = newFlavor;
  }

  @Override
  public String toString() {
    return name;
  }
}
```

다음 CoffeeFlavour 클래스는 flyweight이 적용될, 즉 최대한 light 하게 객체를 생성할 클래스이다. 커피의 주문이 굉장히 빈번하게 발생하기 때문에 name에 따라서 기존에 있는 객체인지 판별할 수 있다.

```java
// Menu acts as a factory and cache for CoffeeFlavour flyweight objects
class Menu {
  private Map<String, CoffeeFlavour> flavours = new HashMap<String, CoffeeFlavour>();

  // flavorName으로 주어진 것이 flavours HashMap에 없다면 생성하고 CoffeeFlavour object를 리턴한다.
  CoffeeFlavour lookup(String flavorName) {
    if (!flavours.containsKey(flavorName))
      flavours.put(flavorName, new CoffeeFlavour(flavorName));
    return flavours.get(flavorName);
  }

  int totalCoffeeFlavoursMade() {
    return flavours.size();
  }
}
```

Menu 클래스는 기존의 CoffeFlavour object가 존재하는지 체크하고 객체 생성 여부를 결정하며 기존에 생성한 객체를 HashMap에 저장하는 팩토리(Factory)/캐시(Cache)의 역할을 한다.

```java
class Order {
  private final int tableNumber;
  private final CoffeeFlavour flavour;

  Order(int tableNumber, CoffeeFlavour flavor) {
    this.tableNumber = tableNumber;
    this.flavour = flavor;
  }

  void serve() {
    System.out.println("Serving " + flavour + " to table " + tableNumber);
  }
}
```

Order 클래스는 공유하기 힘든 데이터, 즉 기존에 있는 걸 갖다 쓰기 힘든 데이터를 나타내는 것이며 테이블 번호와 어떤 커피를 주문할 것인지 받아 저장하고, serve() 메소드를 통해 어떤 테이블에 어떤 커피가 서빙됬는지 알 수 있다.

```java
class CoffeeShop {
  private final List<Order> orders = new ArrayList<Order>();
  private final Menu menu = new Menu();

  void takeOrder(String flavourName, int table) {
    CoffeeFlavour flavour = menu.lookup(flavourName);
    Order order = new Order(table, flavour);
    orders.add(order);
  }

  void service() {
    for (Order order : orders)
      order.serve();
  }

  String report() {
    return "\ntotal CoffeeFlavour objects made: "
        + menu.totalCoffeeFlavoursMade();
  }

  public static void main(String[] args) {
    CoffeeShop shop = new CoffeeShop();

    shop.takeOrder("Cappuccino", 2);
    shop.takeOrder("Frappe", 1);
    shop.takeOrder("Espresso", 1);
    shop.takeOrder("Frappe", 897);
    shop.takeOrder("Cappuccino", 97);
    shop.takeOrder("Frappe", 3);
    shop.takeOrder("Espresso", 3);
    shop.takeOrder("Cappuccino", 3);
    shop.takeOrder("Espresso", 96);
    shop.takeOrder("Frappe", 552);
    shop.takeOrder("Cappuccino", 121);
    shop.takeOrder("Espresso", 121);

    shop.service();
    System.out.println(shop.report());
  }
}
```

CoffeeShop 클래스에선 takeOrder() 메소드를 통해서 테이블에서 커피를 주문하게 된다. 이 때 커피의 종류가 다양할 수 있기 때문에 매 주문마다 그 커피에 대한 객체를 생성하면 메모리를 많이 차지할 것이다. **따라서, 기존에 생성한 객체라면 그걸 가져다 쓰는 것이다.** 마지막에 service() 메소드로 어떤 테이블에서 어떤 커피를 주문했는지 보여준다. 실행결과는 다음과 같다. 이 코드를 통해 **flyweight 패턴의 핵심은 객체를 수없이 많이 생성하면 차지하는 메모리의 양이 커질 수 있기 때문에 그 단점을 효과적으로 줄일 수 있는 것이다.**

![default](https://user-images.githubusercontent.com/35518072/39466565-9b78c878-4d64-11e8-84e0-9145d79d014e.PNG)

