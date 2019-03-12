# Article 읽고 정리

* **External versus Internal Qualities**
  * External quality는 사용자 입장에서의 품질기준
  * Internal quality는 개발자 입장에서의 품질기준
  * External quality와 internal quality는 서로 긴밀하게 연관되어 있다.
    * 예를 들어 verifiability의 internal quality는 reliability의 external quality를 개선해준다.
* **Product and Process Qualities**
  * 소프트웨어 제품 그 자체가 product이며 이 제품을 생산하는 과정이 process인데, 2개의 품질 관계 또한 연관성이 존재한다.
  * 사용자 입장에서의 제품인 product는 실행가능하며 메뉴얼이 있는 것을 말하지만 개발자 입장에선 중간제품(intermediate product) 또한 product가 될 수 있다.
  * Intermediate product는 process의 중간 산물이라고 할 수 있으며 work product라고도 부른다.
  * Work product는 다양한 고객들에게 팔 수 있는 제품이다.
  * Configuration management는 다양한 버전의 같은 제품을 제어하고 운용하는데 사용되는 process이다.

## Representative Qualities

* **Correctness**
  * ***A program is functionally correct if it behaves according to its stated functional specifications.***
  * 소프트웨어가 명세사항을 얼마나 만족시키는지 보여주는 수학적 속성이다.
  * Correctness를 판단할 때는 실험적 접근(experimental approach)과 분석적 접근(analytic approach)을 사용할 수 있다.
  * Correctness는 고급언어를 사용하거나, 기존 라이브러리를 사용하는것 또는 입증된 방법론이나 프로세스를 사용하는 것으로 개선될 수 있다.
* **Reliability**
  * ***If the consequence of a software error is not serious, the incorrect software may still be reliable.***
  * 공학적 제품은 reliable하게 만들어져야 하지만 소프트웨어의 특성상 버그가 존재할 수밖에 없다.
  * 모든 realiable program은 correct program을 포함하지만 역은 거짓이다.
  * Functional requirements가 완벽하지 않을 수 있기 때문에 correctness가 완벽해도 "기대한" 결과가 나오지 않을 수 있다.
* **Robustness**
  * ***A program is robust if it behaves "reasonably", even in circumstances that were not anticipated in the requirements specification.***
  * 잘못된 입력이나 하드웨어의 잘못된 기능으로 인한 상황에도 사용자가 받아들일만하게 동작해야 한다.
  * 사용법을 잘 모르는 사용자의 경우 ill-formatted input이 많을 수 있기 때문에 more robust 해야 한다.
  * Specification에 명세한 requirements가 아닐 경우 robustness에 대한 문제이며 맞을 경우 그것을 성취하는 것이 바로 correctness의 문제이다.
  * Correctness, Reliability 그리고 Robustness는 software process quality에도 사용될 수 있다.
    * 새로운 운영체제가 생기거나 아니면 직원들이 이직하는 등의 예기치 못한 문제가 발생했을 때 이러한 문제들을 프로세스가 수용할 수 있으면 robustness가 높은 것이다.
    * 프로세스가 꾸준히 좋은 품질의 제품을 만들어낸다면 reliability가 높은 것이다.
* **Performance**
  * ***External quality based on user requirements***
  * Performance는 usability에 영향을 미칠 수 있는데 너무 느리거나, 메모리를 많이 사용한다거나 아니면 공간을 많이 차지할 경우 많은 문제를 일으킬 수 있기 때문이다.
  * 또한 scalability에 영향을 미치는데, 별로 성능이 좋지 않은 알고리즘을 사용할 경우 규모를 키우기가 어렵기 때문이다.
  * Performance를 어떻게 평가할 것인가?
    * 시스템이 실행되고 있을 때 하드웨어/소프트웨어 모니터를 통해서 데이터를 모으고 병목지점(bottleneck)들을 발견할 수 있다.
    * 제품을 모델링하고 분석한다. (모델링하기 쉽지만 부정확)
    * 제품을 구동하는 모델을 만든다.(정확하지만 비쌈)
    * 각자의 장단점이 있기 때문에 섞어쓰기도 한다.
    * 그러나 아주 간단한 모델이라도 <u>새로운 설계의 필요성</u>을 최대한으로 줄일 수 있게 도움을 준다.
    * Process의 관점에선 productivity에 적용될 수 있다.
