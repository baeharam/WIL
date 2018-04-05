# RMI(Remote Method Invocation)

RMI란 RPC의 JAVA 버전이다. 그렇다면 RPC는 무엇인가? Remote Procedure Call의 약자로 해석한대로 원격으로 procedure(=function)를 호출하는 것이다. 이 RMI도 똑같은 원리이다. 어떤 로컬 컴퓨터에서 원격 컴퓨터의 메서드를 호출하는 기술을 자바로 구현한 것이다.

당연히 클라이언트-서버 모델로 구현되며 서버에서 원격 메서드를 호출하고 클라이언트는 그 결과값만을 네트워크로 전송받는다.

## RMI의 데이터 전달 기법

자고로 method는 매개변수와 반환값이 있어야 한다. 따라서 로컬 컴퓨터에서 원격으로 method를 호출하더라도 매개변수를 RMI 통신을 통해서 날려주고, 그 리턴값을 다시 되돌려 받아야 한다. 이걸 해결하기 위해서 RMI 내부에서는 **객체 직렬화(Serialization)의 기법**을 사용한다. 매개변수로 넘겨 줄 객체를 직렬화한 후 method를 호출할 때 함께 보내주는 것이며 결과값 또한 직렬화되어 원격 컴퓨터로부터 반환된다.



## RMI 모델

RMI를 구축하기 위해선 원격 컴퓨터에 RMI Server가 필요하다. RMI Server에 객체를 넣어두고(binding) 로컬 컴퓨터에서 접근한 후 객체의 method를 호출한다. 기본적으로 RMI Registry라는 서버 모델이 제공되는데 이건 단순 개발용이고 제일 널리 알려진 것이 EJB(Enterprise JavaBeans)를 서비스해주는 J2EE 모델이라고 하는데 그냥 알아두자.

* **RMI Registry**

자바 RMI가 원격 객체를 관리하고 서비스하는데 제공하는 원격 객체 컨테이너(Container)이다.

* **바인딩(Binding)**

RMI Registry에 원격 객체를 등록하는 과정을 말하며 등록할 때에는 객체를 식별할 수 있는 식별자(name)와 함께 등록해야 한다. 

* **클라이언트의 method 호출**

클라이언트는 lookup이라는 과정을 통해 서버로부터 원격 참조객체를 내려받는다. 원격 객체의 바인딩 과정에서 사용한 식별자를 사용하여 RMI Registry에 존재하는 객체를 클라이언트 측에서 검색하는 작업이다. 그리고 클라이언트가 이 원격 객체를 lookup 하였을 때 RMI Registry는 원격 객체를 대신 할 원격 참조객체를 클라이언트에 보내주게 되며, 클라이언트는 원격 참조객체를 통하여 원격객체를 호출한다.



## RMI 만들기

### 1. 원격 인터페이스 작성

원격 인터페이스는 원격 객체가 사용할 method를 명시하는 과정이다.

```java
import java.rmi.*;

public interface RMIServer extends Remote
{
	public abstract String Hello() throws RemoteException;
}
```

여기에 명시된 method만이 호출 가능하므로 이 인터페이스는 클라이언트 측에 공개되어야 한다. 또한 *Remote*를 상속하며 *RemoteException* 처리를 해주어야 한다. (규약이라고 함)

### 2. 원격 클래스 만들기

위에서 정의한 원격 인터페이스를 구현해야 한다.

```java
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerImpl extends UnicastRemoteObject implements RMIServer{
	public RMIServerImpl() throws RemoteException{}
	public String Hello() throws RemoteException{
		return "Hello";
	}
}
```

*UnicastRemoteObject* 클래스를 상속해야 하며 생성자에도 *RemoteException* 처리를 해주어야 한다. 인터페이스의 abstract method인 *printHello*를 구현하고 있다.

### 3. 스텁(Stub) 클래스 만들기

클라이언트가 사용하는 원격 참조객체를 만들기 위해서는 스텁(Stub) 클래스가 필요하다. 스텁 클래스로 객체를 만들면 원격 참조객체가 되며 *rmic.exe*라는 툴을 사용한다.

