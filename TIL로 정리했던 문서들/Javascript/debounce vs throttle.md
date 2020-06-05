## debounce

이벤트를 그룹화하여 특정 시간 이후에 하나의 이벤트만 발생하도록 하는 기술

* 윈도우 리사이즈 이벤트
* 검색 창 자동완성 이벤트

```javascript
const useDebounce = (value, delay) => {
  const [debouncedValue, setDebouncedValue] = useState(value);
  useEffect(() => {
    const id = setTimeout(() => {
      setDebouncedValue(value);
    }, delay);
    return () => clearTimeout(id);
  }, [value, delay]);
  return debouncedValue;
}
```

## throttle

이벤트가 특정 주기마다 최대 1번 발생하도록 하는 기술로 그 주기를 3초로 설정하면 3초에 아무리 많은 이벤트를 발생시켜도 1번으로 생각하는 점에서 디바운스와 비슷하지만 주기적으로 감시를 한다는 점에서 다르다.

* 인피니트 스크롤, 사용자가 하단으로부터 얼마나 떨어져있는지 주기적으로 확인가능
* 애니메이션 프레임

```javascript
// https://github.com/bhaskarGyan/use-throttle/blob/master/src/index.js

const useThrottle = (value, limit) => {
  const [throttledValue, setThrottledValue] = useState(value);
  const lastTime = useRef(Date.now());
  useEffect(() => {
    const id = setTimeout(() => {
      setThrottled(value);
      lastTime.current = Date.now();
    }, limit - (Date.now() - lastTime.current));
    return () => clearTimeout(id);
  }, [value, limit]);
  return throttledValue;
}
```

http://demo.nimius.net/debounce_throttle/ 에서 애니메이션 확인 가능

## 참고

* https://webclub.tistory.com/607
* https://stackoverflow.com/questions/25991367/difference-between-throttling-and-debouncing-a-function