# Compiler Theory - 5 Week (2)

* **Software system을 design 할 때 ambiguity를 제거하는 것이 중요하다.**
  * <u>Keyword와 user-defined word</u>를 구분해야 하는데, keyword를 먼저 symbol table에 넣어놓고 identifier를 만났을 경우 symbol table의 keyword와 먼저 비교하는 방법을 사용하면 된다.
  * Identifier가 symbol table에 없을 경우 user-defined ID라는 것을 알 수 있다.
  * <u>Multi-token과 single-token</u> 또한 ambiguity가 발생하는데 `<>`와 같은 token이 이에 속한다. 그러나 "principle of maximal munch"에 의해서 longest substring을 더 선호하기 때문에 `<>`를 single-token으로 받아들인다.
  * <u>Token-delimiter</u> 또한 ambiguity가 있으며 
* **Category of High-level languages**
  * Fixed-Format Language
    * Fortran, 1st generation language in 1958
    * No Token Delimiter
    * No Reserved Word
  * Free-Format Language
    * C family languages, C/C++/Java..
    * Script languages, Javascript/Python...
    * Prolog, LISP, COBOL, ALGOL, Pascal, Ada, Modular...
  * Fixed-format language는 column 기준으로 동작하며 free-format language는 포맷이 사용자에게 달려있다. 따라서 free-format language에 token-delimiter가 반드시 필요한 것이다.
    * `#include<stdio.h>main(){int a}`
  * `xtemp=ytemp+1;`과 `xtemp  =   ytemp+   1  ;`의 차이점은 공백의 횟수이지만 free-format language에서 포맷은 사용자에게 달려있기 때문에 token-delimiter인 `;`을 이용해서 구분할 수 있다.
    * `xtemp`와 `=`는 어떻게 구분할까? DFA를 구현할 때 `=`를 만나면 쪼개지도록 설정을 해야 하는데, 여기서 `=`와 공백이 token-delimiter가 된다.
  * Fixed-format language에선 ambiguity를 해결하기 위해 two-pass scanning이라는 2번의 스캐닝을 적용한다. (free-format language는 one-pass scanning)
    * `DO99I=1,10`에선 `,`를 만나면 backtrack 하지만 `DO99I=1.10`에선 컴파일러가 period인 `.`을 알고 있기 때문에 backtrack하지 않는다. (backtrack한다고 token-delimiter가 아닌 이유는 해석이 안돼기 때문이다.)
    * Fortran에선 keyword가 user-defined word로 사용될 수 있다.
* **Token-delimiter**
  * newline, blank, tab, comment와 같은 것들이 있으며 token-delimiter의 역할을 한다.
  * `=`는 다른 DFA에 의해 처리되어 assignment operator라는 것으로 처리되어야 한다.
* **Lookahead symbol**
  * 어떤 character를 처리하기 전에 먼저 lookahead symbol의 character를 봐야 한다.
    - 예를 들어, lookahead symbol에 `=`이 있으면 컴파일러는 이전에 프로세싱한 `xtemp`의 구성요소인지 확인해야 한다. 
    - 그 다음 다시 `=ytemp`에 대해 assignment에 해당하는 DFA가 동작하며 이 때 lookahead symbol에 `y`가 저장된다.
* 모든 operator를 token-delimiter로 생각할 수 있을까? YES
  * `length*height`에서 `*`는 token-delimiter이므로 `length`와 `*`에 각각 DFA가 필요하다.
* **Assignment**
  * 프로그램은 반드시 `<script_start>`로 시작하여야 한다.
  * `//`로 시작하는 주석 또한 처리해주어야 한다.
  * Sequence of token을 신경쓸 필요는 없다.
  * Symbol table을 사용하면 keyword와 user-defined ID를 구분할 수 있다.
  * 함수에 대한 DFA 또한 만들어줘야 한다.
  * `window.prompt("Enter the first number");`와 같은 것은 DFA 2개를 만들거나 아니면 함수로 생각해서 DFA 1개를 만들면 된다.
  * 빨간줄이 keyword, 파란줄이 user-defined word, 보라색이 special character(longest substring 적용)
  * 시작하는 태그와 끝나는 태그를 구분할 때는 따로 DFA를 만들어야 한다.
  * 프로그램은 반드시 `<script_end>`로 끝나야 한다.
* **Thompson's subset-construction**
  * R.E is specification of token
  * NFA가 필요한 이유는 무엇인가? R.E의 의미를 그대로 전달할 수 있기 때문에!
  * Specification is pattern, Regular expression is pattern!!!

