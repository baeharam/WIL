# Software Engineering Week 2 (1)

* 소프트웨어란 무엇인가?

  * 소프트웨어 개발자나 컴퓨터 과학자가 기계 공학자와 다른 점은 무엇인가?
  * 하드웨어는 이미 정의된 기능을 제공하는데, CPU 명령같은 것들이며 굉장히 중요한 계산유닛이다.
  * 소프트웨어는 다른 기능을 만들기 위해서 하드웨어의 기본 기능을 어떻게 구성하느냐에 대한 것이다.
  * 소프트웨어로 구현할 수 있는 모든 것은 하드웨어로 구현할 수 있다.
  * 그러면 왜 소프트웨어 분야가 존재하는가?
    * 소프트웨어 시스템이 계속해서 변화하는 성격을 띠기 때문이다.
    * 컴퓨터를 키면 BIOS가 로드되는데 EEPROM이 동작시킨다. 이건 거의 변화시키거나 다른 것을 변환시킬 우려가 없기 때문에 하드웨어로 분류된다.
    * EEPROM에 쓰이는 로직과 SSD의 로직은 거의 비슷하다.

* **Software is an abstraction of mental activities.**

  * 하드웨어나 사람이나 어떤 조건에 특정화되는 것이 아니라 로직에 대한 추상적인 구현이기 때문에 시대가 변해도 유지된다.
  * 따라서 법(law)과 같은 것도 소프트웨어로 규정할 수 있다. 실행가능하며 추상적인 것을 표현하기 때문이다. Type of software나 Type of logic이라고 할 수 있다.
  * 레시피(recipe) 또한 실행 가능하고, formal meaning을 가지며 objects(contents)는 시대를 넘어서 살아남는다.
  * Syllabus나 Curriculum 또한 동일한 성격이다.
  * Assemble of primitive instruction, composition of instruction.

* 요약

  * A composition of primitive operations, that abstracts intellectual processes for a certain functionality.
  * 소프트웨어는 로직을 항상 담고 있어야 하며 어떠한 의도가 없으면 프로그램이 아니다.
  * 소프트웨어는 항상 의도와 목표(goal) 그리고 기능(functionality)을 가져야 한다.

* 소프트웨어의 특성(Characteristic of software)

  * **Software deals with problems in human society**
    * 컴퓨터는 아주 기본적인 기능만을 구현하지만 우리가 컴퓨터를 사용하는 이유는 <u>좀 더 구체적이고 실용적인 기능을 사용하기 위함</u>이기 때문이므로 소프트웨어가 그 간격을 채워준다고 할 수 있다.
    * 사람은 굉장히 예측 불가능하기 때문에 소프트웨어는 사회의 문제를 다루는 것이다.
    * 예를 들어, 일반적인 자동차라면 예측 가능하지만 자동차를 사람과 상호작용하도록 하는 자율주행 자동차를 사용하면 문제가 복잡해진다.
  * **Software is often complicated**
    * Complex의 의미는 많은 것들이 존재하면서 governing rule이 있는 경우이며 Complicated의 의미는 많은 것들이 존재하는데 governing rule이 없는 것이다.
    * Human society는 굉장히 complicated하다.
  * **Software often has a structure**
  * **Software is mostly a logical entity and has no physical form**
    * 소프트웨어에는 물리적인 형상이 없기 때문에 굉장히 변하기가 쉽다. (Changeability)

* 반도체가 나옴에 따라서 컴퓨터의 계산속도는 exponential 하게 증가하였다.

  * 반도체로 인해 속도가 빨라진 이유는 반도체의 크기가 상당히 작기 때문이다.
  * 작게 만드는 것 + 한방에 엄청많이 만들게 되니까 가격도 떨어져서 수많은 목적으로 컴퓨터를 사용할 수 있게 되었다. (폭발적인 증가)
  * 군대에선 제어와 설계 등을 위해 컴퓨터를 사용하였다.

* 컴퓨터가 급속도로 발전한 후에 60년대 후반에 사람들은 **소프트웨어가 out of control이라는 것을 깨달았다.**

  * 소프트웨어는 늑대인간과 같은 것이어서 처음엔 manageable했지만 점점 갈수록 unmanageable해진다.
  * NASA의 코드 길이는 exponential하게 증가하였으며 프로젝트의 크기가 증가함에 따라서 선형적으로 예측했던 필요시간 또한 exponential하게 증가하였다.
  * 또한 프로젝트의 크기가 커질 수록 실패율은 점점 높아져갔다.

  * 다익스트라, 컴퓨터가 엄청 발전함에 따라 프로그래밍 또한 엄청난 문제가 되었다. 소프트웨어가 하드웨어의 발전 속도를 따라갈 수 없던 것이다. (Scaling matter)
  * 소프트웨어는 fabrication(제작) process로 만들어지는 것이 아니다.

