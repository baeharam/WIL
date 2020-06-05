## SOLID 원칙

Typescript 는 JavaScript의 superset이 되는 언어로 ES6에서 제공하는 class 키워드 외에 접근 제어자, 인터페이스 등을 제공하여 객체지향 프로그래밍을 좀 더 효율적으로 구성하게 한다. 따라서, Typescript를 확실히 알아가기 위해선 객체지향의 대표적인 원칙인 SOLID에 대해 알아둘 필요가 있다. 면접에서도 자주 등장하고 알아두면 유지보수가 쉽고 견고한 소프트웨어를 만드는데 많은 도움이 된다.

### 단일 책임 원칙 (Single Responsibility Principle, SRP)

> A CLASS SHOULD HAVE ONLY ONE REASON TO CHANGE
>
> 하나의 클래스는 변경하려는 이유가 단 하나여야 한다.

https://drive.google.com/file/d/0ByOwmqah_nuGNHEtcU5OekdDMkk/view

단일 책임 원칙은, 하나의 클래스는 하나의 책임을 지녀야 한다는 원리로 클래스가 제공하는 메서드 및 멤버변수가 모두 **"단일 책임"** 에 관련된 것이어야 한다는 원칙이다. 여기서의 책임의 정의는 "변경하려는 이유" 이며 클래스가 변경되는 이유가 1가지여야 한다는 의미와 동일하다. 예를 들어, `Rectangle` 이라는 클래스가 `draw()` 와 `area()` 라는 2가지 메서드를 가진다고 하자. 2개의 다른 어플리케이션이 있는데 하나는 `area()` 만을 사용하고 다른 하나는 2가지 모두를 사용한다고 하면 이 원칙을 위반한 것이다. `Rectangle` 이 2가지 책임을 지니고 있기 때문에 분리시켜야 한다. 2가지 모두 `area()` 를 사용하지만 `draw()` 를 사용하지 않는 클래스가 있기 때문에 `area()` 메서드만을 가진 `GeometricRectangle` 클래스를 만들어서 `Rectangle` 클래스가 `GeometricRectangle` 클래스를 가지도록 만든다. 이렇게 책임을 분리시킬 수 있다.

### 개방 - 폐쇄 원칙 (Open-Closed Principle, OCP)

> SOFTWARE ENTITIES (CLASSES, MODULES, FUNCTIONS, ETC.) SHOULD BE OPEN FOR EXTENSION, BUT CLOSED FOR MODIFICATION
>
> 소프트웨어 개체들 (클래스, 모듈, 함수 등) 은 확장에는 열려있어야 하고, 변경에는 닫혀있어야 한다.

https://drive.google.com/file/d/0BwhCYaYDn8EgN2M5MTkwM2EtNWFkZC00ZTI3LWFjZTUtNTFhZGZiYmUzODc1/view

어플리케이션의 요구사항이 변함에 따라서, 기능을 변경하거나 추가할 때가 있다. 그 때 클래스의 소스코드를 변경하는 방법이 있지만, 이 원칙은 변경을 불가능하게 하는 대신 클래스를 재사용 가능한 개체로 여겨서 확장시키는 방법을 사용한다. 예를 들어, `Circle` 클래스와 `Rectangle` 클래스가 모두 `draw()` 라는 메서드를 가진다고 했을 때 차례대로 그려주는 `drawAllShapes()` 라는 함수가 있다고 하자 만약 이것을 C 같은 걸 써서 절차지향으로 해결한다면 어떤 모양이냐에 따라 그 모양의 `draw()` 를 호출할텐데, 이는 새로운 모양이 생길 때마다 해당되는 경우를 추가해줘야 하기 때문에 이 원칙을 위배한다. 이를 충족시키기 위해선 `Shape` 슈퍼 클래스 또는 인터페이스로 추상화시켜서 `drawAllShapes()` 함수가 자동으로 각각의 `draw()` 를 호출하게 함으로서 원칙을 충족시킬 수 있다.

### 리스코프 치환 원칙 (Liscov Substitution Principle, LSP)

