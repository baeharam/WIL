# Compiler Theory - 14 Week (1)

## Bottom-Up parser Overview

* Left-recursion을 제거할 필요가 없다. 왜? input string 부터 역으로 parse tree를 만들기 때문에.
* Parsing의 power가 LA(LookAhead) symbol을 어떻게 사용하는지에 달려있다.
* Bottom-Up parsing의 종류
  1. **LR(0) parsing**: LR(0) items로 구성된 DFA를 사용하며 LA symbol을 사용하지 않는다.
  2. **SLR(1) parsing**: LR(0) items로 구성된 DFA를 사용하며 1개의 LA symbol을 사용한다.
  3. **LR(1) parsing**: LR(1) items로 구성된 DFA를 사용한다.
  4. **LALR(1) parsing**: LR(1) parsing을 좀 더 간단하게 만든 버전이다.
* LR(0)의 경우 Shift-Reduce conflict, Reduce-Reduce conflict가 발생하는 빈도가 높고 LR(1)가 그 빈도가 제일 낮다. 따라서 LA symbol의 사용성에 초점을 두는 것이다.



## LR(0) → SLR(1)

SLR(1) parsing은 LR(0) parsing에서 발생하는 shift-reduce conflict를 어느정도 해결하기 위한 방법으로 아래와 같은 메커니즘을 사용한다.

```
A → .aX
B → X.
```

라는 DFA의 state가 있다고 했을 때 next token을 보고 `Follow(B)` 에 속한다면 reduction을 하고 아니라면 shifting을 하는 방식이다.

```
If → If Statement.
If → If Statement. else Statement
```

그러나 위와 같은 dangling-else 문제에선 SLR(1)도 Shift-Reduce conflict가 발생하기 때문에 이것을 해결해야 한다. 기본적으로는 **"shifting over reduction"**으로, disambiguating rule이 적용되어 shifting을 하게 된다. 이렇게 SLR(1)도 conflict가 나는 경우가 있으며 이것은 문법이 잘못된 것이 아니라 parsing algorithm이 약한 것이라고 할 수 있다. 그 이유는 LR(1) parsing으로 conflict가 해결되기 때문이다.

```
S → id | V := E
V → id
E → V | n
```

위와 같은 문법이 있다고 했을 때 start state를 보게 되면 아래와 같이 나온다.

```
S' → S
S → .id
S → .V := E
V → .id
```

`id` 에 대해 `S → id.` 와 `V → id.` 를 갖는 state로 transition되는데, 이 때 next token이 `$` 이고 `Follow(S)={$}`, `Follow(V)={$, :=}` 이므로 둘 다 reudction을 적용할 수 있다. 따라서, Reduce-Reduce conflict가 발생하게 된다. **결론적으로, SLR(1)에 conflict가 발생하는 경우는 shift할 next token과 Follow set이 공통된 token을 포함하는 경우라고 할 수 있다.**