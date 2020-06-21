## tabindex

개발에서 직접적으로 사용해본 적은 없지만 계속 눈에 띄는 속성인데 알지는 못해서 넘어갔었다. 그런데 계속 넘어가게 되면 언젠가 문제가 발생할 것 같아서 정리한다. 이 속성은 **"키보드 접근성"** , 즉 키보드로 웹사이트를 사용할 때 사용자의 접근성을 제어하는 속성이다.

[HTML 스펙](https://html.spec.whatwg.org/multipage/interaction.html#the-tabindex-attribute) 을 보면, tabindex가 기본적으로 적용되는 요소들은 다음과 같다.

* `href` 속성을 가진 `a`
* `href` 속성을 가진 `link`
* `button`
* `input` 의 타입이 `hidden` 이 아닌 경우
* `select`
* `textarea`
* `summary` 이면서 `details` 의 1번째 자식일 경우
* 그 외에도 있지만 더 궁금하다면 스펙을 보자.

위의 요소들은 tab 키로 초점을 받을 수 있다는 것이다. 이렇게 tab 키로 초점을 받는 요소와 그렇지 않는 요소를 제어하는 속성이 `tabindex` 이며 음수, 0, 양수에 따라 그 특징이 다르다.

* 음수: `-1` 인 경우로, 기본적으로 초점을 받는 위와 같은 요소들에게 초점을 받지 않도록 강제하는 방식이다.
* 0: 초점을 기본적으로 받지 못하는 요소인 `div`, `span` 등과 같은 요소들이 초점을 받을 수 있도록 해주는 것이다.
* 양수: 초점을 받는 요소들의 순서를 마크업 순서가 아닌 강제로 제어하는 것이다.

현재 0과 음수는 많이 사용하지만 양수의 경우는 마크업 순서가 아닐 경우 사용자의 키보드 접근성을 많이 해칠 수 있기 때문에 **권장하지 않는 방법이다.** 따라서, 정말 예외적이고 특별한 경우가 아니라면 `tabindex` 에 양수를 주는 것은 피하도록 하자.

## 참고

* [키보드 접근성을 고려한 tabindex의 사용](https://nuli.navercorp.com/community/article/1132726?email=true)
* [MDN, tabindex](https://developer.mozilla.org/ko/docs/Web/HTML/Global_attributes/tabindex)