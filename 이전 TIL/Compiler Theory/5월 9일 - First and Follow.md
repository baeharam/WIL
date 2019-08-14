# Compiler Theory - 11 Week

* LL(1) parser를 만들기 위해선 parsing stack을 사용해야 하는데, parsing stack에는 parsing table이 필요하다. 따라서, parsing table을 먼저 만들어야 하며 여기에 필요한 개념이 First와 Follow이다.

* **First Set**

  * <img src="https://user-images.githubusercontent.com/35518072/57520556-e8b4f380-7358-11e9-8124-9d5d3458798e.png">

  * 위 정의가 다소 복잡하기 때문에 좀 더 쉽게 설명한다면, X라는 grammar symbol(non-terminal이나 terminal)이 있을 때 First(X)는 X를 derivation 할 때 나오는 첫 terminal들의 집합이다.

  * 여기서 terminal은 empty string인 엡실론을 포함할 수 있다.

  * ```
    1) exp → exp addop term
    2) exp → term
    3) addop → +
    4) addop → -
    5) term → term mulop factor
    6) term → factor
    7) mulop → *
    8) factor → ( exp )
    9) factor → number
    ```

  * 위와 같은 BNF가 있다고 했을 때 각각의 grammar symbol에서 First를 뽑아보자.

  * <u>1번째 루프</u>

    * Production 1,2는 어떤것도 만들지 않는다.
    * Production 3,4를 통해 First(addop) = { +, - }를 만들 수 있다.
    * Production 5,6 또한 어떤것도 만들지 않는다.
    * Production 7을 통해 First(mulop) = { * }를 만들 수 있다.
    * Production 8,9를 통해 First(factor) = { ), number }를 만들 수 있다.

  * <u>2번째 루프</u>

    * Production 1~5까지 어떤것도 만들지 않는다.
    * Production 6을 통해 First(term) = First(factor) = { ), number }를 만들 수 있다.

  * <u>3번째 루프</u>

    * Production 2를 통해 First(exp) = First(term) = { ), number }를 만들 수 있다.

  * <u>4번째 루프</u>

    * 변화한 것이 없으므로 끝난다.

  * ```
    First(exp) → { ), number }
    First(term) → { ), number }
    First(factor) → { ), number }
    First(addop) → { +, - }
    First(mulop) → { * }
    ```

* **Follow Set**

  * <img src="https://user-images.githubusercontent.com/35518072/57521390-1e5adc00-735b-11e9-98dd-8a1119f115bf.png">

  * Follow는 First 보다 다소 복잡하고 직관적이지 않다. 따라서 위에 명시된 규칙을 따라서 만들어내는 것이 편하다.

  * 또한 명심해야 할 것은 empty string인 엡실론을 포함하지 않는다는 것인데, 그 이유는 Follow라는 개념이 어떤 non-terminal 다음에 어떤 token이 오는지를 명시하는 것이기 때문이다.

  * 즉, Follow는 input값과 매칭되는 것이 무엇인지 보려는 것인데, 엡실론은 non-terminal을 사라지게 하는 기능이므로 input 값과 매칭시킬 수 없는 것이다.

  * ```
    1) exp → exp addop term
    2) exp → term
    3) addop → +
    4) addop → -
    5) term → term mulop factor
    6) term → factor
    7) mulop → *
    8) factor → ( exp )
    9) factor → number
    ```

  * 동일한 BNF를 통해 Follow를 만들어내는 과정을 살펴보자.

  * <u>1번째 루프</u>

    * Production 1을 통해 First(addop)가 Follow(exp)에 들어가고 exp는 start symbol이므로 $도 들어간다. 따라서 Follow(exp) = { $, +, - }가 만들어진다.
    * 똑같이 First(term)이 Follow(addop)에 들어가서 Follow(addop) = { (, number }가 만들어진다.
    * 이후 Follow(exp)가 Follow(term)에 들어가서 Follow(term) = {$, +, - }가 만들어진다.
    * Production 2에 의해서 Follow(exp)가 Follow(term)에 들어가진 방금 했기 때문에 변화는 없다.
    * Production 5에 의해서 First(mulop)가 Follow(term)에 들어가서 Follow(term) = { $, +, -, * }가 된다.
    * 또한 First(factor)가 Follow(mulop)에 들어가서 Follow(mulop) = { (, number }가 된다.
    * 그 다음, Follow(term)이 Follow(factor)에 들어가서 Follow(factor) = { $, +, -, * }가 된다.
    * Production 8을 통해서 Follow(exp)에 First( ( ) = { ( }가 들어가서 Follow(exp) = { $, +, -, (}가 된다.

  * <u>2번째 루프</u>

    * Production 1을 통해 Follow(term)에 "("가 추가되서 Follow(term) = { $, +, -, *, ( }가 된다.
    * Production 5를 통해 Follow(factor)에 "("가 추가되서 Follow(factor) = { $, +, -, *, ( }가 된다. 

  * <u>3번째 루프</u>

    * 변화가 없으므로 종료된다.

    ```
    Follow(exp) = { $, +, -, ( }
    Follow(term) = { $, +, -, *, ( }
    Follow(factor) = { $, +, -, *, ( }
    Follow(addop) = { (, number }
    Follow(mulop) = { (, number }
    ```

    