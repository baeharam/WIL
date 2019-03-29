# Compiler Theory - 5 Week (1)

* Specification의 의미
  * **External behavior** (normally indicates functionalities and performance)
* Internal implementation
  * We have to implement mechanism in detail
  * AI가 발전하면서 internal implementation을 점점 하기 때문에 external behavior를 얼마나 정확하게 describe할 것인가에 달렸다. (Software system의 품질이)
* Regular expression을 pattern specification language로 나타낼 때 external appearance of words (tokens, lexeme)로 나타내어진다.
  * Token은 infinite number of lexeme을 generate하는 **frame**이다.
* Lex와 Flex는 자동으로 DFA를 만들어주므로 **very precisely** specification of token을 **describe**해야 한다.
  * Regular expression을 정확하게 정의했다면 NFA로 변환한 후에 subset construction과 $\epsilon​$ - closure를 통해 NFA를 DFA로 변환한다.
  * 이제 기계가 DFA를 C-code implementation으로 변환한다.
* $L(a),L(\epsilon)$과 같은 것에서 $a,\epsilon$은 frame을 의미한다. (특정 값이 아니라 추상화된 것)
* Empty set과 empty string을 구분해야 한다.
  * <img src="https://user-images.githubusercontent.com/35518072/54964772-0dbff380-4fb1-11e9-83fc-783ad0419c2b.PNG">
* **Regular expression operations (정규 표현식의 연산)**
  * Concatenation의 경우에는 meta-character가 없다.
  * $L(a|\epsilon)=\{a,\epsilon\}$과 $L(a)=\{a\}$는 다르다.
  * $\bigcup_{n=0}^{\infty}S^n=\{\epsilon\}\cup S\cup SS...$
  * 우선순위(Precedence)는 선택(choice), 접합(Concatenation), 반복(Repetition) 순이다.
  * Lex와 Flex는 이미 위 기준의 우선순위를 사용하기 때문에 따로 정의할 필요가 없다.
  * Klenee closure(*)는 괄호를 생략한다.
* **Algebric Properties of Regular Expressions (정규 표현식의 수학적 성질)**
  * 교환법칙(commutative), $r|s=s|r$
  * 결합법칙(associative), $r|(s|t)=(r|s)|t​$
  * 분배법칙(Distributive), $r(s|t)=rs|rt$
  * 항등원(Identity element), $\epsilon r=r$
* $\sum=\{a,b,c\}​$가 있을 때 b를 연속하지 않게 하는 정규표현식은?
  * $(a|c|ba|bc)^*(b|\epsilon)$
  * $(b|\epsilon)(a|c|ab|cb)^*$
* **Extensions to Regular Expressions (정규 표현식의 확장)**
  * 1개 이상의 반복, `(0|1)(0|1)*=(0|1)+​`
  * 임의의 문자, `.*b.*​` 
  * 문자들의 범위, `a|b|..|z=[a-z]​`
  * Individual alternatives, `[abc]​`
  * Multiple range + Individual alternatives, `[a-zA-z]​`
  * 허용되지 않는 문자, `~(a|b|c) = [^abc]`
  * 옵션, `?`
  * 실제 문자, `"."`, meta-character가 아님
* Scanner의 주석처리
  * Pascal, `{(~})*}`
  * Ada, `--(~newline)`
* **Scanner의 ambiguity (애매모호함)**
  * Keyword와 User-defined word를 구분해야 하는데 어떻게 할 것인가?
  * Symbol table을 활용하면 context-sensitivity를 줄 수 있는데, 정의한 identifier가 symbol table에 존재하는지 확인하는 것이다.
  * `<>`와 같은 multiple character의 문제는?
  * **Disambiguating rules**
    * Keyword interpretation is preferred to user-defined word
    * Single token is preferred to two tokens (Principles of longest substring)
  * **Token Delimiter**
    * 토큰과 토큰을 어떻게 구별할것인가?
    * Two character strings를 two token으로 분리시키는 역할을 하며 <u>distinguishing token</u>이라고도 함
    * `int length;`에서 Scanner는 공백을 보고 `int`가 identifier라는 것을 알 수 있고 `;`을 보고 `length`가 identifier라는 것을 알 수 있다.