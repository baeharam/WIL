## Tagged union

타입스크립트로 리듀서를 만들 때 액션의 타입이 각각 달라서 이에 대한 처리를 하는 상황이었다. 이 부분을 어떻게 해결해야할지 고민하던 와중에 해당 컨셉을 알게 되었고 이를 정리한다. 먼저 유니온(union) 타입은 여러개의 타입중 하나를 명시하는 타입으로 아래와 같다.

```typescript
type UnionType = string | number | undefined;
```

이런식으로 여러개의 타입을 유니온으로 선언하는데 각 타입에 "태그(tag)"가 들어있어서 해당 태그로 구분하는 방식을 "태깅된 유니온(Tagged Union)" 타입이라고 하는 것이다. 다른 말로는 "차별화된 유니온(Discriminated Union)" 타입이라고 말하기도 한다.

리듀서의 액션을 예로 들어서, 덧셈,뺄셈,곱셈이 있다고 가정해보자.

```typescript
type ActionAdd {
	type: 'ADD',
	payload: {
		num: number;
	}
}

type ActionSubtract {
	type: 'SUBTRACT',
	payload: {
		num: number;
	}
}

type ActionMultiply {
	type: 'MULTIPLY',
	payload: {
		num: number;
	}
}

type Action = ActionAdd | ActionSubtract | ActionMultiply;

/*
아래처럼 사용.
switch(action.type){
	case 'ADD':
	case 'SUBTRACT':
	case 'MULTIPLY':
}
*/
```

이렇게 사용하면 `type` 이 태그가 되고 이 태그를 통해서 액션을 구분함으로, 타입 문제없이 리듀서를 구현할 수 있다. [공식 문서](https://www.typescriptlang.org/docs/handbook/advanced-types.html#discriminated-unions) 를 보면, `kind` 값이 태그가 되어서 구분이 되는데, 목적은 동일하게 각 태그에 대해서 다른 처리를 하기 위함이다. 각 액션마다 `payload` 의 타입도 다르기 때문에 유용한 타입 정의라고 생각한다.

