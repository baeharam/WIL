# Compiler Theory - 3 Week (1)

* DFA(Deterministic Finite Automaton, Deterministic Finite Acceptor)
  * 컴퓨터는 이러한 이론적인 기계의 실제적인 구현이다.
* **다시 한 번 촘스키 계층**
  * Type 0 language is recognized by <u>Turing Machine</u>
    * 튜링머신의 실제적인 구현이 우리가 사용하는 컴퓨터
    * Recursively-Enumerable Language (Halting problem을 다룬다.)
    * 튜링머신은 모든 걸 포함하지만 어떤 language는 recognize 할 수 없다.
    * Recognize 할 수 없다는 것은 무한루프에 들어간다는 의미하며 enumerable이라는 것이 무한루프를 말한다.
  * Type 1 language is recognized by <u>Linear Bounded Automata</u>
    * Context-Sensitive Language
  * Type 2 language is recognized by <u>Push Down Automata</u>
    * Context-Free Language
    * Automatic Tools: Bison(Yacc)
  * Type 3 language is recognized by <u>Finite State Automata</u>(가장 낮은 레벨)
    * Regular Language
    * Automatic Tools: Lex(Flex)
* **FSM(Finite State Machine)**
  * Finite number of states만을 허용한다. (Finite이라는 의미를 이해해야 한다.)
  * Unlimited number of states라면 현대의 컴퓨터로 계산할 수 없다.
  * Deterministic이란 unique transition이라는 것을 의미한다. (전이가 정해져 있다) 예를 들어, 상태 $q_0$에서 입력값 $a​$를 통해 2개 이상의 상태로 가게 되면 non-deterministic이다.
  * 단 하나의 *start state*만을 가지면서 여러개의 *final states(=accept states)*를 가질 수 있다.
  * DFA는 추상적인 수학적 개념이며 DFA를 통해서 Type 3 language인 regular language를 recognize한다.
  * Regular language는 scanner의 lexical analysis에 상당히 중요한 도구이며 이것이 없으면 scanner(lexer)를 구현할 수 없다.
  * Scanner를 만드는데 사용하는 Lex(Flex)는 regular language를 만들어내는 것이다.
  * FE의 각 컴포턴트에 어떤 이론이 사용되는지 알아야 한다.
* **Formal definition**
  * $M$으로 표기되는 $DFA$는 5개의 튜플로 구성된다.
  * $Q$, 한정된 개수의 상태를 의미
  * $\sum$, 한정된 개수의 입력 심볼을 의미
  * $\delta:Q\times \sum \rarr Q$, 전이함수(transition function)를 의미 (Cartesian Product, 가능한 모든 조합을 말함)
    * $Q=\{Q_1,Q_2,Q_3\}$와 $\sum=\{a,b\}$가 있다면 $Q\times \sum=\{Q_1*a,Q_2*a,Q_3*a,Q_1*b,Q_2*b,Q_3*c\}$
    * State transition table로도 구성할 수 있다.
  * $q_0​$, 초기상태(initial state)를 의미 (원소, element)
  * $F$, 받아들이는 상태의 집합을 의미 (부분집합, subset)
  * $w=a_1a_2...a_n$이라는 문자열이 있고 $r_0,r_1,...,r_n$이라는 상태가 있다고 하면 다음을 만족한다.
    * $r_0=q_0$
    * $r_{i+1}=\delta(r_i,a_{i+1}), for\ i=0,...,n-1​$ (숫자를 넣어보면 빠른 이해 가능)
    * $r_n \in F$
* $L(M)$: **Language is accepted by given machine**
  * 만약 어떤 언어가 regular language라면 FSM에 의해 accept 되어야 한다.
  * 모든 언어는 각각에 맞는 machine이 존재한다.
* Machine이 가장 최적화된 machine이라는 것을 판단하는 것은 NP 문제이다.
* $\epsilon$은 보이지 않는 empty string이며 모든 accept states에 적용될 수 있으며 그 길이는 0이다.
* $\sum^*$는 $\epsilon$을 포함하는 것을 의미하며 $w$가 empty string이든 아니든 extended transition function인 $\delta'$를 통해서 나타낼 수 있다.
* **NFA(Non-deterministic Finite Automata)**
  * NFA에선 여러개의 상태로 귀결될 수 있기 때문에 Power set of Q인 $P(Q)​$로 나타낼 수 있다. 가능한 모든 상태를 말한다.
  * $q_n$이 $a$라는 입력을 통해 $q_n,q_m,q_o,q_p$ 라는 상태들로 이어질 수 있으며 $P(Q)=2^4=16$개의 상태를 가질 수 있다는 것을 의미한다. 이것이 NFA의 Complexity이다.