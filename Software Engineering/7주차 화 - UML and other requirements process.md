# Software Engineering Week 6 (1)

* **예시에서 UC를 작성하고 diagram을 만든 상태**
  * UC를 만들고 stakeholders에게 가져가서 회의를 해야 한다.
  * 여기서의 facilitator는 software engineer를 얘기한다.
  * UML는 communication이 core purpose이며 legality는 문제가 되지 않는다.
  * 모두가 만들어진 UC에 동의하면 그때부터 template-based UC를 만들면 된다.
  * 만약 회의를 하던 도중 놓친 부분을 알았다면 rewrite/redraw 한다.
* **UML**
  * Collections of different modeling languages
    * Class diagram, Sequence diagram, Use case diagram, etc.
  * Developed to remove confusion and ambiguity, 상당히 많은 UML notation이 있었기 때문에.
  * 하지만 이게 표준이라고 해도, 꼭 이렇게 해야하는 법은 없으며 표준으로 정해진 방법을 써도 나타내는 표현방식이 틀릴 수 있다.
  * Use case diagram의 목적은 전달하고자 하는 바를 명확하게 표현하는 것이므로 use case를 표현하는 타원형(oval)을 둥근 사각형(rounded rectangle)으로 바꿔도 상관없다.
  * **결국 make it beautiful이 중요한 것이다. 왜? 그것이 직관적(intuitive)이며 명확(clear)하다.**
  * UML Diagram은 자신이 어떤 것을 표현하고 싶을 때 사용하는 것이다.
  * 특정한 것을 강조하기 위함이지, 모든 것을 포괄하기 위해 사용하는 것이 아니다. (abstract, not concrete)
  * 칠판이나 일시적인 커뮤니케이션에서 가장 효과적이다.
  * Index page(목차)라고 생각하면 된다.
  * `<<include>>`를 통해서 sub-activity를 지정할 수 있지만 꼭 그럴 필요는 없다. (자신이 결정하는 것)
* **Requirement Analysis**
  * Conflicts를 identify하는 이유는 negotiation을 위한 strategy를 만들기 위함이다.
  * 만약 same degree of priority가 존재한다면 stakeholders와 회의를 해야 한다.
  * 모든 requirements를 포함하는 structure를 찾아야 하는데 natural language verbal description이 가장 유용하다.
* **Requirement Specification**
  * Rigorous한 specification이 반드시 만들어져야 한다.
  * **모든 specification은 quantify할 수 있는 수준이 되어야 한다.**
  * System/Software requirements specification 모두 SRS라고 부르며 이것을 "계약조건"으로 여기고 프로젝트의 성공과 실패를 판단한다.
  * SRS가 중간에 바뀌면? → 스스로 진화한다, 즉 변화가 발생하면 개발자에게 제출하여 컨펌을 받는다.
  * SRS에 change가 발생하면 추가요금이 생긴다.
* **Requirement Validation**
  * Requirements errors는 심각하기 때문에 validation에 충분한 시간을 투자하여야 한다.
  * Mathematical method를 사용할 수도 있다.
* **Requirement Management**
  * Traceability는 requirement ↔ design ↔ implementation간의 forward/backward 관계를 유지하는 것.
  * Traceability를 manage하는 개발은 model-based development이며 자유도가 ↓ 안정성 ↑
    * 컨트롤러에서 사용한다.
    * Java document를 작성할 때도 이런식으로 작성하지만 여전히 comment가 outdated된 것들이 있는데 이것은 open issues로 남아 있을 수밖에 없다.
* **Activity Diagram**
  * Good to represent flow of control
  * UML의 한 부분이며 적합한 걸 골라서 쓸 수 있어야 한다.