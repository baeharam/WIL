# 책임 사슬 패턴

이름 그대로 어떠한 이벤트나 문제가 발생했을 때 그 문제의 처리를 단일객체에게 맡기는 것이 아니라 다른 객체에도 처리할 수 있는 기회를 주는 패턴이다. 문제처리가 가능하지 않으면 다음 객체로 보내는 방식이기 때문에 객체들이 사슬처럼 연결되어 있다.  여기서 이벤트나 문제를 던지는 객체가 **명령 객체**이며 그걸 처리하는 일련의 객체집합이 **처리 객체**이다. 

![chain of responsibility pattern](https://user-images.githubusercontent.com/35518072/37784006-375b2690-2e3a-11e8-88c6-6548c7175697.jpg)

다음과 같이 명령객체인 Sender가 존재하고 처리객체의 슈퍼클래스인 Handler가 존재하여서 오른쪽의 시퀀스 다이어그램에서 receiver 1,2,3에 차례대로 handling을 시킴으로 명령을 처리하고 있음을 알 수 있다.

## 목적

1. To **avoid coupling the sender of a request to its receiver**, by giving more than one object a chance to handle the request.
2. **to isolate the clients from knowledge of how responsibilities are assigned**

명령객체와 처리객체를 분리하여 유지보수가 용이하고 명령객체는 처리객체들이 명령을 어떻게 처리하는지 몰라도 상관이 없다.



## Yuki의 코드

```java
// 처리객체들의 슈퍼클래스인 Support클래스이다.
public abstract class Support {
    private String name;
    private Support next; // 사슬처럼 연결하기 위해 next변수를 가지고 있다.
    public Support(String name) {
    	this.name = name;
    }
    // setNext 함수를 사용하여서 어떤 처리객체로 연결할 것인지를 결정한다.
    public Support setNext(Support next) {
    	this.next = next;
    	return next;
    }
    // 명령을 받아서 해결되면 done 함수를 호출하고 해결되지 않으면 다음 처리객체로 넘긴다
    // 만약 모든 처리객체에서 해결되지 않을 경우 fail함수를 호출하고 끝난다.
    public final void support(Trouble trouble) {
        if (resolve(trouble)) {
        	done(trouble);
        } else if (next != null) {
        	next.support(trouble);
        } else {
        	fail(trouble);
        }
    }
    public String toString() {
        return "[" + name + "]";
    }
    
    // 처리객체마다 처리하는 방식이 다르기 때문에 abstract 키워드를 통해서
    // 다형성을 설정한다.
	protected abstract boolean resolve(Trouble trouble);
    
    // 실패와 성공
	protected void done(Trouble trouble) {
		System.out.println(trouble + " is resolved by " + this + ".");
	}
	protected void fail(Trouble trouble) {
		System.out.println(trouble + " cannot be resolved.");
	}
}
```

```java
// 명령클래스이다. 숫자를 전달하여 그 숫자에 따라 처리방식이 달라진다.
public class Trouble{
    private int number;
    public Trouble(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
    public String toString(){
        return "[Trouble " + number + "]";
    }
}
```

```java
// 1번째 처리클래스, limit 미만의 숫자만 처리한다.
public class LimitSupport extends Support {
    private int limit;
    public LimitSupport(String name, int limit) {
    	super(name);
    	this.limit = limit;
    }
    protected boolean resolve(Trouble trouble) {
    	if (trouble.getNumber() < limit) {
    		return true;
    	} else {
    		return false;
    	}
    }
}
```

```java
// 2번째 처리클래스, 아무것도 처리하지 못한다.
public class NoSupport extends Support {
    public NoSupport(String name) {
    	super(name);
    }
    protected boolean resolve(Trouble trouble) {
    	return false;
    }
}
```

```java
// 3번째 처리클래스, 홀수만 처리한다.
public class OddSupport extends Support {
    public OddSupport(String name) {
    	super(name);
    }
    protected boolean resolve(Trouble trouble) {
    	if (trouble.getNumber() % 2 == 1) {
    		return true;
    	} else {
    		return false;
    	}
    }
}
```

```java
// 4번째 처리클래스, 같은 숫자만 처리한다.
public class SpecialSupport extends Support{
    private int number;
    public SpecialSupport(String name, int number){
        super(name);
        this.number = number;
    }
    protected boolean resolve(Trouble trouble){
        if(trouble.getNumber() == number){
            return true;
        }
        else{
            return false;
        }
    }
}
```

```java
// 메인함수에서 어떤 방식으로 처리객체의 순서를 결정할지를 정한다.
public class ChainOfResponsibility {
    public static void main(String[] args) {
        // 처리객체들의 생성
        Support alice = new NoSupport("Alice");
        Support bob = new LimitSupport("Bob", 100);
        Support charlie = new SpecialSupport("Charlie", 429);
        Support diana = new LimitSupport("Diana", 200);
        Support elmo = new OddSupport("Elmo");
        Support fred = new LimitSupport("Fred", 300);
        
        // 처리객체의 순서 결정
        alice.setNext(bob).setNext(charlie).setNext(diana).
        setNext(elmo).setNext(fred);
        
        // 명령객체를 생성해서 여러개의 case로 Handler에 넘긴다.
        for (int i = 0; i < 500; i += 33) {
        	alice.support(new Trouble(i));
        }
    }
}
```

Trouble의 객체가 생성되면 그걸 Handler인 Support 클래스의 서브클래스 객체들인 처리객체들이 일련의 순서를 통해 처리할 수 있으면 처리하고 할 수 없으면 다음 처리객체로 넘긴다. 물론 어떤 처리객체도 해결하지 못하는 경우도 존재한다.
