# Compiler Theory - 4 Week (2)

* **Regular Expression = Pattern Specification Language**

  * Identifier `letter(letter|digit)*`
  * 간단한 정규표현식이라면 DFA로 바꾸는 것이 어렵지 않지만 복잡해지면 NFA를 사용하는 것이 좋다.  
  * DFA로 바꾼다음엔 implementation을 하면 되며 이 과정은 굉장히 mechanical하다

* **DFA를 코드로 바꾸는 방법은 3가지가 있다.**

  1. State를 implicit하게 하면 state가 많아질수록 코드가 점점 복잡해진다.

  2. State를 explicit하게 specify하며 `case`를 사용해서 구현한다. 이 방법은 1번 보다는 훨씬 명확하고 간결하지만 다른 machine을 만들 때 처음부터 다시 짜야 한다는 단점이 있다.
  3. <u>Table-Driven으로 작성하는 방법이 있으며 2번보다 reusable하기 때문에 다른 machine을 만들 때 재사용할 수 있다. (강추!!)</u>

* **Flex란?**

  * Lex의 Linux 버전, Lex는 Unix의 Scanner Generator이다.
  * Flex를 사용할 땐 regular expression을 input으로 주어야 하며 Flex가 자동으로 구현해준다.
  * 따라서 **정확한 regular expression**을 만든다면 Flex가 알아서 해주는 일 밖에 없기 때문에 굉장히 좋음

* **Scanner(Lexer)**

  * Scanner의 역할은 컴파일러로 하여금 소스코드에서 토큰(token)을 만들어내서 분류(classify)하는 것.

  * 토큰으로 나눈 후에는 토큰의 가능한 많은 type, value, memory location 같은 속성(attribute)을 계산.

  * 토큰의 모든 attributes는 symbol table에 저장된다.

  * Scanner(Tokenizing, Tokens) → Parser(Parsing, Parse Tree) → (Optional Optimizer) → IR

    * p.10-11에 optimization example이 나와있음

  * Token이란?

    * a collective sequence of characters representing a unit of information
    * Well-known token is <u>identifier Token(Frame)</u> `letter(letter|digit)*`
    * Identifier의 정규표현식(패턴)에 맞는 값들은 무한대만큼 만들어낼 수 있다.
    * Logical unit which can imply infinite number of values.

  * Symbol Table

    

    * | lexeme | Token      | Attributes |
      | ------ | ---------- | ---------- |
      | int    | identifier | (no value) |
      | length | identifier | 12         |
      | height | identifier | 9          |

    * Attributes는 프로그램을 실행하면서 업데이트 된다.

    * Symbol table은 소스코드를 변환할 때나 프로그램을 실행할 때 사용한다.

  * Lexeme이란?

    * Concrete value of some tokens
    * Value of token

  * Token과 Lexeme의 차이는? [출처](https://stackoverflow.com/a/14958865/11054387)

    * ```
      [Token]       [Informal Description]                  [Sample Lexemes]
      if            characters i, f                         if
      else          characters e, l, s, e                   else
      comparison    < or > or <= or >= or == or !=          <=, !=	
      id            letter followed by letters and digits   pi, score, D2
      number        any numeric constant                    3.14159, 0, 6.02e23
      literal       anything but ", surrounded by "'s       "core dumped"
      ```

  * Programming language is Type 2 language (Context-Free Language)

    * $a^nb^nc^n$ 을 type, variable, usage라고 하면 context-free language로 나타낼 수는 없지만 context-sensitivity를 가지는 symbol table로 나타낼 수 있다.
    * Context-Free grammer, BNF
    * 만약 variable이 declare되지 않았을 경우 symbol table에 없기 때문에 parser가 알고 에러를 띄운다.
    * $NUM$ 토큰이 있을 때 숫자를 저장하며 $PLUS$ 토큰이 잇다면 더하기 부호 하나만을 저장한다.

  * Token의 category

    * **Keywords**, reserve되기 때문에 variable로 사용될 수 없다. $FOR,WHILE...$ 
      * Fortran에선 keywords도 identifier로 사용될 수 있다. `IF(IF.EQ.0)THENTHEN=1.0`
      * Fortran의 단점은 token delimiter를 사용하지 않는다는 것이다.
      * Delimiter의 역할은 valid tokens 사이에 boundary를 지정하는 것이다.
    * **Identifiers**, user-defined strings
    * **Literals**, constant strings/numbers (`const int a = 123`, `String name = "Handong Univ"`)
    * **Special Symbols**, operators, punctuation, arithmetic symbols ($-,+,*,\ge$)
    * **EOF**

  * Regular expression은 individual tokens를 describe한다.

  * Meta-character는 special meaning을 가지는 것이며 meta-language는 다른 language를 표현하기 위한 또다른 language이다.

    * `a|b`, `a*`, `(ab)*cd`
    * Meta-Character, alternative `|`, grouping `()`, Kleene Closure `*`
      * Meta-Language, meta-character로 구성된 expression으로 `[a-z]`와 같은 것이다.