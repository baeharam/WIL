# Compiler Theory - 7 Week (2)

* **Restriction of 4 types Language**

  * Context-Free Grammar
    * NT → a string of NT & T
    * 왼쪽 부분은 오직 1개의 NT만 허용된다.
  * Context-Sensitive Language
    * a string of NT & T → a string of NT & T
    * 오른쪽 부분의 길이는 항상 왼쪽 부분의 길이보다 크거나 같아야 한다.
  * Regular Language
    * Recursive definition이 허용되지 않는다.
  * Unrestricted Grammar for Turing Machine
    * 왼쪽 부분이 1개 이상의 NT를 가지는 것 이외에는 제약이 없다.

* **Parser**

  * 가장 중요한 것은 정확한 production rule을 정하는 것이다.

  * Top-down parsing과 Bottom-up parsing이 있다.

  * Leftmost/Rightmost derivation에 따라서, 또는 wrong grammar 때문에 parse tree가 달라질 수 있다.

    * <img src="https://user-images.githubusercontent.com/35518072/56009085-7a5d2100-5d19-11e9-9edb-da29a023ff2f.PNG" width="500px">
    * <img src="https://user-images.githubusercontent.com/35518072/56009083-79c48a80-5d19-11e9-9a53-70f27183ece5.PNG" width="500px">

  * Multiplication/Division은 multiplicative operation이라고 부르며 Addition/Subtraction은 additive operation이라고 부른다.

  * Leftmost/Rightmost derivation에 따라 다른 parse tree가 생성되는 경우는 associativity와 precedence를 명확하게 지정하지 않았기 때문이다.

    * 따라서 precedence를 강제해주어야 한다.

    * ```
      Goal → Expr
      Expr → Expr + Term | Expr - Term | Term
      Term → Term * Factor | Term / Factor | Factor
      Factor → number | id
      ```

    * Associativity에는 left associativity와 right associativity가 있고 관습적으로 left associativity를 많이 사용한다.

* **Ambiguous Grammar**

  * 1개의 sentential form에서 2개 이상의 leftmost derivation이 발생하면 ambiguous하다.

  * 1개의 sentential form에서 2개 이상의 rightmost derivation이 발생하면 ambiguous하다.

  * <u>Dangling Else Problem</u>

    * ```
      Stmt → if Expr then Stmt 
      	  |if Expr then Stmt else Stmt
      	  |other Stmts...
      ```

    * Sentential form이 `if Expr1 then if Expr2 then Stmt1 else Stmt2`라고 하면 위의 문법에 따라서 다른 parse tree가 만들어진다.

    * <img src="https://user-images.githubusercontent.com/35518072/56009084-79c48a80-5d19-11e9-9acd-6493cedb89f9.PNG" width="500px">

  * <u>Ambiguity 해결방법</u>

    * Correct grammar, 하지만 복잡해져서 **이해하기 어려워진다.**

    * ```
      Stmt → WithElse | NoElse
      WithElse → if Expr then WithElse else WithElse
      		  |OtherStmt
      NoElse → if Expr then Stmt
      		|if Expr then WithElse else NoElse
      ```

    * **Leave ambiguity**, 쉽게 이해하도록 남기고 **disambiguating rule**을 적용하자!

    * 컴파일러가 어떻게 ambiguity를 해결하도록 할 것인가?  **"The most closely nested rule(match *else* to the most closely nested *if*)"!!**

    * 위의 rule을 어떻게 구현할 것인가? (p.214)

    * ```
      I → if S.
      I → if S. else S
      ```

      `.`은 the point of parsing을 의미한다.

    * 위와 같이 구현한 것들을 item이라고 부르며 이러한 것들을 통해서 parsing table을 구축할 수 있고 결국, ambiguous grammar가 남아있다고 해도 correct parsing table을 통해서 해결할 수 있다.

* **세미콜론(;)을 어떻게 정의할까?**

  * State Separator → `S1;S2;S3`,  Separator이기 때문에 마지막에 세미콜론이 없다.
  * State Terminator → `S1;S2;S3;`, Terminator이기 때문에 마지막에 세미콜론이 있다. (C-Family Language가 다 여기에 속한다.)
  * 굉장히 사소해 보이지만 이런 부분도 very correct grammar가 정의되어야 한다.

