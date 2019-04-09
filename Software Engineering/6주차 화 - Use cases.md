# Software Engineering Week 6 (1)

* **Requirement Elicitation**
  * 인터페이스에 대한 이해가 있어야 한다.
    * Ambulance dispatch center의 software를 만든다고 하면 주변의 hospital, traffic controller system 등에 대한 이해가 있어야 한다.
  * Identify stakeholders
    * 누구한테 무엇을 원하는지 물어볼 것을 이해하는 것도 엔지니어링의 일부이다.
  * Acquire the domain knowledge
    * Medical device에 들어갈 software를 만든다고 하면 해당 분야의 의학지식에 대한 어느정도의 이해가 있어야 한다.
    * 따라서, domain knowledge에 대해서도 끊임없이 학습해야 한다.
    * Domain knowledge를 얻기 위해 collaborative session, interview, role play등을 하기도 한다.
  * Define boundary
    * 어떤 것을 다루는가에 대한 이해도 필요하다.
* **Requirement elicitation이 어려운 이유는?**
  * 일의 boundary 자체가 흐릿하고 명확하지 않다.
  * 고객이 자신들이 뭘 원하는지에 대해 모르며 multiple stakeholders가 연관되어 있는 경우에는 requirements를 만드는 프로젝트를 따로 하기도 한다.
  * Target이 변할 수 있으므로 requirements 또한 계속 바뀔 수 있다.
  * 결론은 이러한 문제점들을 제거할 수 없으므로 갖고가는 방법을 찾아야 한다.
* **Use case scenario writing**
  * Movie making과 굉장히 비슷하며 상황이 상세하게 주어지기 때문에 요구조건을 뽑아내기에 매우 좋다.
  * Use case는 사용자의 상호작용(interaction)과 관련된 시나리오들의 집합이다.
  * 장점
    * 프로그래밍 언어와 도표같은 것을 쓰지 않기 때문에 이해하기가 쉽다.
    * 테스트 케이스를 도출해낼 수도 있다.
    * 사용자 문서(User documentation)로도 쓸 수 있다.
    * 구현기술(Implementation technology)과 독립적이다.
  * Use case를 여러개 찾다보면 서로 묶이는 것들도 존재한다.
  * 굉장히 simple하다고 하는데 그 이유는 2가지가 있다.
    * 그렇게 상세하게 쓰지 않는다.
    * 포괄적인 예외 경우를 모두 포함하지 않는다. (1 representative case)
  * 주의사항
    * 목표(Goal)가 실패할 수 있으므로 variations of scenario 또한 이끌어내야 한다.
    * 상호작용은 여러개의 단계로 이루어진다.
    * 시나리오가 stakeholders에게 확실히 중요해야 한다.
  * 배우(Actor)
    * Actor는 한 가지 역할만 해야 한다.
    * Actor가 어떠한 context도 될 수 있다.
    * Actor는 proactive behavior를 해야 한다. 예를 들어, ATM을 설계한다고 했을 때 카드가 자체적인 인증계산을 하기 때문에 actor가 될 수 있다.
    * Actor에는 primary actor, supporting actor, offstage actor가 존재한다.
    * Actor를 identify하는 것과 use case를 작성함으로써 생각이 정리되는 것이 goal이다.
    * Actor의 boundary를 그리는 것도 중요하다.
    * <img src="https://user-images.githubusercontent.com/35518072/55471217-39437d80-5644-11e9-82ac-6d04ad47d8e6.PNG">
  * 조건(condition)
    * 항상 참이라는 가정인 precondition이 존재한다.
    * 어떠한 이벤트를 발생시키는 trigger condition이 존재하는데, 예를 들어 캐셔가 계산을 하기 전에 고객이 상점에 들어온다라는 조건이 있을 수 있다.++
    * Precondition이 만족되지 않았을 경우에는 trigger가 발생되면 안된다.
    * Use case가 성공했을 때 만족시켜야 하는 postcondition이 존재한다.
  * 하나의 use case는 multiple scenarios로 구성되며 다음을 포함한다.
    * Main scenario (sunny-day scenario), 가장 최적의 시나리오로 모든 것이 잘 이루어지는 시나리오.
    * Variants (branches), 메인 시나리오를 축으로 계속해서 곁가지로 만들어지는 시나리오.