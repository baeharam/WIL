# Software Engineering Week 2 (2)

* **소프트웨어른 엔지니어링 한다는 것은 무엇인가?**

  * 컴퓨터 분야에서 엔지니어링 한다는 것은 살짝 이상한 용어인데, 보통 컴퓨터 과학(Computer Science)이라던가 전자 공학(Electrical Engineering)이라는 용어를 쓰지 소프트웨어 공학(Software Engineering)이라는 말은 쓰지 않는다.

  * 소프트웨어가 엔지니어링 세계에서 살고 있는 것이며 엔지니어링 세계에서 약간 외계인 같은 존재이다. 그렇기 때문에 소프트웨어 개발자로서 문제를 해결함에 있어서 과학적 접근을 해야 한다.

  * 소프트웨어 엔지니어링은 모르는 문제에 직면했을 때 그 <u>문제를 정의하고 해결하는 것</u>이다.

  * 소프트웨어가 많은 제품들에서 핵심적인 부분이기 때문에 소프트웨어를 엔지니어링 하고 싶어 한다.

  * 소프트웨어 엔지니어링에 대해 알기 위해선 엔지니어링에 대해 알아야 한다.

  * <u>공예(Crafting), 공학(Engineering), 과학(Science)</u>은 어느정도의 겹치는 요소가 있다.

    * 과학과 공학
      * 과학: 자연의 속성에 대해 더욱 깊은 이해를 하려는 것
      * 공학: 자연의 속성을 이용하여 어떠한 이익을 창출하는 것
    * 공학과 공예
      * 공학: 체계적인 프로세스를 통해 어떠한 결과물을 만들어내는 것
      * 공예: 개인의 관점에서 어떠한 결과물을 만들어내는 것

  * 먼저, 소프트웨어와 소프트웨어 개발을 이해해야 한다. 소프트웨어의 중요한 요인(factor)들을 과학적(Scientific)으로 이해하고 이러한 요인들을 제어할 수 있도록 소프트웨어를 개발하는 것이다.

  * > *Systematic application of scientific and technological knowledge, methods and experience to the design, implementation, testing and documentation of software.*
    >
    > *과학적이고 기술적인 지식,방법 그리고 경험을 소프트웨어의 설계,구현,테스트 그리고 문서화에 체계적으로 적용시키는 것*

  * **소프트웨어 엔지니어링을 하기 위해 해야 할 것들**

    1. 우리의 목적인, 달성하고자 하는 소프트웨어의 품질을 정의해야 한다.

    2. 개발하면서 중간중간에 현재 소프트웨어의 품질(quality)을 평가해야 한다.

    3. 위 평가를 기반으로 결정을 내려야 한다.

  * **Series of scientific decision making**, 여러 사람이 협업할 때 굉장히 중요하다.

    * 혼자서 software crafting을 할 수 있지만 여러 사람이 협업할 때는 상당히 어려운데 그 이유는 각자가 생각하는 기준이 다르기 때문이다.
    * 그럼 어떻게 crafting 수준의 품질을 최대한으로? Scientific measurement/deceision을 활용하자.
      * Establish quality metrics (품질 평가 기준을 지정하자)
      * Ensure accountability of software developments (개발자들의 책임을 확실히 하자)
      * Provide methods to control software factors that matters to quality metrics (품질 측정에 중요한 소프트웨어 요소들을 제어할 수 있는 방법을 제공하자)

