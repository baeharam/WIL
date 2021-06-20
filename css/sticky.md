## position: sticky 기본 특징

* `top`, `left` , `right` , `bottom` 중 1개의 속성을 필수로 써야 한다.
* `overflow: auto` 혹은 `overflow: scroll` 이 적용된 가장 가까운 부모(scroll 박스)를 기준으로 위 속성에 offset 이 적용되어 고정된다.
* 위 부모를 벗어나게 되면 다시 일반적인 흐름을 따른다. (일반적인 흐름을 따른다 = 바라는 것처럼 고정되지 않는다.)
* scroll 박스와 sticky 엘리먼트 사이에 `overflow: hidden` 이 있을 경우에도 일반적인 흐름을 따른다.
* 처음엔 `position: relative` 로 동작하고 일정 임계점(threshold)을 넘었을 경우에 `position: fixed` 로 동작한다. 이 말은, 자신이 있는 위치를 넘어설 경우를 말한다.
* scroll 박스와 sticky 엘리먼트 사이에 다른 부모 엘리먼트가 있을 수 있는데, 다른 부모 엘리먼트 바깥으로 고정될 수 없다. 즉, 부모 안에서 sticky 가 동작하다 부모를 넘어서면 일반적인 흐름을 따른다.
* 임계점을 이미 초과한 경우, 즉 이미 부모 엘리먼트의 top/bottom edge 에 도달한 경우엔 `position: fixed` 가 될 수 없다. MDN 에서 **도달할 때까지** `position: fixed` 가 적용된다고 말하고 있다. 따라서, 이미 도달한 경우에는 `position: relative` 가 적용되기 때문에 `sticky` 가 먹히지 않는다. ([SO](https://stackoverflow.com/a/55966486/11789111) 에서 아주 잘 설명해주고 있다.)
* 또한 containing block 의 높이가 없다면 `position: sticky` 는 동작하지 않는다. 그 이유는 containing block 의 edge 를 넘어설 때까지 고정되는 방식이기 때문이다.

### MDN

> A **stickily positioned element** is an element whose [computed](https://developer.mozilla.org/en-US/docs/Web/CSS/computed_value) `position` value is `sticky`. It's treated as relatively positioned until its [containing block](https://developer.mozilla.org/en-US/docs/Web/CSS/Containing_block) crosses a specified threshold (such as setting [`top`](https://developer.mozilla.org/en-US/docs/Web/CSS/top) to value other than auto) within its flow root (or the container it scrolls within), at which point it is treated as "stuck" until meeting the opposite edge of its [containing block](https://developer.mozilla.org/en-US/docs/Web/CSS/Containing_block).



### 까먹고 다시 올 경우

* ['position: sticky' not working when 'height' is defined](https://stackoverflow.com/questions/49848196/position-sticky-not-working-when-height-is-defined) 의 1번째 답변을 보자.
* [Why does `overflow:hidden` prevent `position:sticky` from working?](https://stackoverflow.com/questions/43909940/why-does-overflowhidden-prevent-positionsticky-from-working) 의 1번째 답변을 보자.
* [공부할 때 시연해 본 codepen](https://codepen.io/BaeHaram/pen/eYvoeRV) 을 보고 다시 한번 톺아보자.

