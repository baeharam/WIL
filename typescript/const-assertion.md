## const assertion

### 소개

Typescript 3.4 에서 소개된 기능으로 기존 그대로의 값(literal value)을 타입으로 변환시켜주는 역할을 한다. 적용되는 대상은 `string` , `number` , `enum` , `array` , `object` 이며 값 그대로 타입을 인지하게 된다.

```typescript
const num = 3 as const; // num 의 타입은 3
const str = "string" as const; // str 의 타입은 "string"

const obj = {
  prop1: "prop1",
  prop2: "prop2"
} as const; // obj 의 타입은 { readonly prop1:  "prop1", readonly prop2: "prop2" }

const arr = [1, 2, 3] as const; // arr 의 타입은 readonly [1, 2, 3]
```

객체와 배열의 경우 `readonly` 가 붙는 다는 점이 다르다. `.tsx` 확장자의 파일이 아니라면 `<const>` 로도 사용할 수 있다.

```typescript
const num = <const> 3;
const str = <const> "string";
```



### 주의사항

단, 말 그대로 literal value 에 적용되기 때문에 동적인 값에 적용되지는 않는다.

```typescript
const random = Math.random() as const; // error
```

하지만 결정적으로 특정 값을 지정해준다면 union type 으로 귀결시킬 수 있다.

```typescript
const random = Math.random() < 0.5 ? 0 as const : 1 as const;
```



## 참고

* [Typescript 3.4](https://www.typescriptlang.org/docs/handbook/release-notes/typescript-3-4.html)