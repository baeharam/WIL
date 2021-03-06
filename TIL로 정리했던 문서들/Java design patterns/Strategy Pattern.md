# 전략 패턴

![general-strategy-pattern](https://user-images.githubusercontent.com/35518072/39158824-2258c276-479d-11e8-9e6d-7d2674db6202.png)

> 출처 : https://dzone.com/articles/java-the-strategy-pattern

다음 UML을 보면 Context와 Strategy를 분리해서 사용하는 것을 알 수 있다. 즉, 사용하는 알고리즘을 사용자와 분리시켜서 유연하게 사용하는 패턴을 말한다. 디자인 패턴의 모든 목적은 Reusability, Maintainability 이기 때문에 분리라는 측면이 모든 패턴에 있어서 핵심적으로 작용한다. 여기서도, Strategy라는 인터페이스를 두고 그 인터페이스를 구현하는 ConcreteStrategy1,2를 사용하는 것으로 기능(알고리즘) 자체를 사용자와 분리시킨다.

위키백과를 보면 다음과 같이 설명한다.

* **특정한 계열의 알고리즘들을 정의하고**
* **각 알고리즘을 <u>캡슐화</u> 하며**
* **이 알고리즘들을 해당 계열 안에서 상호 교체가 가능하게 만든다.**

핵심은 알고리즘을 캡슐화 한다는 것이다. 정확히는, 변하는 부분을 캡슐화하는 것을 말하는데 A라는 알고리즘이 있다고 하고 많은 사용자들이 A라는 알고리즘을 사용한다고 하자. 만약 A를 상속해서  사용한다고 하면, <u>A를 사용하지 않는 사용자도 있을 것이고 B라는 알고리즘을 사용해야 하는 사용자도 있을 것이다.</u> 이런 문제들이 발생하기 때문에 아예 인터페이스로 분리를 시켜서 종속성을 없애는 방법이 바로 전략 패턴의 해결법이다.

코드를 보면서 이해하기 보다는 Animal 클래스로 아주 쉽게 설명해주시는 분이 있으니 [개발이 하고 싶어요](http://hyeonstorage.tistory.com/146) 를 참고하도록 하자.