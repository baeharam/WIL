# Compiler Theory - 7 Week (1)

* Flex나 bison에서 single quoted character는 meta-character가 아니다.
* `yyparse()`는 syntactic analyzer이다.
* `.y` 확장자 파일은 bison 파일이다.
* lexer는 `lex.yy.c`로 parser는 `y.tab.c`로 만들어지며 gcc를 통해 컴파일하여 통합시키게 된다.
* Chomsky Language
  * Context-Free Grammer, non-terminal이 terminal/non-terminal로 정의된다.
  * Context-Sensitive Grammer, terminal/non-terminal이 terminal/non-terminal로 정의된다.
  * Turing machine은 enumerable/recursively-enumerable language를 모두 recognize 할 수 있지만 recursively-enumerable의 경우 infinite loop에 빠질 수도 있다.
* CFG에서 production rule의 의미는 definition이며 production rule을 통해 derivation을 할 때 left/right으로 할 수 있다. 
* 그러나 leftmost derivation과 rightmost derivation에 precedence와 associativity rule이 적용되지 않을 경우 서로 다른 parse tree를 형성하여 evaluation order가 달라지게 된다.
* 따라서, convention of calculation을 다르기 위해서 unique representation이 필요하다.
