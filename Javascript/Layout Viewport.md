## Layout Viewport

IntersectionObserver를 계속해서 사용하다가 원하는대로 intersection이 이루어지지 않아 찾아보니까 공식 문서에서 말하는  viewport는 현재 보이는 화면인 visual viewport가 아니라 렌더링되는 layout viewport라는 것을 알았다. 그래서 layout viewport가 어디서부터 어디까지인지 어떻게 체크하는가를 찾아보았지만 딱히 해결방법이 나와있지 않아 `getBoundingClientRect()` 로 해결하였다.
