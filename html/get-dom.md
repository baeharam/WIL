## DOM 엘리먼트를 가져오는 방법들

모든 DOM 엘리먼트들은 `querySelector*` 형식, 혹은 `getElementsBy*` 형식으로 가져올 수 있다. 하지만 퍼포먼스, 반환타입, 매개변수, 실시간인지 동적인지 등 다양한 측면에서 다르다.

| 함수                   | 실시간인가? | 타입           | 시간복잡도 | 매개변수           |
| ---------------------- | ----------- | -------------- | ---------- | ------------------ |
| querySelector          | 아니오      | Element        | O(n)       | 어떤 선택자도 가능 |
| querySelectorAll       | 아니오      | NodeList       | O(n)       | 어떤 선택자도 가능 |
| getElementById         | 예          | Element        | O(1)       | id                 |
| getElementsByClassName | 예          | HTMLCollection | O(1)       | class              |
| getElementsByTagName   | 예          | HTMLCollection | O(1)       | tag                |
| getElementsByName      | 예          | NodeList       | O(1)       | name               |

여기서 실시간이라는 의미는 해당 함수로 바꾼 DOM의 결과가 바로 다음 코드에 영향을 준다는 것이다. 이는 참조값을 가져오기 때문이며 실시간이 아니라는 것은 참조값이 아니라 값을 복사해서 가져온다는 것을 뜻한다. 시간복잡도는 `querySelector` 가 선택자로 엘리먼트를 찾을 때 id, class, tag, name 등 모든 부분을 봐야 하므로 `O(n)` 이 걸린다. 모던 브라우저들은 해쉬 테이블로 관리를 하기 때문에 `getElementsBy*` 유형의 함수들은 `O(1)` 이 걸리는 것이 자명하다.

<br>

## 참고

* [What is the time complexity of HTML DOM lookups](https://stackoverflow.com/questions/10444074/what-is-the-time-complexity-of-html-dom-lookups)
* [querySelector and querySelectorAll vs getElementsByClassName and getElementById in JavaScript](https://stackoverflow.com/questions/14377590/queryselector-and-queryselectorall-vs-getelementsbyclassname-and-getelementbyid)