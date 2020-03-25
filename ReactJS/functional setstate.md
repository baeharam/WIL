리액트의 상태를 업데이트 할 때 클래스/함수 컴포넌트에 상관없이 2가지 방법이있다.

* 값(훅스) 혹은 객체(클래스) 넘기기
* 함수 넘기기

리액트는 상태 업데이트를 비동기로 해결하기 때문에 `setState()` 를 호출한다고 해서 상태를 바로 업데이트 하지 않는다. 그러나, 2가지 방식에 있어서 차이점이 있다. 객체를 넘기면 그 객체를 모두 합쳐서 하나의 객체로 만들고 재조정을 실행하여 업데이트를 수행하지만 함수를 넘기면 큐에 들어가서 함수 하나하나가 실행되는 방식이다. 그렇기 때문에 여러번의 상태업데이트를 수행한다면 함수형 setstate를 사용하는 것이 좋다.