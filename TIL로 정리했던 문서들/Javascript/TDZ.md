## TDZ(Temporal Dead Zone)

`var` 를 사용하면 변수 호이스팅 문제가 발생하는데 이를 `let` 이나 `const` 로 완벽히 해결할 수 있을까? 아니다. 이들 또한 호이스팅이 되는데 그 부분이 바로 일시적 사각 지대인 TDZ이다.

`var` 의 경우엔 선언과 동시에 초기화가 되어 호이스팅 되면 `undefined` 를 출력하지만 `let` 이나 `const` 의 경우, 선언만 되고 초기화는 되지 않기 때문에 호이스팅 되면 `ReferenceError` 가 발생한다.

```javascript
console.log(testVar); // undefined
console.log(testLet); // ReferenceError: testLet is not defined.

var testVar;
let testLet;
console.log(testLet); // undefined
```

따라서, 위와 같이 선언문을 만났을 경우에 초기화가 이루어져서 `undefined` 를 출력한다. 즉, **TDZ란 블록 레벨 스코프의 시작부터 변수 선언문을 만나기 직전까지의 구간** 을 의미한다. 더욱 자세한 설명은 [여기](https://poiemaweb.com/es6-block-scope) 를 참고하자.