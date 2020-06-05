# Software Engineering Week 13 (2)

## 왜 다른 coverage metric이 더 필요한가?

Graph coverage는 상당히 좋은 모델링 방법이지만, 모든 소프트웨어의 실행이 graph로 표현되지 않을 수 있으며 graph로 표현하는데는 한계가 있을 수 있다. 예를 들어 branching coverage를 생각해보자. 분기를 나누는 경우 true 혹은 false 같이 이분법적으로 나눌 수 있지만 상당히 복잡한 조건을 통해 하나의 decision이 결정될 수도 있다. 하지만 그러한 복잡한 조건문이 하나의 그래프 노드에 박혀버린다면 버그 찾기가 굉장히 어려울 것이다. 따라서 버그를 쉽게 찾을 수 있는 다른 metric이 필요한 상황에서 우리는 logic coverage metric을 사용할 수 있다.

## Logic Coverage Metric

* **Basic Condition**
  * 어떠한 boolean expression에 sub-expression으로 다른 boolean expression이 포함되지 않을 경우를 basic condition이라고 한다.
  * 예를 들어, `a>0 && (b<0 && b+a>2)` 같은 경우에 basic condition을 뽑아보자.
    * `a>0`, `b<0`, `b+a>2` 3개의 boolean expression이 basic condition이 될 수 있다.
    * 왜냐하면 각각의 sub-expression이 expression은 맞지만 boolean expression은 아니기 때문이다.
  * 그렇다면 basic condition을 100% 만족하는 것이 branching coverage를 100% 만족하는 것을 포함할까?
    * 아니다. 그 이유는 branching coverage에 multiple condition이 들어갈 수 있기 때문!!
    * 따라서 사람들이, 2가지를 충족시킬수는 없을까 하고 만들어낸 것이 바로 condition/decision coverage이다.
    * 하지만 condition/decision coverage 또한 primitive 한 것과 완전 compound 한 boolean expression에 대해서만 점검할 수 있기 때문에 ad hoc이라고 할 수 있다.
* **Multiple Condition**
  * 그렇다면 모든 케이스를 고려할 수는 없을까? → true/false이기 때문에 exponential하게 경우의 수가 증가한다.
  * 따라서 너무 많고 현실적으로 불가능할 뿐만 아니라 중복적인 경우도 많다.
* **Modified condition/decision coverage (MC/DC)**
  * 그래서 나온 것이 바로 MC/DC coverage로 실무에서 상당히 많이 쓰인다고 한다.
  * Decision의 evaluation이 basic condition의 조합으로 결정되는 방법으로 특정 basic condition을 flip 시킴에 따라 decision이 어떻게 evaluate 되는지 점검할 수 있다.
  * 즉, 특정 basic condition에 따른 boundary condition을 이끌어 낼 수 있는 것이며 multiple condition보다 경우의 수가 훨씬 줄어든다는 것을 알 수 있다.