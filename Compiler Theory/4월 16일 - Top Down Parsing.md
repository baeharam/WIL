# Compiler Theory - 8 Week (1)

* **Associativity**

  * Left: `A → Aa | a`로 나타낼 수 있으며 a를 일반화하여 B(베타)로 나타낼 수 있음

  * 그러면 `A → Ba*`으로 나타낼 수 있으며 EBNF를 사용하여 `A → B{a}`로 표현가능, 즉 recursive definition에서 RHS에 LHS가 안나오도록 변환한것.

  * Right associativity도 마찬가지 원리로 `A → {a}B`로 변환가능. 그러면 왜 이러한 변환이 필요한가?

  * ```
    Exp → Exp addop term | term
    ```

  * 위와 같은 grammar가 있다고 했을 때 `Exp → term{addop term}`으로 표현가능.

  * 이제 그 이유를 알아보자.

* **Top-Down Parsing**

  * Recursive Descent Parsing과 LL(1) Parsing으로 나뉜다.

  * LL(1) parser는 single stack을 사용하여 parsing을 하고 parsing table에 저장한다.

    * Stack을 사용하기 때문에 Type 2 language의 recognizer는 push-down automaton이라고 불린다.
    * 첫번째 L은 Left-To-Right의 의미이며 두번째 L은 Leftmost-Derivation의 의미이다.
    * 1이라는 뜻은 단 하나의 symbol만을 체크한다는 의미이다.

  * LL(1) parser가 parsing하는 과정은 다음과 같다.

    * LL(1) grammar가 아래와 같고 인풋값이 `(a+a)`라고 하자.

    * ```
      1. S → F
      2. S → (S+F)
      3. F → a
      ```

    * 처음 스택에는 `[S, $]`로 되어있으며 `$`의 의미는 EOF이다. EOF를 만나게 되면 parsing이 성공적으로 되었다는 것을 의미한다.

    * 인풋값의 처음 symbol이 `(`인데 `S`가 TOS(Top Of Stack)이므로 `(S+F)`로 변환한다. 이렇게 되면 스택에는 `[(, S, +, F, ), $]`가 있게 된다.

    * 여기서 `(`가 스택과 인풋 스트림에서 모두 지워지고 `[S, +, F, ), $]`가 남는다.

    * 그 다음 값이 `a`이므로 `S`는 `F`로 변환되어 `[F, +, F, ), $]`가 되고 이런식으로 계속해서 스택과 인풋 스트림이 모두 소모된다.

    * 결국 EOF인 `$`를 만나게 되고 parsing은 종료된다.

    * Derivation하는 과정은 parsing table을 보면서 하는 것이며 자세한 과정은 [위키백과](https://en.wikipedia.org/wiki/LL_parser#Parsing_procedure)를 참고하자.

    * Parsing table은 다음과 같다.

    * 

    * |      | (    | )    | a    | +    | $    |
      | ---- | ---- | ---- | ---- | ---- | ---- |
      | S    | 2    | -    | 1    | -    | -    |
      | F    | -    | -    | 3    | -    | -    |

* **주의할 점**

  * 위 예제는 LL(1) grammar로 very intentional한 grammar, 즉 LL(1) parsing이 되도록 만든 문법이라고 할 수 있기 때문에 적용이 된다.
  * 그러나 보통의 left-recursive grammar는 LL(1) parser가 한번에 1개의 symbol만을 읽을 수 있기 때문에 한번에 전체적인 string을 볼 수 없는 이상 어떤식으로 derivation을 해야할지 알 수가 없다.
  * 따라서, LL(1) parser는 반드시 left-recursion을 removal(제거) 해야 하며 그 때 사용되는 것이 바로 가장 위쪽에서 말했던 Extended BNF (EBNF)이다. 

* 반대로 bottom-up parser는 left-recursion을 제거할 필요가 없기 때문에 더욱 강력하며 bison이 이 방식을 사용하여 parser를 generate시킨다.
