# Software Engineering Week 4 (1)

## Software Quality Review

* **Challenges (Quality Management)**
  * 여러가지 소프트웨어 품질에 대해서 높은 품질을 성취하기 위해선??
  * Software quality에 대해 측정할 수 있고 보장할 수 있어야 한다.
  * **Measurement of software quality**
    * Living software activities를 측정해야 하는데 이것이 어려움.
    * Enough quality가 어디까지인지 알아야 하는 것이 문제이다.
    * Absolute global standard라는 측정방법이 없다.
    * 개발을 하면서 계속해서 quality report를 작성하는 것 또한 cost가 든다.
    * Quality를 quantify 한다고 하는 것조차 어떤 건지 정해져 있지 않다.
  * **Cost of assuring software quality**
    * Measure 할 수 있다고 가정해도 좋은 소프트웨어를 만드는 데 cost가 많이 든다.
    * **High prevention cost + Low failure cost가 reasonable**해야 하는데 predict하기가 어렵다.
    * 수많은 고려사항을 통해 failure cost를 예측할 수 있지만 굉장히 어렵다.
    * 개발 방법론 또한 개발되어야 하며 project마다 unique하다.

## Software Process Model

* **How can we control the quality of software?**
* ***If the process is right, the result will take care of themselves***
  * Making each step correctly, it is a procedural job (Grade in this semester)
  * 마지막 점수는 이전에 한 것들에게서 나오게 되기 때문에 단계적으로 정확하게 수행하지 않는다면 결국 out of control하게 된다.
  * 소프트웨어도 한번에 만들어지거나 개선되는 것이 아니라 시간을 들여서 점진적으로 해야 한다.
* 시대가 달라짐에 따라 사람들의 커뮤니케이션이 필요해졌고 사람들의 지식수준이 낮아졌다. 또한 자신을 위한 프로그램이 아니라 남을 위한 프로그램을 한다. 90년대까지는 다같이 모여서 하는 것으로 여겨졌다.
* 하지만 요즘에는 시장의 경쟁이 굉장히 심해졌고 사용자들은 더 나은 어플리케이션을 사용한다. 달력 앱이 있다면 다른 어플로 언제든지 옮겨갈 수 있다.
* Requirements는 customer가 아닌 market으로부터 오게 되었다. 따라서 소프트웨어 프로세스의 본질(Essence)에 대해서 이해해야 한다.
* **Project management**
  * 결정, 비용 추정, 일정 관리, 일 분배 등이 이에 해당한다.
  * 몇몇 사람들은 이것을 business job이라고 생각하지만 engineer가 직접 해야하는 부분이다.
  * Self management 능력을 길러야 한다.
* **Software Process**
  * Planned way to conduct software engineering, 작업들을 순서대로 해나가는 것을 말한다.
  * Efficient(효율적) 하고 effective(효과적) 한 것이다.
  * 수많은 activities와 expectation이 존재한다.
  * <u>Activity간에 dependencies를 먼저 이해</u>해야 한다. (sequential task에 대한 이해)
* **Software Engineering Activities**
  * **Framework activities**
    * Requirements는 고객에게 있기 때문에 communication이 필요하다.
    * 계획(Planning)
    * 솔루션을 한번에 찾을 수 없기 때문에 모델링을 해야 한다. (Divide and Conquer manner)
    * 구현 (Implementation)
    * 배포, migrate software to target
    * 이 모든 활동들은 각각 다른 특성들을 가지며 좋은 개발자란 이런 다채로운 활동들을 잘 하는 개발자를 말한다.
  * **Umbrella activities** (aspects embedded in framework activities)
    * Project tracking
    * Risk management (government regulation change, turnover, etc)
    * Quality assurance
* **Process Model**
  * Ancient solution은 outdated 될 수 있고 new solution이 나오지만 그 중에서도 **변하지 않는 것이 있다.**
  * **Waterfall Model**
    * Some framework activities에 대해선 strict order가 있다.
    * 특정 단계를 모두 마무리 한 후에 다음 단계가 이어지며 이전 단계를 다시 하지 않는다.
    * **Requirements가 well-defined 되기 전에 프로그램을 개발해서는 안된다.**
    * 이런식으로 하는 이유는 requirements가 제대로 설정되지 않으면 goal이 unclear하지 않고 계속해서 여러가지 문제가 발생하기 때문이며 소프트웨어는 고치는 것이 상당히 어렵기 때문에 처음부터 제대로 하는 것이 좋다.
    * Documentation도 abstract 한 것에서 concrete한 것으로 이어지며 프로세스의 각 단계에서 문서화를 계속해서 하며 그 다음 단계에 넘겨준다.
    * 한 단계가 끝날 때마다 어느 정도를 만족시켜야 하는지에 대한 measurement는 quality documentation으로 할 수 있다.
    * 전체적인 단계는 연속적이지만 개별 단계에서 병렬적으로 일을 나눌 수 있다.
    * Strict ordering을 통해 얻을 수 있는 것은 different activities 간의 communication cost가 줄어든다는 것이다. (Key Idea)
    * **해당 모델을 쓰기에 좋은 조건**
      * <u>Requirement가 complex하거나 stable할 때 좋다.</u>
      * <u>마켓에 출시하는 시간이 중요하지 않을 때 좋다.</u>
      * <u>Result flow의 manage가 잘 될 때 좋다.</u>
      * 우주선은 과학자들이 requirements를 만들기 때문에 stable하고 잘 바뀌지 않으며 시장에 대한 압박이 거의 없기 때문에 waterfall model에 적합하다.
      * 수업(class) 또한 하나하나 이해한다는 관점에서 비슷하다.
    * **해당 모델의 단점은?**
      * Requirements가 계속해서 바뀌는 경우에는 redo 할 수 없기 때문에 적합하지 않다.
      * 중간에 bottleneck이 있으면 다음 단계로 진행할 수 없다.
    * 오래된 아이디어지만 **requirements를 확실히 해야 한다는 것은 아직까지도 굉장히 중요**하다. 몇몇 사람들은 구식이라고 하지만 몇몇 도메인에선 굉장히 유용하다.
    * 각각의 아이디어는 몇몇 부분들이 계속해서 살아남으며 이런 프로세스의 스펙트럼이 존재하게 된다. 따라서 좋은 엔지니어는 스펙트럼을 각 도메인에 맞게 잘 이용해야 한다.