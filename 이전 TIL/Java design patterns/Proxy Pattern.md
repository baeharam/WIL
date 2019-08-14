# 프록시 패턴

프록시는 다른 무언가와 이어지는 인터페이스의 역할을 하는 클래스이다. 따라서 프록시 패턴은 이런 역할을 하는 프록시를 비서 역할로 클라이언트에서 사용하는 개념이다. OODP 수업시간에 배운 것은 원격 프록시로 자바의 RMI에서 스텁 객체가 바로 프록시이다. 스텁 객체는 클라이언트에서 호출한 메소드를 스켈레톤 객체에 전달하여 실제 메소드를 호출하는 기능을 한다. 이 점에서 클라이언트가 스텁 객체에게 일을 시키는 것이기 때문에 프록시의 역할이라고 볼 수 있다.

![proxy](https://user-images.githubusercontent.com/35518072/38710853-3f1d42f4-3efd-11e8-8d03-f716dec0ba6f.gif)

> 출처 : http://limkydev.tistory.com/79

다음은 프록시 패턴의 UML인데 Client가 인터페이스를 통해 Server 역할을 하는 Subject 클래스의 메소드를 호출하고 있다. 하지만 인터페이스이므로 실제 메소드를 호출하는 역할은 바로 Proxy 객체가 RealSubject 클래스의 메소드를 호출하는 것이다. 여기서 Proxy는 Client가 호출한 request() 메소드를 호출하는데 RealSubject의 참조 객체를 통해 호출하는 방식이다. 클라이언트에게서 매개변수가 있다면 전달받은 후 참조객체를 통해 메소드를 호출하고, 만약 리턴값이 있다면 클라이언트에게 돌려줌으로서 끝나게 된다.



## 코드를 보자(OODP 수업 - RMI)

```JAVA
public interface RemoteDate extends Remote
{
	public abstract Date getDate() throws RemoteException;
}
```

먼저 인터페이스인 RemoteDate 클래스인데 클라이언트에게 메소드에 대한 정보를 주는 역할을 한다. 

```java
public class RemoteDateImpl extends UnicastRemoteObject implements RemoteDate
{
    public RemoteDateImpl() throws RemoteException {  }
    
    // 클라이언트가 사용할 서버의 메소드
    public  Date getDate() throws RemoteException {
        return new Date();
    }
    
   public static void main(String[] args)  {
        try {
            RemoteDate dateServer = new RemoteDateImpl();

            // Bind this object instance to the name "DateServer"
            // RMI Registry에 등록
            Naming.rebind("DateServer", dateServer);
            //Naming.rebind("//localhost:1099/DateServer", dateServer);

            System.out.println("DateServer bound in registry");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
```

RemoteDateImpl 클래스는 RemoteDate 클래스를 상속받아서 구체적인 메소드를 구현하며 rmiregistry에 proxy역할을 할 "DateServer"라는 이름의 객체를 등록시킨다. 실제로는 Client에서 사용하게 할 Stub 객체를 등록시키는 것이다.

```java
public class RMIClient 
{  
   public static void main(String args[]) { 
    try {
      String host = "rmi://172.17.215.64/DateServer";
      // 원격 객체를 RMI Registry에서 찾아본다.
      // DateServer가 proxy의 역할을 한다.
      RemoteDate dateServer = (RemoteDate)Naming.lookup(host);
      // 원격 객체를 찾았으면 원하는 메소드 호출
      System.out.println(dateServer.getDate());
    }
    catch (Exception e) {
        System.err.println(e);
    }
   }
}
```

이제 Clinet에서 메소드를 호출할려고 하는데 rmiregistry에서 "DateServer"라는 이름의 스텁 객체를 찾고 이걸 통해서 getDate() 메소드를 호출하고 있다. 따라서 DateServer라는 스텁 객체가 Client로 하여금 Server의 메소드를 호출하게 해주기 때문에 proxy로 볼 수 있는 것이 된다.