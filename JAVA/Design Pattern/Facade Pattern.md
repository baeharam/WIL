# Facade Pattern (외관 패턴)

Facade란 프랑스어로 "건물의 정면(외관)"을 의미한다. 이 패턴은 클라이언트에게 클래스 라이브러리와 같은 어떤 소프트웨어의 커다란 코드 부분에 대한 간략화된 인터페이스를 제공함으로서 훨씬 쉽게 이해하고 편하게 사용할 수 있도록 해준다.

<img src="https://upload.wikimedia.org/wikipedia/commons/a/ac/FacadeDesignPattern.png">

> 출처,설명 : [위키백과](https://ko.wikipedia.org/wiki/%ED%8D%BC%EC%82%AC%EB%93%9C_%ED%8C%A8%ED%84%B4)

UML만 봐도 패키지 1,2,3을 이용하고 여러가지 다른 코드를 이용하는 코드들을 `doSomething()`이라는 하나의 메소드로 묶어서 더 쉬운 인터페이스로 사용할 수 있도록 하고 있고 클라이언트 1,2가 패키지 내의 리소스들을 접근하기 위해 Facade 클래스에 접근하고 있다.



# 코드를 보자 (추상적인 예제)

위키백과에서 제공하는 코드를 보면 추상적이지만 아주 쉽게 이해할 수 있다.

```java
class CPU{
    public void freeze(){}
    public void jump(long position){}
    public void execute(){}
}
class Memory{
    public void load(long position, byte[] data){}
}
class HardDrive{
    public byte[] read(long lba, int size){}
}
```

컴퓨터는 CPU, 메모리, 하드디스크로 등으로 이루어져있고 컴퓨터를 시작하기 위해선 이런 내부의 부품들이 상호작용하여야 한다. 이러한 복합적인 기능을 Facade 클래스로 구현하면 사용자는 전원버튼 역할을 하는 Facade 클래스의 메소드를 통해서 쉽게 컴퓨터를 킬 수 있게 되는 것이다.

```java
class Computer{
    public void startComputer(){
        CPU cpu = new CPU();
        Memory memory = new Memory();
        HardDrive hardDrive = new HardDrive();
        cpu.freeze();
        memory.load(BOOT_ADDRESS, hardDrive.read(BOOT_SECTOR, SECTOR_SIZE));
        cpu.jump(BOOT_ADDRESS);
        cpu.execute();
    }
}
```

보다시피 Facade 클래스의 역할을 하는 Computer 클래스의 `startComputer()` 메소드는 복합적인 과정을 통해 컴퓨터를 켜는 작업을 한다.

```java
class Client{
    public static void main(String[] args){
        Computer facade = new Computer();
        facade.startComputer();
    }
}
```

위와 같이 클라이언트(사용자)는 메소드 하나만으로 복합적인 과정을 모르고서도 컴퓨터를 쉽게 켤 수 있다.



# 특징

Facade 패턴의 정의에 대해 익혔고 코드를 통해서 쉽게 이해했으니 어떤 특징들이 있는지 보고 마무리하도록 하자.

* Facade는 소프트웨어 라이브러리를 쉽게 사용하고 이해할 수 있게 해주며 복합적인 작업에 대해서 간단한 메소드들을 제공해준다.
* Facade는 라이브러리 바깥쪽의 코드가 라이브러리 안쪽 코드에 의존하는 일을 감소시켜주는데, 대부분의 바깥쪽 코드가 Facade를 이용하기 때문에 시스템을 개발하는데 있어 유연성이 향상 된다.
* Facade는 좋게 작성되지 않은 API 집합을 하나의 좋게 작성된 API로 감싸준다.