* **Goodness of software**

  * **사용자의 필요사항에 맞는 것**

    * 사용자가 누구인가? (이 의미는 같은 프로젝트에 여러 종류의 사용자들이 있다는 것이다.)

    * **모든 사용자들의 요구조건을 만족시키는 소프트웨어를 만드는 것은 불가능**

    * > 1. *소프트웨어의 기능을 사용하는 End-Users* (엑셀사용과 같은 것)
      > 2. *고객(Customer)*은 소프트웨어의 비용을 지불하는 사람이다. (결정권자)
      > 3. *같은 프로젝트의 현재 개발자들*, 어떤 개발자가 작성한 코드를 다른 개발자가 쓸 수 있다.
      > 4. *같은 프로젝트의 미래 개발자들*
      > 5. *소프트웨어 시스템을 재사용하는 다른 개발자들*
      > 6. *소프트웨어 시스템과 상호작용하는 다른 소프트웨어 시스템*
      >    * 소니 제품이 엄청 좋았지만 다른 기기와 호환이 안돼서 망했다.
      > 7. *감독관(Supervisior), 조사관(Inspector)*

    * 불편한게 있으면 계속 반복해서 쓰지 말고 고치도록 하자. (히스넷)

  * **Software Quality Attributes (소프트웨어의 품질속성)**

    * 사용자가 누구냐에 따라서 더 강조되는 품질 측면(quality aspects)이 달라지며 그걸 어떻게 조정(coordinate)할 것인지를 알아야 한다.
    * 미식가처럼 **소프트트웨어를 평론할 수 있어야 한다.** 예를 들어, 이 소프트웨어는 다른 소프트웨어에 비해 뭐가 좋고 뭐가 안 좋은지에 대해 알 수 있어야 하는 것.
    * 제품의 기능(Product Operation)은 사용자들에게 더 초점이 맞춰져 있다.
    * 제품의 개선(Product Revision)은 개발자들에게 더 초점이 맞춰져 있다.
    * 제품의 전환(Product Transition)은 고객들과 이 제품을 사용할 다른 시스템들에게 더 초점이 맞춰져 있다.

  * **Correctness(정확성)**

    * **Functional requirements (input and expected output pairs) 에 따라 동작하면 정확한(correct) 소프트웨어라고 말할 수 있다.**
    * **Correctness를 판단**하는 방법은?
      * 수많은 **테스트 케이스**를 돌려보면 된다.
      * 2개의 프로그램이 있으면 각각 몇 퍼센트만큼 정확한지 알 수 있는 것이다.
    * Requirements가 정확하게 정의되고 그 규모가 작다면 쉽게 correctness를 충족시킬 수 있지만 인간 세상이 그렇지가 않고 예측할 수 없다.(unpredictable)
      * Inherent difficulties of software and Halting problem → *No silver bullet*
    * 그럼 어떻게 하라고?
      * Experimental methods and analytic methods
      * 이미 존재하는 algorithms/libraries을 사용하는 것이다. 왜? 이미 잘 짜여져있고 테스트까지 함
    * Soundness(안전성)는 "틀리지  않는 것"

  * **Reliability(신뢰성)**

    * Defined input에 대해서 사용자가 납득할 만한 결과를 도출해 내는가? 즉, functional requirements에 대해 버그가 존재해도 해당 결과를 사용자가 납득할 만한가?
    * Functional requirements를 완벽히 충족시키는 것은 불가능하며 항상 버그가 존재한다.
    * 예를 들어, car navigation system과 같은 경우에 영일대로 가는 길을 알려다라고 할 경우 최단경로를 찾을 텐데 이 때의 functional requirements를 정의할 수 있을까? (혹은 image recognition은?)
    * **모든 correct program은 realibale하지만 역은 성립하지 않는다.**

  * **Robustness(강인성)**

    * **Undefined input에 대해서 사용자가 납득할 만한 결과를 도출해 내는가?**
    * 아예 들어오지 않을 것이라고 생각한 입력이 들어오는 경우
      * HTML이나 JS 문법이 틀려도 웹 브라우저는 robust한데 그 이유는 네트워크로 인해 여러개의 패킷을 잃어버렸을 수 있기 때문이다. (<u>개발자에게 안 좋을 수 있다, 틀린데 계속 동작하니까</u>)
      * 구글의 "이걸 검색하셨나요?" 같은 것
    * 하드웨어의 잘못된 기능에 대해 fault tolerant한 것인데 예를 들어, satellite controller 같은 경우 이상한 방사선으로 인해 데이터가 변동될 여부가 있기 때문에 상당한 반복을 통해서 정밀성을 확인하고 중복을 많이 포함시켜 최대한 정확하게 동작하도록 한다.
    * Robustness는 Correctness와 conflict 할 수 있다. (gene의 엑셀 에러 사건, gene의 스펠링에 대해서 엑셀이 자동적으로 날짜값으로 변환했는데 이것이 gene 네이밍에 에러를 발생시킴)
    * Reliability는 못하는게 없는거고 Robustness는 흔들어도 자기 자리로 돌아가는 것
    * Unexpected니까 예상이 안 될 수는 있지만 범주는 그려줘야 한다.
