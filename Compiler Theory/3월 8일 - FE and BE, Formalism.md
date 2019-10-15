# Compiler Theory - 2 Week (2)

* 컴파일러는 high-level language를 기계에 의해 이해될 수 있는 다른 언어로 바꾸는 translator이다.

  * 이 때 소스코드는 human language가 아닌 high-level language이어야 한다.
  * **Natural Language vs Artificial Language**
  * **Context-sensitive Language(Type-1) vs Context-free Language(Type-2)**
  * 엄밀히 말해서 우리가 쓰는 언어를 context-sensitive language가 모두 표현할 수는 없으며 *natural language가 context-sensitive language를 포함*한다고 할 수 있다.
  * Natural language는 감정을 표현하기 때문 나타내기가 굉장히 어렵다.

* Motivation of using Intermediate Representation

  * IR을 만든 이유는 컴파일러 개발자에게 portability와 maintainability를 제공하여 컴파일러의 업데이트와 수정을 편하게 하게 위함이다.

* **Front-End (Analysis)**

  * Scanner, Parser, Semantic Analyzer
  * FE의 목적은 소스 프로그램을 analyze하여 변수의 type, value, memory location 등을 알아내기 위함.
    * 그래서 엔지니어들이 FE를 analysis라고 부르는 것이다.
  * `int length`에서 `length`는 variable(entity)라고 부르며 `int`로 선언된 것의 의미는 **possible operation을 암시한다.**
    * Addition, Subtraction, Multiplication, Division
    * 이러한 메커니즘을 **Data Encapsulation이라고 부른다.**
  * FE는 분석한 정보들을 활용하여 **IR을 synthesize한다. (Synthesized IR)**

* **Back-End (Synthesis)**

  * Code generator, Code optimizer
  * BE는 **optimized code를 만들어내기 위해 analyze**한다.

* FE에도 synthesis activity가 있고 BE에도 analysis activity가 있기 때문에 FE와 BE라고 부르는게 낫다.

* PDF 빈칸

  * Ideal division of <u>independent modification</u>
  * changing source code - rewriting <u>front-end</u> (changing programming language)
  * changing target code - rewriting <u>back-end</u> (changing target architecture, CPU)
  * depending only on the <u>source code</u> or <u>CPU architecture</u>
  * <u>target</u> independent intermediate <u>synthesis</u>
  * <u>target(CPU)</u>-dependent <u>optimization analysis</u>

* 왜 Optimization이 필요한가? (우리 토픽은 아님)

  * 기계의 일은 대부분 비효율적이다.
  * Object code는 인간이 만든 코드보다 10배 이상 길며 optimization 시키면 코드의 크기를 줄일 수 있다.
  * 기계가 생성한 코드에는 많은 중복연산(redundant operation)이 있으며 이걸 최적화시키는 것이 굉장히 중요하다. 왜냐하면 limited size of memory가 있을 수 있기 때문에.
  * `while (i<=limit-2)` 같은 경우는 `t=limit-2`와 `while(i<=t)`로 바꿔서 CPU 시간을 줄일 수 있다.
  * 이러한 최적화를 위해선 generated code에 analysis 작업을 해야 한다.

* Multiple languages/CPUs를 지원하는 컴파일러를 만들기 위해선 어떤 기술을 써야 할까?

  * 각각의 언어를 컴파일 할 수 있어야 하며 각각의 CPU 아키텍쳐에 맞게 변환을 시켜야 하기 때문에 컴파일러의 크기가 상당히 커질 수 있다.
  * IR의 포맷이 multiple programming languages의 syntax를 수용해야 하기 때문에 굉장히 복잡해지며 variety of CPU architecture를 고려해야 하기 때문에 더 굉장히 복잡해진다.
  * GNU 프로젝트에서 이런 목적의 컴파일러를 만들어냈다.

* **FE의 일**

  * Legal/Illegal program에 대해 판단할 수 있어야 한다.
  * 문법적인 에러에 대해 알아야 한다.
  * IR을 생성할 수 있어야 하가 symbol table을 만들어내야 한다.
    * Symbol table은 memory location을 포함하며 FE와 BE에 모두 쓰인다.
  * 백엔드를 위한 코드를 만들어야 한다.
  * Lex(Flex)와 같이 대부분의 일이 자동화되어야 한다.

* **BE의 일**

  * IR을 target machine code로 변환해야 한다.
  * IR 연산을 수행하기 위해 가장 적합한 명령어를 선택해야 한다.
  * 어떤 값이 어떤 레지스터에 저장할지 알아야 하는데 굉장히 어려운 작업인 것이 레지스터의 수가 한계가 있기 때문이다. (**Graph-Coloring**)
  * 그래프 색칠 문제는 인접한 쌍끼리 같은 색으로 칠하지 않으면서 모든 노드를 칠하는 방법수에 대한 문제이다. NP-완전 문제로 알려져 있다.
  * <img src="https://user-images.githubusercontent.com/35518072/54001704-95bf9400-418f-11e9-8e6f-fd475fc40081.PNG">
  * 4개의 레지스터를 9개의 변수에 할당하는 방법에 관한 문제로 NP 문제이기 때문에 현대 컴퓨터로 해결하는 것이 굉장히 복잡하다고 할 수 있다.
  * 레지스터를 할당할 때는 sequential 하게 할당하여도 된다.

* **Formalism이란 무엇인가?**

  * Formalism은 어떠한 것을 수학적으로 표현하는 것을 말한다.
  * 처음 화살표가 가리키는 지점이 start state이다.
  * 2개의 원이 감싸고 있는 것이 final accepting state이다.
  * empty string이라고 부르는 $\epsilon$으로 쓰는데 imaginary string이다.
  * 입력되는 0이나 1과 같은 것은 alphabet이라고 부르며 $\sum$으로 명시한다.
  * 마지막 상태는 $F$로 명시하며 initial state는 $q_o$라고 명시한다.
  * 또한 transition function은 $\delta$라고 명시하며 표시하는 방법은 2가지 이다.
    * 테이블을 통해 표현한다.
    * $\delta(S_1,0)=S_2$, $\delta(S_2,1)=S_2​$ 등으로 표현한다. 

* Formalism 연습

  * > Construct a DFM that accepts strings except $001$
    >
    > $001​$을 포함하는 substring도 안됨

  * $\sum=\{0,1\}​$

  * $F=\{S_0,S_1,S_2\}$

  * $Q=\{S_0,S_1,S_2,S_3\}​$

  * $q_0=S_0​$

  * 테이블은 그려보면 됨

  * <img src="https://user-images.githubusercontent.com/35518072/54003859-ef778c80-4196-11e9-9c54-52f4026dd2ae.png">