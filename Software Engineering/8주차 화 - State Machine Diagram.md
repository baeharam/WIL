# Software Engineering Week 8 (1)

* Activity diagram이 들어맞는 시스템이 있고 아닌 시스템이 있다.
* **Control-driven이 아닌 event-driven program에는 state machine diagram이 더 잘 맞다.**
  * 예를 들어, GUI나 Reactive Device Control 등이 있다.
  * 모든 프로그램을 event-driven으로 나타낼 수 있지만 event가 거의 발생하지 않는 경우도 있기 때문에 프로그램의 특성에 맞는 방법을 사용하면 된다.
  * initial state와 final state가 존재한다.
    * 그러나 Wifi 공유기같이 final state가 없이 sequence of response가 sequence of event에 따라 발생할 수 있기 때문에 final state는 항상 존재한다고 할 수 없다.
  * event/action [guard condition] 형태로 나타난다.
  * event가 발생해도 guard condition을 충족시킬 때만 transition이 발생한다.
  * 동일한 event에 대해서 여러개의 transition이 있을 수 있으며 이를 non-deterministic이라고 한다.
* **EX) Telephone**
  * 처음에 `Idle` 상태에 있다.
  * 전화기의 경우 "전화기를 드는(Off-Hook) 이벤트"가 존재한다.
  * 전화기를 드는 이벤트 발생 → guard condition인 "유효한 사용자(valid subscriber)"인지 확인한다.
  * 만족하면 "전화벨 소리"라는 액션이 발생하고 `Held` 상태로 전환된다.
* **EX) Secret Door to Safe Room**
  * <img src="https://user-images.githubusercontent.com/35518072/56207993-0dd08200-608b-11e9-83e7-ad31474b98b7.png">
* **Internal Activities**
  - State안에 여러개의 virtual(artificial) events가 존재하며 event에 따라서 internal state가 정해진다.
  - Multiple transition이 있을 경우엔 virtual event를 통해서 표현할 수 있다.
* **Activity States**
  * State represents some static things, and progress comes from only state transition
  * State 안에서도 progress를 보여주고 싶을 때 사용하며 internal activity와의 다른 점은 internal의 경우 virtual event에 따라 발생하지만 activity state는 procedural한 방식으로 state가 전환되었을 때 "Do" 방식으로 작업을 진행한다.
  * 예를들어 "검색버튼"이 있다고 했을 때 이 버튼을 누르면 "검색 상태"로 가지만 일의 진척도를 볼 수 없다. 따라서 activity state로 state안에 "검색하는 작업"이라는 activity를 넣는 것이다.
* **Nested States**
  * State transition diagram이 다른 state안에 포함된 경우이다.
  * State안의 child states는 parent state의 transition을 전부 inherit한다.
  * cancel과 같이 여러가지 과정 중에서 언제든지 다른 state로 옮겨가는 경우에 nested states를 사용한다.
  * <img src="https://user-images.githubusercontent.com/35518072/56208645-96035700-608c-11e9-9821-0f5c8c3a2daa.png">
* Activity diagram은 one case를 simple하게 나타내지만 state diagram은 infinite cases를 나타낸다.