* **Usability**
  * ***A software system is usable (or user friendly) if its human users find it easy to use.***
  * UI가 굉장히 중요한 요소이며 새로운 사용자와 숙련된 사용자에게 다르게 느껴질 수 있는데, 새로운 사용자는 GUI환경에 익숙한 반면 숙련된 사용자는 CLI환경이 더 편할 수 있다.
  * Correctness나 performance 같은 다른 품질들도 usability에 영향을 미친다.
    * 잘못된 답을 내놓는 소프트웨어 시스템은 사용자에게 친숙하지 않다.
    * 답을 굉장히 느리게 내놓는 소프트웨어 시스템 또한 친숙하지 않다.
  * Ease of use는 표준화(standardization)를 통해 성취되기도 하며 이는 TV를 한번 사용하면 다른 TV도 사용할 수 있는 것을 의미한다.
* **Verifiability**
  * ***A software system is verifiable if its properties can be verified easily***
  * Verification은 소프트웨어 시스템의 correctness나 performance를 verify하는 것이며 공식적이거나 비공식적인 분석방법 또는 테스트를 통해 이뤄질 수 있다.
  * Verifiability를 개선하기 위해선 "software monitors"를 사용해서 performance나 correctness를 감시하거나 모듈화 설계, 숙련된 코딩 습관 또는 적절한 프로그래밍 언어 등이 사용될 수 있다.
  * Internal quality라고 할 수 있지만 security-critical 프로그램에선 external quality로 여겨질 수 있다.
* **Maintainability**
  * ***Software maintenance is commonly used to refer to the modifications that are made to a software system after its initial release***
  * Maintenance 비용은 전체 비용의 60%를 초과하며 관례적으로 3가지로 나눈다.
    * Corrective maintenance, 20%를 차지하며 남아있는 에러를 제거하는 것이다.
    * Adaptive maintenance, 20%를 차지하며 소프트웨어가 적용되는 환경이 바뀌었을 때 소프트웨어를 해당 환경에 맞추는 것이다.
    * Perfective maintenance, 50%를 넘게 차지하며 개발자나 고객이 요구하는 사항으로 품질을 개선하는 것이다.
  * 2가지 품질로도 볼 수 있는데 Reparability와 Evolvability이다.
* **Maintainability (1) - Reparability**
  * ***A software system is repairable if its defects can be corrected with a reasonable amount of work***
  * 다른 공학 분야는 표준화를 사용하여 닳거나 쇠한 부분을 교체하면 되지만 소프트웨어는 닳는 것이 아니기 때문에 시간과 비용을 줄일 수는 있어도 reparability에 대한 부분은 아니다.
  * 소프트웨어가 잘 설계된 모듈들로 구성되어 있는 것이 하나 그 자체로 있는 것보다 repair하기 쉽기 때문에 적절한 모듈화(modularization)가 reparability를 증진시켜준다고 할 수 있다.
  * 어셈블리어보다 고급 언어를 사용함으로서도 reparability를 개선할 수 있다.
  * Reliability가 좋을수록 reparability에 대한 필요성은 낮아진다.
* **Maintainability (2) - Evolvability**
  * ***If the software is designed with evolution in mind, and if each modification is designed and applied carefully, then the software can evolve gracefully***
  * Evolvability는 적절한 모듈화를 통해 성취되지만 예상치 못한 변화가 시스템의 모듈성을 줄일 수 있다.
  * 하드웨어가 발전함에 따라서 소프트웨어와의 균형을 맞춰야 하기 때문에 production의 cost와 complexity가 높아질수록 evolvability는 중요해진다.
  * 큰 규모의 시스템은 소프트웨어 제품의 릴리즈가 많이 될 수록 evolvability가 떨어지며 이 문제를 해결하기 위해선 처음 설계를 할 때 evolvability에 대해 염두해 두어야 한다.
  * Evolvability는 경제적 영향력이 있기 때문에 중요한 quality라고 할 수 있다.
