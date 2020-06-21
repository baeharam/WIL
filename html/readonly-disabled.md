## readonly vs disabled

`input` 요소의 스타일링이 달랐기 때문에 `readonly` 속성과 `disabled` 속성을 모두 부여해서 플래그(flag)를 바꾸는 방식으로 스타일링을 진행하였다. 하지만, 두 속성이 어떤 역할을 하는지 몰라서 이렇게 하는 것은 위험한 것 같다. 그 차이를 정리해보자.

|                                  | readonly | disabled |
| :------------------------------: | :------: | :------: |
|         **편집가능한가**         |   :x:    |   :x:    |
|     **기본스타일이 있는가**      |   :x:    |   :o:    |
|  **폼 요소와 같이 전송되는가**   |   :o:    |   :x:    |
|        **포커스 되는가**         |   :o:    |   :x:    |
| **모든 폼 요소가 속성을 갖는가** |   :x:    |   :o:    |

`<select>` , `<option>` , `<button>` 은 `readonly` 속성을 갖지 않는다. 어떤 것이 더 좋다고는 할 수 없고, 각자 상황에 맞게 써야 한다고 생각한다.



## 참고

* [What's the difference between disabled=“disabled” and readonly=“readonly” for HTML form input fields?](https://stackoverflow.com/questions/7730695/whats-the-difference-between-disabled-disabled-and-readonly-readonly-for-ht)