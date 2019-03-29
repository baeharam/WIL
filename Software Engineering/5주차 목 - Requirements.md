# Software Engineering Week 5 (2)

* **Requirements의 정의와 종류**
  * Requirements에는 desirable properties와 constraints가 있는데, 이걸 하나로 묶어서 **"what we want to achieve under constraints"** 라고 말할 수 있다.
  * IEEE의 정의에 따르면 requirements는 contract, standard, specification 등의 different sources에서 온다고 되어 있다.
  * Requirements에는 process의 requirements인 budget, menpower 등이 있고 product의 requirements인 어떠한 function을 만족시켜야 하는 functional requirements와 software quality attributes라고 말하는 non functional requirements가 있다.
* **Descriptive vs Prescriptive requirements**
  * <u>Descriptive requirements</u>는 <u>이미 주어진 것이며 바꿀 수 없고 시스템이 고려해야 할 것들</u>이다.
  * <u>Prescriptive requirements</u>는 <u>처음에 명세되지 않으며 이루어야 할 목표</u>이다.
  * 땅 위에 다리를 짓는다고 할 때 땅이라는 특정한 제약조건(constraints)이 descriptive requirements이고 다리라는 목표를 위한 요구조건(requirements)이 prescriptive requirements이다.
  * Descriptive requirements의 예시, 불변하는 것.
    * Train controller를 설계할 때 "문이 열린 것은 닫히지 않은 것이다"라는 것.
    * Meeting scheduler를 설계할 때 "한 사람이 동시에 두 개의 회의에 참석할 수 없다"라는 것.
* Mathematically speaking으로 real world의 constraints를 원하는 goals로 매핑해주는 하나의 함수를 SW라고 할 수 있으며 SW를 만드는 것은 finding piece of logic이다.
* Descriptive/Prescriptive requirements 모두 중요하며 반드시 개발자들은 **두 가지에 대해 공부해야 한다.**
* **Requirements를 engineering 해야 하는 이유는?**
  * 고객은 기술적인 배경이 없기 때문에 requirements를 잘 말할 수가 없으므로 전문성이 있는 개발자들이 고객이 무엇을 원하는지 이끌어내주어야 한다.
  * 기술을 아는 사람에게 시야(vision)가 있다.
  * Error가 발생하면 recovery cost가 굉장히 높은데, 만약 requirements를 결정하는 step에서 발견하면 그 비용이 낮지만 construction 이후인 maintenance에서 발견하게 되면 비용이 상당히 높아진다.
  * Implementation error는 그렇게 치명적이지 않지만 requirements error는 상당히 치명적이다. (traffic controller system을 생각해보자.)
  * 상세할수록 좋지만 처음에 그렇게 하기가 어렵기 때문에 systematically engineering 해야 한다.
  * Engineering 하는 것은 requirements를 modeling 하는 것이며 구조화해서 incompleteness와 ambiguity 등을 점검하기 위함이다.
* **Requirements Engineering Process**
  * 고객이 원하는 것을 이끌어내야 하며 그것을 기반으로 범위(scope)를 설정해야 한다.
  * 이끌어낸 요구조건들을 모아서 모순이 있거나 동시에 되지 않는 것을 점검한다.
  * 문서, 테스트 케이스 또는 프로토타입이나 시나리오 등을 활용하여 명세를 한다.
  * 요구조건들은 추적할 수 있어야 (traceable) 하는데 이 말은 추상적인 수준과 구체적인 수준이 연결되어야 한다는 것이다. (traceability가 있을 때만 test case를 잘 쓸 수 있다.)
  * Traceability가 있으면 어디에 문제가 생겼을 때 어떤 requirements 때문에 생긴 문제들인지 알 수 있기 때문이다.