* **Reusability**
  * ***In product reuse, we use the product - perharps with minor changes - to build another product***
  * UNIX의 쉘이나 C/C++의 라이브러리 같은 것이 reusable product라고 할 수 있다.
  * Reusability 연구자들의 한가지 목표는 재사용될 수 있는 컴포넌트들의 granularity를 증가시키는 것이다.
  * Artifact가 더 모듈화 될수록 추후에 재사용될 가능성이 더욱 높아진다.
  * Software production process 또한 reuse 될 수 있으며 다른 제품들을 만들기 위해 같은 프로세스를 사용하는 것이 그 예이다.
* **Portability**
  * ***Software is portable if it can run in different environments***
  * Portability는 소프트웨어를 모듈화함으로서 얻을 수 있는데, 다른 환경에 포팅해야 할 의존성을 가진 것들을 적은 양의 모듈로서 구성하면 되기 때문이다.
  * UNIX나 Linux가 machine-specfic software이지만 그럼에도 불구하고 포팅하는 것이 처음부터 새로 짜는 것보다 더 낫기 때문에 어느정도의 portability를 가진다고 할 수 있다.
* **Understandability**
  * ***Some software systems are easier to understand than others***
  * 추상화와 모듈화는 시스템의 understandability를 향상시켜준다.
  * 대부분의 maintenance 시간은 프로그램의 로직을 이해하는 시간이므로 굉장히 중요한 속성이다.
  * Internal product quality이지만 external로 본다면 예측 가능한 행동을 보일 경우에 understandable 하기 때문에 usability로 볼 수도 있다.
* **Interoperability**
  * ***"Interoperability" refers to the ability of a system to coexist and cooperate with other systems***
  * 판매자가 다른 제품들을 생산하여 사용자들에게 필요하다면 같이 쓰게 할 수 있다.
  * 인터페이스의 표준화를 통해 성취할 수 있다.
  * "Open System"은 인터페이스를 공개하여 여러개의 어플리케이션이 상호 호환될 수 있게 한다.
* **Productivity**
  * ***Productivity is a quality of the software production process, referring to its efficiency and performance.***
  * 각각의 individual productivity는 다르며 팀에선 individual productivity를 적절히 활용해서 전체의 productivity를 향상시켜야 한다.
  * 소프트웨어의 재사용은 productivity를 증가시키는 테크닉 중에 하나이다.
* **Timeliness**
  * ***Timeliness is a process-related quality that refers to the ability to deliver a product on time***
  * Timeliness는 주의깊은 일정조정, 작업에 대한 정확한 추정, 그리고 명확하게 명세되고 입증 가능한 목표 지점(마일스톤)을 요구한다.
  * 표준 프로젝트 관리 기술은 소프트웨어의 requirements를 정의하는데 있는 inherent difficulties와 추상적인 성질 때문에 적용하기 어렵다.
  * 사용자의 requirements를 계속해서 변화시키기 때문에도 timeliness를 성취하기 어렵다.
  * Timeliness를 달성하기 위해선 incremental delivery를 사용해야 한다.
    * 프로그램을 사용하게 해주고 requirements에 대해 점점 refine해가는 방법이다.
    * Incrementall delivery가 정의되지 않거나 불필요할 때가 있을 수 있으니 적절히 사용하도록 하자.
* **Visibility**
  * ***A software development process is visible if all of its steps and its current status are documented early***
  * 프로젝트의 현재 상태를 알지 못하는 채로 계속 진행하는 것은 심각한 문제를 초래할 수 있다.
  * Visibility는 팀 멤버들끼리 같은 방향으로 협업할 수 있도록 만들어준다.
  * 고객에게 현재 어느 정도까지 진행되었는지 알려줄 때도 쓰인다.
  * 각 단계의 문서화가 잘 되어야 하며 requirements/design specification과 같은 중간 산물들의 현재 상태에 대해서 정확하게 유지해야 한다.