# Software Engineering Week 3 (2)

## Software Engineering Principles and Strategies

* Software engineering is difficult, yet achievable.
* 소프트웨어 공학은 여러가지 작업으로 구성된다.
  * Requirement analysis
  * Software Design
  * Implementation
  * Verification and validation
  * Maintenance
* C 컴파일러를 디자인해보는 것을 통해 여러가지 principles를 알아보자.
* **Rigor and Formality**
  * **Rigor**, target software의 **모델**을 구성하는 것이며 그것을 통해 기대되는 결과를 분석하는 것이다.
  * **Formality**, 소프트웨어를 모델링하기 위해서 **수학**을 사용하는 것이다. Mathematical이란 의미는 unclear한 부분이 없다는 것이며 simplification을 involve한다.
  * 다리를 건설한다고 할 때 다리를 모델링하고 다리에 대한 미분방정식과 같은 것을 세울 수 있다.
  * 모바일 앱 개발을 한다고 했을 때 안드로이드에서의 액티비티 간 전환은 **그래프로 모델링 가능하다.**
  * 또는 웹페이지를 디자인 할 때 HTML 페이지 간의 전환 또한 모델링 가능하지만 javascript 같은 것이 생기게 되면 모델링이 굉장히 복잡해진다. (halting problem에 관한 문제)
  * 그래프 같은 것으로 모델링하게 되면 analyze 할 수 있게 된다.
  * 그러나, 수학적 모델링을 배우는 작업은 일반적인 개발자에게 상당한 시간이 소요되기도 하며 완벽한 모델링은 존재하지 않는다. 따라서 적절한 모델링 수준을 잡아야 한다.
    * 온라인 뱅킹 앱을 개발한다고 했을 때 인증모듈(authentication module)에 대해선 엄격한 수학적 모델링이 필요하지만 GUI모듈에 관해서는 그렇게 엄격할 필요가 없다.
    * 개발자는 rigor와 formality에 대한 **식견이 있어야 한다.** (pros and cons에 대해 확실히 알자)
  * Model-based Development, forward의 경우는 모델링하고 할 수 있지만 back and forth로 접근해야 할 때는 코드와 모델간의 traceability를 유지해야 한다.
  * 컴파일러를 설계한다고 생각해봤을 때는 bug-free를 해야 한다. 그러나 gcc 또한 버그가 존재한다. 너무 강한 최적화는 버그를 발생시킨다.
    * BNF와 같은 formal language를 통해 정의하자.
    * ad hoc manner보다는 formal language에 맞게 parsing을 하게 되면 더 bug-free하다.
    * Generated code가 제대로 작동하는지에 대한 증명 기법을 사용하기도 한다. (지금도 큰 주제임)
* **Separation of Concerns**
  * Many concerns가 interact하기 때문에 회의를 할 때 시간이 낭비될 수 있다. 따라서 concerns에 대해서 separation을 해줘야 한다.
  * Complex problem을 subproblem으로 divide해서 **divide and conquer**를 적용함으로서도 independent한 concerns에 대해 separation을 할 수 있다.
  * Quality attributes에 대한 용어 분리, correctness와 efficiency에 대해 다르게 생각하자.
  * Different View, functional behavior와 temporal behavior에 대해 다르게 생각하자.
  * Separation은 well-made 되었을 때에 좋은 것이며 만약 separation에서 **essential connection을 고려**하지 않게 되면 좋지 않게 된다.
  * Separation은 modularization이 아니라 <u>meta-level의 문제</u>이다.
  * 컴파일러가 branch statement를 binary code로 바꿀 때 multiple concerns가 발생할 수 있다.
    * Correctness
    * Efficiency
    * Generated code의 runtime performance 또한 efficient 해야 한다.
    * Generated code size 또한 다른 문제이다.
    * Usability는 syntax error에 대한 알람에 관한 문제일 수 있다.
    * 이러한 concerns에 대해 separation을 하지 않는다면 결정을 하기가 굉장히 힘들 수 있다. 따라서 separation을 한다음 최종 결정을 내려야 한다.
