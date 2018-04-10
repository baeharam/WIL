# 옵저버 패턴

옵저버 패턴이란 말 그대로 여러개의 옵저버 객체를 통해 하나의 객체를 감시하면서 그 객체에 어떤 변화가 생기는지를 보는 것이다.

![1281px-observer svg](https://user-images.githubusercontent.com/35518072/38545317-bd13c3e2-3ce4-11e8-8fbc-8d100b891260.png)

> 출처 : [위키백과, 옵저버패턴](https://ko.wikipedia.org/wiki/%EC%98%B5%EC%84%9C%EB%B2%84_%ED%8C%A8%ED%84%B4)

옵저버 패턴의 UML을 자세히 들여다 보자. 디자인 패턴을 공부하면서 느끼게 된 것은 UML을 제대로 이해하여야 제대로된 패턴의 역할과 기능을 알 수 있다는 것이다. 

Observer 클래스는 추상클래스로 구체적으로 구현된 ConcreteObserverA와 ConcreteObserverB 클래스를 가진다. 실제, 옵저버로 사용하는 객체는 이 2가지이다.

Subject 클래스는 "이벤트를 발생시키는 주체"이다. Subject 클래스 내부에서 옵저버 객체를 **등록(Register)/제거(Unregister)**한다. 따라서 Subject 클래스에 등록된 옵저버 객체들만이 Subject 객체에서 발생한 이벤트에 대해 감지할 수 있다.

이벤트가 발생하면 *notifyObservers()* 메소드를 통해서 등록된 각 옵저버 객체로 *notify()* 메소드를 호출한다. 따라서 Subject 객체에서 이벤트가 발생하면 옵저버 객체가 바로 감지하여 어떤 메시지를 출력할 수 있을 뿐더러, 옵저버 자신이 생성한 인자값을 전달할 수도 있다.



## 코드를 보자 (OODP 수업)

```java
public class Client {
	public static void main(String[] args) {
		LogSubject subject = new LogSubject();
		IObserver ob1 = new Observer1();
		IObserver ob2 = new Observer2();
		subject.attach(ob1);
		subject.attach(ob2);
		subject.setState("state1");
		subject.setState("state2");
		subject.detach(ob1);
		subject.setState("state3");
	}
}
```

제일 먼저 모든 걸 관장하는 메인 함수가 있는 Client 클래스이다. 이벤트를 발생시키는 주체인 LogSubject 객체를 생성하고 2개의 옵저버 객체 ob1과 ob2를 등록하고 있음을 볼 수 있다.

또한 *setState* 메소드를 통하여 LogSubject 객체에 변화를 주기도 하고, ob1 옵저버 객체를 제거하고 다시 상태 변화를 주기도 한다. 이제 자세히 어떻게 동작하는지 알아보자.

```java
public interface IObserver {
	void update(String state);
}
```

먼저 볼 것 없는 IObserver 인터페이스이다. 위에선 추상클래스라고 말했지만 둘 다 가능하다. 물론 구체적인 상황에 따라서는 다르다. OODP 수업에서 사용한 예제는 전부 인터페이스이니, 일단은 인터페이스로...

```java
import java.util.List;
import java.util.ArrayList;
public class LogSubject {
	//private List <IObserver> observerList = new ArrayList <IObserver>();
	List<IObserver> observerList = new ArrayList<IObserver>();
	private String state;

	public String getState() {
		return state;
	}

	public void attach(IObserver observer) {
		observerList.add(observer);
	}

	public void detach(IObserver observer) {
		observerList.remove(observer);
	}

	public void setState(String state) {
		this.state = state;
		stateChanged();
	}

	private void stateChanged() {
		for (IObserver item: observerList) {
			item.update(getState());
		}
	}
}
```

다음은 이벤트를 발생시키는 LogSubject 클래스이다. 먼저 옵저버 클래스인 IObserver에 대해 ArrayList를 만드는 데 그 이유는 옵저버 객체들을 등록하기 위함이다. 그 다음 상태 변수인 state가 있고 getState(), setState() 모두 기본적으로 있다. 여기선 상태변화 감지가 중요하기 때문에 setState() 메소드가 중요하다. 

그 다음에 attach/detach 함수는 그 이름에서 알 수 있다시피 옵저버 객체를 등록하고 제거하는 역할을 한다. 마지막으로 가장 중요하게 봐야할 것은 **stateChanged()** 메소드이다. 이 메소드는 **setState()**를 통해서 이벤트(상태변화)가 발생하면 호출되는 메소드이며 호출되는 순간 for문으로 모든 옵저버 객체를 돌면서 이벤트가 발생했다는 것을 알린다. 이 부분이 바로 옵저버 패턴의 핵심이다.

```java
public class Observer1 implements IObserver {
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void update(String state) {
		setState(state);
		System.out.println("Observer1 has received update signal with new state: " + getState());
	}
}
```

1번째 옵저버 객체로 사용되는 Observer1 클래스에선 **update(state)** 메소드가 중요한데 LogSubject 객체에 이벤트가 발생함을 통해서 어떤 상태로 변화되었는지 매개변수를 전달받아 상태에 변화를 주고 원하는 메시지를 출력해주고 있다. 이로써 사용자는 어떠한 이벤트가 발생했다는 것을 알 수 있다. 2번째로 사용되는 Observer2 클래스는 메시지만 다르고 로직이 동일하기 때문에 스킵한다.

마지막으로 옵저버 패턴을 예를 들어 설명하자면 감옥에 갇혀 있는 죄수에 대해서 여러명의 간수가 죄수가 어떤 짓을 저지르는지 계속해서 감시하는 상황이라고 보면 될 것 같다.