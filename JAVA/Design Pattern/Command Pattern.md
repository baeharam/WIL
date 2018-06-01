# Command Pattern(명령 패턴)

> *"커맨드 패턴이란 요청을 객체의 형태로 캡슐화하여 사용자가 보낸 요청을 나중에 이용할 수 있도록 메서드 이름, 매개변수 등 요청에 필요한 정보를 저장 또는 로깅, 취소할 수 있게 하는 패턴이다."*  (위키백과)

<img src="https://upload.wikimedia.org/wikipedia/commons/8/8e/Command_Design_Pattern_Class_Diagram.png">

위 그림과 같이 커맨드 패턴은 4가지의 객체를 가진다.

* Command 인터페이스를 구현하는 ConcreteCommand 객체, 실제 명령내용이 담겨있다.
* ConcreteCommand 객체를 전달받아 명령을 발동시키는 Invoker 객체.
* ConcreteCommand 객체가 명령을 수행할 때 사용하는 Receiver 객체.
* 마지막으로 이런 일련의 과정을 수행하는 Client 객체.

여기서 Invoker 객체는 ConcreteCommand 객체를 전달받아 명령을 발동시키기 때문에 <u>명령 발동에 대한 로그를 남길 수 있고, 다수의 객체를 전달받을 수도 있다.</u> 따라서 Client 객체는 Invoker 객체 1개와 1개 이상의 ConcreteCommand 객체를 보유한다. <u>또한, 명령취소를 구현할 수도 있다.</u>



# 코드를 보자

먼저 주문을 하는 Command 인터페이스가 있다.

```java
public interface Order {
	public abstract void execute();
}
```

또한 이걸 구체적으로 구현하는 두 종류의 ConcreteCommand 클래스가 있다.

```java
class BuyStockOrder implements Order {
	private StockTrade stock;

	public BuyStockOrder(StockTrade st) {
		stock = st;
	}

	public void execute() {
		stock.buy();
	}
}
class SellStockOrder implements Order {
	private StockTrade stock;

	public SellStockOrder(StockTrade st) {
		stock = st;
	}

	public void execute() {
		stock.sell();
	}
}
```

위의 ConcreteCommand 클래스들을 보면 `StockTrade` 클래스의 객체로 `buy()`라는 메서드를 호출하고 있는 것을 볼 수 있기 때문에 `StockTrade`는 Receiver라는 것을 단번에 알 수 있다.

```java
class StockTrade {
	public void buy() {
		System.out.println("You want to buy stocks");
	}
	public void sell() {
		System.out.println("You want to sell stocks ");
	}
}
```

명령을 받아서 일련의 기능을 수행하는 Receiver 클래스이다. 이제 이러한 명령을 발동시키는 Invoker 클래스를 보자.

```java
import java.util.ArrayList;
class Agent {
	private ArrayList<Order> ordersQueue = new ArrayList<Order>();
	public Agent() {}

	void placeOrder(Order order) {
		ordersQueue.add(order);
		order.execute();
	}
}
```

Command 인터페이스의 객체를 인자로 받아 큐에 넣고 명령을 발동시키는 `Agent` 클래스이다. 결국 명령을 수행하는 과정은 `Agent` 클래스가 명령을 발동시키고 `Order` 인터페이스의 객체들이 `StockTrade` 객체를 통해 명령을 수행하는 것으로 이루어진다. 마지막으로 Client 클래스를 보고 마무리하도록 하자.

```java
public class Client {
	public static void main(String[] args) {
		StockTrade stock = new StockTrade();
		BuyStockOrder bsc = new BuyStockOrder(stock);
		SellStockOrder ssc = new SellStockOrder(stock);
		Agent agent = new Agent();

		agent.placeOrder(bsc); // Buy Shares
		agent.placeOrder(ssc); // Sell Shares
	}
}
```

ConcreteCommand 객체들과 Invoker 객체, Receiver 객체를 생성하고 이들의 관계를 연결시켜 주고 있다는 것을 알 수 있다. ConcreteCommand 객체들인 `bsc`와 `ssc`는 Receiver 객체인 `stock`을 인자로 받고 있고 Invoker 객체인 `agent`는 메서드를 통해서 `bsc`와 `ssc`를 인자로 받고 있다.

UML을 보면서 각 역할을 하는 객체와 그 관계를 이해하도록 하자.

![default](https://user-images.githubusercontent.com/35518072/40814045-0d3a64a4-6579-11e8-827a-ee4d9d6aaff3.JPG)