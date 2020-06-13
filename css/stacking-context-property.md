## 쌓임 맥락(Stacking Context)을 형성하는 조건들

CSS를 공부하면서, 알지 못하는 부분에서 계속 막히는 현상을 보니, 쉽게 넘어갔던 부분들을 확실히 알아두는 것이 좋을 것 같아서 막히는 부분마다 확실히 정리해서 암기해두려고 한다. 이번엔 사용자의 관점에서 보게 되는 HTML 요소들이 쌓이는 쌓임 맥락이 언제 형성되는지 정리한다.

* **문서의 루트 요소(Root Stacking Context)**
* **`position: relative` 혹은 `position: absolute` 이면서 `z-index: auto` 가 아님**
* **`position: fixed` 혹은 `position: sticky` 는 `z-index` 에 상관없이 항상 쌓임 맥락을 형성한다.**
* **`display: flex` 의 하위 요소, 즉 flex-item 이면서 `z-index: auto` 가 아님**
* **`display: grid` 의 하위 요소이면서 `z-index: auto` 가 아님**
* **`opacity` 가 1보다 작음**
* 요소를 변형시키는 속성들
  * **`transform: none` 이 아님**
  * `transform-style: preserve-3d`
  * `perspective: none` 이 아님
* **`filter: none` 이 아님**
* **`will-change`의 값으로 기본값이 아닐 때 새로운 쌓임 맥락을 형성하는 속성이 지정되어 잇을 경우**
* `isolation: isolate`

이외에도 2가지인 *CSS region* 과 *paged media* 에 관련된 속성이 있는데, 실무에서 잘 사용하지 않거나 초안(draft)인 상태인 것 같아서 필요하다면 아래 링크를 참고하자. 어쨌든 여기서 반드시 알아야 한다고 생각하는 부분은 강조해두었으니 머리에 박아놓고 기억하도록 하자.

<br>

## 쌓임 순서(Stacking Order)

쌓임 맥락의 조건을 알면, 그 맥락에서 어떤 순서로 요소가 쌓이는가를 아는 것도 중요하다.

1. 루트요소
2. `position: static` 이 아닌 것 중에서 `z-index` 가 음수
3. `position: static` 인 요소
4. `position: static` 이 아닌 요소 중에서 `z-index: auto`
5. `position: static` 이 아닌 요소 중에서 `z-index` 가 양수

이를 이해하는 것이 중요한 이유는 **"쌓임 맥락을 형성해서 어쩔건데?"** 라는 질문에 대답할 수 있게 하기 때문이다. 처음 쌓임 맥락의 개념을 배웠을 때 어디다 쓰지 하는 생각을 했는데 쌓임 순서를 이해하게 되면 어떤 식으로 요소들이 쌓이고 왜 그러는지를 확실히 이해할 수 있을 것 같다.

<br>

## 참고

* [Which CSS properties create a stacking context?](https://stackoverflow.com/questions/16148007/which-css-properties-create-a-stacking-context)
* [MDN, 쌓임 맥락](https://developer.mozilla.org/ko/docs/Web/CSS/CSS_Positioning/Understanding_z_index/The_stacking_context)