> FUNCTIONS THAT USE POINTERS OR REFERENCES TO BASE CLASSES MUST BE ABLE TO USE OBJECTS OF DERIVED CLASSES WITHOUT KNOWING IT
>
> 기반 클래스에 포인터 혹은 참조를 사용하는 함수는 그것을 알지 못한채로 서브 클래스의 객체를 사용할 수 있어야 한다.

https://drive.google.com/file/d/0BwhCYaYDn8EgNzAzZjA5ZmItNjU3NS00MzQ5LTkwYjMtMDJhNDU5ZTM0MTlh/view

명명만으로는 이해하기 힘든 원칙인데, 서브 클래스가 항상 기반 클래스와 호환될 수 있어야 한다는 것으로 서브 클래스가 반드시 기반 클래스의 규약을 모두 지켜야 한다는 말과 동일하다. 가장 유명한 예제는 직사각형(Rectangle)과 정사각형(Square)의 상속관계 규명이다. `Rectangle` 클래스에 너비와 높이를 설정하는 `setWidth` 와 `setHeight` 메서드가 있다고 하자. 수학적으로, 모든 정사각형은 직사각형이기 때문에 `Square` 클래스로 하여금 `Rectangle` 클래스를 상속하게 한다. 하지만, 이렇게 하면 `setWidth` 와 `setHeight` 이 정상적으로 동작할 수가 없다. 애초에, 정사각형의 너비와 높이는 동일해야 하기 때문이다. 따라서 LSP 원칙을 위반하며 직사각형과 정사각형의 관계는 상속관계로 규명할 수 없다는 결론이 나온다.

### 인터페이스 분리 원칙 (Interface Segregation Principle, ISP)

> CLIENTS SHOULD NOT BE FORCED TO DEPEND UPON INTERFACES THAT THEY DO NOT USE
>
> 클라이언트들은 그들이 사용하지 않는 인터페이스를 의존해서는 안된다.

https://drive.google.com/file/d/0BwhCYaYDn8EgOTViYjJhYzMtMzYxMC00MzFjLWJjMzYtOGJiMDc5N2JkYmJi/view

클래스가 자신이 사용하지 않는 인터페이스를 구현하지 말아야 하는 원칙으로, SRP가 클래스의 단일 책임 원칙을 요구한다면 ISP는 인터페이스의 단일 책임 원칙을 요구한다. 복사, 팩스, 프린트 기능을 모두 수행하는 복합기 클래스가 있다고 하자. 프린터/복사/팩스 클라이언트는 모두 이 클래스를 상속하여 원하는 메서드를 호출한다. 하지만 이는 ISP를 위반하는데, 자신이 사용하지 않는 메서드를 가지기 때문이다. 따라서 ISP 원칙을 충족하도록 인터페이스로 분리한다면, 프린터/복사/팩스 인터페이스를 각각 만들어서 각 클라이언트로 하여금 해당 인터페이스를 상속하도록 하면 된다.

### 의존성 역전 원칙 (Dependency Inversion Principle, DIP)

> A. HIGH LEVEL MODULES SHOULD NOT DEPEND UPON LOW LEVEL MODULES. BOTH SHOULD DEPEND UPON ABSTRACTIONS.
>
> B. ABSTRACTIONS SHOULD NOT DEPEND UPON DETAILS. DETAILS SHOULD DEPEND UPON ABSTRACTIONS.
>
> A. 상위 수준의 모듈은 하위 수준의 모듈에 의존해서는 안된다. 모든 모듈이 추상화에 의존해야 한다.
>
> B. 추상화는 상세한 것에 의존해서는 안된다. 상세한 것은 추상화에 의존해야 한다.

예를 들어, 자동차 클래스가 겨울에 사용하는 스노우 타이어 클래스라는 바퀴를 끼고 있다고 하자. 스노우 타이어 클래스를 그대로 사용한다면, 하위 수준의 모듈에 의존하는 형태가 된다. 타이어가 다른 종류로 바뀔 때마다 자동차가 해당 수정에 노출되는 것이다. DIP 원칙을 위배하며, 이를 해결하기 위해 타이어 클래스로 추상화를 한뒤 각 종류의 타이어들로 하여금 타이어 클래스를 상속하는 방식을 사용한다.