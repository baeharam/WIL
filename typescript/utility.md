## 유틸리티 타입(Utility Types)

타입스크립트로 계속해서 개발을 하면서 다른 분의 코드를 보고 있었는데, 타입스크립트 자체에서 제공하는 유틸리티 타입에 대해서 알게 되었다. 타입을 만들어내는 유용한 유틸들로, 알아두면 상당히 유용하다. 여기선 한번에 정리하기 보다, 특정 유틸을 배울 때마다 정리하기로 한다.

* `Partial<T>`

타입 T의 모든 프로퍼티를 선택적 타입(optional type)으로 만드는 유틸이다.

```typescript
interface OriginalType {
  name: string;
  age: number;
}

// 에러!!
const person: OriginalType = {
  name: '이름'
}

type PartialType = Partial<OriginalType>;

// 정상작동
const person: PartialType = {
  name: '이름'
}
```

* `Readonly<T>`

타입 T의 모든 프로퍼티를 read-only 타입으로 만드는 유틸이다. 프로퍼티 앞에 `readonly` 를 붙여서도 똑같이 동작하게 할 수 있지만, 프로퍼티가 많아질 경우 하나 하나 다 붙이는 것보다 이 유틸로 해결하는 것이 간편하다.

```typescript
interface OriginalType {
  name: string;
  age: number;
}

const person: OriginalType = {
  name: '김코딩',
  age: 10
}
// 정상작동
person.name = '김코딩2';

type ReadonlyType = Readonly<OriginalType>;
const person: ReadonlyType = {
  name: '김코딩',
  age: 10
}
// 에러!!
person.name = '김코딩2';
```

* `Omit<T,K>`

타입 T의 모든 프로퍼티를 가져오는 대신 타입 K를 생략한다.

```typescript
interface OriginalType {
  name: string;
  age: number;
}

type NameType = Omit<OriginalType, 'name'>;
```

* `Parameters<T>`

함수타입인 T의 매개변수들을 튜플 타입으로 만드는 유틸이다.

```typescript
type FunctionType = (a: string) => void;
type ParametersType = Parameters<FunctionType>; // [string]
```

* `ReturnType<T>`

함수타입인 T의 리턴타입을 가져오는 유틸이다.

```typescript
type FunctionType = (a: string) => string;
type ReturnFunctionType = ReturnType<FunctionType>; // string
```



## 참고

* [타입스크립트 공식 문서, 유틸리티 타입](https://www.typescriptlang.org/docs/handbook/utility-types.html)