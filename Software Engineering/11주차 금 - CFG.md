# Software Engineering Week 11

## Test Coverage

* **Test Requirement**
  * 테스크케이스에 대해서 만족을 하는지 못하는지에 대한 여부이다.
  * 이것을 통해 하나의 체크리스트를 만들 수 있고 프로그램이 그 체크리스트의 항목들을 만족하는지 점검하는 방식을 사용할 수 있다.
* **Coverage Metrics**
  * 주어진 리소스들(코드, 실행파일, 문서, 등)을 통해서 test requirement를 이끌어내는 방법이다.
  * 예를 들어, line coverage란 코드의 한 줄, 한 줄이 실행되는지 확인한다는 predicate(참/거짓으로 결론짓는 문장)을 생성하는 방법이다.
  * 따라서, 100줄의 코드가 있다면 line coverage를 통해서 100개의 서로다른 test requirement를 만들게 된다.
* **Coverage (coverage level)**
  * 전체 test requirement에 비해서 test set을 만족하는 test requirement의 비율이다.
  * 여기서 test set이란 test case가 아니며 a set of program execution을 말한다.
  * 비율이기 때문에 0과 1사이이고 single value이다.
* **Coverage Criterion**
  * 어느정도의 coverage level을 달성해야 하는지에 대한 조건이라고 할 수 있다.
  * Goal은 coverage level을 1로 하는 것이겠지만 실제적으론, defensive programming이라고 불리는 unspecified part에 대해 대비하는 예외처리적인 프로그래밍을 하기 때문에 불가능하다.
  * 실제로, 70~80%의 coverage를 달성하면 굉장히 높은 비율이다.
  * 다른 infeasible code의 경우는 variation code for different platforms가 있다.

## Graph Models of Program Behaviors

* 소스 코드, 설계, 문서 등은 하나의 그래프적인 방법으로 표현할 수 있다.
* 그래프적으로 표현하는 것을 모델을 만든다고 하며 모델이란 원본의 단순화(simplification of original)이면서 동시에 원본에서 중요하게 생각하는 것을 가진다.
* Graph-like model로부터 test requirement를 derive 할 수 있다.
* 대부분 방향 그래프로 표현하며, function call을 나타내는 call graph의 경우와 같이 execution path를 나타낼 수 있다.

## Control Flow Graph (CFG)

* 모델의 실행을 instruction execution의 sequence로 본다.
* Different execution이 different path를 만든다. 그러나 CFG에 path가 있다고 해도 program에는 없을 수 있다.
  * CFG가 program을 포함하는 관계로 program에 대한 over-approximation이다.
  * 따라서, CFG는 program에 존재하지 않는 path를 가질 수 있다.
  * 물론, CFG에 존재하지 않는 path를 program이 가지는 것은 불가능이다.
* **CFG의 구축**
  * 노드(Node): Code block에서 같이 실행될 수 밖에 없는 코드의 continuous region을 지칭하며 basic block이라고도 한다.
  * 간선(Edge): Basic block 사이의 transition이다.
* **Basic block을 찾는 방법은??**
  1. 코드를 어셈블리 코드로 변환한다.
  2. 반드시 같이 실행되는 부분을 찾을 수 있고 그것들을 묶은 continuous region을 찾을 수 있다.
  3. 해당 region이 basic block이다.