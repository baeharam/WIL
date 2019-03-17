# Compiler Theory - 3 Week (2)

* DFA, $\delta:Q\times \sum^* \rarr Q​$
* NFA, $\delta:Q\times \sum^* \rarr P(Q)​$
  * State가 정해져 있지 않기 때문에 임의로 지정해주어야 한다.
  * 5개의 states가 존재한다면 $2^5=32$개의 states가 존재하는 것.
  * NFA는 DFA를 만들어내는데 굉장한 편리함을 준다.
* Infinite states가 존재한다면 계산할 수 없다.
* 우리의 목표는 Scanner(Lexer)를 만드는 것이다.
  * Regular Expression → NFA → DFA → Scanner

* Make a DFA that simulates a newspaper vending machine that consumes 30 cents for opening a latch. The machine does not consider change. (accepting a dime(10 cents), a nickle(5 cents), a quarter(25 cents))
  * DFA는 language processing에만 쓰이는 것이 아니라 이런 예시와 같은 일반 기계에도 쓰이곤 한다.
  * 라벨링(Labeling)이랑 가독성(Readability)을 고려해야 하며 **가능한 모든 입력**을 생각하자.
* Draw a DFA that accepts strings of `a` and `b`, which contains even number of `a`'s' and odd number of `b`'s
  * 0홀, 짝0, 홀짝, 짝짝, 홀홀, 짝홀, 홀0, 짝0, 00(start state)
* 최대한 number of states를 minimize해야 한다.