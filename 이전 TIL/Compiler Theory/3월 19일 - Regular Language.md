# Compiler Theory - 4 Week (1)

* Regular Language is Pattern = DFA, NFA

* **Regular Language를 만족하는 DFA가 반드시 있어야 한다.**

  * 예를 들어 $a^nb^n(n>0)$이라는 regular expression이 있다고 하자, 이를 만족하는 DFA가 있는가? 구현하기 위해선 a number of repetition을 기억하는 메커니즘이 있어야 한다.
  * 그렇다면 DFA에 메모리를 가지고 있는가? 없다. 따라서 위의 언어는 regular language가 아니라 type 2인 context-free language이다.
  * $a^nb^nc^n$은? 만약 이것이 type 2 language라면 이에 해당하는 push-down automata가 있어야 하지만 없기 때문에 type 1 language인 context-sensitive language이다.
  * 어떤 문자열(string)을 주어졌을 때 문자열을 rotation으로 표현하게 되는데 문제는 이에 해당하는 기계(machine)가 있어야 한다는 것이다. 이것이 바로 오토마타 이론의 핵심이다.

* 저번에 잘못 냈던 문제

  > ***a DFA that accepts even # of a's and odd number of b's (there should be at least one a and one b)***

* The meaning of name, Deterministic Finite State Machine

  * 이름의 의미를 이해해야 한다.
  * 기계는 상태(states)로 구성된다.
  * 그 상태는 한정된(finite) 개수를 갖는다.
  * 한정된 상태에 대한 선택은 결정적(deterministic)이다.
  * $a^nb^n$이 context-free language이지만 DFA로 디자인하게 되면 무한한 상태로 이어지게 된다.
  * <img src="https://user-images.githubusercontent.com/35518072/54599925-04013200-4a7f-11e9-9876-1582362425d6.PNG" width="400px">

* ```
  		      		   Compiler
        		     ↙        ↘
        	Front End    IR    Back End
        	↙	  ↘           ↙           ↘
  Scanner	  Parser   Code Generation Optimization, Object code
  (Lexer)
  ```

* Identifier에 대한 regular expression, $Letter(Letter|Digit)^*$ 

  * $^*$의 의미는 Kleene Closure이며 $|$의 의미는 alternative이다.
  * 이런 표현은 **문제점이 있는데 identifier의 끝이 어딘지에 대한 delimiter가 없다는 것이다.**
  * 만약 delimiter를 만나게 되면 error state로 향하게 된다.
  * 따라서 delimiter를 고려한 재설계를 해야 하며 scanner를 만들 때도 delimiter를 고려해야 한다.

* **DFA에서 보통 error states는 생략한다.** Error state는 escape 할 수 없다고 해서 trap state라고 한다.

* Parser는 Scanner에게 demands하고 Scanner는 Parser에게 generates한다.

* Scanner에서 character of input string은 하나씩 consume된다.

  * `a|[|i|n|d|e|x|]| |= |4| |+| |2|`
  * 위와 같은 문자열에서 `[`를 만나게 되면 delimiter로 여겨져서 consume되지 않고 `i`에서 다시 시작한다.

* Parser에선 주석을 처리할 필요가 없는데, Scanner가 다 알아서 처리해준다.

* Scanner는 lexical analyzer, Parser는 syntax analyzer

* DFA를 구성할 때는 small DFA를 구성한 뒤에 합치는 것이 좋다.

* ```
      Algol Language - Fortran(Formular Translation)
   	  ↙        ↘
   	 C		  Pascal	
   	 ↓			↓
   	C++		   Ada (US Defence Language)
   	 ↓			↓
      Java	  Modular
  ```