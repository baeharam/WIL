# Memento Pattern (기억 패턴)

> ***The memento pattern provides the ability to restore an object to its previous state (undo via rollback). The memento pattern is implemented with three objects: the originator, a caretaker and a memento***

메멘토 패턴은 롤백을 통해 이전 상태의 객체상태로 되돌리는 기능을 제공하는 패턴이다. Behavioral 패턴의 일종이며 Originator, Memento, CareTaker라고 불리는 3개의 클래스를 사용한다.

>The originator is some object that has an internal state.

Originator는 내부 상태를 가지고 있는 객체이다.

> The caretaker is going to do something to the originator, but wants to be able to undo the change. The caretaker first asks the originator for a memento object. Then it does whatever operation (or sequence of operations) it was going to do. To roll back to the state before the operations, it returns the memento object to the originator

Caretaker는 originator에게 어떤 작업을 수행하려고도 하고 변경 내용을 되돌리고도 싶어한다. 먼저 originator에게 memento 객체를 요청하고 일련의 연산과정을 거친다. 만약 연산하기 전에 상태로 되돌리려면 originator에게 memento 객체를 반환한다.

> The memento object itself is an opaque object (one whitch the caretaker cannot, or should not, change).

Memento  객체는 불투명 객체이다. (caretaker가 바꿀 수 없기도 하고 바꿔서는 안되는)

<img src="https://upload.wikimedia.org/wikipedia/commons/1/18/Memento_design_pattern.png">



# 코드를 보자

```java
class Originator {
	private String state;
	// The class could also contain additional data that is not part of the
	// state saved in the memento.

	public void set(String state) {
		System.out.println("Originator: Setting state to " + state);
		this.state = state;
	}
	// 현재 상태 저장
	public Memento saveToMemento() {
		System.out.println("Originator: Saving to Memento.");
		return new Memento(state);
	}
	// 이전 상태 복구
	public void restoreFromMemento(Memento memento) {
		state = memento.getSavedState();
		System.out.println("Originator: State after restoring from Memento: " + state);
	}
	// Inner class로 Memento 클래스를 가진다.
	public static class Memento {
		private final String state;
		private Memento(String stateToSave) {
			state = stateToSave;
		}
		private String getSavedState() {
			return state;
		}
	}
}
```

Originator 클래스인데 memento 객체를 생성하여 현재 상태를 저장하거나 이전 상태로 복원시키는 역할을 한다. 좀 특이한 것이 Memento 클래스가 클래스 안에서 정의된다는 것이다. 여기서 처음 본 것으로 찾아보니 nested static class라고 하는데 다시 한 번 정리를 해야 할 것 같다. 어쨌든 Memento 클래스는 상태를 저장하고 리턴해주는 역할을 한다. 이제 Caretaker 클래스를 통해서 어떤 식으로 메멘토 패턴이 동작하는지 보도록 하자.

```java
import java.util.List;
class Caretaker {
	public static void main(String[] args) {
		List<Originator.Memento> savedStates = new ArrayList<Originator.Memento>();
		Originator originator = new Originator();
		originator.set("State1");
		originator.set("State2");
		savedStates.add(originator.saveToMemento());
		originator.set("State3");
		// We can request multiple mementos, and choose which one to roll back to.
		savedStates.add(originator.saveToMemento());
		originator.set("State4");
		originator.restoreFromMemento(savedStates.get(1));
		originator.restoreFromMemento(savedStates.get(0));
	}
}
```

Memento 객체를 담는 `saveStates` 리스트 객체가 존재하며 Origiantor 객체인 `originator`로 상태를 저장하는데 되돌리고 싶은 상태의 경우 메멘토 객체에 저장하여 `saveStates` 에 담는다. 마지막엔 `restoreFromMemento` 메소드를 통해서 이전 상태를 되돌리고 있다.

코드를 보면 알 수 있듯이 memento 객체에 접근할 수 있는 객체는 `originator` 뿐이다. Caretaker에서는 내부적으로 숨겨져 있고 단지 `originator`에게 memento 객체를 인자로 전달하거나 요청할 수 있는 것이 전부이다. 