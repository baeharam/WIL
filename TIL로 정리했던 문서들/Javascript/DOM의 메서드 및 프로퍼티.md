* 쿼리
  * getElementsByTagName
  * getElementsByClassName
  * querySelectorAll
  * getElementById
  * querySelector
  * get은 HTMLCollection, query는 NodeList 반환. HTMLCollection은 실시간 반영이 되기 때문에 의도한 대로 동작하지 않을 수 있고 `forEach()` 와 같은 메서드가 없다.
* DOM 노드 접근
  * parentNode
  * firstChild, lastChild
  * firstElmentChild, lastElementChild
  * previousSibling, previousElementSibling
  * nextSibling, nextElementSibling
  * childNodes, children, hasChildNodes()
  * 그냥 Child/Sibling은 공백과 같은 특수문자를 포함하기 때문에 HTML 엘리먼트를 얻기 위해선 Element가 붙은 프로퍼티를 사용해야 한다.
  * childNodes는 NodeList를 반환하고 children은 HTMLCollection을 반환한다.
* 노드 값 변경
  * nodeType, nodeValue, nodeName
  * className, classList
  * hasAttribute, getAttribute, setAttribute, removeAttribute
  * textContent, innerText, innerHTML
  * innerText는 비표준이고, CSS를 고려하기 때문에 보이지 않게 설정되어 있으면 가져올 수 없다.
* DOM 조작
  * createElement, createTextNode, appendChild, removeChild
  * insertAdjacentHTML
  * style
  * innerHTML이나 insertAdjacentHTML의 경우 XSS 공격에 취약하다.
* html은 document.documentElement로, body는 document.body로 가져올 수 있음