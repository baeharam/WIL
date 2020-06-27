## Element.closest()

리액트에서 react testing library로 작성한 다른 분의 코드를 보다가 DOM API 중에 `closest()` 라는 메서드가 있다는 것을 처음 알게 되었다. 괄호안에 들어가는 조건을 기준으로 자신에게서 가장 가까운 엘리먼트를 찾아내는 메서드이다. 만약 찾지 못하면 `null` 을 반환한다.

```html
<div class="grand-parent">
  <div class="parent" id="parent-id">
    <div class="child"></div>
  </div>
</div>
```

위와 같은 마크업 상태가 있다고 했을 때 클래스가 `child` 인 가장 안쪽 엘리먼트에서 특정 조건을 기준으로 가장 가까운 엘리먼트를 찾고 싶을 때 사용한다. 예를 들어, `grand-parent` 클래스의 자식이며 `parent-id` 라는 id값을 가지는 엘리먼트 중 가장 가까운 것을 찾는다고 해보자.

```javascript
const child = document.querySelector(.child);
const closest = child.closest('.grand-parent #parent-id');
console.log(closest); // <div class="parent" id="parent-id">…</div>
```

이렇게 찾을 수 있게 된다. 리액트에서 직접적으로 사용할 일은 없을 것 같지만, 혹시 모르니 정리해둔다.



## 참고

* [MDN, Element.closest()](https://developer.mozilla.org/ko/docs/Web/API/Element/closest)