* **그러면 어떻게 좋은 소프트웨어를 만들 것인가?**

  * 다익스트라의 ["Goto Statement considered harmful"](http://www.cs.utexas.edu/users/EWD/ewd02xx/EWD215.PDF) 논문, 프로그래밍을 하는데 있어서 goto문을 사용하게 되면 소프트웨어의 구조를 추정하기가 힘들다는 것이다.
  * 그러면 왜 이게 문제가 되는가? 소프트웨어는 계속 변화하기 때문에 유지보수하기가 어렵다. 따라서 **Structural Programming을 해야 한다.**

* 모래로 어떤 걸 만든다고 했을 때 어떤 구조나 원칙을 정하지 않게 되면 한계가 있을 수밖에 없다. (세계에서 가장 높은 모래성이 16m 정도라는 걸 보면 알 수 있음)

* 소프트웨어도 마찬가지로 **structural component**를 만들어야 하는데 모래를 벽돌로 만드는 것과 같은 것이다. 또한 만들 것에 대한 설계를 해야 하고 어떻게 일을 분배할 것인지에 대한 커뮤니케이션을 해야 하며 여러가지 도구(tool)들을 활용해야 한다.

* Characteristics of Large Software System (or, inherent difficulties of Engineering software)

  * **Complexity**

    * no two parts are alike (같은 부분이 있으면 가져다 쓰자)
    * the complexity of a software artifact increases non-linearly with the size of the artifact
    * 왜? components가 서로 상호작용하기 때문이며 그 상호작용을 simplify하기가 굉장히 힘들다.
    * 설계도 같은것이 잘 안 생기기 때문에 **코드 자체로 이해**하는 것이 좋으며 추상화하는데 일이 든다. (소프트웨어의 Invisibility 속성)
    * there is no single way to abstract software artifacts perfectly
    * 소프트웨어는 기능하는게 있으면 갖다 써야 하며 다시 만들면 잘못하는 것이다. **있는 로직 다시 짜는 것보다 멍청한 짓은 없다.**

  * **Conformity**

    * Software artifacts need to confront arbitrariness of many human artifacts and human society

  * **Changeability**

    * Software artifacts are intangible.
    * Software artifacts are constantly subject to pressure of change.
    * Soft의 의미는 fragile, breakable하다는 의미이다.

  * **Malleability**

    * Software artifacts can quickly become extremely complicated and expensive to change correctly

  * 소프트웨어는 automatic verification이 불가하며 (ex, 정지문제) verify 하는데 상당한 비용이 발생한다.

    * **정지문제(halting problem)**는 임의의 A라는 프로그램에 임의의 B라는 입력을 넣었을 때 언젠가 알고리즘이 끝날 것인가 혹은 영원히 끝나지 않을 것인가에 대한 문제이다.

    * 귀류법을 통해서 증명할 수 있다. 예를 들어 정지문제를 해결하는 알고리즘 H(a,b)가 존재하며 프로그램 a의 입력 값이 b라고 해보자.

    * H(a,b)가 제대로 끝나면 `true`를 반환하고 끝나지 않으면 `false`를 반환하는데 `test`라는 함수를 다음과 같이 만들자.

    * ```c
      function test(string s){
          if(h(s,s)==false) return true;
          else infinite loop
      }
      ```

    * 여기서 만약 `h(s,s)`가 정상종료된다면 `test`함수도 정상종료가 되어야 하지만 `true`를 반환하면 무한루프에 빠지게 되기 때문에 거짓이 된다.

    * 만약 정상종료되지 않아 `false`를 반환할 경우 무한루프에 빠져야 하는데 `true`를 반환하면서 정상종료되기 때문에 거짓이 된다.

    * 따라서 정지문제를 해결할 수 있는 알고리즘은 존재하지 않는다고 할 수 있다.

* What is software engineering?

  * the application of a systematic, disciplined, quantifiable approach to the development, operation, and maintenance of software
  * study of approaches

* **SE(Software Engineering)의 특징**

  * 처음에 대부분의 시스템 명세사항(specification)은 알 수가 없으며 특정한 목표가 정해지지 않는다.
  * 새로운 웹앱을 만들면서 수많은 requirements가 생기며 계속해서 변화하기 때문에 **software는 개발(development)한다고 한다.**
  * SE is essentially to model complex intellectual processes, which requires intellectual effort, creativity and time.
  * SE에서 가장 중요한 부분은 "사람"이며 될놈될이다. **못하는 애들 아무리 한 트럭을 쏟아부어도 잘하는 애 1명보다 못하다.**
  * SE involves multiple persons building multiple versions of SW
  * SE is to manage software changes. (교수님이 좋아하는 정의)
  * SE is to generate not only a program itself but also associated documentation, libraries, data that are needed to make the program useful.