## enum 을 사용하면 좋지 않은 이유

### enum 은 JS 의 typed superset 이라는 의미에서 좋지 않다.

enum 을 실제로 tsc 같은 타입스크립트 트랜스파일러로 돌려보면 다음과 같이 나온다.

```typescript
enum Fruit {
  Orange,
  Apple,
  Grape
}
```

```javascript
var Fruit;
(function (Fruit) {
    Fruit[Fruit["Orange"] = 0] = "Orange";
    Fruit[Fruit["Apple"] = 1] = "Apple";
    Fruit[Fruit["Grape"] = 2] = "Grape";
})(Fruit || (Fruit = {}));
```

IIFE 로 생성하는 객체임을 확인할 수 있다. 런타임에 생성되는 코드인데, 실제로 JS 와 호화되는 객체를 만들어내지만 실제 코드는 오직 타입스크립트에서만 사용할 수 있기 때문에 타입스크립트를 사용하는 이유와 걸맞지 않다고 볼 수 있다.

### const enum 은 위험하다.

기본적인 enum 대신 const enum 을 사용하면 실제로 트랜스파일 했을 때 해당 값이 들어가게 된다.

```typescript
const enum Fruit {
  Orange,
  Apple,
  Grape
}
const fruits = [Fruit.Orange, Fruit.Apple];
```

```javascript
var fruits = [0 /* Orange */, 1 /* Apple */];
```

변수로 해당 enum 을 사용하지 않고 트랜스파일 했을 때 일반 enum 과 같은 객체조차 생기지 않는다. 이런 점에서 볼 때 매우 타당하게 보이지만 여러가지 위험성을 안고 있다.

*  Babel로 트랜스파일 되지 않는다.
* Ambient context 에 const enum 을 선언하는 건 여러가지 문제를 일으킬 가능성이 높다.
* 심지어는, `--isolatedModules` 플래그와 같이 사용할 경우에 ambient context 가 아니더라도 문제를 일으킬 가능성이 높다.

### Numeric enum 은 타입 안정적이지 않다.

```typescript
enum Fruit {
  Orange,
  Apple
}

const fruit: Fruit = 2; // 에러가 없다!!
```

### 그럼 어떤 걸 사용해야 하나?

사람들은 위와 같은 단점들과 위험성 때문에 union type 과 const assertion 을 활용하라고 말한다. 위에서 예시로 들었던 `Fruit` 을 그렇게 구현해보자.

```typescript
const Fruit = {
  Orange: 0,
  Apple: 1,
  Grape: 2
} as const;

for (const fruit of Object.value(Fruit)) {
  // 해당 작업
}
```

위와 같이 반복작업도 가능하다.

### 물론 enum 에서만 가능한 것도 있다.

enum 을 문자열 형태로 만든 후에 특정 변수를 enum 타입으로 선언하면 해당 변수에 literal 값을 넣을 수 없다. 즉, enum 의 속성 값으로만 넣는게 가능한 것이다.

```typescript
enum Fruit {
  Orange = "Orange",
  Apple = "Apple",
  Grape = "Grape"
}

const fruit: Fruit = "Apple"; // 에러!!!
```

이걸 union type 으로 바꾸면 가능해진다.

```typescript
const Fruit = {
  Orange: "Orange",
  Apple: "Apple",
  Grape: "Grape"
} as const;

type FruitType = typeof Fruit[keyof typeof Fruit];

const fruit: FruitType = "Apple"; // 통과
```

그래서 이런 목적이 **반드시** 필요할 경우에만 enum 을 쓰는 것이 좋다. 이런 enum 의 특징을 **"불투명(opaque)"** 하다고 표현한다. enum 의 실제 값을 투명하게 사용하지 못하고 enum 을 통해서만 불투명하게 사용한다는 의미인 것 같다.



## 참고

* [Typescript has unions, so are enums redundant? 에 대한 답변](https://stackoverflow.com/a/60041791/11789111)