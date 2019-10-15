# Software Engineering Week 5 (1)

* **Agile Method가 나온 배경**
  * 사람들이 software process model들이 strict standards를 따라야 한다는 걸 발견하면서 CMMI같은 process model을 평가하는 기준을 만들기 시작했다.
  * 그러다 보니 software process model의 중요성이 너무 부각되고 documentation 부분에서 많은 오버헤드를 발생시키게 되었다.
  * 따라서 코드품질에 기여할 시간은 떨어지고 documentation에만 집중하게 됨으로 전체적인 소프트웨어의 quality가 떨어지게 되었다.
  * 결국 documentation을 통해서 communication을 하기보다 code 자체로 communication을 함으로써 code quality에 좀 더 집중을 하는 방법론이 나오게 된 것이다.
* **Agile Manifesto (애자일 성명서)**
  * [원본링크](http://agilemanifesto.org/iso/ko/manifesto.html)
  * **Individuals and Interactions** over process and tools
  * **Working software** over comprehensive documentation
  * **Customer collaboration** over contract negotiation
  * **Responding to change** over following a plan
* **Agile의 특징**
  * 이전의 process model은 plan-driven에 너무 많은 포커스를 맞추었다.
  * 애자일이 중시하는 것은 developer's capability, code, close communication이다.
  * 애자일의 Key medium은 code itself를 통한 effective communication으로 코드 자체를 통해 소통을 이루어가는 것을 의미한다.
  * 애자일이 무조건 옳은 것인가? 아니다. 프로젝트 특성에 따라서 plan-driven 방식이 좋을 때도 있다.
  * 애자일은 사람이 많아질수록 빌런(못하는 사람)이 생길 가능성이 높아지기 때문에 불안하다.
  * Spiral process model과의 다른 점은 customer involvement가 있다는 것이며 lots of time to code가 있다는 것이다.
* **Extreme Programming(XP)**
  * <img src="http://1.bp.blogspot.com/-ZT-Wpgr8xzc/VHfx9b-a_cI/AAAAAAAAAko/QdbMzvIqQqo/s1600/xp.png">
  * Use case stories를 통해서 requirements를 표현한다.
  * Stories를 쪼갠다.
  * 쪼갠 story들을 통해서 각각 어떤 식으로 진행할지 planning을 한다.
  * Planning한 것들을 통해 incremental development를 한다.
  * 각각 develop한 것들을 merge한다.
  * Customer evaluation을 받고 다시 처음으로 돌아가서 반복한다.

* **XP의 특징들**
  * Refactoring, rewrite the code for better readability
  * Merge, better modularization
  * Pair programming, 코드 짜면서 설명하면 실수를 피하기 쉽다. (awareness가 달라진다.)
  * Collective ownership, specific person만 ownership을 가지면 rational decision이 어렵다.
  * CI(Continuous Integration)을 통해 계속해서 executable version을 가능하게 해야 한다.
  * CI를 통해 빌드 시간을 줄일 수 있고 sanity checking process(new merge bugs)가 가능하다.
  * Test-Driven Developement(TDD)
  * 새로운 것이 아니며 rapidly changing requirements를위한 minimum plan-driven process이다.
* **Nightly build란?**
  * 개발자가 매일 업데이트하거 수정한 것이 빌드되는 것으로 테스트를 거치지 않았을 수 있기 때문에 정식 버전보다 불안정하다.
* Agile method의 가정은 각각의 개발자들이 비슷한 이해와 능력을 가진다는 것이다. 따라서, 빌런이 한 명 있다면 capability가 그 빌런의 capability로 하향평준화 되기 때문에 plan-driven이 나을 수도 있다.