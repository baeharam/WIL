## caret(^)

caret(^)은 문장의 첫번째 문자가 특정한 것으로 시작한다는 것을 의미한다.

예를 들어 `^(the cat).+` 에서 `the cat runs` 라는 문장은 일치하지만 `see the cat` 은 일치하지 않는다.

## dollar ($)

dollar($)는 문장의 끝을 의미한다.

예를 들어 `.+(the cat)$` 에서 `watch the cat` 은 일치하지만 `the cat eat` 는 일치하지 않는다.