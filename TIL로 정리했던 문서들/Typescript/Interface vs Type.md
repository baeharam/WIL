## 객체 / 함수

### Interface

```typescript
interface Point {
  x: number;
  y: number;
}

interface SetPoint {
	(x: number, y: number): void;
}
```

### Type

```typescript
type Point = {
  x: number;
  y: number;
};

type SetPoint = (x: number, y: number) => void;
```

## 다른 타입들

인터페이스와는 다르게 타입은 원시타입, 유니온 및 튜플에 사용될 수 있다.

```typescript
type Name = string;

type PartialPointX = { x: number; };
type PartialPointY = { y: number; };

type PartialPoint = PartialPointX | PartialPointY;

type Data = [number, string];
```

## 확장

둘다 동일하게 확장 가능하지만 문법이 다르고 상호 배타적이지 않다.

### 인터페이스가 인터페이스를 확장

```typescript
interface PartialPointX { x: number; }
interface Point extends PartialPointX { y: number; }
```

### 타입이 타입을 확장

```typescript
type PartialPointX = { x: number; };
type Point = PartialPointX & { y: number; };
```

### 인터페이스가 타입을 확장

````typescript
type PartialPointX = { x: number; };
interface Point extends PartialPointX { y: number; }
````

### 타입이 인터페이스를 확장

```typescript
interface PartialPointX { x: number; }
type Point = PartialPointX & { y: number; };
```

## 구현

클래스는 인터페이스와 타입을 모두 구현할 수 있지만, 클래스와 인터페이스의 경우 정적이기 때문에 유니온 타입을 구현/확장할 수 없다.

```typescript
interface Point {
  x: number;
  y: number;
}

class SomPoint implements Point {
  x = 1;
  y = 2;
}

type Point2 = {
  x: number;
  y: number;
};

class SomPoint2 implements Point2 {
  x = 1;
  y = 2;
}

type PartialPoint = { x: number; } | { y: number; };

// 불가능
class SomePartialPoint implements PartialPoint {
  x = 1;
  y = 2;
}
```

## 선언 병합

타입과 다르게 인터페이스는 여러번 선언될 수 있으며 하나의 인터페이스로 병합된다.

```typescript
interface Point { x: number; }
interface Point { y: number; }

const point: Point = { x: 1, y: 2 };
```