![default](https://user-images.githubusercontent.com/35518072/38370184-77302014-3924-11e8-879d-2d64eeb73729.PNG)

다음 사진에서 볼 수 있다시피 *RMIServer.java*와 *RMIServerImpl.java*를 컴파일해서 *rmic.exe*로 실행해주면 *RMIServerImpl_Stub.class*가 생기는 것을 볼 수 있다. 이것이 바로 클라이언트 측에서 서버와 통신하기 위해 사용하는 스텁 클래스이다. 원래는 스켈레톤(Skeleton) 클래스도 같이 생기는데 클라이언트와 서버가 직접 통신을 하지 않고, 스텁 객체와 스켈레톤 객체를 통해서 통신하게 되기 때문이다. 잠시 헷갈리니 스텁 객체와 스켈레톤 객체를 통한 RMI 통신방식을 확인하고 넘어가도록 하자.

![gk0](https://user-images.githubusercontent.com/35518072/38370604-74d41a22-3925-11e8-8ec6-af1486d5f7f8.gif)

> 출처 : http://mysnyc.tistory.com/9

1. RMI Server에서 원격객체를 생성한다. 이 때, RMI 런타임의 내부적인 메커니즘에 의해서 스텁과 스켈레톤 객체가 생성된다. 생성된 스텁 객체는 RMI 원격 객체에 대한 위치 정보(IP, port)와 객체 식별자 정보 등을 포함한다.
2. RMI Server는 rmiregistry에 RMI 원격 객체를 약속된 이름으로 등록한다. 이 때, rmiregistry에 등록되는 객체는 내부적인 메커니즘에 의해서 원격 객체가 아닌 스텁 객체가 등록된다.
3. 클라이언트는 약속된 이름과 위치정보를 통하여 rmiregistry에 접근하고 약속된 이름으로 스텁객체를 찾는다.
4. 찾은 스텁 객체를 가져온다.
5. 클라이언트는 전달받은 스텁 객체를 원격 인터페이스로 형변환하여 원격 인터페이스에 정의된 method를 호출한다.
6. 스텁 객체는 RMI 원격 객체에 대한 위치 정보와 객체 식별자 정보를 통하여 서버에 접속하고 클라이언트가 호출한 method와 매개변수들을 RMI 프로토콜에 맞추어 전송한다.
7. 스텁 객체가 전송한 정보는 네트워크를 통해 스켈레톤 객체에 전달한다.
8. 스켈레톤은 전달받은 정보에 따라 원격 객체로 method 호출을 분배한다.
9. 호출 후 리턴받은 정보를 다시 네트워크를 통해 스텁 객체에 전달한다.
10. 스텁 객체는 다시 클라이언트엑 반환하여 하나의 호출을 완료한다.

과정이 상당히 길기는 하지만 중간에 rmiregistry가 있고 스텁 객체와 스켈레톤 객체가 통신하면서 method를 호출하는 과정임을 알 수 있다. (Java 1.2 이후에는 스텁 클래스에 스켈레톤 클래스의 기능이 통합되었다고 한다.)

### 4. 스텁과 스켈레톤을 사용하여 바인딩 시키기

*rmic.exe*를 통해 스텁과 스켈레톤까지 만들었다면 원격 클래스를 통하여 원격 객체를 만든 후 rmiregistry에 바인딩 시키는 서버 프로그램이 필요하다.

```java
public static void main(String[] args) {
	try {
		RMIServer server = new RMIServerImpl();
		// RMI server name is "Server"
		Naming.bind("Server",server);
		System.out.println("Server is connected");
	}
	catch(Exception e) {
		System.err.println(e);
	}
}
```

원격 클래스인 RMIServer의 객체 server를 만들어서 "Server"라는 식별자로 Naming의 bind()를 이용해서  rmiregistry에 바인딩 시켰다. 이제 rmiregistry를 실행시키고 RMIServerImpl을 실행시키면  rmiregistry에 원격 객체가 등록된다.

![2](https://user-images.githubusercontent.com/35518072/38371374-3b3e4eb6-3927-11e8-98ed-ad5bf2bbf461.PNG)

![3](https://user-images.githubusercontent.com/35518072/38371376-3b70a848-3927-11e8-9fa6-5aa2c67bd81c.PNG)

> * rmiregistry를 실행할 때 port를 변경하려면 bind()에서 사용한 port도 변경해주어야 한다고 한다. 
>
>
> * 클라이언트가 원격 객체를 lookup 할 때 RMI Server는 원격 참조객체(Stub)의 메모리를 직렬화시켜 클라이언트로 전송한다. 이것을 역직렬화 시키기 위해선 원격 참조객체의 클래스 파일이 필요한데 이것이 stub 파일이다. 따라서 stub 파일은 클라이언트가 다운로드 할 숭 있는 곳에 위치시켜야 하며, 이 때 위치는 codebase를 사용해서 지정한다고 한다.

### 5. 클라이언트의 lookup

* 클라이언트의 lookup 과정
  * lookup 요청
  * 직렬화된 Stub 객체 반환
  * Stub 파일을 찾기 위해서 codebase 검색
  * Stub 클래스 파일 요청
  * Stub 클래스 파일 다운로드
  * 직렬화된 Stub 객체를 Stub 클래스를 이용해서 역직렬화

이렇게 역직렬화를 마치면 원격 참조객체가 만들어지며, 이것을 이용해서 클라이언트는 method를 호출할 수 있게 된다. 단, 실제 method를 호출할 때는 원격 참조객체를 인터페이스로 casting 해주어야 한다. (이것이 바로 인터페이스가 공개되어야 하는 이유)

```java
import java.rmi.*;

public class RMIClient {
	public static void main(String[] args) {
		try {
			String host = "rmi://172.17.215.64/Server";
			// "Server"라는 이름을 가진 RMI server를 찾아서 연동시킨다.
			RMIServer Server = (RMIServer)Naming.lookup(host);
			for(int i=0; i<10; i++) System.out.println(Server.Hello());
		}
		catch(Exception e) {
			System.err.println(e);
		}
	}
}
```

![4](https://user-images.githubusercontent.com/35518072/38372086-f8f77a08-3928-11e8-8b81-4b053b862c20.PNG)

만들어진 원격 참조객체(여기선 Server)를 통하여 RMIServer의 method인 *Hello()*를 호출할 수 있게 되었고 *RMIClient*를 실행했을 때 다음과 같이 "Hello"가 10번 출력되는 것을 볼 수 있다. 

#### 모든출처

http://devyongsik.tistory.com/42

http://mysnyc.tistory.com/9