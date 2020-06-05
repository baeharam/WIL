## 클래스

* **componentDidMount**: 맨 처음 화면에 렌더링 될 때 불리는 메서드
* **componentDidUpdate**: 화면이 다시 렌더링 될 때마다 불리드 메서드
* **componentWillUnmount**: 컴포넌트가 제거되기 직전에 불리는 메서드

## Hooks

Hooks는 클래스와는 다르게 `useEffect()` 로 클래스의 3가지 메서드를 모두 구현한다. 예를들어, 3가지 상태변수가 있다고 했을 때 클래스라면 각 때에 따라 모든 상태변수를 관리하지만 Hooks는 각 상태변수에 대해 모든 때를 관리한다고 할 수 있다.

* **componentDidMount**

```react
useEffect(콜백함수, []);
```

* **componentDidUpdate**

```react
useEffect(콜백함수, [업데이트 해야할 값]);
```

* **componentWillUnmount**

```react
useEffect(콜백함수 안에 리턴문, []);
```

클래스와 생명주기가 1대1로 매칭되지는 않으며 어느 정도 비슷하게 동작한다는 사실을 기억하자. 애초에 Hooks로 구현하면 렌더링 될 때마다 Hooks로 정의된 부분 전체를 실행하기 때문에 클래스와 똑같이 동작할 수는 없다.