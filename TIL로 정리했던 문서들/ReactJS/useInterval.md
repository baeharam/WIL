# Hooks 에서 안전한 타이머 함수 사용하기

[Dan abramov](https://overreacted.io/making-setinterval-declarative-with-react-hooks/) 가 소개한, 안전한 타이머 함수를 보고 왜 이렇게 사용해야 하는지를 정리해본다. 먼저 `setInterval` 과 같은 함수를 사용할 때 주의해야 할 점은 hooks의 경우 `useEffect` 를 사용하는데, 함수 컴포넌트가 매 렌더링마다 고유의 이펙트를 가진다는 것이다.

```react
const Counter = () => {
  const [count, setCount] = useState(0);
  useEffect(() => {
    const id = setInterval(() => {
      setCount(count + 1);
    }, 1000);
    return () => clearInterval(id);
  }, []);
  return <h1>{count}</h1>
}
```

1초마다 숫자가 1씩 증가하도록 한번만 설정을 해줬는데, 이렇게 되면 매 렌더링마다 고유의 이펙트를 가지기 때문에 `count` 값은 초기값인 0, 즉 `setCount(0 + 1)` 이 1초마다 호출된다.

이를 해결하기 위해선, 함수형 상태갱신을 사용하면 되는데, 이전 상태를 가지고 상태를 업데이트 하기 때문에 해결할 수 있다.

```react
const Counter = () => {
  const [count, setCount] = useState(0);
  useEffect(() => {
    const id = setInterval(() => {
      // setCount(count + 1);
      setCount(c => c + 1);
    }, 1000);
    return () => clearInterval(id);
  }, []);
  return <h1>{count}</h1>
}
```

하지만 이 경우, 넘어오는 최신의 props를 사용할 수 없고 설정할 때의 props 만을 사용할 수 있다. 이를 useReducer로 해결할 수 있는데, dispatch의 경우 리렌더링이 일어나도 변경되지 않기 때문에 props를 사용하여 새로운 상태값을 반환할 수 있다. 하지만 부수효과를 처리할 수 없다고 하는데 여기선 큰 문제가 될 것 같진 않다.

이런 문제들을 해결할 수 있는 커스텀 훅을 만들면 다음과 같다.

```react
const useInterval = (callback, delay) => {
  const savedCallback = useRef();
  useEffect(() => {
    savedCallaback.current = callback;
  }, [callback]);
  
  useEffect(() => {
    if (delay !== null) {
      const id = setInterval(savedCallback.current, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
}
```

콜백함수가 바뀔 때마다 새로 갱신하는데, 렌더링 마다 바뀌지 않도록 `useRef()` 를 사용한다. 또한 지연시간이 바뀔 때마다 클리어하고 다시 실행하고 있는데, 여기서 인자로 `null` 을 주면 멈출 수도 있다.