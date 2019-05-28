# Software Engineering Week 14 (1)

## Data Flow based Coverage

* CFG의 경우는 프로그램의 흐름만을 가지고 coverage metric을 정의하기 때문에 variable이 정의되고 사용되는 것에 대한 정보를 알 수 없다.
* 따라서 CFG에 라벨을 달아 확장된 방식을 사용하는데 `def(n)` 과 `use(n)` 으로 표기해서 사용한다.
  * `def(n)` : 노드 n에 정의되었다는 것
  * `use(n)` : 노드 n에서 사용되었다는 것
* 하지만 변수의 수는 굉장히 많기 때문에 거의 infinite하다고 할 수 있고, 이걸 abstract 하여 finite set으로 만드는 방식을 사용하고는 한다.

## Def-Use Graph

* CFG를 확장한 그래프의 형태로, def는 정의된 변수의 집합을 나타내고 use는 사용된 변수의 집합을 나타낸다.
* **def-clear with respect to v**
  * 노드 a에서 노드 b로 이어지는 path가 있다고 했을 때, 노드 a와 b에 define 되는 v를 제외하고 path에 속한 다른 노드들에 v가 한번도 정의되지 않으면, 즉 `def`  에 속하지 않으면 v에 관해 def-clear 하다고 말한다.
* **reach**
  * 노드 a에서 노드 b로 이어지는 path가 존재하고 v에 관해 def-clear 할 때, `def(a)` 에 속한 변수가 `use(b)` 에 등장하면 `def(a)` 가 `use(b)` 에 reach 한다고 말한다.
* **du-path**
  * CFG에서 배웠던 simple path이면서 reach하면 a~b의 path는 du-path이다. 따라서, 당연히 reach보다 강한 개념이라고 할 수 있다.
* <u>주의사항</u>
  * 실제코드에서 def-use graph를 만드는 것은 상당히 어려운데, 그 이유는 변수가 참조하는 메모리 주소가 수시로 바뀌는 경우가 있기 때문이다. 예를 들어, pointer de-reference를 하는 경우에 `*p` 와 같은 변수는 사실상 expression이다. 왜냐하면 p의 값, 메모리 주소가 상시로 변할 수 있기 때문이다.
  * 따라서, 포인터 변수인 `p` 를 정의하는 경우에는 `*p` 를 정의하는 것과 동일하게 생각한다.
  * 이런 특성 때문에 모든 메모리 주소에 대한 변수를 정의할 수 없고, abstract하게 되며 def의 가능성이 있는 경우를 다 포함시키게 되는 over-approximation을 하게 된다.

## Def-User Coverage Criteria

* **All-Defs**
  * 먼저 def-path set을 알아야 한다. 임의의 노드에서 시작하는 v에 관한 du-path의 집합을 말하며 이러한 각각의 def-path set으로부터 적어도 하나의 du-path가 test set에 의해 실행될 때, all-defs를 만족한다.
* **All-Uses**
  * 먼저 def-pair set을 알아야 한다. 임의의 노드에서 시작하여 임의의 노드에서 끝나는 v에 관한 du-path의 집합을 말하며 이러한 각각의 def-pair set으로부터 적어도 하나의 du-path가 test set에 의해 실행될 때, all-uses를 만족한다.
* **All-DU-Path**
  * 모든 du-path가 test set에 의해 실행되면 all-du-path를 만족한다.

<img src="https://user-images.githubusercontent.com/35518072/58471261-6745c980-817e-11e9-97c9-ccefce34a4e8.png" width="500px">

위 그래프에서 X에 대해 어떤 테스트 셋이 위 평가 기준들을 충족하는지 알아보자.

* All-Defs
  * 0,1,3,4,6
* All-Uses
  * 0,1,3,4,6
  * 0,1,3,5,6
* All-du-paths
  * 0,1,3,4,6
  * 0,2,3,4,6
  * 0,1,3,5,6
  * 0,2,3,5,6

## 어떤 coverage metric을 사용해야 하는가?

* Node coverage, Edge coverage, Edge-Pair coverage, Complete path coverage, Prime path coverage, condition-decision coverage, MC/DC coverage, data flow based coverage 등에 대해 배웠는데 어떨 때 무엇을 사용해야 좋은 것일까?
* 절대적인 기준은 없고 현재 남은 개발기간과 비용을 체크하면서 그에 맞게 타협하는 것이 좋다. 시간이 별로 안 남았는데 Prime path coverage를 사용한다면 다 못하고 중간에 끝날 수도 있기 때문.
  * 따라서, coarse-grained coverage (약한 coverage) 부터 시작하는 것이 좋다.
* 그렇다면 더 엄격한 coverage metric을 사용하며 더 높은 coverage level을 성취하는 것이 정말로 필요하며 신뢰할 만할까? 라는 의문이 생길 수 있다.
  * 연구결과, high structural/data-flow coverage를 성취하는 것이 high fault detection에 도움된다는 결론이 나왔고 많은 사람들이 이 방법론을 신뢰하게 되었다.

