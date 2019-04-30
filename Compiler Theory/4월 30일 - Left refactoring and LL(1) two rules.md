# Compiler Theory - 10 Week (1)

* **Top-Down Parsing**

  * Recursive Descent Parsing
  * LL(1) Parsing
  * 한계, left-recursion removal이 필요하다.
  * 부작용, understandability가 상당히 저하된다.
  
* **Recursive descent parsing에서 BNF를 EBNF로 만드는 이유는?**

  * "in the form of function", 즉 함수형태로 사용하기 위해서이다.

  * ```
    exp → term {addop term}
    ```

  * 위 EBNF에서 `exp`는 함수이름이 되고 `term {addop term}`은 함수몸체가 된다.

  * ```
    exp → term {addop term}
    term → factor {mulop factor}
    factor → (exp) | number
    ```

  * `exp`는 `term`을 호출하고, `term`은 `factor`를 호출한다. `factor`는 다시 `exp`를 호출하기도 하기 때문에 recursive하게 이루어진다는 의미의 이름을 사용하는 것이다.

* **Recursive descent parsing이 있는데 굳이 LL(1) parsing이 필요한 이유는?**

  * BNF를 EBNF로 변환할 때가 어려운 경우가 있다, 즉 보장할 수 없다.
  * 따라서, simply remove left-recursion from original grammer를 통해 left-recursion removal을 수행할 수 있다.
  * 그렇다면 bottom-up parsing은 왜? 가장 위에서 언급한 top-down parsing의 한계와 부작용 때문에.

* **Left-Refactoring**

  * ```
    stmt → assign-stmt | call-stmt | other
    assign-stmt → identifier := exp
    call-stmt → identifier(exp-list)
    ```

  * 위의 BNF를 보면 `stmt`로 `assign-stmt`와 `call-stmt`를 derive 할 수 있다. 그러나 각각의 non-terminal의 첫번째 토큰이 `identifier`로 같다. 

  * 따라서 컴파일러는 어떻게 derive 해야 하는지 알 수 없으므로 해결해주어야 하는데 그것이 바로 left-refactoring이다.

  * ```
    stmt → common-stmt | other
    common-stmt → identifier stmt'
    stmt' → :=exp | (exp-list)
    ```

  * 문제가 있던 BNF에서 common factor는 `identifier`이었기 때문에 따로 분리했다고 볼 수 있고 이제 2개의 statement를 구분하는 방법은 lookahead symbol인 `:=`와 `(`이다.

  * 그러나, 직관적으로 이해하기가 어렵다는 것을 알 수 있다.

* **Basic two rules for parsing table construction**

  1. $A → \alpha$, $\alpha \Rightarrow^* a\beta$ 라면 parsing table인 $M$에서 $M[A,a]=A→\alpha$
  2. $A → \alpha$, $\alpha \Rightarrow^* \epsilon$ 이고 $S\$ \Rightarrow^* \beta Aa \gamma$ 인데 $S$는 starting non-terminal일 때, $M[A,a]=A → \alpha$
  3. $\beta$, $\gamma$는 a string of terminals and non-terminals이다.

* **Derivation of disambiguating rules based on basic two rules**

  * $$
    \text{stmt → if-stmt | }other\\
    \text{if-stmt → }if \text{ ( exp ) stmt else-part}\\
    \text{else-part → }else \text{ stmt | }\epsilon\\
    \text{exp → }0\text{ | }1
    $$

  * 1번째 규칙에의해서 `else-part → else stmt`이고 조건에 맞으므로 `M[else-part, else]=else-part→else stmt`가 성립한다.

  * 2번째 규칙에의해서 `else-part → epsilon`이고 다음과 같은 derivation을 할 수 있으므로 `M[else-part, else]=else-part → epsilon`이 성립한다.

    * ```
      stmt → if-stmt
      	 → if ( exp ) stmt else-part
      	 → if ( exp ) if-stmt else-part
      	 → if ( exp ) if ( exp ) stmt else-part else-part
      	 → if ( exp ) if ( exp ) stmt else-part else stmt
      ```

    * 위에서 $\beta$는 `if(exp)if(exp)stmt`이고 $A$는 `else-part`, $a$는 `else`이고 마지막으로 $\gamma$가 `stmt`가 되므로 조건 성립!

  * 결국 parsing table에서 `M[else-part, else]`부분에는 `else-part → else stmt`와 `else-part → epsilon`으로 2가지가 만들어진다.