* **Modularity**
  * Subcategory of Separation in Design
  * 큰 규모의 시스템을 각 모듈로 분리시켜서 **complexity를 줄이는 것이 목적**이다. 이게 가능한 이유는 각 모듈로 구성하게 되면 한번에 다루기가 쉽기 때문이다.
  * 그러나 모듈화를 잘못하게 되면 모듈 간의 의존성이 강해지기 때문에 더 안 좋아질 수가 있다.
  * 만약 너무 기초적인 것들로만 구성되어 있으면 **작은 것들을 묶는 것 또한 모듈화**이다.
  * **좋은 모듈화 설계의 조건**
    * **Decomposability**, complex system을 decomposing하는 능력
    * **Composability**, complex system을 composing하는 능력
    * **Understandability**, 각 모듈에 관련해서 시스템을 이해하는 능력
    * **Modifiability**, 오직 해당 모듈만 변경하는 능력
  * 이러한 속성들에 대해서 어떻게 정량화(quantify) 할 수 있을까?
    * 좋은 모듈화 설계는 high cohesion과 low coupling을 갖는다.
    * High cohesion, 모든 모듈의 요소들이 강하게 연결되어 있어야 한다. (모듈 안쪽)
    * "Single-mindness", 즉 모듈 자체가 conceptual component가 되어 some portion of the program을 represent한다.
    * Low coupling, 다른 모듈끼리 깊게 연관되어 있지 않다.
  * 좋지 않은 모듈화는 하나를 바꾸면 다른 모든 것들이 바뀌게 되는 코드의 경우이다.
  * 컴파일러 최적화 기술이 굉장히 좋기 때문에 readability에 초점을 맞추는 것이 좋다.
  * GCC vs LLVM
    * GCC의 내부구조가 너무 복잡하기 때문에 소수의 사람들만 수정하고 업데이트한다.
    * 그래서 **LLVM**이라는 새로운 프레임워크가 나왔고 **아주 좋은 모듈화**를 갖고 있다. 프론트엔드와 백엔드 모듈 well-made modularization을 갖게 되었다.
  * 모듈화 정도를 quantify하는 것이 좋다.
  * 좋은 모듈화가 reusable 하기 쉬운 이유는 **모듈이 conceptualization되기 때문**이다.
  * 잘못된 모듈화는 퍼포먼스를 떨어뜨린다.
* **Abstraction**
  * Cut-off details, Remain only important thing. Abstraction은 reduction이다.
  * Electrical circuit을 이해한다고 했을 때 R-L-C 모델을 통해 더 좋은 이해를 할 수 있다. 물론 완벽하지 않지만 **기존보다 더 높은 simplicity**를 제공해주기 때문이다.
  * 고급 언어를 사용하는 것이 machine instructions에 대해 abstract 시켜준다.
  * 파일 시스템이 I/O 커뮤니케이션을 abstract 시켜준다.
  * OS-dependent features와 language syntax를 분리하는 것도 abstraction이다.
* **Anticipation**
  * 소프트웨어는 계속 변화하지만 처음부터 예상되는 변화를 고려해서 좋은 설계를 한다면 predictable manner로 변화하게 된다.
  * LLVM의 abstraction은 LLVM bit-code인 intermediate representation이라고 할 수 있다. 이것이 abstraction layer이며 FE와 BE를 깔끔하게 나누게 한다.
  * 소프트웨어를 만들 때는 항상 변화를 고려해야 한다. 따라서 특정한 어떤 목표를 설정하는 것은 충분하지 않고 그에 수반되는 수많은 **변화사항을 포함하는 목표를 설정**해야 하는 것이 상당히 중요하다.
  * 그럼 **어떻게 predictable manner로 design**을 할 수 있는가?
    * 변화할 가능성이 있는 것들에 대해 **isolated module**로 분리시킨다. (MySQL, Mongo DB)
    * Git과 같은 버전관리 시스템을 사용하자.
    * 모든 코드는 다양한 사람들의 기여로 이루어지기 때문에 **직원들의 이직률**을 고려해야 한다.
* **Generality**
  * 기존 솔루션을 사용하는 것이 새로운 것을 개발하는 것보다 낫다.
  * 물론 완벽한 솔루션은 없지만 오랜시간 증명된 해결책이 굉장히 신뢰도가 있기 때문에 중요한 것!
    * 기존 솔루션을 사용하는 것은 구입하는 것으로 이어질 수 있는데 개발발비용과 구입/적용 비용을 고려해야 한다.
* **Incrementality**
  * 처음부터 incremental development에 대한 생각을 가지고 있어라.
  * 개발에 먼저 뛰어들기 전에 여러개의 subproblem으로 나누고 하나씩 정복해나가는 방법을 생각하라.
  * **CI(Continuous Integration)**를 사용해서 더 쉽게 성취할 수 있다.
  * 컴파일러 개발
    1. Core Language
    2. 더 많은 특징들
    3. 계속해서 추가...
* 7개의 원칙을 글로 배운다고 되는 것은 아니지만 더 자세한 것들에 대해 토의하기에 좋은 것이 된다.